package tests;

import listeners.AllureListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

import java.io.File;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void Setup(@Optional("chrome") String browser) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        if(browser.equals("chrome")) {
            String userDir = tmpDir + "/chrome-" + UUID.randomUUID();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--user-data-dir=" + userDir);
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            String userDir = tmpDir + "/firefox-" + UUID.randomUUID();
            File profileDir = new File(userDir);
            profileDir.mkdirs();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("-profile");
            options.addArguments(userDir);
            driver = new FirefoxDriver(options);
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
            driver = null;
        }
    }
}
