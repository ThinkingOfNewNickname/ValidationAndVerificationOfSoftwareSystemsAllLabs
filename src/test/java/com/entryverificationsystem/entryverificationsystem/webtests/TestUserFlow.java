package com.entryverificationsystem.entryverificationsystem.webtests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserFlow {
    static WebDriver driver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/entryverificationsystem/entryverificationsystem/webtests/resources/chromedriver_win32/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginInvalidCredentials() {
        driver.get("http://localhost:8080/logout");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));

        usernameField.sendKeys("testUser");
        passwordField.sendKeys("testPassword");

        passwordField.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-message")));

        assertTrue(errorMessageElement.getText().contains("Please enter correct username and password"));
    }

    @Test
    public void testLoginValidCredentials() {
        driver.get("http://localhost:8080/logout");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));

        usernameField.sendKeys("test");
        passwordField.sendKeys("123");

        passwordField.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement welcomeMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        assertTrue(welcomeMessageElement.getText().contains("Welcome"));
    }

    @Test
    public void testNewUserRegisterExistingUser() {
        driver.get("http://localhost:8080/register");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement emailField = driver.findElement(By.name("email"));

        usernameField.sendKeys("test");
        passwordField.sendKeys("123");
        emailField.sendKeys("notReal@gmai.com");

        passwordField.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-message")));

        assertTrue(errorMessageElement.getText().contains("Username already exists"));
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }
}
