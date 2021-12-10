package view2;

import data.Data;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import tool.DisplayType;
import tool.TabArgumentMap;
import tool.Tool;
import util.Holder;
import view.TableRow;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;


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

    /**
     * 初始化提示框，并将提示框的控制命令放入
     * @param root
     * @param argumentsMap
     */
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
        // 补充：新增了多线程的并发支持。
        tipNotify = new Consumer<String>() {
            @Override synchronized
            public void accept(String s) {
                message.setText(s);
            }
        };

        // 设置提示框消失动画。
        FadeTransition goDeath = new FadeTransition(Duration.seconds(2), tipBox);
        goDeath.setAutoReverse(false);
        goDeath.setToValue(0.);
        FadeTransition goLive = new FadeTransition(Duration.seconds(2), tipBox);
        goLive.setAutoReverse(false);
        goLive.setToValue(1.);
        Holder<Boolean> isDeath = new Holder<>(); isDeath.obj = false;

        MenuItem displayTip = new MenuItem("显示/隐藏提示框");
        //noinspection Convert2Lambda
        displayTip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
//            synchronized
            public void handle(ActionEvent event) {
                isDeath.obj = !isDeath.obj;
                BorderPane root = (BorderPane) storeMap.get("root");
                if (!isDeath.obj) {
                    root.setBottom(tipBox);
                    goLive.play();
//                    System.out.println("提示框出现");
                }
                else {
                    goDeath.play();
                    final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.), e -> root.bottomProperty().set(null)));
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            }
        });
        displayTip.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN));
        storeMap.put("displayOption", displayTip);
    }

    private static final Map<String, Object> storeMap = new HashMap<>();

    private static final IntSupplier cntSupplier = new IntSupplier() {
        private int s = 0;
        @Override
        synchronized
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
    private static final Function<Map<String, Object>, Tab> tabSupplier = (map) -> {
        Tab returnTab = new Tab();

        if (!map.containsKey("title")) {
            map.put("title", map.getOrDefault("type", "New Page").toString() + " " + cntSupplier.getAsInt());
        }
        returnTab.setText((String) map.get("title"));

        // 设置该标签页内部的页面框架。
        BorderPane viewPane = new BorderPane();
        returnTab.setContent(viewPane);

        // 设置该标签页右边的相关选项框、搜索框。
        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(20));
        viewPane.setRight(searchPane);

        // 设置搜索、选择页的相关风格
        searchPane.setStyle((String )map.getOrDefault("searchPaneStyle", "-fx-background-color: #CCFF99;"));
        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth((double)map.getOrDefault("searchPanePrefWidth", 200.));

        //设置table页面
        if (map.getOrDefault("type", DisplayType.TABLE) == DisplayType.TABLE) {

            // 创建搜索框等相关操作
            {
                // 搜索框
                VBox searchBox = new VBox();
                searchBox.setFocusTraversable(false);
                searchBox.setSpacing(10);
                searchBox.setAlignment(Pos.TOP_RIGHT);

                // 可键入的搜索框初始化
                TextField searchField = new TextField();
                // 可以通过快捷键选中搜索文本框
                searchField.setFocusTraversable(true);

                // 新增搜索框的大小初始化描述
                searchField.setPrefSize((double )map.getOrDefault("searchPrefWidth", 150.),
                        (double )map.getOrDefault("searchPrefHeight", 20.));

                // 增添提示信息
                searchField.setPromptText(map.getOrDefault("searchPromptText", "请输入关键词").toString());
                searchField.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tipNotify.accept(map.getOrDefault("searchTip", "输入关键词以搜索相关信息").toString()));
                searchField.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tipNotify.accept(""));

                // 创建搜索按钮
                Button searchConfirmButton = new Button(map.getOrDefault("searchButton", "搜索").toString());
                searchConfirmButton.setPrefSize((double )map.getOrDefault("searchButtonPrefWidth", 50.),
                        (double )map.getOrDefault("searchPrefHeight", 20.));

                // 新增搜索按钮的提示信息
                searchConfirmButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tipNotify.accept(map.getOrDefault("searchButtonTip", "点击确认搜索").toString()));
                searchConfirmButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tipNotify.accept(""));

                // 将搜索框和搜索按钮一同添加到搜索组件中。
                searchBox.getChildren().addAll(searchField, searchConfirmButton);

                // 搜索组件放入右侧快捷栏中。
                searchPane.getChildren().add(searchBox);
            }


            // 创建图表的相关操作
            {
                TableView<TableRow> tableRowTableView = initTableView();
                viewPane.setCenter(tableRowTableView);
                // 稍稍设置一下相关的图形参数吧，让它好看点
                tableRowTableView.setPadding(new Insets(20));
            }

        }
        //设置graph页面
        else if (map.get("type") == DisplayType.GRAPH){

            // todo: 创建一个合适的图像以显示相关信息。
            // 我们会在创建图像的选单中就确定创建什么样的表格信息，不必担心不知道是什么样的表单。

//            VBox choiceBox = new VBox();
//            Label choice1 = new Label("Area Chart");
//            Label choice2 = new Label("Bar Chart");
//            Label choice3 = new Label("Pie Chart");
        }

        return returnTab;
    };

    private static TabPane initTabPane(Tab... tabs) {
        TabPane tabPane = new TabPane(tabs);
        if (tabs.length == 0) {

            if ("旧版使用的代码！" == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "table");
                map.put("title", "Wed, Dec");
                tabPane.getTabs().addAll(tabSupplier.apply(map)
                );
            } else {
                tabPane.getTabs().addAll(tabSupplier.apply(new TabArgumentMap().title("明天你好").type(DisplayType.TABLE)));
            }

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
        storeMap.put("root", root);
        Scene scene = new Scene(root);

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

//        menuBar.setPrefHeight(20);
//        menuBar.setPrefWidth(1000);
        root.setTop(menuBar);

        Menu menuFile = new Menu("文件");
        Menu menuData = new Menu("数据");
        Menu help = new Menu("帮助");
        menuBar.getMenus().addAll(menuFile, menuData, help);

        {
            // 设置显示、隐藏提示框的操作，并增加新的快捷键
            MenuItem displayOption = (MenuItem) storeMap.get("displayOption");
            help.getItems().add(displayOption);

            // 快捷键为 Alt + T, means optional to see tip box.
            KeyCombination combination = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN);
            scene.getAccelerators().put(combination, displayOption::fire);
        }

        MenuItem tableMenu = new MenuItem("表");
        menuData.getItems().add(tableMenu);
        tableMenu.setOnAction(event -> {
            DialogPane setNamePane = new DialogPane();
            setNamePane.setHeaderText("Set Table Name");
            TextField name = new TextField();
            setNamePane.setContent(name);
            setNamePane.getButtonTypes().add(ButtonType.CANCEL);
            setNamePane.getButtonTypes().add(ButtonType.APPLY);

            Scene stageScene = new Scene(setNamePane);
            Stage stage = new Stage();
            stage.setWidth(300);
            stage.setTitle("Set Name");
            stage.setScene(stageScene);
            stage.show();

            final Consumer<Object> tableAction = (o) -> {
                Map<String, Object> map;
                if (!name.getText().equals("")) {
                    map = new TabArgumentMap().title(name.getText()).type(DisplayType.TABLE);
                }
                else map = new TabArgumentMap().type(DisplayType.TABLE);

                Tab apply = tabSupplier.apply(map);
                tabPane1.getTabs().add(apply);
                tabPane1.getSelectionModel().select(apply);
                stage.close();
            };


            //设置Enter快捷键
            stageScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER)
                    tableAction.accept(new Object());
            });

            //给apply按钮设置action
            Button applyButton = (Button) setNamePane.lookupButton(ButtonType.APPLY);
            applyButton.setOnAction(e -> tableAction.accept(new Object()));

            Button cancelButton = (Button) setNamePane.lookupButton(ButtonType.CANCEL);
            cancelButton.setOnAction(e -> stage.close());
        });

        MenuItem graphMenu = new MenuItem("Graph");
        menuData.getItems().add(graphMenu);

        graphMenu.setOnAction(event -> {
            DialogPane setNamePane = new DialogPane();
            setNamePane.setHeaderText("Set Graph Name");
            TextField name = new TextField();
            setNamePane.setContent(name);
            setNamePane.getButtonTypes().add(ButtonType.CANCEL);
            setNamePane.getButtonTypes().add(ButtonType.APPLY);

            Scene stageScene = new Scene(setNamePane);
            Stage stage = new Stage();
            stage.setWidth(300);
            stage.setTitle("Set Name");
            stage.setScene(stageScene);
            stage.show();

            final Consumer<Object> graphAction = (o) -> {
                Map<String, Object> map;
                if (!name.getText().equals("")) {
                    map = new TabArgumentMap().type(DisplayType.GRAPH).title(name.getText());
                }
                else map = new TabArgumentMap().type(DisplayType.GRAPH);

                Tab apply = tabSupplier.apply(map);
                tabPane1.getTabs().add(apply);
                tabPane1.getSelectionModel().select(apply);
                stage.close();
            };

            //设置Enter快捷键
            stageScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER)
                    graphAction.accept(new Object());
            });

            //给apply按钮设置action
            Button applyButton = (Button) setNamePane.lookupButton(ButtonType.APPLY);
            applyButton.setOnAction(e -> graphAction.accept(new Object()));

            Button cancelButton = (Button) setNamePane.lookupButton(ButtonType.CANCEL);
            cancelButton.setOnAction(e -> stage.close());
        });

        //全屏/窗口模式切换
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.F11)
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
        });

        primaryStage.setFullScreenExitHint("按 F11 切换全屏/窗口模式");
        primaryStage.setFullScreen(true);

        primaryStage.setScene(scene);
        primaryStage.setHeight(630);
        primaryStage.setWidth(1050);
        primaryStage.setTitle("COVID-19 TRACING");
        primaryStage.getIcons().add(new Image("file:"+System.getProperty("user.dir")+"/res/picture/icon1.png"));

        primaryStage.show();
    }
}
