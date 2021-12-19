package service;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;

import java.util.function.Consumer;

/**
 * 菜单栏服务提供<br>
 * <p/>
 *
 * 其实呢，菜单栏好像也没什么服务好提供的。<br>
 * 虽然说我们预期让它提供很多功能 emmmm, 不过这些功能显然是通过 tabPane 提供的功能来实现的！ <br>
 * 那不如先把菜单栏设置好？<br>
 * 然后提供若干接口让外部将菜单栏与其功能直接绑定。<br>
 * <p/>
 *
 * 对调用者（其实也算是开发者，二级开发者）的使用规范：<br>
 * 调用菜单栏的激活事件以实现该行为。<br>
 * 你可以通过多次调用该菜单栏的事件绑定以更换相应的事件。<br>
 * 请尽早将相关的项目与对应的内容绑定。<br>
 * 特别地，调用者可以通过传入一个 null 来取消之前绑定的行为。<br>
 * 真棒，不是么？<br>
 * <p/>
 *
 * 对开发者（开发当前的实现类）的约定：<br>
 * 新增的相关服务绑定将被设成 default, 保证对之前的实现的兼容性。<br>
 * 服务绑定将会提供一个布尔返回值，表示该操作是否被正确执行了。<br>
 * 默认实现将统一直接返回 false. <br>
 * 请注意，菜单栏可以自由裁量自己的菜单形式、布局，这是属于开发者（你）的自由——只要它和别人实现的菜单栏一样都具有相同的功能。<br>
 * <p/>
 */

public interface MenuBarService {

    /**
     * 基本的系统菜单初始化服务调用。<br>
     * <p/>
     *
     * 该方法不应当被调用第二次。<br>
     * 二次调用会是 undefined behavior. <br>
     * <p/>
     */
    void init();

    /**
     * 在完成了初始化之后，你可以通过该方法直接获得创建出来的菜单实例。<br>
     * <p/>
     *
     * 这是遵循单例模式的一个菜单栏实例，多次调用该方法，会获得同一个实例。<br>
     * <p/>
     *
     * @return 菜单相关服务初始化时创建的菜单栏。
     */
    MenuBar getMenuBar();

    /**
     * 设置「导出」键的方法绑定
     * @param consumer 导出键单击后的操作
     * @return true 如果绑定操作成功
     */
    default boolean setExportOnAction(Consumer<Void> consumer) {
        return false;
    }

    /**
     * 设置关闭键的行为绑定<br>
     * 单击关闭键将会关闭当前正在显示的标签页。<br>
     * <p/>
     *
     * @param consumer 单击「关闭」键后的操作
     * @return true 如果绑定成功
     */
    default boolean setCloseOnAction(Consumer<Void> consumer) {
        return false;
    }

    /**
     * 设置显示当前区域信息的表格行为绑定。<br>
     * 该方法将会创建一个新的标签页以显示当前区域信息。<br>
     * <p/>
     *
     * @param consumer 创建一个新的标签页以显示区域信息表行为定义
     * @return true 如果绑定成功
     */
    default boolean setShowLocationTableOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowLocationBarOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowLocationPieOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowCovidTableOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowCovidBarOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowCovidPieOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowCovidLineOnAction(Consumer<Void> consumer) {
        return false;
    }

    default boolean setShowOtherDataAction(Consumer<Void> consumer) {
        return false;
    }

}
