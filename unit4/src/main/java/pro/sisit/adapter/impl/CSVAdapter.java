package pro.sisit.adapter.impl;

import pro.sisit.adapter.CSV_converter;
import pro.sisit.adapter.IOAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T extends CSV_converter> implements IOAdapter<T> {

    private Class<T>        entityType;
    private BufferedReader  reader;
    private BufferedWriter  writer;
    private final String    separator;
    private final int       MAX_PAGE_SIZE;

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
                      BufferedWriter writer, String separator) {

        this.entityType     = entityType;
        this.reader         = reader;
        this.writer         = writer;
        this.separator      = separator;
        this.MAX_PAGE_SIZE  = 4096;
    }

    private boolean setCursorToLine(int lineIndex) throws IOException {
        int lines = calculateCSVFileLines();
        if(((lines - 1) < lineIndex) || (lineIndex < 0)) { return false; }
        for(int i  = 0; i < lineIndex; i++){
            String  str = reader.readLine();
        }
        return true;
    }

    @Override
    public T read(int index) throws IllegalAccessException, InstantiationException, IOException {


        //Метка, чтобы после всех операций чтения вернуться в начало
        reader.mark(MAX_PAGE_SIZE);
        if(!setCursorToLine(index)){
            throw new RuntimeException(entityType.getName() + ":T read() -> Не удалось прочитать по заданному индексу: index = " + index);
        }
        //Читаем CSV
        String[] readedFields = reader.readLine().split(separator);
        reader.reset();

        List<String> toFields = new ArrayList<>();
        for(int i = 0; i < readedFields.length; i++) toFields.add(readedFields[i]);

        T returnClass = entityType.newInstance();
        returnClass.fromCSVToFields(toFields);
        return returnClass;

    }

    private int calculateCSVFileLines() throws IOException {
        int cntLines = 0;
        reader.mark(MAX_PAGE_SIZE); //todo: Узкое место, переделать
        while(reader.readLine() != null) cntLines++;
        reader.reset();
        return cntLines;
    }

    @Override
    public int append(CSV_converter converter) throws IOException {

        List<String> inputListToCSV = converter.fromFieldsToCSV();
        for(int i = 0; i < inputListToCSV.size(); i++){
            writer.write(inputListToCSV.get(i) + separator);
        }
        writer.flush();
        writer.newLine();

        //Посчетаем кол-во строк в файле
        int lines = calculateCSVFileLines();
        return  lines - 1;
    }
}

