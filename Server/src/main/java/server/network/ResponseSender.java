package server.network;

import commons.responses.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ResponseSender {
    private static final Logger RESPONSE_LOGGER = Logger.getLogger(ResponseSender.class.getName());
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

    public void sendAnswer(Response s, SocketChannel channel) {
        fixedThreadPool.submit(() -> {
            try {
                send(s, channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void send(Response s, SocketChannel channel) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(s);
        oos.flush();
        byte[] data = bos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.write(buffer);
        RESPONSE_LOGGER.info("Отправка ответа клиенту: " + s);
    }
}
