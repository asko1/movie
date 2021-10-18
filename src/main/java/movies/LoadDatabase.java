package movies;

import movies.dto.Actor;
import movies.dto.Movie;
import movies.repos.ActorRepository;
import movies.repos.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initMovieDatabase(MovieRepository movieRepository) {
        return args -> {
            log.info("Preloading " + movieRepository.save(new Movie("Lord of the Rings")));
            log.info("Preloading " + movieRepository.save(new Movie("Lord of the Kings")));
        };
    }

    @Bean
    CommandLineRunner initActorDatabase(ActorRepository actorRepository) {
        return args -> {
            log.info("Preloading " + actorRepository.save(new Actor("Indrek Ott")));
            log.info("Preloading " + actorRepository.save(new Actor("Asko Mägi")));
        };
    }
}
