
public class Player_info {
	
	private Deck deck;
	private int Player_order;
	
	public Player_info(Deck a, int b) {
		this.deck = a;
		this.Player_order = b;
	}
	public Player_info(int a) {
		this.deck = null;
		this.Player_order = a;
	}
	
	public int getorder() {
		return this.Player_order;
	}
	
	public void setorder(int a) {
		this.Player_order = a;
	}
	
	public Deck getDeck() {
		return this.deck;
	}
	
	public void setDeck(Deck a) {
		this.deck = a;
	}
}
