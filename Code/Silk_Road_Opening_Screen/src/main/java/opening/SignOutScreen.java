package opening;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignOutScreen extends Application{

    Label signOutMessage;
    Button returnLogIn, exit;
    VBox signOutLayout;
    BorderPane signOutPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        signOutMessage = new Label("You Have Logged Out");
        signOutMessage.setStyle("-fx-font-size: 40;");

        returnLogIn = new Button("  Return to\nHome Screen");
        returnLogIn.setStyle("-fx-font-size: 22;");
        returnLogIn.setPrefSize(100, 200);
        returnLogIn.setMaxSize(150, 125);

        exit = new Button("Quit Game");
        exit.setPrefSize(100, 200);
        exit.setMaxSize(150, 125);

        signOutLayout = new VBox(20, signOutMessage, returnLogIn, exit);
        signOutLayout.setAlignment(Pos.CENTER);

        signOutPane = new BorderPane();
        signOutPane.setCenter(signOutLayout);

        Scene scene = new Scene(signOutPane,500,450);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        returnLogIn.setOnAction(event -> {
            try {
                new OpeningScreen().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exit.setOnAction(event -> {
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
