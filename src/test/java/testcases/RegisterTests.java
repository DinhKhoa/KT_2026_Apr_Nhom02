package testcases;

import common.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.RegisterPage;

public class RegisterTests extends BaseTest {
    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoPage("Register", RegisterPage.class);
        String newEmail = Utilities.generateRandomEmail("newEmail");
        String newPass = Utilities.generateRandomString(12);
        String pid = Utilities.generateRandomString(9);

        // Step 3: Enter valid information into all fields
        // Step 4: Click on "Register" button
        registerPage.register(newEmail, newPass, newPass, pid);

        String actualMsg = registerPage.getRegisterMessage();
        Assert.assertEquals(actualMsg, "Registration Confirmed! You can now log in to the site.", "Success message is not displayed as expected");
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - User can't create account with 'Confirm password' is not the same with 'Password'");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoPage("Register", RegisterPage.class);
        String newEmail = Utilities.generateRandomEmail("mismatch");
        String newPass = Utilities.generateRandomString(12);
        String misMatchPass = Utilities.generateRandomString(11);
        String pid = Utilities.generateRandomString(9);

        // Step 3: Enter valid information into all fields except "Confirm password" is not the same with "Password"
        // Step 4: Click on "Register" button
        registerPage.register(newEmail, newPass, misMatchPass, pid);

        String actualMsg = registerPage.getRegisterMessage();
        Assert.assertEquals(actualMsg, "There're errors in the form. Please correct the errors and try again.", "Error message is not displayed as expected");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoPage("Register", RegisterPage.class);
        String validEmail = Utilities.generateRandomEmail("tc11");
        
        // Step 3: Enter valid email address and leave other fields empty
        // Step 4: Click on "Register" button
        registerPage.register(validEmail, "", "", "");

        String formMsg = registerPage.getRegisterMessage();
        String pwdErr = registerPage.getPasswordError();
        String pidErr = registerPage.getPidError();

        Assert.assertEquals(formMsg, "There're errors in the form. Please correct the errors and try again.", "Form message incorrect");
        Assert.assertEquals(pwdErr, "Invalid password length", "Password error incorrect");
        Assert.assertEquals(pidErr, "Invalid ID length", "PID error incorrect");
    }
}
