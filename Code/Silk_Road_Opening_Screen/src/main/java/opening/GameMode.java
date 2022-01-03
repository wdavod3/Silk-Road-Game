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

public class GameMode extends Application{

    Label gameMode, username;
    Button singlePlayer, multiPlayer, switchAccount, logOut;
    HBox playerMode;
    VBox gameModeBox, signOutBox;
    BorderPane gameModePane;
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

        gameMode = new Label("Choose Game Mode");
        gameMode.setStyle("-fx-font-size: 75;");

        username = new Label("Player: " + user);
        username.setStyle("-fx-font-size: 50;");

        singlePlayer = new Button("Single Player");
        singlePlayer.setPrefSize(300, 250);
        singlePlayer.setMaxSize(400, 350);

        multiPlayer = new Button("Multi Player");
        multiPlayer.setPrefSize(300, 250);
        multiPlayer.setMaxSize(400, 350);
        multiPlayer.setDisable(true);

        playerMode = new HBox(50, singlePlayer, multiPlayer);
        playerMode.setAlignment(Pos.CENTER);

//        switchAccount = new Button("Switch Account");
//        switchAccount.setPrefSize(100, 50);
//        switchAccount.setMaxSize(200, 150);

        logOut = new Button("Log Out");
        logOut.setPrefSize(100, 50);
        logOut.setMaxSize(200, 150);

        signOutBox = new VBox(10, logOut);
        signOutBox.setAlignment(Pos.CENTER);
        gameModeBox = new VBox(50, gameMode, playerMode, signOutBox);
        gameModeBox.setAlignment(Pos.CENTER);

        gameModePane = new BorderPane();
        gameModePane.setCenter(gameModeBox);
        gameModePane.setTop(username);

        Scene scene = new Scene(gameModePane,1000,700);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        singlePlayer.setOnAction(event -> {
            try {
                new SinglePlayer().transition(user, primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        switchAccount.setOnAction(event -> {
//            try {
//                new opening.LogInScreen().start(new Stage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            primaryStage.close();
//        });

        logOut.setOnAction(event -> {
            try {
                new SignOutScreen().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
