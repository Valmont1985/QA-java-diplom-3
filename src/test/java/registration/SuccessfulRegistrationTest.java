package registration;

import clients.UserClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.RegisterPage;
import pojo.CreateUserRequest;
import pojo.LoginUserRequest;
import providers.UserProvider;

import java.util.Objects;
import java.util.Random;

@RunWith(Parameterized.class)
public class SuccessfulRegistrationTest {
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

        private final String name;
        private final String email;
        private final String password;

        public SuccessfulRegistrationTest(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        @Parameterized.Parameters(name = "Данные для регистрации пользователя. Тестовые данные: {0} , {1} , {2}")
        public static Object[][] sendFieldsForSuccessfulRegistration() {
            Random random = new Random();
            return new Object[][]{
                    {"Илья", "tallisker" + random.nextInt(100) +"@yandex.ru", "123456"},
                    {"Ilya", "iprosvirnin" + random.nextInt(100) + "@gmail.com", "test1985"},
                    {"T", random.nextInt(100) + "@mail.ru", "_test_"},
                    {"АбракадабраАбракадабра", "iprosvirnin" + random.nextInt(100) + "@wildred.ru", "Парольпарольпарольпароль"},
                    {"Какое то имя", random.nextInt(100) + "@ya.ru", "Какой то пароль"}
            };
        }
        @Test
        @Step("Регистрируем пользователя")
        @Description("Тест проверяет успешную регистрацию пользователя")
        @DisplayName("Тест - Успешная регистрация пользователя")
        public void checkSuccessfulRegistration() {
            //Переходим на страницу регистрации
            driver.get("https://stellarburgers.nomoreparties.site/register");
            //Объект класса страница регистрации
            RegisterPage objRegisterPage = new RegisterPage(driver);
            //Зарегистрировать пользователя
            objRegisterPage.register(name, email, password);
            //Проверяем, что пользователь успешно зарегистрирован
            driver.get("https://stellarburgers.nomoreparties.site/register");
            objRegisterPage.register(name, email, password);
            String actual = objRegisterPage.findTextUserAlreadyRegistered();
            Assert.assertEquals("Ошибка - пользователь не зарегистрирован", "Такой пользователь уже существует",actual);
        }
        @After
        public void teardown() {
            //Получаем токен для удаления пользователя
            CreateUserRequest createUserRequest = UserProvider.getDataCreatedUser(email, password, name);
            LoginUserRequest loginUserRequest = LoginUserRequest.from(createUserRequest);
            accessToken = userClient.login(loginUserRequest)
                    .statusCode(200)
                    .extract().jsonPath().get("accessToken");
            //Удаляем пользователя
            if( !(Objects.equals(accessToken, null)) && !(Objects.equals(accessToken, "")) ) {
                userClient.delete(accessToken)
                        .statusCode(202);
            }
            //Закрываем страницу
            driver.quit();
        }
}
