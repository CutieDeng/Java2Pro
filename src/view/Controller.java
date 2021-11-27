package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Controller {

    @FXML
    private Button searchButton;

    @FXML
    private BorderPane root;

    @FXML
    private MenuButton menuButton;

    @FXML
    private AnchorPane menu;

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

