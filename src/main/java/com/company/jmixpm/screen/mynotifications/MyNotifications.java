package com.company.jmixpm.screen.mynotifications;

import com.company.jmixpm.entity.Notification;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("MyNotifications")
@UiDescriptor("my-notifications.xml")
public class MyNotifications extends Screen {

    @Autowired
    private DataGrid<Notification> notificationsTable;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionLoader<Notification> notificationsDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        notificationsDl.setParameter("current_user_id", ((User) currentAuthentication.getUser()).getId());
        notificationsDl.load();
    }

    @Subscribe("notificationsTable.markAsRead")
    public void onNotificationsTableMarkAsRead(Action.ActionPerformedEvent event) {
        // take item from the table
        Notification item = notificationsTable.getSingleSelected();
        if (item == null) {
            return;
        }

        // set isRead
        item.setIsRead(true);
        // save changes
        dataManager.save(item);

        // reload the table
        notificationsDl.load();
    }
}
