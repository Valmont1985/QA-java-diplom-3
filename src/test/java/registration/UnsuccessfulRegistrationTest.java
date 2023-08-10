package registration;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.RegisterPage;

@RunWith(Parameterized.class)
public class UnsuccessfulRegistrationTest {
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
        }

        private final String name;
        private final String email;
        private final String password;

        public UnsuccessfulRegistrationTest(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        @Parameterized.Parameters(name = "Данные для регистрации пользователя. Тестовые данные: {0} , {1} , {2}")
        public static Object[][] sendFieldsForCheckUnsuccessfulRegistration() {
            return new Object[][]{
                    {"", "tallisker@yandex.ru", "123456"},
                    {"Valmont1985", "", "123456"},
                    {"Valmont1985", " ", "123456"},
                    {"Valmont1985", "tallisker@yandex.ru", ""},
                    {"Valmont1985", "tallisker@yandex.ru", " "},
                    {"Яфейковыйюзер", "emailemailemail", "123456"},
                    {"Яфейковыйюзер", "tallisker@yandex.ru", "12345"}
            };
        }
        @Test
        @Step("Регистрируем пользователя с неверными данными")
        @Description("Тест проверяет регистрацию пользователя с неверными данными")
        @DisplayName("Тест - Регистрация пользователя с неверными данными")
        public void checkUnsuccessfulRegistration() {
            //Переход на страницу регистрации
            driver.get("https://stellarburgers.nomoreparties.site/register");
            //Объект класса страница регистрации
            RegisterPage objRegisterPage = new RegisterPage(driver);
            //Попытка регистрации пользователя
            objRegisterPage.register(name, email, password);
            driver.get("https://stellarburgers.nomoreparties.site/login");
            //Объект класса страница логина
            LoginPage objLoginPage = new LoginPage(driver);
            objLoginPage.login(email, password);
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickButtonPersonalAccount();
            //Проверка
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/login";
            Assert.assertEquals("Ошибка, пользователь успешно зарегистрирован", expected,actual);
        }
        @After
        public void teardown() {
            driver.quit();
        }
}