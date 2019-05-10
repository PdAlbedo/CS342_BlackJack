import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeckTest {

	@Test
	void testGetDeckSize() {
		Deck tmp = new Deck();
		int a = tmp.getDeckSize();
		assertEquals(a, 0, "get Deck size successful.");
		//fail("Not yet implemented");
	}

	@Test
	void testSetDeckSize() {
		Deck tmp = new Deck();
		tmp.setDeckSize(20);
		int b = tmp.getDeckSize();
		assertEquals(b, 20, "get Deck size successful.");
		//fail("Not yet implemented");
	}

	@Test
	void testGetNthCard() {
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		Card x = tmp.getNthCard(4);
		Suit y = x.getSuit();
		Value z = x.getValue();
		assertEquals(y, Suit.HEART, "Get nth card successful");
		assertEquals(z, Value.FIVE, "Get nth card successful");
		//fail("Not yet implemented");
	}

	@Test
	void testAddCard() {
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		int c = tmp.getDeckSize();
		assertEquals(c, 2, "Add card successful");
		//fail("Not yet implemented");
	}

	@Test
	void testShuffle() {
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		tmp.shuffle();
		Card x = tmp.getNthCard(1);
		Value z = x.getValue();
		Card y = tmp.getNthCard(2);
		Value v = y.getValue();
		assertEquals(z, Value.TWO, "Shuffle successful");
		assertEquals(v, Value.THREE, "Shuffle successful");
		//fail("Not yet implemented");
	}

	@Test
	void testCout() {
		Card a = new Card(Suit.HEART, Value.TWO);
		Card b = new Card(Suit.HEART, Value.THREE);
		Card c = new Card(Suit.HEART, Value.FOUR);
		Card d = new Card(Suit.HEART, Value.FIVE);
		Deck tmp = new Deck();
		tmp.addCard(a);
		tmp.addCard(b);
		tmp.addCard(c);
		tmp.addCard(d);
		int x = tmp.cout();
		assertEquals(x, 9, "Calculate score successful");
		//fail("Not yet implemented");
	}

}
