package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;

    /*Локаторы*/

    //Кнопка "Войти в аккаунт" на главной странице
    private final By buttonSignInAccount = By.xpath(".//button[text() = 'Войти в аккаунт']");
    //Кнопка "Личный кабинет"
    private final By buttonPersonalAccount = By.xpath(".//a/p[text() = 'Личный Кабинет']");
    //Раздел "булки"
    private final By sectionBuns = By.xpath(".//div/span[text() = 'Булки']");
    //Раздел "соусы"
    private final By sectionSauce = By.xpath(".//div/span[text() = 'Соусы']");
    //Раздел "Начинки"
    private final By sectionFilling = By.xpath(".//div/span[text() = 'Начинки']");
    //Выбран раздел "булки"
    private final By selectedSectionBuns = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Булки']");
    //Выбран раздел "Соусы"
    private final By selectedSectionSauce = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Соусы']");
    //Выбран раздел "Начинки"
    private final By selectedSectionFilling = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span[text() = 'Начинки']");

    /*Конструктор*/
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /*Методы*/

    //Метод клик по кнопке "Войти в аккаунт"
    public void clickButtonSignInAccount() {
        driver.findElement(buttonSignInAccount).click();
    }
    //Метод клик по кнопке "Личный кабинет"
    public void clickButtonPersonalAccount() {
        driver.findElement(buttonPersonalAccount).click();
    }
    //Метод ожидания подсветки раздела "Булки"
    public void waitBuns() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedSectionBuns));
    }
    //Метод ожидания перехода к элементу "Соусы"
    public void waitSauce() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedSectionSauce));
    }
    //Метод ожидания перехода к элементу "Начинки"
    public void waitFilling() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedSectionFilling));
    }
    //Метод клик по разделу "Булки"
    public void clickSectionBuns() {
        driver.findElement(sectionBuns).click();
        waitBuns();
    }
    //Метод клик по разделу "Соусы"
    public void clickSectionSauce() {
        driver.findElement(sectionSauce).click();
        waitSauce();
    }
    //Метод клик по разделу "Начинки"
    public void clickSectionFilling() {
        driver.findElement(sectionFilling).click();
        waitFilling();
    }
}
