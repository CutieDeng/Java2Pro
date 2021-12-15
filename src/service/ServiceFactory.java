package service;

/**
 * GUI 服务工厂 <br>
 * <p/>
 *
 * 通过实现该工厂的所有方法来为 GUI 的整体设计提供所有功能。<br>
 * <p/>
 *
 * 该功能实现分为三大板块：<br>
 * - 菜单栏板块<br>
 * - 内容版块<br>
 * - 提示框板块<br>
 * <p/>
 *
 * 请注意任何一个工厂都应只提供单例的服务使用，以避免多实例造成的混乱。<br>
 * <p/>
 */
public interface ServiceFactory {
    MenuBarService getMenuBarService();
    TipService getTipService();
    TabPaneService getTabPaneService();

}
