package net.codetojoy.server.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

public class RegistryConfiguration {
    private final AnnotationConfigApplicationContext context;
    
    public RegistryConfiguration() {
        context = new AnnotationConfigApplicationContext(Config.class);        
    }

    public Object getMyRegistry() {
        return context.getBean(Config.BEAN_MY_REGISTRY);
    }
}
