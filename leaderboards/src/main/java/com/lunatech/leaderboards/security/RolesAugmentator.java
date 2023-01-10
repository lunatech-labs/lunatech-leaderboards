package com.lunatech.leaderboards.security;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class RolesAugmentator implements SecurityIdentityAugmentor {

    @Inject
    Instance<SecurityIdentitySupplier> securityIdentitySupplierInstance;

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity securityIdentity, AuthenticationRequestContext context) {
        if(securityIdentity.isAnonymous()) return Uni.createFrom().item(securityIdentity);

        var securityIdentitySupplier = securityIdentitySupplierInstance.get();
        securityIdentitySupplier.setSecurityIdentity(securityIdentity);
        return context.runBlocking(securityIdentitySupplier);
    }

}
