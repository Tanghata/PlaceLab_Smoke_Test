package placelab.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage {

    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyLoginPage(String host) {
        Assert.assertEquals(this.driver.getCurrentUrl(), host);
        Assert.assertEquals(this.driver.getTitle(), "PlaceLab");
        getLoginPageLogo().isDisplayed();
    }

    private WebElement getLoginPageLogo() {
        WebElement logo = this.driver.findElement(By.xpath("//img[@src='/assets/logo" +
                "-526ea19604d26801aca90fe441f7df4775a24a5d74ae273dbc4af85f42241259.png']"));
        return logo;
    }

    public void enterPassword(final String password) {
        this.driver.findElement(By.name("password")).sendKeys(password);
    }

    public void enterUsername(final String username) {
        this.driver.findElement(By.name("email")).sendKeys(username);
    }

    public void submit() {
        this.driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }
}
