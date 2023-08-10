package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalAccountPage {
    private final WebDriver driver;

    /*Локаторы*/

    //Локатор на кнопку "Выйти"
    private final By buttonExit = By.xpath(".//nav//li/button[text() = 'Выход']");
    //Локатор на кнопку "Конструктор"
    private final By buttonConstructor = By.xpath(".//nav//a/p[text() = 'Конструктор']");
    //Локатор на логотип Stellar Burgers
    private final By logoStellarBurgers = By.xpath(".//nav/div[contains(@class,'AppHeader_header__logo')]");

    /*Конструктор*/
    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    /*Методы*/

    //Метод ожидания загрузки страницы
    public void waitLoadPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonExit));
    }
    //Метод клик по кнопке "Выйти"
    public void clickButtonExit() {
        waitLoadPage();
        driver.findElement(buttonExit).click();
    }
    //Метод клик по "Конструктор"
    public void clickButtonConstructor() {
        driver.findElement(buttonConstructor).click();
    }
    //Метод клик по лого
    public void clickLogoStellarBurgers() {
        driver.findElement(logoStellarBurgers).click();
    }
}
