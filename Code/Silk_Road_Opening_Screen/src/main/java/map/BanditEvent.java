package map;

import abstract_events.EventChoice;
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


public class BanditEvent extends Application{
    Label message;
    Button fight, run;
    HBox buttons;
    VBox layout;
    BorderPane banditPane;
    SilkRoadGame player;

    public void transition(Stage primaryStage, SilkRoadGame player){
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

        message = new Label("You see a band of bandits ahead. \nWhat would you like to do?");
        message.setStyle("-fx-font-size: 30;");

        fight = new Button("Fight");
        run = new Button("Run");
        buttons = new HBox(15, fight, run);
        buttons.setAlignment(Pos.CENTER);

        layout = new VBox(10, message, buttons);
        layout.setAlignment(Pos.CENTER);


        banditPane = new BorderPane();
        banditPane.setCenter(layout);

        Stage secondStage = new Stage();
        Scene scene = new Scene(banditPane,500,300);
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

        fight.setOnAction(event -> {
            player.pickEventChoice(EventChoice.Fight);
            try {
                new EventScreen().transition(4, primaryStage, player);
            } catch (Exception e) {
                e.printStackTrace();
            }
            secondStage.close();

        });

        run.setOnAction(event -> {
            player.pickEventChoice(EventChoice.Run);
            try {
                new EventScreen().transition(4, primaryStage, player);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
