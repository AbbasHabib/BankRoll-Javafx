package Game;

import java.awt.*;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;

import com.sun.media.jfxmedia.events.PlayerEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class BankRoll extends Application
{
    private static final List<GamingBlock> GamingBlockList = new ArrayList <GamingBlock>();
    private final ImageView diceImageView = new ImageView();
    private static final List<Player> Players = new ArrayList<Player>();
    private Dice Dice = new Dice() ;


    public static Roller clock ;

    public static List<Player> getPlayers()
    {
        return Players;
    }
    public static double SCREEN_HEIGHT;
    public static double SCREEN_WIDTH;
    public static GridPane grid;
    public static final double WINING_NET_CASH = 2000;
    public static final Button Rollbutton = new Button("Roll");


    private static int JOINED_PLAYERS;
    public static int PLAYER_TURN;
    private static StartGameBlock startBlock = null;


    public static List<GamingBlock> getGamingBlocks()
    {
        return GamingBlockList;
    }

   public class Roller extends AnimationTimer
    {
        private static final int MAX = 20;
        private static final long FRAMES_PER_SEC = 50L;
        private static final long Interval =1000000000L/FRAMES_PER_SEC;
        private int count=0;
        private long last=0;

        @Override
        public void handle(long now) {

            if(now-last > Interval)
            {
                Dice= new Dice();
                Dice.SetDieImage(Dice.RollDice(),diceImageView);
                last = now;
                count++;
                Rollbutton.setDisable(true);
                if(count> MAX)
                {
                    clock.stop();
                    Rollbutton.setDisable(false);
                    Players.get(PLAYER_TURN).movePlayer(Dice.getTop(), GamingBlockList);
                    giveNextTurn();
                    count=0;
                }
            }
        }
    }

    private void giveNextTurn()
    {
        if(PLAYER_TURN + 1 >= JOINED_PLAYERS)
            PLAYER_TURN = 0;
        else
            PLAYER_TURN++;

    }

    private static void MaximizeInAnchorPane(Node toMaximize)
    {
        AnchorPane.setTopAnchor(toMaximize, 0.0);
        AnchorPane.setRightAnchor(toMaximize, 0.0);
        AnchorPane.setLeftAnchor(toMaximize, 0.0);
        AnchorPane.setBottomAnchor(toMaximize, 0.0);
    }

    private GridPane DrawBlocks()
    {
        GridPane grid = new GridPane();
        try
        {
            File file = new File("GameBlocks.bin");
            Scanner sc = new Scanner(file);
            if (!file.exists())
            {
                System.out.println("file cannot be read");
                return null;
            }
            while (sc.hasNext())
            {
                switch (sc.next())
                {
                    case "c" -> {
                        CountryBlock countryBlock;
                        AnchorPane pane = new AnchorPane();
                        MaximizeInAnchorPane(pane);
                        countryBlock = new CountryBlock(Double.parseDouble(sc.next()), sc.next(), Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), new Image(getClass().getResourceAsStream(sc.next())), Integer.parseInt(sc.next()));
                        pane.getChildren().add(countryBlock.SetBlockImage());
                        GridPane.setConstraints(pane, countryBlock.getRowIndex(), countryBlock.getColumnIndex());
                        grid.getChildren().add(pane);
                        GamingBlockList.add(countryBlock);
                    }
                    case "j" -> {
                        JailBlock jailblock;
                        AnchorPane pane = new AnchorPane();
                        MaximizeInAnchorPane(pane);


                        jailblock = new JailBlock(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), new Image(getClass().getResourceAsStream(sc.next())), Integer.parseInt(sc.next()));

                        pane.getChildren().add(jailblock.SetBlockImage());
                        GridPane.setConstraints(pane, jailblock.getRowIndex(), jailblock.getColumnIndex());

                        grid.getChildren().add(pane);
                        GamingBlockList.add(jailblock);
                    }
                    case "s" -> {
                        startBlock = new StartGameBlock( Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), new Image(getClass().getResourceAsStream(sc.next())), Integer.parseInt(sc.next()));
                        AnchorPane pane = new AnchorPane();
                        MaximizeInAnchorPane(pane);
                        pane.getChildren().add(startBlock.SetBlockImage());
                        GridPane.setConstraints(pane, startBlock.getRowIndex(), startBlock.getColumnIndex());
                        grid.getChildren().add(pane);
                        GamingBlockList.add(startBlock);
                    }
                }
            }
            sc.close();
            if(startBlock == null)
            {
                System.out.println("file wasnt read correctly");
                return null;
            }
            Rectangle frontalBg = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT, Color.web("#000000", 0.3));
            grid.add(frontalBg, 0,0, 8, 6);
            MaximizeInAnchorPane(frontalBg);



            Dice.SetDieImage(Dice.RollDice(), diceImageView);
            GridPane.setConstraints(Rollbutton, 3, 3);
            GridPane.setHalignment(Rollbutton, HPos.CENTER);
            GridPane.setValignment(Rollbutton, VPos.TOP);


            GridPane.setConstraints(Dice.getDiceImageView(), 3, 2);

            grid.getChildren().addAll(Rollbutton, Dice.getDiceImageView());


            // setting gridPane background color
            BackgroundFill bf = new BackgroundFill(Color.web("#283655"),
                    CornerRadii.EMPTY , Insets.EMPTY);
            Background bg = new Background(bf);
            grid.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            grid.setBackground(bg);

            return grid;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    private void drawPlayersData(GridPane gridpane)
    {
        AnchorPane pane = new AnchorPane();
        HBox players_container = new HBox();
        for(Player p : Players)
        {
            VBox P1Box = new VBox();
            BackgroundFill bf = new BackgroundFill(Color.web("#1e1f26"),
                    CornerRadii.EMPTY , Insets.EMPTY);

            Background bg = new Background(bf);
            P1Box.setBorder(new Border(new BorderStroke(Color.web("#1e1f26"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            P1Box.setBackground(bg);

            Label nameLabel = new Label("Player Name: " + p.getPlayerName() + "  -> ");
            Circle player = new Circle(19,Color.web(p.getColor()));
            HBox Name_player_icon = new HBox(nameLabel, player);


            Label walletLabel = new Label("Wallet: ");
            Label walletLabel2 = new Label("Wallet: ");
            HBox wallet_text = new HBox(walletLabel2, walletLabel);

            walletLabel.textProperty().bind(p.p_wallet);
            Label netCashLabel = new Label("NetCash:");
            Label netCashLabel2 = new Label("NetCash:");

            HBox netCash_text = new HBox(netCashLabel2, netCashLabel);


            netCashLabel.textProperty().bind(p.net_Cash);
            nameLabel.setFont(Font.font("verdana", 32));
            walletLabel.setFont(Font.font("Cambria", 32));
            netCashLabel.setFont(Font.font("Cambria", 32));
            netCashLabel2.setFont(Font.font("Cambria", 32));
            walletLabel2.setFont(Font.font("Cambria", 32));

            netCashLabel.setTextFill(Color.web("#ffffff", 1));
            nameLabel.setTextFill(Color.web("#ffffff", 1));
            walletLabel.setTextFill(Color.web("#ee4035", 1));
            netCashLabel.setTextFill(Color.web("#fdf498", 1));
            netCashLabel2.setTextFill(Color.web("#ffffff", 1));
            walletLabel2.setTextFill(Color.web("#ffffff", 1));
            P1Box.setMinHeight(SCREEN_HEIGHT / 6);
            P1Box.setMinWidth(SCREEN_WIDTH / 4);
            P1Box.getChildren().addAll(Name_player_icon, wallet_text, netCash_text);
            if(Players.size() == 2)
                players_container.setSpacing(SCREEN_WIDTH / 4);

            players_container.getChildren().add(P1Box);
        }

        pane.getChildren().add(players_container);

        MaximizeInAnchorPane(pane);

        gridpane.add(pane, 1, 4, 6, 1);

    }


    private void addButtonsLogic()
    {
           Rollbutton.setOnAction(e -> {

            clock.start();
            Rollbutton.setDisable(false);
        });
    }
    private void drawPlayers(GridPane g)
    {
        for(Player p : Players)
        {
            p.setPlayerPosition(startBlock.getBlockNum());
            GridPane.setConstraints(p.Draw_player(), startBlock.getRowIndex(), startBlock.getColumnIndex());
            g.getChildren().add(p.getPlayer_obj());
        }
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException
    {

        clock = new Roller();

        BorderPane root = new BorderPane();
        SCREEN_HEIGHT = 900;
        SCREEN_WIDTH = 1600;

        Players.add(new UserPlayer(0,"P1"));
        Players.add(new ComputerPlayer(1,"P2", "#ffaaa5"));

        JOINED_PLAYERS = Players.size();

        grid = DrawBlocks();
        drawPlayers(grid);


        if(grid == null)
        {
            System.out.println("Error drawing or reading the file");
            return;
        }
        root.setCenter(grid);


//        grid.setGridLinesVisible(true);
        drawPlayersData(grid);
        addButtonsLogic();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setTitle("antosh");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        try
        {
            launch(args);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

}
