package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selectors.WithText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MenuPage {
    private static Actions actions = new Actions(getWebDriver());
    private static String smartphoneAndTVMenu = "Смартфоны, ТВ и электроника";
    private static String phonesMenu = "//h4/a[contains(.,'Телефоны')]";
    private static String phone = "телефоны";
    private static String smartPhone = "Смартфоны";

    private static String thingsForHours = "Товары для дома";
    private static String chemistry = "Бытовая химия";
    private static String forWashing = "Для стирки";
    private static String washingPowder = "Порошок";

    public static void openMenuSmartphonesAndTV() {
        actions.moveToElement($(new WithText(smartphoneAndTVMenu))).click().build().perform();
    }

    public static void openMenuPhones() {
        if ($(new WithText(smartphoneAndTVMenu)).isDisplayed()) {
            actions.moveToElement($(new WithText(phone))).build().perform();
            openMenuSmartphonesAndTV();
            actions.moveToElement($(By.xpath(phonesMenu))).click().build().perform();
        } else {
            actions.moveToElement($(By.xpath(phonesMenu))).click().build().perform();
        }
    }

    public static void openMenuSmartphones() {
        $(byText(smartPhone)).click();
    }

    public static void openMenuThingsForHours() {
        actions.moveToElement($(new WithText(thingsForHours))).click().build().perform();
    }

    public static void openMenuChemistry() {
        actions.moveToElement($(new WithText(chemistry))).click().build().perform();
    }

    public static void openMenuForWashing() {
        actions.moveToElement($(new WithText(forWashing))).click().build().perform();
    }

    public static void openMenuWashingPowder() {
        actions.moveToElement($(new WithText(washingPowder))).click().build().perform();
    }
}