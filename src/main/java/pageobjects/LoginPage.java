package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends GeneralPage {
    // Locators
    private final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");

    // Elements
    public WebElement getTxtUsername() {
        return findElement(_txtUsername);
    }

    public WebElement getTxtPassword() {
        return findElement(_txtPassword);
    }

    public WebElement getBtnLogin() {
        return findElement(_btnLogin);
    }

    public WebElement getLblLoginErrorMsg() {
        return findElement(_lblLoginErrorMsg);
    }

    // Methods
    public boolean isLoginFormDisplayed() {
        return findElement(_btnLogin).isDisplayed();
    }

    public HomePage login(String username, String password) {
        this.sendKeys(_txtUsername, username);
        this.sendKeys(_txtPassword, password);
        this.click(_btnLogin);
        return new HomePage();
    }

    public String getLoginErrorMsg() {
        return getLblLoginErrorMsg().getText();
    }

    public ForgotPasswordPage gotoForgotPasswordPage() {
        By lnkForgotPassword = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");
        click(lnkForgotPassword);
        return new ForgotPasswordPage();
    }
}