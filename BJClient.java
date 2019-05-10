import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BJClient extends Application{
	Dealer myDealer;
	Player myPlayer;

	private boolean isServer = false;
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
	Button connect_n;
	Button exit_n;
	Button pass;
	Button get;
	Label info;
	
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
		
		primaryStage.setTitle("Welcome to Black Jack");
		sceneMap = new HashMap<String, Scene>();
		myStage = primaryStage;
		
	//start scene
		start = new Button("Start Game");
		exit = new Button("Exit Game");
		start.setLayoutX(20);
		start.setLayoutY(200);
		exit.setLayoutX(500);
		exit.setLayoutY(200);
		
		
		//buttons
		start.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				myStage.setScene(sceneMap.get("network_sc"));
			}
		});
		
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
		
	//network & game scene
		
		messages.setPrefHeight(200);
		//TextField input = new TextField();
		
		Label textport = new Label("Port: ");
		textport.setFont(Font.font("cambria", 20));
		textport.setTextFill(Color.WHITE);
		TextField portinput = new TextField();
		portinput.setMaxWidth(200);
		Label textip = new Label("IP: ");
		textip.setFont(Font.font("cambria", 20));
		textip.setTextFill(Color.WHITE);
		TextField ipinput = new TextField();
		ipinput.setMaxWidth(200);
		connect_n = new Button("Connect");
		exit_n = new Button("Exit");
		info = new Label("Game Info:");
		info.setFont(Font.font("cambria", 25));
		info.setTextFill(Color.WHITE);
		messages.setPrefHeight(400);
		messages.setPrefWidth(450);
		pass = new Button("SUBMIT");
		get = new Button("GET");
		//exit.setLayoutX(350);
		//exit.setLayoutY(380);
		
		//start_n.setDisable(true);
		
		connect_n.setOnAction(event->{
			System.out.println("conneting..");
			System.out.println("port: "+ portinput.getText());
			System.out.println("ip: "+ ipinput.getText());
			messages.appendText("////////////////////\n");
			messages.appendText("Conneting..\n");
			messages.appendText("Port: " + portinput.getText() + "\n");
			messages.appendText("Ip: " + ipinput.getText() + "\n");
			messages.appendText("////////////////////\n");
			int porti = Integer.parseInt(portinput.getText());
			
			String address = ipinput.getText();
			portinput.clear();
			ipinput.clear();
			conn = createClient(address,porti);
			messages.appendText("connected to " + porti + "\n");
			try {
				conn.startConn();
				//System.out.print("start");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//messages.appendText("connected to "+ porti);
			connect_n.setDisable(true);
		});
		
		exit_n.setOnAction(e->{
			primaryStage.close();
			
		});
		
		get.setOnAction(event->{
			System.out.println("Get another card.");
			try {
				conn.send("get");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		pass.setOnAction(e->{
			try {
				conn.send("yes");
				pass.setDisable(true);
				get.setDisable(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Image pic2 = new Image("bj2.jpg");

		ImageView b2 = new ImageView(pic2);
		b2.setFitHeight(600);
		b2.setFitWidth(900);
		//b2.setPreserveRatio(true);
		b2.setImage(pic2);
		
		HBox opts = new HBox (60, connect_n, exit_n);
		VBox box = new VBox (20, textport, portinput, textip, ipinput, opts);
		box.setLayoutX(50);
		box.setLayoutY(20);
		HBox get_pass = new HBox (50, get, pass);
		VBox game_info = new VBox (30, info, messages, get_pass);
		game_info.setLayoutX(400);
		game_info.setLayoutY(20);
		//set group
		Group network = new Group();
		networking = new Scene(network,900,600);
		network.getChildren().addAll(b2);
		network.getChildren().addAll(box, game_info);
	
		
		sceneMap.put("start_sc", scene);
		sceneMap.put("network_sc", networking);
		
		primaryStage.setScene(sceneMap.get("start_sc"));
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	private Client createClient(String ip, int i) {
		return new Client(ip, i, data -> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
			});
		});
	}

}





















