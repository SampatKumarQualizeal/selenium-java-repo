package gluecode;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Shared {
    public WebDriver driver;
    public FluentWait<WebDriver> wait;
    public Utils utils;
    public Scenario scenario;
    private Properties properties;

    @Before
    public void setUp(Scenario scenario) throws IOException {
        this.scenario = scenario;
        properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/config.properties"));

        String browser = properties.getProperty("browser");
        String url = properties.getProperty("url");

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class);

        utils = new Utils(driver);
        driver.manage().window().maximize();
        driver.get(url);
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        scenario.log("Starting step: " + scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        scenario.log("Finished step: " + scenario.getName());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}