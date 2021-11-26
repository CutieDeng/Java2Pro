package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Launch extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#FF9900");

        AnchorPane mainView = new AnchorPane();
        AnchorPane menu = new AnchorPane();
        menu.setStyle("-fx-background-color:#CCFF99");
        menu.setPrefWidth(200);
        menu.setPrefHeight(620);
        root.setCenter(mainView);
        root.setRight(menu);

        TextField searchBox = new TextField();
        searchBox.setPrefWidth(150);
        searchBox.setPrefHeight(20);
        searchBox.setLayoutX(5.0);
        searchBox.setLayoutY(15.0);
        searchBox.setPromptText("search...");
        menu.getChildren().add(searchBox);

        Button searchButton = new Button("Go!");
        searchButton.setFont(Font.font("Trebuchet MS", 12));
        searchButton.setLayoutX(5.0 + searchBox.getPrefWidth() + searchBox.getLayoutX());
        searchButton.setLayoutY(searchBox.getLayoutY());
        menu.getChildren().add(searchButton);


        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(620);
        primaryStage.setWidth(1000);
        primaryStage.setTitle("COVID-19 TRACING");
        primaryStage.getIcons().add(new Image("/picture/icon1.png"));
        primaryStage.show();
    }
}
