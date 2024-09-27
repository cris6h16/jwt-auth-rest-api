package org.cris6h16.Config.SpringBoot;

import org.cris6h16.Config.SpringBoot.Security.PasswordEncoderImpl;
import org.cris6h16.Config.SpringBoot.Security.UserDetails.UserDetailsServiceImpl;
import org.cris6h16.Config.SpringBoot.Services.ErrorMessagesImpl;
import org.cris6h16.Config.SpringBoot.Utils.JwtUtilsImpl;
import org.cris6h16.In.Ports.*;
import org.cris6h16.Repositories.UserRepository;
import org.cris6h16.Services.EmailService;
import org.cris6h16.Services.MyPasswordEncoder;
import org.cris6h16.UseCases.*;
import org.cris6h16.Utils.ErrorMessages;
import org.cris6h16.Utils.JwtUtils;
import org.cris6h16.Utils.UserValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// todo: classify this better
@Configuration
public class Beans {

    @Bean
    public ErrorMessages errorMessages() {
        return new ErrorMessagesImpl();
    }

    @Bean
    public UserValidator userValidator(ErrorMessages errorMessages) {
        return new UserValidator(errorMessages);
    }


    @Bean
    public CreateAccountPort createAccountPort(UserRepository userRepository,
                                               MyPasswordEncoder passwordEncoder,
                                               EmailService emailService,
                                               @Qualifier("JwtUtils") JwtUtils jwtUtils,
                                               ErrorMessages errorMessages,
                                               UserValidator userValidator) {
        return new CreateAccountUseCase(
                userRepository,
                passwordEncoder,
                emailService,
                jwtUtils,
                errorMessages,
                userValidator
        );
    }

    @Bean
    public VerifyEmailPort verifyEmailPort(UserRepository userRepository,
                                           UserValidator userValidator,
                                           ErrorMessages errorMessages
    ) {
        return new VerifyEmailUseCase(
                userRepository,
                userValidator,
                errorMessages
        );
    }

    @Bean
    public RequestResetPasswordPort requestResetPasswordPort(EmailService emailService,
                                                             UserValidator userValidator,
                                                             UserRepository userRepository,
                                                             ErrorMessages errorMessages) {
        return new RequestResetPasswordUseCase(
                emailService,
                userValidator,
                userRepository,
                errorMessages
        );
    }

    @Bean
    public LoginPort loginPort(UserRepository userRepository,
                               MyPasswordEncoder passwordEncoder,
                               @Qualifier("JwtUtils") JwtUtils jwtUtils,
                               EmailService emailService,
                               ErrorMessages errorMessages,
                               UserValidator userValidator) {
        return new LoginUseCase(
                userRepository,
                passwordEncoder,
                jwtUtils,
                emailService,
                errorMessages,
                userValidator
        );
    }


    @Bean
    public MyPasswordEncoder passwordEncoder(PasswordEncoder encoder) {
        return new PasswordEncoderImpl(encoder);
    }

    @Bean
    public PasswordEncoder springPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean("JwtUtils")
    public JwtUtils jwtUtils(@Value("${jwt.secret.key}") String secretKey,
                             @Value("${jwt.token.access.expiration.secs}") long accessTokenExpTimeSecs,
                             @Value("${jwt.token.refresh.expiration.secs}") long refreshTokenExpTimeSecs) {
        return new JwtUtilsImpl(secretKey, accessTokenExpTimeSecs, refreshTokenExpTimeSecs);
    }

    // used in infrastructure, contains methods like validate, getId, etc.
    @Bean("JwtUtilsImpl")
    public JwtUtilsImpl jwtUtilsImpl(@Value("${jwt.secret.key}") String secretKey,
                                     @Value("${jwt.token.access.expiration.secs}") long accessTokenExpTimeSecs,
                                     @Value("${jwt.token.refresh.expiration.secs}") long refreshTokenExpTimeSecs) {
        return new JwtUtilsImpl(secretKey, accessTokenExpTimeSecs, refreshTokenExpTimeSecs);
    }

    @Bean
    public ResetPasswordPort getResetPasswordPort(
            UserRepository userRepository,
            UserValidator userValidator,
            MyPasswordEncoder myPasswordEncoder,
            ErrorMessages errorMessages) {

        return new ResetPasswordUseCase(
                userRepository,
                userValidator,
                myPasswordEncoder,
                errorMessages
        );
    }

    // refreshAccessTokenPort
    @Bean
    public RefreshAccessTokenPort refreshAccessTokenPort(
            @Qualifier("JwtUtils") JwtUtils jwtUtils,
            UserRepository userRepository,
            ErrorMessages errorMessages,
            UserValidator userValidator
    ) {
        return new RefreshAccessTokenUseCase(
                jwtUtils,
                userRepository,
                errorMessages,
                userValidator
        );
    }

    //RequestDeleteAccountPort
    @Bean
    public RequestDeleteAccountPort requestDeleteAccountPort(
            UserValidator userValidator,
            UserRepository userRepository,
            EmailService emailService,
            ErrorMessages errorMessages
    ) {
        return new RequestDeleteAccountUseCase(
                userValidator,
                userRepository,
                emailService,
                errorMessages
        );
    }

    //DeleteAccountPort
    @Bean
    public DeleteAccountPort deleteAccountPort(
            UserValidator userValidator,
            UserRepository userRepository
    ) {
        return new DeleteAccountUseCase(
                userValidator,
                userRepository
        );
    }

    //UpdateUsernamePort
    @Bean
    public UpdateUsernamePort updateUsernamePort(
            UserValidator userValidator,
            UserRepository userRepository,
            ErrorMessages errorMessages
    ) {
        return new UpdateUsernameUseCase(
                userValidator,
                userRepository,
                errorMessages
        );
    }

    //UpdatePasswordPort
    @Bean
    public UpdatePasswordPort updatePasswordPort(
            UserValidator userValidator,
            UserRepository userRepository,
            MyPasswordEncoder myPasswordEncoder,
            ErrorMessages errorMessages
    ) {
        return new UpdatePasswordUseCase(
                userValidator,
                userRepository,
                myPasswordEncoder,
                errorMessages
        );
    }

    //UpdateEmailPort
    @Bean
    public UpdateEmailPort updateEmailPort(
            UserValidator userValidator,
            UserRepository userRepository,
            EmailService emailService,
            ErrorMessages errorMessages
    ) {
        return new UpdateEmailUseCase(
                userValidator,
                userRepository,
                emailService,
                errorMessages
        );
    }

    //RequestUpdateEmailPort
    @Bean
    public RequestUpdateEmailPort requestUpdateEmailPort(
            UserValidator userValidator,
            UserRepository userRepository,
            EmailService emailService,
            ErrorMessages errorMessages
    ) {
        return new RequestUpdateEmailUseCase(
                userValidator,
                userRepository,
                emailService,
                errorMessages
        );
    }

    //GetPublicProfilePort
    @Bean
    public GetPublicProfilePort getPublicProfilePort(
            UserValidator userValidator,
            UserRepository userRepository,
            ErrorMessages errorMessages
    ) {
        return new GetPublicProfileUseCase(
                userValidator,
                userRepository,
                errorMessages
        );
    }

    //GetAllPublicProfilesPort
    @Bean
    public GetAllPublicProfilesPort getAllPublicProfilesPort(
            UserRepository userRepository
            ) {
        return new GetAllPublicProfilesUseCase(
                userRepository
        );
    }

}

