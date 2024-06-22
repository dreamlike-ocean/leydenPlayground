package io.github.dreamlike;

import io.github.dreamlike.generated.Service9999;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@SpringBootApplication()
public class LeydenSpringMain {
    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(LeydenSpringMain.class, args);
//        context.getBean(Service9999.class).run();
        new SpringApplicationBuilder(LeydenSpringMain.class)
                .web(WebApplicationType.NONE)
                .run(args)
                .getBean(Service9999.class).run();
    }


}