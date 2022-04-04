package placelab.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SinglePlaceSearchPage {

    public WebDriver driver;
    private String newSinglePlaceSearchUrl = "https://demo.placelab.com/places/single_place_searches/new";

    public SinglePlaceSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifySinglePlaceSearchPage() {
        Assert.assertEquals(this.driver.getCurrentUrl(), newSinglePlaceSearchUrl);
    }

    public String getSinglePlaceSearchTitle() {
        return this.driver.findElement(By.xpath("//*[@id='single_poi_dialog']/div[1]/div")).getText();
    }

    public void enterReportName(String reportName) {
        this.driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div/div[2]/form/div[1]/div/input"))
                .sendKeys(reportName);
    }

    public void enterPlaceName(String placeName) {
        this.driver.findElement(By.xpath("//*[@id='single_text']")).sendKeys(placeName);
    }

    private void locationDropdownMenu() {
        new WebDriverWait(this.driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//ul[@class='typeahead dropdown-menu']"))).click();
    }

    public void enterLocation(final String location) {
        this.driver.findElement(By.xpath("//*[@id='location_name']")).sendKeys(location);
        locationDropdownMenu();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void confirmLocation() {
        this.driver.findElement(By.xpath("/html/body/div[10]/div[3]/div/button[1]")).click();
    }

    public boolean createReportButtonEnabled() {
        return this.driver.findElement(By.xpath("//button[@class='btn large-btn run-btn run-all-btn']"))
                .isEnabled();
    }

    public void createReport() {
        this.driver.findElement(By.xpath("//button[@class='btn large-btn run-btn run-all-btn']")).click();
    }
}
