package client;

import client.gui.GUIManager;
import commons.responses.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientReciever {
    private final SocketChannel socketChannel;
    GUIManager guiManager;
    CommandHandler commandHandler;

    public ClientReciever(SocketChannel socketChannel, CommandHandler commandHandler, GUIManager guiManager) {
        this.commandHandler = commandHandler;
        this.socketChannel = socketChannel;
        this.guiManager = guiManager;
        new Thread(() -> {
            while (true) {
                try {
                    Response response = recieveCommand();
                    switch (response.getResponseType()) {
                        case AUTH_SUCCESS:
                            guiManager.setUser(((AuthSuccessResponse) response).getUser());
                            guiManager.showSuccessWindow();
                            break;
                        case AUTH_FAIL:
                            guiManager.showFailWindow(((AuthFailResponse) response).getError());
                            break;
                        case ADD_TICKET:
                            guiManager.addNewTicket(((AddTicketResponse) response).getTicket());
                            break;
                        case TICKET_REMOVE:
                            guiManager.removeTicket(((TicketRemoveResponse) response).getId());
                            break;
                        case UPDATE_TICKET:
                            guiManager.removeTicket(((UpdateTicketResponse) response).getTicket().getTicketId());
                            guiManager.addNewTicket(((UpdateTicketResponse) response).getTicket());
                            break;
                        case ADD_IF_FAIL:
                            guiManager.showFailWindow(((AddIfFail) response).getError());
                            break;
                        case SERVER_STOP: {
                            guiManager.showFailWindow("Server is dead close app and restart");
                            return;
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public Response recieveCommand() throws ClassNotFoundException {
        // Read response
        ByteBuffer readBuffer = ByteBuffer.allocate(102400);
        int num;
        try {
            if ((num = socketChannel.read(readBuffer)) > 0) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(readBuffer.array());//массив байтов
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Response response = (Response) objectInputStream.readObject();//считываем объект
                return response;
            }
        } catch (IOException e) {
            return new ServerStopResponse(guiManager.getUser());
        }
        return new ServerStopResponse(guiManager.getUser());
    }


}
