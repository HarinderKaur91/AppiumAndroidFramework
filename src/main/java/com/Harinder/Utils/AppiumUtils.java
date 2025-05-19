package com.Harinder.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public abstract class AppiumUtils {
	public AppiumDriverLocalService service;
	// AppiumDriver works for both Android and IOS
//commnet added in feature2
	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
		service = new AppiumServiceBuilder()
				.withAppiumJS(
						new File("C:\\Users\\kaurh\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress(ipAddress).usingPort(port).build();
		service.start();
		return service;

	}

	public Double getFormattedAmount(String amount) {
		Double price = Double.parseDouble(amount.substring(1));
		return price;
	}

	public void waitForElementToAppear(WebElement element, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.attributeContains((element), "text", "Cart"));
	}

	// drive data from Json files and load into Hashmap
	// PArse Json file --> Json string (commons-io)
	// Json String ---> Hashmap (jackson)
	// Hashmap -->testcase(testng data provider)

	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
		// convert json file content to json string
		// System.getProperty("user.dir") +
		// "\\src\\test\\java\\com\\Harinder\\TestData\\eCommerce.json"
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
	
	public String getScreenshotPath(String testCaseName,AppiumDriver driver) throws IOException
	{
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"//reports"+testCaseName+".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		//1.ccaptureand place in folder //2. extent report pick file and attach to report
	}
}
