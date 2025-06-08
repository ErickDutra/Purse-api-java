package purse.coin.purse.integration.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import purse.coin.purse.integration.blockchain.InviteTransaction;

@Configuration
public class AcceptTransactionConfig {
    @Bean
    @Lazy
    public InviteTransaction inviteTransaction() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger(InviteTransaction.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(InviteTransaction.class, "http://localhost:8000");

    }

}
