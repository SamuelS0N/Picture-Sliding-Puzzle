package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.Field;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {

    public static void main(String[] args) {
        //SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI) {
        return s -> consoleUI.play();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI(new Field(2,2));
    }

    @Bean
    public ScoreService scoreService() {return new ScoreServiceRestClient(); }

    @Bean
    public CommentService commentService() { return new CommentServiceRestClient(); }

    @Bean
    public RatingService ratingService() { return new RatingServiceRestClient();}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
