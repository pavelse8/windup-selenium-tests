package org.jboss.windup.web.selenium.pages;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.jboss.windup.web.selenium.tools.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public abstract class CommonProject {

    public static final String WITH_LOGIN_PROPERTY = "withLogin";
    protected WebDriver driver;
    private final String rhamtBaseUrl;
    private final boolean withLogin;
    long LONGTIMEOUT;
    long TIMEOUT;

    //locators for commonPage:
    SelenideElement username = $(By.id("username"));
    SelenideElement password = $(By.id("password"));
    SelenideElement loginButton = $(By.id("kc-login"));
    SelenideElement noProjectWelcome = $(By.tagName("wu-no-projects-welcome"));
    SelenideElement projectWelcome = $(By.className("projects-list"));
    SelenideElement projectLoad = $(By.cssSelector(".activated-item"));
    SelenideElement kcImage = $(By.id("kc-logo-wrapper"));
    //shared locators:
    SelenideElement dropDown = $(By.className("dropdown-toggle"));
    SelenideElement searchContainer = $(By.cssSelector("wu-search"));
    SelenideElement projectIcon = $(By.className("home"));
    SelenideElement activePageList = $(By.cssSelector("ul.list-group"));
    SelenideElement pageTitle = $(By.cssSelector("div.main"));

    public CommonProject()
    {
        Property properties = Property.getInstance();
        rhamtBaseUrl = properties.getDefaultUrl();
        LONGTIMEOUT = properties.getLongTimeout();
        TIMEOUT = properties.getTimeOut();
        withLogin = System.getProperty(WITH_LOGIN_PROPERTY) != null;

        open(rhamtBaseUrl);
        if (withLogin)
        {
            kcImage.waitUntil(exist,TIMEOUT);
            login();
        }
    }

    public void login()
    {
        username.setValue("rhamt");
        password.setValue("password");
        loginButton.click();
    }

    public String getRhamtBaseUrl() {
        return rhamtBaseUrl;
    }

    public String checkURL() {
        return WebDriverRunner.getWebDriver().getCurrentUrl();
    }

    public void waitForProjectList()
    {
        projectWelcome.waitUntil(exist, LONGTIMEOUT);

    }

    public void waitForNoProjectWelcomePage()
    {
        noProjectWelcome.waitUntil(exist, LONGTIMEOUT);
    }

    public void waitForProjectLoad()
    {
        projectLoad.waitUntil(exist, LONGTIMEOUT);
    }

    /**
     * from the project list screen this will navigate to whichever project is given by the name
     * @param projName the exact string form of the project name
     * @return true if the project is found
     */

    public boolean navigateProject(String projName) {
        waitForProjectList();
        SelenideElement title = $(By.xpath("//*[@class='list-group-item-heading' and contains(.,'"+ projName+"')]"));
        try {
            title.waitUntil(Condition.exist, TIMEOUT);
            title.click();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void navigateTo(int index) throws InterruptedException {
        ArrayList tabs = new ArrayList(WebDriverRunner.getWebDriver().getWindowHandles());
        if (tabs.size() <= index) {
            Thread.sleep(1000); //TODO Изменить метод, необходимо избавиться от ожидания
            navigateTo(index);
        }
        else {
            WebDriverRunner.getWebDriver().switchTo().window((String)tabs.get(index));
            WebDriverRunner.getWebDriver().switchTo().defaultContent();
        }
    }

    /**
     * this method will take the driver back to the previous page
     */
    public void goBack() {
        WebDriverRunner.getWebDriver().navigate().back();
    }

    public String headerTitle() {
        return WebDriverRunner.getWebDriver().getTitle(); //TODO Падает из за ошибки приложения, исправить позже
    }

    /**
     * this will collect the information from the project dropdown
     * @return "Project" a new line, and the project name
     */

    public String dropDownInfo() {
        dropDown.waitUntil(Condition.exist, TIMEOUT);
        return dropDown.getText();
    }

    public void clickProjectsIcon() {
        projectIcon.waitUntil(Condition.exist,TIMEOUT);
        projectIcon.click();
    }

    /**
     * can type in deleteAppDialog for the delete box and confirmDialog for save and
     * running before the packages are loaded
     *
     * @param s
     *            the string for the type of dialog box
     * @return true if the popup is removed
     */
    public boolean popupRemoved(String s) {
        SelenideElement dialog = $(By.cssSelector("div#" + s + ".modal.fade.in"));
        if (dialog.isDisplayed()) {return false;} else {return true;}
    }

    /**
     * returns the name of the page that the driver is on
     *
     * @return
     */
    public String pageTitle() {
        pageTitle.waitUntil(Condition.enabled, TIMEOUT);
        return pageTitle.getText();
    }
}


