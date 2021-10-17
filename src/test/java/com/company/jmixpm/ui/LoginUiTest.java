package com.company.jmixpm.ui;

import com.company.jmixpm.ui.screen.LoginScreen;
import io.jmix.masquerade.Conditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static io.jmix.masquerade.Conditions.EDITABLE;
import static io.jmix.masquerade.Conditions.ENABLED;
import static io.jmix.masquerade.Conditions.VISIBLE;
import static io.jmix.masquerade.Conditions.caption;
import static io.jmix.masquerade.Selectors.$j;

public class LoginUiTest {

    // The application must be running on the url = http://localhost:8080
    // User can change the base url using the 'selenide.baseUrl' variable in build.gradle file
    @Test
    @DisplayName("Checks login screen")
    public void login() {
        // open URL of an application
        open("/");

        // obtain UI object of LoginScreen
        LoginScreen loginScreen = $j(LoginScreen.class);

        // fluent asserts
        loginScreen.getUsernameField()
                .shouldBe(EDITABLE)
                .shouldBe(ENABLED);

        // setting an empty value
        loginScreen.getUsernameField()
                .setValue("")
                .shouldBe(Conditions.value(""));

        // setting values
        loginScreen.getUsernameField().setValue("admin");
        loginScreen.getPasswordField().setValue("admin");

        // fluent asserts
        loginScreen.getWelcomeLabelTest()
                .shouldBe(VISIBLE);

        // fluent asserts
        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .shouldHave(caption("Submit"));

        $j("loginForm").shouldBe(VISIBLE);

        loginScreen.getLoginButton().click();
    }
}
