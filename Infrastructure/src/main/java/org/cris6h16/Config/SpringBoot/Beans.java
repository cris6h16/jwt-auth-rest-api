package org.cris6h16.Config.SpringBoot;

import org.cris6h16.Adapters.Out.SpringData.UserJpaRepository;
import org.cris6h16.Config.SpringBoot.Security.PasswordEncoderImpl;
import org.cris6h16.In.Ports.CreateAccountPort;
import org.cris6h16.UseCases.CreateAccountUseCase;
import org.cris6h16.Utils.MyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
public class Beans {

    @Bean
    @Scope("singleton")
    public CreateAccountPort createAccountCommand(UserJpaRepository userJpaRepository, MyPasswordEncoder passwordEncoder) {
        return new CreateAccountUseCase(userJpaRepository, passwordEncoder);
    }

    @Bean
    @Scope("singleton")
    public MyPasswordEncoder passwordEncoder() {
        return new PasswordEncoderImpl();
    }
}

