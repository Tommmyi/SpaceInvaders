package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import model.*;

class ProiettileTest {

	private static final double DELTA = 1e-9;

	@Test
	void proiettileGiocatoreSale() {
		ProiettileGiocatore p = new ProiettileGiocatore(new Punto2D(10, 300));
		double velocita = p.getVelocita();
		p.muovi();
		assertEquals(300 - velocita, p.getPosizione().getY(), DELTA);
		assertEquals(10, p.getPosizione().getX(), DELTA);
	}

	@Test
	void proiettileNemicoScende() {
		ProiettileNemico p = new ProiettileNemico(new Punto2D(10, 300), 4);
		p.muovi();
		p.muovi();
		assertEquals(308, p.getPosizione().getY(), DELTA);
		assertEquals(10, p.getPosizione().getX(), DELTA);
	}
}
