package com.company.jmixpm.screen.notification;

import com.company.jmixpm.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Notification.browse")
@UiDescriptor("notification-browse.xml")
@LookupComponent("table")
public class NotificationBrowse extends MasterDetailScreen<Notification> {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Notification> event) {
        event.getEntity().setSender((User) currentAuthentication.getUser());
        event.getEntity().setIsRead(false);
    }
}