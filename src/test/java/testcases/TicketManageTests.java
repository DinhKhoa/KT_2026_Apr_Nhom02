package testcases;

import common.Constant;
import common.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.*;

public class TicketManageTests extends BaseTest {
    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");
        // Pre-condition: Create and activate a new account (Mocked via existing account)

        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with a valid account
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Book ticket" tab
        BookTicketPage bookTicketPage = homePage.gotoPage("Book ticket", BookTicketPage.class);

        // Step 4: Select a "Depart date" from the list
        String departDate = bookTicketPage.selectRandom("Date");
        // Step 5: Select "Sài Gòn" for "Depart from" and "Nha Trang" for "Arrive at" sequentially
        String departFrom = bookTicketPage.selectStationCustom("Depart From", "Sài Gòn");
        Utilities.delay(300);
        String arriveAt = bookTicketPage.selectStationCustom("Arrive At", "Nha Trang");
        // Step 6: Select "Soft bed with air conditioner" for "Seat type"
        String seatType = bookTicketPage.selectRandom("Seat Type");
        // Step 7: Select "1" for "Ticket amount"
        String amount = "1";

        Utilities.delay(500);

        // Step 8: Click on "Book ticket" button
        bookTicketPage.clickBtnBookTicket();

        String ticketId = Utilities.getIDFromURL(Constant.WEBDRIVER.getCurrentUrl());
        System.out.println("Depart from: " + departFrom);
        System.out.println("Arrive at: " + arriveAt);
        System.out.println("Booked Ticket ID: " + ticketId);

        String successMsg = bookTicketPage.getSuccessMessage();
        Assert.assertEquals(successMsg, "Ticket booked successfully!", "Success message is wrong");

        Assert.assertEquals(bookTicketPage.getTicketFieldValue("Depart Station"), departFrom, "Depart station wrong");
        Assert.assertEquals(bookTicketPage.getTicketFieldValue("Arrive Station"), arriveAt, "Arrive station wrong");
        Assert.assertEquals(bookTicketPage.getTicketFieldValue("Seat Type"), seatType, "Seat type wrong");
        Assert.assertEquals(bookTicketPage.getTicketFieldValue("Amount"), amount, "Amount wrong");
        Assert.assertEquals(bookTicketPage.getTicketFieldValue("Depart Date"), departDate, "Depart date wrong");

        // Cancel to be able to book other tickets without the 10-ticket limit
        MyTicketPage myTicketPage = bookTicketPage.gotoPage("My ticket", MyTicketPage.class);
        myTicketPage.cancelTicket(ticketId);
        Assert.assertFalse(myTicketPage.isTicketExist(ticketId), "The ticket with ID " + ticketId + " was not cancelled correctly");
    }

    @Test
    public void TC15() {
        System.out.println("TC15 - User can open 'Book ticket' page by clicking on 'Book ticket' link in 'Train timetable' page");
        // Pre-condition: Create and activate a new account

        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with a valid account
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Timetable" tab
        TimetablePage timetablePage = homePage.gotoPage("Timetable", TimetablePage.class);

        // Step 4: Click on a "book ticket" link for Huế to Sài Gòn
        String[] route = timetablePage.clickBookTicketOrDefaultRandom("Huế", "Sài Gòn");
        String expectedDepart = route[0];
        String expectedArrive = route[1];
        Utilities.delay(500);

        BookTicketPage bookTicketPage = new BookTicketPage();

        Utilities.delay(1000);

        String actualDepart = bookTicketPage.getSelectedDepartFrom();
        String actualArrive = bookTicketPage.getSelectedArriveAt();

        System.out.println("Expected Depart: " + expectedDepart);
        System.out.println("Actual Depart: " + actualDepart);
        System.out.println("Expected Arrive: " + expectedArrive);
        System.out.println("Actual Arrive: " + actualArrive);

        Assert.assertEquals(actualDepart, expectedDepart, "Depart station is incorrect on Book Ticket page");
        Assert.assertEquals(actualArrive, expectedArrive, "Arrive station is incorrect on Book Ticket page");
    }

    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket using Ticket ID");
        // Pre-condition: Create and activate a new account (Mocked via existing account)

        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with a valid account
        LoginPage loginPage = homePage.gotoPage("Login", LoginPage.class);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Book a ticket
        BookTicketPage bookTicketPage = homePage.gotoPage("Book ticket", BookTicketPage.class);

        String departFrom = bookTicketPage.selectRandom("Depart From");
        String arriveAt = bookTicketPage.selectRandom("Arrive At");
        String departDate = bookTicketPage.getDepartDateByIndex(5);
        bookTicketPage.bookTicket(departDate, departFrom, arriveAt, "Soft bed", "1");

        String ticketId = Utilities.getIDFromURL(Constant.WEBDRIVER.getCurrentUrl());
        System.out.println("ID to cancel: " + ticketId);

        // Step 4: Click on "My ticket" tab
        MyTicketPage myTicketPage = homePage.gotoPage("My ticket", MyTicketPage.class);

        // Step 5: Click on "Cancel" button of ticket which user want to cancel.
        // Step 6: Click on "OK" button on Confirmation message "Are you sure?"
        myTicketPage.cancelTicket(ticketId);

        Assert.assertFalse(myTicketPage.isTicketExist(ticketId), "The ticket with ID " + ticketId + " was not cancelled correctly");
    }
}
