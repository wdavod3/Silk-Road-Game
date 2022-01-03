package map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import trade.City;
import trade.SilkRoadGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventScreen extends Application{
    Label eventMessage;
    Button close;
    VBox eventBox;
    BorderPane eventPane;
    Integer turn;
    SilkRoadGame player;

    public void transition(Integer turn, Stage primaryStage, SilkRoadGame player){
        this.turn = turn;
        this.player = player;
        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }//end of try catch
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        eventMessage = new Label();
        eventMessage.setAlignment(Pos.CENTER);

        close = new Button("Close");

        eventBox = new VBox(10, eventMessage, close);
        eventBox.setAlignment(Pos.CENTER);

        if (turn == 1) {
            eventMessage.setText("Another traveler gave you a gift.\n" +
                    "Your received 10 pieces of gold.");
            eventMessage.setStyle("-fx-font-size: 30;");
        }
        else if (turn == 2) {
            eventMessage.setText("You got Caught in a Sandstorm.\n" +
                    "Actions Points reduced to 1 for this turn.");
            eventMessage.setStyle("-fx-font-size: 25;");
            eventMessage.setAlignment(Pos.CENTER);
        }
        else if (turn == 3) {
            eventMessage.setText("Your wagon broke down.\n" +
                    "You can't move for 1 turn.");
            eventMessage.setStyle("-fx-font-size: 25;");
            eventMessage.setAlignment(Pos.CENTER);
        }
        else if (turn == 4) {
            if (player.getEventResult() == "You and the bandit were evenly matched so he ran away.") {
                eventMessage.setText("You and the bandit were evenly \nmatched so he ran away.");
                eventMessage.setStyle("-fx-font-size: 25;");
            }
            else if (player.getEventResult() == "You ran away successfully!") {
                    eventMessage.setText("You ran away successfully!");
                    eventMessage.setStyle("-fx-font-size: 25;");
            }

        }
        else if (turn == 10) {
            eventMessage.setText("Half you turns are up.\n" +
                    "Only 10 more turns left!");
            eventMessage.setStyle("-fx-font-size: 25;");
            eventMessage.setAlignment(Pos.CENTER);
        }
        else if (turn == 15) {
            eventMessage.setText("Only 5 turns left!");
            eventMessage.setStyle("-fx-font-size: 25;");
            eventMessage.setAlignment(Pos.CENTER);
        }

        eventPane = new BorderPane();
        eventPane.setCenter(eventBox);

        Stage secondStage = new Stage();
        Scene scene = new Scene(eventPane,500,300);
        switch (player.curPlayer.getFaction().getName()){
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
        secondStage.setScene(scene);
        secondStage.show();

        close.setOnAction(event -> {
            secondStage.close();
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
