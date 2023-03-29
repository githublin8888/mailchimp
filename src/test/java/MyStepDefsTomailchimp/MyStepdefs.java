package MyStepDefsTomailchimp;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I have chosen a {string} and logged in Mailchimp")
    public void iHaveChosenAandLoggedinMailchimp(String browser) throws InterruptedException{


        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get("https://login.mailchimp.com/signup/");

            driver.manage().window().maximize();

            WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
          element.click();

            //driver.findElement(By.id("onetrust-reject-all-handler")).click();//cookies

           // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
           // driver.findElement(By.id("onetrust-reject-all-handler")).click();

        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://login.mailchimp.com/signup/");
            driver.manage().window().maximize();
            WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
            element.click();

        }
        wait = new WebDriverWait(driver, 10);

    }

    @Given("I enter my {string}")
    public void iEnterMy(String email) {
        driver.findElement(By.id("email")).sendKeys(email);
    }

    @And("I enter a {string}")
    public void iEnterA(String username) {
        driver.findElement(By.name("username")).sendKeys(username);
    }

    @And("I select a {string}")
    public void iSelectA(String password) {
        driver.findElement(By.id("new_password")).sendKeys(password);
    }

    @When("I click sign up")
    public void iClickSignUp() {

        driver.findElement(By.id("create-account-enabled")).click();
        //Thread.sleep(5000);
    }



    @Then("My registration will be {string}")
    public void myRegistrationWillBe(String result) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"signup-content\"]/div/div[1]/section/div/h1")));
        if (result.equals("yes")) {

            String actual = driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div[1]/section/div/h1")).getText();
            String expected = "Check your email";
            assertEquals(expected, actual);
        }else if(result.equals("tooLong")){
            String actual = driver.findElement(By.className("invalid-error")).getText();
            String expected = "Enter a value less than 100 characters long";
            assertEquals(expected, actual);
        } else if (result.equals("occupiedName")) {
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")).getText();
            String expected = "Enter a value less than 100 characters long";  //By.cssSelector(".invalid-error")
            assertEquals(expected, actual);
        }else if (result.equals("noEmail")) {
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")).getText();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        }

//By.cssSelector(".\\!margin-bottom--lv3")
    }
    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }



}
  /*
    WebElement element =
      (new WebDriverWait(driver, 10)).until(ExpectedConditions.

                    presenceOfElementLocated(By.id("elementId")));


               @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }

   */


