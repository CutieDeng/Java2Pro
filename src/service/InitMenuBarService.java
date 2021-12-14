package service;

import javafx.scene.Scene;

import java.awt.*;
import java.util.function.Consumer;

public interface InitMenuBarService {

    /**
     * 基本的系统菜单初始化服务调用。<br>
     * <p/>
     *
     *
     * @return
     */
    MenuBar init(Consumer<MenuBar> finalOperation);

    default MenuBar init(Scene scene, Consumer<MenuBar> finalOperation) {
        return init(finalOperation);
    }

}
