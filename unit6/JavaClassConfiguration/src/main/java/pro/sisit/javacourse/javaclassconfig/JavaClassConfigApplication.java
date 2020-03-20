package pro.sisit.javacourse.javaclassconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JavaClassConfigApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(JavaClassConfiguration.class);

        JavaClassConfiguration config = applicationContext.getBean(JavaClassConfiguration.class);

        // bean petService по умолчанию
        PetService service = applicationContext.getBean(PetService.class);

        // bean petService - явно задаем кошачий
        PetService serviceCat = applicationContext.getBean(CatShop.class);

        // bean petService - явно задаем собачий
        PetService serviceDog = config.petServiceDog();

        System.out.println("cat service speak: " + serviceCat.adorablePet().speak());
        System.out.println("dog service speak: " + serviceDog.adorablePet().speak());

        System.out.println("cat's toy is: " + serviceCat.adorablePet().toy());

        System.out.println("default service speak: " + service.adorablePet().speak());
    }
}
