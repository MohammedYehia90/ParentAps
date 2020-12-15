package test;


import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Parameters;


	import io.github.bonigarcia.wdm.WebDriverManager;


	public class CreateEvent {
		
		WebDriver driver;
		
		@BeforeTest
		@Parameters({"browser"})
		public void setupEnviroment(String browser) {
			
			//Check if parameter passed from TestNG is 'firefox'
			if(browser.equalsIgnoreCase("firefox")){
				
				// To disable Notification pop up window
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(new FirefoxProfile());
				options.addPreference("dom.webnotifications.enabled", false);
				
				WebDriverManager.firefoxdriver().setup();
				driver= new FirefoxDriver(options);

			}
			//Check if parameter passed as 'chrome'
			else if(browser.equalsIgnoreCase("chrome")){
				
			    // To disable Notification pop up window	
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				
				WebDriverManager.chromedriver().setup();

				driver= new ChromeDriver(options);
			}
			
			
			driver.navigate().to("https://portal-staging.parent.cloud/login");
			driver.manage().window().maximize();
			
		}
		
		@Test(priority=1)
		public void login() {
			
			WebElement emailField = driver.findElement(By.id("txtEmail"));
			emailField.sendKeys("demo@parent.eu");
			
			WebElement passwordField = driver.findElement(By.id("txtPassword"));
			passwordField.sendKeys("12345678");
			
			WebElement button = driver.findElement(By.id("submitBtn"));
			button.click();
		}
		
		@Test(priority=2)
		public void selectKidsPlaceCalender() {
			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			WebElement kidsPlace = driver.findElement(By.xpath("/html/body/app-root/app-main-layout/main/app-institution/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div[1]"));
			kidsPlace.click();
			

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			WebElement calender = driver.findElement(By.id("calendarTab"));
			calender.click();
			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		
		@Test(priority=3)
		public void createEvent() throws InterruptedException {
			
			WebElement createEvent = driver.findElement(By.id("createEventBtn"));
			createEvent.click();
			
			/*
			 * To handle loading of create Event screen
			 */
			
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			Thread.sleep(2000);


			WebElement eventTitle = driver.findElement(By.xpath("//*[@id=\"eventTitle\"]"));
			eventTitle.sendKeys("Test Event");
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			WebElement eventDescription = driver.findElement(By.id("eventDescription"));
			eventDescription .sendKeys("This is to test Description");
			
			WebElement receipts = driver.findElement(By.id("Recipients"));
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			receipts.click();
			
			WebElement receipts2 = driver.findElement(By.id("option_1"));
			receipts2.click();
			
		//	WebElement receipts = chrome.findElement(By.id("Recipients"));
			receipts.click();
			
			WebElement receipts3 = driver.findElement(By.id("option_2"));
			receipts3.click();
			
			WebElement date = driver.findElement(By.xpath("/html/body/app-root/app-main-layout/main/app-create-event/div/div[2]/form/div/div/prt-date-picker/div/div"));
			date.click();
			
			WebElement dateCalendar = driver.findElement(By.xpath("/html/body/bs-datepicker-container/div/div/div/div/bs-days-calendar-view/bs-calendar-layout/div[2]/table/tbody/tr[5]/td[3]/span"));
			dateCalendar.click();
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			// Select From and To meeting time
			WebElement startTime = driver.findElement(By.xpath("//*[@id=\"timepickerStartTime\"]"));
			startTime.sendKeys("13:00");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			WebElement endTime = driver.findElement(By.xpath("//*[@id=\"timepickerEndTime\"]"));
			endTime.clear();
			endTime.sendKeys("13:30");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//Reservation Needed
			
			WebElement resNeededBtn = driver.findElement(By.xpath("//*[@id=\"is_reservation_needed__wrapper\"]/span"));
			resNeededBtn.click();
			
			WebElement clickRes = driver.findElement(By.id("event_for"));
			clickRes.click();
			
			WebElement selectOption = driver.findElement(By.xpath("//*[@id=\"option_2\"]"));
			selectOption.click();

			//chrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			WebElement deadlineDate = driver.findElement(By.xpath("/html/body/app-root/app-main-layout/main/app-create-event/div/div[2]/form/div/div/div[2]/prt-date-picker/div/div"));
			deadlineDate.click();
			
			WebElement resDeadline = driver.findElement(By.xpath("/html/body/bs-datepicker-container/div/div/div/div/bs-days-calendar-view/bs-calendar-layout/div[2]/table/tbody/tr[4]/td[2]"));
			resDeadline.click();
			
			//Reminder Day Before toggle button
			WebElement remind = driver.findElement(By.xpath("//*[@id=\"reminder_before_event__wrapper\"]/span"));
			remind.click();
			
			//Upload Image
			
			String imgName = "Summerlandscape.jpg";
			String imgPath = System.getProperty("user.dir")+"/img/"+imgName;
			
			WebElement uploadBtn = driver.findElement(By.xpath("/html/body/app-root/app-main-layout/main/app-create-event/div/div[2]/form/div/div/prt-upload-capture-button/div[1]/button"));
			uploadBtn.click();
			
			WebElement upload = driver.findElement(By.xpath("//*[@id=\"imageInput\"]"));
			upload.sendKeys(imgPath);
			
			/*
			 * To handle loading image before click on Save button
			 */
			
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			Thread.sleep(2000);
			
			/*
			 * Zoom Toggle button sometime is enabled but then disabled and require login
			 */
			
			//Zoom Meeting toggle button
//			WebElement zoomMeeting = driver.findElement(By.xpath("//*[@id=\"isZoomMeeting__wrapper\"]/span"));
//			zoomMeeting.click();
			
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			WebElement saveBtn = driver.findElement(By.id("submitCreateEventBtn"));
			saveBtn.click();
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
		}
		
		@Test(priority=4)
		public void checkSuccessfullNotification() {
			/*
			 * To handle loading time between click on Save and Navigate to Calendar Page
			 */
			
			//chrome.manage().timeouts().implicitlyWait(55, TimeUnit.SECONDS);
			
			try {
				Thread.sleep(17000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String confirmMsg = driver.findElement(By.xpath("//*[@id=\"toast-container\"]")).getText();
			
			Assert.assertEquals(confirmMsg, "Calendar event registered");
			//System.out.println("Event Created Successfully");
		}

		@AfterTest
		public void tearDown() {
			
			driver.quit();
		}
		

	}



