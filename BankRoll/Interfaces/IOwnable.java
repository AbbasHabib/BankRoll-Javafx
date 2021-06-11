package Game.Interfaces;
import Game.Player;

public interface IOwnable
{
    boolean purchase(Player p);
    Player getOwner();
    boolean hasOwner();
    double getRentalPrice();
    double getPurchasePrice();
}
