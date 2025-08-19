package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import listeners.AllureListener;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;
import utils.ExcelReader;
import java.util.Objects;

@Listeners(AllureListener.class)
public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelReader.getData("src/main/resources/Selenium_project.xlsx", "LoginData");
    }

    @Description("Check the functionality of the login page")
    @Epic("TC_L001")
    @Story("Check if all login patters are successful")
    @Test(dataProvider = "loginData",
            groups = {"login", "regression", "smoke"})
    public void loginTest(String testCaseID, String description, String testSteps,
                          String inputData, String expectedResult,
                          String actualResult, String executionStatus,
                          String priority, String postPageActions, String notes) {
        if (executionStatus.isEmpty()) {
            System.out.println("Skipping test " + testCaseID);
            throw new SkipException("Skipped" + testCaseID);
        }
        if (executionStatus.equals("Pass")) {
            System.out.println("Skipping test as already passed" + testCaseID);
            throw new SkipException("Skipped" + testCaseID);
        }
        if(testCaseID.isEmpty()) {
            throw new SkipException("Skip");
        }


        LoginPage login = new LoginPage(driver);
        ProductPage product = new ProductPage(driver);

        String[] parts = inputData.split("Password:");

        String username = parts[0].replace("Username:", "").trim();

        String password = "";
        if (parts.length > 1) { //
            password = parts[1].trim();
        }


        login.Username(username);
        login.Password(password);
        login.Login();
        login.errorMessage();

        // TC 1-6-10
        if (expectedResult.contains("Epic sadface")) {
            System.out.println("Login not successful");
            Assert.assertTrue(expectedResult.contains(login.errorMessage()));
            System.out.println(login.errorMessage());
        } else if (expectedResult.contains("should be able to login") && postPageActions.isEmpty()){
            System.out.println("Login success");
            Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("inventory.html"));
            System.out.println(driver.getCurrentUrl());
        } else if (expectedResult.contains("Expected title")) {
            String title = "Swag Labs";
            Assert.assertEquals(login.title(), title);
            System.out.println("10 pass");
            
        }


        if (!postPageActions.isEmpty()) {
            String[] checks = postPageActions.split(",");
            for (String check : checks) {
                switch (check.trim()) {
                    // TC 7
                    case "InventoryImages.All dogs":
                        Assert.assertTrue(product.allItemDog());
                        System.out.println("All images are of dogs - Test passed");
                        break;
                    // TC 8
                    case "InventoryImages.First Only":
                        Assert.assertTrue(product.onlyFirstDog());
                        System.out.println("Only first image is of a dog - Test passed");
                        break;
                    // TC 9
                    case "InventoryImages.cart":
                        Assert.assertTrue(product.errorCheck());
                        System.out.println("Error login - Test passed");
                        }
                }
            }
        teardown();
        }
    }

