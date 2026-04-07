package testcases;

import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        // Step 3: Enter valid Email and Password
        // Step 4: Click on "Login" button
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        System.out.println("Expected: " + expectedMsg);
        System.out.println("Actual: " + actualMsg);

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank Username textbox");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 3: User doesn't type any words into "Username" textbox but enter valid information into "Password" textbox
        // Step 4: Click on "Login" button
        loginPage.login("", Constant.PASSWORD);

        String actualMsg = loginPage.getLoginErrorMsg();
        Assert.assertEquals(actualMsg, "There was a problem with your login and/or errors exist in your form.", "Error message is not displayed as expected");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 3: Enter valid Email and invalid Password
        // Step 4: Click on "Login" button
        loginPage.login(Constant.USERNAME, "InvalidPassword123!");

        String actualMsg = loginPage.getLoginErrorMsg();
        Assert.assertEquals(actualMsg, "Invalid username or password. Please try again.", "Error message is not displayed as expected");
    }

    @Test
    public void TC04() {
        System.out.println("TC04 - Login page displays when un-logged User clicks on 'Book ticket' tab");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Book ticket" tab
        LoginPage loginPage = homePage.gotoPage("Book ticket", LoginPage.class);
        String actualUrl = Constant.WEBDRIVER.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("Account/Login.cshtml") || loginPage.isLoginFormDisplayed(), "Login page is not displayed instead of Book ticket page");
    }

    @Test
    public void TC05() {
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);

        // Step 3: Enter valid information into "Username" textbox except "Password" textbox.
        // Step 4: Click on "Login" button
        // Step 5: Repeat step 3 three more times.
        for (int i = 0; i < 4; i++) {
            loginPage.login(Constant.USERNAME, "WrongPassword" + i);
        }

        String actualMsg = loginPage.getLoginErrorMsg();
        Assert.assertTrue(actualMsg.contains("You have used 4 out of 5 login attempts") || actualMsg.contains("Invalid username or password"), "Error message message incorrect");
    }

    @Test
    public void TC06() {
        System.out.println("TC06 - Additional pages display once user logged in");
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 3: Login with valid account
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Assert.assertTrue(homePage.isMenuTabDisplayed("My ticket"), "My ticket tab is not displayed");
        Assert.assertTrue(homePage.isMenuTabDisplayed("Change password"), "Change password tab is not displayed");
        Assert.assertTrue(homePage.isMenuTabDisplayed("Log out"), "Logout tab is not displayed");

        homePage.clickMenuTab("My ticket");
        Assert.assertTrue(Constant.WEBDRIVER.getCurrentUrl().contains("ManageTicket"), "Did not navigate to My ticket page");

        homePage.clickMenuTab("Change password");
        Assert.assertTrue(Constant.WEBDRIVER.getCurrentUrl().contains("ChangePassword"), "Did not navigate to Change password page");
    }

    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");
        // Pre-condition: Create a new account but do not activate it (Mocked via random unactivated email)
        
        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Login" tab
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        
        // Step 3: Enter username and password of account hasn't been activated.
        // Step 4: Click on "Login" button
        loginPage.login(common.Utilities.generateRandomEmail("unactivated"), "Password123");

        String actualMsg = loginPage.getLoginErrorMsg();
        Assert.assertEquals(actualMsg, "Invalid username or password. Please try again.", "Error message incorrect");
    }
}