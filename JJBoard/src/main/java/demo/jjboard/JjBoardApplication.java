package demo.jjboard;

import com.querydsl.core.QueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JjBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjBoardApplication.class, args);
    }




}
