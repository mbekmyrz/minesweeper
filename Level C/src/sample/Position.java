package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

class Position extends Button {
    private boolean isBomb;
    private int neighborBombs;
    private boolean isShown;
    private boolean isFlagged;
    private BackgroundImage bImage;

    private Image notClickedImage = new Image("blue.png");
    private Image emptyImage = new Image(("empty.png"));
    private Image num1Image = new Image("1.png");
    private Image num2Image = new Image("2.png");
    private Image num3Image = new Image("3.png");
    private Image num4Image = new Image("4.png");
    private Image num5Image = new Image("5.png");
    private Image num6Image = new Image("6.png");
    private Image num7Image = new Image("7.png");
    private Image num8Image = new Image("8.png");
    private Image bombImage = new Image("bomb.png");
    private Image flagImage = new Image("flag.png");
    private Image notBombImage = new Image("notbomb.png");
    private Image clickedBombImage = new Image("clkbomb.png");

    Position() throws IllegalArgumentException{
        this.isBomb=false;
        this.neighborBombs=0;
        this.isShown=false;
        this.isFlagged=false;
        this.setMinSize(30,30);
        this.setMaxSize(30,30);
        setBackImage(-1);
    }

    boolean isBomb() {
        return isBomb;
    }
    void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }
    boolean isShown() {
        return isShown;
    }
    void setShown(boolean isShown) {
        this.isShown = isShown;
    }
    void setNeighborBombs(int NeighborBombs) {
        this.neighborBombs=NeighborBombs;
    }
    int getNeighborBombs() {
        return neighborBombs;
    }
    boolean isFlagged() {
        return isFlagged;
    }
    void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
    void setBackImage(int index) {
        switch (index) {
            case -1:
                this.bImage = new BackgroundImage(notClickedImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 0:
                this.bImage = new BackgroundImage(emptyImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 1:
                this.bImage = new BackgroundImage(num1Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 2:
                this.bImage = new BackgroundImage(num2Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 3:
                this.bImage = new BackgroundImage(num3Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 4:
                this.bImage = new BackgroundImage(num4Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 5:
                this.bImage = new BackgroundImage(num5Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 6:
                this.bImage = new BackgroundImage(num6Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 7:
                this.bImage = new BackgroundImage(num7Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 8:
                this.bImage = new BackgroundImage(num8Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 9:
                this.bImage = new BackgroundImage(bombImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 10:
                this.bImage = new BackgroundImage(flagImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 11:
                this.bImage = new BackgroundImage(notBombImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
            case 12:
                this.bImage = new BackgroundImage(clickedBombImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(),
                        true, true, true, false));
                break;
        }
        this.setBackground(new Background(bImage));
    }
}
