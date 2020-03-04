package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;

import javafx.geometry.Insets;

public class MineSweeperMain extends Application {
    private static BorderPane root;
    private static int rows = 9, columns = 9;
    private static Grid game;
    private static boolean isMultiGame, restart, pressedF;
    private static Label playerAScore, playerBScore, bombsCounter, playerTurn;

    private static void renewLabels() {
        String a = "-fx-background-color: rgb(233,113,100); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";
        String b = "-fx-background-color: rgb(233,213,100); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";
        playerAScore.setText("" + game.getScoreA());
        bombsCounter.setText("" + game.getLeftBombs());
        if (isMultiGame) {
            playerBScore.setText("" + game.getScoreB());
            if (game.isTurn()) {
                playerTurn.setText("It is " + game.getPlayerB() + "'s turn");
                playerTurn.setStyle(b);
            } else {
                playerTurn.setText("It is " + game.getPlayerA() + "'s turn");
                playerTurn.setStyle(a);
            }
        } else {
            playerBScore.setText("" + game.getMoves());
        }
    }

    private BorderPane gameLayout(String playerA, String playerB, int level) {
        if (isMultiGame)
            game = new Grid(rows, columns, playerA, playerB, level);
        else
            game = new Grid(rows, columns, playerA, level);

        restart = true;
        BorderPane layout = new BorderPane();

        //game board
        GridPane boardLayout = new GridPane();
        boardLayout.setStyle("-fx-background-color: #32363b;");
        boardLayout.setPadding(new Insets(10));
        boardLayout.setHgap(3);
        boardLayout.setVgap(3);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardLayout.add(game.board[i][j], j, i);
                game.board[i][j].setOnMouseClicked(buttonClick);
                game.board[i][j].setOnMouseEntered(mouseEntered);
            }
        }
        boardLayout.setAlignment(Pos.CENTER);

        //score board
        GridPane topPane = new GridPane();
        topPane.setMinHeight(80);
        topPane.setStyle("-fx-background-color: rgb(40,43,47);");
        String rightStyle = "-fx-background-color: rgb(230,230,230); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";

        Label playerALabel = new Label(game.getPlayerA());
        playerALabel.setStyle("-fx-background-color: rgb(233,113,100); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n");
        playerALabel.setMinSize(200,40);
        playerALabel.setMaxSize(200,40);
        playerALabel.setAlignment(Pos.CENTER);

        Label playerBLabel = new Label(game.getPlayerB());
        playerBLabel.setStyle("-fx-background-color: rgb(233,213,100); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n");
        playerBLabel.setMinSize(200,40);
        playerBLabel.setMaxSize(200,40);
        playerBLabel.setAlignment(Pos.CENTER);

        Label movesLabel = new Label("Moves");
        movesLabel.setStyle("-fx-background-color: rgb(233,213,100); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 10 15 10 15; -fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n");
        movesLabel.setMinSize(200,40);
        movesLabel.setMaxSize(200,40);
        movesLabel.setAlignment(Pos.CENTER);

        Image bombsImage = new Image("leftbomb.png");
        ImageView bombImageView = new ImageView(bombsImage);
        Label bombsLabel = new Label("", bombImageView);
        bombsLabel.setStyle("-fx-background-color: rgb(40,43,47);");
        bombsLabel.setMinSize(200,40);
        bombsLabel.setMaxSize(200,40);
        bombsLabel.setAlignment(Pos.CENTER);

        playerAScore = new Label("" + game.getScoreA());
        playerAScore.setStyle(rightStyle);
        playerAScore.setMinSize(200,40);
        playerAScore.setMaxSize(200,40);
        playerAScore.setAlignment(Pos.CENTER);

        if (isMultiGame)
            playerBScore = new Label("" + game.getScoreB());
        else
            playerBScore = new Label(String.valueOf(game.getMoves()));

        playerBScore.setStyle(rightStyle);
        playerBScore.setMinSize(200,40);
        playerBScore.setMaxSize(200,40);
        playerBScore.setAlignment(Pos.CENTER);

        bombsCounter = new Label("" + game.getLeftBombs());
        bombsCounter.setStyle(rightStyle);
        bombsCounter.setMinSize(200,40);
        bombsCounter.setMaxSize(200,40);
        bombsCounter.setAlignment(Pos.CENTER);

        topPane.add(playerALabel, 0, 0);
        topPane.add(bombsLabel, 1, 0);
        if (isMultiGame)
            topPane.add(playerBLabel, 2, 0);
        else
            topPane.add(movesLabel, 2, 0);
        topPane.add(playerAScore, 0, 1);
        topPane.add(bombsCounter, 1, 1);
        topPane.add(playerBScore, 2, 1);

        topPane.setPadding(new Insets(10));
        topPane.setHgap(50);
        topPane.setVgap(5);
        topPane.setAlignment(Pos.CENTER);

        //Player turn board
        GridPane btmPane = new GridPane();
        btmPane.setStyle("-fx-background-color: rgb(40,43,47);");
        playerTurn = new Label("It is " + game.getPlayerA() + "'s turn");
        playerTurn.setStyle(playerALabel.getStyle());
        playerTurn.setMinSize(300,40);
        playerTurn.setMaxSize(300,40);
        playerTurn.setAlignment(Pos.CENTER);
        btmPane.getChildren().addAll(playerTurn);
        btmPane.setAlignment(Pos.CENTER);

        layout.setTop(topPane);
        layout.setCenter(boardLayout);
        if (isMultiGame)
            layout.setBottom(btmPane);
        return layout;
    }
    private BorderPane aboutLayout() {
        restart = false;
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #32363b;-fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;");
        TitledPane titledPane1 = new TitledPane();
        titledPane1.setText("Authors");
        Label label1 = new Label();
        label1.setText("\u26AB" + " There are two guys who made this game.\n" +
                "\u26AB" + " Yassawe Kainolda made the game board working.\n" +
                "\u26AB" + " Marat Bekmyrza gave it graphics.\n" +
                "\u26AB" + " Nazarbayev University, Astana, 2019.\n");
        titledPane1.setContent(label1);
        TitledPane titledPane2 = new TitledPane();
        titledPane2.setText("Game");
        Label label2 = new Label();
        label2.setText("\u26AB" + " Minesweeper.\n" +
                "\u26AB" + " It is an old Microsoft Game.\n" +
                "\u26AB" + " Now it has a feature of multi player.\n" +
                "\u26AB" + " Thank you for playing!\n");
        titledPane2.setContent(label2);
        Accordion accordion = new Accordion(titledPane1,titledPane2);
        layout.setCenter(accordion);

        return layout;
    }
    private BorderPane rulesLayout() {
        restart = false;
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #32363b;-fx-font-family: \"Helvetica\"; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #32363b;");

        TitledPane titledPane1 = new TitledPane();
        titledPane1.setText("Quick Start:");
        Label label1 = new Label();
        label1.setText("\u26AB" + " You are presented with a board of squares.\n" +
                "\u26AB" + " Some squares contain mines (bombs), others don't. If you click on a square containing a bomb, you lose.\n" +
                "\u26AB" + " If you manage to click all the squares (without clicking on any bombs) you win.\n" +
                "\u26AB" + " Clicking a square which doesn't have a bomb reveals the number of neighbouring squares containing bombs.\n" +
                "\u26AB" + " To open a square, point at the square and click on it. To mark a square you think is a bomb, point and right-click.\n" +
                "\u26AB" + " To mark a square you think is a bomb, point and right-click.\n" +
                "\u26AB" + " Or press \"F\" and enter the mouse to the target square.\n");
        titledPane1.setContent(label1);
        TitledPane titledPane2 = new TitledPane();
        titledPane2.setText("Detailed Instructions:");
        Label label2 = new Label();
        label2.setText("\u26AB" + " A squares \"neighbours\" are the squares adjacent above, below, left, right, and all 4 diagonals.\n" +
                "\u26AB" + " If you open a square with 0 neighboring bombs, all its neighbors will automatically open.\n" +
                "\u26AB" + " To remove a bomb marker from a square, point at it and right-click again.\n" +
                "\u26AB" + " You don't have to mark all the bombs to win; you just need to open all non-bomb squares.\n");
        titledPane2.setContent(label2);
        TitledPane titledPane3 = new TitledPane();
        titledPane3.setText("Status Information:");
        Label label3 = new Label();
        label3.setText("\u26AB" + " Game score is presented at the top of the screen under player's name.\n" +
                "\u26AB" + " The number of left bombs is also shown at the top and decreases as player adds marker.\n" +
                "\u26AB" + " For the two player mode, the player's turn is shown at the bottom.\n" +
                "\u26AB" + " For the one player mode, the number of moves is shown at the top.\n");
        titledPane3.setContent(label3);
        TitledPane titledPane4 = new TitledPane();
        titledPane4.setText("Source:");
        Label label4 = new Label();
        label4.setText("\u26AB" + " http://www.freeminesweeper.org/help/minehelpinstructions.html\n");
        titledPane4.setContent(label4);
        Accordion accordion = new Accordion(titledPane1,titledPane2,titledPane3, titledPane4);
        layout.setCenter(accordion);
        return layout;
    }
    private VBox gameSetupLayout() {
        restart = false;
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #32363b;");
        String leftStyle = "-fx-background-color: rgb(113,169,212); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 15 20 15 20; -fx-font-family: \"Helvetica\"; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";
        String rightStyle = "-fx-background-color: rgb(230,230,230); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 15 20 15 20; -fx-font-family: \"Helvetica\"; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";

        //1
        Image logo = new Image("logo.png");
        ImageView logoView = new ImageView(logo);

        //2
        GridPane menuLayout = new GridPane();
        Label boardRows = new Label("Board Rows:");
        boardRows.setStyle(leftStyle);
        boardRows.setMinWidth(200);
        Label boardColumns = new Label("Board Columns:");
        boardColumns.setStyle(leftStyle);
        boardColumns.setMinWidth(200);
        Label rowNumber = new Label("" + rows);
        rowNumber.setStyle(rightStyle);
        Label columnNumber = new Label("" + columns);
        columnNumber.setStyle(rightStyle);
        Label rowMinus = new Label("-");
        rowMinus.setStyle(rightStyle);
        rowMinus.setOnMouseClicked(e -> {
            if (rows > 3)
                rows--;
            rowNumber.setText(String.valueOf(rows));
        });
        Label rowPlus = new Label("+");
        rowPlus.setStyle(rightStyle);
        rowPlus.setOnMouseClicked(e -> {
            if (rows < 20)
                rows++;
            rowNumber.setText(String.valueOf(rows));
        });
        Label columnMinus = new Label("-");
        columnMinus.setStyle(rightStyle);
        columnMinus.setOnMouseClicked(e -> {
            if (columns > 3)
                columns--;
            columnNumber.setText(String.valueOf(columns));
        });
        Label columnPlus = new Label("+");
        columnPlus.setStyle(rightStyle);
        columnPlus.setOnMouseClicked(e -> {
            if (columns < 20)
                columns++;
            columnNumber.setText(String.valueOf(columns));
        });

        //levels
        Label level = new Label("Game Level:");
        level.setStyle(leftStyle);
        level.setMinWidth(200);
        ToggleButton easy = new ToggleButton("Easy");
        easy.setStyle(leftStyle);
        ToggleButton moderate = new ToggleButton("Moderate");
        moderate.setStyle(rightStyle);
        ToggleButton hard = new ToggleButton("Hard");
        hard.setStyle(rightStyle);
        easy.setSelected(true);
        easy.setOnMouseClicked(e -> {
            easy.setSelected(true);
            easy.setStyle(leftStyle);
            moderate.setSelected(false);
            moderate.setStyle(rightStyle);
            hard.setSelected(false);
            hard.setStyle(rightStyle);
        });
        moderate.setOnMouseClicked(e -> {
            easy.setSelected(false);
            easy.setStyle(rightStyle);
            moderate.setSelected(true);
            moderate.setStyle(leftStyle);
            hard.setSelected(false);
            hard.setStyle(rightStyle);
        });
        hard.setOnMouseClicked(e -> {
            easy.setSelected(false);
            easy.setStyle(rightStyle);
            moderate.setSelected(false);
            moderate.setStyle(rightStyle);
            hard.setSelected(true);
            hard.setStyle(leftStyle);
        });

        //Player names
        Label playerALabel = new Label("Player Name:"); //single player
        if (isMultiGame)
            playerALabel = new Label("Player 1 Name:"); //multiplayer
        playerALabel.setStyle(leftStyle);
        playerALabel.setMinWidth(200);
        TextField playerANameField = new TextField("...");
        playerANameField.setStyle(rightStyle);
        Label playerBLabel = new Label("Player 2 Name:");
        playerBLabel.setStyle(leftStyle);
        playerBLabel.setMinWidth(200);
        TextField playerBNameField = new TextField("...");
        playerBNameField.setStyle(rightStyle);

        HBox forBoardRows = new HBox();
        forBoardRows.getChildren().addAll(rowMinus,rowNumber,rowPlus);
        forBoardRows.setAlignment(Pos.CENTER_LEFT);
        forBoardRows.setPadding(new Insets(10));
        forBoardRows.setSpacing(5);

        HBox forBoardColumns = new HBox();
        forBoardColumns.getChildren().addAll(columnMinus,columnNumber,columnPlus);
        forBoardColumns.setAlignment(Pos.CENTER_LEFT);
        forBoardColumns.setPadding(new Insets(10));
        forBoardColumns.setSpacing(5);

        HBox forLevel = new HBox();
        forLevel.getChildren().addAll(easy,moderate,hard);
        forLevel.setAlignment(Pos.CENTER_LEFT);
        forLevel.setPadding(new Insets(10));
        forLevel.setSpacing(5);

        HBox forFieldA = new HBox();
        forFieldA.getChildren().addAll(playerANameField);
        forFieldA.setAlignment(Pos.CENTER_LEFT);
        forFieldA.setPadding(new Insets(10));
        forFieldA.setSpacing(5);

        HBox forFieldB = new HBox();
        forFieldB.getChildren().addAll(playerBNameField);
        forFieldB.setAlignment(Pos.CENTER_LEFT);
        forFieldB.setPadding(new Insets(10));
        forFieldB.setSpacing(5);

        menuLayout.add(boardRows, 0, 0);
        menuLayout.add(forBoardRows, 1, 0);
        menuLayout.add(boardColumns, 0, 1);
        menuLayout.add(forBoardColumns, 1, 1);
        menuLayout.add(level, 0,2);
        menuLayout.add(forLevel,1,2);
        menuLayout.add(playerALabel,0, 3);
        menuLayout.add(forFieldA,1, 3);
        if (isMultiGame) {
            menuLayout.add(playerBLabel,0, 4);
            menuLayout.add(forFieldB,1, 4);
        }
        menuLayout.setPadding(new Insets(5));
        menuLayout.setHgap(5);
        menuLayout.setVgap(5);
        menuLayout.setAlignment(Pos.CENTER);

        //3
        Button start = new Button("Start");
        start.setMinWidth(210);
        start.setStyle(leftStyle);
        start.setOnMouseClicked(e -> {
            String playerAName = playerANameField.getText();
            String playerBName = playerBNameField.getText();
            try {
                if (easy.isSelected())
                    root.setCenter(gameLayout(playerAName, playerBName, 1));
                else if (moderate.isSelected())
                    root.setCenter(gameLayout(playerAName, playerBName, 2));
                else
                    root.setCenter(gameLayout(playerAName, playerBName, 3));
            } catch (Exception GridWasNotInitialised) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error message");
                alert.setHeaderText("Error was found during the initialisation of the game board.");
                alert.setContentText("The error might be due to missing images in the game directory.");
                alert.show();
            }
        });

        VBox.setMargin(logoView, new Insets(20, 20, 20, 20));
        VBox.setMargin(menuLayout, new Insets(20, 20, 20, 20));
        VBox.setMargin(start, new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(logoView, menuLayout, start);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
    private VBox welcomeLayout() {
        restart = false;
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #32363b;;");
        String style = "-fx-background-color: rgb(113,169,212); -fx-background-insets: 0,1,2,3,0; -fx-background-radius: 9,8,5,4,3;\n" +
                "-fx-padding: 15 20 15 20; -fx-font-family: \"Helvetica\"; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #32363b;\n";
        Image logo = new Image("logo.png");
        ImageView logoView = new ImageView(logo);
        Button newSingle = new Button("Single Player Game");
        newSingle.setStyle(style);
        newSingle.setMinWidth(210);
        newSingle.setOnMouseClicked(e -> {
            isMultiGame = false;
            root.setCenter(gameSetupLayout());
        });
        Button newMulti = new Button("Two Player Game");
        newMulti.setOnMouseClicked(e -> {
            isMultiGame = true;
            root.setCenter(gameSetupLayout());
        });
        newMulti.setStyle(style);
        newMulti.setMinWidth(210);
        Button howTo = new Button("How to Play?");
        howTo.setOnMouseClicked(e -> root.setCenter(rulesLayout()));
        howTo.setStyle(style);
        howTo.setMinWidth(210);
        VBox.setMargin(logoView, new Insets(10, 10, 10, 10));
        VBox.setMargin(newSingle, new Insets(10, 10, 10, 10));
        VBox.setMargin(newMulti, new Insets(10, 10, 10, 10));
        VBox.setMargin(howTo, new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(logoView,newSingle, newMulti, howTo);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    private final EventHandler<MouseEvent> buttonClick = event -> {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (event.getSource() == game.board[i][j] && !game.isFinished() && !game.board[i][j].isShown()) {
                    if (event.getButton() == MouseButton.SECONDARY)
                        flag(i, j);
                    else if (event.getButton() == MouseButton.PRIMARY && !game.board[i][j].isFlagged())
                        open(i, j);
                    break;
                }
            }
        }
    };
    private final EventHandler<MouseEvent> mouseEntered = event -> {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (event.getSource() == game.board[i][j] && !game.isFinished() && pressedF && !game.board[i][j].isShown()) {
                    flag(i,j);
                    break;
                }
            }
        }
    };

    private static void open(int r, int c) {
        game.board[r][c].setShown(true);
        game.setMoves(game.getMoves()+1);
        if(game.board[r][c].isBomb()) {
            game.reveal();
            game.board[r][c].setBackImage(12);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BOOOOMB!");
            if (isMultiGame) {
                if(game.isTurn()) {
                    //player B lost
                    alert.setHeaderText(game.getPlayerA() + " won this game!");
                    alert.setContentText(game.getPlayerB() + " was bombed!");
                } else {
                    //player A lost
                    alert.setHeaderText(game.getPlayerB() + " won this game!");
                    alert.setContentText(game.getPlayerA() + " was bombed!");
                }
            } else {
                alert.setHeaderText("You lost this game!");
                alert.setContentText("Better luck next time!");
            }
            alert.show();
        } else {
            game.check(r, c);
            if (isMultiGame) game.setTurn(!game.isTurn());
            if(game.isWon()) {
                game.reveal();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                if (isMultiGame) {
                    if(game.getScoreA()>game.getScoreB()) {
                        //player A won
                        alert.setHeaderText(game.getPlayerA() + " won this game!");
                        alert.setContentText(game.getPlayerA() + " had scored " + game.getScoreA() + "\n" +
                                game.getPlayerB() + " had score " + game.getScoreB());
                    } else if(game.getScoreA()<game.getScoreB()) {
                        //player B won
                        alert.setHeaderText(game.getPlayerB() + " won this game!");
                        alert.setContentText(game.getPlayerA() + " had scored " + game.getScoreA() + "\n" +
                                game.getPlayerB() + " had score " + game.getScoreB());
                    } else {
                        //stalemate
                        alert.setHeaderText("Friendship wins this game!");
                    }
                } else {
                    alert.setHeaderText("You won this game!");
                    alert.setContentText("You made " + game.getMoves() + " moves.");
                }
                alert.show();
            }
            renewLabels();
        }
        game.show();
    }
    private static void flag(int r, int c) {
        if (game.board[r][c].isFlagged()) {
            game.board[r][c].setFlagged(false);
            game.board[r][c].setBackImage(-1); //not clicked image
            game.setLeftBombs(game.getLeftBombs()+1);
        } else if (game.getLeftBombs() > 0) {
            game.board[r][c].setFlagged(true);
            game.board[r][c].setBackImage(10); //flag image
            game.setLeftBombs(game.getLeftBombs()-1);
        }
        renewLabels();
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("MineSweeper Game");
        window.setMinHeight(400);
        window.setMinWidth(400);
        window.setResizable(false);

        root = new BorderPane();
        root.setCenter(welcomeLayout());

        MenuBar menuBar = new MenuBar();
        Menu gameMenu =  new Menu("_Game");
        Menu helpMenu = new Menu("_Help");
        MenuItem restartGame = new MenuItem("Restart");
        restartGame.setOnAction(e -> {
            if (restart)
                root.setCenter(gameLayout(game.getPlayerA(), game.getPlayerB(), game.getLevel()));
        });
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> root.setCenter(welcomeLayout()));
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> root.setCenter(aboutLayout()));
        MenuItem rulesItem = new MenuItem("Rules");
        rulesItem.setOnAction(e -> root.setCenter(rulesLayout()));
        gameMenu.getItems().addAll(restartGame, newGame);
        helpMenu.getItems().addAll(aboutItem, rulesItem);
        menuBar.getMenus().addAll(gameMenu, helpMenu);
        root.setTop(menuBar);

        Scene welcomeScreen = new Scene(root, 850, 850);
        welcomeScreen.setOnKeyPressed(e -> { if(e.getCode() == KeyCode.F) pressedF = true; });
        welcomeScreen.setOnKeyReleased(e -> { if(e.getCode() == KeyCode.F) pressedF = false; });
        window.setScene(welcomeScreen);
        window.show();
    }

    //main is ignored
    public static void main(String[] args) {
        launch(args);
    }
}
