package demo;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;
        

        @Test (enabled = true)
        public void testCase01(){
        System.out.println("Start Test Case: TestCase01"); 
        // Open YouTube website
        Wrappers wrapper = new Wrappers(driver);
        wrapper.navigateToHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Navigation to YouTube.com Failed");
        //Go to About Page
        wrapper.getAboutlink();
        Assert.assertTrue(driver.getCurrentUrl().contains("about"), "Navigation to about page failed");

        //Print Message at About Page
        wrapper.getMessage();
        System.out.println("End TestCase : TestCase01");
       
        }


        @Test(enabled=true)
        public void testCase02() throws InterruptedException{
        System.out.println("Start TestCase: TestCase02");
        SoftAssert softAssert = new SoftAssert();
        // Open YouTube website
        Wrappers wrapper = new Wrappers(driver);
        wrapper.navigateToHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Navigation to YouTube.com Failed");
        //Click on Movies section
        wrapper.getTab("Movies");
        //In the 'Top Selling' section, scroll to the extreme right. 
        wrapper.scrollToRight("Top selling");
         //Get all the movies
        String[] ratingCategory=wrapper.getMovieRatingAndCategory();
        softAssert.assertTrue(ratingCategory[0].contains("A"), "Movie is marked as Mature.");

        // Check if the movie category exists
        String category = ratingCategory[1];
        softAssert.assertTrue(category != null && !category.isEmpty(), "Movie category exists.");
       
        // Assert all
        softAssert.assertAll();
        System.out.println("End TestCase : TestCase02");
    }

         @Test(enabled=true)
         public void testCase03() throws InterruptedException{

         System.out.println("Start TestCase : TestCase03");
         SoftAssert softAssert = new SoftAssert();
         Wrappers wrapper = new Wrappers(driver);
         wrapper.navigateToHome();
         Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Navigation to YouTube.com Failed");
         //Go to the "Music" tab and in the 1st section, scroll to the extreme right. 
         wrapper.getTab("Music");
         wrapper.scrollToRight("India's Biggest Hits");
         //Print the name of the playlist.
         int count =wrapper.getTitleAndTrackCount();
         //Soft Assert on whether the number of tracks listed is less than or equal to 50.
         softAssert.assertTrue(count<=50, "the number of tracks listed is not less than or equal to 50");

         softAssert.assertAll();

         System.out.println("End TestCase : TestCase03");
    }

       
    
         @Test(enabled=true)
         public void testCase04() throws InterruptedException{
          System.out.println("Start TestCase : TestCase04");
          SoftAssert softAssert = new SoftAssert();
          Wrappers wrapper = new Wrappers(driver); 
          wrapper.navigateToHome();
          softAssert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Navigation to YouTube.com Failed");
          wrapper.getTab("News");
          wrapper.getSection("Latest news post");
          wrapper.getNewsTitleLikeCountAndBody();
          softAssert.assertAll();
          System.out.println("End TestCase : TestCase04");
    }

    @Test(dataProvider = "excelData" ,enabled=true)
    public void testCase05(String sectionName) throws InterruptedException{
        System.out.println("Start TestCase : TestCase04");
          //SoftAssert softAssert = new SoftAssert();
          Wrappers wrapper = new Wrappers(driver); 
          wrapper.navigateToHome();
          wrapper.searchResult(sectionName);
          wrapper.getViewsCount(sectionName);
    }
    
    /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest (enabled=false)
        public void endTest() {
                driver.close();
                driver.quit();

        }
}