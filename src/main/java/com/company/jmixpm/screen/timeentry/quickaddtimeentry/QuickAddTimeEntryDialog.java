package com.company.jmixpm.screen.timeentry.quickaddtimeentry;

import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.TimeEntry;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("QuickAddTimeEntry")
@UiDescriptor("quick-add-time-entry.xml")
public class QuickAddTimeEntryDialog extends Screen {

    private Task task;

    @Autowired
    private InstanceContainer<TimeEntry> timeEntryDc;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private Form timeEntryForm;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = (User) currentAuthentication.getUser();
        user = dataManager.load(User.class).id(user.getId()).one();

        TimeEntry entry = dataContext.create(TimeEntry.class);
        entry.setTask(task);
        entry.setAssignee(user);
        timeEntryDc.setItem(entry);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        ValidationErrors validationErrors = screenValidation.validateUiComponents(timeEntryForm);
        if (!validationErrors.isEmpty()) {
            screenValidation.showValidationErrors(this, validationErrors);
            return;
        }
        dataContext.commit();
        close(StandardOutcome.COMMIT);
        notifications.create(Notifications.NotificationType.TRAY)
                .withDescription(messageBundle.getMessage("timeEntrySaved"))
                .show();
    }


}