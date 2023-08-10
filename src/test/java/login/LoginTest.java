package login;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.ForgotPassword;
import pageObject.HomePage;
import pageObject.RegisterPage;

public class LoginTest {
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
    @Test
    @Step("Клик по кнопке 'Войти' на странице восстановления пароля")
    @Description("Тест проверяет, что пользователь переходит со страницы восстановления пароля на страницу входа в систему")
    @DisplayName("Тест - Выполняет клик по кнопке 'Войти' на странице восстановления пароля")
    public void clickSignInFromForgotPassPage() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        ForgotPassword objForgotPassPage = new ForgotPassword(driver);
        objForgotPassPage.clickButtonSignIn();
        String actual = driver.getCurrentUrl();
        String expected = "https://stellarburgers.nomoreparties.site/login";
        Assert.assertEquals("Ошибка - Пользователь не перешел из 'Восстановление пароля' на 'Вход в систему'", expected, actual);
    }
    @Test
    @Step("Клик по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Тест проверяет, что пользователь переходит со стартовой страницы на страницу входа в систему")
    @DisplayName("Тест - Выполняет клик по кнопке 'Войти в аккаунт' на главной странице")
    public void clickSignInFromHomePage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        HomePage objHomePage = new HomePage(driver);
        objHomePage.clickButtonSignInAccount();
        String actual = driver.getCurrentUrl();
        String expected = "https://stellarburgers.nomoreparties.site/login";
        Assert.assertEquals("Ошибка - Пользователь не перешел из стартовой страницы на 'Вход в систему'", expected, actual);
    }
    @Test
    @Step("Клик по кнопке 'Личный кабинет' на главной странице")
    @Description("Тест проверяет, что пользователь переходит со стартовой страницы на страницу входа в систему")
    @DisplayName("Тест - Выполняет клик по кнопке 'Личный кабинет' на главной странице")
    public void clickPersonalAccountFromHomePage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        HomePage objHomePage = new HomePage(driver);
        objHomePage.clickButtonPersonalAccount();
        String actual = driver.getCurrentUrl();
        String expected = "https://stellarburgers.nomoreparties.site/login";
        Assert.assertEquals("Ошибка - Пользователь не перешел со стартовой страницы на 'Вход в систему'", expected, actual);
    }
    @Test
    @Step("Клик по кнопке 'Войти' на странице регистрации пользователя")
    @Description("Тест проверяет, что пользователь переходит со страницы регистрации на страницу входа в систему")
    @DisplayName("Тест - Выполняет клик по кнопке 'Войти' на странице регистрации пользователя")
    public void clickSignInFromRegisterPage() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegisterPage objRegisterPage = new RegisterPage(driver);
        objRegisterPage.clickButtonSignIn();
        String actual = driver.getCurrentUrl();
        String expected = "https://stellarburgers.nomoreparties.site/login";
        Assert.assertEquals("Ошибка - Пользователь не перешел со страницы регистрации на 'Вход в систему'", expected, actual);
    }
    @After
    public void teardown() {
        driver.quit();
    }
}