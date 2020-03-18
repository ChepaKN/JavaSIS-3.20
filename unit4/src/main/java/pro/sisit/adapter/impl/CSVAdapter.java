package pro.sisit.adapter.impl;

import pro.sisit.adapter.IOAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T> implements IOAdapter<T> {

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

        //Узнаем список полей класса
        Field[] fields = entityType.getDeclaredFields();

        //Получаем объект
        T entityClass = entityType.newInstance();
        //Устанавливаем курсор на нужную строку

        //Метка, чтобы после всех операций чтения вернуться в начало
        reader.mark(MAX_PAGE_SIZE);
        if(!setCursorToLine(index)){
            throw new RuntimeException(entityType.getName() + ":T read() -> Не удалось прочитать по заданному индексу: index = " + index);
        }
        //Читаем CSV
        String[] readedFields = reader.readLine().split(separator);
        reader.reset();

        if(fields.length != readedFields.length) {
            throw new RuntimeException(entityType.getName() + ":T read() -> Несовпадение колличества полей класса и считанных из CSV файла!");
        }

        //Заполняем объект
        for(int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true);
            fields[i].set(entityClass, readedFields[i]);
            fields[i].setAccessible(false);
        }
        return entityClass;

    }

    private int calculateCSVFileLines() throws IOException {
        int cntLines = 0;
        reader.mark(1000); //todo: Узкое место, переделать
        while(reader.readLine() != null) cntLines++;
        reader.reset();
        return cntLines;
    }

    @Override
    public int append(T entity) throws IllegalAccessException, IOException {

        //Получаем имена полей
        Field[] fields = entityType.getDeclaredFields();
        List<String> fieldsList = new ArrayList<>();
        //Достаем значения и записываем в файл с заданными разделителями
        for(Field f : fields){
            f.setAccessible(true);
            writer.write(f.get(entity).toString() + separator);
            f.setAccessible(false);
        }
        writer.newLine();
        writer.flush();

        //Посчетаем кол-во строк в файле
        int lines = calculateCSVFileLines();
        //Вернем индекс записанной строки
        return lines - 1;
    }
}


