package com.company.jmixpm.screen.public_.register;

import com.company.jmixpm.app.RegistrationService;
import com.company.jmixpm.entity.User;
import io.jmix.email.EmailException;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserRegistration")
@UiDescriptor("user-registration.xml")
public class UserRegistration extends Screen {

    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private Form form;
    @Autowired
    private TextField<String> lastNameField;
    @Autowired
    private TextField<String> firstNameField;
    @Autowired
    private TextField<String> emailField;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Button registerButton;

    private static final Logger log = LoggerFactory.getLogger(UserRegistration.class);

    @Subscribe("backToLogin")
    public void onBackToLoginClick(Button.ClickEvent event) {
        // todo go to login screen
    }

    @Subscribe("register")
    public void onRegister(Action.ActionPerformedEvent event) {
        if (!validateFields()) {
            return;
        }

        User user = registrationService.registerNewUser(emailField.getValue(), firstNameField.getValue(), lastNameField.getValue());

        String activationToken = registrationService.generateRandomActivationToken();

        registrationService.saveActivationToken(user, activationToken);

        try {
            registrationService.sendActivationEmail(user);
        } catch (EmailException e) {
            log.error("Error", e);
            notifications.create(Notifications.NotificationType.ERROR)
                    .withDescription("Failed to send registration email. Sorry for inconvenience.")
                    .show();
            return;
        }

        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withDescription("User registered successfully. Check your email inbox.")
                .show();

        form.setEditable(false);
        registerButton.setEnabled(false);
    }

    public boolean validateFields() {
        ValidationErrors validationErrors = screenValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            screenValidation.showValidationErrors(this, validationErrors);
            return false;
        }

        String email = emailField.getValue();

        if (registrationService.checkUserAlreadyExist(email)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withDescription("User with this email already exists")
                    .withPosition(Notifications.Position.MIDDLE_CENTER)
                    .show();
            return false;
        }

        return true;
    }
}