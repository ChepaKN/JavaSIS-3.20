package pro.sisit.adapter;

import java.util.List;

public interface CSV_converter {
    List<String>    fromFieldsToCSV();
    void            fromCSVToFields(List<String> fromCSV);
}
