package Game;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


abstract public class GamingBlock {
    static double WIDTH;
    static double HEIGHT;
    private int ColumnIndex;
    private int RowIndex;
    private Image BlockImage;
    private ImageView  BlockImageView ;
    private int blockNum;
    public static int totalBlocks = 0;

    /// Constructors //
    public GamingBlock()
    {
        WIDTH = BankRoll.SCREEN_WIDTH / 8;
        HEIGHT = BankRoll.SCREEN_HEIGHT / 6;
        totalBlocks++;
    }

    public GamingBlock(int ColumnIndex, int RowIndex, int BlockNo)
    {
        this();
        this.ColumnIndex = ColumnIndex;
        this.RowIndex = RowIndex;
        this.blockNum = BlockNo;
    }

    public GamingBlock( int ColumnIndex, int RowIndex, Image BlockImage, int BlockNo)
    {
        this();
        this.ColumnIndex = ColumnIndex;
        this.RowIndex = RowIndex;
        this.BlockImage = BlockImage;
        this.blockNum = BlockNo;

    }

    //// functions ////
    public Node SetBlockImage()
    {
        this.BlockImageView = new ImageView();
        this.BlockImageView.setFitHeight(HEIGHT);
        this.BlockImageView.setFitWidth(WIDTH);
        this.BlockImageView.setImage(this.BlockImage);
        return this.BlockImageView;
    }

    //// Getters and Setters ///

    public static int GetTotalBlocks()
    {
        return totalBlocks;
    }

    public int getBlockNum()
    {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }


    public Image getBlockImage() {
        return BlockImage;
    }

    public void setBlockImage(Image BlockImage) {
        this.BlockImage = BlockImage;
    }

    public ImageView getBlockImageView() {
        return BlockImageView;
    }

    public void setBlockImageView(ImageView BlockImageView) {
        this.BlockImageView = BlockImageView;
    }

    public int getColumnIndex() {
        return ColumnIndex;
    }

    public void setColumnIndex(int ColomnIndex) {
        this.ColumnIndex = ColomnIndex;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int RowIndex) {
        this.RowIndex = RowIndex;
    }
}
