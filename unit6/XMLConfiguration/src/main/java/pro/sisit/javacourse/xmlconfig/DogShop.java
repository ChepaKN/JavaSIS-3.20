package pro.sisit.javacourse.xmlconfig;

public class DogShop implements PetService {
    @Override
    public Pet adorablePet() {
        return new Dog();
    }
}
