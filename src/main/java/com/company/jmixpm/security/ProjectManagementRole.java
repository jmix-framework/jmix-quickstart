package com.company.jmixpm.security;

import com.company.jmixpm.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "ProjectManagement", code = "project-management")
public interface ProjectManagementRole {
    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.ALL)
    void project();

    @EntityAttributePolicy(entityClass = Document.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Document.class, actions = EntityPolicyAction.ALL)
    void document();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"firstName", "lastName", "email"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"id", "version", "username", "password", "active"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @ScreenPolicy(screenIds = {"User.browse", "Project.browse", "Task_.browse", "Document.browse", "Document.edit", "Project.edit", "Task_.edit", "User.edit", "MyNotifications", "TimeEntry.browse", "TimeEntry.edit", "QuickAddTimeEntry"}, screenClasses = {})
    void screens();

    @MenuPolicy(menuIds = {"application", "Project.browse", "Task_.browse", "Document.browse", "User.browse", "MyNotifications", "TimeEntry.browse"})
    void commonMenus();

    @SpecificPolicy(resources = "pm.projects.archive")
    void specify();

    @EntityAttributePolicy(entityClass = Notification.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Notification.class, actions = EntityPolicyAction.READ)
    void notification();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.ALL)
    void timeEntry();
}