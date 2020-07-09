package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // PEGANDO A PEÇA (P) QUE ESTIVER NA POSIÇÃO
		return p == null || p.getColor() != getColor(); // PEÇA P MOVE SE NA FRENTE FOR NUUL OU FOR UMA PEÇA ADVERSARIA
	}

	private boolean testRookCastling(Position position) { // METODO QUE TESTA SE A TORRE ESTÁ APTA PARA O "ROQUE"
		ChessPiece p = (ChessPiece) getBoard().piece(position); // PEGANDO A POSIÇÃO DA PEÇA
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; // CONDIÇÃO
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
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { // CONDIÇOES
			mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
		}

		// specialmove CASTLING
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// ROQUE PEQUENO
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // POSIÇÃO ONDE DEVE ESTAR A
																						// TORRE NO LE
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); // POSIÇÃO DA CASA AO LADO DO
																							// REI
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) { // SE AS POSIÇÕES AO LADO ESTIVEREM
																					// VAZIAS
					mat[position.getRow()][position.getColumn() + 2] = true; // A PEÇA ANDA 2 CASA PARA O LADO
				}
			}

			// ROQUE GRANDE
			Position posT2 = new Position(position.getRow(), position.getColumn() + 4); 
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); 
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) { 
					mat[position.getRow()][position.getColumn() - 2] = true; // A PEÇA ANDA 2 CASA PARA O LADO
				}
			}
		}

		return mat;
	}
}
