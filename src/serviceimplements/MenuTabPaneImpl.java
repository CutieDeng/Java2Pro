package serviceimplements;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import service.InitMenuService;
import service.TabPaneServices;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MenuTabPaneImpl implements InitMenuService, TabPaneServices {
    @Override
    public List<Menu> initMenus() {
        List<Menu> ans = new ArrayList<>();

        Menu menuFile = new Menu("文件");
        Menu menuData = new Menu("数据");
        Menu help = new Menu("帮助");

        //menuFile下的Items
        {
            MenuItem save = new MenuItem("保存");
            save.setOnAction(event -> {

            });

            menuFile.getItems().add(save);
        }

        //menuData下的Items
        {

        }

        //help下的Items
        {

        }

        return null;
    }

    @Override
    public TabPane initTabPane(Consumer<TabPane> finalOperation) {
        return null;
    }
}
