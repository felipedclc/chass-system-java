package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // PEGANDO A PEÇA (P) QUE ESTIVER NA POSIÇÃO
		return p == null || p.getColor() != getColor(); // PEÇA P MOVE SE NA FRENTE FOR NUUL OU FOR UMA PEÇA ADVERSARIA
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// nw(noroeste)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}
		
		// ne
		p.setValues(position.getRow() -1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}
		
		// sw
		p.setValues(position.getRow() +1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}
		
		// se
		p.setValues(position.getRow() +1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		return mat;
	}
}
