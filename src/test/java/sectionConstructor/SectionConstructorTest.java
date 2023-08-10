package sectionConstructor;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.HomePage;

public class SectionConstructorTest {
        private WebDriver driver;

        @Before
        public void setUp() {
            //Драйвер Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-notifications");
            this.driver = new ChromeDriver(options);

             /*Драйвер Yandex
            System.setProperty("webdriver.chrome.driver","path/to/yandex/browser");
            ChromeDriverService service = new ChromeDriverService.Builder().build();
            this.driver = new ChromeDriver(service);*/

            //Переход на главную страницу
            driver.get("https://stellarburgers.nomoreparties.site/");
        }

        @Test
        @Step("Переходим в раздел 'Булки' в Конструкторе")
        @Description("Тест проверяет переход в раздел 'Булки' в Конструкторе на главной странице")
        @DisplayName("Тест - Переход в раздел 'Булки' в Конструкторе")
        public void testTransitionInSectionBuns() {
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickSectionFilling();
            //Проверка перехода в раздел "Булки"
            objHomePage.clickSectionBuns();
            By expected = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Булки']");
            boolean actual = driver.findElements(expected).size() > 0;
            Assert.assertTrue("Ошибка, переход к разделу 'Булки' не осуществлен", actual);
        }
        @Test
        @Step("Переходим в раздел 'Соусы' в Конструкторе")
        @Description("Тест проверяет переход в раздел 'Соусы' в Конструкторе на главной странице")
        @DisplayName("Тест - Переход в раздел 'Соусы' в Конструкторе")
        public void testTransitionInSectionSauce() {
            HomePage objHomePage = new HomePage(driver);
            //Проверка перехода в раздел "Соусы"
            objHomePage.clickSectionSauce();
            By expected = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Соусы']");
            boolean actual = driver.findElements(expected).size() > 0;
            Assert.assertTrue("Ошибка, переход к разделу 'Соусы' не осуществлен", actual);
        }
        @Test
        @Step("Переходим в раздел 'Начинки' в Конструкторе")
        @Description("Тест проверяет переход в раздел 'Начинки' в Конструкторе на главной странице")
        @DisplayName("Тест - Переход в раздел 'Начинки' в Конструкторе")
        public void testTransitionInSectionFilling() {
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickSectionSauce();
            //Проверка перехода в раздел "Начинки"
            objHomePage.clickSectionFilling();
            By expected = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Начинки']");
            boolean actual = driver.findElements(expected).size() > 0;
            Assert.assertTrue("Ошибка, переход к разделу 'Начинки' не осуществлен", actual);
        }
        @After
        public void teardown() {
            driver.quit();
        }
}
