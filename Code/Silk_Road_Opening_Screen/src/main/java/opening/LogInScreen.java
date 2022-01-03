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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LogInScreen extends  Application{
    Label logIn, instructions, username, password;
    TextField usernameText, passwordText;
    Button logInB;
    HBox usernameBox, passwordBox;
    VBox logInBox, topBox;
    BorderPane logInPane;
    String user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logIn = new Label("Log In");
        logIn.setStyle("-fx-font-size: 175;");
        topBox = new VBox(logIn);
        topBox.setAlignment(Pos.CENTER);

        instructions = new Label("Enter Username and Password");
        instructions.setStyle("-fx-font-size: 30;");
        username = new Label("Username:");
        username.setStyle("-fx-font-size: 50;");
        password = new Label("Password:");
        password.setStyle("-fx-font-size: 50;");

        usernameText = new TextField();
        usernameText.setPrefSize(200, 45);
        usernameText.setMaxSize(300, 45);
        usernameBox = new HBox(10, username, usernameText);
        usernameBox.setAlignment(Pos.CENTER);

        passwordText = new TextField();
        passwordText.setPrefSize(200, 45);
        passwordText.setMaxSize(300, 45);
        passwordBox = new HBox(10, password, passwordText);
        passwordBox.setAlignment(Pos.CENTER);

        logInB = new Button("Log In");
        logInB.setPrefSize(100, 50);
        logInB.setMaxSize(200, 150);

        logInBox = new VBox(20, instructions, usernameBox, passwordBox, logInB);
        logInBox.setAlignment(Pos.CENTER);

        logInPane = new BorderPane();
        logInPane.setTop(topBox);
        logInPane.setCenter(logInBox);


        Scene scene = new Scene(logInPane,1000,700);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        logInB.setOnAction(event -> {
            if (usernameText.getText().isEmpty() && passwordText.getText().isEmpty()) {
                instructions.setText("Username and Password are Blank \n\tPlease Enter Both to Log In");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (usernameText.getText().isEmpty()) {
                instructions.setText("       Username is Blank\nPlease Enter your Username");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (passwordText.getText().isEmpty()) {
                instructions.setText("       Password is Blank \nPlease Enter your Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else {
                try {
                    user = usernameText.getText();
                    new GameMode().transition(user, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
