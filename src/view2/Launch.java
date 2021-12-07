package view2;

import data.Data;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tool.Tool;
import view.TableRow;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;


public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #FF9900;");


        AnchorPane searchPane = new AnchorPane();
        searchPane.setPrefWidth(200);
        searchPane.setStyle("-fx-background-color: #CCFF99;");
        root.setRight(searchPane);

        AnchorPane mainView = new AnchorPane();
        mainView.setPrefWidth(800);
        root.setCenter(mainView);

        TableView<TableRow> table = new TableView<>();
        table.setLayoutX(148);
        table.setLayoutY(33);
        table.setPrefHeight(500);
        table.setPrefWidth(560);
        table.setTableMenuButtonVisible(true);
        mainView.getChildren().add(table);

        TableColumn<TableRow, String> isoCodeCol = new TableColumn<>("ISO");
        isoCodeCol.setCellValueFactory(new PropertyValueFactory<>("isoCode"));
        isoCodeCol.setPrefWidth(60);
        TableColumn<TableRow, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(140);
        TableColumn<TableRow, Integer> totalCasesCol = new TableColumn<>("Total Cases");
        totalCasesCol.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
        totalCasesCol.setPrefWidth(120);
        TableColumn<TableRow, Integer> newCasesCol = new TableColumn<>("New Cases");
        newCasesCol.setCellValueFactory(new PropertyValueFactory<>("newCases"));
        newCasesCol.setPrefWidth(110);
        TableColumn<TableRow, Integer> totalDeathsCol = new TableColumn<>("Total Deaths");
        totalDeathsCol.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));
        totalDeathsCol.setPrefWidth(130);
        table.getColumns().addAll(isoCodeCol, dateCol, totalCasesCol, newCasesCol, totalDeathsCol);

        ObservableList<TableRow> tableData = FXCollections.observableArrayList();
        table.setItems(tableData);
        List<Data> allData = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        allData.stream().map(TableRow::new).forEach(tableData::add);


        TextField searchBox = new TextField();
        searchBox.setFocusTraversable(false);
        searchBox.setLayoutX(5);
        searchBox.setLayoutY(10);
        searchBox.setPrefWidth(150);
        searchBox.setPrefHeight(20);
        searchBox.setPromptText("search...");
        searchPane.getChildren().add(searchBox);

        Button searchButton = new Button("Go");
        searchButton.setLayoutX(160);
        searchButton.setLayoutY(15);
        searchButton.setPrefWidth(35);
        searchButton.setPrefHeight(20);
        searchPane.getChildren().add(searchButton);


        //根据search的内容，重载tableView里的数据
        final Consumer<Event> doSearch = event -> {
            String searchText = searchBox.getText();
            ObservableList<TableRow> searchData = FXCollections.observableArrayList();
            List<Data> data = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
            data.stream()
                    .filter(d -> d.fetch("iso code").equals(searchText))
                    .map(TableRow::new).forEach(searchData::add);
            data.stream()
                    .filter(d -> d.fetch("date").equals(searchText))
                    .map(TableRow::new).forEach(searchData::add);

            table.setItems(searchData);
        };

        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, doSearch::accept);
        searchBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                doSearch.accept(event);
            }
        });


        MenuBar menuBar = new MenuBar();
        menuBar.setPrefHeight(20);
        menuBar.setPrefWidth(1000);
        root.setTop(menuBar);

        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().addAll(menuFile, menuEdit);

        MenuItem item1 = new MenuItem("12");
        menuFile.getItems().add(item1);




        //全屏/窗口模式切换
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED,e -> {
            if (e.getCode() == KeyCode.F11)
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
        });
        primaryStage.setFullScreenExitHint("按 F11 切换全屏/窗口模式");
        primaryStage.setFullScreen(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(620);
        primaryStage.setWidth(1050);
        primaryStage.setTitle("COVID-19 TRACING");
        primaryStage.getIcons().add(new Image("file:"+System.getProperty("user.dir")+"/res/picture/icon1.png"));
        primaryStage.show();
    }
}
