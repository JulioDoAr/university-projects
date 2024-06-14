package com.circuit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.DecimalFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CircuitTest {

	private final String toString1 = "<circuito:Australia> <cond: Gravilla> <comp: BAJA(original:1,00)(actual:1,05)> <dist: LARGA(original:300)(actual:285,00)>";
	private final String toString2 = "<circuito:Portugal> <cond: Gravilla Nocturno> <comp: MEDIA(original:1,25)(actual:1,57)> <dist: INTERMEDIA(original:275)(actual:209,00)>";

	private static DecimalFormat df;
	static CircuitFactory circuitFactory;

	@BeforeAll
	public static void beforeAll() {
		df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		circuitFactory = new CircuitFactoryImpl();
	}

	@Test
	void test_creation() {
		Circuit c = circuitFactory.portugal();
		assertNotNull(c);
		assertEquals("Portugal", c.getName());
		assertEquals(Complexity.MEDIA, c.getComplexity());
		assertEquals(Distance.INTERMEDIA, c.getDistance());
		assertEquals(1.57, c.getCalculatedComplexity());
		assertEquals(209.0, c.getCalculatedDistance());

		c = circuitFactory.australia();
		assertNotNull(c);
		assertEquals("Australia", c.getName());
		assertEquals(Complexity.BAJA, c.getComplexity());
		assertEquals(Distance.LARGA, c.getDistance());
		assertEquals(1.05, c.getCalculatedComplexity());
		assertEquals(285.0, c.getCalculatedDistance());

		c = circuitFactory.chile();
		assertNotNull(c);
		assertEquals("Chile", c.getName());
		assertEquals(Complexity.ALTA, c.getComplexity());
		assertEquals(Distance.CORTA, c.getDistance());
		assertEquals(1.58, c.getCalculatedComplexity());
		assertEquals(237.5, c.getCalculatedDistance());

		c = circuitFactory.alemania();
		assertNotNull(c);
		assertEquals("Alemania", c.getName());
		assertEquals(Complexity.MEDIA, c.getComplexity());
		assertEquals(Distance.INTERMEDIA, c.getDistance());
		assertEquals(1.44, c.getCalculatedComplexity());
		assertEquals(233.75, c.getCalculatedDistance());

		c = circuitFactory.corcega();
		assertNotNull(c);
		assertEquals("Córcega", c.getName());
		assertEquals(Complexity.MEDIA, c.getComplexity());
		assertEquals(Distance.INTERMEDIA, c.getDistance());
		assertEquals(1.57, c.getCalculatedComplexity());
		assertEquals(209.0, c.getCalculatedDistance());

		c = circuitFactory.cerdena();
		assertNotNull(c);
		assertEquals("Cerdeña", c.getName());
		assertEquals(Complexity.ALTA, c.getComplexity());
		assertEquals(Distance.CORTA, c.getDistance());
		assertEquals(1.82, c.getCalculatedComplexity());
		assertEquals(201.88, c.getCalculatedDistance());

		c = circuitFactory.finlandia();
		assertNotNull(c);
		assertEquals("Finlandia", c.getName());
		assertEquals(Complexity.ALTA, c.getComplexity());
		assertEquals(Distance.CORTA, c.getDistance());
		assertEquals(2.28, c.getCalculatedComplexity());
		assertEquals(153.0, c.getCalculatedDistance());
	}

	@Test
	void test_toString() {
		Circuit portugal = circuitFactory.portugal();
		Circuit australia = circuitFactory.australia();

		assertEquals(toString1, australia.toString());
		assertEquals(toString2, portugal.toString());
	}

}
