
import java.io.*;
import java.util.*;

public class Test {

	public static void Rewrite() throws Exception {
ChangeVbs("Starter","chromeStarter");
ChangeVbs("InternetStart","test");
ChangeVbs("Kill","Killer");
ChangeBat("test");
ChangeBat("test1");
//ChangeOne("App");
	}
	  public static String fileToString(String filePath) throws Exception{
	      String input = null;
	      Scanner sc = new Scanner(new File(filePath));
	      StringBuffer sb = new StringBuffer();
	      while (sc.hasNextLine()) {
	         input = sc.nextLine();
	         sb.append(input);
	      }
	      return sb.toString();
	  }
	public static void ChangeVbs(String file,String file1) throws Exception
{
	File BatStart=new File(System.getProperty("user.dir")+"\\"+"Mat\\"+file+".vbs");
	String filePath=System.getProperty("user.dir")+"\\"+"Mat\\"+file+".vbs";
	String result = fileToString(filePath);
	 
     //Replacing the word with desired one
	
	result=result.replaceAll("\\W", "");
	result=result.replaceAll(result, "");


	
	

//Rewriting the contents of the file
     PrintWriter writer = new PrintWriter(new File(filePath));
     writer.append(result);
     writer.flush();

     //System.out.println(fileToString(filePath));

	FileWriter write=new FileWriter(BatStart,true);
	String vbs="Set WshShell = CreateObject(\"WScript.Shell\" ) \r\n"+ "WshShell.Run chr(34) & "+"\""+System.getProperty("user.dir");
	write.write(vbs+"\\Mat\\"+file1+".bat"+"\" "+"& Chr(34), 0 \r\n"
			+ "Set WshShell = Nothing ");
	write.close();
}
public static void ChangeBat(String file) throws Exception
{
	File BatStart=new File(System.getProperty("user.dir")+"\\"+"Mat\\"+file+".bat");
	String filePath=System.getProperty("user.dir")+"\\"+"Mat\\"+file+".bat";
	String result = fileToString(filePath);
	 

	if(file.equals("test")) {
	result=result.replaceAll("\\W", "");
	result=result.replaceAll(result, "");
    PrintWriter writer = new PrintWriter(new File(filePath));
    writer.append(result);
    writer.flush();

	FileWriter write=new FileWriter(BatStart,true);

	write.write("netsh wlan show interfaces | Findstr /c:\"Signal\">nul && Echo Online>nul||"+ System.getProperty("user.dir")+"\\Mat\\test1.bat");
	 write.write(System.getProperty("line.separator"));
	write.write("test.bat");
	write.close();	
	}
	if(file.equals("test1")) 
	{
		result=result.replaceAll("\\W", "");
		result=result.replaceAll(result, "");
	    PrintWriter writer = new PrintWriter(new File(filePath));
	    writer.append(result);
	    writer.flush();

		FileWriter write=new FileWriter(BatStart,true);

		write.write("@echo off");
		 
		write.write(System.getProperty("line.separator"));
		
		 write.write("setlocal enabledelayedexpansion");
		 
		 write.write(System.getProperty("line.separator"));
		 
		 write.write("for /f \"tokens=2delims=:\" %%a in ('netsh wlan show networks ^|findstr \":\"') do (");
		 
		 write.write(System.getProperty("line.separator"));
		
		 write.write("set \"ssid=%%~a\"");
		 
		 write.write(System.getProperty("line.separator"));
		
		 write.write("call :getpwd \"%%ssid:~1%%\"");
		 
		 write.write(System.getProperty("line.separator"));
		
		 write.write(")");
		 
		 write.write(System.getProperty("line.separator"));
		
		 write.write(":getpwd");
		
		 write.write(System.getProperty("line.separator"));
		
		 write.write("set \"ssid=%*\"");
		
		 write.write(System.getProperty("line.separator"));
		
		 write.write("for /f \"tokens=2delims=:\" %%i in ('netsh wlan show profile name^=\"%ssid:\"=%\" key^=clear ^| findstr /C:\"Key Content\"') do netsh wlan connect name=%ssid%");
		
		 write.write(System.getProperty("line.separator"));

		 write.write(System.getProperty("user.dir")+"\\Mat\\test.bat");
		
		 write.close();	
		
	}


//Rewriting the contents of the file

     //System.out.println(fileToString(filePath));


}
public static void ChangeOne(String file) throws Exception
{
	File BatStart=new File(System.getProperty("user.dir")+"\\"+file+".bat");
	String filePath=System.getProperty("user.dir")+"\\"+file+".bat";
	String result = fileToString(filePath);
	 
	result=result.replaceAll("\\W", "");
	result=result.replaceAll(result, "");
    PrintWriter writer = new PrintWriter(new File(filePath));
    writer.append(result);
    writer.flush();

	FileWriter write=new FileWriter(BatStart,true);

	write.write("cd "+System.getProperty("user.dir"));
	 write.write(System.getProperty("line.separator"));
	write.write("java -jar AutoMeeter.jar");
	write.close();
}
}
