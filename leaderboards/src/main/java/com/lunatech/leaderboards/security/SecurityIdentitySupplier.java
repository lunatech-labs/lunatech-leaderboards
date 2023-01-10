package com.lunatech.leaderboards.security;

import com.lunatech.leaderboards.entity.User;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import java.util.function.Supplier;

@Dependent
class SecurityIdentitySupplier implements Supplier<SecurityIdentity> {

    private SecurityIdentity securityIdentity;

    @Override
    @ActivateRequestContext
    public SecurityIdentity get() {
        return User.find("email", securityEmail(securityIdentity)).singleResultOptional()
                .map(user -> (SecurityIdentity) QuarkusSecurityIdentity.builder(securityIdentity).addRole("user").build())
                .orElse(securityIdentity);
    }

    private String securityEmail(SecurityIdentity securityIdentity) {
        OidcJwtCallerPrincipal oidcPrincipal = (OidcJwtCallerPrincipal) securityIdentity.getPrincipal();
        return oidcPrincipal.getClaim("email");
    }

    public void setSecurityIdentity(SecurityIdentity securityIdentity) {
        this.securityIdentity = securityIdentity;
    }
}
