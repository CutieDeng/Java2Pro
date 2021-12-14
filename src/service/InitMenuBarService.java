package service;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.util.List;
import java.util.function.Consumer;

public interface InitMenuBarService {

    /**
     * 基本的系统菜单初始化服务调用。<br>
     * <p/>
     *
     *
     * @return
     */
    MenuBar init(InitMenuService service, Consumer<MenuBar> finalOperation);

}
