package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static sample.adminConsegnatiController.lista_venduti;
import static sample.adminOrdiniController.lista_ordini;
import static sample.ricevutiController.lista_ricevuti;

public class ordiniController extends cartController implements  Initializable {
    @FXML
    private Button bt_indietro;
    @FXML
    public TableView<ordiniView> id_table;
    @FXML
    private TableColumn<ordiniView, LocalDate> col_data;
    @FXML
    private TableColumn<ordiniView, Double> col_prezzo;
    @FXML
    private TableColumn<ordiniView, Integer> col_quantità;
    @FXML
    private TableColumn<ordiniView, String> col_vino;
    @FXML
    private TableColumn<ordiniView, CheckBox> col_consegnato;
    @FXML
    private Label totale;

    String username = UserModel.getInstance().getUsername();

    private static ObservableList<ordiniView> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_vino.setCellValueFactory(new PropertyValueFactory<>("vino"));
        col_quantità.setCellValueFactory(new PropertyValueFactory<>("quantità"));
        col_prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        col_consegnato.setCellValueFactory(new PropertyValueFactory<>("consegnato"));
        col_prezzo.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double prezzo, boolean empty) {super.updateItem(prezzo, empty);
                if (empty || prezzo == null) {
                    setText(null);
                } else {
                    setText(String.format("€ %.2f", prezzo));
                }
            }
        });

        for (int i = 0; i < static_nome.length; i++) {
            String nomeVino = static_nome[i].getText();
            String quantitaText = static_quantità[i].getText();
            String prezzoText = static_prezzo[i].getText().replace("€ ", "").replace(",", ".");
            CheckBox consegnato = new CheckBox();

            if (!nomeVino.isEmpty() && !quantitaText.isEmpty() && !prezzoText.isEmpty()) {
                int quantitaVino = Integer.parseInt(quantitaText);
                Double prezzoVino = Double.valueOf(prezzoText);
                aggiungiOrdine(nomeVino, quantitaVino, prezzoVino, id_table, consegnato);
            } else {
                break;
            }
        }

        id_table.setItems(lista);
        updateTotale();

        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datiController.resetDati();
                Utilis.changeScene(event, "logged-in.fxml", "Home", null);
            }
        });
    }
    public void aggiungiOrdine(String nomeVino, int quantita, double prezzo, TableView<ordiniView> tableView, CheckBox consegnato) {
        ordiniView ordine = new ordiniView(nomeVino, quantita, prezzo, LocalDate.now(), consegnato);
        lista.add(ordine);
    }

    public void updateTotale() {
        Double tot = 0.0;
        for (ordiniView ordine : lista) {
            tot += ordine.getPrezzo();
        }
        totale.setText(String.format("€ %.2f", tot));
    }

    @FXML
    private void bt_conferma(ActionEvent event){
        ObservableList<ordiniView> ordiniSelezionati = FXCollections.observableArrayList();
        for (int i = 0; i < id_table.getItems().size(); i++){
            ordiniView ordine = id_table.getItems().get(i);
            if (ordine.getConsegnato().isSelected()){
                ordiniSelezionati.add(ordine);
            }
        }
        lista.removeAll(ordiniSelezionati);

        lista_ricevuti.addAll(ordiniSelezionati);
        for (ordiniView ordine : ordiniSelezionati) {
            ricevutiController.setConsegna(ricevutiController.lista_ricevuti.indexOf(ordine), LocalDate.now());
        }

        lista_venduti.addAll(ordiniSelezionati);
        for (ordiniView ordine : ordiniSelezionati){
            adminConsegnatiController.setUsername(adminConsegnatiController.lista_venduti.indexOf(ordine), username);
        }


        for (ordiniView ordine : ordiniSelezionati) {
            // Trova l'ordine corrispondente in lista_ordini
            adminOrdiniView ordineCorrispondente = null;
            for (adminOrdiniView adminOrdine : lista_ordini) {
                if (sonoOrdiniCorrispondenti(ordine, adminOrdine)) {
                    ordineCorrispondente = adminOrdine;
                    break;
                }
            }
            // Rimuovi l'ordine corrispondente, se trovato
            if (ordineCorrispondente != null) {
                lista_ordini.remove(ordineCorrispondente);
            }
        }

    }

    private boolean sonoOrdiniCorrispondenti(ordiniView ordine, adminOrdiniView adminOrdine) {
        // Implementa il criterio di corrispondenza tra ordiniView e adminOrdiniView
        // ad esempio, confronta nome del vino, quantità e prezzo
        return ordine.getVino().equals(adminOrdine.getVino()) &&
                ordine.getQuantità() == adminOrdine.getQuantità() &&
                ordine.getPrezzo() == adminOrdine.getPrezzo();
    }
}
