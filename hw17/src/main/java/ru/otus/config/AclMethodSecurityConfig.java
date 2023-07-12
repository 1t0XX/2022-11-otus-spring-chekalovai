package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class AclMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private final MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return defaultMethodSecurityExpressionHandler;
    }
}