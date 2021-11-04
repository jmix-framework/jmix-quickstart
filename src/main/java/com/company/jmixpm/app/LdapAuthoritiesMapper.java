package com.company.jmixpm.app;

import com.company.jmixpm.security.CombinedManagerRole;
import com.company.jmixpm.security.DeveloperRole;
import com.company.jmixpm.security.FullAccessRole;
import io.jmix.ldap.userdetails.JmixLdapGrantedAuthoritiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LdapAuthoritiesMapper {
    @Autowired
    private JmixLdapGrantedAuthoritiesMapper grantedAuthoritiesMapper;

    private final Map<String, String> authorityMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        authorityMap.put("admin", FullAccessRole.CODE);
        authorityMap.put("managers", CombinedManagerRole.CODE);
        authorityMap.put("developers", DeveloperRole.CODE);

//        authorityMap.put("Scientists", FullAccessRole.CODE);
//        authorityMap.put("Chemists", CombinedManagerRole.CODE);
//        authorityMap.put("Mathematicians", DeveloperRole.CODE);

        grantedAuthoritiesMapper.setAuthorityToRoleCodeMapper(this::mapAuthorityToRoleCode);
    }

    public String mapAuthorityToRoleCode(String authority) {
        return authorityMap.getOrDefault(authority, authority);
    }
}