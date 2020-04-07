package chess;

import chess.consoleView.GameManager;

public class ConsoleUIChessApplication {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.runGame();
    }
}
