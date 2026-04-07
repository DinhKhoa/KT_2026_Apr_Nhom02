package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PasswordResetPage extends GeneralPage {
    // Locators
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken");
    private final By btnResetPassword = By.xpath("//input[@type='submit' and @value='Reset Password']");
    private final By lblFormMessage = By.xpath("//p[contains(@class, 'message')]");
    private final By lblTokenError = By.xpath("//label[@for='resetToken' and @class='validation-error']");
    private final By lblConfirmPasswordError = By.xpath("//label[@for='confirmPassword' and @class='validation-error']");

    // Elements
    public WebElement getTxtNewPassword() {
        return findElement(txtNewPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return findElement(txtConfirmPassword);
    }

    public WebElement getTxtResetToken() {
        return findElement(txtResetToken);
    }

    public WebElement getBtnResetPassword() {
        return findElement(btnResetPassword);
    }

    public WebElement getLblFormMessage() {
        return findElement(lblFormMessage);
    }

    public WebElement getLblTokenError() {
        return findElement(lblTokenError);
    }

    public WebElement getLblConfirmPasswordError() {
        return findElement(lblConfirmPasswordError);
    }

    // Methods
    public void resetPassword(String newPwd, String confirmPwd, String token) {
        sendKeys(txtNewPassword, newPwd);
        sendKeys(txtConfirmPassword, confirmPwd);
        sendKeys(txtResetToken, token);
        click(btnResetPassword);
    }

    public String getFormMessage() {
        return getText(lblFormMessage);
    }

    public String getTokenError() {
        return getText(lblTokenError);
    }

    public String getConfirmPasswordError() {
        return getText(lblConfirmPasswordError);
    }
}
