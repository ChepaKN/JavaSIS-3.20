package pro.sisit.adapter;

public interface IOAdapter<T> {

    T read(int index);

    int append(T entity);
}
