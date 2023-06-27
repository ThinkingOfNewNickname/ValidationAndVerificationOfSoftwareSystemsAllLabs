package com.onlinegamestore.onlinegamestore.webtests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserLogin {

    @Test
    public void testLogin() {
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/onlinegamestore/onlinegamestore/webtests/resources/chromedriver_win32/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("http://localhost:8080/");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));

        usernameField.sendKeys("testUser");
        passwordField.sendKeys("testPassword");

        passwordField.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement welcomeMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-message")));

        assertTrue(welcomeMessageElement.getText().contains("Please enter correct username and password"));


        driver.quit();
    }
}
