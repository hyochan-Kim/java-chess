package chess.view;

import chess.board.Coordinate;
import chess.board.Tile;

import java.util.Map;

public class OutputView {

    public static void showAllCommand() {
        System.out.println("체스 게임을 시작합니다.");
        System.out.println("게임 시작은 start, 종료는 end 명령을 입력하세요.");
    }

    public static void showChessBoard(ChessBoard chessBoard) {
        Map<Coordinate, Tile> board = chessBoard.getChessBoard();

    }
}
