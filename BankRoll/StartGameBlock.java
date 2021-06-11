package Game;
import javafx.scene.image.Image;


public class StartGameBlock extends GamingBlock{

   private int StartBlockMoney = 200;
   private static int NumOfRounds=0;
   

    public StartGameBlock() {
    }

    public StartGameBlock(int ColomnIndex, int RowIndex, Image BlockImage, int BlockNo) {
        super(ColomnIndex, RowIndex, BlockImage, BlockNo);
        NumOfRounds++;
    }
    
    public void DepositMoney(Player Player)
    {
        Player.depositMoney(StartBlockMoney);
    }
    public static int GetNumOfRounds()
    {
        return NumOfRounds;
    }
}
