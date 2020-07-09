package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // MATRIZ AUXILIAR
		Position p = new Position(0, 0); // POSIÇÃO AUXILIAR

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn()); // 1 POSIÇÂO ACIMA
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Posição existir e não tiver peça bloqueando a passagem
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() - 2, position.getColumn()); // 2 POSICOES ACIMA
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() - 1, position.getColumn() - 1); // 1 POSIÇÂO DIAGONAL LE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // Posição existir e tiver peça oponent
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() - 1, position.getColumn() + 1); // 1 POSIÇÂO DIAGONAL LD
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // Posição existir e tiver peça oponent
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER

			}
		}
		else { // SE A PEÇA FOR PRETA (PEÇA ANDA PARA BAIXO)
			p.setValues(position.getRow() + 1, position.getColumn()); // 1 POSIÇÂO ABAIXO
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Posição existir e não tiver peça bloqueando a passagem
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() + 2, position.getColumn()); // 2 POSICOES ABAIXO
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() + 1, position.getColumn() - 1); // 1 POSIÇÂO DIAGONAL LE
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // Posição existir e tiver peça oponent
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
			p.setValues(position.getRow() +1 , position.getColumn() + 1); // 1 POSIÇÂO DIAGONAL LD
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // Posição existir e tiver peça oponent
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P"; 
	}
	
}