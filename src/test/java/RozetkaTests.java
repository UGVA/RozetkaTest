import com.codeborne.selenide.ElementsCollection;
import entity.Smartphone;
import org.junit.Test;

import java.util.LinkedList;

import static helpers.Connect_db.writeToDB;
import static helpers.SendEmail.sendEmails;
import static helpers.Utils.writeToExcel;
import static pages.MenuPage.*;
import static pages.SmartphonePage.*;

public class RozetkaTests extends SetupTest {
    private LinkedList<Smartphone> smartphoneTopSalesLinkedList;
    private LinkedList<Smartphone> topSalesLinkedList;
    private LinkedList<Smartphone> dataLinkedList;
    private ElementsCollection listItems;

    @Test
    public void rozetkaNameSmartphoneTest() {
        openMenuSmartphonesAndTV();
        openMenuPhones();
        openMenuSmartphones();

        listItems = getSmartphoneFromPage();
        smartphoneTopSalesLinkedList = getSmartphoneName(listItems);

        openPage(2);
        listItems = getSmartphoneFromPage();
        smartphoneTopSalesLinkedList = getSmartphoneName(listItems);

        openPage(3);
        listItems = getSmartphoneFromPage();
        smartphoneTopSalesLinkedList = getSmartphoneName(listItems);

        sendEmails("report.txt");
    }

    @Test
    public void rozetkaThigsForHours() {
        openMenuThingsForHours();
        openMenuChemistry();
        openMenuForWashing();
        openMenuWashingPowder();
        listItems = getItemsFromPage();
        dataLinkedList = getDataItemFromPage(listItems, 100, 300);
        for (int i = 2; i < 6; i++) {
            openPage(i);
            listItems = getItemsFromPage();
            dataLinkedList.addAll(getDataItemFromPage(listItems, 100, 300));
        }
        writeToDB(dataLinkedList);

    }

    @Test
    public void rozetkaTopSalesSmartphone() {

        openMenuSmartphonesAndTV();
        openMenuPhones();
        openMenuSmartphones();

        listItems = getSmartphoneFromPage();
        smartphoneTopSalesLinkedList = getTopSalesFromPage(listItems);
        dataLinkedList = getDataItemFromPage(listItems, 3000, 6000);

        openPage(2);
        listItems = getSmartphoneFromPage();
        topSalesLinkedList = getTopSalesFromPage(listItems);
        smartphoneTopSalesLinkedList.addAll(topSalesLinkedList);
        dataLinkedList.addAll(getDataItemFromPage(listItems, 3000, 6000));


        openPage(3);
        listItems = getSmartphoneFromPage();
        topSalesLinkedList = getTopSalesFromPage(listItems);
        smartphoneTopSalesLinkedList.addAll(topSalesLinkedList);
        dataLinkedList.addAll(getDataItemFromPage(listItems, 3000, 6000));

        openPage(4);
        dataLinkedList.addAll(getDataItemFromPage(listItems, 3000, 6000));

        openPage(5);
        dataLinkedList.addAll(getDataItemFromPage(listItems, 3000, 6000));
        writeToExcel(smartphoneTopSalesLinkedList, "TOP", dataLinkedList, "Smartphon3000to6000");

        sendEmails("Test.xlsx");
    }
}
