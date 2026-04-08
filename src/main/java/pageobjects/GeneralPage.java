package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class GeneralPage {
    protected final WebDriverWait wait;
    // Locators
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']//strong");
    // Dynamics
    private final String menuTabXpath = "//div[@id='menu']//a[.//span[contains(text(), '%s')]]";

    protected GeneralPage() {
        this.wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(Constant.TIMEOUT));
    }

    protected GeneralPage(int timeout) {
        this.wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(timeout));
    }

    // Elements
    public boolean isMenuTabDisplayed(String tabName) {
        try {
            return getMenuTab(tabName).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement getMenuTab(String tabName) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(menuTabXpath, tabName))));
    }


    public String getPageTitle() {
        return Constant.WEBDRIVER.getTitle();
    }

    // Methods
    public void clickMenuTab(String tabName) {
        this.getMenuTab(tabName).click();
    }

    public String getWelcomeMessage() {
        // Search for welcome message in the main content area instead of a specific hidden tag
        return findElement(By.xpath("//div[@id='content']")).getText();
    }

    // Generic navigation to reduce repetitive methods
    public <T extends GeneralPage> T gotoPage(String tabName, Class<T> pageClass) {
        this.clickMenuTab(tabName);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to navigate to page: " + pageClass.getName(), e);
        }
    }

    // Common Element Interactions
    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void sendKeys(By locator, String value) {
        if (value == null) return;
        sendKeys(findElement(locator), value);
    }

    protected void sendKeys(WebElement element, String value) {
        if (value == null) return;
        scrollToElement(element);
        element.clear();
        element.sendKeys(value);
    }

    protected void click(By locator) {
        click(findElement(locator));
    }

    protected void click(WebElement element) {
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (org.openqa.selenium.WebDriverException e) {
            JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
            js.executeScript("arguments[0].click();", element);
        }
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    protected org.openqa.selenium.support.ui.Select select(By locator) {
        return new org.openqa.selenium.support.ui.Select(findElement(locator));
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
