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
    public void iHaveChosenAandLoggedinMailchimp(String browser) throws InterruptedException {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get("https://login.mailchimp.com/signup/");

            driver.manage().window().maximize();

            WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
            element.click(); //cookies


        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://login.mailchimp.com/signup/");
            driver.manage().window().maximize();
            WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
            element.click();
            Thread.sleep(2000);
        }

        wait = new WebDriverWait(driver, 20);

    }

    @Given("I enter my {string}")
    public void iEnterMy(String email) throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys(email);
        // Thread.sleep(3000);
    }

    @And("I enter a {string}")
    public void iEnterA(String username) throws InterruptedException {

        WebElement field = driver.findElement(By.name("username"));
        field.click();
        field.clear();
        field.sendKeys(username);
        Thread.sleep(2000);
    }

    @And("I select a {string}")
    public void iSelectA(String password) throws InterruptedException {
        driver.findElement(By.id("new_password")).sendKeys(password);
        Thread.sleep(2000);
    }

    @When("I click sign up")
    public void iClickSignUp() throws InterruptedException {

        driver.findElement(By.id("create-account-enabled")).click();
        Thread.sleep(2000);
    }

    @Then("My registration will be finished")
    public void myRegistrationWillBeFinished() throws InterruptedException {
        if (driver.findElement(By.cssSelector("#av-flash-errors li")).isDisplayed()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#av-flash-errors li")));
            String actual = driver.findElement(By.cssSelector("#av-flash-errors li")).getText();
            String expected = "Please check your entry and try again.";
            assertEquals(expected, actual);
        } else if (driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).isDisplayed()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^=\"/?username=\"]")));
            String actual = driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).getText();
            String expected = "log in";
            assertEquals(expected, actual);
        } else if (driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")).isDisplayed()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")));
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")).getText();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        } else if (driver.findElement(By.cssSelector("!margin-bottom--lv3")).isDisplayed()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("!margin-bottom--lv3")));
            String actual = driver.findElement(By.className("!margin-bottom--lv3")).getText();
            String expected = "Check your email";
            assertEquals(expected, actual);
        }

    }
    @After
    public void tearDown () {
        driver.close();
        driver.quit();
    }}
/*
    @Then("My registration will be {string}")
    public void myRegistrationWillBe(String result) throws InterruptedException {

        if (result.equals("tooLong")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#av-flash-errors li")));
            String actual = driver.findElement(By.cssSelector("#av-flash-errors li")).getText();
            String expected = "Please check your entry and try again.";
            assertEquals(expected, actual);
        } else if (result.equals("occupiedName")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^=\"/?username=\"]")));
            String actual = driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).getText();
            String expected = "log in";
            assertEquals(expected, actual);
        } else if (result.equals("noEmail")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")));
            String actual = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span")).getText();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        } else if (driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")).isDisplayed() && result.equals("yes")) {
            String actual = driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).getText();
            String expected = "log in";
            assertEquals(expected, actual);
        } else if (result.equals("yes")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("!margin-bottom--lv3")));
            String actual = driver.findElement(By.className("!margin-bottom--lv3")).getText();
            String expected = "Check your email";
            assertEquals(expected, actual);
        }


    }*/






/*
else if (driver.findElement(By.cssSelector("a[href^=\\\"/?username=\\\"]")).isDisplayed()&&result.equals("yes") ) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^=\\\"/?username=\\\"]")));
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^=\"/?username=\"]"))); //By.cssSelector(".av-flash li")
            String actual = driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).getText();
            String expected = "log in";
            assertEquals(expected, actual);


            else if (driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")).isDisplayed()&&result.equals("yes") ) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")));
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href^=\"/?username=\"]"))); //By.cssSelector(".av-flash li")
            String actual = driver.findElement(By.cssSelector("a[href^=\"/?username=\"]")).getText();
            String expected = "log in";
            assertEquals(expected, actual);
        }else if (result.equals("yes")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("!margin-bottom--lv3")));
            String actual = driver.findElement(By.className("!margin-bottom--lv3")).getText();
            String expected = "Check your email";
            assertEquals(expected, actual);

        }
 */

