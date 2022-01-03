package opening;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;
import trade.SilkRoadGame;

public class EndScreen extends Application{
    Label endMessage, resultMessage;
    Button playAgain, quit;
    VBox layout;
    HBox buttonLayout;
    BorderPane endPane;

    SilkRoadGame curgame;

    public void transition(Stage primaryStage, SilkRoadGame game){
        this.curgame = game;
        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }//end of try catch
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        endMessage = new Label("Game is over!\n" + "Total gold: " + curgame.getWallet() + "\n"
        + "Total Items: " + curgame.getInventory());
        endMessage.setAlignment(Pos.CENTER);
        endMessage.setStyle("-fx-font-size: 40;");

        resultMessage = new Label();

        if (curgame.getWallet() >= 20.0) {
            resultMessage.setText("Great Job! That's a lot of gold you got!\n" + "Do you want to play again?");
        }
        else if (curgame.getWallet() >= 10.0) {
            resultMessage.setText("Not Bad! That's a decent amount of gold!\n"  + "Do you want to play again?");
        }
        else if (curgame.getWallet() < 10.0) {
            resultMessage.setText("What Happened To All Your Gold?\n" + "Do you want to play again?");
        }
        else {
            resultMessage.setText("Do you want to play again?");
        }

        resultMessage.setAlignment(Pos.CENTER);
        resultMessage.setStyle("-fx-font-size: 40;");

        playAgain = new Button("Play Again?");
        quit = new Button ("Quit");

        buttonLayout = new HBox(15, playAgain, quit);
        buttonLayout.setAlignment(Pos.CENTER);

        layout = new VBox(20, endMessage, resultMessage, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        endPane = new BorderPane();
        endPane.setCenter(layout);

        Scene scene = new Scene(endPane,1000,750);
        switch (curgame.curPlayer.getFaction().getName()){
            case "Rome":
                scene.getStylesheets().add("Rome.css");
                break;
            case "Parthian":
                scene.getStylesheets().add("Parthian.css");
                break;
            case "Han":
                scene.getStylesheets().add("Han.css");
                break;
            default:
                scene.getStylesheets().add("style.css");
                break;
        }
        primaryStage.setScene(scene);
        primaryStage.show();

        playAgain.setOnAction(event -> {
            try {
                new GameMode().transition(curgame.getTraderName(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        quit.setOnAction(event -> {
            primaryStage.close();
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }//end of handle
        });//end of CloseRequest

    }
}
