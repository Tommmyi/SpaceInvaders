package view;

import java.awt.*;
import java.util.List;
import javax.swing.JPanel;
import model.*;

public class PannelloGioco extends JPanel {

    private static final Color COL_TIPO_1 = new Color(80, 220, 100);
    private static final Color COL_TIPO_2 = new Color(230, 70, 70);
    private static final Color COL_TIPO_3 = new Color(180, 90, 230);
    private static final Color COL_SCUDO = new Color(60, 160, 255);
    private static final Color COL_GIOCATORE = Color.WHITE;
    private static final Color COL_SPARO_GIOCATORE = Color.YELLOW;
    private static final Color COL_SPARO_NEMICO = Color.ORANGE;
    private static final Font FONT_HUD = new Font("Monospaced", Font.BOLD, 18);

    private final Gioco gioco;

    public PannelloGioco(Gioco gioco) {
        this.gioco = gioco;
        setPreferredSize(new Dimension((int) Gioco.LARGHEZZA_CAMPO, (int) Gioco.ALTEZZA_CAMPO));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);                       // pulisce lo sfondo
        if (gioco.getGiocatore() == null) return;      // partita non ancora avviata

        disegnaAlieni(g);
        disegnaScudi(g);
        disegnaProiettili(g);
        disegnaGiocatore(g);
        disegnaHud(g);
    }

    private void disegnaAlieni(Graphics g) {
        for (List<Alieno> riga : gioco.getGrigliaAlieni().getGriglia()) {
            for (Alieno a : riga) {
                if (!a.getStatoVita()) continue;       // i morti non li ridisegna
                g.setColor(coloreAlieno(a.getTipo()));
                disegnaRettangolo(g, a);
            }
        }
    }

    private Color coloreAlieno(TipoAlieno tipo) {
        switch (tipo) {
            case TIPO_2: return COL_TIPO_2;
            case TIPO_3: return COL_TIPO_3;
            default:     return COL_TIPO_1;
        }
    }

    private void disegnaScudi(Graphics g) {
        g.setColor(COL_SCUDO);
        for (Scudo s : gioco.getScudi()) {
            if (s.getStatoVita()) disegnaRettangolo(g, s);
        }
    }

    private void disegnaProiettili(Graphics g) {
        g.setColor(COL_SPARO_GIOCATORE);
        for (ProiettileGiocatore p : gioco.getSpariGiocatore()) {
            if (p.getStatoVita()) disegnaRettangolo(g, p);
        }
        g.setColor(COL_SPARO_NEMICO);
        for (ProiettileNemico p : gioco.getSpariNemici()) {
            if (p.getStatoVita()) disegnaRettangolo(g, p);
        }
    }

    private void disegnaGiocatore(Graphics g) {
        Giocatore giocatore = gioco.getGiocatore();
        if (!giocatore.getStatoVita()) return;
        g.setColor(COL_GIOCATORE);
        disegnaRettangolo(g, giocatore);
    }

    private void disegnaHud(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(FONT_HUD);
        g.drawString("Punti: " + gioco.getPunteggio(), 10, 22);
        g.drawString("Record: " + gioco.getRecord(), 200, 22);
        g.drawString("Vite: " + gioco.getViteGiocatore(), 420, 22);
        g.drawString("Ondata: " + (gioco.getOndate() + 1), 600, 22);
    }

    // un solo punto che converte da model (double) a pixel (int)
    private void disegnaRettangolo(Graphics g, OggettoDiGioco o) {
        g.fillRect((int) o.getPosizione().getX(), (int) o.getPosizione().getY(),
                   (int) o.getLarghezza(), (int) o.getAltezza());
    }
}