package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class adminConsegnatiController implements Initializable {

    @FXML
    private Button bt_indietro;
    @FXML
    private TableColumn<ordiniView, String> col_username;
    @FXML
    private TableColumn<ordiniView, String> col_vino;
    @FXML
    private TableColumn<ordiniView, Integer> col_quantità;
    @FXML
    private TableColumn<ordiniView, Double> col_prezzo;
    @FXML
    private TableColumn<ordiniView, LocalDate> col_data;
    @FXML
    private TableColumn<ordiniView, LocalDate> col_consegna;
    @FXML
    public TableView<ordiniView> id_table;

    public static ObservableList<ordiniView> lista_venduti = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
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

        id_table.setItems(lista_venduti);

        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datiController.resetDati();
                Utilis.changeScene(event, "adminHome.fxml", "Home", null);
            }
        });
    }

    public static void setUsername(int index, String username) {
        lista_venduti.get(index).setUsername(username);
    }
}
