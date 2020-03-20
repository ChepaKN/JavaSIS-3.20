package pro.sisit.javacourse.javaclassconfig;


public class Cat extends Pet {
    public Cat(Toy toy) {
        this.toy = toy;
    }

    @Override
    public String speak() {
        return "МЯУ!";
    }

    private Toy toy;

    @Override
    public Toy toy() {
        return toy;
    }



}
