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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class AvatarSelect extends Application{

    Label chooseAvatar, username;
    Button avatar1, avatar2, avatar3, avatar4;
    VBox layout, buttons;
    HBox row1, row2;
    BorderPane  avatarPane;

    //Trader trader;
    //LocationsData locationsData;
    SilkRoadGame curGame;

    public void transition(SilkRoadGame game, Stage primaryStage){
        this.curGame = game;
        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }//end of try catch
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        chooseAvatar = new Label("Choose an Avatar");
        chooseAvatar.setStyle("-fx-font-size: 75;");

        username = new Label("Player: " + curGame.getTraderName());
        username.setStyle("-fx-font-size: 50;");

        avatar1 = new Button();

        avatar2 = new Button();

        row1 = new HBox(150, avatar1, avatar2);
        row1.setAlignment(Pos.CENTER);

        avatar3 = new Button();

        avatar4 = new Button();

        row2 = new HBox(150, avatar3, avatar4);
        row2.setAlignment(Pos.CENTER);

        buttons = new VBox(100, row1, row2);
        buttons.setAlignment(Pos.CENTER);

        layout = new VBox(50, chooseAvatar, buttons);
        layout.setAlignment(Pos.CENTER);

        avatarPane = new BorderPane();
        avatarPane.setCenter(layout);
        avatarPane.setTop(username);

        String currentFaction = curGame.curPlayer.getFaction().getName();

        Scene scene = new Scene(avatarPane, 1000, 700);
        switch (currentFaction){
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

        String[] hanImages = new String[]{"han1.jpg", "han2.jpg", "han3.jpg", "han4.jpg"};
        String[] parthianImages = new String[]{"parthian1.jpg", "parthian2.jpg","parthian3.jpg", "parthian4.jpg" };
        String[] romanImages = new String[]{"roman1.jpg", "roman2.jpg", "roman3.jpg", "roman4.jpg"};

        ImageView[] imageViews = new ImageView[]{
                new ImageView(), new ImageView(), new ImageView(), new ImageView()
        };


        String[] currentFactionAvatars;
        switch (currentFaction){
            case "Rome":
                currentFactionAvatars = romanImages;
                break;
            case "Parthian":
                currentFactionAvatars = parthianImages;
                break;
            case "Han":
            default:
                currentFactionAvatars = hanImages;
                break;
        }

        for (int i = 0; i < 4; i++) {
            ImageView imageview = imageViews[i];
            imageview.setImage(new Image(currentFactionAvatars[i]));
            imageview.setFitHeight(150);
            imageview.setFitWidth(150);
            imageview.setPreserveRatio(true);
        }


        avatar1.setGraphic(imageViews[0]);
        avatar2.setGraphic(imageViews[1]);
        avatar3.setGraphic(imageViews[2]);
        avatar4.setGraphic(imageViews[3]);


        avatar1.setOnAction(event -> {
            try {
                new GameGUI().transition(curGame, (ImageView) avatar1.getGraphic(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        avatar2.setOnAction(event -> {
            try {
                new GameGUI().transition(curGame, (ImageView) avatar2.getGraphic(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        avatar3.setOnAction(event -> {
            try {
                new GameGUI().transition(curGame, (ImageView) avatar3.getGraphic(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        avatar4.setOnAction(event -> {
            try {
                new GameGUI().transition(curGame, (ImageView) avatar4.getGraphic(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
