package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Input {
	
	private String[] textToRead;

	
	public Input(){
		getInput();
		
	}
	
	protected String[] getTextToRead() {
		return textToRead;
	}
	protected int getNumberOfLines() {
		return textToRead.length;
	}
	
	private void getInput() {
		int userCase;
		String s=null;
		boolean b = true;
		do {
			try {
				s=JOptionPane.showInputDialog(null,"1 - Enter your text\n2- Text File");
				userCase = Integer.parseInt(s);
				if (userCase==1)
					getConsole();b=false;
				
				if (userCase==2)
					getFile();b=false;

		
			}catch (NumberFormatException e) {
				if(s==null)	
					b =false;
				if(s=="")
					b =false;		
					
			}
			
			
		}while(b);	
	
		
	}
	private void getConsole() {
		ArrayList<String> list = new ArrayList<String>();
		String s;
		
		s = JOptionPane.showInputDialog(null,"Enter your text");
	
		while(s.equals("")) {
		
			s = JOptionPane.showInputDialog(null,"Enter valid text");
		}
				do {
			list.add(s);
			s =JOptionPane.showInputDialog(null,"Enter your text , cancel to stop");
			
			
		}while(s!=null);
		textToRead = list.toArray(new String[list.size()]);
		
		
	}
	private void getFile() {
		ArrayList<String> list = new ArrayList<String>();
		File input = validFile();
		String s;
		try {
			FileInputStream fis =new FileInputStream (input);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			while((s = br.readLine())!=null) {
				list.add(s);
			}
			br.close();
			isr.close();
			fis.close();
		}catch (IOException e ){
			System.out.println("Error Occured while trying to read from file");
		}
		textToRead = list.toArray(new String[list.size()]);
		
		
		
		
	}
	private File validFile() {
		File f;
		String s;
		
		
		do {	
			s= (JOptionPane.showInputDialog(null,"Enter your file path"));
			f = new File(s);
		}while(!(f.exists()));

		return f;
		
	}


}
