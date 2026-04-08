package pageobjects;

import org.openqa.selenium.By;

public class RegisterPage extends GeneralPage {

    // Locators
    private final By txtEmail = By.id("email");
    private final By txtPassword = By.id("password");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtPid = By.id("pid");
    private final By btnRegister = By.xpath("//input[@type='submit' and @value='Register']");
    private final By lblRegisterMessage = By.xpath("//div[@id='content']/p[last()]");
    private final By lblPasswordError = By.xpath("//label[@for='password' and @class='validation-error']");
    private final By lblPidError = By.xpath("//label[@for='pid' and @class='validation-error']");

    // Dynamics

    // Methods
    public void register(String email, String password, String confirmPassword, String pid) {
        this.sendKeys(txtEmail, email);
        this.sendKeys(txtPassword, password);
        this.sendKeys(txtConfirmPassword, confirmPassword);
        this.sendKeys(txtPid, pid);
        this.click(btnRegister);
    }

    public String getPasswordError() {
        return getText(lblPasswordError);
    }

    public String getPidError() {
        return getText(lblPidError);
    }

    public String getRegisterMessage() {
        try {
            return getText(lblRegisterMessage);
        } catch (Exception e) {
            return getText(By.id("content"));
        }
    }
}
