package com.company.jmixpm.security;

import com.company.jmixpm.entity.*;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.model.RowLevelPredicate;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Restrictions for developer", code = DeveloperRowLevelRole.CODE)
public interface DeveloperRowLevelRole {
    String CODE = "developer-restrictions";

    @JpqlRowLevelPolicy(entityClass = Task.class,
            where = "{E}.assignee.id = :current_user_id"
    )
    void restrictTasks();

    @JpqlRowLevelPolicy(entityClass = TimeEntry.class,
            where = "{E}.assignee.id = :current_user_id"
    )
    void restrictTimeEntries();

    @PredicateRowLevelPolicy(entityClass = TimeEntry.class,
            actions = {RowLevelPolicyAction.CREATE, RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.DELETE}
    )
    default RowLevelPredicate<TimeEntry> allowOnlyManagerUpdateOrDeleteProject(CurrentAuthentication currentAuthentication) {
        return entry -> {
            User currentUser = (User) currentAuthentication.getUser();
            return currentUser.equals(entry.getAssignee());
        };
    }
}
