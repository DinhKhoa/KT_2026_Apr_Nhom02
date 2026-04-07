package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ChangePasswordPage extends GeneralPage {
    // Locators
    private final By txtCurrentPassword = By.id("currentPassword");
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By btnChangePassword = By.xpath("//input[@type='submit' and @value='Change Password']");
    private final By lblChangeMessage = By.xpath("//p[contains(@class, 'message')]");

    // Elements
    public WebElement getTxtCurrentPassword() {
        return findElement(txtCurrentPassword);
    }

    public WebElement getTxtNewPassword() {
        return findElement(txtNewPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return findElement(txtConfirmPassword);
    }

    public WebElement getBtnChangePassword() {
        return findElement(btnChangePassword);
    }

    public WebElement getLblChangeMessage() {
        return findElement(lblChangeMessage);
    }

    // Methods
    public void changePassword(String currentPwd, String newPwd, String confirmPwd) {
        sendKeys(txtCurrentPassword, currentPwd);
        sendKeys(txtNewPassword, newPwd);
        sendKeys(txtConfirmPassword, confirmPwd);
        click(btnChangePassword);
    }

    public String getChangeMessage() {
        return getText(lblChangeMessage);
    }
}
