package map;

import actual_events.LocalBanditAttack;
import actual_events.LocalBrokenWagon;
import actual_events.LocalItemGift;
import actual_events.LocalSandstorm;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import opening.EndScreen;
import trade.City;
import trade.SilkRoadGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GameGUI extends Application {

    enum Action {Move, Other}
    Action currentAction;
    boolean mapChange = true;

    Random rand = new Random();

    int randTurn1 = rand.nextInt(20);
    int randTurn2 = rand.nextInt(20);
    int randTurn3 = rand.nextInt(20);
    int randTurn4 = rand.nextInt(20);
    int randTurn5 = rand.nextInt(20);
    int randTurn6 = rand.nextInt(20);

    IntegerProperty actionPoints = new SimpleIntegerProperty();
    DoubleProperty wallet = new SimpleDoubleProperty();

    //LocationsData locationsData;
    SilkRoadGame curGame;
    ImageView avatar;

    public static void main(String[] args) {
        launch(args);
    }

    public void transition(SilkRoadGame game, ImageView player, Stage primaryStage){
        this.curGame = game;
        this.avatar = player;
        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }//end of try catch
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Silk Road");

        VBox holder = new VBox();
        BorderPane borderPane = new BorderPane();

        //Basic Canvas holding Terrain, Basic Map, and HexInfo Map
        Canvas basicCanvas = new Canvas(980, 600);
        GraphicsContext basicGC = basicCanvas.getGraphicsContext2D();

        //Action Canvas holding movement Actions
        Canvas actionCanvas = new Canvas(980, 600);
        GraphicsContext actionGC = actionCanvas.getGraphicsContext2D();

        //Political Canvas holding States
        Canvas politicalCanvas = new Canvas(980, 600);
        GraphicsContext politicalGC = politicalCanvas.getGraphicsContext2D();


        ObjectInputStream out;
        HashMap<Hexagon, HexData> data = null;

        try {
            out = new ObjectInputStream(new FileInputStream("src/main/resources/RomanEraMap"));
            data = (HashMap<Hexagon, HexData>) out.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MapData mapData = new MapData(data);


        //Always Will be rendered basically
        Map basicMap = new BasicMap(mapData, basicGC);
        basicMap.selected = true;

        TerrainMap terrainMap = new TerrainMap(mapData, basicGC);
        terrainMap.selected = true;

        ActionMap actionMap = new ActionMap(mapData, actionGC);
        actionMap.selected = true;

        PoliticalMap politicalMap = new PoliticalMap(mapData, politicalGC);
        politicalMap.selected = true;

        BuildingMap buildingMap = new BuildingMap(mapData, basicGC);
        buildingMap.selected = true;

        PlayerMap playerMap = new PlayerMap(mapData, basicGC, curGame.getTraderName());

        //Manual Setup
        Hexagon rome = new Hexagon(0,4);
        Hexagon han = new Hexagon(18,3);
        Hexagon parthian = new Hexagon(5,6);

        //Manually defining cities
        //TODO: Let Map Editor take care of this
        mapData.getHexData(rome).setBuilding(new Building("Rome"));
        mapData.getHexData(han).setBuilding(new Building("Han"));
        mapData.getHexData(parthian).setBuilding(new Building("Parthian"));

        Integer[] location = curGame.curPlayer.getFaction().getCapitalCity().location;
        Hexagon origin = new Hexagon(location[0], location[1]);
        playerMap.setPlayer(origin);

        playerMap.selected = true;

        ArrayList<Map> mapOrder = new ArrayList<>();
        mapOrder.add(basicMap);
        mapOrder.add(terrainMap);
        mapOrder.add(buildingMap);
        mapOrder.add(playerMap);
        mapOrder.add(actionMap);
        mapOrder.add(politicalMap);

        //Adding Canvases to BorderPane
        Pane pane = new Pane();
        pane.getChildren().addAll(basicCanvas, actionCanvas, politicalCanvas);

        //Top Bar
        HBox topBar = new HBox();
        HBox leftSide = new HBox();
        Label nameLabel = new Label("Player Name: " + curGame.getTraderName());

        //Action Points
        actionPoints.set(curGame.getCurrentActionPoints());
        Label actionPointsTitleLabel = new Label("Action Points: ");
        Label actionPointsLabel = new Label();
        actionPointsLabel.textProperty().bind(actionPoints.asString());

        ImageView aP = new ImageView(new Image("action_points.png"));
        aP.setFitWidth(30);
        aP.setFitHeight(30);
        aP.setPreserveRatio(true);

        //Wallet
        wallet.set(curGame.getWallet());
        Label walletLabel = new Label();
        walletLabel.textProperty().bind(wallet.asString());


        ImageView gP = new ImageView(new Image("gary.png"));
        gP.setFitHeight(30);
        gP.setFitWidth(30);
        gP.setPreserveRatio(true);

        Integer turn = curGame.getCurrentTurn();
        Label turnCount = new Label("Turn #: " + turn);


        //Setting up left side
        leftSide.getChildren().addAll(nameLabel, new Separator(Orientation.VERTICAL), aP, actionPointsTitleLabel, actionPointsLabel, new Separator(Orientation.VERTICAL), gP, walletLabel, new Separator(Orientation.VERTICAL), turnCount, new Separator(Orientation.VERTICAL));
        leftSide.setAlignment(Pos.CENTER_LEFT);
        leftSide.setPadding(new Insets(10));

        //End Turn
        Button endTurn = new Button("End Turn");
        endTurn.setOnAction(event -> {
            curGame.endTurn();
            turnCount.setText("Turn #: " + curGame.increaseTurn());
            mapChange = true;

            if (curGame.getCurrentTurn() == 20) {
                try {
                    new EndScreen().transition(primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (curGame.getCurrentTurn() == 10) {
                try {
                    new EventScreen().transition(10, primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (curGame.getCurrentTurn() == 15) {
                try {
                    new EventScreen().transition(15, primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (curGame.getCurrentTurn() == randTurn1) {

                new LocalItemGift(curGame.curPlayer).changeData();

                try {
                    new EventScreen().transition(1, primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (curGame.getCurrentTurn() == randTurn2) {
                new LocalSandstorm(curGame.curPlayer).changeData();

                try {
                    new EventScreen().transition(2, primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (curGame.getCurrentTurn() == randTurn3) {
                new LocalBrokenWagon(curGame.curPlayer).changeData();

                try {
                    new EventScreen().transition(3, primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if ( ((curGame.getCurrentTurn() == randTurn4) || (curGame.getCurrentTurn() == randTurn5)
            || (curGame.getCurrentTurn() == randTurn6)) && curGame.getCurrentTurn() > 10) {
                try {
                    new BanditEvent().transition(primaryStage, curGame);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new LocalBanditAttack(curGame.curPlayer).changeData();
            }


        });

        endTurn.setDefaultButton(true);

        HBox rightSide = new HBox();
        rightSide.getChildren().add(endTurn);
        HBox.setHgrow(rightSide, Priority.ALWAYS);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(leftSide, rightSide);
        topBar.setMinHeight(40);
        topBar.setAlignment(Pos.CENTER);

        //Right Bar
        VBox inventoryVBox = new VBox();
        inventoryVBox.setAlignment(Pos.CENTER);
        inventoryVBox.getChildren().addAll(avatar, new Label("Inventory"));

        ObservableMap<String, Integer> inventoryMap = FXCollections.observableHashMap();
        ObservableList<String> inventKeys = FXCollections.observableArrayList();

        inventoryMap.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                // no put for existing key
                if (removed) {
                    inventKeys.remove(change.getKey());
                } else {
                    inventKeys.add(change.getKey());
                }
            }
        });

        TableView inventoryItemsView = new TableView(inventKeys);
        inventoryItemsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<String, String> inventoryItem = new TableColumn<>("Item");
        inventoryItem.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue()));
        TableColumn<String, Integer> inventoryAmount = new TableColumn<>("Amount");
        inventoryAmount.setCellValueFactory(cd -> Bindings.valueAt(inventoryMap, cd.getValue()));
        inventoryMap.putAll(curGame.getInventory());
        inventoryItemsView.getColumns().setAll(inventoryItem, inventoryAmount);

        HBox buyControl = new HBox();
        TextField buyAmount = new TextField();
        buyAmount.setPromptText("Buy");
        Button buyButton = new Button("Buy");
        buyButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        buyControl.getChildren().addAll(buyAmount, buyButton);

        HBox sellControl = new HBox();
        TextField sellAmount = new TextField();
        sellAmount.setPromptText("Sell");
        Button sellButton = new Button("Sell");
        sellButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        sellControl.getChildren().addAll(sellAmount, sellButton);

        inventoryVBox.getChildren().addAll(inventoryItemsView, buyControl, sellControl);
        inventoryVBox.setPrefWidth(200);

        //Bottom Bar
        TabPane tabPane = new TabPane();
        Tab buy = new Tab("Buy");
        buy.setClosable(false);
        Tab sell = new Tab("Sell");
        sell.setClosable(false);
        Tab party = new Tab("Party");
        party.setClosable(false);

        City home = curGame.getCity(mapData.getHexData(playerMap.currentPosition).building.name);

        //Buy
        ObservableMap<String, Double> buyMap = FXCollections.observableHashMap();
        ObservableList<String> buyKeys = FXCollections.observableArrayList();

        buyMap.addListener((MapChangeListener.Change<? extends String, ? extends Double> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                // no put for existing key
                if (removed) {
                    buyKeys.remove(change.getKey());
                } else {
                    buyKeys.add(change.getKey());
                }
            }
        });

        TableView buyItems = new TableView(buyKeys);
        TableColumn<String, String> buyItem = new TableColumn<>("Item");
        buyItem.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue()));

        TableColumn<String, Double> buyPrice = new TableColumn<>("Price");
        buyPrice.setCellValueFactory(cd -> Bindings.valueAt(buyMap, cd.getValue()));

        buyMap.putAll(home.getBuyList());
        buyItems.getColumns().setAll(buyItem, buyPrice);
        buy.setContent(buyItems);

        buyItems.setPlaceholder(new Label("Not in City"));

        buyButton.setOnMouseClicked(event1 -> {
            if(buyItems.getSelectionModel().getSelectedItem() != null){
                String item = buyItems.getSelectionModel().getSelectedItem().toString();
                City city = curGame.getCity(mapData.getHexData(playerMap.currentPosition).building.name);
                Integer amount = Integer.parseInt(buyAmount.getText());
                System.out.println(curGame.getInventory());

                curGame.buyItems(item, city, amount);

                if(currentAction == Action.Move){
                    currentAction = Action.Other;
                    actionMap.resetMovable();
                }
                mapChange = true;
            }
        });

        //Sell
        ObservableMap<String, Double> sellMap = FXCollections.observableHashMap();
        ObservableList<String> sellKeys = FXCollections.observableArrayList();

        sellMap.addListener((MapChangeListener.Change<? extends String, ? extends Double> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                // no put for existing key
                if (removed) {
                    sellKeys.remove(change.getKey());
                } else {
                    sellKeys.add(change.getKey());
                }
            }
        });

        TableView sellItems = new TableView(sellKeys);
        TableColumn<String, String> sellItem = new TableColumn<>("Item");
        sellItem.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue()));

        TableColumn<String, Double> sellPrice = new TableColumn<>("Price");
        sellPrice.setCellValueFactory(cd -> Bindings.valueAt(sellMap, cd.getValue()));

        sellMap.putAll(home.getSellList());

        sellItems.getColumns().setAll(sellItem, sellPrice);
        sell.setContent(sellItems);

        sellItems.setPlaceholder(new Label("Not in City"));

        sellButton.setOnMouseClicked(event1 -> {
            if(sellItems.getSelectionModel().getSelectedItem() != null){
                String item = sellItems.getSelectionModel().getSelectedItem().toString();
                City city = curGame.getCity(mapData.getHexData(playerMap.currentPosition).building.name);
                Integer amount = Integer.parseInt(sellAmount.getText());
                System.out.println(curGame.getInventory());

                curGame.sellItems(item, city, amount);

                if(currentAction == Action.Move){
                    currentAction = Action.Other;
                    actionMap.resetMovable();
                }
                mapChange = true;
            }
        });

        tabPane.getTabs().addAll(buy, sell, party);
        tabPane.setPrefHeight(150);

        //Setting BorderPane
        borderPane.setTop(topBar);
        borderPane.setCenter(pane);
        borderPane.setRight(inventoryVBox);
        borderPane.setBottom(tabPane);

        //Adding BorderPane to a VBox Holder
        holder.getChildren().addAll(borderPane);
//        holder.getStylesheets().add("style.css");

        pane.setOnMouseClicked(event -> {
            Hexagon est = mapData.pixelToHex(new Point(event.getX(), event.getY()));

            if(mapData.getHexData(est).player != null){
                currentAction = Action.Move;
                actionMap.setMovable(est, curGame.getCurrentActionPoints());
                mapChange = true;
            }
            else {
                if (currentAction == Action.Move) {
                    if(actionMap.reacheable(est)){
                        curGame.takeAction(actionMap.movable.get(est));
                        playerMap.movePlayer(est);

                        if(mapData.getHexData(est).building != null){
                            City city = curGame.getCity(mapData.getHexData(est).building.name);
                            System.out.println(mapData.getHexData(est).building.name);
                            System.out.println(city);
                            System.out.println(city.getBuyList());
                            buyButton.setDisable(false);
                            sellButton.setDisable(false);
                            buyMap.putAll(city.getBuyList());
                            sellMap.putAll(city.getSellList());
                        }
                        else{
                            buyButton.setDisable(true);
                            sellButton.setDisable(true);
                            sellMap.clear();
                            buyMap.clear();
                        }
                    }
                    currentAction = Action.Other;
                    actionMap.resetMovable();
                    mapChange = true;
                }
            }
        });

        primaryStage.setScene(new Scene(holder));
        switch (curGame.curPlayer.getFaction().getName()){
            case "Rome":
                holder.getStylesheets().add("RomeGame.css");
                break;
            case "Parthian":
                holder.getStylesheets().add("ParthianGame.css");
                break;
            case "Han":
                holder.getStylesheets().add("HanGame.css");
                break;
            default:
                holder.getStylesheets().add("style.css");
                break;
        }
//        holder.getStylesheets().add("style.css");
        primaryStage.show();


        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(mapChange){
                    basicGC.clearRect(0,0, basicCanvas.getWidth(), basicCanvas.getHeight());
                    actionGC.clearRect(0,0, actionCanvas.getWidth(), actionCanvas.getHeight());
                    politicalGC.clearRect(0,0, politicalCanvas.getWidth(), politicalCanvas.getHeight());

                    for(Map map : mapOrder){
                        if(map.selected){
                            map.drawMap();
                        }
                    }

                    inventoryMap.clear();
                    inventoryMap.putAll(curGame.getInventory());
                    actionPoints.set(curGame.getCurrentActionPoints());
                    wallet.set(curGame.getWallet());
                    mapChange = false;
                }
            }
        }.start();

    }
}
