package placelab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ReportsPage {

    public WebDriver driver;

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getReport(String reportName) {
        String reportLocation = "//div[@class='query_name']/a[text()='" + reportName + "']";
        return this.driver.findElement(By.xpath(reportLocation));
    }

    public void openReport() {
        this.driver.findElement(By.xpath("//div[@class='query_name']/a[1]")).click();
    }

    public void deleteReport(String reportName) {
        this.driver.findElement(By.xpath("//*[@id='table_queries']/tbody/tr[1]/td[2]/div")).click();
        this.driver.findElement(By.xpath("//*[@id='action-delete']/a/i")).click();
        confirmDelete();
    }

    private void confirmDelete() {
        new WebDriverWait(this.driver, Duration.ofSeconds(5)).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//*[@id='confirm-link']"))).click();
    }

    public String successfulFileDeletion() {
        return this.driver.findElement(By.xpath("//*[@id='alert-place']/div")).getText();
    }
}
