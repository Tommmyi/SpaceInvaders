package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.*;
import org.junit.jupiter.api.Test;

class Punto2DTest {

	private static final double DELTA = 1e-9;

	@Test
	void costruttoreImpostaCoordinate() {
		Punto2D p = new Punto2D(3, 2);
		assertEquals(3, p.getX(), DELTA);
		assertEquals(2, p.getY(), DELTA);
	}

	@Test
	void setterModificanoCoordinate() {
		Punto2D p = new Punto2D(0, 0);
		p.setX(-4.5);
		p.setY(7);
		assertEquals(-4.5, p.getX(), DELTA);
		assertEquals(7, p.getY(), DELTA);
	}
}
