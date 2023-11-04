package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineGoryankaTest extends BaseTest {
    private void createApiPipeline(String jobName) {
        getDriver().findElement(By.xpath("//span[@class = 'task-icon-link']")).click();

        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void goToDashBoard() {
        getDriver().findElement(By.xpath("//li[@class= 'jenkins-breadcrumbs__list-item']")).click();
    }


    @Test
    public void testCreateJob() {
        final String jobName = "Pipeline_1";

        createApiPipeline(jobName);

        goToDashBoard();

        String actual = getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText();
        Assert.assertEquals(actual, jobName);

    }
}
