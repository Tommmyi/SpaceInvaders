package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PannelloMenu extends JPanel{

	private final JButton gioca = new BottoneGioco("GIOCA", Color.WHITE, 24);
	
	public PannelloMenu() {
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());	// Centra gli elementi sia in verticale che in orizzontale
		
		JPanel sezioneCentrale = new JPanel(new GridLayout(0,1,0,40));
        sezioneCentrale.setOpaque(false);
        
		//Creo la scritta del titolo:
        JLabel titolo = new JLabel("SPACE INVADERS", SwingConstants.CENTER);
        titolo.setForeground(new Color(80, 220, 100));
        titolo.setFont(new Font("Monospaced", Font.BOLD, 36)); // Carattere stile retro arcade
        
        sezioneCentrale.add(titolo);
        sezioneCentrale.add(gioca);

        
        add(sezioneCentrale);
	} 
	
    public void setListenerGioca(ActionListener l) { 
    	gioca.addActionListener(l); 
    }	
}
