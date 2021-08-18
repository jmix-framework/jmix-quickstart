package com.company.jmixpm.screen.task;

import com.company.jmixpm.app.TaskService;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {
    @Autowired
    private TaskService taskService;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Task> event) {
        event.getEntity().setAssignee(taskService.findLeastBusyUser());
    }
}