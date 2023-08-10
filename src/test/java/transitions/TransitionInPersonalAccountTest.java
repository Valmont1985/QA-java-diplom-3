package transitions;

import clients.UserClient;
import providers.UserProvider;
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

import java.util.Objects;

public class TransitionInPersonalAccountTest {
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
        }

        @Test
        @Step("Переходим в личный кабинет через кнопку 'Личный кабинет' с авторизованным пользователем")
        @Description("Тест проверяет переход в личный кабинет с авторизованным пользователем")
        @DisplayName("Тест - Переход в личный кабинет с авторизованным пользователем")
        public void testClickPersonalAccountWithAuth() {
            //Создание пользователя
            CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
            accessToken = userClient.create(createUserRequest)
                    .extract().jsonPath().get("accessToken");
            //Логин
            driver.get("https://stellarburgers.nomoreparties.site/login");
            LoginPage objLoginPage = new LoginPage(driver);
            objLoginPage.login(createUserRequest.getEmail(), createUserRequest.getPassword());
            //Проверка клика по кнопке Личный кабинет
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickButtonPersonalAccount();
            //Ожидание загрузки страницы
            PersonalAccountPage objPersonalAcc = new PersonalAccountPage(driver);
            objPersonalAcc.waitLoadPage();
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/account/profile";
            Assert.assertEquals("Ошибка - Пользователь не находится в личном кабинете", expected, actual);
        }
        @Test
        @Step("Переходим в личный кабинет через кнопку 'Личный кабинет' с неавторизованным пользователем")
        @Description("Тест проверяет переход в личный кабинет с неавторизованным пользователем")
        @DisplayName("Тест - Переход в личный кабинет без авторизации")
        public void testClickPersonalAccountWithoutAuth() {
            //Переходим на cтартовую страницу
            driver.get("https://stellarburgers.nomoreparties.site/");
            //Проверка клика по кнопке Личный кабинет без авторизации
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickButtonPersonalAccount();
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/login";
            Assert.assertEquals("Ошибка - Пользователь не находится на странице входа", expected, actual);
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