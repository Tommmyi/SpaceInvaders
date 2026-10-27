package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Difficolta;

public class PannelloDifficolta extends JPanel{
	
	private final JButton facile = new BottoneGioco("Facile", new Color(80, 220, 100), 20);
	private final JButton normale = new BottoneGioco("Normale", new Color(255, 210, 60), 20);
	private final JButton difficile = new BottoneGioco("Difficile", new Color(230, 70, 70), 20);
	private final JButton indietro = new BottoneGioco("Indietro", new Color(170, 170, 170), 14);	
	
	public PannelloDifficolta() {
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		
		JPanel sezioneCentrale = new JPanel(new GridLayout(0, 1, 0, 16));
		sezioneCentrale.setOpaque(false);
		JLabel titolo = new JLabel("Scegli la difficoltà", SwingConstants.CENTER);
        titolo.setForeground(Color.WHITE);
        titolo.setFont(new Font("Monospaced", Font.BOLD, 28));
        sezioneCentrale.add(titolo);
        sezioneCentrale.add(facile);
        sezioneCentrale.add(normale);
        sezioneCentrale.add(difficile);
        
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.add(sezioneCentrale);
        this.add(centro, BorderLayout.CENTER);
        
        //bottone indietro in basso
        JPanel basso = new JPanel();
        basso.setOpaque(false);
        basso.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));	//30 pixel dal bordo inferiore
        basso.add(this.indietro);
        this.add(basso, BorderLayout.SOUTH);
        
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
