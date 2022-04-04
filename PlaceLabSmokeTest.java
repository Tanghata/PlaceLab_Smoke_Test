package placelab.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import placelab.pages.*;
import placelab.utilities.WebDriverSetup;

import java.time.Duration;

public class PlaceLabSmokeTest {
    public WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private String host = System.getProperty("host");
    private String homePageUrl = System.getProperty("homepage");
    private String user = "Damir Memic";
    private String username = System.getProperty("username");
    private String password = System.getProperty("password");
    private String reportName = System.getProperty("reportName");
    private String placeName = System.getProperty("placeName");
    private String location = System.getProperty("location");
    private String newReportPageTitle = "Create Single Place Search Report";
    private LoginPage loginPage;
    private HomePage homePage;
    private SinglePlaceSearchPage singlePlaceSearchPage;
    private ReportsPage reportsPage;
    private CreatedReportPage createdReportPage;

    //Specify the driver and browser that will be used for this scenario
    @Parameters({"browser"})

    @BeforeSuite(alwaysRun = true)
    public void initDriver(String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        singlePlaceSearchPage = new SinglePlaceSearchPage(driver);
        reportsPage = new ReportsPage(driver);
        createdReportPage = new CreatedReportPage(driver);
    }

    @BeforeTest(alwaysRun = true, groups = {"Positive, Negative"},
            description = "Verify that user is able to open PlaceLab demo website.")

    public void openWebsite() {
        driver.navigate().to(host); //Go to required website
        loginPage.verifyLoginPage(host); //Validate that user is redirected to the right page
    }

    //LOGIN PAGE TEST CASES
    @Test(priority = 1, groups = {"Positive"}, description = "This test verifies that user is able to " +
            "log in to PlaceLab with valid credentials.", suiteName = "Smoke Test")
    public void loginToPlaceLab() {
        //Fill out login parameters, and then click on the login button
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.submit();

        //Validate that specific user is successfully logged in
        Assert.assertEquals(driver.getCurrentUrl(), homePageUrl);
        assert (homePage.getUser().contains(user));
        softAssert.assertEquals(homePage.getUserRole(), "Group Admin",
                "Warning: Logged user has incorrect user role!");
        softAssert.assertAll();
    }

    //HOMEPAGE TEST CASES
    @Test(priority = 2, groups = {"Positive"}, description = "This test verifies that user is able to " +
            "initiate creation of the 'Single Place Search' report.", suiteName = "Smoke Test")
    public void openSinglePlaceSearchPage() {
        //Click on the 'Create Report' dropdown menu, and the click on the 'Single Place Search'
        homePage.initiateSinglePlaceSearch();

        //Validate that user is redirected to the right page
        try {
            assert (singlePlaceSearchPage.getSinglePlaceSearchTitle().contains(newReportPageTitle));
            singlePlaceSearchPage.verifySinglePlaceSearchPage();
        } catch (Exception e) {
            throw new IllegalArgumentException("User has been redirected to the wrong page!");
        }
    }

    //SINGLE PLACE SEARCH PAGE TEST CASES
    @Test(priority = 3, groups = {"Positive"}, description = "This test verifies that user is able to enter " +
            "required information in blank fields on the Single Place Search page.", suiteName = "Smoke Test")
    public void submitRequiredInfo() {
        //Enter required information into blank fields on the Single Place Search page
        singlePlaceSearchPage.enterReportName(reportName);
        singlePlaceSearchPage.enterPlaceName(placeName);
        singlePlaceSearchPage.enterLocation(location);
    }

    @Test(priority = 4, groups = {"Positive"}, description = "This test verifies that data generated on the " +
            "basis of user input is correct on the Single Place Search page.", suiteName = "Smoke Test")
    public void verifyGeneratedData() {
        //Verify that location is correct
        singlePlaceSearchPage.confirmLocation();
    }

    @Test(priority = 5, groups = {"Positive"}, description = "This test verifies that 'Create Report' button " +
            "is enabled and that the user is able to create a report.", suiteName = "Smoke Test")
    public void createReport() {
        Assert.assertEquals(singlePlaceSearchPage.createReportButtonEnabled(), true,
                "Critical error: 'Create Report' button remains disabled!");
        singlePlaceSearchPage.createReport();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    //REPORTS PAGE TEST CASES
    @Test(priority = 6, groups = {"Positive"}, description = "This test verifies that the user has " +
            "successfully created a report.", suiteName = "Smoke Test")
    public void verifyCreatedReport() {
        WebElement report = reportsPage.getReport(reportName);
        Assert.assertEquals(report.getText(), reportName, "Error: Report has not been created!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo.placelab.com/queries",
                "Error: User has been navigated to the wrong page!");
    }

    @Test(priority = 7, groups = {"Positive"}, description = "This test verifies that user is able to open " +
            "the report he/she has just created.", suiteName = "Smoke Test")
    public void openCreatedReport() {
        reportsPage.openReport();
        //Validate that the user has been redirected to the right report page
        Assert.assertEquals(createdReportPage.getCreatedReportPageTitle(), reportName,
                "User has been redirected to the wrong report page!");
    }

    //CREATED REPORT PAGE TEST CASES
    @Test(priority = 8, groups = {"Positive"}, description = "This test verifies that key widgets are " +
            "present in the report user has just created.", suiteName = "Smoke Test")
    public void areKeyWidgetsPresent() {
        driver.navigate().refresh();
        softAssert.assertEquals(createdReportPage.getAnalysisInfoWidgetTitle(), "Analysis Info",
                "Warning: Analysis Info widget is missing from the report!");
        softAssert.assertEquals(createdReportPage.getSimilarityScoreWidgetTitle(), "Similarity Score",
                "Warning: Similarity Score widget is missing from the report!");
        softAssert.assertEquals(createdReportPage.getCategoryFrequencyWidgetTitle(), "Category Frequency",
                "Warning: Category Frequency widget is missing from the report!");
        softAssert.assertEquals(createdReportPage.getDistanceAnalysisWidgetTitle(), "Distance Analysis",
                "Warning: Distance Analysis widget is missing from the report!");
        softAssert.assertEquals(createdReportPage.getRawDataWidgetTitle(), "Raw data",
                "Warning: Raw Data widget is missing from the report!");
        driver.navigate().back();
    }

    //REPORTS PAGE TEST CASES
    @Test(priority = 9, groups = {"Positive"}, description = "This test verifies that the user is able " +
            "to delete the report he/she had previously created.", suiteName = "Smoke Test")
    public void deleteUserCreatedReport() {
        reportsPage.deleteReport(reportName);
        //Validate that report has been successfully deleted
        assert (reportsPage.successfulFileDeletion().contains("Successfully deleted selected queries"));
    }

    @AfterTest(alwaysRun = true, description = "Verify that user is able to successfully " +
            "log out from the PlaceLab demo website.")
    private void logOut() {
        driver.navigate().to(homePageUrl);
        homePage.signOut();
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowser() {
        driver.close();
    }
}