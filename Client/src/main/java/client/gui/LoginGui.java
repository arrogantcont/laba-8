package client.gui;

import Exeptions.CommandException;
import client.ClientSender;
import commons.User;
import commons.commands.LoginCommand;

import javax.swing.*;
import java.io.IOException;

public class LoginGui extends JPanel {
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JTextField userText;
    private JButton loginButton;
    private JButton registerButton;
    private ClientSender clientSender;
    private GUIManager guiManager;
    private JComboBox languages;

    public LoginGui(ClientSender clientSender, GUIManager guiManager) {
        super();
        this.clientSender = clientSender;
        this.guiManager = guiManager;
        placeLoginComponents();

    }

    private void placeLoginComponents() {
        this.setLayout(null);

        userLabel = new JLabel(UIMessages.user);

        userLabel.setBounds(10, 20, 80, 25);
        this.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        this.add(userText);

        passwordLabel = new JLabel(UIMessages.password);
        passwordLabel.setBounds(10, 50, 80, 25);
        this.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        this.add(passwordText);

        // Creating login button
        loginButton = new JButton(UIMessages.login);
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener((actionEvent) -> {
            String username = userText.getText();

            String password = passwordText.getText();
            if (username.equals("") || password.equals("")) guiManager.showFailWindow(UIMessages.null_field_message);
            else {
                User user = new User(username, password);
                LoginCommand loginCommand = new LoginCommand(user);
                try {
                    clientSender.sendCommand(loginCommand);
                } catch (IOException | CommandException e) {
                    e.printStackTrace();
                }
            }
        });

        languages = new JComboBox(new String[]{"ru", "eng", "lv", "ro", "es"});
        languages.setBounds(10, 110, 80, 25);
        languages.addActionListener(arg0 -> {
            String s = (String) languages.getSelectedItem();
            switch (s) {
                case "ru": {
                    UIMessages.setRu();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    System.out.println(UIMessages.event_question);
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    break;
                }
                case "eng": {
                    UIMessages.setEn();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    break;
                }
                case "lv": {
                    UIMessages.setLv();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    break;
                }
                case "ro": {
                    UIMessages.setRo();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    break;
                }
                case "es": {
                    UIMessages.setCo();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    break;
                }
            }
        });
        this.add(languages);
        this.add(loginButton);
        registerButton = new JButton(UIMessages.register);
        registerButton.setBounds(110, 80, 80, 25);
        registerButton.addActionListener(arg0 -> guiManager.showRegisterForm());
        this.add(registerButton);
    }

    public void refreshComps() {
        passwordLabel.setText(UIMessages.password);
        userLabel.setText(UIMessages.user);
        loginButton.setText(UIMessages.login);
        registerButton.setText(UIMessages.register);
    }
}
