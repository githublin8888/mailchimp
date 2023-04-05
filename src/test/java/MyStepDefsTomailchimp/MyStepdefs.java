package MyStepDefsTomailchimp;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    //int randomNr = 10 + (int) (Math.random() * 1000);

    @Given("I have chosen a {string} and logged in Mailchimp")
    public void iHaveChosenAandLoggedinMailchimp(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get("https://login.mailchimp.com/signup/");

            driver.manage().window().maximize();
            cookies(driver, By.id("onetrust-reject-all-handler")); //click the cookies


        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://login.mailchimp.com/signup/");
            driver.manage().window().maximize();
            cookies(driver, By.id("onetrust-reject-all-handler"));
        }

        wait = new WebDriverWait(driver, 20);

    }

    private void cookies(WebDriver driver, By by) {   //en privat metod som använder sig av explicit wait
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    @Given("I enter my {string}")
    public void iEnterMy(String email) throws InterruptedException {
        int randomNr = 10 + (int) (Math.random() * 1000);
        if (!email.equals("")) {
            email = randomNr + email;
        }
        driver.findElement(By.id("email")).sendKeys(email);

        Thread.sleep(2000);
    }

    @And("I enter a {string}")
    public void iEnterA(String username) throws InterruptedException {
        int randomNr = 10 + (int) (Math.random() * 1000);

        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();

        if (!username.equals("clunlin")) {
            username = username + randomNr;
        }
        driver.findElement(By.name("username")).sendKeys(username);

        Thread.sleep(2000);
    }

    @And("I select a {string}")
    public void iSelectA(String password) throws InterruptedException {
        driver.findElement(By.id("new_password")).sendKeys(password);
        Thread.sleep(2000);
    }

    @When("I click sign up")
    public void iClickSignUp() {
        driver.findElement(By.id("create-account-enabled")).click();
    }

    @Then("My registration will be {string}")
    public void myRegistrationWillBe(String result) throws InterruptedException {
        Thread.sleep(5000);

        if (result.equalsIgnoreCase("yes")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("!margin-bottom--lv3")));
            String actual = driver.findElement(By.className("!margin-bottom--lv3")).getText();
            String expected = "Check your email";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("tooLong")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"))); //By.cssSelector("#av-flash-errors li")
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")).getText();  //Please check your entry and try again.
            String expected = "Enter a value less than 100 characters long";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("occupiedName")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")));
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")).getText();
            String expected = "Great minds think alike - someone already has this username. If it's you, log in.";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("noEmail")) {
            String actual = getText();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        }
    }

    private String getText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")));
        String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")).getText();
        return actual;
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}




