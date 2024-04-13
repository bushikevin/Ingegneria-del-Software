package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ricevutiController implements  Initializable {
    @FXML
    private Button bt_indietro;
    @FXML
    public TableView<ordiniView> id_table;
    @FXML
    private TableColumn<ordiniView, LocalDate> col_consegna;
    @FXML
    private TableColumn<ordiniView, LocalDate> col_data;
    @FXML
    private TableColumn<ordiniView, Double> col_prezzo;
    @FXML
    private TableColumn<ordiniView, Integer> col_quantità;
    @FXML
    private TableColumn<ordiniView, String> col_vino;

    public static ObservableList<ordiniView> lista_ricevuti = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_vino.setCellValueFactory(new PropertyValueFactory<>("vino"));
        col_quantità.setCellValueFactory(new PropertyValueFactory<>("quantità"));
        col_prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        col_consegna.setCellValueFactory(new PropertyValueFactory<>("consegna"));
        col_prezzo.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double prezzo, boolean empty) {
                super.updateItem(prezzo, empty);
                if (empty || prezzo == null) {
                    setText(null);
                } else {
                    setText(String.format("€ %.2f", prezzo));
                }
            }
        });

        id_table.setItems(lista_ricevuti);

        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datiController.resetDati();
                Utilis.changeScene(event, "logged-in.fxml", "Home", null);
            }
        });
    }

    public static void setConsegna(int index, LocalDate consegna) {
        lista_ricevuti.get(index).setConsegna(consegna);
    }

}
