package serviceimplements;

import javafx.scene.control.MenuBar;
import service.InitMenuBarService;
import service.InitMenuService;

import java.util.function.Consumer;

/**
 * 基本的菜单页面实现<br>
 * <p/>
 *
 *
 */
public class SimpleInitMenuBarService implements InitMenuBarService {

    private MenuBar init() {
        MenuBar ans = new MenuBar();

        // 设置 MenuBar 的应用程序的等级
        ans.setUseSystemMenuBar(true);

        return ans;
    }

    @Override
    public MenuBar init(InitMenuService service, Consumer<MenuBar> finalOperation) {
        final MenuBar ans = init();
        ans.getMenus().addAll(service.initMenus());
        return ans;
    }
}
