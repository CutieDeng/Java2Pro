package service;

import javafx.scene.control.TabPane;

/**
 * 主页面相关服务提供者<br>
 * <p/>
 *
 * 这是整个 GUI 设计的核心服务类。<br>
 * 所有其他组件都需要在此进行初始化、修饰和展现。<br>
 * <p/>
 *
 * 菜单栏需要从中获取有效的操作以提供正确的实现。<br>
 * 提示框需要向它提供其服务以实现更加优雅的绑定。<br>
 * 而它自身则是负责初始化整个主体页面，并向外界提供它所持有的相关页面。<br>
 * <p/>
 *
 * 同理，新增的服务内容将会通过 default 方法进行提供。<br>
 * <p/>
 *
 */
public interface TabPaneService {
    /**
     * 通过该方法初始化一个主页。<br>
     * <p/>
     */
    void init();

    /**
     * 获取初始化的主页。<br>
     *
     * @return 获取主页服务托管的主页
     */
    TabPane getTabPane();
}
