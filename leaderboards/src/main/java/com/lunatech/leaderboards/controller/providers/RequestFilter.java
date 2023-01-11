package com.lunatech.leaderboards.controller.providers;

import com.lunatech.leaderboards.entity.User;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
        securityEmail().ifPresent(email -> {
            User user = User.<User>find("email", email).singleResultOptional()
                            .orElseGet(() -> addUser(email));
            addUserEmailToHeader(user.email, context);
        });
    }

    private void addUserEmailToHeader(String email, ContainerRequestContext context) {
        context.getHeaders().add("email", email);
    }

    @Transactional
    public User addUser(String email) {
        User user = new User();
        user.email = email;
        user.displayName = email;
        user.persist();
        return user;
    }

    private Optional<String> securityEmail() {
        return Optional.ofNullable(securityIdentity)
                .map(SecurityIdentity::getPrincipal)
                .map(principal -> (OidcJwtCallerPrincipal) principal)
                .map(principal -> principal.getClaim("email"));
    }
}
