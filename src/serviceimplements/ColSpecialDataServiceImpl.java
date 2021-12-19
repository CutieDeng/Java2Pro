package serviceimplements;

import data.Data;
import service.DataService;
import tool.Col2RowController;
import tool.Controller;

import java.io.File;
import java.util.List;

public class ColSpecialDataServiceImpl implements DataService {

    private final Col2RowController controller;

    public ColSpecialDataServiceImpl(File file) {
        controller = Controller.instance.getColFileData(file);
    }

    @Override
    public List<Data> getDataList() {
        return controller.getDataList();
    }

    @Override
    public List<String> getColumnNames() {
        return controller.getColumnNames();
    }
}
