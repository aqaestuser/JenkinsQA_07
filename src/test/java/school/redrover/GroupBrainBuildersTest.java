package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import static org.testng.Assert.assertEquals;


public class GroupBrainBuildersTest extends BaseTest {

    @Ignore
    @Test
    public void testAskentLogIn() throws InterruptedException {
        getDriver().get("https://www.askent.ru/cabinet/order/?show_all=Y");

        String title = getDriver().getTitle();
        assertEquals(title, "ASKENT - российский бренд аксессуаров из натуральной кожи");

        WebElement emailField = getDriver().findElement(By.xpath("//form[@id='loginform']/div[3]/div/input"));
        emailField.click();
        emailField.sendKeys("testaccaskenttest@gmail.com");

        WebElement passwordField = getDriver().findElement(By.xpath("//form[@id='loginform']/div[3]/div[2]/input"));
        passwordField.click();
        passwordField.sendKeys("testpasswordaskent123!");

        WebElement submitButtonLogIn = getDriver().findElement(By.xpath("//form[@id='loginform']/a"));
        submitButtonLogIn.click();
        Thread.sleep(1000);

        WebElement personalAccountTitle = getDriver().findElement(By.cssSelector("h1"));
        String resultTitle = personalAccountTitle.getText();
        Assert.assertEquals(resultTitle, "Личный кабинет");
    }

    @Ignore
    @Test
    public void testAskentAddToCart() throws InterruptedException {
        getDriver().get("https://www.askent.ru/cat/bumazhniki/portmone_308/");

        String title = getDriver().getTitle();
        assertEquals(title, "МИНИ ПОРТМОНЕ MODULE , пол: Женский, цвет: breen, размер: 100х080х035. Купить в интернет-магазине ASKENT. Цена 4 490 руб.");

        if (getDriver().findElement(By.xpath("//*[@id = 'cookie_accept']")).isDisplayed()) {
            WebElement cookieButton = getDriver().findElement(By.xpath("//*[@id = 'cookie_accept']"));
            cookieButton.click();
        }

        WebElement addToCartButton = getDriver().findElement(By.xpath("//*[@class = 'optionsBlock__add add-cart-statistics']"));
        addToCartButton.click();

        WebElement cartIcon = getDriver().findElement(By.xpath("//*[@class = 'cart_icon']"));
        cartIcon.click();
        Thread.sleep(2000);

        WebElement itemName = getDriver().findElement(By.xpath("//*[@href = '/cat/bumazhniki/portmone_308/']"));
        String resultName = itemName.getText();
        Assert.assertEquals(resultName, "МИНИ ПОРТМОНЕ MODULE");
    }

    @Ignore
    @Test
    public void testAlcobendasSearch() throws InterruptedException{

        getDriver().get("https://www.alcobendas.org/es");

        String title = getDriver().getTitle();
        assertEquals(title, "Página Web del Ayuntamiento de Alcobendas");

        Thread.sleep(2000);

        WebElement lupaButton = getDriver().findElement(By.xpath("//*[@id='block-views-block-ayto-vista-lupa-header-block-1']/div/div"));
        WebElement buscarButton = getDriver().findElement(By.xpath("//*[@id='edit-submit-ayto-resultados-de-busqueda-bloque']"));
        WebElement searchInput = getDriver().findElement(By.xpath("//*[@id='edit-buscar']"));

        lupaButton.click();
        searchInput.sendKeys("yoga");
        buscarButton.click();

        WebElement resultOfSearch = getDriver().findElement(By.xpath("//*[@id='block-contenidoprincipaldelapagina-2']/div/div/div[1]/div[1]/h2"));
        Thread.sleep(2000);
        String value = resultOfSearch.getText();
        Assert.assertEquals(value, "/2 resultados");
    }


    @Test
    public void testJenkinsAdminStatus() {

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(2) > span > a")).click();
        // From the list of users I would like to get name of the particular user and click on it
        WebElement recordInTheList = getDriver().findElement(By.className("jenkins-table__link"));
        String userName = recordInTheList.getText();
        recordInTheList.click();
        // And to verify that on the next page userID match with the name
        Assert.assertTrue(getDriver().getPageSource().contains(userName));
    }

    @Ignore
    @Test
    public void testAskentSearch() {
        getDriver().get("https://www.askent.ru/");

        String title = getDriver().getTitle();
        assertEquals(title, "ASKENT - российский бренд аксессуаров из натуральной кожи");

        WebElement magnifierIcon = getDriver().findElement(By.className("search_icon"));
        magnifierIcon.click();

        WebElement searchTextField = getDriver().findElement(By.name("q"));
        searchTextField.click();
        searchTextField.sendKeys("сумка");

        WebElement magnifierButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        magnifierButton.click();

        WebElement searchResult = getDriver().findElement(By.cssSelector("h1"));
        String result = searchResult.getText();
        Assert.assertEquals(result, "Результаты поиска");
    }

    @Test
    public void testJenkinsCredentialsTooltip() {

        WebElement adminMenu = getDriver().findElement(By.xpath("//a[@href='/user/admin']"));
        adminMenu.click();

        WebElement credentialsItem = getDriver().findElement(By.xpath("//a[@href='/user/admin/credentials']"));
        credentialsItem.click();

        WebElement systemTableItem = getDriver().findElement(By.xpath("//a[@href='/manage/credentials/store/system']"));
        systemTableItem.click();

        WebElement imageSystemTable = getDriver().findElement(By.xpath("//img[@class='icon-credentials-domain icon-lg']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(imageSystemTable).perform();

        WebElement tooltip = getDriver().findElement(By.xpath("//img[@aria-describedby = 'tippy-10']"));
        Assert.assertTrue(tooltip.isDisplayed());
    }
}
