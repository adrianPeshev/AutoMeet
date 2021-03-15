import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class EnterHours {
	private static Workbook wb;
	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Row row;
	private static Cell cell;

	public static void EnterHour() throws Exception {
		Main starter=new Main();
		System.out.println("Type when you lesson starts,then type when your lesson finishes[Note:type the hours and minutes like this: 8 30]");
		int test=0;
		int hoursLength = 0;
		String lessonStart="0";
		int text = 0;
		String[] text1 = { "Starts :", "Ends :" };
		boolean nextDay=false;
		int cells = 0;
		int lessonHour=0;
String[] days= {"Monday","Tuesday","Wednesday","Thursday","Friday"};
		Scanner scan = new Scanner(System.in);
		fis = new FileInputStream(System.getProperty("user.dir")+"\\Mat\\hours.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");
		for (int i = 0; i < 18; i++) {
			row = sh.createRow(i);
		}
		System.out.println("The hours for :"+days[0]);
		while (true) {
		
			row = sh.getRow(hoursLength);
			cell = row.createCell(cells);
			if (text % 2 == 0) {
				System.out.print(text1[0]);
				lessonHour++;
				lessonStart=Integer.toString(lessonHour);

			} else {
				System.out.print(text1[1]);
lessonStart="0";
			}
			String hours = scan.nextLine();
		
			if (hours.equals("next")||nextDay) {
	            int num=sh.getPhysicalNumberOfRows();
	            lessonHour=0;
	            cell.setCellValue("00 00 0");

				fos = new FileOutputStream(System.getProperty("user.dir")+"\\Mat\\hours.xlsx");
				wb.write(fos);
				fos.flush();
				fos.close();


	      test=0;
				
nextDay=false;
				cells++;
		
			
			
		
				hoursLength = 0;
				text = 0;
		        while(test<num){
		     	   
		        	row=sh.getRow(test);
		       
		         if(row.getCell(cells-1)==null) {	
		        	  cell=row.createCell(cells-1);
		         cell.setCellValue("00 00 0");
		         			fos = new FileOutputStream(System.getProperty("user.dir")+"\\Mat\\hours.xlsx");
		        			wb.write(fos);
		        			fos.flush();
		        			fos.close();
		         }
		         	
		            test++;
		     
		            }
				try {
					System.out.println("The hours for :"+days[cells]);
					}
					catch(IndexOutOfBoundsException e) 
					{
						starter.EnterLesson();
						System.out.println("You are ready to go!!!");
					break;
					}
				continue;
			}

            cell.setCellValue(hours+" "+lessonStart);

			fos = new FileOutputStream(System.getProperty("user.dir")+"\\Mat\\hours.xlsx");
			wb.write(fos);
			fos.flush();
			fos.close();

		
			if (hoursLength >= 16) {
		nextDay=true;

			}
			text++;
			hoursLength++;
		}
	
	}
	}
