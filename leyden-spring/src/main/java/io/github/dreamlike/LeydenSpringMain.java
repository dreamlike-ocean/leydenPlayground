package io.github.dreamlike;
import io.github.dreamlike.generated.Service0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.List;

@SpringBootApplication()
@EnableGenerate
public class LeydenSpringMain implements CommandLineRunner {
    @Autowired
    private Service0 service0;
    public static void main(String[] args) {
        new SpringApplicationBuilder(LeydenSpringMain.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        service0.run();
    }
}