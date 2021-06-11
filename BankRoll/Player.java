package Game;

import java.util.ArrayList;
import java.util.List;

import Game.Interfaces.IOwnable;
import Game.Interfaces.IPayable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.control.*;
import javafx.scene.shape.StrokeType;
import javafx.scene.control.Alert.AlertType;

public abstract class  Player
{
    private double wallet;
    private String color;
    private String PlayerName;
    private Circle Player_obj;
    private int PlayerPosition; //player square position
    private List<Integer> OwnedProperty = new ArrayList <Integer>();
    private boolean PlayerTurn;
    private double netCash;
    private int _steps_counter;
    private int _id;
    private boolean allowPlayerToMove;



    private int countJailRounds=3;

    public StringProperty p_wallet;
    public StringProperty net_Cash;

    public Player(int id)
    {
        this._id = id;
        this._steps_counter = 0;
        this.wallet = wallet = 1000;
        this.netCash = this.wallet;
        p_wallet = new SimpleStringProperty(Double.toString(this.wallet));
        net_Cash = new SimpleStringProperty(Double.toString(this.wallet));
        setAllowPlayerToMove(true);
    }

    public Player(int id,String PlayerName, String color)
    {
        this(id);
        this.color = color;
        this.PlayerName = PlayerName;

    }
    public Player(int id, String PlayerName)
    {
        this(id);
        this.color = "#fdfdff";
        this.PlayerName = PlayerName;
    }

    public abstract void movePlayer(int top,List<GamingBlock> GamingBlockList);

    public abstract void checkJailBlock(GamingBlock currentBlock) ;

    public void checkStartPoint(int diceTop)
    {
        if(_steps_counter + diceTop >= BankRoll.getGamingBlocks().size())
        {
            _steps_counter += diceTop;
            _steps_counter -= (BankRoll.getGamingBlocks().size() - 1);

            this.depositMoney(300);
        }
        else
            _steps_counter += diceTop;
    }

    public abstract void checkCurrentProperty(GamingBlock currentBlock);


    public void depositMoney(double DepositMoney)
    {
        wallet += DepositMoney;
        p_wallet.setValue(Double.toString(wallet));
        this.netCash += DepositMoney;
        net_Cash.setValue(Double.toString(this.netCash));
        checkNetCash();
    }

    public void checkNetCash()
    {
        if(this.netCash >= BankRoll.WINING_NET_CASH)
        {
            ButtonType buttonYes = new ButtonType("Ok");
            Alert a = new Alert(AlertType.NONE, "ok", buttonYes);
            a.setContentText(this.getPlayerName() +" Won !"+"\n" + "Press ok to restart");
            a.show();
            a.setOnCloseRequest(e->{
                ButtonType result = a.getResult();
                String resultText = result.getText();
                //resetgame(0);
            });
        }
    }

    public boolean buyProperty(double WithdrawMoney, int countryNum)
    {
        if(WithdrawMoney(WithdrawMoney))
        {
            this.OwnedProperty.add(countryNum);
            return true;
        }
        else
            return false;
    }

    public boolean WithdrawMoney(double Withdraw_money)
    {
        if(this.wallet < Withdraw_money) //player went bankrupt
        {
            System.out.println(this.PlayerName+" went bankrupt");
            return false;
        }
        else
        {
            this.wallet -= Withdraw_money;
            p_wallet.setValue(Double.toString(wallet));
            return true;
        }
    }

    public void WithdrawMoneyTo(double Withdraw_money,Player SendToPlayer)
    {
        if(WithdrawMoney(Withdraw_money))
        {
            SendToPlayer.depositMoney(Withdraw_money);
        }
        else
        {
            System.out.println(this.PlayerName+" u r broke lol hehe");
        }
    }



    public Circle Draw_player()
    {
        Circle mCircle = new Circle(0,0,22);
        mCircle.setFill(Color.web(this.color));
        mCircle.setStrokeWidth(3);
        mCircle.setStrokeMiterLimit(10);
        mCircle.setStrokeType(StrokeType.CENTERED);
        mCircle.setStroke(Color.web("#2e003e"));
        if(this._id == 0)
        {
            GridPane.setHalignment(mCircle, HPos.CENTER);
            GridPane.setValignment(mCircle, VPos.CENTER);
        }
        else if(this._id == 1)
        {
            GridPane.setHalignment(mCircle, HPos.CENTER);
            GridPane.setValignment(mCircle, VPos.TOP);
        }
        else if(this._id == 2)
        {
            GridPane.setHalignment(mCircle, HPos.CENTER);
            GridPane.setValignment(mCircle, VPos.BOTTOM);
        }
        this.Player_obj= mCircle;
        return Player_obj;
    }

    public int getPlayerPosition() {
        return PlayerPosition;
    }

    public void setPlayerPosition(int PlayerPosition) {
        this.PlayerPosition = PlayerPosition;
    }

    public boolean isPlayerTurn() {
        return PlayerTurn;
    }

    public void setPlayerTurn(boolean PlayerTurn) {
        this.PlayerTurn = PlayerTurn;
    }

    public List<Integer> getOwnedProperty() {
        return OwnedProperty;
    }

    public void setOwnedProperty(List<Integer> OwnedProperty) {
        this.OwnedProperty = OwnedProperty;
    }

    public Circle getPlayer_obj() {
        return Player_obj;
    }

    public void setPlayer_obj(Circle Player_obj) {
        this.Player_obj = Player_obj;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getColor()
    {
        return this.color;
    }

    public int get_id() {
        return _id;
    }

    public boolean isAllowPlayerToMove() {
        return allowPlayerToMove;
    }

    public void setAllowPlayerToMove(boolean allowPlayerToMove) {
        this.allowPlayerToMove = allowPlayerToMove;
    }

    public int getCountJailRounds() {
        return countJailRounds;
    }

    public void setCountJailRounds(int countJailRounds) {
        this.countJailRounds = countJailRounds;
    }
}
