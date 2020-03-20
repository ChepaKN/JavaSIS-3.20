package pro.sisit.javacourse.javaclassconfig;

public class DogShop implements PetService {
    @Override
    public Pet adorablePet() {
        return new Dog();
    }
}
