package view2;

import data.Data;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tool.Tool;
import util.Holder;
import view.TableRow;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;


public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #FF9900;");

        TabPane tabPane = new TabPane();
        root.setCenter(tabPane);

        Tab origin = new Tab("Table");
        tabPane.getTabs().add(origin);

        BorderPane tablePane = new BorderPane();
        origin.setContent(tablePane);

        AnchorPane searchPane = new AnchorPane();
        searchPane.setPrefWidth(200);
        searchPane.setStyle("-fx-background-color: #CCFF99;");
        tablePane.setRight(searchPane);

        AnchorPane tableView = new AnchorPane();
        tableView.setPrefWidth(800);
        tablePane.setCenter(tableView);

        VBox tipView = new VBox();
        tipView.setPadding(new Insets(5));
        tipView.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID,
                new CornerRadii(0), new BorderWidths(2))));
        tipView.setStyle("-fx-background-color:#CCFFFF");
        root.setBottom(tipView);

        Label message = new Label("empty");
        tipView.getChildren().add(message);


        TableView<TableRow> table = new TableView<>();
        table.setLayoutX(148);
        table.setLayoutY(33);
        table.setPrefHeight(500);
        table.setPrefWidth(560);
        table.setTableMenuButtonVisible(true);
        tableView.getChildren().add(table);

        //根据列名初始化列
        final ToIntFunction<String> widthSupplier = str -> 80;
        Function<String, TableColumn<TableRow, String>> normalColGenerator =
                s -> {
            final TableColumn<TableRow, String> col = new TableColumn<>(s);
            col.setCellValueFactory(new PropertyValueFactory<>(s));
            col.setPrefWidth(widthSupplier.applyAsInt(s));
            return col;
        };

        final Function<String, String> strMap = s -> {
            char[] cs = s.toCharArray();
            StringBuilder builder = new StringBuilder();
            boolean flag = false;
            for (char c : cs) {
                if (c == '_') {
                    flag = true;
                    continue;
                }
                if (!flag) {
                    builder.append(c);
                } else {
                    if (c >= 'a' && c <= 'z')
                        builder.append((char)(c + 'A' - 'a'));
                    else
                        builder.append(c);
                }
                flag = false;
            }
            return builder.toString();
        };

        Holder<List<String>> holder = new Holder<>();
        Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile(), holder);
        for (String s : holder.obj) {
            System.out.println(s);
        }
        holder.obj.forEach(s -> table.getColumns().add(strMap.andThen(normalColGenerator).apply(s)));


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
        Menu menuData = new Menu("Data");
        Menu addPage = new Menu("+");
        menuBar.getMenus().addAll(menuFile, menuData, addPage);

        addPage.setOnAction(event -> {
            System.out.println("test");
            Tab newPage = new Tab("New Page");
            tabPane.getTabs().add(newPage);
        });

        MenuItem tableMenu = new MenuItem("Table");
        menuData.getItems().add(tableMenu);


        MenuItem graphMenu = new MenuItem("Graph");
        menuData.getItems().add(graphMenu);



        graphMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("hao");
            }
        });




        //全屏/窗口模式切换
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.F11)
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
        });
        primaryStage.setFullScreenExitHint("按 F11 切换全屏/窗口模式");
        primaryStage.setFullScreen(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(630);
        primaryStage.setWidth(1050);
        primaryStage.setTitle("COVID-19 TRACING");
        primaryStage.getIcons().add(new Image("file:"+System.getProperty("user.dir")+"/res/picture/icon1.png"));
        primaryStage.show();
    }
}
