package opening;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class nickName extends Application {

    Label createName;
    TextField nameSpace;
    Button gMode;
    VBox layout, title;
    String user;
    BorderPane namePane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        createName = new Label ("Create a Name to Play");
        createName.setStyle("-fx-font-size: 80;");
        title = new VBox(createName);
        title.setAlignment(Pos.CENTER);

        nameSpace = new TextField();
        nameSpace.setPrefSize(200, 45);
        nameSpace.setMaxSize(300, 45);

        gMode = new Button("Play Game");
        gMode.setPrefSize(100, 50);
        gMode.setMaxSize(200, 150);

        layout = new VBox(30, nameSpace, gMode);
        layout.setAlignment(Pos.CENTER);

        namePane = new BorderPane();
        namePane.setCenter(layout);
        namePane.setTop(title);

        Scene scene = new Scene(namePane,1000,700);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);

        gMode.setOnAction(event -> {
            if (nameSpace.getText().isEmpty()) {
                createName.setText("Please Create a Name to Play");
                createName.setStyle("-fx-font-size: 70;");
            }
            else {
                try {
                    user = nameSpace.getText();
                    new GameMode().transition(user, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
