package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class gestioneClientiController implements Initializable {

    @FXML
    private Button bt_indietro;

    @FXML
    private TableColumn<userView, String> col_cognome;

    @FXML
    private TableColumn<userView, String> col_email;

    @FXML
    private TableColumn<userView, String> col_nome;

    @FXML
    private TableColumn<userView, String> col_username;

    @FXML
    private TableView<userView> id_table;

    @FXML
    private TextField tx_cerca;

    private ObservableList<userView> clienti = FXCollections.observableArrayList();
    private ObservableList<userView> clientiFiltrati = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));


        // Popola la TableView con i dati dal database
        populateTableView();

        tx_cerca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Se il campo di ricerca è vuoto, mostra tutti i clienti
                id_table.setItems(clienti);
            } else {
                // Altrimenti, esegui una ricerca e aggiorna la TableView
                cercaCliente(newValue.trim());
            }
        });

        bt_indietro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utilis.changeScene(event, "adminHome.fxml", "Home", null);
            }
        });
    }

    private void populateTableView() {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            psSelect = connection.prepareStatement("SELECT * FROM users");
            resultSet = psSelect.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String email = resultSet.getString("e_mail");


                userView user = new userView(username, nome, cognome, email);
                clienti.add(user);
            }

            id_table.setItems(clienti);

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
    }

    private void cercaCliente(String cognome) {
        clientiFiltrati.clear();
        for (userView cliente : clienti) {
            if (cliente.getCognome().toLowerCase().contains(cognome.toLowerCase())) {
                clientiFiltrati.add(cliente);
            }
        }
        id_table.setItems(clientiFiltrati);
    }
}
