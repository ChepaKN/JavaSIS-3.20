package pro.sisit.javacourse.lifecyclehooks;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleHooksDemoApplication {

    public static void main(String[] args) {
        System.out.println("---> Program started");

        ApplicationContext context = new
            AnnotationConfigApplicationContext(LifeCycleHooksDemoConfiguration.class);

        System.out.println("---> Context created");

        printBeanDefinition(context);

        demoBeanCreatedOnlyOnce(context);

        closeContext(context);

        System.out.println("---> Program finished");
    }

    /**
     * Список бинов, загруженных в даный момент
     */
    private static void printBeanDefinition(ApplicationContext context) {
        System.out.println("========== Bean definitions ==========");

        for (String s : context.getBeanDefinitionNames()) {
            System.out.println(s + " >> " + context.getBean(s));
        }

        System.out.println("========== Bean definitions ==========");
    }

    /**
     * чтобы показать в каких случаях вызываются хуки
     */
    private static void closeContext(ApplicationContext context) {
        System.out.println("========== Context closing ==========");

        ((AnnotationConfigApplicationContext) context).close();

        System.out.println("========== Context closing ==========");
    }

    /**
     * @Bean создается только один раз. Почему?
     * класс Unit6Configuration обертывается прокси, через библиотеку CGLIB (Code Generation Library)
     * внутри вызова метода getBean идет проверка не получался ли уже такой бин,
     * и если да то возвращается он же, без инициализации нового
     */
    private static void demoBeanCreatedOnlyOnce(ApplicationContext context) {
        System.out.println("========== Bean created only once ==========");

        LifeCycleDemo myFirstBean = context.getBean("lifeCycle", LifeCycleDemo.class);
        LifeCycleHooksDemoConfiguration configuration = context.getBean(LifeCycleHooksDemoConfiguration.class);
        LifeCycleDemo mySecondBean = configuration.lifeCycle();

        System.out.println(myFirstBean.equals(mySecondBean));

        System.out.println("========== Bean created only once ==========");
    }
}
