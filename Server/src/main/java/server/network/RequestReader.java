package server.network;

import commons.commands.Command;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class RequestReader {
    private static final Logger READ_LOGGER = Logger.getLogger(RequestReader.class.getName());
    ForkJoinPool pool = new ForkJoinPool(
            4, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    ClientCommandHandler clientCommandHandler;

    public RequestReader(ClientCommandHandler clientCommandHandler) {
        this.clientCommandHandler = clientCommandHandler;
    }

    public String encryptThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void clientService(SocketChannel channel, SelectionKey key) {
        pool.submit(new RecursiveAction() {
            //пример официант - переложить ответственность
            @Override
            protected void compute() {
                synchronized (channel) {
                    try {
                        ByteBuffer readBuffer = ByteBuffer.allocate(102400);//выделяем буффер на 1Кб
                        if (!channel.isOpen())
                            return;
                        int num = channel.read(readBuffer);
                        if (num > 0) {
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(readBuffer.array());//массив байтов
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            Command command = (Command) objectInputStream.readObject();//считываем объект
                            READ_LOGGER.info("Новый запрос от клиента: " + command);
                            if (command.getCommand().equals("login") || command.getCommand().equals("register"))
                            command.getUser().setPassword(encryptThisString(command.getUser().getPassword()));
                            clientCommandHandler.handleCommand(command, channel);
                        } else if (num == -1) {
                            // - 1 represents that the connection has been closed
                            channel.close();
                        }
                    } catch (ClosedChannelException e) {
                        key.cancel();
                        READ_LOGGER.warning("Потеряно соединение с клиентом");
                        System.out.println("Клиент отключился");
                    } catch (Exception e) {
                        key.cancel();
                        clientCommandHandler.getActiveUsers().remove(channel);
                    }

                }
            }
        });
    }


}
