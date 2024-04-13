package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(new Scene(root, 600,400));
        primaryStage.show();

        Parent secondRoot = FXMLLoader.load(getClass().getResource("start.fxml"));
        Stage secondStage = new Stage();
        secondStage.setTitle("Second Window");
        secondStage.setScene(new Scene(secondRoot, 600, 400));
        secondStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}