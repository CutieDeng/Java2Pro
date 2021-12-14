package service;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;

public interface ServiceFactory {


    InitMenuBarService getInitMenuBarService();

    GenerateTipBoxService getGenerateTipBoxService();

    TabPaneServices getTabPaneServices();


}
