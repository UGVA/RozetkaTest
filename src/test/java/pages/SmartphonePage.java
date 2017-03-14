package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import entity.Smartphone;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.Utils.writeToFile;

public class SmartphonePage {

    private static final String itemOnPageWithPopularityLabelXpath = ".//i[@class='g-tag g-tag-icon-middle-popularity sprite']";
    private static final String NameXpath = ".//div[@class='g-i-tile-i-title clearfix']/a";
    private static final String PriceXpath = ".//div[@class='g-price-uah']";
    private static final String itemWithPromotion = "goods_item_with_promotion";
    private static final String itemFromPage = "//div[@class='g-i-tile g-i-tile-catalog g-i-large-tile-catalog']";

    public static LinkedList<Smartphone> getTopSalesFromPage(ElementsCollection listItems) {
        LinkedList<Smartphone> LinkedList = new LinkedList<>();
        String title;
        String price;

        for (SelenideElement item : listItems) {
            if (item.should(exist).find(By.xpath(itemOnPageWithPopularityLabelXpath)).exists()) {
                Smartphone smartphone = new Smartphone();
                title = item.findElement(By.xpath(NameXpath)).getText();
                smartphone.setName(title);
                price = item.findElement(By.xpath(PriceXpath)).getText();
                smartphone.setPrice(price);
                LinkedList.add(smartphone);
            }
        }
        return LinkedList;
    }

    public static LinkedList<Smartphone> getSmartphoneName(ElementsCollection listSmartphones) {
        LinkedList<Smartphone> smartphoneLinkedList = new LinkedList<>();
        String smartphoneTitle;

        for (SelenideElement listSmartphone : listSmartphones) {
            Smartphone smartphone = new Smartphone();
            smartphoneTitle = listSmartphone.findElement(By.xpath(NameXpath)).getText();
            smartphone.setName(smartphoneTitle);
            smartphoneLinkedList.add(smartphone);
            writeToFile(smartphoneTitle + "\n");
        }
        return smartphoneLinkedList;
    }

    public static void openPage(int numberPage) {
        $(By.id("page" + numberPage)).click();
        $(By.xpath("//div[@name='preloader']")).should(disappear);

        WebDriverWait wait = new WebDriverWait(getWebDriver(), 30);

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) getWebDriver()).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
    }

    public static ElementsCollection getSmartphoneFromPage() {
        return $$(By.name(itemWithPromotion));
    }

    public static ElementsCollection getItemsFromPage() {
        return $$(By.xpath(itemFromPage));
    }

    public static LinkedList<Smartphone> getDataItemFromPage(ElementsCollection listItems, int from, int to) {
        LinkedList<Smartphone> LinkedList = new LinkedList<>();
        String title;
        String price;

        for (SelenideElement item : listItems) {
            Smartphone smartphone = new Smartphone();
            title = item.findElement(By.xpath(NameXpath)).getText();
            smartphone.setName(title);
            price = item.findElement(By.xpath(PriceXpath)).getText();
            price = price.replaceAll("[^\\d]", "");
            int i = Integer.decode(price);
            if (from < i && i < to) {
                smartphone.setPrice(price);
                LinkedList.add(smartphone);
            }
        }
        return LinkedList;
    }

}
