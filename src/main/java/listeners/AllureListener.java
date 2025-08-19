package listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureListener implements ITestListener {
    public static WebDriver driver;

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(">>> onTestFailure triggered for: " + result.getName());
        if (driver != null) {
            try {
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(bytes), "png");
                System.out.println(">>> Screenshot attached to Allure.");
            } catch (Exception e) {
                System.out.println(">>> Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.out.println(">>> Driver is NULL, no screenshot taken.");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        onTestFailure(result); // reuse the same logic
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
}
