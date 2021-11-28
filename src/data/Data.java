package data;

/**
 * 数据对象接口<br>
 * <p/>
 *
 * 一个数据对象表示数据库中的一行信息！<br>
 * <p/>
 *
 * 接口约定：<br>
 * 1. 通过列属性获取相应的字符串字段，属性名忽略大小写及下划线（可以通过空格代替下划线）。<br>
 * 2. 获取失败返回 null, 否则必定返回一个字符串实例。<br>
 * <p/>
 *
 *
 */
public interface Data {
    String fetch(String property);
}
