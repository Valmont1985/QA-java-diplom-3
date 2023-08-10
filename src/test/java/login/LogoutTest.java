package login;

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

public class LogoutTest {
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

            //создаем пользователя
            CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
            accessToken = userClient.create(createUserRequest)
                    .extract().jsonPath().get("accessToken");

            //логинимся
            driver.get("https://stellarburgers.nomoreparties.site/login");
            LoginPage objLoginPage = new LoginPage(driver);
            objLoginPage.login(createUserRequest.getEmail(), createUserRequest.getPassword());
        }
        @Test
        @Step("Выйти из аккаунта")
        @Description("Тест проверяет выход из личного кабинета")
        @DisplayName("Тест - Выход из личного кабинета")
        public void testLogoutPersonalAccount() {
            HomePage objHomePage = new HomePage(driver);
            objHomePage.clickButtonPersonalAccount();
            PersonalAccountPage objAccountPage = new PersonalAccountPage(driver);
            objAccountPage.clickButtonExit();

            //ожидаем загрузку страницы
            LoginPage objLoginPage = new LoginPage(driver);
            objLoginPage.waitLoadPage();
            String actual = driver.getCurrentUrl();
            String expected = "https://stellarburgers.nomoreparties.site/login";
            Assert.assertEquals("Ошибка, пользователь не вышел из системы", expected, actual);
        }
        @After
        public void teardown() {
            if( !(Objects.equals(accessToken, null)) && !(Objects.equals(accessToken, "")) ) {
                userClient.delete(accessToken)
                        .statusCode(202);
            }
            driver.quit();
        }
}
