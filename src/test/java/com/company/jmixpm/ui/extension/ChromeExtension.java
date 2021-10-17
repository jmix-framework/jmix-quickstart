package com.company.jmixpm.ui.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class ChromeExtension implements BeforeEachCallback, AfterEachCallback {

    private BrowserWebDriverContainer browser;

    @Override
    public void beforeEach(ExtensionContext context) {
        browser = new BrowserWebDriverContainer()
                .withCapabilities(new ChromeOptions());
        browser.start();
        WebDriverRunner.setWebDriver(browser.getWebDriver());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Selenide.closeWebDriver();
        browser.stop();
    }
}

