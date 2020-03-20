package pro.sisit.javacourse.javaclassconfig;


public class Toy {
    private String name;

    public Toy(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Toy {" +
                "name='" + name + '\'' +
                '}';
    }
}
