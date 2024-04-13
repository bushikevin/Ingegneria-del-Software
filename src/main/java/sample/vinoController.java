package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class vinoController extends cartController implements Initializable {

    @FXML
    private TableView<vinoView> id_table;
    @FXML
    private TableColumn<vinoView, String> col_vino;
    @FXML
    private TableColumn<vinoView, Integer> col_anno;
    @FXML
    private TableColumn<vinoView, Double> col_prezzo;
    @FXML
    private Button button_cart;
    @FXML
    private Button button_indietro;

    private ObservableList<vinoView> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lista = getViniFromDatabase();

        col_vino.setCellValueFactory(new PropertyValueFactory<>("vino"));
        col_anno.setCellValueFactory(new PropertyValueFactory<>("anno"));
        col_prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
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

        id_table.setItems(lista);

        button_cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (dati_vino != null){
                    Platform.runLater(()->{
                        for (int i = 0; i < dati_vino.size() && i < static_nome.length; i++) {
                            static_nome[i].setText(dati_vino.get(i));

                            String tipo = tipi[i];
                            if (tipo != null) {
                                static_tipo[i].setText(tipo);
                            }

                            String sconto = sconti[i];
                            if (sconto != null) {
                                static_sconto[i].setText(sconto);
                            }
                        }
                    });
                }
                if (dati_prezzo != null){
                    Platform.runLater(()->{
                        for (int i = 0; i < datiController.getDatiPrezzo().size() && i < static_prezzo.length; i++) {
                            double prezzo = Double.parseDouble(datiController.getDatiPrezzo().get(i));
                            static_prezzo[i].setText(String.format("€ %.2f", prezzo));
                        }
                    });
                }
                Utilis.changeScene(event, "cart.fxml", "Carrello", null);}
        });

        button_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "logged-in.fxml", "Home", null);
            }
        });
    }

    private ObservableList<String> dati_vino = FXCollections.observableArrayList();
    public ObservableList<String> dati_prezzo = FXCollections.observableArrayList();
    private String[] tipi = new String[5];
    private String[] sconti = new String[5];


    @FXML
    void bt_aggiungi(ActionEvent event) {
        vinoView data = id_table.getSelectionModel().getSelectedItem();
        if (data != null) {
            String vino = data.getVino();
            Double prezzo = data.getPrezzo();
            if (!dati_vino.contains(vino)) {
                dati_vino.add(vino);
                int index = dati_vino.indexOf(vino);
                tipi[index] = "bottiglia di";
            }
            datiController.aggiungiPrezzo(prezzo.toString());
        }
    }

    @FXML
    void bt_aggiungiCassa(ActionEvent event) {
        vinoView data = id_table.getSelectionModel().getSelectedItem();
        if (data != null) {
            String vino = data.getVino();
            Double prezzoTotale = data.getPrezzo() * 6;
            Double prezzo = prezzoTotale * 0.95;
            if (!dati_vino.contains(vino)) {
                dati_vino.add(vino);
                int index = dati_vino.indexOf(vino);
                tipi[index] = "cassa da 6 bottiglie di";
                sconti[index] = "sconto del 5%";
            }
            datiController.aggiungiPrezzo(prezzo.toString());
        }
    }

    private ObservableList<vinoView> getViniFromDatabase() {
        ObservableList<vinoView> catalogo = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            psSelect = connection.prepareStatement("SELECT * FROM vini");
            resultSet = psSelect.executeQuery();

            while (resultSet.next()) {
                String vino = resultSet.getString("vino");
                int anno = resultSet.getInt("anno");
                double prezzo = resultSet.getDouble("prezzo");
                catalogo.add(new vinoView(vino, anno, prezzo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (psSelect != null) psSelect.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return catalogo;
    }
}
