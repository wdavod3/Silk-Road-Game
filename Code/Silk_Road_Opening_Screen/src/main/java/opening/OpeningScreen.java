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


public class OpeningScreen extends Application{
    Label silkRoad;
    Button nickName;
    VBox titles, logCreate;
    Image background;
    BorderPane openPane;

    public static void main(String[] args) {
        launch(args);
    }//end of main

    @Override
    public void start(Stage primaryStage) throws Exception {
        silkRoad = new Label("The Silk Road");
        silkRoad.setStyle("-fx-font-size: 120;" + "-fx-padding: 10;" +
                "-fx-background-radius: 5;" + "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
        + "-fx-font-family: Papyrus,Fantasy;");

//        logIn = new Button("Log In");
//        logIn.setPrefSize(200, 150);
//        logIn.setMaxSize(300, 250);
//        createAccount = new Button("Create Account");
//        createAccount.setPrefSize(200, 150);
//        createAccount.setMaxSize(300, 250);

        nickName = new Button("Create Name");
        nickName.setPrefSize(200, 150);
        nickName.setMaxSize(300, 250);

        titles = new VBox(20, silkRoad);
        titles.setAlignment(Pos.CENTER);
        titles.getStylesheets().add("style.css");
        logCreate = new VBox(30, nickName);
        logCreate.getStylesheets().add("style.css");
        logCreate.setAlignment(Pos.CENTER);

        BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                true, true, true, true);
        background = new Image("silk_road.jpg");

        openPane = new BorderPane();
        openPane.setTop(titles);
        openPane.setCenter(logCreate);
        openPane.setBackground(new Background(new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bgSize)));

        Scene scene = new Scene(openPane,1000,700);
        primaryStage.setScene(scene);
        primaryStage.show();

        nickName.setOnAction(event -> {
            try {
                new nickName().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        logIn.setOnAction(event -> {
//            try {
//                new opening.LogInScreen().start(new Stage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            primaryStage.close();
//        });
//
//        createAccount.setOnAction(event -> {
//            try {
//                new opening.CreateAccount().start(new Stage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            primaryStage.close();
//        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }//end of handle
        });//end of CloseRequest
    }
}

