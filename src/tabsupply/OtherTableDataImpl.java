package tabsupply;

import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.DataService;
import service.ServiceFactory;
import serviceimplements.ColSpecialDataServiceImpl;
import serviceimplements.HighDataServiceImpl;
import serviceimplements.NormalDataServiceImpl;
import tool.Controller;
import tool.Tool;
import util.Holder;
import view2.OtherTableRow;
import view2.Tmp;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.*;
import java.util.logging.Logger;

public class OtherTableDataImpl extends AbstractTabSupplyImpl {
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

    private static TableView<OtherTableRow> initTableView(List<String> colNames, List<Data> datas) {
        TableView<OtherTableRow> view = new TableView<>();
        view.setTableMenuButtonVisible(true);

        final ToIntFunction<String> widthSupplier = str -> 80;

        final Function<String, TableColumn<OtherTableRow, String>> colGenerator =
                s -> {
                    final TableColumn<OtherTableRow, String> col = new TableColumn<>(s);
                    col.setCellValueFactory(new PropertyValueFactory<>(s));
                    col.setPrefWidth(widthSupplier.applyAsInt(s));
                    return col;
                };
        colNames.forEach(cn -> view.getColumns().add(colGenerator.apply(Tool.transferReverse(cn))));

        ObservableList<OtherTableRow> dataList = FXCollections.observableArrayList();
        datas.stream().map(
                d -> {
                    try {
                        return new OtherTableRow(d);
                    } catch (RuntimeException e) {
//                        Logger.getGlobal().warning(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull).forEach(dataList::add);
        view.setItems(dataList);
        return view;
    }

    @Override
    public Tab supply(ServiceFactory factory) {
        // 初始化一个标签页
        Tab ans = super.supply(factory);
        ans.setText("Other Covid Confirm Table " + cntSupplier.get());

        // 设置该标签页的提示信息
        ans.setTooltip(new Tooltip("其他疫情信息表"));

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
            TextField searchField = new TextField();
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

            DatePicker datePicker = new DatePicker(LocalDate.now());
            datePicker.setEditable(false);
            searchBox.getChildren().add(datePicker);

            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                String date = newValue.format(DateTimeFormatter.ISO_DATE);
                searchField.setText(date);
            });
            datePicker.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> factory.getTipService().setTipMessage("通过点击日期快速搜索对应时间"));
            datePicker.addEventFilter(MouseEvent.MOUSE_EXITED, e -> factory.getTipService().setTipMessage(""));

            datePicker.setOnAction(e -> searchAction.accept(searchField.getText()));


        }


        // 创建表的相关操作
        {
            File dataFile = Paths.get("res", "file", "time_series_covid19_confirmed_global.csv").toFile();
            service = new ColSpecialDataServiceImpl(dataFile);
            TableView<OtherTableRow> table = initTableView(service.getColumnNames(), service.getDataList());
            viewPane.setCenter(table);
            // 稍稍设置一下相关的图形参数吧，让它好看点
            table.setPadding(new Insets(20));

            // 设置搜索会发生的事情
            @SuppressWarnings("unchecked") final List<Data> rows = service.getDataList();

            searchBoxActionHolder.obj = (searchText) -> {
                ObservableList<OtherTableRow> searchList = FXCollections.observableArrayList();

                rows.stream().filter(d -> {
                    if (d.fetch("location").contains(searchText))
                        return true;
                    if (d.fetch("date").equals(searchText))
                        return true;
                    return false;
                }).map(OtherTableRow::new).forEach(searchList::add);

                table.setItems(searchList);

                dataFilter = d -> {
                    if (d.fetch("location").contains(searchText))
                        return true;
                    if (d.fetch("date").equals(searchText))
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
    private DataService service;

    private void exportAction() {
        File file = StandTabSupplyTool.getChooseFile(new FileChooser(), "CovidTable",
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("txt", "*.txt"));

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
