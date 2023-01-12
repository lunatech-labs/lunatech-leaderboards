package com.lunatech.leaderboard.controller.provider.filter;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
public class RequestFilter implements ContainerRequestFilter {

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        addUserEmailToHeader(context);
    }

    private void addUserEmailToHeader(ContainerRequestContext context) {
        securityEmail().ifPresent(email -> context.getHeaders().add("email", email));
    }

    private Optional<String> securityEmail() {
        return Optional.ofNullable(securityIdentity)
                .map(SecurityIdentity::getPrincipal)
                .map(principal -> (OidcJwtCallerPrincipal) principal)
                .map(principal -> principal.getClaim("email"));
    }
}
