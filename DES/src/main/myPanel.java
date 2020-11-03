package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class myPanel extends JPanel{
	private JButton setInput, encrypt, decrypt;
	private ED ed;
	private boolean b = false;
	public myPanel() {
		setInput = new JButton("Input");
		encrypt = new JButton("Encrypt");
		decrypt = new JButton("Decrypt");
		setInput.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				ed = new ED(new Input());
				b =true;
				
			}
			
		});
		encrypt.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				if(!b)
					JOptionPane.showMessageDialog(null, "Set Input First");
				else {
					b=false;
					try {
						ed.encrypt();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		});
		decrypt.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				if(!b)
					JOptionPane.showMessageDialog(null, "Set Input First");
				else {
					b=false;
					try {
						ed.decrypt();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		});
		add(setInput);
		add(encrypt);
		add(decrypt);
		setPreferredSize(new Dimension(300,250));
		setBackground(Color.CYAN);
	}

}

