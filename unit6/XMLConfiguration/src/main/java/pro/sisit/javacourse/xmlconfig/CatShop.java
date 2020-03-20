package pro.sisit.javacourse.xmlconfig;

public class CatShop implements PetService {

    private Toy toy;

    public CatShop(Toy toy) {
        this.toy = toy;
    }

    @Override
    public Pet adorablePet() {
        return new Cat(toy);
    }
}
