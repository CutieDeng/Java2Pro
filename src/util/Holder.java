package util;

public class Holder<T> {
    public T obj = null;

    @Override
    public int hashCode() {
        return obj == null ? 0 : obj.hashCode();
    }

    /**
     * 重写了 Holder 包装器的等于方法。<br>
     * <p/>
     *
     * 该方法将会智能对 Holder 进行自动的包装拆解，对于多次包装的对象和单次包装的对象，只要最终持有的实际对象相同，则调用该方法将会获得一致的答案。<br>
     * 实现过程通过尾递归描述，Java 中建议使用迭代优化。<br>
     *
     * @param obj 待比较的对象
     * @return true 如果两个对象相同
     */
    @Override
    public boolean equals(Object obj) {
        assert (this != this.obj);
        if (obj instanceof Holder) {
            return equals(((Holder<?>) obj).obj);
        } else {
            if (this.obj == null)
                return obj == null;
            else
                return this.obj.equals(obj);
        }
    }

    /**
     * 包装器将会直接返回对应的对象的 String 内容，并不对其做特殊的包装和处理。<br>
     * <p/>
     *
     * 通过该方法将能够智能地直接获取包装器内部的对象的 String 值。<br>
     * <p/>
     *
     * 若包装器没有包装任何内容，则该方法将会返回一个空字符串，代表该包装器内部没有对象。<br>
     * 该方法的递归调用已经做了空指针检验，不必担心包装器内部尚未包装对象而导致异常的发生。<br>
     * <p/>
     *
     *
     * @return 返回内部对象的字符串值。
     */
    @Override
    public String toString() {
        return obj == null ? null : obj.toString();
    }
}
