package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Difficolta;

public class PannelloDifficolta extends JPanel{
	
	JButton facile = new JButton("Facile");
	JButton normale = new JButton("Normale");
	JButton difficile = new JButton("Difficile");
	JButton indietro = new JButton("indietro");
	
	
	public PannelloDifficolta() {
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());
		
		JPanel sezioneCentrale = new JPanel(new GridLayout(0, 1, 0, 12));
		sezioneCentrale.setOpaque(false);
		JLabel titolo = new JLabel("Scegli la difficoltà", SwingConstants.CENTER);
        titolo.setForeground(Color.WHITE);
        titolo.setFont(new Font("Monospaced", Font.BOLD, 28));
        sezioneCentrale.add(titolo);
        sezioneCentrale.add(facile);
        sezioneCentrale.add(normale);
        sezioneCentrale.add(difficile);
        sezioneCentrale.add(indietro);
        add(sezioneCentrale);
	}
	
	public void setListenerDifficolta(Consumer<Difficolta> azione) {
        facile.addActionListener(e -> azione.accept(Difficolta.FACILE));
        normale.addActionListener(e -> azione.accept(Difficolta.NORMALE));
        difficile.addActionListener(e -> azione.accept(Difficolta.DIFFICILE));
    }

    public void setListenerIndietro(ActionListener l) { 
    	indietro.addActionListener(l);	
    }
}
