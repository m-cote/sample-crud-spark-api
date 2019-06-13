package app;

import app.config.WebConfig;
import app.controller.UsersController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"app"})
public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        new WebConfig(ctx.getBean(UsersController.class));
        ctx.registerShutdownHook();

    }

}
