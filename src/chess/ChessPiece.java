package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // CHESSPIECE (P) RECEBE A POSI��O QUE ESTIVER A PE�A
		return p != null && p.getColor() != color; // TESTANDO SE � UMA PE�A ADVERS�RIA 
	}
}
