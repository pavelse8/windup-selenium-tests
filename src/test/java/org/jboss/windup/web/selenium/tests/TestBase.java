package org.jboss.windup.web.selenium.tests;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    @BeforeEach
    public void initWebDriver() {
        Configuration.startMaximized=true;
        Configuration.browser = "chrome";
    }

    @AfterEach
    public void AfterTest() {
    }


}
