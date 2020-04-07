import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserInterface {

    BorderPane mainPane;
    GridPane boardPane;
    TextArea infoArea;
    TextField commandField;
    Button[][] displaySquares = new Button[Board.BOARD_SIZE][Board.BOARD_SIZE];
    Scrabble scrabble;
    boolean gameOver;

    UserInterface() {
        scrabble = new Scrabble();
        gameOver = false;
    }

    // Stage display methods

    public void displayStage(Stage primaryStage) {
        primaryStage.setTitle("Scrabble");

        mainPane = new BorderPane();
        boardPane = new GridPane();
        infoArea = new TextArea();
        commandField = new TextField();

        infoArea.setPrefRowCount(10);
        infoArea.setPrefColumnCount(15);
        infoArea.setWrapText(true);

        commandField.setPromptText("Enter command...");
        commandField.setPrefColumnCount(15);
        commandField.setOnAction(e -> {
            if (!gameOver) {
                String input = commandField.getText();
                infoArea.appendText("> " + input + "\n");
                commandField.clear();
                processInput(input);
            }
            if (!gameOver) {
                printPrompt();
            }
        });

        // rows are numbered from zero at the top
        // columns are numbers from zero at the left
        boardPane.setGridLinesVisible(true);
        int squareSize = 50;
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            boardPane.getColumnConstraints().add(new ColumnConstraints(squareSize));
            boardPane.getRowConstraints().add(new RowConstraints(squareSize));
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                Button button = new Button();
                boardPane.add(button, c, r);
                displaySquares[r][c] = button;
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillHeight(button, true);
                GridPane.setFillWidth(button, true);
            }
        }
        refreshBoard();

        mainPane.setCenter(boardPane);
        mainPane.setBottom(commandField);
        mainPane.setRight(infoArea);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();

        printGameStart();
        printPrompt();
    }

    void refreshBoard() {
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                displaySquare(r, c);
            }
        }
    }

    void displaySquare(int r, int c) {
        Square square = scrabble.getBoard().getSquare(r, c);
        Button button = displaySquares[r][c];
        String style = "";
        style+="-fx-background-radius: 0;";
        String color;
        if (square.isDoubleLetter())
        {
            color = "8080ff";
        }
        else if (square.isTripleLetter())
        {
            color = "0000ff";
        }
        else if (square.isDoubleWord())
        {
            color = "ff8080";
        }
        else if (square.isTripleWord())
        {
            color = "ff0000";
        }
        else
        {
            color = "ffffff";
        }
        //Color.rbg(130,30,12);
        style+="-fx-background-color: #";
        style+=color;
        style+=';';
        if (square.isOccupied()) {
            style+="-fx-font-size: 14pt;";
            style+="-fx-font-weight: bold;";
            button.setStyle(style);
            button.setText(square.getTile() + "");  // placed letter
        } else {
            style+="-fx-font-size: 8pt;";
            style+="-fx-font-weight: lighter;";
            button.setStyle(style);
            if (r == 0) {
                button.setText(((char) (((int) 'A') + c)) + "");  // column letters
            } else if (c == 0) {
                button.setText((r + 1) + "");  // row numbers
            } else {
                button.setText("");  // empty
            }
        }
    }

    // Input methods

    private void processInput(String input) {
        String command = input.trim().toUpperCase();
        Player currentPlayer = scrabble.getCurrentPlayer();
        if (command.equals("QUIT") || command.equals("Q")) {
            System.exit(0);
        } else if (!gameOver && (command.equals("PASS") || command.equals("P"))) {
            scrabble.zeroScorePlay();
            if (scrabble.isZeroScorePlaysOverLimit()) {
                printZeroScorePlaysOverLimit();
                gameOver = true;
            } else {
                scrabble.turnOver();
            }
        } else if (!gameOver && (command.equals("HELP")||command.equals("H"))) {
            printHelp();
        } else if (!gameOver && (command.equals("SCORE")||command.equals("S"))) {
            printScores();
        } else if (!gameOver && (command.equals("POOL")||command.equals("O"))) {
            printPoolSize();
        }
        else if (!gameOver && (command.equals("DDOS")||command.equals("D"))) {

            printLine(scrabble.getDictionary().dictionar.size()+"size");
            // scrabble.getDictionary().getWord();

            printLine(" Bool in scrabble ");
            if(scrabble.getDictionary().dictionarysearch(scrabble.getBoard().lastWord.getLetters()) /*scrabble.getDictionary().dictionarysearch(scrabble.getBoard().lastWord.getLetters())*/)
                printLine("Celebrate it's a word");
            else
                printLine("Not in the Dict");
            //printLine(scrabble.dictionar.get(276));
        }
         else if (!gameOver && (command.equals("CHALLENGE")||command.equals("C")))
         {

             if (scrabble.getBoard().challengeLegal())
             {
                 if (!scrabble.getDictionary().dictionarysearch(scrabble.getBoard().lastWord.getLetters()))
                 {
                     scrabble.challenge();
                     refreshBoard();
                 }
                 else
                     printLine("correct word, board is occupied");
             }
             else
                 {
                 scrabble.getBoard().challengeErrorAssinger();
                 printPlayError(scrabble.getBoard().getErrorCode());
             }
         }
             else if (!gameOver && (command.matches("NAME( )+([A-Z_]){0,9}") || command.matches("N( )+([A-Z_]){0,9}"))) {
            String[] parts = command.split("( )+");
            String uname = parts[1];
            if (uname.length() > 0)
                currentPlayer.setName(uname);
        } else if (!gameOver && (command.matches("[A-O](\\d){1,2}( )+[A,D]( )+([A-Z_]){1,15}"))) {
            Word word = parsePlay(command);
            if (!scrabble.getBoard().isLegalPlay(currentPlayer.getFrame(), word)) {
                printPlayError(scrabble.getBoard().getErrorCode());
            } else {
                scrabble.getBoard().place(currentPlayer.getFrame(), word);
                refreshBoard();
                int points = scrabble.getBoard().getPoints();
                printPoints(points);
                currentPlayer.addPoints(points);
                currentPlayer.getFrame().refill(scrabble.getPool());
                scrabble.scorePlay();
                scrabble.turnOver();
                if (currentPlayer.getFrame().isEmpty() && currentPlayer.getFrame().isEmpty()) {
                    gameOver = true;
                }
            }
        } else if (!gameOver && (command.matches("EXCHANGE( )+([A-Z_]){1,7}") || command.matches("X( )+([A-Z_]){1,7}"))) {
            String[] parts = command.split("( )+");
            String letters = parts[1];
            if (!currentPlayer.getFrame().isLegalExchange(scrabble.getPool(), letters)) {
                printExchangeError(currentPlayer.getFrame().getErrorCode());
            } else {
                currentPlayer.getFrame().exchange(scrabble.getPool(), letters);
                printTiles();
                scrabble.zeroScorePlay();
                if (scrabble.isZeroScorePlaysOverLimit()) {
                    printZeroScorePlaysOverLimit();
                    gameOver = true;
                } else {
                    scrabble.turnOver();
                }
            }
        } else {
            printLine("Error: command syntax incorrect. See help.");
        }
        if (gameOver) {
            scrabble.adjustScores();
            printScores();
            printWinner();
            printGameOver();
        }
    }

    private Word parsePlay(String command) {
        String[] parts = command.split("( )+");
        String gridText = parts[0];
        int column = ((int) gridText.charAt(0)) - ((int) 'A');
        String rowText = parts[0].substring(1);
        int row = Integer.parseInt(rowText) - 1;
        String directionText = parts[1];
        boolean isHorizontal = directionText.equals("A");
        String letters = parts[2];
        return new Word(row, column, isHorizontal, letters);
    }

    // Print methods

    private void print(String text) {
        infoArea.appendText(text);
    }

    private void printLine(String text) {
        infoArea.appendText(text + "\n");
    }

    private void printGameStart() {
        printLine("WELCOME TO SCRABBLE");
    }

    private void printTiles() {
        printLine(scrabble.getCurrentPlayer().getName() + " has the following tiles:");
        for (Tile tile : scrabble.getCurrentPlayer().getFrame().getTiles()) {
            print(tile + " ");
        }
        printLine("");
    }

    private void printPrompt() {
        printLine(scrabble.getCurrentPlayer().getName() + "'s turn:");
        printTiles();
    }

    private void printPoints(int points) {
        printLine(scrabble.getCurrentPlayer() + " scored " + points + " points.");
    }

    private void printPoolSize() {
        printLine("Pool has " + scrabble.getPool().size() + " tiles");
    }

    private void printHelp() {
        printLine("Command options: Q (quit), P (pass), X (exchange), S (scores), O (pool) or play");
        printLine("For an exchange, enter the letters that you wish to exchange. E.g. X ABC");
        printLine("For a play, enter the grid reference of the first letter, and A (across) or D (down)m and the word optionally including any letters already on the board. E.g. A1 D HELLO");
        printLine("For blank use underscore");
    }

    private void printPlayError(int errCode) {
        String message = "";
        switch (errCode) {
            case Board.WORD_OUT_OF_BOUNDS:
                message = "Error: Word does not fit on the board.";
                break;
            case Board.WORD_LETTER_NOT_IN_FRAME:
                message = "Error: You do not have the necessary letters.";
                break;
            case Board.WORD_LETTER_CLASH:
                message = "Error: The word entered does not fit with the letters on the board.";
                break;
            case Board.WORD_NO_LETTER_PLACED:
                message = "Error: The word does not use any of your letters.";
                break;
            case Board.WORD_NO_CONNECTION:
                message = "Error: The word is not connected with the words on the board. ";
                break;
            case Board.WORD_INCORRECT_FIRST_PLAY:
                message = "Error: The first word must be in the centre of the board.";
                break;
            case Board.WORD_EXCLUDES_LETTERS:
                message = "Error: there are tiles excluded";
                break;
            case Board.WORD_ONE_LETTER_LENGTH:
                message = "Error: your full word is too short- it has to have at least 2 letters";
                break;
            case Board.CHALLENGED_BEFORE_FIRST_ROUND:
                message = "Error: you can't challenge your opponent's choice if he made no choice yet";
        }
        printLine(message);
    }


    public void printChallengeFirstWordError() {
        String message = "";
        message = "Error: You're challenging opponent's word, but he hadn't put any words on board yet.";
        printLine(message);
    }

    public void printExchangeError (int errCode) {
        String message = "";
        switch (errCode) {
            case Frame.EXCHANGE_NOT_AVAILABLE:
                message = "Error: Letter not available in the frame.";
                break;
            case Frame.EXCHANGE_NOT_ENOUGH_IN_POOL:
                message = "Error: No enough tiles in the pool.";
                break;
        }
        printLine(message);
    }

    private void printZeroScorePlaysOverLimit() {
        printLine("The number of zero score plays is over the limit.");
    }

    private void printScores() {
        for (Player player : scrabble.getPlayers()) {
            printLine(player.getName() + " has " + player.getScore() + " points.");
        }
    }

    private void printWinner() {
        int maxScore = -1000;
        ArrayList<Player> winners = new ArrayList<>();
        boolean draw = false;
        for (Player player : scrabble.getPlayers()) {
            if (player.getScore() > maxScore) {
                draw = false;
                winners.clear();
                winners.add(player);
                maxScore = player.getScore();
            } else if (player.getScore() == maxScore) {
                draw = true;
                winners.add(player);
            }
        }
        if (!draw) {
            printLine(winners.get(0).getName() + " wins!");
        } else {
            printLine("The following players draw!");
            for (Player winner : winners) {
                printLine(winner.getName() + "");
            }
        }
    }

    private void printGameOver() {
        printLine("GAME OVER");
    }
}