package Game;

import Game.Interfaces.IPayable;
import javafx.scene.image.Image;

public class JailBlock extends GamingBlock  implements IPayable {

    public final int trialMoney=200;
    
    public JailBlock() {}

    public JailBlock(int ColomnIndex, int RowIndex, Image BlockImage, int BlockNo) {
        super(ColomnIndex, RowIndex, BlockImage, BlockNo);
    }

    @Override
    public boolean PayTrialMoney(Player player) {
        return player.WithdrawMoney(trialMoney);
    }

}
