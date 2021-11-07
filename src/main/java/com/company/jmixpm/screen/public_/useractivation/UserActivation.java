package com.company.jmixpm.screen.public_.useractivation;

import com.company.jmixpm.app.RegistrationService;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.screen.login.LoginScreen;
import com.company.jmixpm.screen.main.MainScreen;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinServletResponse;
import io.jmix.core.security.SecurityContextHelper;
import io.jmix.core.security.SystemAuthenticationToken;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@UiController("UserActivation")
@UiDescriptor("user-activation.xml")
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

    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy;

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

        //loginByPassword(password);

        loginAsTrusted();
    }

    private void loginByPassword(String password) {
        // todo login with password
    }

    // mostly copied from io.jmix.securityui.authentication.LoginScreenSupport
    private void loginAsTrusted() {
        log.info("Login without password");

        // todo login without password
        Authentication authentication = null;

        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        VaadinServletResponse response = VaadinServletResponse.getCurrent();

        sessionAuthenticationStrategy.onAuthentication(authentication, request, response);

        SecurityContextHelper.setAuthentication(authentication);

        screenBuilders.screen(this)
                .withScreenClass(MainScreen.class)
                .withOpenMode(OpenMode.ROOT)
                .build()
                .show();
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