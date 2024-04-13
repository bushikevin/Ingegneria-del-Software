package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class compraController extends cartController implements Initializable {
    @FXML
    private Button bt_acquisto;

    @FXML
    private Button bt_esci;

    @FXML
    private TextField tf_telefono;

    @FXML
    private TextField tf_città;

    @FXML
    private TextField tf_indirizzo;

    @FXML
    private TextField tf_paese;

    @FXML
    private TextField tf_post;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_esci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datiController.resetDati();
                Utilis.changeScene(event, "logged-in.fxml", "Home", null);
            }
        });

        bt_acquisto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < static_nome.length; i++) {
                    String nomeVino = static_nome[i].getText();
                    String quantitaText = static_quantità[i].getText();
                    String prezzoText = static_prezzo[i].getText().replace("€ ", "").replace(",", ".");
                }

                String paese = tf_paese.getText();
                UserModel.getInstance().setPaese(paese);

                String città = tf_città.getText();
                UserModel.getInstance().setCittà(città);

                String inidirizzo = tf_indirizzo.getText();
                UserModel.getInstance().setIndirizzo(inidirizzo);

                String posta = tf_post.getText();
                UserModel.getInstance().setPosta(posta);

                String telefono = tf_telefono.getText();
                UserModel.getInstance().setTelefono(telefono);

                Utilis.changeScene(event, "ordini.fxml", "Ordini", null);
            }
        });
    }
}
