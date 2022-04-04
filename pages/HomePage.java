package placelab.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class HomePage {

    public WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUser() {
        return this.driver.findElement(By.id("user-name")).getText();
    }

    public String getUserRole() {
        return this.driver.findElement(By.id("user-role")).getText();
    }

    public void initiateSinglePlaceSearch () {
        this.driver.findElement(By.xpath("//*[@id='create-menu']")).click();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        this.driver.findElement(By.xpath("//*[@id='singleplacesearch']/a/label")).click();
    }

    public void signOut() {
        this.driver.findElement(By.xpath("//*[@id='actions-nav-item']")).click();
        this.driver.findElement(By.linkText("Sign out")).click();
    }
}
