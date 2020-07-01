package client.gui;

import Exeptions.CommandException;
import client.ClientSender;
import commons.User;
import commons.commands.RegisterCommand;

import javax.swing.*;
import java.io.IOException;

public class RegisterGui extends JPanel {

    private JLabel userLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JTextField userText;
    private final ClientSender clientSender;
    private final GUIManager guiManager;
    private JButton signUpButton;
    private JButton goBackButton;
    private JLabel password1Label;
    private JPasswordField password1Text;

    public RegisterGui(ClientSender clientSender, GUIManager guiManager) {
        super();
        this.clientSender = clientSender;
        this.guiManager = guiManager;
        placeRegisterComponents();

    }

    private void placeRegisterComponents() {

        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        this.setLayout(null);

        // Creating JLabel
        userLabel = new JLabel(UIMessages.user);
        /* This method specifies the location and size
         * of component. setBounds(x, y, width, height)
         * here (x,y) are coordinates from the top left
         * corner and remaining two arguments are the width
         * and height of the component.
         */
        userLabel.setBounds(10, 20, 80, 25);
        this.add(userLabel);

        /* Creating text field where user is supposed to
         * enter user name.
         */
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        this.add(userText);

        passwordLabel = new JLabel(UIMessages.password);
        passwordLabel.setBounds(10, 50, 80, 25);
        this.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        this.add(passwordText);

        password1Label = new JLabel(UIMessages.password);
        password1Label.setBounds(10, 80, 80, 25);
        this.add(password1Label);


        password1Text = new JPasswordField(20);
        password1Text.setBounds(100, 80, 165, 25);
        this.add(password1Text);


        signUpButton = new JButton(UIMessages.register);
        signUpButton.setBounds(10, 110, 80, 25);

        this.add(signUpButton);
        signUpButton.addActionListener((actionEvent) -> {
            String username = userText.getText();
            String password = passwordText.getText();
            String password1 = password1Text.getText();
            if (username.equals("") || password.equals("") || password1.equals(""))
                guiManager.showFailWindow(UIMessages.null_field_message);
            else if (password.equals(password1)) {
                User user = new User(username, password);
                RegisterCommand registerCommand = new RegisterCommand(user);
                try {
                    clientSender.sendCommand(registerCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            } else guiManager.showFailWindow(UIMessages.not_matching_pass_message);
        });
        goBackButton = new JButton(UIMessages.go_back);
        goBackButton.setBounds(110, 110, 80, 25);
        goBackButton.addActionListener(arg0 -> guiManager.showLoginForm());
        this.add(goBackButton);
    }

    public void refreshComps() {
        userLabel.setText(UIMessages.user);
        passwordLabel.setText(UIMessages.password);
        signUpButton.setText(UIMessages.register);
        goBackButton.setText(UIMessages.go_back);
        password1Label.setText(UIMessages.password);
    }
}
