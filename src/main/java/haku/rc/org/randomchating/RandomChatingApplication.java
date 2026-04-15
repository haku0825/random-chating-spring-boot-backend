package haku.rc.org.randomchating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RandomChatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomChatingApplication.class, args);
    }

}
