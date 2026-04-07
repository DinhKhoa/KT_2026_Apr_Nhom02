package common.components;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebTable {
    private final By tableLocator;
    private final WebDriverWait wait;

    public WebTable(By tableLocator) {
        this.tableLocator = tableLocator;
        this.wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(Constant.TIMEOUT));
    }

    private WebElement getTable() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(this.tableLocator));
    }

    public int getColumnIndex(String columnName) {
        // Find how many th are before our target th, then add 1 to get the exact index
        String xpath = ".//th[contains(text(), '" + columnName + "')]/preceding-sibling::th";
        try {
            return getTable().findElements(By.xpath(xpath)).size() + 1;
        } catch (Exception e) {
             throw new RuntimeException("Column '" + columnName + "' not found in table.");
        }
    }

    /**
     * Gets a cell where the row contains a unique identifier (like an ID in text or an onclick button)
     */
    public WebElement getCellByRowId(String rowId, String columnName) {
        int colIndex = getColumnIndex(columnName);
        String rowXpath = ".//tr[td[input[contains(@onclick,'" + rowId + "')]] or td[contains(text(),'" + rowId + "')]]";
        WebElement row = getTable().findElement(By.xpath(rowXpath));
        return row.findElement(By.xpath("./td[" + colIndex + "]"));
    }

    /**
     * Gets a cell by exact row index (excluding header rows)
     */
    public WebElement getCellByRowIndex(int rowIndex, String columnName) {
        int colIndex = getColumnIndex(columnName);
        WebElement row = getTable().findElement(By.xpath(".//tr[not(th)][" + rowIndex + "]"));
        return row.findElement(By.xpath("./td[" + colIndex + "]"));
    }
    
    /**
     * Finds a button inside a specific row
     */
    public WebElement getButtonInRow(String rowId, String buttonText) {
        String rowXpath = ".//tr[td[input[contains(@onclick,'" + rowId + "')]] or td[contains(text(),'" + rowId + "')]]";
        WebElement row = getTable().findElement(By.xpath(rowXpath));
        return row.findElement(By.xpath(".//input[@value='" + buttonText + "']"));
    }

    /**
     * Gets a cell from a specific row WebElement by column name dynamically
     */
    public WebElement getCellFromRow(WebElement row, String columnName) {
        int colIndex = getColumnIndex(columnName);
        return row.findElement(By.xpath("./td[" + colIndex + "]"));
    }
}
