import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Killer extends Thread
{
	public void run(){
	
	
	File internet=new File(System.getProperty("user.dir")+"\\Mat\\Killer.bat");
	
	try {
	
		Desktop.getDesktop().open(internet);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}