package registration;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.RegisterPage;

public class ErrorPasswordTest {
        private WebDriver driver;

        @Before
        public void setUp() {
            //драйвер Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-notifications");
            this.driver = new ChromeDriver(options);

            /*Драйвер Yandex
            System.setProperty("webdriver.chrome.driver","path/to/yandex/browser");
            ChromeDriverService service = new ChromeDriverService.Builder().build();
            this.driver = new ChromeDriver(service);*/
        }
        @Test
        @Step("Проверяем наличие ошибки при вводе пароля менее 6 символов")
        @Description("Тест проверяет наличие ошибки при вводе пароля менее 6 символов")
        @DisplayName("Тест - Проверка ошибки при вводе короткого пароля, менее 6 символов")
        public void checkErrorWhenEnteringPasswordLessThanSixCharacters() {
            //Переход на страницу регистрации
            driver.get("https://stellarburgers.nomoreparties.site/register");
            //Объект класса страницы регистрации
            RegisterPage objRegisterPage = new RegisterPage(driver);
            //Попытка регистрации пользователя с паролем менее 6 символов
            objRegisterPage.register("test", "tallisker@yandex.ru", "12345");
            String actual = objRegisterPage.findTextIncorrectPassword();
            Assert.assertEquals("Текст ошибки отсутствует или не совпадает с ожидаемым значением", "Некорректный пароль", actual);
        }
        @After
        public void teardown() {
            driver.quit();
        }
}
