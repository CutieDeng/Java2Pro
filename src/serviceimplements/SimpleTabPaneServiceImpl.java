package serviceimplements;

import javafx.scene.control.TabPane;
import service.TabPaneService;

public class SimpleTabPaneServiceImpl implements TabPaneService {

    private TabPane ans;

    /**
     * 调用该方法初始化一个标签页。<br>
     * <p/>
     *
     *
     */
    @Override
    public void init() {
        ans = new TabPane();
    }



    /**
     * 请在初始化主页之后调用该方法，以获得初始化出来的 tab pane. <br>
     * <p/>
     *
     * @return 初始化后的主页
     */
    @Override
    public TabPane getTabPane() {
        return ans;
    }
}
