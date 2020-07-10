package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // MATRIZ AUXILIAR
		Position p = new Position(0, 0); // POSIÇÃO AUXILIAR

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn()); // 1 POSIÇÂO ACIMA
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Posição existir e não tiver peça
																				// bloqueando a passagem
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

			// MOVIMENTO EN PASSAN (WHITE)
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1); // EN PASSAN NO PEAO LE
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
					;
				mat[left.getRow() - 1][left.getColumn()] = true; // AUTORIZA O MOVIMENTO
			}
			Position right = new Position(position.getRow(), position.getColumn() - 1); // EN PASSAN NO PEAO LD
			if (getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
				mat[right.getRow() - 1][right.getColumn()] = true; // AUTORIZA O MOVIMENTO
			}
		}

		else { // SE A PEÇA FOR PRETA (PEÇA ANDA PARA BAIXO)
			p.setValues(position.getRow() + 1, position.getColumn()); // 1 POSIÇÂO ABAIXO
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Posição existir e não tiver peça
																				// bloqueando a passagem
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
			p.setValues(position.getRow() + 1, position.getColumn() + 1); // 1 POSIÇÂO DIAGONAL LD
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // Posição existir e tiver peça oponent
				mat[p.getRow()][p.getColumn()] = true; // PODE MOVER
			}

			// MOVIMENTO EN PASSAN (BLACK)
			if (position.getRow() == 3) {
				Position right = new Position(position.getRow(), position.getColumn() - 1); // EN PASSAN NO PEAO LE
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
					;
				mat[right.getRow() - 1][right.getColumn()] = true; // AUTORIZA O MOVIMENTO
			}
			Position right = new Position(position.getRow(), position.getColumn() - 1); // EN PASSAN NO PEAO LD
			if (getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
				mat[right.getRow() - 1][right.getColumn()] = true; // AUTORIZA O MOVIMENTO
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}