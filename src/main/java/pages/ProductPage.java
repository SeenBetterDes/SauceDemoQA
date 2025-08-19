package pages;

import org.openqa.selenium.By;
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
    By item_remove = By.id("remove-sauce-labs-bike-light");
    By item_remove_global = By.linkText("Remove");
    By cart = By.xpath("//a[@class='shopping_cart_link']");


    By all_items = By.xpath("//div[@data-test='inventory-item-name']");
    By all_item_names = By.xpath("//div[@data-test='inventory-item-name']");
    By all_items_price = By.xpath("//div[@class='inventory_item_price']");
    By all_items_images = By.xpath("//img[@class='inventory_item_img']");
    By sorting_button = By.xpath("//select[@class='product_sort_container']");
    By add_items = By.cssSelector("button.btn_inventory");
    By wrapper_menu = By.id("react-burger-menu-btn");
    By wrapper_menu_options = By.xpath("//nav[@class='bm-item-list']//a");
    By wrapper_about = By.xpath("//a[@id='about_sidebar_link']");
    By wrapper_logout = By.cssSelector("#logout_sidebar_link");
    By wrapper_reset = By.cssSelector("#reset_sidebar_link");


    public static final String DOG_KEYWORD = "sl-404";
    public static final String EXPECTED_URL = "https://saucelabs.com/";
    public static final String BASE_URL = "https://www.saucedemo.com/";

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    public boolean error_check() {
        boolean isPassed;
        WebElement single_item = wait.until(ExpectedConditions.elementToBeClickable(item));
        single_item.click();
        WebElement single_item_del = wait.until(ExpectedConditions.elementToBeClickable(item_remove));
        single_item_del.click();
        String number_item = driver.findElement(cart).getText();
        Integer number = Integer.parseInt(number_item);
        if (number.equals(1)) {
            return true;
        } else {
            isPassed = false;

        }

        return isPassed;
    }

    // SHEET 2

    public List<WebElement> itemsPresent() {
        return driver.findElements(all_items);
    }

    public int checkAmountOfItems() {
        return itemsPresent().size();
    }

    public Select sortingFunctionality() {
        WebElement sorting = driver.findElement(sorting_button);
        return new Select(sorting);
    }

    public WebElement getAddToCartButton(String productName) {
        return driver.findElement(By.cssSelector("button[data-test='add-to-cart-" + productName + "']"));
    }

    public WebElement removeItemButton(String productName) {
        return driver.findElement(By.cssSelector("button[data-test='remove-" + productName + "']"));
    }
    public List<String> getAllItemNames() {
        return driver.findElements(all_item_names).stream()
                .map(WebElement::getText)
                .toList();
    }



    // Methods
    public List<String> sortingAscending() {
        sortingFunctionality().selectByValue("za");
        sortingFunctionality().selectByValue("az");
        return driver.findElements(all_items).stream()
                .map(WebElement::getText)
                .toList();
    }


    public List<String> sortingDescending() {
        sortingFunctionality().selectByValue("za");
        return driver.findElements(all_items).stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> sortingPriceAscending() {
        sortingFunctionality().selectByValue("lohi");
        return driver.findElements(all_items_price).stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> sortingPrinceDescending() {
        sortingFunctionality().selectByValue("hilo");
        return driver.findElements(all_items_price).stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean checkCartFunctionality() {
        driver.findElement(cart).click();
        return driver.getCurrentUrl().contains("cart.html");
    }

    public boolean checkCartButtonFunctionality() {
        List<WebElement> item_add = driver.findElements(add_items);
        int count_item = 0;
        for (WebElement item : item_add) {
            item.click();
            count_item++;
            String cart_number = driver.findElement(cart).getText();
            int cartNumber = Integer.parseInt(cart_number);
            if (cartNumber != count_item) {
                return false;
            }
        }
        return true;
    }

    public boolean removeButtonFunctionality() {
        getAddToCartButton("sauce-labs-bolt-t-shirt").click();
        String cart_number = driver.findElement(cart).getText();
        int cartNumber = Integer.parseInt(cart_number);
        if (cartNumber == 1) {
            removeItemButton("sauce-labs-bolt-t-shirt").click();
        }
        cart_number = driver.findElement(cart).getText();
        cartNumber = cart_number.isEmpty() ? 0 : Integer.parseInt(cart_number);
        if (cartNumber != 0) {
            return false;
        }
        return true;
    }

    public boolean removeMultipleButtonFunctionality() {
        List<WebElement> item_add = driver.findElements(add_items);
        int count_item = 0;
        for (WebElement item : item_add) {
            item.click();
            count_item++;
            String cart_number = driver.findElement(cart).getText();
            int cartNumber = Integer.parseInt(cart_number);
            if (cartNumber == count_item) {
                List<WebElement> item_remove = driver.findElements(item_remove_global);
                for (WebElement remove : item_remove) {
                    remove.click();
                    if (cartNumber == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<String> checkReactMenu() {
        List<String> options = new ArrayList<>();
        driver.findElement(wrapper_menu).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(wrapper_menu_options));
        List<WebElement> wrap_option = driver.findElements(wrapper_menu_options);
        for (WebElement item : wrap_option) {
            options.add(item.getText().trim());
        }
        return options;
    }

    public boolean checkAboutButton() {
        driver.findElement(wrapper_menu).click();
        wait.until(ExpectedConditions.elementToBeClickable(wrapper_about)).click();
        wait.until(driver -> driver.getCurrentUrl().equals(EXPECTED_URL));
        return driver.getCurrentUrl().equals(EXPECTED_URL);
    }

    public boolean checkLogoutButton() {
        driver.findElement(wrapper_menu).click();
        wait.until(ExpectedConditions.elementToBeClickable(wrapper_logout)).click();
        wait.until(driver -> driver.getCurrentUrl().equals(BASE_URL));
        return driver.getCurrentUrl().equals(BASE_URL);
    }

    public boolean resetAppButton() {
        getAddToCartButton("sauce-labs-bolt-t-shirt").click();
        String cartNumber = driver.findElement(cart).getText();
        if (!cartNumber.equals("1")) {
            return false;
        }
        driver.findElement(wrapper_menu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(wrapper_reset)).click();
        String afterReset = driver.findElement(cart).getText();
        return afterReset.isEmpty();
    }
    public boolean verifyIfItemsPricesCorrect() {
        List<WebElement> items = driver.findElements(all_items);
        List<WebElement> prices = driver.findElements(all_items_price);

        for(int i=0;i<items.size();i++) {
            String expectedPrises = prices.get(i).getText();
            items.get(i).click();
            String current = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='inventory_details_price']"))).getText();
            if(!expectedPrises.equals(current)) {
                return false;
            }
            driver.navigate().back();
            items = driver.findElements(all_items);
            prices = driver.findElements(all_items_price);
        }
        return true;


    }
    public boolean verifyItItemsImagesCorrect() {
        List<WebElement> items = driver.findElements(all_items);
        List<WebElement> images = driver.findElements(all_items_images);
        for(int i=0;i<items.size();i++) {
            String image_handle = images.get(i).getAttribute("src");
            items.get(i).click();
            String current = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='inventory_details_img']"))).getAttribute("src");
            if(!image_handle.equals(current)) {
                return false;
            }
            driver.navigate().back();
            items = driver.findElements(all_items);
            images = driver.findElements(all_items_images);

        }
        return true;

    }



}



