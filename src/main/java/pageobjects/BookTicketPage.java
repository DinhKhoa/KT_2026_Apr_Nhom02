package pageobjects;

import common.Utilities;
import common.components.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookTicketPage extends GeneralPage {
    // Locators
    private final By _ddlDepartDate = By.name("Date");
    private final By _ddlDepartFrom = By.name("DepartStation");
    private final By _ddlArriveAt = By.name("ArriveStation");
    private final By _ddlSeatType = By.name("SeatType");
    private final By _ddlTicketAmount = By.name("TicketAmount");
    private final By _btnBookTicket = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private final By _lblBookResultMessage = By.xpath("//h1[@align='center']");

    // Elements
    public WebElement getDdlDepartDate() {
        return findElement(_ddlDepartDate);
    }

    public WebElement getDdlDepartFrom() {
        return findElement(_ddlDepartFrom);
    }

    public WebElement getDdlArriveAt() {
        return findElement(_ddlArriveAt);
    }

    public WebElement getDdlSeatType() {
        return findElement(_ddlSeatType);
    }

    public WebElement getDdlTicketAmount() {
        return findElement(_ddlTicketAmount);
    }

    public WebElement getBtnBookTicket() {
        return findElement(_btnBookTicket);
    }

    public WebElement getLblBookResultMessage() {
        return findElement(_lblBookResultMessage);
    }

    // Methods
    public void bookTicket(String departDate, String departFrom, String arriveAt, String seatType, String ticketAmount) {
        select(_ddlDepartDate).selectByVisibleText(departDate);
        select(_ddlDepartFrom).selectByVisibleText(departFrom);
        select(_ddlArriveAt).selectByVisibleText(arriveAt);
        select(_ddlSeatType).selectByVisibleText(seatType);
        select(_ddlTicketAmount).selectByVisibleText(ticketAmount);

        click(_btnBookTicket);
    }

    public void bookTicketRandom() {
        selectRandom("Date");
        selectRandom("Depart Station");
        selectRandom("Arrive Station");
        selectRandom("Seat Type");
        selectRandom("Ticket Amount");
        clickBtnBookTicket();
    }

    public String selectRandom(String fieldName) {
        By locator = getLocatorByFieldName(fieldName);

        if (fieldName.toLowerCase().contains("arrive")) {
            Utilities.delay(500);
            wait.until(d -> select(locator).getOptions().size() > 1);
        }

        return selectRandomOption(locator);
    }

    private By getLocatorByFieldName(String fieldName) {
        return switch (fieldName.toLowerCase().replace(" ", "")) {
            case "date", "departdate" -> _ddlDepartDate;
            case "departstation", "departfrom" -> _ddlDepartFrom;
            case "arrivestation", "arriveat" -> _ddlArriveAt;
            case "seattype" -> _ddlSeatType;
            case "ticketamount", "amount" -> _ddlTicketAmount;
            default -> throw new IllegalArgumentException("Field not found: " + fieldName);
        };
    }

    public void clickBtnBookTicket() {
        click(_btnBookTicket);
    }

    public String getDepartDateByIndex(int index) {
        return select(_ddlDepartDate).getOptions().get(index).getText();
    }

    public String selectRandomOption(By locator) {
        org.openqa.selenium.support.ui.Select dropdown = select(locator);
        List<WebElement> options = dropdown.getOptions();
        int startIndex = 1;
        if (options.size() > 1) {
            int index = (int) (Math.random() * (options.size() - startIndex)) + startIndex;
            String originalText = options.get(index).getText();
            dropdown.selectByIndex(index);
            return originalText;
        }
        return dropdown.getFirstSelectedOption().getText();
    }

    public String getBookResultMessage() {
        return getText(_lblBookResultMessage);
    }

    public String getSelectedDepartFrom() {
        return select(_ddlDepartFrom).getFirstSelectedOption().getText();
    }

    public String getSelectedArriveAt() {
        return select(_ddlArriveAt).getFirstSelectedOption().getText();
    }

    public String getSuccessMessage() {
        return getBookResultMessage();
    }

    public String getTicketFieldValue(String columnName) {
        WebTable resultTable = new WebTable(By.xpath("//table[.//th[contains(text(), '" + columnName + "')]]"));
        return resultTable.getCellByRowIndex(1, columnName).getText();
    }
}
