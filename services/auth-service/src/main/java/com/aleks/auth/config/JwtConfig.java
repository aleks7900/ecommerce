package com.aleks.auth.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

  @Bean
  public KeyPair keyPair() throws Exception {

    KeyPairGenerator generator =
        KeyPairGenerator.getInstance("RSA");

    generator.initialize(2048);

    return generator.generateKeyPair();
  }

  @Bean
  public RSAKey rsaKey(KeyPair keyPair) {

    return new RSAKey.Builder(
        (RSAPublicKey) keyPair.getPublic()
    )
        .privateKey((RSAPrivateKey) keyPair.getPrivate())
        .keyID(UUID.randomUUID().toString())
        .build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource(
      RSAKey rsaKey
  ) {

    return new ImmutableJWKSet<>(
        new com.nimbusds.jose.jwk.JWKSet(rsaKey)
    );
  }

  @Bean
  public JwtEncoder jwtEncoder(
      JWKSource<SecurityContext> jwkSource
  ) {

    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder(
      RSAKey rsaKey
  ) throws JOSEException {

    return NimbusJwtDecoder
        .withPublicKey(rsaKey.toRSAPublicKey())
        .build();
  }
}
