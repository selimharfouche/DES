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
	private StringBuilder sb = new StringBuilder();
	private StringBuilder OKLM = new StringBuilder();
	private File file = new File("src/output");
	
	public ED(Input input) {
		this.input = input;
		textToRead = input.getTextToRead();
	}
	protected void encrypt() throws IOException {
		setEncryptionSettings();
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
		JOptionPane.showMessageDialog(null, "Encrypted file in \"src/output\"");
	
		
		
		
	}
	private void setEncryptionSettings() {
		space = getInt(1,"Number of Spaces");
		upperLimit = getInt(500, "Random character upper limit");
		addition = getInt(15000,"Character addition");
	}
	private int getInt(int base, String s) {
		do {
			try {
				s=JOptionPane.showInputDialog(null,s +"\nrecommended: "+base);
				base = Integer.parseInt(s);
				break;
			}catch (NumberFormatException e) {
				if(s==null);
				return base;
			}
			
			
		}while(true);
		
	
		
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
		setDecryptionSettings();
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
		JOptionPane.showMessageDialog(null, "Decrypted file in \"src/output\"");
		
	}
	private void setDecryptionSettings() {
		Boolean b = true;
		String key = null;
		
	
		while(b) {
			key = JOptionPane.showInputDialog(null,"Enter your key");
			try {
				
				key.charAt(9);
					b =false;
				
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
			
		}
		space = (int)key.charAt(4) -(int)(key.charAt(7));
		
	}
	private void decryptLine(String s) {
		sb = new StringBuilder(s);
		OKLM.setLength(0);
		while(sb.length()!=0) {
			addition = (int)sb.charAt(0);
			sb.delete(0, space);
			OKLM.append(Character.toString((char)(Math.abs(addition-(int)sb.charAt(0)))));
			sb.delete(0,1);
		}
	}
	private void setKey() {
		for (int i =0;i<10;i++)
			OKLM.append((char)rnd.nextInt(16000));
		OKLM.setCharAt(4,(char)(space+(int)OKLM.charAt(7)));
		key = OKLM.toString();
	}
	private String getKey() {
		setKey();
		return key;
	}
	
	
	

}
