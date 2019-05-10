import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

	@Test
	void testGetValue() {
		Card tmp = new Card(Suit.HEART, Value.TWO);
		Value i = tmp.getValue();
		assertEquals(i, Value.TWO, "Get value successful.");
		//fail("Not yet implemented");
	}

	@Test
	void testGetSuit() {
		Card tmp = new Card(Suit.HEART, Value.TWO);
		Suit i = tmp.getSuit();
		assertEquals(i, Suit.HEART, "Get suit successful.");
		//fail("Not yet implemented");
	}

	@Test
	void testCard() {
		Card tmp = new Card(Suit.HEART, Value.TWO);
		Card tmp2 = new Card(Suit.HEART, Value.ACE);
		Suit i = tmp.getSuit();
		Value j = tmp2.getValue();
		assertEquals(i, Suit.HEART, "Get suit successful.");
		assertEquals(j, Value.ACE, "Get value successful.");
		//fail("Not yet implemented");
	}

	@Test
	void testToString() {
		Card tmp = new Card(Suit.HEART, Value.TWO);
		String i = tmp.toString();
		assertEquals(i, "HEART TWO", "Get value successful.");
		//fail("Not yet implemented");
	}

}
