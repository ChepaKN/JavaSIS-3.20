package pro.sisit.javacourse.lifecyclehooks;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class LifeCycleDemo {

    public void onInited() {
        System.out.println("Life Cycle: onInited");
    }

    public void onDestroy() {
        System.out.println("Life Cycle: onDestroy");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Life Cycle: @PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Life Cycle: @PreDestroy");
    }
}
