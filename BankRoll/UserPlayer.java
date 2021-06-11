package Game;

import Game.Interfaces.IOwnable;
import Game.Interfaces.IPayable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import java.applet.*;
import java.net.*;

import java.util.List;

import static Game.BankRoll.Rollbutton;

public class UserPlayer extends Player{

    public UserPlayer(int id) {
        super(id);
    }

    public UserPlayer(int id, String PlayerName, String color) {

        super(id, PlayerName, color);
    }

    public UserPlayer(int id, String PlayerName) {
        super(id, PlayerName);
    }

    @Override
    public void movePlayer( int top,List<GamingBlock> GamingBlockList)
    {
        if (isAllowPlayerToMove())
        {
            int go_to = getPlayerPosition() + top;
            if(top + getPlayerPosition()>= GamingBlock.totalBlocks - 1)
            {
                go_to = (GamingBlock.totalBlocks - 1)- getPlayerPosition();
                go_to = top - go_to;
            }
            for (GamingBlock x : GamingBlockList)
            {
                if(x.getBlockNum() == go_to)
                {
                    setPlayerPosition(go_to);
                    GridPane.setConstraints(this.getPlayer_obj(),x.getRowIndex(), x.getColumnIndex());
                    checkStartPoint(top);
                    checkCurrentProperty(x);
                    checkJailBlock(x);
                    break;
                }
            }
        }
        else
        {
            if (getCountJailRounds()==1)
            {
                setAllowPlayerToMove(true);
            }
            else
            {
                ButtonType buttonOk = new ButtonType("Ok");
                Alert a = new Alert(Alert.AlertType.NONE, "ok", buttonOk);
                a.setContentText("you still have "+getCountJailRounds()+" rounds");
                a.show();
                a.setOnCloseRequest(e->{
                    ButtonType result = a.getResult();
                    String resultText = result.getText();

                });

                setCountJailRounds((getCountJailRounds())-1);
            }
        }

    }

    public void checkCurrentProperty(GamingBlock currentBlock)
    {

        if (currentBlock instanceof IOwnable)
        {
            IOwnable block = (IOwnable) currentBlock;

            if (block.hasOwner())
            {
                if (block.getOwner().equals(this)) {
                    System.out.println("u own this prop");
                } else {
                    this.WithdrawMoneyTo(block.getRentalPrice(), block.getOwner());
                }

                if(BankRoll.getPlayers().get(BankRoll.PLAYER_TURN) instanceof ComputerPlayer)
                {
                    BankRoll.clock.start();
                    Rollbutton.setDisable(false);
                }
            }
            else
            {
                System.out.println("confirm");
                ButtonType buttonYes = new ButtonType("Yes");
                ButtonType buttonNo = new ButtonType("No");
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "do u want to buy?", buttonYes, buttonNo);
                a.setContentText("do you want to buy "+ ((CountryBlock)block).getCountryName()+ " for " + block.getPurchasePrice() + "$  ?");
                a.show();
                a.setOnCloseRequest(e->{
                    ButtonType result = a.getResult();
                    String resultText = result.getText();
                    if (resultText.equals("Yes"))
                    {
                        System.out.println("yes");
                        if(!block.purchase(this))
                            System.out.println("no enough money boii");
                    }
                    else
                    {
                        System.out.println("nooo");
                    }
                    if(BankRoll.getPlayers().get(BankRoll.PLAYER_TURN) instanceof ComputerPlayer)
                    {
                        BankRoll.clock.start();
                        Rollbutton.setDisable(false);
                    }
                });
            }
        }
    }

    public void checkJailBlock(GamingBlock currentBlock) {
        if (currentBlock instanceof IPayable)
        {

            System.out.println("confirm");
            ButtonType buttonPay = new ButtonType("Pay");
            ButtonType buttonWait = new ButtonType("Wait");
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Wait 3 rounds or Pay 200", buttonPay, buttonWait);
            a.setContentText("do you want to wait 3 rounds or Pay 200? ");
            a.show();
            a.setOnCloseRequest(e->{
                ButtonType result = a.getResult();
                String resultText = result.getText();
                if (resultText.equals("Pay"))
                {
                    IPayable Block = (IPayable) currentBlock;
                    Block.PayTrialMoney(this);
                    setAllowPlayerToMove(true);

                }
                else
                {

                    setAllowPlayerToMove(false);
                }
            });
        }

    }
}
