package com.company.jmixpm.ui.screen;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.DateField;
import io.jmix.masquerade.component.EntityPicker;
import io.jmix.masquerade.component.TextField;

public class ProjectEdit extends Composite<ProjectEdit> {

    @Wire
    private TextField nameField;
    @Wire
    private DateField startDateField;
    @Wire
    private DateField endDateField;
    @Wire
    private EntityPicker managerField;
    @Wire
    private Button commitAndCloseBtn;

    public TextField getNameField() {
        return nameField;
    }

    public DateField getStartDateField() {
        return startDateField;
    }

    public DateField getEndDateField() {
        return endDateField;
    }

    public EntityPicker getManagerField() {
        return managerField;
    }

    public void commitAndClose() {
        commitAndCloseBtn.shouldBe(Condition.visible)
                .click();
    }
}
