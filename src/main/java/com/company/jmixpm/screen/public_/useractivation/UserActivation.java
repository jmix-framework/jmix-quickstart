package com.company.jmixpm.screen.public_.useractivation;

import com.company.jmixpm.app.RegistrationService;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.screen.login.LoginScreen;
import io.jmix.securityui.authentication.AuthDetails;
import io.jmix.securityui.authentication.LoginScreenSupport;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.screen.*;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

@UiController("UserActivation")
@UiDescriptor("user-activation.xml")
@Route(value = "activate", root = true)
public class UserActivation extends Screen {

    private static final Logger log = LoggerFactory.getLogger(UserActivation.class);

    @Autowired
    private Label<String> welcomeLabel;
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private Label<String> notFoundLabel;
    @Autowired
    private Form form;
    @Autowired
    private Button activateButton;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private LoginScreenSupport loginScreenSupport;
    @Autowired
    private Notifications notifications;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private LinkButton returnToLoginScreen;

    private User user;
    private boolean initialized = false;

    @Subscribe
    public void onUrlParamsChanged(UrlParamsChangedEvent event) {
        String receivedActivationToken = event.getParams().get("token");

        if (StringUtils.isNotEmpty(receivedActivationToken)) {
            user = registrationService.loadUserByActivationToken(receivedActivationToken);
        } else {
            user = null;
        }

        updateUI();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (!initialized) {
            updateUI();
        }
    }

    private void updateUI() {
        boolean success = user != null;

        welcomeLabel.setVisible(success);
        form.setVisible(success);
        activateButton.setVisible(success);

        notFoundLabel.setVisible(!success);
        returnToLoginScreen.setVisible(!success);

        if (user != null) {
            welcomeLabel.setValue(messageBundle.formatMessage("finishActivation", user.getFirstName(), user.getLastName()));
        }

        initialized = true;
    }

    @Subscribe("activateButton")
    public void onActivateButtonClick(Button.ClickEvent event) {
        if (!validateFields()) {
            return;
        }

        String password = passwordField.getValue();
        registrationService.activateUser(user, password);

        try {
            // also redirects to main screen
            loginScreenSupport.authenticate(AuthDetails.of(user.getUsername(), password), this);
        } catch (AuthenticationException e) {
            log.info("Login failed", e);
            notifications.create(Notifications.NotificationType.ERROR)
                    .withDescription("Activation failed")
                    .show();
        }
    }

    public boolean validateFields() {
        ValidationErrors validationErrors = screenValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            screenValidation.showValidationErrors(this, validationErrors);
            return false;
        }
        return true;
    }

    @Subscribe("returnToLoginScreen")
    public void onReturnToLoginScreenClick(Button.ClickEvent event) {
        screenBuilders.screen(this)
                .withScreenClass(LoginScreen.class)
                .withOpenMode(OpenMode.ROOT)
                .build()
                .show();
    }
}