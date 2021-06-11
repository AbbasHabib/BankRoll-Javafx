package Game;
import Game.Interfaces.IOwnable;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.List;

public class CountryBlock extends GamingBlock implements IOwnable
{
    private double purchasePrice;
    private String CountryName;
    private List<CountryBlock> neighbours;
    private Player Owner;
    private double rentalPrice;

    /// functions implementations///
    @Override
    public boolean purchase(Player p)
    {
        if(p.buyProperty(this.purchasePrice, super.getBlockNum()))
        {
            System.out.println("got owned");
            this.Owner = p;

            Rectangle playerShadow = new Rectangle(30,30);
            playerShadow.setFill(Color.web(p.getColor()));
            playerShadow.setStrokeWidth(3);
            playerShadow.setStrokeMiterLimit(10);
            playerShadow.setStrokeType(StrokeType.CENTERED);
            playerShadow.setStroke(Color.web("#2e003e"));

            GridPane.setHalignment(playerShadow, HPos.CENTER);
            GridPane.setValignment(playerShadow, VPos.CENTER);
            GridPane.setConstraints(playerShadow, this.getRowIndex(), this.getColumnIndex());

            BankRoll.grid.getChildren().add(playerShadow);
            return true;
        }
        else
        {
            System.out.println("no money lol fokin peasent");
            return false;
        }
    }

    @Override
    public Player getOwner()
    {
        return Owner;
    }

    @Override
    public boolean hasOwner() {
        return Owner != null;
    }

    @Override
    public double getRentalPrice() {
        return purchasePrice * 0.2;
    }
    /// Constructors ///

    public CountryBlock(double price, String CountryName, int ColomnIndex, int RowIndex, Image BlockImage, int BlockNo)
    {
        super(ColomnIndex, RowIndex, BlockImage, BlockNo);
        this.purchasePrice = price;
        this.CountryName = CountryName;
    }

    public CountryBlock()
    {
        Owner = null;
    }
    /// Getters and Setters ///

    public double getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public String getCountryName()
    {
        return CountryName;
    }

    public void setCountryName(String countryName)
    {
        this.CountryName = countryName;
    }

    public void setOwner(Player owner)
    {
        Owner = owner;
    }

}
