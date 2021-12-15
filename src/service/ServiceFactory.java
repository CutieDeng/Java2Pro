package service;

/**
 * GUI 服务工厂 <br>
 * <p/>
 *
 * 通过实现该工厂的所有方法来为 GUI 的整体设计提供所有功能。<br>
 * <p/>
 *
 *
 */
public interface ServiceFactory {


    InitMenuBarService getInitMenuBarService();

    GenerateTipBoxService getGenerateTipBoxService();

    TabPaneServices getTabPaneServices();


}
