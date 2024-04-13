package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Utilis {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;

        if (username != null){
            try {
                FXMLLoader loader = new FXMLLoader(Utilis.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Utilis.class.getResource(fxmlFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }



    public static void signUpUser(ActionEvent event, String username, String e_mail, String password, String nome, String cognome){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUser = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            psCheckUser = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUser.setString(1,username);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Utente già in uso");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Non puoi usare questo username");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, nome, cognome, e_mail) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, nome);
                psInsert.setString(4, cognome);
                psInsert.setString(5, e_mail);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Benvenuto", username);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void loginInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "mf66v0H1M2");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Utente non trovato");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Credenziali non corrette");
                alert.show();
            } else {
                while (resultSet.next()){
                    String rePassword =  resultSet.getString("password");
                    if (rePassword.equals(password)){
                        if (password.equals("a")) {
                            changeScene(event, "adminHome.fxml", "Accesso speciale", username);
                        }
                        else {
                            changeScene(event, "logged-in.fxml", "Benvenuto", username);
                        }
                    } else {
                        System.out.println("Password non corretta");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Credenziali non corrette");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
