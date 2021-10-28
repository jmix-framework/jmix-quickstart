package com.company.jmixpm.security;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.model.RowLevelPredicate;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Restrict projects for modification", code = "restricted-projects")
public interface RestrictedProjectsRole {
    @PredicateRowLevelPolicy(entityClass = Project.class,
            actions = {RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.DELETE}
    )
    default RowLevelPredicate<Project> allowOnlyManagerUpdateOrDeleteProject(CurrentAuthentication currentAuthentication) {
        return project -> {
            User currentUser = (User) currentAuthentication.getUser();
            return currentUser.equals(project.getManager());
        };
    }
}
