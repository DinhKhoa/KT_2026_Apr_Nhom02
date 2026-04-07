package testcases;

import common.Constant;
import common.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.*;

public class PasswordTests extends BaseTest {
    @Test
    public void TC09() {
        System.out.println("TC09 - User can change password");
        // Pre-condition: Create and activate a new account (Mocked via existing account)
        
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with valid account
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Change Password" tab
        ChangePasswordPage changePasswordPage = loginPage.gotoPage("Change password", ChangePasswordPage.class);
        String newPass = Utilities.generateRandomString(12);
        
        // Step 4: Enter valid value into all fields.
        // Step 5: Click on "Change Password" button
        changePasswordPage.changePassword(Constant.PASSWORD, newPass, newPass);

        String actualMsg = changePasswordPage.getChangeMessage();
        
        // Revert password
        changePasswordPage.changePassword(newPass, Constant.PASSWORD, Constant.PASSWORD);
        
        Assert.assertEquals(actualMsg, "Your password has been updated!", "Message is not displayed as expected");
    }

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        // Pre-condition: Create and activate a new account (Mocked via existing account)
        
        // Step 1: Navigate to QA Railway Login page
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 2: Click on "Forgot Password page" link
        ForgotPasswordPage forgotPwdPage = loginPage.gotoForgotPasswordPage();
        
        // Step 3: Enter the email address of the created account in Pre-condition
        // Step 4: Click on "Send Instructions" button
        forgotPwdPage.submitEmail(Constant.USERNAME);

        // Step 5: Open mailbox and click on reset password link (Mocked by direct navigation)
        Constant.WEBDRIVER.get(Constant.RAILWAY_URL + "/Account/PasswordReset.cshtml");
        
        if (Constant.WEBDRIVER.getTitle().contains("Error") || Constant.WEBDRIVER.getPageSource().contains("Server Error")) {
            Assert.assertTrue(false, "Error page displays instead of Password Reset page");
        }

        PasswordResetPage resetPage = new PasswordResetPage();
        String tempPass = Utilities.generateRandomString(12);
        
        // Step 6: Enter new passwords and remove the Password Reset Token
        // Step 7: Click "Reset Password" button
        resetPage.resetPassword(tempPass, tempPass, "");

        String formMsg = resetPage.getFormMessage();
        String tokenErr = resetPage.getTokenError();

        Assert.assertEquals(formMsg, "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.", "Form error message incorrect");
        Assert.assertEquals(tokenErr, "The password reset token is invalid.", "Token error message incorrect");
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        // Pre-condition: Create and activate a new account (Mocked via existing account)
        
        // Step 1: Navigate to QA Railway Login page
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 2: Click on "Forgot Password page" link
        ForgotPasswordPage forgotPwdPage = loginPage.gotoForgotPasswordPage();
        
        // Step 3: Enter the email address of the created account in Pre-condition
        // Step 4: Click on "Send Instructions" button
        forgotPwdPage.submitEmail(Constant.USERNAME);

        // Step 5: Open mailbox and click on reset password link (Mocked by direct navigation)
        Constant.WEBDRIVER.get(Constant.RAILWAY_URL + "/Account/PasswordReset.cshtml");
        
        if (Constant.WEBDRIVER.getTitle().contains("Error") || Constant.WEBDRIVER.getPageSource().contains("Server Error")) {
            Assert.assertTrue(false, "Error page displays instead of Password Reset page");
        }

        PasswordResetPage resetPage = new PasswordResetPage();
        
        // Step 6: Enter different values for password fields
        // Step 7: Click "Reset Password" button
        resetPage.resetPassword(Utilities.generateRandomString(12), "Mismatch" + Utilities.generateRandomString(5), "fake_token_" + Utilities.generateRandomString(5));

        String formMsg = resetPage.getFormMessage();
        String confirmErr = resetPage.getConfirmPasswordError();

        Assert.assertEquals(formMsg, "Could not reset password. Please correct the errors and try again.", "Form message incorrect");
        Assert.assertEquals(confirmErr, "The password confirmation did not match the new password.", "Confirm password error incorrect");
    }
}
