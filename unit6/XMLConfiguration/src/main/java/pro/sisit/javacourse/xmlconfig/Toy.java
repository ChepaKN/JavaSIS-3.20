package pro.sisit.javacourse.xmlconfig;


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
