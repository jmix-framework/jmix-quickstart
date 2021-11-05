package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import io.jmix.ldap.userdetails.AbstractLdapUserDetailsSynchronizationStrategy;
import io.jmix.security.authentication.RoleGrantedAuthority;
import io.jmix.security.model.RowLevelRole;
import io.jmix.security.role.RowLevelRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class LdapUserSyncStrategy extends AbstractLdapUserDetailsSynchronizationStrategy<User> {

    @Autowired
    private RowLevelRoleRepository rowLevelRoleRepository;

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

    /**
     * Additionally get row-level roles from repeatable "employeeType" LDAP user attribute.
     */
    @Nonnull
    @Override
    protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, @Nonnull String username) {
        String[] roleCodes = user.getStringAttributes("employeeType");
        if (roleCodes == null || roleCodes.length == 0) {
            return Collections.emptySet();
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String roleCode: roleCodes) {
            RowLevelRole rowLevelRole = rowLevelRoleRepository.findRoleByCode(roleCode);
            if (rowLevelRole != null) {
                RoleGrantedAuthority grantedAuthority = RoleGrantedAuthority.ofRowLevelRole(rowLevelRole);
                authorities.add(grantedAuthority);
            }
        }
        return authorities;
    }
}