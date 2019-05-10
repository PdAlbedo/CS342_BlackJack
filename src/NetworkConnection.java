
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class NetworkConnection {
	Deck dealer_Deck = new Deck();
	private ConnThread connthread = new ConnThread();
	protected int count = 0;
	protected int num_players_ol = 0;
	private Consumer<Serializable> callback;
	boolean clientOne, clientTwo;
	String dataOne, dataTwo;
	ArrayList<ClientThread> ct;
	ArrayList<Player_info> pi;
	//the list to store the players who wants to play
	//ArrayList<ClientThread> playable_list;
	//private int score1,score2;
	private Dealer mydealer = new Dealer();
	int confim = 0;
	
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		ct = new ArrayList<ClientThread>();
		clientOne = false;
		clientTwo = false;
		
	}
	
	public void startConn() throws Exception{
		connthread.start();
		
	}
	
	public void send(Serializable data) throws Exception{
		if(isServer() && clientOne && clientTwo) {
			//String message = compare();
			
			ct.forEach(t->{
				try {
					//t.tout.writeObject(message);
					clientOne = false;
					clientTwo = false;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			});
		}
		else {
			connthread.out.writeObject(data);
		}
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void compare() {
		for(int i =0; i<4; i++) {
			if(ct.get(i).mydeck.cout() < 22) {
				if(dealer_Deck.cout() >21) {
					ct.get(i).result ="Tie!";
				}
				else if(ct.get(i).mydeck.cout() > dealer_Deck.cout()) {
					ct.get(i).result ="you win!";
				}
				else {
					ct.get(i).result ="you lose!";
				}
			}
			else {
				ct.get(i).result ="you lose!";
			}
		}
		
		ct.forEach(t->{
			try {
				
				t.tout.writeObject("Dealer's Card: ");
				for (int a = 0; a < dealer_Deck.getDeckSize(); a++) {
					t.tout.writeObject(dealer_Deck.getNthCard(a).toString());
				}
				t.tout.writeObject("Dealer's points: ");
				t.tout.writeObject(dealer_Deck.cout());
				t.tout.writeObject(t.result);
				t.tout.writeObject("\n");
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void closeConn() throws Exception{
		connthread.socket.close();
	}
	public void closess() throws Exception{
		connthread.closess();
	}
	
	
	abstract protected boolean isServer();
	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		private Socket socket;
		private ObjectOutputStream out;
		private ServerSocket server;
		
		
		
		public void closess() {
			try {
				this.server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void run() {
			int number = 1;
			if(isServer()) {
				try {
					server = new ServerSocket(getPort());
					while(true) {
						ClientThread t1 = new ClientThread(server.accept(),number);
						ct.add(t1);
						//Player_info tmp = new Player_info(number);
						//pi.add(tmp);
						t1.start();
						callback.accept("player" + number + " join");
						number++;
						Thread.sleep(1000);
						if (ct.size() >= 4) {
							//Player_info tmp_s = new Player_info(5);
							//pi.add(tmp_s);
							gameStart();
						}
					}
				}
				catch (Exception e) {
				}
			}
			
			else {
				try {
					Socket socket = new Socket(getIP(), getPort());
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
					this.socket = socket;
					this.out = out;
					socket.setTcpNoDelay(true);
					
					while(true) {
						Serializable data = (Serializable) in.readObject();
						System.out.print(data);
						callback.accept(data);
					}
				} 
				catch (Exception e) {
					
				}
			}
		}

		
	}
	
	class ClientThread extends Thread{
		Socket s;
		int number;
		ObjectOutputStream tout;
		ObjectInputStream tin;
		Player myplayer;
		Deck mydeck = new Deck();
		String result;
		
		ClientThread(Socket socket, int num){
			this.s = socket;
			this.number = num;
			myplayer = new Player();
		}
		public void run() {
			try(
				ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(this.s.getInputStream())){
				s.setTcpNoDelay(true);
				this.tout = out;
				this.tin = in;
				//System.out.print("here");
				tout.writeObject("Welcome player: "+ number + "\n");
				/*
				if(number ==1) {
					tout.writeObject("waiting for another player come in");
				}
				else {
					tout.writeObject("game start, pick you choich");
				}
				
				*/
				while(true) {
					Serializable data = (Serializable) in.readObject();
					String inputdata = data.toString();
					/*
					System.out.println(inputdata.substring(0,4));
					if( inputdata.substring(0, 4).equals("Read")) {
						count++;
						num_players_ol--;
					}*/
					
					System.out.println(inputdata.substring(0,3));
					if( inputdata.substring(0, 3).equals("get")) {
						System.out.println(number);
						Card c = mydealer.dealACard();
						System.out.println(""+c.getSuit()+c.getValue());
						mydeck.addCard(c);
						if (mydeck.cout() > 21) {
							ct.get(number-1).tout.writeObject("\nBOMB!!!! DON'T REQUEST A CARD ANY FURTHER\n");
						}
						
						//mydeck.printCards();
						displaycard();
					}
					if( inputdata.substring(0, 3).equals("yes")) {
						confim++;
					}
					if(confim == 4) {
						System.out.print("suan fen");
						cal_ai_score();
						compare();
					}
					callback.accept(data);
				}
				
			}
			catch(Exception e) {
				callback.accept("connection Closed");
			}
		}
		
	}
	public void cal_ai_score() {
		while (dealer_Deck.cout() <= 14) {
			dealer_Deck.addCard(mydealer.dealACard());
		}
	}
	public void displaycard() throws Exception{
			
			ct.forEach(t->{
				String message;
				try {
					for(int i=0; i<5; i++) {
						if(i == 4) {
							for(int j=0; j< dealer_Deck.getDeckSize(); j++){
								if(j!=0) {
									message = "dealer: " + dealer_Deck.getNthCard(j).toString() + "\n///////////////////////////////////////////////////////";
									t.tout.writeObject(message);
								}
								
							}
						}
						else {
							//t.tout.writeObject("/////////////////////////////////////////////////////////");
							for(int s=0; s< ct.get(i).mydeck.getDeckSize(); s++){
								if(s!=0) {
									message = "player" + (i+1) +" " +ct.get(i).mydeck.getNthCard(s).toString();
									t.tout.writeObject(message);
								}
								
							}
						}
						
						//t.tout.writeObject(message);
					}
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			});
		
		
	}
	

	public void gameStart() {
		mydealer.startGame();
		ArrayList<Deck> display_card = new ArrayList<Deck>();
		//Card card_0;
		Card card;
		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < 5; i++ ) {
				try {
					if(i == 4) {
						card = mydealer.dealACard();
						dealer_Deck.addCard(card);
						
						
					}
					else {
						//card_0 = mydealer.dealACard();
						card = mydealer.dealACard();
						//System.out.print(card.toString());
						//ct.get(i).mydeck.addCard(card);
						ct.get(i).mydeck.addCard(card);
						//System.out.println("1111");
						ct.get(i).tout.writeObject(card.toString());
						//ystem.out.println("2222");
						
						
					}
					
					
					
					//playable_list.get(i).tout.writeObject(card);
					//ct.get(i).tout.writeObject(card.toString());
					//pi.get(i).getDeck().addCard(card_0);


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			displaycard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}	


