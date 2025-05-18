package com.Harinder.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.Harinder.PageObjects.FormPage;
import com.Harinder.Utils.AppiumUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseTest extends AppiumUtils {
	//added comment in the BaseTest
	public AndroidDriver driver;

	FormPage formPage;

	@BeforeClass(alwaysRun = true)
	public void configureAppium() throws IOException {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\Harinder\\resources\\data.properties");
		properties.load(fis);
		// we can also pass parameters from cmd using -D, e.g if we have to send
		// ipAddress as parameter from maven, use following command
		// it checks using ternary operator if ip is coming from maven parameters then
		// ok otherwise give fom properties file
		// command----mvn test -DipAddress=127.0.0.1
		// String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") :properties.getProperty("ipAddress");

		String ipAddress = properties.getProperty("ipAddress");
		String port = properties.getProperty("port");

		service = startAppiumServer(ipAddress, Integer.parseInt(port));


		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(properties.getProperty("AndroidDeviceName"));
		options.setApp(
				"C:\\Users\\kaurh\\eclipse-workspace\\AppiumMobileFramework\\src\\test\\java\\resources\\General-Store.apk");
		options.setCapability("uiautomator2ServerLaunchTimeout", 60000); // 60 seconds
		options.setCapability("adbExecTimeout", 60000); // 60 seconds

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
		// or above line can be written as
		// driver = new AndroidDriver(service.getUrl(), options);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		formPage = new FormPage(driver);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
		service.stop();
	}
}
