package service;

import javafx.scene.control.TabPane;

import java.util.function.Consumer;

public interface TabPaneServices {

    TabPane initTabPane(Consumer<TabPane> finalOperation);

}
