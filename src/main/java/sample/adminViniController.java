package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminViniController implements Initializable {

    @FXML
    private Button button_indietro;
    @FXML
    private Button button_aggiungi;
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
    private TextField tf_anno;
    @FXML
    private TextField tf_fornitore;
    @FXML
    private TextField tf_prezzo;
    @FXML
    private TextField tf_vino;

    private ObservableList<adminVinoView> catalogo = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catalogo = getViniFromDatabase();

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

        id_table.setItems(catalogo);

        button_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "adminHome.fxml", "Home", null);
            }
        });

        button_aggiungi.setOnAction(event -> {
            String vino = tf_vino.getText();
            int anno = Integer.parseInt(tf_anno.getText());
            double prezzo = Double.parseDouble(tf_prezzo.getText());
            String fornitore = tf_fornitore.getText();

            if (!vino.trim().isEmpty() && !tf_anno.getText().trim().isEmpty() && !tf_prezzo.getText().trim().isEmpty() && !fornitore.trim().isEmpty()) {
                aggiungiVinoAlDatabase(event, vino, anno, prezzo, fornitore);
            } else {
                System.out.println("Inserisci tutti i dettagli del vino");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inserisci tutti i dettagli del vino");
                alert.show();
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

    private void aggiungiVinoAlDatabase(ActionEvent event, String vino, int anno, double prezzo, String fornitore) {

        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            psInsert = connection.prepareStatement("INSERT INTO vini (vino, anno, prezzo, fornitore) VALUES (?, ?, ?, ?)");
            psInsert.setString(1, vino);
            psInsert.setInt(2, anno);
            psInsert.setDouble(3, prezzo);
            psInsert.setString(4, fornitore);
            psInsert.executeUpdate();

            catalogo.add(new adminVinoView(vino, anno, prezzo, fornitore));
            id_table.setItems(catalogo);

            tf_vino.clear();
            tf_anno.clear();
            tf_prezzo.clear();
            tf_fornitore.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Si è verificato un errore durante l'aggiunta del vino.");
            alert.show();
        } finally {
            try {
                if (psInsert != null) psInsert.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
