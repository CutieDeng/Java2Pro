package tool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TabArgumentMap implements Map<String, Object> {

    private Map<String, Object> hashmap = new HashMap<>();

    /**
     * 设置 tab 标签页的标题名。<br>
     * <p/>
     *
     * 更改器方法。<br>
     * <p/>
     *
     *
     *
     *
     *
     * @param title tab 标签页的标题
     * @return 被设置好标题名的映射表
     */
    public TabArgumentMap title(String title) {
        put(title);
        return this;
    }

    /**
     * 设置该标签页应展示的类型<br>
     * <p/>
     *
     * {@link DisplayType#GRAPH} 代表要展示图类型的信息。<br>
     * {@link DisplayType#TABLE} 代表要展示表格类型的信心。<br>
     * <p/>
     *
     *
     * @param type 展示的图表类型
     * @return 被设置好的映射表
     */
    public TabArgumentMap type(DisplayType type) {
        put(type);
        return this;
    }

    /**
     * 设置该控制页的宽度偏好值。<br>
     * <p/>
     *
     * 你可以通过一个宽度信息，描述其相关的内容。<br>
     * <p/>
     *
     * @param width 右侧搜索页的宽度信息
     * @return 被设置好的映射表
     */
    public TabArgumentMap searchPanePrefWidth(double width) {
        put(width);
        return this;
    }

    /**
     * 展示控制页的风格设置选项。<br>
     * <p/>
     *
     * 在标签页被设置好的时候，在其右侧将会有特殊的一个标签页负责管理其相关的信息。<br>
     * 你可以通过传入一个关于 CSS 风格的字符串来描述这个信息，以便于展示出合适的图表。<br>
     * <p/>
     *
     * @param style css 风格描述.
     * @return 被设置好的映射表
     */
    public TabArgumentMap searchPaneStyle(String style) {
        put(style);
        return this;
    }







    private void put(Object value) {
        hashmap.put(Thread.currentThread().getStackTrace()[2].getMethodName(), value);
    }

    @Override
    public int size() {
        return hashmap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashmap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return hashmap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return hashmap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return hashmap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return hashmap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return hashmap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        hashmap.putAll(m);
    }

    @Override
    public void clear() {
        hashmap.clear();
    }

    @Override
    public Set<String> keySet() {
        return hashmap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return hashmap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return hashmap.entrySet();
    }

    @Override
    public int hashCode() {
        return this.hashmap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        while (obj instanceof TabArgumentMap)
            obj = ((TabArgumentMap) obj).hashmap;
        return this.hashmap.equals(obj);
    }

    @Override
    public String toString() {
        return this.hashmap.toString();
    }
}
