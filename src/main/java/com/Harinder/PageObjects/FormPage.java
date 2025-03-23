package com.Harinder.PageObjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;

import com.Harinder.Utils.AndroidActions;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage extends AndroidActions {

	AndroidDriver driver;

	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;
	// driver.findElement(By.id("com.androidsample.generalstore:id/nameField")

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
	private WebElement femaleOption;
	// driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']"))

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
	private WebElement maleOption;

	@AndroidFindBy(id = "android:id/text1")
	private WebElement countrySelection;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement shopButton;

//	public void setActivity() {
//		((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
//		        "appPackage", "com.androidsample.generalstore",
//		        "appActivity", "com.androidsample.generalstore.MainActivity"
//		));
//	}

	public void setActivity() {

		System.out.println("Checking current context...");

		// Get all available contexts
		Set<String> contexts = driver.getContextHandles();
		System.out.println("Available contexts: " + contexts);

		// If WebView exists, switch back to Native
		for (String context : contexts) {
			if (context.contains("WEBVIEW")) {
				System.out.println("Switching back to NATIVE_APP...");
				driver.context("NATIVE_APP");
				break;
			}
		}

		// Use Android back button to return to the app
		driver.navigate().back();
		driver.navigate().back();

		System.out.println("Successfully switched back to Native App.");
	}

	public void setNameField(String name) {
		nameField.sendKeys(name);
		driver.hideKeyboard();
	}

	public void setGender(String gender) {
		if (gender.contains("female"))
			femaleOption.click();
		else
			maleOption.click();
	}

	public void setCountrySelection(String countryName) {
		countrySelection.click();
		scrollToText(countryName);
		driver.findElement(By.xpath("//android.widget.TextView[@text='" + countryName + "']")).click();
	}

	public ProductCatalogue submitForm() {
		shopButton.click();
		return new ProductCatalogue(driver);
	}

}
