package pro.sisit.javacourse.lifecyclehooks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleHooksDemoConfiguration {

    @Bean(initMethod = "onInited", destroyMethod = "onDestroy")
    public LifeCycleDemo lifeCycle() {
        return new LifeCycleDemo();
    }
}
