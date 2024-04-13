package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_signup;

    @FXML
    private Button button_login;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_cognome;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nome = tf_nome.getText();
                String cognome = tf_cognome.getText();

                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_nome.getText().trim().isEmpty() && !tf_cognome.getText().trim().isEmpty()){
                    Utilis.signUpUser(event, tf_username.getText(), tf_email.getText(), tf_password.getText(), nome, cognome);
                    UserModel.getInstance().setNome(nome);
                    UserModel.getInstance().setCognome(cognome);
                } else {
                    System.out.println("Immetti le tue credenziali");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Metti le tue credenziali per accedere");
                    alert.show();
                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "start.fxml", "Accedi", null);
            }
        });
    }
}
