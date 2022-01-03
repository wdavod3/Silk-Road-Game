package opening;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import map.GameGUI;
import org.json.simple.JSONObject;

import trade.Bonus;
import trade.Faction;
import trade.SilkRoadGame;

public class SinglePlayer extends Application{
    Label chooseCount, username, statsR, statsC, statsP, tradeR, tradeC, tradeP;
    Button rome, china, parthian;
    VBox layout, listR, listC, listP;
    HBox countries;
    BorderPane singlePane;
    String user;

    public void transition(String user, Stage primaryStage){
        this.user = user;

        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }//end of try catch
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        chooseCount = new Label("Choose Your Country");
        chooseCount.setStyle("-fx-font-size: 75;");

        username = new Label("Player: " + user);
        username.setStyle("-fx-font-size: 50;");

        rome = new Button("Rome");
        rome.setPrefSize(100, 50);
        rome.setMaxSize(200, 150);
        statsR = new Label("Imperial Legacy:\nTrade posts are 25%\ncheaper to build\n");
        tradeR = new Label("Starting Trade Resource:\nGrain");
        statsR.setStyle("-fx-font-size: 20;");
        tradeR.setStyle("-fx-font-size: 20;");
        listR = new VBox(20, rome, statsR, tradeR);
        listR.setAlignment(Pos.CENTER);

        china = new Button("Han");
        china.setPrefSize(100, 50);
        china.setMaxSize(200, 150);
        statsC = new Label("Economic Giant:\nMerchants start\nwith more money\n");
        tradeC = new Label("Starting Trade Resource:\nSilk");
        statsC.setStyle("-fx-font-size: 20;");
        tradeC.setStyle("-fx-font-size: 20;");
        listC = new VBox(20, china, statsC, tradeC);
        listC.setAlignment(Pos.CENTER);

        parthian = new Button("Parthian");
        parthian.setPrefSize(100, 50);
        parthian.setMaxSize(200, 150);
        statsP = new Label("Middlemen of the Silk Road:\nArabian merchant sell\nitems at 2% increase\n");
        tradeP = new Label("Starting Trade Resource:\nSaffron");
        statsP.setStyle("-fx-font-size: 20;");
        tradeP.setStyle("-fx-font-size: 20;");
        listP = new VBox(20, parthian, statsP, tradeP);
        listP.setAlignment(Pos.CENTER);

        countries = new HBox(100, listR, listC, listP);
        countries.setAlignment(Pos.CENTER);

        layout = new VBox(150, chooseCount, countries);
        layout.setAlignment(Pos.CENTER);

        singlePane = new BorderPane();
        singlePane.setCenter(layout);
        singlePane.setTop(username);

        Scene scene = new Scene(singlePane,1000,700);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();


        //LocationsData locationsData = new LocationsData("src/main/resources/3Posts.json","src/main/resources/3Cities.json");
        SilkRoadGame curGame = new SilkRoadGame();
        
        rome.setOnAction(event -> {
            Faction rome = new Faction("Rome", curGame.getCity("Rome"), Bonus.Imperial_Legacy);
            curGame.setTrader(user, rome);
            try {
                new AvatarSelect().transition(curGame, primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        china.setOnAction(event -> {
            Faction han = new Faction("Han", curGame.getCity("Han"), Bonus.Economic_Giant);

        	curGame.setTrader(user, han);
            try {
                new AvatarSelect().transition(curGame, primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        parthian.setOnAction(event -> {
            Faction parthian = new Faction("Parthian", curGame.getCity("Parthian"), Bonus.Middlemen_of_the_Silk_Road);
        	curGame.setTrader(user, parthian);
            try {
                new AvatarSelect().transition(curGame, primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
