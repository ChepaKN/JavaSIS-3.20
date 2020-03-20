package pro.sisit.javacourse.javaclassconfig;

public class Dog extends Pet {
    @Override
    public String speak() {
        return "ГАВ!";
    }

    @Override
    public Toy toy() {
        return null;
    }
}
