package Game;

import Game.Interfaces.IOwnable;
import Game.Interfaces.IPayable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ComputerPlayer extends Player {


    public ComputerPlayer(int id) {
        super(id);
    }

    public ComputerPlayer(int id, String PlayerName, String color) {
        super(id, PlayerName, color);
    }

    public ComputerPlayer(int id, String PlayerName) {
        super(id, PlayerName);
    }

    @Override
    public void movePlayer( int top,List<GamingBlock> GamingBlockList) {

        if (isAllowPlayerToMove())
        {
            int go_to = getPlayerPosition() + top;
            if(top + getPlayerPosition() >= GamingBlock.totalBlocks - 1)
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


    @Override
    public void checkJailBlock(GamingBlock currentBlock) {
        if (currentBlock instanceof IPayable)
        {
            int random= 1+(int)(Math.random()*2);
            System.out.println("confirm");
            ButtonType buttonPay = new ButtonType("Pay");
            ButtonType buttonWait = new ButtonType("Wait");
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Wait 3 rounds or Pay 200", buttonPay, buttonWait);
            a.setContentText("do you want to wait 3 rounds or Pay 200? ");
            a.show();
            a.setOnCloseRequest(e->{
                ButtonType result = a.getResult();
                String resultText = result.getText();
                if (random==1)
                {
                    resultText.equals("Pay");
                    IPayable Block = (IPayable) currentBlock;
                    Block.PayTrialMoney(this);
                    setAllowPlayerToMove(true);

                }
                else if (random==2)
                {
                    resultText.equals("Wait");
                    setAllowPlayerToMove(false);
                }
            });
        }

    }


    @Override
    public void checkCurrentProperty(GamingBlock currentBlock) {
        if (currentBlock instanceof IOwnable) {
            IOwnable block = (IOwnable) currentBlock;
            if (block.hasOwner()) {
                if (block.getOwner().equals(this))
                {
                    System.out.println("u own this prop");
                } else {
                    this.WithdrawMoneyTo(block.getRentalPrice(), block.getOwner());
                }
            } else {
                int random = 1 + (int) (Math.random() * 2);
                System.out.println("confirm");
                if (random == 1)
                {
                    System.out.println("yes");
                    if (!block.purchase(this))
                        System.out.println("no enough money boii");
                } else if (random == 2)
                {
                    System.out.println("nooo");
                }
            }
        }
    }

}

