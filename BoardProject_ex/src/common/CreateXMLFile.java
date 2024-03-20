package common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {
	public static void main(String[] args) {
	
		try {
			Scanner sc = new Scanner(System.in);
			
			Properties prop = new Properties();
			
			System.out.println("생성할 파일 이름 : ");
			String fileName = sc.nextLine();
			
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			
			prop.storeToXML(fos, fileName + ".xml file");
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}
