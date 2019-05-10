import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DealerTest {

	@Test
	void testStartGame() {
		Dealer tmp = new Dealer();
		tmp.startGame();
		int a = tmp.getDeckSize();
		assertEquals(a, 52, "Initialize deck successful");
		//fail("Not yet implemented");
	}

	@Test
	void testDealCards() {
		Dealer tmp = new Dealer();
		Deck a = tmp.dealCards();
		int b = a.getDeckSize();
		assertEquals(b, 3, "Deal cards successful");
		//fail("Not yet implemented");
	}

}
