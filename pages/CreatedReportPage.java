package placelab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreatedReportPage {

    public WebDriver driver;

    public CreatedReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCreatedReportPageTitle() {
        return this.driver.findElement(By.xpath("//*[@id='breadcrumb']/div/span[2]")).getText();
    }

    public String getAnalysisInfoWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id='query_info']/div[1]/h5/span")).getText();
    }

    public String getSimilarityScoreWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id='similarity_score']/div[1]/h5/span")).getText();
    }

    public String getCategoryFrequencyWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id='normalized_category']/div[1]/h5/span")).getText();
    }

    public String getDistanceAnalysisWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id='distance_single']/div[1]/h5/span")).getText();
    }

    public String getRawDataWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id='raw_data']/div[1]/h5")).getText();
    }
}
