import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BJServer extends Application{
	Dealer myDealer;
	Player myPlayer;
	private boolean isServer = true;
	
	private NetworkConnection  conn; 
	private TextArea messages = new TextArea();

	public static void main(String[] args) {
		
		launch(args);
	}

	//start scene
	Stage myStage;
	Scene scene;
	Button start,exit;
	HashMap<String, Scene> sceneMap;
	
	//network scene
	Scene networking;
	protected Button start_Net;
	Button btn;
	Button quit;
	
	//game scene
	Scene game;
	Label p0_0 = new Label("Dealer:");
	TextArea p0_1;
	Label p1_0 = new Label("Player 1:");
	TextArea p1_1;
	Label p2_0 = new Label("Player 2:");
	TextArea p2_1;
	Label p3_0 = new Label("Player 3:");
	TextArea p3_1;
	Label p4_0 = new Label("Player 4:");
	TextArea p4_1;
	//Label waiting = new Label("Waiting for the other players...");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("BlackJack_Server");
		sceneMap = new HashMap<String, Scene>();
		myStage = primaryStage;
		
	//start scene
		start = new Button("Start Game");
		exit = new Button("Exit Game");
		start.setLayoutX(20);
		start.setLayoutY(200);
		exit.setLayoutX(500);
		exit.setLayoutY(200);
		
		
		//start btn on the start scene
		start.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				myStage.setScene(sceneMap.get("network_s"));
			}
		});
		//exit btn on the start scene
		exit.setOnAction(e->{
			primaryStage.close();
			
		});
		
		//image
		Image pic = new Image("blackJack.jpeg");
		
		ImageView b = new ImageView(pic);
		b.setFitHeight(600);
		b.setFitWidth(600);
		b.setPreserveRatio(true);
		b.setImage(pic);
		
		//set group
		Group root = new Group();
		root.getChildren().addAll(b);
		root.getChildren().add(start);
		root.getChildren().add(exit);
		scene = new Scene(root,600,300);
		
	//network scene
		//startGame = new Button("Start");
		btn = new Button("Server On");
		btn.setLayoutX(300);
		btn.setLayoutY(550);
		start_Net = new Button("Start Game");
		quit = new Button("QUIT");
		quit.setLayoutX(300);
		quit.setLayoutY(1100);
		messages = new TextArea();
		Text textport = new Text("Port:");
		TextField text = new TextField();
		
		HBox game_opt = new HBox(200, btn, start_Net);
		VBox portbox = new VBox(30, textport, text, game_opt, messages, quit);
		portbox.setPadding(new Insets(20,10,0,10));
		
		//Server on btn on network scene
		btn.setOnAction(event->{
			//System.out.println(text.getText());
			int i = Integer.parseInt(text.getText());
			conn = createServer(i);
			try {
				conn.startConn();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server Established: " + i + "\n");
			messages.appendText("Server Established: " + i + "\n");
			btn.setDisable(true);
			//myDealer = new Dealer();
			//myDealer.startGame();
			//myStage.setScene(sceneMap.get("game"));
		});
		
		//start btn on network scene goes to game scene
		start_Net.setOnAction (new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event) {
				myStage.setScene(sceneMap.get("game_s"));
				start_Net.setDisable(true);
			}
		});
		
		//quit btn on network scene
		quit.setOnAction(event-> {
			primaryStage.close();
		});
		
		
		/*
		//exit btn on the start scene
		exit = new Button("Exit");
		exit.setLayoutX(350);
		exit.setLayoutY(380);
		
		exit.setOnAction(e->{
			primaryStage.close();
		});
		
		startGame.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				myDealer = new Dealer();
				myDealer.startGame();
				myStage.setScene(sceneMap.get("game"));
			}
		});
		*/
		
		//set group
		BorderPane network = new BorderPane();
		//Group network = new Group();
		network.setCenter(portbox);
		networking = new Scene(network,500,500);
		//network.getChildren().add(portbox);
		
		
		
		
		
	//game scene
		//image
		Image pic2 = new Image("game.jpg");

		ImageView b2 = new ImageView(pic2);
		b2.setFitHeight(600);
		b2.setFitWidth(900);
		b2.setPreserveRatio(true);
		b2.setImage(pic2);
		
		p0_0.setTextFill(Color.BLACK);
		p0_0.setFont(Font.font("Cambria", 20));
		p0_1 = new TextArea();
		p0_1.setPrefHeight(120);
		p0_1.setPrefWidth(300);
		VBox p0 = new VBox (10, p0_0, p0_1);
		p0.setLayoutX(300);
		p0.setLayoutY(20);
		
		p1_0.setTextFill(Color.BLACK);
		p1_0.setFont(Font.font("Cambria", 20));
		p1_1 = new TextArea();
		p1_1.setPrefHeight(120);
		p1_1.setPrefWidth(300);
		VBox p1 = new VBox (10, p1_0, p1_1);
		p1.setLayoutX(20);
		p1.setLayoutY(180);

		p2_0.setTextFill(Color.BLACK);
		p2_0.setFont(Font.font("Cambria", 20));
		p2_1 = new TextArea();
		p2_1.setPrefHeight(120);
		p2_1.setPrefWidth(300);
		VBox p2 = new VBox (10, p2_0, p2_1);
		p2.setLayoutX(580);
		p2.setLayoutY(180);
		
		p3_0.setTextFill(Color.BLACK);
		p3_0.setFont(Font.font("Cambria", 20));
		p3_1 = new TextArea();
		p3_1.setPrefHeight(120);
		p3_1.setPrefWidth(300);
		VBox p3 = new VBox (10, p3_0, p3_1);
		p3.setLayoutX(20);
		p3.setLayoutY(380);
		
		p4_0.setTextFill(Color.BLACK);
		p4_0.setFont(Font.font("Cambria", 20));
		p4_1 = new TextArea();
		p4_1.setPrefHeight(120);
		p4_1.setPrefWidth(300);
		VBox p4 = new VBox (10, p4_0, p4_1);
		p4.setLayoutX(580);
		p4.setLayoutY(380);
		/*
		waiting.setTextFill(Color.WHITE);
		waiting.setFont(Font.font("Cambria", 20));
		waiting.setLayoutX(330);
		waiting.setLayoutY(290);
		*/
		
		//set group
		Group gamePlay = new Group();
		game = new Scene(gamePlay,900,600);
		gamePlay.getChildren().addAll(b2);
		gamePlay.getChildren().add(p0);
		gamePlay.getChildren().add(p1);
		gamePlay.getChildren().add(p2);
		gamePlay.getChildren().add(p3);
		gamePlay.getChildren().add(p4);
		//gamePlay.getChildren().add(waiting);
		
		
		//map
		sceneMap.put("start_s", scene);
		sceneMap.put("network_s", networking);
		sceneMap.put("game_s", game);
		
		primaryStage.setScene(sceneMap.get("start_s"));
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	
	
	
	private Server createServer(int i) {
		return new Server(i, data-> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
			});
		});
	}

}






