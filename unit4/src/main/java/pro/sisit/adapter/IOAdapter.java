package pro.sisit.adapter;

import java.io.IOException;

public interface IOAdapter<T> {

    T read(int index) throws IllegalAccessException, InstantiationException, IOException;

    int append(T entity) throws IllegalAccessException, IOException;
}
