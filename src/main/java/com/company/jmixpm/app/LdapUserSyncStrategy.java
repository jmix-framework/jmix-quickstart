package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import io.jmix.ldap.userdetails.AbstractLdapUserDetailsSynchronizationStrategy;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class LdapUserSyncStrategy extends AbstractLdapUserDetailsSynchronizationStrategy<User> {

    @Nonnull
    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void mapUserDetailsAttributes(User userDetails, DirContextOperations ctx) {
        userDetails.setFirstName(ctx.getStringAttribute("givenName"));
        userDetails.setLastName(ctx.getStringAttribute("sn"));
        userDetails.setEmail(ctx.getStringAttribute("mail"));
        userDetails.setTelephoneNumber(ctx.getStringAttribute("telephoneNumber"));
    }
}