package com.moodleBrowser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MoodleCompatibility {



	public WebDriver driver;
	@Parameters({"username","password","browser"})
	@Test
	public void browserLaunch(String username,String password,String browser) {

		switch (browser) {
		case "chrome":

			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();

			break;

		case "firefox":

			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			break;
			
		case "edge":
			
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
			break;
		}

		String url="http://localhost/my/";
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[.='Log in']")).click();
		
		String title = driver.getTitle();
		String exc="Dashboard | moddle";
		System.out.println("Page Title: "+title);
		Assert.assertEquals(title, exc);
		
}
	@AfterMethod
	public void snapShot() 
	{
		TakesScreenshot snap= (TakesScreenshot)driver;
		File src = snap.getScreenshotAs(OutputType.FILE);
		File dsc=new File("C:\\Users\\skal\\eclipse-workspace\\moodle\\snaps\\"+timeStamps()+".png");
		try {
			FileHandler.copy(src, dsc);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public String timeStamps(){
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	}
	
	@AfterClass
	void close()
	{
		driver.quit();
	}

}
