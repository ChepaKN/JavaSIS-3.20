package pro.sisit.adapter.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import pro.sisit.adapter.IOAdapter;

// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T> implements IOAdapter<T> {

    private Class<T> entityType;
    private BufferedReader reader;
    private BufferedWriter writer;

    public CSVAdapter(Class<T> entityType, BufferedReader reader,
        BufferedWriter writer) {

        this.entityType = entityType;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public T read(int index) {
        throw new RuntimeException("Метод read не реализован");
    }

    @Override
    public int append(T entity) {
        throw new RuntimeException("Метод append не реализован");
    }
}
