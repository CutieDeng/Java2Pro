package service;

import data.Data;

import java.util.List;

/**
 * 数据相关服务者负责掌控一个文件-层级的数据信息。<br>
 * <p/>
 *
 * 每个服务者提供一个相关服务。<br>
 * 其中包括提供其拥有的数据内容和数据列名。<br>
 * <p/>
 *
 */
public interface DataService {
    /**
     * 获取数据服务者托管的所有数据。<br>
     * <p/>
     *
     * @return 所有其托管的数据
     */
    List<Data> getDataList();

    /**
     * 获取数据服务者托管的数据的列名集合。<br>
     * <p/>
     *
     * @return 其托管的数据表格的列名
     */
    List<String> getColumnNames();
}
