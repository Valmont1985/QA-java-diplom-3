package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    private final WebDriver driver;

    /*Локаторы*/

    //Поле Имя
    private final By fieldName = By.xpath(".//fieldset[1]//div/input");
    //Поле Email
    private final By fieldEmail = By.xpath(".//fieldset[2]//div/input");
    //Поле Пароль
    private final By fieldPassword = By.xpath(".//div/input[@name = 'Пароль']");
    //Кнопка "Зарегистрироваться"
    private final By buttonRegister = By.xpath(".//button[text() = 'Зарегистрироваться']");
    //Кнопка(ссылка) Войти
    private final By buttonSignIn = By.xpath(".//p/a[text() = 'Войти']");
    //Проверка - такой пользователь уже зарегистрирован
    private final By userAlreadyRegistered = By.xpath(".//p[text() = 'Такой пользователь уже существует']");
    //Ошибка - Некорректный пароль
    private final By incorrectPassword = By.xpath(".//p[text() = 'Некорректный пароль']");

    /*Конструктор*/
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    /*Методы*/

    //Метод ввода данных в поле Имя
    public void setFieldName(String name) {
        driver.findElement(fieldName).click();
        driver.findElement(fieldName).sendKeys(name);
    }
    //Метод ввода данных в поле Email
    public void setFieldEmail(String email) {
        driver.findElement(fieldEmail).click();
        driver.findElement(fieldEmail).sendKeys(email);
    }
    //Метод ввода данных в поле Пароль
    public void setFieldPassword(String password) {
        driver.findElement(fieldPassword).click();
        driver.findElement(fieldPassword).sendKeys(password);
    }
    //Метод клик по кнопке "Зарегистрироваться"
    public void clickButtonRegister() {
        driver.findElement(buttonRegister).click();
    }
    //Метод клик по кнопке "Войти"
    public void clickButtonSignIn() {
        driver.findElement(buttonSignIn).click();
    }
    //Метод ожидания загрузки страницы
    public void waitLoadPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonRegister));
    }
    //Метод регистрации пользователя
    public void register(String name, String email, String password) {
        waitLoadPage();
        setFieldName(name);
        setFieldEmail(email);
        setFieldPassword(password);
        clickButtonRegister();
    }
    //Метод ожидания появления сообщения "Такой пользователь уже зарегистрирован"
    public void waitTextUserAlreadyRegistered() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(userAlreadyRegistered));
    }
    //Метод поиска сообщения "Такой пользователь уже существует" при повторной регистрации такого же пользователя
    public String findTextUserAlreadyRegistered() {
        waitTextUserAlreadyRegistered();
        return driver.findElement(userAlreadyRegistered).getText();
    }
    //Метод поиска сообщения "Некорректный пароль" при вводе пароля менее 6 символов
    public String findTextIncorrectPassword() {
        return driver.findElement(incorrectPassword).getText();
    }
}
