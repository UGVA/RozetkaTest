import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;
import helpers.Utils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.Connection;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static helpers.Connect_db.closeConnection;
import static helpers.Utils.url;


public class SetupTest {
    static final org.slf4j.Logger Log = org.slf4j.LoggerFactory.getLogger("");
    static private Connection connection = null;

    @Rule
    public final ScreenShooter screenShooter = ScreenShooter.failedTests();


    @BeforeClass
    public static void setUp() throws IOException {
        Utils.getConfig();
        Utils.deleteFile();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        timeout = 30000;
        baseUrl = url;
        WebDriverRunner.setWebDriver(driver);

    }

    @AfterClass
    public static void closeDriver() {
        closeWebDriver();
    }

    @Before
    public void preCondition() {
        open("/");
    }

    @After
    public void tearDown() {
        if (connection != null) {
            closeConnection(connection);
        }

    }
}