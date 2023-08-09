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
    }

    private void cookies(WebDriver driver, By by) {   //a private method that uses explicit wait
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    @Given("I enter my {string}")
    public void iEnterMy(String email)  {
        int randomNr = 10 + (int) (Math.random() * 1000);
        if (!email.equals("")) {
            email = randomNr + email;
        }
        driver.findElement(By.id("email")).sendKeys(email);

    }

    @And("I enter a {string}")
    public void iEnterA(String username)  {
        int randomNr = 10 + (int) (Math.random() * 1000);

        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        if (!username.equals("clunlin")) {
            username = username + randomNr;
        }
        driver.findElement(By.name("username")).sendKeys(username);

    }

    @And("I select a {string}")
    public void iSelectA(String password)  {
        driver.findElement(By.id("new_password")).sendKeys(password);

    }

    @When("I click sign up")
    public void iClickSignUp() {
        driver.findElement(By.id("create-account-enabled")).click();
    }

    @Then("My registration will be {string}")
    public void myRegistrationWillBe(String result)  {

        if (result.equalsIgnoreCase("yes")) {
            String actual = getText(driver, By.className("!margin-bottom--lv3"));
            String expected = "Check your email";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("tooLong")) {
            String actual = getText(driver, By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"));  //Please check your entry and try again.
            String expected = "Enter a value less than 100 characters long";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("occupiedName")) {
            String actual = getText(driver, By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"));
            String expected = "Great minds think alike - someone already has this username. If it's you, log in.";
            assertEquals(expected, actual);
        } else if (result.equalsIgnoreCase("noEmail")) {
            String actual = getText(driver, By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span"));
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        }
    }

    private String getText(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element.getText();
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}




