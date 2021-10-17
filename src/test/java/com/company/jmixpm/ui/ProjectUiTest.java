package com.company.jmixpm.ui;

import com.company.jmixpm.JmixPmApplication;
import com.company.jmixpm.ui.extension.ChromeExtension;
import com.company.jmixpm.ui.screen.LoginScreen;
import com.company.jmixpm.ui.screen.MainScreen;
import com.company.jmixpm.ui.screen.ProjectBrowse;
import com.company.jmixpm.ui.screen.ProjectEdit;
import com.company.jmixpm.ui.screen.UserBrowse;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.component.HasActions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Selenide.open;
import static io.jmix.masquerade.Conditions.ENABLED;
import static io.jmix.masquerade.Conditions.VISIBLE;
import static io.jmix.masquerade.Selectors.$j;

@ExtendWith(ChromeExtension.class)
@SpringBootTest(classes = JmixPmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"jmix.ui.testMode=true"})
public class ProjectUiTest {

    @Test
    @DisplayName("Checks project creation")
    public void checkProjectCreation() {
        // open URL of an application
        open("/");

        /* Authorization */
        // obtain UI object of LoginScreen
        LoginScreen loginScreen = $j(LoginScreen.class);

        // setting values
        loginScreen.getUsernameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("dev1");
        loginScreen.getPasswordField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("dev1");

        // login
        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .click();

        /* Project creation */
        ProjectBrowse projectBrowse = $j(MainScreen.class).openProjectBrowse();

        // fill form fields
        ProjectEdit projectEdit = projectBrowse.create();
        projectEdit.getNameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("UI testing");
        projectEdit.getStartDateField()
                .shouldBe(VISIBLE, ENABLED)
                .setDateValue("03/11/2021");
        projectEdit.getEndDateField()
                .shouldBe(VISIBLE, ENABLED)
                .setDateValue("04/11/2021");
        UserBrowse userBrowse = projectEdit.getManagerField()
                .shouldBe(VISIBLE, ENABLED)
                .triggerAction(UserBrowse.class, new HasActions.Action("entityLookup"));
        userBrowse.selectUser("dev1");

        // save project
        projectEdit.commitAndClose();

        // check the display of the new project in the table
        projectBrowse.getProjectsTable()
                .shouldBe(VISIBLE, ENABLED)
                .getRow(Selectors.byText("UI testing"))
                .shouldBe(VISIBLE);
    }
}
