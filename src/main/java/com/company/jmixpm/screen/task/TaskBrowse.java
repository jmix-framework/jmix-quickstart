package com.company.jmixpm.screen.task;

import com.company.jmixpm.entity.Task;
import com.company.jmixpm.screen.timeentry.quickaddtimeentry.QuickAddTimeEntryDialog;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private GroupTable<Task> tasksTable;

    @Subscribe("tasksTable.addSpentTime")
    public void onTasksTableAddSpentTime(Action.ActionPerformedEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task == null) {
            return;
        }
        QuickAddTimeEntryDialog dialog = screenBuilders.screen(this)
                .withScreenClass(QuickAddTimeEntryDialog.class)
                .build();
        dialog.setTask(task);
        dialog.show();
    }
}