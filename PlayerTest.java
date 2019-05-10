import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void testSetPlayerHand() {
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		Player tmp0 = new Player();
		tmp0.setPlayerHand(tmp);
		assertEquals(4, tmp0.getPlayerHand().getDeckSize(), "Set Player hand successful");
		//fail("Not yet implemented");
	}

	@Test
	void testGetstat() {
		Player tmp0 = new Player();
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		tmp0.setPlayerHand(tmp);
		boolean z = tmp0.getstat();
		assertEquals(z, false, "Get Player status successful");
		//fail("Not yet implemented");
	}

	@Test
	void testValueCal() {
		Player tmp0 = new Player();
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		tmp0.setPlayerHand(tmp);
		int z = tmp0.valueCal();
		assertEquals(z, 9, "Calculate score successful");
		//fail("Not yet implemented");
	}

}
