package demo.wrappers;

import org.checkerframework.checker.units.qual.t;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Wrappers {
    WebDriver driver;
    String url = "https://www.youtube.com";


    public Wrappers(WebDriver driver){
        this.driver= driver;
    }


    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(url)) {
            this.driver.get(url);
        }
    }

    public void getAboutlink(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement about=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='About']")));
        about.click();
    }

    public void getMessage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement message =wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        System.out.println("Message at About page is :" + message.getText());
    }

    public void getTab(String tab) throws InterruptedException{
         // Click on the "Movies" tab (you may need to adjust the selector)
            Thread.sleep(3000);
            WebElement tabName = driver.findElement(By.xpath("//a[contains(@title,'"+tab+"')]"));
            tabName.click();
            System.out.println("Successfuly clicked on : "+tab+" Tab");
    }

    public void getSection(String sectionName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[@id='title' and contains(text(),'"+sectionName+"')]")));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", section);
    }


    public String[] getMovieRatingAndCategory() throws InterruptedException{
    // Wait for the section to load and find movie items
         Thread.sleep(3000);
        List<WebElement> movies=driver.findElements(By.xpath("//ytd-grid-movie-renderer"));
        //find rating
        int size = movies.size()-1;
        WebElement rating = driver.findElement(By.xpath("//ytd-grid-movie-renderer["+size+"]/ytd-badge-supported-renderer/div[2]"));
        String movieRating = rating.getAttribute("aria-label");

        //find category 
        WebElement category=driver.findElement(By.xpath("//ytd-grid-movie-renderer["+size+"]//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
        String movieCategory = category.getText();
        String[] ratingCategory = new String[2];  
        ratingCategory[0]=movieRating;
        ratingCategory[1]=movieCategory;

        return ratingCategory;   
    }


    public boolean scrollToRight(String topSection){
        Boolean status;
        try{ 
        Thread.sleep(3000);        
        List<WebElement> sections=driver.findElements(By.xpath("//div[@id='dismissible']//span[@id='title']"));
        //System.out.println("Section size is:" + sections.size());
        for(WebElement section: sections) {
            int count=1;
           // System.out.println("Section Name is :" +section.getText());
            if(section.getText().contains(topSection)){       
                List<WebElement> nextBtn = section.findElements(By.xpath("//button[@aria-label='Next']"));
                //System.out.println("Button count is: "+ nextBtn.size());
                for(int i=0;i<=count;i++){
                     while(nextBtn.get(i).isDisplayed()) {
                         JavascriptExecutor js = (JavascriptExecutor)driver;
                         js.executeScript("arguments[0].click();",nextBtn.get(i));
                     }
            }
                 count++;
        }
    }  
             status=true;
        }catch(Exception e){
            //System.out.println("Exception occurred :");
            //e.printStackTrace();
            status=false;
        }
        return status;
    }

    public int getTitleAndTrackCount(){
        int trackCount=0;
        try{
       List<WebElement> playlistCount= driver.findElements(By.xpath("//ytd-item-section-renderer[1]//ytd-compact-station-renderer"));
       int size= playlistCount.size();
       String title= driver.findElement(By.xpath("//ytd-item-section-renderer[1]//ytd-compact-station-renderer["+size+"]/div[1]/a[1]/h3")).getText();
       System.out.println("Title of playlist is " + title);
     
        WebElement counts = driver.findElement(By.xpath("//ytd-item-section-renderer[1]//ytd-compact-station-renderer["+size+"]/div[1]/a[1]/p[normalize-space(@id='video-count-text')]"));
        String count= counts.getText();
        trackCount=Integer.parseInt(count.split(" ")[0]);
        System.out.println("Track count is: "+ trackCount); 

        }   catch(Exception e){
                System.out.println("Exception occurred");
                e.printStackTrace();
     }
        return trackCount;
    }


 


    public void getNewsTitleLikeCountAndBody(){
        int likeCount = 0;  
        int totalLikes=0;  
        List<WebElement> newsList = driver.findElements(By.xpath("//ytd-rich-section-renderer[2]//ytd-rich-item-renderer"));

        for(int i = 0; i < Math.min(newsList.size(), 3); i++){
            WebElement news = newsList.get(i);
            String newsTitle = news.findElement(By.xpath(".//a[@id='author-text']/span")).getText();
            String newsBody  = news.findElement(By.xpath(".//div[@id='body']")).getText();

            //Print Title and body
            System.out.println("News Title is  : " + newsTitle);
            System.out.println("News Body is   : " + newsBody);
            
            WebElement newsLikes = null;
            try{
                newsLikes= news.findElement(By.xpath(".//span[contains(@aria-label,'like')]"));
                if(newsLikes.getAttribute("aria-label").equals("")){
                    likeCount = 0;
                }else {
                    likeCount = Integer.parseInt(newsLikes.getAttribute("aria-label").split(" ")[0]);
                }
            }catch(NoSuchElementException e){
                System.out.println("Element not found with either XPath.");
                likeCount =0;
            }
             totalLikes = totalLikes + likeCount;
        }      
             //Print Total like count
           System.out.println("Total Likes counts is : " + totalLikes);
        }
 
 
    public void searchResult(String searchName) throws InterruptedException{
        WebElement searchBox = driver.findElement(By.name("search_query"));
        searchBox.sendKeys(Keys.CONTROL+"a");
        searchBox.sendKeys(Keys.DELETE);
        searchBox.sendKeys(searchName);

        WebElement searchButton = driver.findElement(By.xpath("//button[@aria-label='Search']"));
        searchButton.click();
        System.out.println("Successfully performed search for "+ searchName);
        Thread.sleep(3000);
        }

    public void getViewsCount(String sectionName) throws InterruptedException{
      // Total views count
      long totalViews = 0;
         // Scroll until total views reach 10 crore
         while (totalViews < 100000000) {
             // Wait for results to load
             Thread.sleep(2000);
            
             // Find video elements
             List <WebElement> viewCounts = driver.findElements(By.xpath("//div[@id='metadata-line']/span[contains(text(),'views')]"));
             
             for (WebElement view : viewCounts) {
                 // Extract view count
                 String viewsText = view.getText().split(" ")[0];
                 long views = parseViewCount(viewsText);
                 totalViews += views;

                 // Break if we've reached 10 crore
                 if (totalViews >= 10000000) {
                     break;
                 }
             }
             // Scroll down
             ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000);");
             
         }
         System.out.println("Total views reached: " + totalViews + "for " + sectionName);

    }

     // Method to parse view count from string
     public static long parseViewCount(String input) {
        // Remove any whitespace and convert to Upper case for easier matching
        input = input.trim().toUpperCase();        
        // Determine the multiplier based on the suffix
        long multiplier = 1;
        if (input.endsWith("M")) {
            multiplier = 1000000;
            input = input.replace("M", "").trim();
        } else if (input.endsWith("B")) {
            multiplier = 1000000000;
            input = input.replace("B", "").trim();
        } else if (input.endsWith("K")) {
            multiplier = 1000;
            input = input.replace("K", "").trim();
        }

        // Parse the numeric part
        double numericValue = Double.parseDouble(input);
        
        // Multiply to get the final long value
        return (long) (numericValue * multiplier);
    }
}

           

          