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
import java.sql.*;
import java.util.ResourceBundle;

public class gestioneViniController implements Initializable {
    @FXML
    private Button bt_indietro;
    @FXML
    private Button bt_elimina;
    @FXML
    private TableColumn<adminVinoView, String> col_vino;
    @FXML
    private TableColumn<adminVinoView, Integer> col_anno;
    @FXML
    private TableColumn<adminVinoView, Double> col_prezzo;
    @FXML
    private TableColumn<adminVinoView, String> col_fornitore;
    @FXML
    private TableView<adminVinoView> id_table;
    @FXML
    private TextField tx_cerca;

    private ObservableList<adminVinoView> catalogo = FXCollections.observableArrayList();
    private ObservableList<adminVinoView> filteredCatalogo = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catalogo = getViniFromDatabase();
        filteredCatalogo.addAll(catalogo);

        col_vino.setCellValueFactory(new PropertyValueFactory<>("vino"));
        col_anno.setCellValueFactory(new PropertyValueFactory<>("anno"));
        col_fornitore.setCellValueFactory(new PropertyValueFactory<>("fornitore"));
        col_prezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
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

        id_table.setItems(filteredCatalogo);

        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "adminHome.fxml", "Home", null);
            }
        });

        tx_cerca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredCatalogo.setAll(catalogo);
            } else {
                searchVino(newValue.trim());
            }
        });

        bt_elimina.setOnAction(event -> {
            adminVinoView selectedVino = id_table.getSelectionModel().getSelectedItem();
            if (selectedVino != null) {
                deleteVino(selectedVino);
            } else {
                // Nessun vino selezionato, mostra un messaggio di avviso
                showAlert(Alert.AlertType.WARNING, "Nessun vino selezionato", "Seleziona un vino da eliminare.");
            }
        });
    }

    private ObservableList<adminVinoView> getViniFromDatabase() {
        ObservableList<adminVinoView> catalogo = FXCollections.observableArrayList();
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
                String fornitore = resultSet.getString("fornitore");
                catalogo.add(new adminVinoView(vino, anno, prezzo, fornitore));
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

    private void searchVino(String searchString) {
        filteredCatalogo.clear();
        for (adminVinoView vino : catalogo) {
            if (vino.getVino().toLowerCase().contains(searchString.toLowerCase())) {
                filteredCatalogo.add(vino);
            }
        }
    }

    private void deleteVino(adminVinoView vinoToDelete) {
        // Rimuovi il vino dalla TableView
        catalogo.remove(vinoToDelete);
        filteredCatalogo.remove(vinoToDelete);

        // Rimuovi il vino dal database
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            psDelete = connection.prepareStatement("DELETE FROM vini WHERE vino = ?");
            psDelete.setString(1, vinoToDelete.getVino());
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostra un messaggio di errore in caso di fallimento dell'eliminazione
            showAlert(Alert.AlertType.ERROR, "Errore durante l'eliminazione", "Impossibile eliminare il vino.");
        } finally {
            try {
                if (psDelete != null) psDelete.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

