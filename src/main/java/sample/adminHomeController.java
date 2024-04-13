package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.cartController.static_nome;

public class adminHomeController implements Initializable {
    @FXML
    private Button button_logout;
    @FXML
    private Button bt_gestioneVini;
    @FXML
    private Button bt_gestioneClienti;
    @FXML
    private Button bt_ordini;
    @FXML
    private Button bt_consegnati;
    @FXML
    private Button bt_vini;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "start.fxml", "Log in", null);
            }
        });
        bt_vini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "adminVini.fxml", "vini", null);}
        });
        bt_ordini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (static_nome == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Non ci sono ordini");
                    alert.show();
                } else {
                    Utilis.changeScene(event, "adminOrdini.fxml", "vini", null);
                }
            }
        });

        bt_consegnati.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "adminConsegnati.fxml", "vini", null);}
        });

        bt_gestioneVini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "gestioneVini.fxml", "vini", null);}
        });

        bt_gestioneClienti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { Utilis.changeScene(event, "gestioneClienti.fxml", "vini", null);}
        });
    }
}

