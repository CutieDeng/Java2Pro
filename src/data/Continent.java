package data;

import java.util.Locale;

/**
 * 七大洲<br>
 * <p/>
 *
 * 一个包含了七大洲（亚洲、欧洲...）的枚举类。<br>
 * <p/>
 *
 * 调用 {@link Continent#valueOf(String)} 能够直接获得相应的枚举实例。<br>
 * 例如：<br>
 * <blockquote>
 *     Continent africa = Continent.valueOf("AFRICA");
 * </blockquote>
 * <p/>
 *
 * 调用该方法需要传递相关的对应的枚举实例全名，而不是缺省值、或者其他语言下的翻译。<br>
 * 如果尝试写出以下代码：<br>
 * <blockquote>
 *     Continent 亚洲 = Continent.valueOf("亚洲");
 * </blockquote>
 * 则会得到一个参数不合法的异常回馈：{@link IllegalArgumentException}.
 * <p/>
 *
 * 在 tool 包中提供了一个特殊的解释器实例 {@link tool.Explainer}, 它将提供重载的解释方法 {@link tool.Explainer#getName(Continent)} 用户获取对应语言下的实例解释。<br>
 * <p/>
 *
 * @see tool.Explainer
 */
@SuppressWarnings("unused")
public enum Continent {
    /**
     * 亚洲
     */
    ASIA,
    /**
     * 欧洲
     */
    EUROPE,
    /**
     * 北美洲
     */
    NORTH_AMERICA,
    /**
     * 南美洲
     */
    SOUTH_AMERICA,
    /**
     * 非洲
     */
    AFRICA,
    /**
     * 大洋洲
     */
    OCEANIA,
    /**
     * 南极洲
     */
    ANTARCTICA;

    public static void main(String[] args) {
        testGetContinent("africa".toUpperCase(Locale.ROOT));
        testGetContinent("亚洲");
    }

    private static void testGetContinent(String name) {
        Continent continent = Continent.valueOf(name);
        System.out.println(continent);
    }
}
