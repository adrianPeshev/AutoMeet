import org.apache.poi.ss.usermodel.*;

import com.gargoylesoftware.htmlunit.javascript.host.URL;
import java.util.*;


import java.io.*;

public class EnterProgramm {

	private static Workbook wb;
	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Row row;
	private static Cell cell;

	public static void EnterProgram() throws Exception{
		Main starter=new Main();
		boolean continueRow = false;
		int hours = 1;
		String lessons = "";
		int rows = 0;
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
		int cells = 0;
		Scanner scan = new Scanner(System.in);
		fis = new FileInputStream(System.getProperty("user.dir")+"\\Mat\\data.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");

		System.out.println("Here you must enter your week programm!!!");
		System.out.println();
		System.out.println("After you reach Friday just type next and the programm will stop!!! ");
		System.out.println("The programm for:" + days[0]);

		for (int i = 0; i < 9; i++) {
			row = sh.createRow(i);
		}
		while (true) {
			if (!continueRow) {
				System.out.print(hours + " :");
				lessons = scan.nextLine();
				hours++;
			}
			if (lessons.equals("next") || continueRow) {

				continueRow = false;
				try {
					System.out.println("The programm for:" + days[cells + 1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					starter.CallHours();
					System.out.println("Excellent ,you are ready to go!!!");
					System.exit(0);
				}
				rows = 0;
				cells++;
				hours = 1;
				continue;

			}

			try {
				if (rows > 7) {
					throw new NullPointerException();
				}

			} catch (NullPointerException e) {

				continueRow = true;
			}

			row = sh.getRow(rows);
			cell = row.createCell(cells);
			cell.setCellValue(lessons);
//System.out.println(cell.getStringCellValue());
			fos = new FileOutputStream(System.getProperty("user.dir")+"\\Mat\\data.xlsx");
			wb.write(fos);
			fos.flush();
			fos.close();
			rows++;

		}
	}

}

