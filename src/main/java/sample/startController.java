package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class startController implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button button_signin;

    @FXML
    public TextField tf_username;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = tf_username.getText();
                String password = tf_password.getText();
                Utilis.loginInUser(event, username, password);
                UserModel.getInstance().setUsername(username);
            }
        });

        button_signin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "sign-up.fxml", "Registrati", null);
            }
        });
    }
}
