import org.openqa.selenium.*;
import java.net.*;
import java.text.SimpleDateFormat;

import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.SystemOutLogger;

public class Main {
	private static Workbook wb;

	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Row row;
	private static Cell cell;

	public static void main(String[] args) throws Exception {
		Test ts = new Test();
		ts.Rewrite();
		Killer killer = new Killer();
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {

					killer.run();

				} catch (IllegalMonitorStateException e) {
					System.out.println(e.getMessage());
				}
			}

		});

		Scanner scan = new Scanner(System.in);
		EnterProgramm program = new EnterProgramm();

		System.out.println("Type: Yes or No!!");
		System.out.println("Do you want to add new school programm?");
		String decision = scan.nextLine();
		decision.toLowerCase();
		if (decision.equals("yes")) {
			program.EnterProgram();
		} else {
			CallHours();
		}

	}

	public static String GetCurrentLesson(int rows, int cells) throws Exception {

		fis = new FileInputStream(System.getProperty("user.dir") + "\\Mat\\data.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");
		int numOfRows = sh.getLastRowNum();
		if (numOfRows < rows) {
			throw new NullPointerException();

		}
		row = sh.getRow(rows);
		cell = row.getCell(cells);
		String lesson = cell.getStringCellValue();
		return lesson;

	}

	public static void EnterLesson() throws Exception {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Admin\\eclipse-workspace\\AutomaticMeetEnter\\src\\Driver&Path\\chromedriver.exe");
		fis = new FileInputStream(System.getProperty("user.dir") + "\\Mat\\hours.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");
		int rows = -1;
		int numOfRows = sh.getLastRowNum();

		File file = new File(System.getProperty("user.dir") + "\\Mat\\Starter.vbs");
		Desktop.getDesktop().open(file);
		ChromeOptions option = new ChromeOptions();
		option.setExperimentalOption("debuggerAddress", "localhost:9014");
		int counter = 0;
		List<Integer> hours = new ArrayList<>();
		List<Integer> minutes = new ArrayList<>();
		
		int counter1 = 0;
		WebDriver driver = new ChromeDriver(option);
		driver.manage().window().maximize();
		boolean IhaveLesson = false;
		for (int i = 0; i <= numOfRows; i++) {
			if (getCurrentHour(i) != 0) {
				hours.add(getCurrentHour(i));
				minutes.add(getCurrentMinute(i));
			

			} else if (getCurrentHour(i) == 24) {
				hours.add(0);
				minutes.add(getCurrentMinute(i));
		
			}
		}

		
		while (true) {

			Calendar cal = Calendar.getInstance();

			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);

			int day = cal.get(Calendar.DAY_OF_WEEK);

			int cells = day - 2;
			String lesson = "";

			try {
				for (int i = 0; i < hours.size(); i++) {

					if (i % 2 == 0) {
			
						if (hour == hours.get(i) && minute == minutes.get(i) && counter1 == 0) {

					rows = getStart(i);

							IhaveLesson = true;
							lesson = GetCurrentLesson(rows-1, cells);
							counter1 = 1;

						}

					} else {
						if (hour == hours.get(i) && minute == minutes.get(i)) {
							IhaveLesson = false;
							counter1 = 0;
						}

					}

				}

				if (lesson.equals("purvanova")) {
					lesson = getHtmlLink(driver);

				} else if (lesson.equals("bg")) {

					lesson = getBGLink(driver);

				}

			} catch (NullPointerException | IOException e) {

				System.out.println("Now you don't have lessons!!");
				break;
			}

			if (IhaveLesson && counter == 0) {

				driver.findElement(By.id("i3")).sendKeys(lesson);
				driver.findElement(
						By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/div/div[1]/div[3]/div[2]/div[2]/button"))
						.click();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.findElement(By.className("sUZ4id")).click();
				driver.findElement(By.className("GOH7Zb")).click();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//span[contains(text(),'Join now')]")).click();
				// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				// driver.findElement(By.xpath("//*[@id=\"ow3\"]/div[1]/div/div[9]/div[3]/div[1]/div[3]/div/div[2]/div[3]")).click();
				// driver.findElement(By.xpath("//*[@id=\"ow3\"]/div[1]/div/div[9]/div[3]/div[4]/div/div[2]/div[2]/div[2]/span[2]/div/div[4]/div[1]/div[1]/div[2]/textarea")).sendKeys("Съжалявам
				// нямам микрофон");
				// driver.findElement(By.xpath("//*[@id=\"ow3\"]/div[1]/div/div[9]/div[3]/div[4]/div/div[2]/div[2]/div[2]/span[2]/div/div[4]/div[2]/span")).click();
				counter = 1;

				continue;
			}

			if (!IhaveLesson && counter == 1) {

				driver.findElement(By.xpath("//*[@id=\"ow3\"]/div[1]/div/div[9]/div[3]/div[9]/div[2]/div[2]/div"))
						.click();
				driver.findElement(By.xpath("//span[contains(text(),'Return to home screen')]")).click();
				counter = 0;
			}

		}

	}

	public static String getBGLink(WebDriver driver) throws IOException {
		driver.findElement(By.id("i3")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("popup_window = window.open('https://classroom.google.com/u/1/h','_blank');");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get("https://classroom.google.com/u/1/h");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[2]/div/div[2]/div/ol/li[15]/div[1]/div[3]/a")).click();
		String value = driver.findElement(By.xpath("//span[contains(text(),'meet')]")).getText();

		String code = value.substring(31, 44);

		driver.switchTo().window(tabs.get(0));
		js.executeScript("popup_window.close()");
		return code;

	}

	public static String getHtmlLink(WebDriver driver) throws Exception {
		driver.findElement(By.id("i3")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("popup_window = window.open('https://classroom.google.com/u/1/h','_blank');");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[2]/div/div[2]/div/ol/li[17]/div[1]/div[3]")).click();
		String value = driver.findElement(By.xpath("//*[@id=\"ow265\"]/.//html-blob/span/a")).getText();
		String code = value.substring(24, 36);

		driver.switchTo().window(tabs.get(0));

		js.executeScript("popup_window.close()");
		return code;
	}

	public static int getCurrentHour(int rows) throws Exception {
		Calendar cal = Calendar.getInstance();
		int cells = cal.get(Calendar.DAY_OF_WEEK);
		fis = new FileInputStream(System.getProperty("user.dir") + "\\Mat\\hours.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");

		row = sh.getRow(rows);
		cell = row.getCell(cells - 2);

		String curr = cell.getStringCellValue();

		curr = curr.substring(0, 2);
		String Current = "";
		for (int i = 0; i < curr.length(); i++) {
			if (curr.charAt(i) != ' ')
				Current += curr.charAt(i);

		}

		int currentHour = Integer.parseInt(Current);
		return currentHour;

	}

	public static int getCurrentMinute(int rows) throws Exception {
		Calendar cal = Calendar.getInstance();
		int cells = cal.get(Calendar.DAY_OF_WEEK);
		fis = new FileInputStream(System.getProperty("user.dir") + "\\Mat\\hours.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");
		row = sh.getRow(rows);
		cell = row.getCell(cells - 2);
		String curr = cell.getStringCellValue();
		String Current = "";
		for (int i = 0; i < curr.length(); i++) {
			if (curr.charAt(i) != ' ')
				Current += curr.charAt(i);

		}

		Current = Current.substring(0, Current.length() - 1);
		int currentMinute = Integer.parseInt(Current);
		if (getCurrentHour(rows) < 10 && currentMinute < 100) {
			Current = Current.substring(1, 2);
		} else if (getCurrentHour(rows) < 10 && currentMinute >= 100 && currentMinute < 1000) {
			Current = Current.substring(1, 3);
		} else if (getCurrentHour(rows) >= 10 && currentMinute < 1000 && currentMinute >= 100) {
			Current = Current.substring(2, 3);
		} else if (getCurrentHour(rows) >= 10 && currentMinute >= 1000) {
			Current = Current.substring(2, 4);
		}

		int EndMinute = Integer.parseInt(Current);

		return EndMinute;
	}

	public static int getStart(int rows) throws Exception {

		Calendar cal = Calendar.getInstance();
		int cells = cal.get(Calendar.DAY_OF_WEEK);
		fis = new FileInputStream(System.getProperty("user.dir") + "\\Mat\\hours.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");

		row = sh.getRow(rows);
		cell = row.getCell(cells - 2);
		String curr = cell.getStringCellValue();
		String Current = "";
		for (int i = 0; i < curr.length(); i++) {
			if (curr.charAt(i) != ' ')
				Current += curr.charAt(i);

		}

		Current = Current.substring(Current.length() - 1, Current.length());

		int EndMinute = Integer.parseInt(Current);

		return EndMinute;
	}

	public static void StartBatches() throws Exception {
		File internet = new File(System.getProperty("user.dir") + "\\Mat\\internetStart.vbs");
		Desktop.getDesktop().open(internet);
	}

	public static void CallHours() throws Exception {
		Scanner scan = new Scanner(System.in);
		EnterHours hours = new EnterHours();
		System.out.println("Do you want to add new hours of beginning and ending?");
		String decision = scan.nextLine();
		decision.toLowerCase();
		if (decision.equals("yes")) {
			hours.EnterHour();
		} else {
			try {
				EnterLesson();
			} catch (IOException e) {
				System.out.println("Not recoagnised");

			}
		}
	}

}
