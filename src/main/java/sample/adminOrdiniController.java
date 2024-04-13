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

public class adminOrdiniController extends cartController implements Initializable {

    @FXML
    private Button bt_indietro;
    @FXML
    private TableView<adminOrdiniView> id_table;
    @FXML
    private TableColumn<adminOrdiniView, LocalDate> col_data;
    @FXML
    private TableColumn<adminOrdiniView, String> col_username;
    @FXML
    private TableColumn<adminOrdiniView, String> col_nome;
    @FXML
    private TableColumn<adminOrdiniView, String> col_cognome;
    @FXML
    private TableColumn<adminOrdiniView, String> col_vino;
    @FXML
    private TableColumn<adminOrdiniView, Integer> col_quantità;
    @FXML
    private TableColumn<adminOrdiniView, Double> col_prezzo;
    @FXML
    private TableColumn<adminOrdiniView, LocalDate> col_paese;
    @FXML
    private TableColumn<adminOrdiniView, LocalDate> col_città;
    @FXML
    private TableColumn<adminOrdiniView, String> col_indirizzo;
    @FXML
    private TableColumn<adminOrdiniView, String> col_posta;
    @FXML
    private TableColumn<adminOrdiniView, Integer> col_telefono;

    String username = UserModel.getInstance().getUsername();
    String nome = UserModel.getInstance().getNome();
    String cognome = UserModel.getInstance().getCognome();
    String paese = UserModel.getInstance().getPaese();
    String città = UserModel.getInstance().getCittà();
    String indirizzo = UserModel.getInstance().getIndirizzo();
    String posta = UserModel.getInstance().getPosta();
    String telefono = UserModel.getInstance().getTelefono();

    public static ObservableList<adminOrdiniView> lista_ordini = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cartController.clearCartData();
                Utilis.changeScene(event, "adminHome.fxml", "Home", null);
            }
        });

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        col_vino.setCellValueFactory(new PropertyValueFactory<>("vino"));
        col_quantità.setCellValueFactory(new PropertyValueFactory<>("quantità"));
        col_prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        col_paese.setCellValueFactory(new PropertyValueFactory<>("paese"));
        col_città.setCellValueFactory(new PropertyValueFactory<>("città"));
        col_indirizzo.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        col_posta.setCellValueFactory(new PropertyValueFactory<>("posta"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data"));
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
            String vino = static_nome[i].getText();
            String quantità = static_quantità[i].getText();
            String prezzo = static_prezzo[i].getText().replace("€ ", "").replace(",", ".");

            if (!vino.isEmpty() && !quantità.isEmpty() && !prezzo.isEmpty()) {
                int quantitaVino = Integer.parseInt(quantità);
                Double prezzoVino = Double.valueOf(prezzo);
                aggiungiDati(username, nome, cognome, vino, quantitaVino, prezzoVino, paese, città, indirizzo, posta, telefono, id_table);
            } else {
                break;
            }
        }

        id_table.setItems(lista_ordini);
    }

    public void aggiungiDati(String username, String nome, String cognome, String vino, int quantità, double prezzo, String paese, String città, String indirizzo, String posta, String telefono, TableView<adminOrdiniView> tableView) {
        adminOrdiniView dati = new adminOrdiniView(username, nome, cognome, vino, quantità, prezzo, paese, città, indirizzo, posta, telefono, LocalDate.now());
        lista_ordini.add(dati);
    }

}
