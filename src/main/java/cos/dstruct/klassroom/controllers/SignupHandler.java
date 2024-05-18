package cos.dstruct.klassroom.controllers;

import cos.dstruct.klassroom.Klassroom;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class SignupHandler {


    public TextField username_field;
    public PasswordField password_field;
    public PasswordField password_field1;
    public SignupHandler(Klassroom klassroom) {
        this.klassroom = klassroom;
    }

    public void showLoginScreen(ActionEvent event) {
        klassroom.showLoginScreen();
    }

    public void addUserEvent(ActionEvent event) {
        try {
            String username = username_field.getText();
            String password = password_field.getText();

            if (password.equals(password_field1.getText())) {
                klassroom.addUser(username, password);
                klassroom.loginUser(username, password);
                username_field.setText("");
            } else {
                System.err.println("Passwords do NOT match, goose.");
            }
            password_field1.setText("");
            password_field.setText("");
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }
    }
    Klassroom klassroom;

}
