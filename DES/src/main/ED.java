package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JOptionPane;

public class ED {
	private char[] arr;
	private int space, upperLimit, addition;
	private Input input;
	private Random rnd = new Random();
	private String key = null;
	private String[] textToRead;
	private StringBuilder sb = new StringBuilder("");
	private StringBuilder OKLM = new StringBuilder("");
	private File file;
	
	public ED(Input input) {
		this.input = input;
		textToRead = input.getTextToRead();
	}
	protected void encrypt() throws IOException {
		if(setEncryptionSettings()){
			setFilePath();
			if(!(file.exists())) {
				file = new File("src/output");
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println("Key: "+getKey());
			for (String s : textToRead) {
				encryptLine(s);
				s = sb.toString();
				pw.println(s);
				
			}
			pw.close();
			bw.close();
			fw.close();
			JOptionPane.showMessageDialog(null, "Encrypted file in "+file.getAbsolutePath());
		
		
	
		}
		
		
	}
	private boolean setEncryptionSettings() {
		space = getInt(1,"Number of Spaces");
		if(space==-1)
			return false;
		upperLimit = getInt(500, "Random character upper limit");
		if(upperLimit==-1)
			return false;
		addition = getInt(15000,"Character addition");
		if(addition==-1)
			return false;
		return true;
		
	}
	private void setFilePath() {
		String s;
		String base = "src/output";
		boolean b = true;
		do {	
				s=JOptionPane.showInputDialog(null,"Enter Output File Path\nEmpty for default: "+base);
				if(s==null||s=="") {
					file = new File("src/output");b =false;			
				}
					
				else {
					file = new File(s);b =false;
				}
				try {
					if(!(file.exists())) {
						file.createNewFile();
					}
				}catch(Exception e) {
					
				}
					
				
		
		}while(b);	
		
	}
	private int getInt(int base, String s) {
		boolean b = true;
		do {
			try {
				s=JOptionPane.showInputDialog(null,s +"\nEmpty for default: "+base);
				base = Integer.parseInt(s);
				if(base>0)
					break;
				
			}catch (NumberFormatException e) {
				if(s==null)	
					return -1;b =false;
				if(s=="")
					return base;b =false;
				
				
					
			}
			
			
		}while(b);	
		return base;
	}
	private void encryptLine (String s) {
		int randomCharInt;
		arr = s.toCharArray();	
		sb.setLength(0);
		for(char c : arr) {
			randomCharInt = rnd.nextInt(upperLimit)+addition;
		
			sb.append((char)randomCharInt);
			if(space>1) {
				for (int i =1;i<space;i++) {
					sb.append((char)(rnd.nextInt(upperLimit)+addition));
				}
			}
			sb.append((char)((int)c + randomCharInt));
			
		}
		
		
	}
	protected void decrypt() throws IOException {
			
		if(setDecryptionSettings()) {
			setFilePath();
			if(!(file.exists())) {
				file = new File("src/output");
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (String s : textToRead) {
				decryptLine(s);
				s = OKLM.toString();
				pw.println(s);
			}
			pw.close();
			bw.close();
			fw.close();
			JOptionPane.showMessageDialog(null, "Decrypted file in "+file.getAbsolutePath());
		
		}
	}
	private boolean setDecryptionSettings() {
		Boolean b = true;
		String key = null;
		
	
		while(b) {
			key = JOptionPane.showInputDialog(null,"Enter your key");
			try {
				
				key.charAt(9);
					b =false;
				
			}catch(NullPointerException e) {
				if(key == null)
					return false;
			}
			
		}
		
		space = (int)key.charAt(10) -15000;
		
		return true;
	}
	private void decryptLine(String s) {
		sb = new StringBuilder(s);
		OKLM.setLength(0);
		while(sb.length()!=0) {
			if(s.equals(""))
				break;
			
			addition = (int)sb.charAt(0);
			sb.delete(0, space);
			
			OKLM.append(Character.toString((char)(Math.abs(addition-(int)sb.charAt(0)))));
			
			
			sb.delete(0,1);
			
			
		}
	}
	private void setKey() {
		for (int i =0;i<10;i++) {
			OKLM.append((char)(rnd.nextInt(500)+15000));
		}
		OKLM.append((char)(15000+space));
		
		
		
	}
	private String getKey() {
		setKey();
		return OKLM.toString();
	}
	
	
	

}
