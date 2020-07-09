package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() { // OBTENDO A POSIÇÃO DA PEÇA 
		return ChessPosition.fromPosition(position); // POSIÇÃO DA PEÇA EM MATRIZ
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // CHESSPIECE (P) RECEBE A POSIÇÃO QUE ESTIVER A PEÇA
		return p != null && p.getColor() != color; // TESTANDO SE É UMA PEÇA ADVERSÁRIA 
	}
}
