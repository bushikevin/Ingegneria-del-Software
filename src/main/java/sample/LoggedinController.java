package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.EventHandler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class LoggedinController extends ordiniController implements  Initializable {

    @FXML
    private Button button_logout;

    @FXML
    private Button button_ordini;

    @FXML
    private Button button_vini;

    @FXML
    private Button bt_ricevuti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "start.fxml", "Log in", null);
            }
        });

        button_vini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "vini.fxml", "vini", null);}
        });

        button_ordini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (static_nome == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Devi prima comprare qualcosa");
                    alert.show();
                } else {
                    cartController.clearCartData();
                    Utilis.changeScene(event, "ordini.fxml", "vini", null);

                }
            }
        });

        bt_ricevuti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "ricevuti.fxml", "vini", null);}
        });
    }
}
