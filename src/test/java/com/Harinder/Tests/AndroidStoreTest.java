package com.Harinder.Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Harinder.PageObjects.CartPage;
import com.Harinder.PageObjects.FormPage;
import com.Harinder.PageObjects.ProductCatalogue;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidStoreTest extends BaseTest {
	
	//browser stack
	//bs://c6eb6f5e4f0b19e622e481a6e43864c826099569
	int testIteration = 0;

	@BeforeMethod
	public void preSetup() {
		if (testIteration > 0) {
			System.out.println("Resetting app to the main page...");
			// screen to home page
			formPage.setActivity();
			System.out.println("App reset successfully.");
		}
		testIteration++; // Increment after each test run
	}

//There are 3 different ways to run test in this class, thats why used enabled = false becoz at a time, I will use 1 way of ruuning test
	@Test(enabled = false)
	public void fillForm() throws InterruptedException {
		formPage.setNameField("Harinder Kaur");
		formPage.setGender("female");
		formPage.setCountrySelection("Argentina");
		ProductCatalogue productCatalogue = formPage.submitForm();
		productCatalogue.addItemToCartByIndex(0);
		productCatalogue.addItemToCartByIndex(0);
		CartPage cartPage = productCatalogue.goToCartPage();
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		Assert.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
	}

	// getting data from dataprovider directly
	@Test(enabled = false, dataProvider = "getData")
	public void fillFormUsingDataProvider(String name, String gender, String country) throws InterruptedException {
		formPage.setNameField(name);
		formPage.setGender(gender);
		formPage.setCountrySelection(country);
		ProductCatalogue productCatalogue = formPage.submitForm();
		productCatalogue.addItemToCartByIndex(0);
		productCatalogue.addItemToCartByIndex(0);
		CartPage cartPage = productCatalogue.goToCartPage();
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		Assert.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
	}

	// getting data from dataprovider which further is reading fom json file and
	// storing ot hashmap
	@Test(dataProvider = "getData")
	public void fillFormUsingJson(HashMap<String, String> input) throws InterruptedException {
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		formPage.setCountrySelection(input.get("country"));
		ProductCatalogue productCatalogue = formPage.submitForm();
		productCatalogue.addItemToCartByIndex(0);
		productCatalogue.addItemToCartByIndex(0);
		CartPage cartPage = productCatalogue.goToCartPage();
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		Assert.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		// returning data through json file
		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\Harinder\\TestData\\eCommerce.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };

		// returning data directly through dataprovider
		// return new Object[][] { { "Harinder Kaur", "female", "Argentina" },{
		// "Harinderjit Kaur", "female", "Canada" } };
	}

}
