package view;

import data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import tool.Tool;

import java.nio.file.Paths;
import java.util.List;

public class Controller {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button searchButton;

    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Menu menu1;

    @FXML
    private Menu menu2;

    @FXML
    private Menu menu3;

    @FXML
    private AnchorPane mainView;

    @FXML
    private TextField searchBox;

    @FXML
    private TableView<TableRow> table;

    @FXML
    private TableColumn<TableRow, Integer> newCasesCol;

    @FXML
    private TableColumn<TableRow, Integer> totalCasesCol;

    @FXML
    private TableColumn<TableRow, Integer> totalDeathsCol;

    @FXML
    private TableColumn<TableRow, String> dateCol;

    @FXML
    private TableColumn<TableRow, String> isoCodeCol;

    private ObservableList<TableRow> tableData = FXCollections.observableArrayList();

    @FXML
    private MenuItem graph1;

    @FXML
    private MenuItem graph2;


    @FXML
    private void initialize(){

        isoCodeCol.setCellValueFactory(new PropertyValueFactory<>("isoCode"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalCasesCol.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
        newCasesCol.setCellValueFactory(new PropertyValueFactory<>("newCases"));
        totalDeathsCol.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));

        //绑定数据到TableView
        table.setItems(tableData);

        //todo 这里加数据
        //添加数据到tableData，TableView会自动更新
//        tableData.add(new TableRow("EFO", "2021-11-1", 2330, 234, 123));
        List<Data> data = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        data.stream().map(TableRow::new).forEach(tableData::add);
    }

    @FXML
    void clickGraph1(ActionEvent event) {
        /*AnchorPane graphAnchorPane = new AnchorPane();
        root.setCenter(graphAnchorPane);*/


    }

    @FXML
    void clickGraph2(ActionEvent event) {
    }

    @FXML
    void pressEnter(ActionEvent event) {
        /*KeyEvent keyEvent = (KeyEvent) event.getSource();
        if (keyEvent.getCode().name().equals("ENTER")) {
            //todo
        }*/
    }

    @FXML
    void clickGo(MouseEvent event) {
        String searchText = searchBox.getText();
        ObservableList<TableRow> searchData = FXCollections.observableArrayList();
        List<Data> data = Tool.readDataFile(Paths.get("res", "file", "owid-covid-data.csv").toFile());
        data.stream()
                .filter(d -> d.fetch("iso code").equals(searchText))
                .map(TableRow::new).forEach(searchData::add);

        table.setItems(searchData);

    }

}

