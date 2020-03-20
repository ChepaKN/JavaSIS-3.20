package pro.sisit.javacourse.javaclassconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JavaClassConfiguration {

    @Bean
    @Primary
    public PetService petServiceCat() {
        return new CatShop(new Toy("mouse"));
    }

    @Bean
    public PetService petServiceDog() {
        return new DogShop();
    }
}
