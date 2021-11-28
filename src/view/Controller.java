package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
    void pressEnter(KeyEvent event) {
        if (event.getCode().name().equals("ENTER")) {
            //todo
        }
    }

    @FXML
    void clickGo(MouseEvent event) {
        //todo
    }

}

