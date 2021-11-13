package com.org.acn;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flipkart {
	
	static WebDriver driver;
	static long startTime, endTime;
	
	@BeforeClass(groups = "Launch")
	public static void Launch() {
		System.out.println("Before Class");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@BeforeMethod(groups = "Launch")
	public void beforeMethod() {
		startTime = System.currentTimeMillis();

	}
	
	@Parameters({"mobiles"})
	@Test(groups = "mobileSearch")
	public void mobileSearch(String value) {
		try {
			
			WebElement button = driver.findElement(By.xpath("//button[text()='✕']"));
			button.isDisplayed();
			button.click();
			
		} catch (Exception e) {
			System.out.println("Pop up is not displayed");
		}
		
		driver.findElement(By.name("q")).sendKeys(value,Keys.ENTER);
		WebElement element = driver.findElement(By.xpath("(//div[@class='_4rR01T'])[1]"));
		element.click();

	}
	
	@Test(groups = "mobileSearch")
	public void windowHandling() {
		String parentWindow = driver.getWindowHandle();
		
		Set<String> allWindow = driver.getWindowHandles();
		for (String a : allWindow) {
			if (!a.equals(parentWindow)) {
				driver.switchTo().window(a);
			}
		}

	}
	
	@Test(groups = "Launch")
	public void dropDown() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	@AfterMethod(groups = "Launch")
	public void afterMethod() {
		endTime = System.currentTimeMillis();
		long t = endTime - startTime;
		System.out.println("Time taken to complete task is " + t );

	}
	
	@AfterClass(groups = "Launch")
	public static void closeBrowser() throws IOException {
		TakesScreenshot s = (TakesScreenshot)driver;
		File source = s.getScreenshotAs(OutputType.FILE);
		String title1 = driver.getTitle();
		File target = new File(".//target//report" + title1 + ".png");
		FileUtils.copyFile(source, target);
		driver.quit();

	}

}
