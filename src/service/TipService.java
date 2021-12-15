package service;

import javafx.scene.Node;

import java.util.function.Consumer;

/**
 * 提示框相关服务<br>
 * <p/>
 *
 * 提示框服务提供：<br>
 * - 根据一些参数初始化一个提示框。<br>
 * - 获取初始化的提示框 javafx 对象。<br>
 * - 设置提示框所显示的文本/提示信息。<br>
 * <p/>
 *
 * 针对调用者的建议：<br>
 * 在获取该服务后，你可以直接使用以下代码初始化一个提示框。<br>
 * <blockquote>
 *     TipService service; <br>
 *     service.init(); <br>
 *     // 获取该提示框对象。 <br>
 *     Node node = service.getTip(); <br>
 * </blockquote>
 * <p/>
 *
 * 更多的调用建议和协议约定在方法的详细说明中给出。<br>
 * <p/>
 *
 *
 */
public interface TipService {
    /**
     * 提示框的初始化方法。<br>
     * <p/>
     *
     * 该方法只允许被调用一次，调用第二次的行为是未定义的。<br>
     * <p/>
     *
     * 建议在获取到服务的同时便直接调用该方法初始化一个提示框。<br>
     * <p/>
     *
     * 针对开发者的建议：<br>
     * 出于对旧版本的兼容性保护，接口升级的过程中将一律使用 default 重载方法的方式实现。<br>
     * 并且在正常情况下，新增加的参数将会支持默认实现操作。<br>
     * 一个方法不应当超过四个输入参数，一旦超过应当建议使用对象承载方法的变量以增强方法的灵活性和可读性。<br>
     * <p/>
     *
     * 该方法没有异常及特殊的返回值约定。<br>
     * <p/>
     *
     * 对于提示框的追加约定 v211215: <br>
     * 提示框被初始化后其文本内容显示应为空，或规范的默认值。<br>
     * <p/>
     */
    void init();

    /**
     * 方法 {@link #init()} 的拓展。<br>
     * <p/>
     *
     * 你可以传入字符串参数以设置该提示框的默认信息。<br>
     * 这可以在提示框未被「激活」的情况下提供一点有效的信息。<br>
     * <p/>
     *
     * 值得注意的是，该方法不一定被实现，这意味着你传入的参数可能被放弃。<br>
     * 如果它的实现不可或缺，请自行确认所使用的 TipService 提供了额外的实现功能。<br>
     * <p/>
     *
      * @param prompt 缺省的提示字符串信息
     */
    default void init(String prompt) {
        init();
    }

    /**
     * 提示框的获取方法。<br>
     * <p/>
     *
     * 在其相关服务初始化完成后，你可以通过该方法直接获取到提示框的 javafx 对象。<br>
     * <p/>
     *
     * 一种仅供参考的使用方式是：<br>
     * <blockquote>
     *     TipService tipService; <br>
     *     Pane pane; <br>
     *     Node tipObject = tipService.getTip(); <br>
     *     pane.getChildren().add(tipObject); <br>
     * </blockquote>
     * <p/>
     *
     * 当前协议暂时没有规定提示框服务的提供者必须使用的 javafx 组件。<br>
     * 请不要断言其返回的对象一定是 {@code VBox} 实例或 {@code HBox} 实例。<br>
     * 这是一种不推荐的做法。<br>
     * <p/>
     *
     * @return 提示框的 javafx 对象
     */
    Node getTip();

    /**
     * 该方法用于设置提示框的提示信息。<br>
     * <p/>
     *
     * 各窗口组件都应当自行监听自身的鼠标出入事件，并在其中关联该方法以实现对自身组件的巧妙提示。<br>
     * <p/>
     *
     * 该方法在实现应当是线程安全的。<br>
     * 因为该方法将会频繁在并发场景下调用！<br>
     * <p/>
     *
     * 协议没有规定方法调用失败的反馈。<br>
     * 由于这只是一个提示框行为，其错误行为并不会影响整个 GUI 的工作流程。<br>
     * 建议在方法出现错误的情况下将相关信息交付给日志管理中。<br>
     * 但这并不是一个强制的要求和协议。<br>
     * <p/>
     *
     */
    void setTipMessage(String information);
}
