package com.company.jmixpm.ui.screen;

import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.ComboBox;
import io.jmix.masquerade.component.Label;
import io.jmix.masquerade.component.PasswordField;
import io.jmix.masquerade.component.TextField;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends Composite<LoginScreen> {

    @Wire
    private TextField usernameField;

    @Wire
    private PasswordField passwordField;

    @Wire(path = {"loginForm", "loginButton"})
    private Button loginButton;

    @Wire
    private ComboBox localesField;

    @Wire
    private Label welcomeLabel;

    @FindBy(className = "jmix-login-caption")
    private Label welcomeLabelTest;

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public ComboBox getLocalesField() {
        return localesField;
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }

    public Label getWelcomeLabelTest() {
        return welcomeLabelTest;
    }
}
