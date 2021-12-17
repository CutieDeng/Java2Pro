package tabsupply;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import service.ServiceFactory;
import service.TabGenerateService;
import service.TipService;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 标准、抽象的页面提供实现。<br>
 * <p/>
 */
public abstract class AbstractTabSupplyImpl implements TabGenerateService {

    /**
     * 该抽象标签页创建服务提供创建的标签页。<br>
     * <p/>
     *
     * 请不要使用一个服务提供创建多个标签页。<br>
     * 这是一个未定义的行为！<br>
     * <p/>
     */
    protected Tab ans = tabGenerate();

    /**
     * 标记该标签页是否展示在了 GUI 页面上！<br>
     * <p/>
     *
     * 编码规范：<br>
     * true: 展示在 GUI 页面上<br>
     * false: 隐藏在 GUI 页面上<br>
     */
    protected boolean isShown = false;

    /**
     * 默认初始化 ans 的方法。<br>
     * <p/>
     *
     * 访问修饰符为 protected, 建议下层服务自行重写该方法以实现自己的页面方式。<br>
     * <p/>
     *
     * 由于该方法没有传入任何参数，建议将与页面无关的参数放在此处设置，以提升方法设计的内聚程度。<br>
     * <p/>
     *
     * 该方法只有在最初 new 的时候才会调用！<br>
     * <p/>
     *
     * @return 默认刚刚创建的 tab 页面
     */
    protected Tab tabGenerate() {
        return new Tab();
    }

    /**
     * 该方法用于获取一个行为。<br>
     * <p/>
     *
     * 用于当该页面出现在 GUI 上时，其出于方便提供服务而设计的方法实现。<br>
     * <p/>
     *
     * 请尽量缓存该值，以避免方法多次调用的风险。<br>
     * <p/>
     *
     * @return 该页面出现在 GUI 的显示页面时必要的预处理措施。
     */
    protected abstract Consumer<Void> getBeforeAction();

    /**
     * 与 {@link #getBeforeAction()} 对称，获取一个逆行为。<br>
     * <p/>
     *
     * 当页面从 GUI 显示中消失时，其负责处理之前预处理行为中产生的不必要的副作用。<br>
     * 建议该行为应该是 {@link #getBeforeAction()} 的逆行为。<br>
     * <p/>
     *
     * @return 返回页面消失时必要的处理措施。
     */
    protected abstract Consumer<Void> getAfterAllAction();

    @Override
    public Tab supply(ServiceFactory factory) {
        ans.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            synchronized
            public void handle(Event e) {
                isShown = !isShown;
                if (isShown) {
                    if (getBeforeAction() != null)
                        getBeforeAction().accept(null);
                } else {
                    if (getAfterAllAction() != null)
                        getAfterAllAction().accept(null);
                }
            }
        });
        return ans;
    }

    protected static void setTip(Node node, Supplier<String> tipInfoSupplier, TipService tipService) {
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tipService.setTipMessage(tipInfoSupplier.get()));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tipService.setTipMessage(""));
    }
}
