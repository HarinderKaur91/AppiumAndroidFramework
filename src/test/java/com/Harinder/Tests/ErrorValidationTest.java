package com.Harinder.Tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;

public class ErrorValidationTest extends BaseTest {

	
	@BeforeMethod(alwaysRun = true)
	public void preSetup() throws IOException {
		// screen to home page
//		Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.MainActivity");
//		driver.startActivity(activity);
//		//formPage.setActivity();
		
		String command = "adb -s HarinderEmulator shell am start -n com.androidsample.generalstore/com.androidsample.generalstore.MainActivity";
		Runtime.getRuntime().exec(command);

		
		//((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of("intent",
			//	"com.androidsample.generalstore/com.androidsample.generalstore.MainActivity"));
	}

	@Test(enabled = false)
	public void fillForm() throws InterruptedException {
//	driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Harinder Kaur");
		driver.hideKeyboard();
		driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))"));
		driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		// Thread.sleep(2000);
		String toastMessage = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getDomAttribute("name");
		Assert.assertEquals(toastMessage, "Please enter your name");

	}

	@Test(groups= {"Smoke"},enabled = false)
	public void fillForm2() throws InterruptedException {
		
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Harinder Kaur");
		driver.hideKeyboard();
		driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))"));
		driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		// Thread.sleep(2000);
		Assert.assertTrue(driver.findElements(By.xpath("(//android.widget.Toast)")).size() < 1);
	}
}
