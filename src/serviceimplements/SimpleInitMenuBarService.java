package serviceimplements;

import javafx.scene.control.MenuBar;
import service.InitMenuBarService;

import java.util.function.Consumer;

public class SimpleInitMenuBarService implements InitMenuBarService {

    private MenuBar init() {
        MenuBar ans = new MenuBar();

        // 设置 MenuBar 的应用程序的等级
        ans.setUseSystemMenuBar(true);

        return ans;
    }

    @Override
    public MenuBar init(Consumer<MenuBar> finalOperation) {
        MenuBar ans = init();
        finalOperation.accept(ans);
        return ans;
    }
}
