package io.github.dreamlike;

import io.github.dreamlike.generated.Service0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.io.FileNotFoundException;
import java.io.PrintStream;

@SpringBootApplication()
@EnableGenerate
public class LeydenSpringMain implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LeydenSpringMain.class);

    @Autowired
    private Service0 service0;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws FileNotFoundException {
        boolean printLog = Boolean.parseBoolean(System.getenv().getOrDefault("printLog", "true"));
        if (!printLog) {
            System.setOut(new PrintStream("/dev/null"));
        }
        new SpringApplicationBuilder(LeydenSpringMain.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("bean count: {}", applicationContext.getBeanDefinitionCount());
        service0.run();
    }
}