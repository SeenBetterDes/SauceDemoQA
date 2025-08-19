package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;


    By inventoryImages = By.cssSelector("img.inventory_item_img");
    By item = By.id("add-to-cart-sauce-labs-bike-light");
    By itemRemove = By.id("remove-sauce-labs-bike-light");
    By itemRemoveGlobal = By.linkText("Remove");
    By cart = By.xpath("//a[@class='shopping_cart_link']");
    By cartBadge = By.cssSelector(".shopping_cart_badge");


    By sidebar = By.cssSelector(".bm-menu-wrap");
    By allItems = By.xpath("//div[@data-test='inventory-item-name']");
    By allItemNames = By.xpath("//div[@data-test='inventory-item-name']");
    By allItemPrice = By.xpath("//div[@class='inventory_item_price']");
    By allItemImages = By.xpath("//img[@class='inventory_item_img']");
    By sortingButton = By.xpath("//select[@class='product_sort_container']");
    By addItems = By.cssSelector("button.btn_inventory");
    By wrapperMenu = By.id("react-burger-menu-btn");
    By wrapperMenuOptions = By.xpath("//nav[@class='bm-item-list']//a");
    By wrapperAbout = By.xpath("//a[@id='about_sidebar_link']");
    By wrapperLogout = By.cssSelector("#logout_sidebar_link");
    By wrapperReset = By.cssSelector("#reset_sidebar_link");



    public static final String DOG_KEYWORD = "sl-404";
    public static final String EXPECTED_URL = "https://saucelabs.com/";
    public static final String BASE_URL = "https://www.saucedemo.com/";

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public List<WebElement> getAllPhotos() {
        return driver.findElements(inventoryImages);
    }

    public boolean allItemDog() {
        return getAllPhotos().stream()
                .allMatch(img -> img.getAttribute("src").contains(DOG_KEYWORD));
    }

    public boolean onlyFirstDog() {
        List<WebElement> images = getAllPhotos();
        boolean firstIsDog = images.getFirst().getAttribute("src").contains(DOG_KEYWORD);
        boolean otherNotDog = true;
        for (int i = 1; i < images.size(); i++) {
            if (images.get(i).getAttribute("src").contains(DOG_KEYWORD)) {
                otherNotDog = false;
                break;
            }
        }
        return firstIsDog && otherNotDog;
    }

    public boolean errorCheck() {
        WebElement singleItem = wait.until(ExpectedConditions.elementToBeClickable(item));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", singleItem);
        WebElement singleItemDel = wait.until(ExpectedConditions.visibilityOfElementLocated(itemRemove));
        safeClick(singleItemDel);
        String numberItem = driver.findElement(cart).getText().trim();
        Integer number = Integer.parseInt(numberItem);
        return number.equals(1);
    }


    // SHEET 2

    public List<WebElement> itemsPresent() {
        return driver.findElements(allItems);
    }

    public int checkAmountOfItems() {
        return itemsPresent().size();
    }

    public Select sortingFunctionality() {
        WebElement sorting = driver.findElement(sortingButton);
        return new Select(sorting);
    }

    public WebElement getAddToCartButton(String productName) {
        return driver.findElement(By.cssSelector("button[data-test='add-to-cart-" + productName + "']"));
    }

    public WebElement removeItemButton(String productName) {
        return driver.findElement(By.cssSelector("button[data-test='remove-" + productName + "']"));
    }
    public List<String> getAllItemNames() {
        return driver.findElements(allItemNames).stream()
                .map(WebElement::getText)
                .toList();
    }
    private void safeClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }



    // Methods
    public List<String> sortingAscending() {
        sortingFunctionality().selectByValue("za");
        sortingFunctionality().selectByValue("az");
        return driver.findElements(allItems).stream()
                .map(WebElement::getText)
                .toList();
    }


    public List<String> sortingDescending() {
        sortingFunctionality().selectByValue("za");
        return driver.findElements(allItems).stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> sortingPriceAscending() {
        sortingFunctionality().selectByValue("lohi");
        return driver.findElements(allItemPrice).stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> sortingPrinceDescending() {
        sortingFunctionality().selectByValue("hilo");
        return driver.findElements(allItemPrice).stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean checkCartFunctionality() {
        driver.findElement(cart).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return driver.getCurrentUrl().contains("cart.html");
    }



    public boolean checkCartButtonFunctionality() {
        int totalItems = driver.findElements(addItems).size();
        int countItem = 0;

        for (int i = 0; i < totalItems; i++) {
            List<WebElement> item_add = driver.findElements(addItems);
            safeClick(item_add.get(i));
            countItem++;

            wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadge, String.valueOf(countItem)));
            String cart_number = driver.findElement(cartBadge).getText();
            int cartNumber = Integer.parseInt(cart_number);
            if (cartNumber != countItem) {
                return false;
            }
        }
        return true;
    }
    public boolean removeButtonFunctionality() {
        getAddToCartButton("sauce-labs-bolt-t-shirt").click();
        wait.until(ExpectedConditions.textToBe(cart, "1"));
        String cartNumber = driver.findElement(cart).getText();
        int cartNum = Integer.parseInt(cartNumber);
        if (cartNum == 1) {
            removeItemButton("sauce-labs-bolt-t-shirt").click();
            wait.until(ExpectedConditions.or(ExpectedConditions.invisibilityOfElementLocated(cart), ExpectedConditions.textToBe(cart, "")));
        }
        cartNumber = driver.findElement(cart).getText();
        cartNum = cartNumber.isEmpty() ? 0 : Integer.parseInt(cartNumber);
        if (cartNum != 0) {
            return false;
        }
        return true;
    }
    public boolean removeMultipleButtonFunctionality() {
        List<WebElement> itemAdd = driver.findElements(addItems);
        int countItem= 0;

        for (WebElement item : itemAdd) {
            String before = driver.findElement(cart).getText();
            safeClick(item);
            wait.until(driver -> {
                String current = driver.findElement(cart).getText();
                return !current.equals(before) && !current.isEmpty();
            });
            String cartNumber = driver.findElement(cart).getText();
            int cartNum = Integer.parseInt(cartNumber);

            if (cartNum == ++countItem) {
                List<WebElement> itemRemove = driver.findElements(itemRemoveGlobal);
                for (WebElement remove : itemRemove) {
                    String beforeRemove = driver.findElement(cart).getText();

                    safeClick(remove);

                    wait.until(driver -> {
                        String current = driver.findElement(cart).getText();
                        return !current.equals(beforeRemove);
                    });

                    cartNum = driver.findElement(cart).getText().isEmpty() ? 0 : Integer.parseInt(driver.findElement(cart).getText());
                    if (cartNum == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public List<String> checkReactMenu() {
        List<String> options = new ArrayList<>();
        WebElement menuBtn = wait.until(ExpectedConditions.elementToBeClickable(wrapperMenu));
        safeClick(menuBtn);
        WebElement side = wait.until(ExpectedConditions.presenceOfElementLocated(sidebar));
        wait.until(driver -> side.getCssValue("transform").equals("none") || side.isDisplayed());
        List<WebElement> wrapOption = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(wrapperMenuOptions));
        for (WebElement item : wrapOption) {
            options.add(item.getText().trim());
        }
        return options;
    }
    public boolean checkAboutButton() {
        driver.findElement(wrapperMenu).click();
        WebElement AboutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(wrapperAbout));
        safeClick(AboutBtn);
        wait.until(ExpectedConditions.urlToBe(EXPECTED_URL));
        return driver.getCurrentUrl().equals(EXPECTED_URL);
    }

    public boolean checkLogoutButton() {
        driver.findElement(wrapperMenu).click();
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(wrapperLogout));
        safeClick(logoutBtn);
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        return driver.getCurrentUrl().equals(BASE_URL);
    }

    public boolean resetAppButton() {
        getAddToCartButton("sauce-labs-bolt-t-shirt").click();
        String cartNumber = driver.findElement(cart).getText();
        if (!cartNumber.equals("1")) {
            return false;
        }
        driver.findElement(wrapperMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(sidebar));
        WebElement ResetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(wrapperReset));
        safeClick(ResetBtn);
        String afterReset = driver.findElement(cart).getText();
        return afterReset.isEmpty();
    }
    public boolean verifyIfItemsPricesCorrect() {
        int totalItems = driver.findElements(allItems).size();

        for (int i = 0; i < totalItems; i++) {
            List<WebElement> items = driver.findElements(allItems);
            List<WebElement> prices = driver.findElements(allItemPrice);
            String expectedPrice = prices.get(i).getText();
            safeClick(items.get(i));
            String current = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inventory_details_price']"))).getText();
            if (!expectedPrice.equals(current)) {
                return false;
            }
            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(allItems));
        }
        return true;
    }
    public boolean verifyItItemsImagesCorrect() {
        int totalItems = driver.findElements(allItems).size();

        for (int i = 0; i < totalItems; i++) {
            List<WebElement> items = driver.findElements(allItems);
            List<WebElement> images = driver.findElements(allItemImages);
            String imageHandle = images.get(i).getAttribute("src");
            safeClick(items.get(i));
            String current = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='inventory_details_img']"))).getAttribute("src");

            if (!imageHandle.equals(current)) {
                return false;
            }

            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(allItems));
            wait.until(ExpectedConditions.visibilityOfElementLocated(allItemImages));
        }
        return true;
    }
}



