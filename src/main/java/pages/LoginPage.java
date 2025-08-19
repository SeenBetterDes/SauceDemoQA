package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoginPage {
    WebDriver driver;

    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By login_button = By.id("login-button");
    By message = By.xpath("//div[@class='error-message-container error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }



    public void Username(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }
    public void Password(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
    public void Login() {
        driver.findElement(login_button).click();
    }
    public String errorMessage() {

        List<WebElement> errors = driver.findElements(message);
        if(errors.isEmpty()) {
            String url = driver.getCurrentUrl();
            return url;
        }
        else {
            return errors.getFirst().getText();
        }

    }
    public String title() {
        return driver.getTitle();
    }

}

