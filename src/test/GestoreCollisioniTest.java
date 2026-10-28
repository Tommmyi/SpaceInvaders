package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class GestoreCollisioniTest {

	private static final double DELTA = 1e-9;
	private static final double RESISTENZA = Scudo.RESISTENZA;

	private Gioco gioco;
	private GestoreCollisioni gestore;

	@BeforeEach
	void setUp() {
		gioco = new Gioco();
		gioco.avvia(Difficolta.NORMALE);
		gestore = new GestoreCollisioni();
	}

	private Punto2D copia(Punto2D p) {
		return new Punto2D(p.getX(), p.getY());
	}

	private ProiettileNemico proiettileNemicoSu(OggettoDiGioco bersaglio) {
		ProiettileNemico p = new ProiettileNemico(copia(bersaglio.getPosizione()), 3);
		gioco.getSpariNemici().add(p);
		return p;
	}

	private ProiettileGiocatore proiettileGiocatoreSu(OggettoDiGioco bersaglio) {
		ProiettileGiocatore p = new ProiettileGiocatore(copia(bersaglio.getPosizione()));
		gioco.getSpariGiocatore().add(p);
		return p;
	}

	@Test
	void proiettileColpisceGiocatore() {
		ProiettileNemico p = proiettileNemicoSu(gioco.getGiocatore());
		gestore.risolvi(gioco);
		assertEquals(Gioco.VITE_FACILE_NORMALE - 1, gioco.getViteGiocatore());
		assertFalse(p.getStatoVita());
	}

	@Test
	void contattoAlienoGiocatore() {
		Alieno a = gioco.getGrigliaAlieni().getGriglia().get(1).get(0);
		a.setPosizione(copia(gioco.getGiocatore().getPosizione()));
		gestore.risolvi(gioco);
		assertEquals(0, gioco.getViteGiocatore());
		assertFalse(gioco.getGiocatore().getStatoVita());
	}
	
	@Test
	void proiettileMortoNonColpisce() {
		Alieno a = gioco.getGrigliaAlieni().getGriglia().get(1).get(0);
		ProiettileGiocatore p = proiettileGiocatoreSu(a);
		p.distruggi();
		gestore.risolvi(gioco);
		assertTrue(a.getStatoVita());
		assertEquals(0, gioco.getPunteggio());
		}

	@Test
	void proiettileNonColpisceAlienoGiaMorto() {
		Alieno a = gioco.getGrigliaAlieni().getGriglia().get(1).get(0);
		a.distruggi();
		ProiettileGiocatore p = proiettileGiocatoreSu(a);
		gestore.risolvi(gioco);
		assertTrue(p.getStatoVita());
		assertEquals(0, gioco.getPunteggio());
	}

	@Test
	void proiettileNemicoDanneggiaScudo() {
		Scudo s = gioco.getScudi().get(0);
		ProiettileNemico p = proiettileNemicoSu(s);
		gestore.risolvi(gioco);
		assertEquals(RESISTENZA - 1, s.getVitaScudo(), DELTA);
		assertFalse(p.getStatoVita());
	}

	@Test
	void proiettileGiocatoreDanneggiaScudo() {
		Scudo s = gioco.getScudi().get(1);
		ProiettileGiocatore p = proiettileGiocatoreSu(s);
		gestore.risolvi(gioco);
		assertEquals(RESISTENZA - 1, s.getVitaScudo(), DELTA);
		assertFalse(p.getStatoVita());
	}

	@Test
	void scudoDistruttoNonFermaProiettili() {
		Scudo s = gioco.getScudi().get(2);
		s.azzeraVitaScudo();
		ProiettileNemico p = proiettileNemicoSu(s);
		gestore.risolvi(gioco);
		assertTrue(p.getStatoVita());
	}

	@Test
	void alienoCheToccaScudoLoDistrugge() {
		Scudo s = gioco.getScudi().get(3);
		Alieno a = gioco.getGrigliaAlieni().getGriglia().get(4).get(10);
		a.setPosizione(copia(s.getPosizione()));
		gestore.risolvi(gioco);
		assertFalse(s.getStatoVita());
	}
}
