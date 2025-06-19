package purse.coin.purse.integration.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import purse.coin.purse.integration.blockchain.AcceptTransactions;

@Configuration
public class AcceptTransactionConfig {

    @Bean
    @Lazy
    public AcceptTransactions AcceptTransactions() {
        return Feign.builder()
                .decoder(new feign.jackson.JacksonDecoder())
                .encoder(new feign.jackson.JacksonEncoder())
                .logger(new Slf4jLogger(AcceptTransactions.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(AcceptTransactions.class, "http://localhost:8000");

    }

}
