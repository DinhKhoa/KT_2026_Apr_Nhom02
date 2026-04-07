package pageobjects;

import common.Constant;
import common.components.WebTable;
import org.openqa.selenium.By;

public class MyTicketPage extends GeneralPage {
    // Locators
    private final By _tblRows = By.xpath("//table[contains(@class, 'MyTable')]//tr");
    private final WebTable myTicketTable = new WebTable(By.xpath("//table[contains(@class, 'MyTable')]"));

    // Dynamics
    private final String rowXpath = "//table[contains(@class, 'MyTable')]//tr[td[input[contains(@onclick,'%s')]]]";

    // Elements
    // Methods
    public void cancelTicket(String rowId) {
        myTicketTable.getButtonInRow(rowId, "Cancel").click();
        Constant.WEBDRIVER.switchTo().alert().accept();
        waitForInvisibility(By.xpath(String.format(rowXpath, rowId)));
    }

    public boolean isTicketExist(String ticketId) {
        try {
            return Constant.WEBDRIVER.findElements(By.xpath(String.format(rowXpath, ticketId))).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
