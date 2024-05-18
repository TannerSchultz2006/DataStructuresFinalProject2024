package cos.dstruct.klassroom.controllers;

import cos.dstruct.klassroom.Klassroom;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class LoginHandler {

    public TextField username_field_log;
    public PasswordField password_field_log;
    public LoginHandler(Klassroom klassroom) {
        this.klassroom = klassroom;
    }

    public void loginEvent(ActionEvent event) throws RuntimeException {
        try {
            String username = username_field_log.getText();
            String password = password_field_log.getText();
            klassroom.loginUser(username, password);
        } catch (IllegalArgumentException e) {
            password_field_log.setText("");
            System.out.println("Silly goose! You will never guess the right password.");
        }
    }

    public void showSignupScreen(ActionEvent event) {
        klassroom.showSignupScreen();
    }
    Klassroom klassroom;

}
