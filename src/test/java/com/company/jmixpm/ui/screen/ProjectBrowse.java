package com.company.jmixpm.ui.screen;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.Table;

import static io.jmix.masquerade.Components.wire;

public class ProjectBrowse extends Composite<ProjectBrowse> {

    @Wire
    private Button createBtn;
    @Wire
    private Table projectsTable;
    @Wire
    private Button lookupSelectAction;

    public Table getProjectsTable() {
        return projectsTable;
    }

    public ProjectEdit create() {
        createBtn.should(Condition.visible)
                .click();
        return wire(ProjectEdit.class);
    }

    public void selectProject(String projectName) {
        projectsTable.shouldBe(Condition.visible)
                .selectRow(Selectors.byText(projectName));

        lookupSelectAction.shouldBe(Condition.visible, Condition.enabled)
                .click();
    }
}
