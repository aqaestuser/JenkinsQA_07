package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.BuildHistoryPage;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.FreestyleProjectDetailsPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BuildHistoryTest extends BaseTest {

    private static final String NAME_FREESTYLE_PROJECT = "FreestyleProject";

    @Test
    public void testViewBuildHistory() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickBuildHistoryButton();

        Assert.assertTrue(buildHistoryPage.getMainPanel().isDisplayed());
    }

    @Test
    public void testViewBuildHistoryClickableIconLegend() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickBuildHistoryButton()
                .clickIconLegendButton();

        Assert.assertEquals(buildHistoryPage.getIconLegendHeaders().size(), 2);
    }

    @Test
    public void testCheckDateAndMonthBuildHistory() {
        Date systemDate = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        String dateNow = formatForDateNow.format(systemDate);

        FreestyleProjectDetailsPage buildHistoryPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("Test")
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .clickBuildNowButton();

        if (dateNow.length() == 5) {
            Assert.assertEquals(getWait10().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText()
                    .substring(0, 5), dateNow);
        } else {
            Assert.assertEquals(getWait10().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//a[@class='model-link inside build-link']"))).getText()
                    .substring(0, 6), dateNow);
        }
    }

    @Test
    public void testTooltipIsVisibleInTheTimeSinceSection() {
        String tooltipIsVisible = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FREESTYLE_PROJECT)
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .clickBuildNowButton()
                .goHomePage()
                .clickBuildHistoryButton()
                .getTextLastTimeSinceInLineBuild();

        Assert.assertEquals(tooltipIsVisible, "Click to center timeline on event");
    }

    @Test (dependsOnMethods = "testTooltipIsVisibleInTheTimeSinceSection")
    public void testEditBuildInformation(){

    }
    
    @Test(dependsOnMethods = "testTooltipIsVisibleInTheTimeSinceSection")
    public void testReturnBuildPoint() {
        Point startPosition = new HomePage(getDriver())
                .clickBuildByGreenArrow(NAME_FREESTYLE_PROJECT)
                .clickBuildHistoryButton()
                .getPointLocation();

        Point actualPosition = new BuildHistoryPage(getDriver())
                .scrollTimelineBuildHistory()
                .clickLastTimeSinceInLineBuild()
                .getPointLocation();

        Assert.assertEquals(actualPosition, startPosition);
    }
}