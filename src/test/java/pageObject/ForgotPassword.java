package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPassword {
    private final WebDriver driver;

    /*Локаторы*/

    //Поле email
    private final By fieldEmail = By.xpath(".//label[text() = 'Email']");
    //Кнопка "Восстановить"
    private final By buttonRestore = By.xpath(".//button[text() = 'Восстановить']");
    //Кнопка "Войти"
    private final By buttonSignIn = By.xpath(".//div/p/a[text() = 'Войти']");

    /*Конструктор*/
    public ForgotPassword(WebDriver driver) {
        this.driver = driver;
    }

    /*Методы*/

    //Метод ввода email
    public void setFieldEmail(String email) {
        driver.findElement(fieldEmail).sendKeys(email);
    }
    //Метод клик по кнопке "Восстановить"
    public void clickButtonRestore() {
        driver.findElement(buttonRestore).click();
    }
    //Метод клик по кнопке "Войти"
    public void clickButtonSignIn() {
        driver.findElement(buttonSignIn).click();
    }
    //Метод ожидания загрузки страницы
    public void waitLoadPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonRestore));
    }
    //Метод восстановления пароля
    public void restorePassword(String email) {
        waitLoadPage();
        setFieldEmail(email);
        clickButtonRestore();
    }
}
