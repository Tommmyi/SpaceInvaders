package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import model.*;

class GrigliaAlieniTest {

	private static final double DELTA = 1e-9;

	private List<Alieno> tutti(GrigliaAlieni griglia) {
		return griglia.getGriglia().stream().flatMap(List::stream).toList();
	}

	@Test
	void composizioneGriglia() { //5 righe da 11 alieni a riga
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		assertEquals(GrigliaAlieni.RIGHE, griglia.getGriglia().size());
		for (List<Alieno> riga : griglia.getGriglia()) {
			assertEquals(GrigliaAlieni.COLONNE, riga.size());
		}
		assertTrue(tutti(griglia).stream().allMatch(Alieno::getStatoVita));
		assertFalse(griglia.isDistrutta());
		assertEquals(Direzione.DESTRA, griglia.getDirezione());
	}

	@Test
	void alieniNonCondividonoLaStessaPosizione() {
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		List<Alieno> alieni = tutti(griglia);
		for (int i = 0; i < alieni.size(); i++) {
			for (int j = i + 1; j < alieni.size(); j++) {
				assertFalse(alieni.get(i).interseca(alieni.get(j)), "alieni " + i + " e " + j + " sovrapposti");
			}
		}
	}
	
	@Test
	void grigliaArrivaBordo() {
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		Alieno primo = griglia.getGriglia().get(0).get(0);
		Alieno ultimo = griglia.getGriglia().get(0).get(GrigliaAlieni.COLONNE - 1);
		double xPrima = 0;
		double yPrima = 0;
		int passi = 0;
		while (griglia.getDirezione() == Direzione.DESTRA && passi < 10000) {
			xPrima = primo.getPosizione().getX();
			yPrima = primo.getPosizione().getY();
			griglia.muovi(Gioco.LARGHEZZA_CAMPO);
			assertTrue(ultimo.getPosizione().getX() + ultimo.getLarghezza() <= Gioco.LARGHEZZA_CAMPO);
			passi++;
		}
		assertEquals(Direzione.SINISTRA, griglia.getDirezione());
		assertEquals(yPrima + GrigliaAlieni.VELOCITA_Y, primo.getPosizione().getY(), DELTA);
		assertEquals(xPrima, primo.getPosizione().getX(), DELTA);	//quando scende non si sposta in orizzontale

		griglia.muovi(Gioco.LARGHEZZA_CAMPO);
		assertEquals(xPrima - griglia.getVelocita(), primo.getPosizione().getX(), DELTA);
	}

	@Test
	void raggiungeFondoConsideraAlieniVivi() {
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		double baseUltimaRiga = 260;	//50 + 4 * (30 + 15) + 30
		assertTrue(griglia.raggiungeFondo(baseUltimaRiga));
		assertFalse(griglia.raggiungeFondo(baseUltimaRiga + 1));
		griglia.getGriglia().get(GrigliaAlieni.RIGHE - 1).forEach(Alieno::distruggi);
		assertFalse(griglia.raggiungeFondo(baseUltimaRiga));
	}

	@Test
	void distruzioneGriglia() {
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		List<Alieno> alieni = tutti(griglia);
		for (int i = 0; i < alieni.size() - 1; i++) {
			alieni.get(i).distruggi();
		}
		assertFalse(griglia.isDistrutta());
		alieni.get(alieni.size() - 1).distruggi();
		assertTrue(griglia.isDistrutta());
	}

	@Test
	void senzaTiratoriViviNonSpara() {
		GrigliaAlieni griglia = new GrigliaAlieni(Difficolta.NORMALE, 0);
		griglia.getGriglia().get(0).forEach(Alieno::distruggi);
		assertTrue(griglia.scegliTiratore().isEmpty());
	}
}
