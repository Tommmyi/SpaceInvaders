package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class BottoneGioco extends JButton{

	public BottoneGioco(String testo, Color colore, int dimensioneFont) {
		super(testo);
		this.setFont(new Font("Monospaced", Font.BOLD, dimensioneFont));
		this.setForeground(colore);
		this.setBackground(Color.BLACK);
		this.setOpaque(true);			
		this.setBorder(BorderFactory.createLineBorder(colore, 2));
		this.setFocusPainted(false);	

		Dimension d = this.getPreferredSize();	
		this.setPreferredSize(new Dimension(d.width + 40, d.height + 10));
	}
}
