package mystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableAspectJAutoProxy
public class MystoreApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(MystoreApplication.class, args);
        
    }
}
