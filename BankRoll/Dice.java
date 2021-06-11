package Game;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Dice
{

    private int top;
    private ImageView DiceImageView;

    public ImageView getDiceImageView() {
        return DiceImageView;
    }

    public void SetDieImage(int top,ImageView DiceImageView)
    {
        DiceImageView.setImage(new Image(getClass().getResourceAsStream("Dice/Dice" +top+".png")));
        this.DiceImageView = DiceImageView;
        GridPane.setHalignment(DiceImageView, HPos.CENTER);
        GridPane.setValignment(DiceImageView, VPos.BOTTOM);

        this.DiceImageView.setFitHeight(BankRoll.SCREEN_HEIGHT / 10);
        this.DiceImageView.setFitWidth(BankRoll.SCREEN_WIDTH / 16);
    }

    public void setDiceImageView(ImageView DiceImageView) {
        this.DiceImageView = DiceImageView;
    }


    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Dice() { }

    public Dice(int top)
    {
        this.top = top;
    }

    public int RollDice()
    {
        return this.top=1+(int)(Math.random()*6);
    }



}
