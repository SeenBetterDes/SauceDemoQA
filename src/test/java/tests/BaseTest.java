package tests;

import listeners.AllureListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void Setup(@Optional("chrome") String browser) {
        if(browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser not yet supported" + browser);
        }
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        AllureListener.driver = driver;

    }
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if(driver!=null) {
            driver.quit();
        }
    }
}
