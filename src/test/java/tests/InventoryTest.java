package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Epic("Inventory Module - TC_I")
@Feature("Inventory functionality checks")
public class InventoryTest extends BaseTest {
    ProductPage productPage;
    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setupInventoryPage() {
        driver.get(ConfigReader.URL());
        loginPage = new LoginPage(driver);
        loginPage.Username(ConfigReader.User());
        loginPage.Password(ConfigReader.Pass());
        loginPage.Login();
        productPage = new ProductPage(driver);
    }

    @Test(groups = {"inventory","positive","regression"})
    @Story("Amount of items on the page")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that the amount of items on the Inventory page is correct")
    public void verifyInventoryItemCount() {
        Assert.assertEquals(productPage.checkAmountOfItems(),6,"Amount of items on the site is incorrect");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Sorting button - Alphabet")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if the sorting button alphabetically sorts all the items")
    public void verifySortAlphabetical() {
        List<String> result = productPage.sortingAscending();
        List<String> expected = new ArrayList<>(result);
        Assert.assertEquals(result,expected,"Items are not sorted by name in alphabetical order");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Sorting button - Reverse Alphabet")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if the sorting button reverse-alphabetically sorts all the items")
    public void verifySortReverseAlphabetical() {
        List<String> result = productPage.sortingDescending();
        List<String> expected = new ArrayList<>(result);
        Assert.assertEquals(result,expected,"Items are not sorted by name in reverse alphabetical order");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Sorting button - Price Ascending")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensures that all items on the Inventory page are sorted in ascending order when the Price Sort button is clicked")
    public void verifySortPriceAscending() {
        List<String> result = productPage.sortingPriceAscending();
        List<String> expected = new ArrayList<>(result);
        Assert.assertEquals(result,expected,"Items are not sorted by price in ascending order");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Sorting button - Price Descending")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensures that all items on the Inventory page are sorted in descending order when the Price Sort button is clicked")
    public void verifySortPriceDescending() {
        List<String> result = productPage.sortingPrinceDescending();
        List<String> expected = new ArrayList<>(result);
        Assert.assertEquals(result,expected,"Items are not sorted by price in descending order");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Cart button functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if the cart button item functions")
    public void verifyCartButtonFunctions() {
        Assert.assertTrue(productPage.checkCartFunctionality(),"Cart button is not functioning");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Checks if the 'Add to Cart' button works for all items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if user has an ability to add selected items to the cart")
    public void verifyCartButtonExists() {
        Assert.assertTrue(productPage.checkCartButtonFunctionality(),"Items can not be added to cart");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Checks if 'Remove' button works for a single item")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if user has an ability to a remove selected item from the cart")
    public void verifyRemoveButtonFunctionality() {
        Assert.assertTrue(productPage.removeButtonFunctionality(),"Items can not be deleted from the cart");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Checks if 'Remove' button works for a multiple items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if user has an ability to remove selected items from the cart")
    public void verifyMultipleRemoveButton() {
        Assert.assertTrue(productPage.removeMultipleButtonFunctionality(),"Cart is not updated depending on the number of deleted items");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Checks if Wrapper menu is functional")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if the Wrapper menu is functional and has all sub-items")
    public void VerifyWrapMenu() {
        List<String> expectedOptions = Arrays.asList("All Items", "About", "Logout", "Reset App State");
        List<String> actualOptions = productPage.checkReactMenu();
        Assert.assertEquals(actualOptions,expectedOptions,"Wrapper menu interaction functionality has failed");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Checks if 'About' button in Wrapper menu is functional")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if 'About' button works")
    public void VerifyWrapMenuAbout() {
        Assert.assertTrue(productPage.checkAboutButton(),"About button is not functioning");
    }
    @Test(groups = {"inventory","positive","regression"})
    @Story("Checks if 'Logout' button in Wrapper menu is functional")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies if 'Logout' button logs the user out")
    public void VerifyWrapMenuLogout() {
        Assert.assertTrue(productPage.checkLogoutButton(),"Logout button is not functioning");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Checks if 'Reset App' button in Wrapper menu is functional")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if the button resets the state of the app")
    @Step("Ensure the cart is emptied after the clicking the button")
    public void VerifyWrapMenuResetButton() {
        Assert.assertTrue(productPage.resetAppButton(),"Reset app state button is not functioning");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Check if item images match when singular items are selected")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if the images match when user selects them")
    public void VerifyCorrectImageHandling() {
        Assert.assertTrue(productPage.verifyItItemsImagesCorrect(),"Images do not match ones on the inventory page");
    }
    @Test(groups = {"inventory","positive","smoke"})
    @Story("Che ck if item prices match when singular items are selected")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies if the prices match when user selects them")
    public void VerifyCorrectPriceHandling() {
        Assert.assertTrue(productPage.verifyIfItemsPricesCorrect(),"Prices do not match ones on the inventory page");
    }


}




