package service;

import javafx.scene.control.Tab;

/**
 * 创建数据可视化页面服务提供<br>
 * <p/>
 *
 * 每个数据页实例负责提供一个服务！<br>
 * <p/>
 *
 * 数据页作为通信的实体，我们传入我们的工厂类实体，以便能让该数据页获取并调整整个页面的行为！<br>
 * <p/>
 *
 */
public interface TabGenerateService {
    Tab supply(ServiceFactory factory);
}
