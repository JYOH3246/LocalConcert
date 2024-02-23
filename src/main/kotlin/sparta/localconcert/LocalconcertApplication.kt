package sparta.localconcert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class LocalconcertApplication

fun main(args: Array<String>) {
    runApplication<LocalconcertApplication>(*args)
}
