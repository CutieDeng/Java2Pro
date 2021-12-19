package tabsupply;

import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.HighDataServiceImpl;
import tool.Tool;
import util.Holder;
import view2.Tmp;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;

public class LocationTableTabSupplyImpl extends AbstractTabSupplyImpl {
    @Override
    protected Consumer<Void> getBeforeAction() {
        return beforeAction;
    }

    @Override
    protected Consumer<Void> getAfterAllAction() {
        return after;
    }

    private Consumer<Void> beforeAction;
    private Consumer<Void> after;

    @Override
    protected Tab tabGenerate() {
        return super.tabGenerate();
    }

    private static final Supplier<Integer> cntSupplier = new Supplier<Integer>() {
        int number = 1;
        @Override
        synchronized
        public Integer get() {
            return number++;
        }
    };

    private static TableView<Tmp> initTableView(List<String> colNames, List<Data> datas) {
        TableView<Tmp> view = new TableView<>();
        view.setTableMenuButtonVisible(true);

        final ToIntFunction<String> widthSupplier = str -> 80;

        final Function<String, TableColumn<Tmp, String>> colGenerator =
                s -> {
                    final TableColumn<Tmp, String> col = new TableColumn<>(s);
                    col.setCellValueFactory(new PropertyValueFactory<>(s));
                    col.setPrefWidth(widthSupplier.applyAsInt(s));
                    return col;
                };
        colNames.forEach(cn -> view.getColumns().add(colGenerator.apply(Tool.transferReverse(cn))));

        ObservableList<Tmp> dataList = FXCollections.observableArrayList();
        datas.stream().map(Tool::createRow).forEach(dataList::add);
        view.setItems(dataList);
        return view;
    }

    final DataService service = new HighDataServiceImpl();
    TextField searchField;

    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        Tab ans = super.supply(factory);
        ans.setText("Location Table " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("地区信息表"));

        // 设置该标签页内部的页面框架。
        BorderPane viewPane = new BorderPane();
        ans.setContent(viewPane);

        // 设置该标签页右边的相关选项框、搜索框。
        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(20));
        viewPane.setRight(searchPane);

        // 设置搜索、选择页的相关风格
        searchPane.setStyle("-fx-background-color: #CCFF99;");
        // 设置搜索、选择页的宽度
        searchPane.setPrefWidth(200);

        //设置table页面
        final Holder<Consumer<String>> searchBoxActionHolder = new Holder<>();

        // 创建搜索框等相关操作
        {
            // 搜索框
            VBox searchBox = new VBox();
            searchBox.setFocusTraversable(false);
            searchBox.setSpacing(10);
            searchBox.setAlignment(Pos.TOP_RIGHT);

            // 可键入的搜索框初始化
            searchField = new TextField();
            // 可以通过快捷键选中搜索文本框
            searchField.setFocusTraversable(true);

            // 新增搜索框的大小初始化描述
            searchField.setPrefSize(150, 20);

            // 搜索会发生的事情
            final Consumer<String> searchAction = (searchContent) -> searchBoxActionHolder.obj.accept(searchContent);

            // 增添提示信息
            searchField.setPromptText("请输入关键词");
            setTip(searchField, factory.getTipService(), () -> "输入关键词以搜索相关信息");


            // 创建搜索按钮
            Button searchConfirmButton = new Button("搜索");
            searchConfirmButton.setPrefSize(50, 20);

            // 新增搜索按钮的提示信息
            setTip(searchConfirmButton, factory.getTipService(), () -> "点击确认搜索");

            // 将搜索框和搜索按钮一同添加到搜索组件中。
            searchBox.getChildren().addAll(searchField, searchConfirmButton);

            // 搜索组件放入右侧快捷栏中。
            searchPane.getChildren().add(searchBox);

            // 集中处理搜索事件
            searchField.setOnAction(e -> searchAction.accept(searchField.getText()));
            searchConfirmButton.setOnAction(e -> searchAction.accept(searchField.getText()));

        }


        // 创建表的相关操作
        {
            @SuppressWarnings("unchecked")
            TableView<Tmp> tableRowTableView = initTableView(service.getColumnNames(), service.getDataList());
            viewPane.setCenter(tableRowTableView);
            // 稍稍设置一下相关的图形参数吧，让它好看点
            tableRowTableView.setPadding(new Insets(20));

            // 设置搜索会发生的事情
            @SuppressWarnings("unchecked") final List<Data> rows = service.getDataList();

            searchBoxActionHolder.obj = (searchText) -> {
                ObservableList<Tmp> searchList = FXCollections.observableArrayList();

                rows.stream().filter(d -> {
                    if (d.fetch("location").contains(searchText))
                        return true;
                    if (d.fetch("iso code").startsWith(searchText))
                        return true;
                    if (d.fetch("continent").contains(searchText))
                        return true;
                    return false;
                }).map(Tool::createRow).forEach(searchList::add);

                tableRowTableView.setItems(searchList);

                dataFilter = d -> {
                    if (d.fetch("location").contains(searchText))
                        return true;
                    if (d.fetch("iso code").startsWith(searchText))
                        return true;
                    if (d.fetch("continent").contains(searchText))
                        return true;
                    return false;
                };
            };
        }

        //文件导出
        beforeAction = v -> {
            factory.getMenuBarService()
                    .setExportOnAction(e -> exportAction());
        };
        after = v -> factory.getMenuBarService().setExportOnAction(null);


        return ans;

    }

    private Predicate<Data> dataFilter = d -> true;

    private void exportAction() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("save one file");

        //默认文件名
        fileChooser.setInitialFileName("LocationTable");

        //设置选择的文件的扩展名
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("txt", "*.txt"));

        //返回用户选中的文件的路径，注意，如果用户不选，则会返回null
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) return;

        try (PrintStream writer = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            service.toStringStream(dataFilter).forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            file.createNewFile();//如果保存的文件没有数据，则需要这句话。否则，不需要
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
