package view2;

import data.Data;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import java.util.HashMap;
import java.util.List;
import java.util.function.*;


public class Launch extends Application {

    /**
     * 该方法用于根据参数表创建一个窗口的主页。<br>
     * <p/>
     *
     * 你可以通过一个映射表来描述你希望设置的参数信息。<br>
     * <p/>
     *
     * 例如，"prefWidth" 将会设置该主页的默认宽度，"prefHeight" 设置默认高度。<br>
     * "style" 设置该主页的 CSS 风格。<br>
     * <p/>
     *
     * @param argumentsMap 设置主页的参数表。
     * @return 主页（BorderPane）
     */
    private static BorderPane initMainPane(HashMap<String, Object> argumentsMap) {
        BorderPane root = new BorderPane();
        root.setPrefWidth((Double) argumentsMap.getOrDefault("prefWidth", 1e3));
        root.setPrefHeight((Double) argumentsMap.getOrDefault("prefHeight", 8e2));
        root.setStyle((String) argumentsMap.getOrDefault("style", "-fx-background-color: #FF9900;"));

        initTipBox(root, argumentsMap);



        return root;
    }

    private static void initTipBox(BorderPane root, HashMap<String, Object> argumentsMap) {
        // 创建消息提示框并设置它的位置。
        VBox tipBox = new VBox();
        root.setBottom(tipBox);

        // 设置消息提示框的样式。
        tipBox.setPadding(new Insets((Double) argumentsMap.getOrDefault("tipBoxInsets", 5.0)));
        tipBox.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID,
                new CornerRadii(5), new BorderWidths(2))));
        tipBox.setStyle((String) argumentsMap.getOrDefault("tipBoxStyle", "-fx-background-color:#CCFFFF"));

        // 提示框内容设置
        final Label message = new Label("");
        tipBox.getChildren().add(message);
        // 将对提示框的内容设置方法上传到全局信息，便于其他方法进行调用。
        tipNotify = message::setText;
    }

    private static IntSupplier cntSupplier = new IntSupplier() {
        private int s = 0;
        @Override
        public int getAsInt() {
            return s++;
        }
    };

    private static Consumer<String> tipNotify;

    /**
     * 该对象封装了对标签页的方法提供。<br>
     * <p/>
     *
     * 通过映射表可以设置特定的参数以便于获取该标签页的性质进行修改。<br>
     * <p/>
     *
     *
     */
    private static final Function<HashMap<String, String>, Tab> tabSupplier = (map) -> {
        Tab returnTab = new Tab();
        if (!map.containsKey("title")) {
            map.put("title", map.getOrDefault("type", "table") + cntSupplier.getAsInt());
        }

        // 设置该标签页内部的页面框架。
        BorderPane graph = new BorderPane();
        returnTab.setContent(graph);

        // 设置该标签页右边的相关选项框、搜索框。
        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(20));
        graph.setRight(searchPane);

        // 设置搜索、选择页的相关风格
        searchPane.setStyle(map.getOrDefault("searchPaneStyle", "-fx-background-color: #CCFF99;"));
        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth(Double.parseDouble(map.getOrDefault("searchPanePrefWidth", "200")));

        // 创建搜索框等相关操作
        {
            // 搜索框
            VBox searchBox = new VBox();
            searchBox.setFocusTraversable(false);

            // 可键入的搜索框初始化
            TextField searchField = new TextField();
            // 可以通过快捷键选中搜索文本框
            searchField.setFocusTraversable(true);

            // 新增搜索框的大小初始化描述
            searchField.setPrefSize(Double.parseDouble(map.getOrDefault("searchPrefWidth", "150")),
                    Double.parseDouble(map.getOrDefault("searchPrefHeight", "20")));

            // 增添提示信息
            searchField.setPromptText(map.getOrDefault("searchPromptText", "输入关键词以搜索相关信息"));
            searchField.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                tipNotify.accept(map.getOrDefault("searchTip", "输入关键词以搜索相关信息"));
            });

            // 创建搜索按钮
            Button searchConfirmButton = new Button(map.getOrDefault("searchButton", "搜索"));
            searchConfirmButton.setPrefSize(Double.parseDouble(map.getOrDefault("searchButtonPrefWidth", "35")),
                    Double.parseDouble(map.getOrDefault("searchPrefHeight", "20")));

            // 新增搜索按钮的提示信息
            searchConfirmButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                tipNotify.accept(map.getOrDefault("searchButtonTip", "点击确认搜索"));
            });

            // 将搜索框和搜索按钮一同添加到搜索组件中。
            searchBox.getChildren().addAll(searchField, searchConfirmButton);

            // 搜索组件放入右侧快捷栏中。
            searchPane.getChildren().add(searchBox);
        }


        // 创建图表的相关操作
        {
            TableView<TableRow> tableRowTableView = initTableView();
            graph.setCenter(tableRowTableView);
            // 稍稍设置一下相关的图形参数吧，让它好看点
            tableRowTableView.setPadding(new Insets(20));
        }

        return returnTab;
    };

    private static TabPane initTabPane(Tab... tabs) {
        TabPane tabPane = new TabPane(tabs);
        if (tabs.length == 0) {
            tabPane.getTabs().add(tabSupplier.apply(new HashMap<>()));
        }
        return tabPane;
    }

    @Deprecated
    private static TableView<TableRow> initTableView() {
        TableView<TableRow> table = new TableView<>();
        table.setPrefSize(560, 500);
        table.setTableMenuButtonVisible(true);

        // 根据列名设置列的宽度
        final ToIntFunction<String> widthSupplier = str -> 80;

        // 根据列名设置该列的相关信息
        final Function<String, TableColumn<TableRow, String>> normalColGenerator =
                s -> {
                    final TableColumn<TableRow, String> col = new TableColumn<>(s);
                    col.setCellValueFactory(new PropertyValueFactory<>(s));
                    col.setPrefWidth(widthSupplier.applyAsInt(s));
                    return col;
                };

        // Java 风格的变量名变化。
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

        // 获取列名及各行信息
        Holder<List<String>> holder = new Holder<>();
        List<Data> allData = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile(), holder);

        // 创建各列信息
        holder.obj.forEach(s -> table.getColumns().add(strMap.andThen(normalColGenerator).apply(s)));

        // 创建数据集对象
        ObservableList<TableRow> tableData = FXCollections.observableArrayList();
        table.setItems(tableData);

        // 将各值放入表格中
        allData.stream().map(TableRow::new).forEach(tableData::add);

        return table;
    }





    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = initMainPane(new HashMap<>());

        TabPane tabPane1 = initTabPane();
        root.setCenter(tabPane1);

        //根据search的内容，重载tableView里的数据
//        final Consumer<Event> doSearch = event -> {
//            String searchText = searchBox.getText();
//            ObservableList<TableRow> searchData = FXCollections.observableArrayList();
//            List<Data> data = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
//            data.stream()
//                    .filter(d -> d.fetch("iso code").equals(searchText))
//                    .map(TableRow::new).forEach(searchData::add);
//            data.stream()
//                    .filter(d -> d.fetch("date").equals(searchText))
//                    .map(TableRow::new).forEach(searchData::add);
//
//            table.setItems(searchData);
//        };

//        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, doSearch::accept);
//        searchBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                doSearch.accept(event);
//            }
//        });


        MenuBar menuBar = new MenuBar();

        // 设置该菜单栏为该应用程序的系统级菜单栏
        menuBar.setUseSystemMenuBar(true);

        menuBar.setPrefHeight(20);
        menuBar.setPrefWidth(1000);
        root.setTop(menuBar);

        Menu menuFile = new Menu("File");
        Menu menuData = new Menu("Data");
        menuBar.getMenus().addAll(menuFile, menuData);

        MenuItem tableMenu = new MenuItem("Table");
        menuData.getItems().add(tableMenu);
        tableMenu.setOnAction(event -> {
            Tab newTab = new Tab("New Table");
            //todo 这样子加似乎不行，tablePane再次加在新的tab上，不同tab之间会干扰
//            newTab.setContent(tablePane);
//
//            tabPane.getTabs().add(newTab);
        });


        MenuItem graphMenu = new MenuItem("Graph");
        menuData.getItems().add(graphMenu);
        graphMenu.setOnAction(event -> {
//            Tab newTab = new Tab("New Graph");
//            tabPane.getTabs().add(newTab);
        });






        //全屏/窗口模式切换
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.F11)
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            else
                System.out.println(e);
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
