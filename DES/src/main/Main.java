package main;
import java.util.*;

import javax.swing.JFrame;

import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException{
		JFrame myFrame = new JFrame("File Manager");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.getContentPane().add(new myPanel());
		myFrame.pack();
		myFrame.setVisible(true);
		
	}
	

}
