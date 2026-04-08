package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage extends GeneralPage {
    private final By txtEmail = By.id("email");
    private final By btnSendInstructions = By.xpath("//input[@type='submit' and @value='Send Instructions']");

    // Elements
    public WebElement getTxtEmail() {
        return findElement(txtEmail);
    }

    public WebElement getBtnSendInstructions() {
        return findElement(btnSendInstructions);
    }

    // Methods
    public void submitEmail(String email) {
        sendKeys(txtEmail, email);
        click(btnSendInstructions);
    }
}
