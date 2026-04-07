package pageobjects;

import common.Constant;
import common.components.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class TimetablePage extends GeneralPage {
    // Locators
    private final By _allBookTicketLinks = By.xpath("//table[contains(@class, 'MyTable')]//tr[td and .//a[contains(@href, 'BookTicket')]]");
    private final WebTable tbTimetable = new WebTable(By.xpath("//table[contains(@class, 'MyTable')]"));

    // Methods
    public BookTicketPage clickBookTicketLink(String depart, String arrive) {
        String xpath = "//table[contains(@class, 'MyTable')]//tr[td[contains(text(), '%s')] and td[contains(text(), '%s')]]//a[contains(@href, 'BookTicket')]";
        click(By.xpath(String.format(xpath, depart, arrive)));
        return new BookTicketPage();
    }

    public String[] clickBookTicketOrDefaultRandom(String targetDepart, String targetArrive) {
        String xpath = "//table[contains(@class, 'MyTable')]//tr[td[contains(text(), '%s')] and td[contains(text(), '%s')]]//a[contains(@href, 'BookTicket')]";
        List<WebElement> routes = Constant.WEBDRIVER.findElements(By.xpath(String.format(xpath, targetDepart, targetArrive)));
        WebElement rowToClick;

        if (!routes.isEmpty()) {
            rowToClick = routes.get(0).findElement(By.xpath("./ancestor::tr"));
        } else {
            List<WebElement> allRows = Constant.WEBDRIVER.findElements(_allBookTicketLinks);
            if (allRows.isEmpty()) {
                throw new RuntimeException("No book ticket links found in timetable.");
            }
            rowToClick = allRows.get(new Random().nextInt(allRows.size()));
        }

        String expectedDepart = tbTimetable.getCellFromRow(rowToClick, "Depart Station").getText();
        String expectedArrive = tbTimetable.getCellFromRow(rowToClick, "Arrive Station").getText();

        WebElement bookBtn = rowToClick.findElement(By.xpath(".//a[contains(@href, 'BookTicket')]"));
        click(bookBtn);

        return new String[]{expectedDepart, expectedArrive};
    }
}
