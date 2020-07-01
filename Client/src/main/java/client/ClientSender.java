package client;

import Exeptions.CommandException;
import commons.commands.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Getter
@Setter
public class ClientSender {
    private final SocketChannel socketChannel;
    CommandHandler commandHandler;

    public ClientSender(SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
    }

    public void sendCommand(Command command) throws IOException, CommandException {
        // Send requests
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(command);
        oos.flush();
        byte[] data = bos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        socketChannel.write(buffer);
    }
}
