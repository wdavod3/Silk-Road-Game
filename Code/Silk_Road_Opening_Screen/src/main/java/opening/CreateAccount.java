package opening;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAccount extends  Application{
    Label create, instructions, username, password, reenter, colon;
    Button createAccount;
    TextField userText, passText, reText;
    HBox userBox, passBox, reBox;
    VBox layout;
    BorderPane createPane;
    String user;


    public static void main(String[] args) {
        launch(args);
    }//end of main

    @Override
    public void start(Stage primaryStage) throws Exception {
        create = new Label("Create Account");
        create.setStyle("-fx-font-size: 75;");
        instructions = new Label("Create a Username and Password");
        instructions.setStyle("-fx-font-size: 30;");
        username = new Label("Username:");
        username.setStyle("-fx-font-size: 50;");
        password = new Label("Password:");
        password.setStyle("-fx-font-size: 50;");
        reenter = new Label("Reenter \nPassword");
        reenter.setStyle("-fx-font-size: 45;");
        colon = new Label(":");
        colon.setStyle("-fx-font-size: 50;");

        userText = new TextField();
        userText.setPrefSize(200, 45);
        userText.setMaxSize(300, 45);
        userBox = new HBox(10, username, userText);
        userBox.setAlignment(Pos.CENTER);

        passText = new TextField();
        passText.setPrefSize(200, 45);
        passText.setMaxSize(300, 45);
        passBox = new HBox(10, password, passText);
        passBox.setAlignment(Pos.CENTER);

        reText = new TextField();
        reText.setPrefSize(200, 45);
        reText.setMaxSize(300, 45);
        reBox =  new HBox(10, reenter, colon, reText);
        reBox.setAlignment(Pos.CENTER);

        createAccount = new Button("Create Account");
        createAccount.setPrefSize(100, 50);
        createAccount.setMaxSize(200, 150);


        layout = new VBox(20, create, instructions, userBox, passBox, reBox, createAccount);
        layout.setAlignment(Pos.CENTER);

        createPane = new BorderPane();
        createPane.setCenter(layout);

        Scene scene = new Scene(createPane,1000,700);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        createAccount.setOnAction(event ->  {
            if (userText.getText().isEmpty() && passText.getText().isEmpty() && reText.getText().isEmpty()) {
                instructions.setText("                All Fields are Empty\nPlease Create Username and Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (userText.getText().isEmpty() && passText.getText().isEmpty()) {
                instructions.setText("Please Create Username and Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (userText.getText().isEmpty() && reText.getText().isEmpty()) {
                instructions.setText("Please Create your Username and Reenter you Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (passText.getText().isEmpty() && reText.getText().isEmpty()) {
                instructions.setText("Please Create your Password and Reenter it");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (userText.getText().isEmpty()) {
                instructions.setText("       Username is Blank\nPlease Create your Username");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (passText.getText().isEmpty()) {
                instructions.setText("       Password is Blank \nPlease Create your Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (reText.getText().isEmpty()) {
                instructions.setText("       Please Reenter you Password");
                instructions.setAlignment(Pos.CENTER);
            }
            else if (!passText.getText().equals(reText.getText())) {
                instructions.setText("       Passwords Doesn't Match\nMake Sure your Passwords Match");
                instructions.setAlignment(Pos.CENTER);
            }
            else {
                user = userText.getText();
                try {
                    new GameMode().transition(user, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}