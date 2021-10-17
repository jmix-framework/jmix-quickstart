package com.company.jmixpm.ui.screen;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.Table;

public class UserBrowse extends Composite<UserBrowse> {

    @Wire
    private Table usersTable;

    @Wire
    private Button lookupSelectAction;

    public void selectUser(String username) {
        usersTable.shouldBe(Condition.visible)
                .selectRow(Selectors.byText(username));

        lookupSelectAction.shouldBe(Condition.visible, Condition.enabled)
                .click();
    }
}
