package com.company.jmixpm.security;

import com.company.jmixpm.entity.Notification;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.TimeEntry;
import com.company.jmixpm.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Developer", code = DeveloperRole.CODE, scope = "UI")
public interface DeveloperRole extends UiMinimalRole {
    String CODE = "developer";

    @MenuPolicy(menuIds = {"Task_.browse", "MyNotifications", "TimeEntry.browse"})
    @ScreenPolicy(screenIds = {"Task_.browse", "MyNotifications", "TimeEntry.browse", "QuickAddTimeEntry", "TimeEntry.edit", "Task_.edit", "User.browse"}, screenClasses = {})
    void screens();

    @EntityAttributePolicy(entityClass = Notification.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Notification.class, actions = EntityPolicyAction.READ)
    void notification();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.READ)
    void task();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.ALL)
    void timeEntry();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"username", "firstName", "lastName", "email", "id", "version"}, action = EntityAttributePolicyAction.VIEW)
    void user();
}