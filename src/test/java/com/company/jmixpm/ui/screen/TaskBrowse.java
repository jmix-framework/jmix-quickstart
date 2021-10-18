package com.company.jmixpm.ui.screen;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.Table;

import static io.jmix.masquerade.Components.wire;

public class TaskBrowse extends Composite<TaskBrowse> {

    @Wire
    private Button createBtn;
    @Wire
    private Table tasksTable;

    public Table getTasksTable() {
        return tasksTable;
    }

    public TaskEdit create() {
        createBtn.should(Condition.visible)
                .click();
        return wire(TaskEdit.class);
    }
}
