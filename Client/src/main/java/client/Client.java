package client;

import Exeptions.CommandException;
import client.gui.GUIManager;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.NoSuchElementException;


public class Client {
    private static String host = "localhost";
    private static int port = 8080;
    static String path;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            if (args.length > 0)
                path = args[0];
            if (args.length > 1)
                host = args[1];

            CommandHandler commandHandler = new CommandHandler(reader, port, host);
            JFrame.setDefaultLookAndFeelDecorated(true);
            javax.swing.SwingUtilities.invokeLater(() -> {
                GUIManager guiManager = new GUIManager(commandHandler, commandHandler.getClientSender());
                new ClientReciever(commandHandler.getSocketChannel(), commandHandler, guiManager);
                guiManager.startApplication();
            });
            commandHandler.getClientSender().setCommandHandler(commandHandler);
            System.out.println("Введите команду");
            while (true) {
                try {
                    commandHandler.handleInput();
                } catch (CommandException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Конец ввода");
        } catch (NumberFormatException e) {
            System.out.println("Неправильно задан порт");
        } catch (JsonSyntaxException e) {
            System.out.println("Ошибка парсинга файла");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено имя файла");
        } catch (ConnectException e) {
            System.out.println("Сервер недоступен");
        } catch (IOException e) {
            System.out.println("Потеряно соединение с сервером");
        }
    }
}
