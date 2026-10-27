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

public class PannelloFine extends JPanel{

	 private final JLabel esito = new JLabel("", SwingConstants.CENTER);
	 private final JLabel punti = new JLabel("", SwingConstants.CENTER);
	 private final JLabel record = new JLabel("", SwingConstants.CENTER);
	 private final JButton riprova = new BottoneGioco("Torna al menu", Color.WHITE, 18);
	 	
	 public PannelloFine() {
		 setPreferredSize(new Dimension(800, 600));
	     setBackground(Color.BLACK);
	     setLayout(new GridBagLayout());

	     JPanel sezioneCentrale = new JPanel(new GridLayout(0, 1, 0, 12));
	     sezioneCentrale.setOpaque(false);
	     esito.setForeground(Color.WHITE);
	     esito.setFont(new Font("Monospaced", Font.BOLD, 32));
	     punti.setForeground(Color.WHITE);
	     punti.setFont(new Font("Monospaced", Font.PLAIN, 22));
	     record.setFont(new Font("Monospaced", Font.PLAIN, 22));
	     record.setForeground(Color.WHITE);
	     sezioneCentrale.add(esito);
	     sezioneCentrale.add(record);
	     sezioneCentrale.add(punti);
	     sezioneCentrale.add(riprova);
	     add(sezioneCentrale);
	 }

	 public void mostraEsito(boolean nuovoRecord, int punteggio, int recordAttuale) {
	     esito.setText(nuovoRecord ? "NUOVO RECORD!" : "GAME OVER");
	     punti.setText("Punteggio: " + punteggio);
	     this.record.setText("Record: " + recordAttuale);
	 }

	 public void setListenerRiprova(ActionListener l) { 
	   	riprova.addActionListener(l);
	 }
}
