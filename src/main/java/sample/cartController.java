package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cartController implements Initializable {
    @FXML
    private Button button_indietro;
    @FXML
    private Button bt_compra;
    @FXML
    public Label nome1, nome2, nome3, nome4, nome5;
    @FXML
    private Label quantità1, quantità2, quantità3, quantità4, quantità5;
    @FXML
    public Label prezzo1, prezzo2, prezzo3, prezzo4, prezzo5;
    @FXML
    public Label label_totale;
    @FXML
    public Label tipo1, tipo2, tipo3, tipo4, tipo5;
    @FXML
    public Label sconto1, sconto2, sconto3, sconto4, sconto5;

    public static Label[] static_prezzo;
    public static Label[] static_nome;
    public static Label[] static_tipo;
    public static Label[] static_sconto;
    public static Label[] static_quantità;
    private int quantita = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datiController.resetDati();
                Utilis.changeScene(event, "vini.fxml", "Lista", null);}});

        bt_compra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("compra.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
            }
        });

        static_nome = new Label[]{nome1, nome2, nome3, nome4, nome5};
        static_prezzo = new Label[]{prezzo1, prezzo2, prezzo3, prezzo4, prezzo5};
        static_tipo = new Label[]{tipo1, tipo2, tipo3, tipo4, tipo5};
        static_sconto = new Label[] {sconto1, sconto2, sconto3, sconto4, sconto5};
        static_quantità = new Label[]{quantità1, quantità2, quantità3, quantità4, quantità5};
        updatePrezzoTotale();
    }

    @FXML
    void b_incrementa(ActionEvent event) {quantita++;updateQuantita(quantità1, 0);}
    @FXML
    void b_incrementa2(ActionEvent event) {quantita++;updateQuantita(quantità2, 1);}
    @FXML
    void b_incrementa3(ActionEvent event) {quantita++;updateQuantita(quantità3, 2);}
    @FXML
    void b_incrementa4(ActionEvent event) {quantita++;updateQuantita(quantità4, 3);}
    @FXML
    void b_incrementa5(ActionEvent event) {quantita++;updateQuantita(quantità5, 4);}
    @FXML
    void b_decrementa(ActionEvent event) {if (quantita > 0) {quantita--;updateQuantita(quantità1, 0);}}
    @FXML
    void b_decrementa2(ActionEvent event) {if (quantita > 0) {quantita--;updateQuantita(quantità2, 1);}}
    @FXML
    void b_decrementa3(ActionEvent event) {if (quantita > 0) {quantita--;updateQuantita(quantità3, 2);}}
    @FXML
    void b_decrementa4(ActionEvent event) {if (quantita > 0) {quantita--;updateQuantita(quantità4, 3);}}
    @FXML
    void b_decrementa5(ActionEvent event) {if (quantita > 0) {quantita--;updateQuantita(quantità5, 4);}}

    @FXML
    void updateQuantita(Label label, int index) {
        label.setText(String.valueOf(quantita));
        updatePrezzo(index);
        updatePrezzoTotale();
    }

    void updatePrezzo(int index) {
        if (datiController.getDatiPrezzo() != null) {
            if (index >= 0 && index < datiController.getDatiPrezzo().size()) {
                double totale = quantita * Double.parseDouble(datiController.getDatiPrezzo().get(index));

                if (index < static_tipo.length) {
                    if ("cassa da 6 bottiglie di".equals(static_tipo[index].getText()) && quantita > 1) {
                        totale *= 0.98; // Sconto del 2%
                        static_sconto[index].setText("+ 2% di sconto");
                    } else {
                        if ("cassa da 6 bottiglie di".equals(static_tipo[index].getText()) && quantita == 1) {
                            static_sconto[index].setText("sconto del 5%");
                        }
                    }
                    static_prezzo[index].setText(String.format("€ %.2f", totale));
                }
            }
        }
    }

    void updatePrezzoTotale() {
        double totale = 0.0;
        for (int i = 0; i < static_prezzo.length; i++) {
            String prezzoText = static_prezzo[i].getText().replace("€ ", "").replace(",", ".");
            if (!prezzoText.isEmpty()) {
                totale += Double.parseDouble(prezzoText);
            }
        }
        label_totale.setText(String.format("€ %.2f", totale));
    }

    public static void clearCartData() {
        for (Label label : static_nome){
            label.setText("");
        }
        for (Label label : static_quantità){
            label.setText("");
        }
        for (Label label : static_prezzo){
            label.setText("");
        }
    }

}
