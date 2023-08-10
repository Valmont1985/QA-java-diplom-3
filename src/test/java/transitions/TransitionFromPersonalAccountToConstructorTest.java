package transitions;

import clients.UserClient;
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
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.PersonalAccountPage;
import pojo.CreateUserRequest;
import providers.UserProvider;

import java.util.Objects;

public class TransitionFromPersonalAccountToConstructorTest {
        private WebDriver driver;
        private final UserClient userClient = new UserClient();
        private String accessToken;

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

            //Создание пользователя
            CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
            accessToken = userClient.create(createUserRequest)
                    .extract().jsonPath().get("accessToken");
            //Логин
            driver.get("https://stellarburgers.nomoreparties.site/login");
            LoginPage objLoginPage = new LoginPage(driver);
            objLoginPage.login(createUserRequest.getEmail(), createUserRequest.getPassword());
            //Переход в личный кабинет
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickButtonPersonalAccount();
        }
        @Test
        @Step("Переходим из личного кабинета в конструктор через кнопку 'Конструктор'")
        @Description("Тест проверяет переход из личного кабинета в конструктор через кнопку 'Конструктор'")
        @DisplayName("Тестовый метод: переход из личного кабинета в конструктор через кнопку 'Конструктор'")
        public void testClickConstructorFromPersonalAccount() {
            PersonalAccountPage objAccountPage = new PersonalAccountPage(driver);
            //Клик по разделу "Конструктор"
            objAccountPage.clickButtonConstructor();
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/";
            Assert.assertEquals("Ошибка - Пользователь находится не в Конструкторе", expected, actual);
        }
        @Test
        @Step("Переходим из личного кабинета в конструктор через логотип в хедере")
        @Description("Тест проверяет переход из личного кабинета в конструктор через логотип в хедере")
        @DisplayName("Тестовый метод: переход из личного кабинета в конструктор через логотип в хедере")
        public void testClickLogoFromPersonalAccount() {
            PersonalAccountPage objAccountPage = new PersonalAccountPage(driver);
            //Клик по логотипу
            objAccountPage.clickLogoStellarBurgers();
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/";
            Assert.assertEquals("Ошибка - Пользователь находится не в конструкторе", expected, actual);
        }
        @After
        public void teardown() {
            //Удаляем пользователя
            if( !(Objects.equals(accessToken, null)) && !(Objects.equals(accessToken, "")) ) {
                userClient.delete(accessToken)
                        .statusCode(202);
            }
            //Закрываем страницу
            driver.quit();
        }
}
