package login;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class LoginTest {

	public static AppiumDriver<MobileElement> driver;
	public static DesiredCapabilities capability = new DesiredCapabilities();

	public static void main(String[] args) {

	}

	public static void setEmail(String email) {
		driver.findElementById("com.example.loginpage:id/activity_main_email").setValue(email);
	}

	public static void setPassword(String password) {
		driver.findElementById("com.example.loginpage:id/activity_main_password").setValue(password);
	}

	public static void clickLogin() {
		driver.findElementById("com.example.loginpage:id/activity_main_loginButton").click();
	}

	public static String getToastMessage() {
		MobileElement toastElement = driver.findElementByXPath("//android.widget.Toast[1]");
		String toastMessage = toastElement.getAttribute("name");
		return toastMessage;
	}

	@BeforeSuite
	public static void setCapabilities() throws Exception {
		capability.setCapability("deviceName", "Oppo");
		capability.setCapability("udid", "IF89KFMZAAT86HRC");
		capability.setCapability("automationName", "uiautomator2");
		capability.setCapability("platformVersion", "12");
		capability.setCapability("platformName", "Android");
		capability.setCapability(MobileCapabilityType.APP,
				"/Users/jaya/eclipse-workspace/Selenium/appiumtests/loginApp.apk");

	}

	@BeforeMethod
	public void setUpDriver() {
		try {
			driver = new AppiumDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capability);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Test(description = "Verify successful login")
	public void testSuccessfulLogin() {
		setEmail("dasari.jayalakshmi17@gmail.com");
		setPassword("password");
		clickLogin();
		Assert.assertEquals("Login Successful", getToastMessage());
	}

	@Test(description = "Verify failed login")
	public void testFailedLogin() {
		setEmail("dasari.jayalakshmi@gmail.com");
		setPassword("password");
		clickLogin();
		Assert.assertEquals("Login Failed", getToastMessage());
	}

	@Test(description = "Verify failed login with out password")
	public void testFailedWithOutPassword() {
		setEmail("dasari.jayalakshmi@gmail.com");
		clickLogin();
		Assert.assertEquals("Password is not populated", getToastMessage());
	}

	@Test(description = "Verify failed login with out email")
	public void testFailedWithOutEmail() {
		setPassword("password");
		clickLogin();
		Assert.assertEquals("Email is not populated", getToastMessage());
	}

	@Test(description = "Verify failed login with out email and password")
	public void testFailed() {
		clickLogin();
		Assert.assertEquals("Email and Password are not populated", getToastMessage());
	}

	@AfterSuite
	public void stopDriver() {
		driver.quit();
	}

}
