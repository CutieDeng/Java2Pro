package serviceimplements;

import data.Data;
import service.DataService;
import tool.Controller;
import tool.FileController;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class HighDataService implements DataService {

    private static final File dataFile = Paths.get("res", "file", "owid-covid-data.csv").toFile();

    private FileController c;

    public HighDataService() {
        c = Controller.instance.getFileData(dataFile);
    }

    @Override
    public List<Data> getDataList() {
        return c.higherList;
    }

    @Override
    public List<String> getColumnNames() {
        return c.higherListColName;
    }
}
