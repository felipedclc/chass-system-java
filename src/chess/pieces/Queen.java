package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

	public class Queen extends ChessPiece {

		public Queen(Board board, Color color) {
			super(board, color);
		}

		@Override
		public String toString() {
			return "Q"; // LETRA DO TABULEIRO
		}

		@Override
		public boolean[][] possibleMoves() {
			boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

			Position p = new Position(0, 0);

			// above
			p.setValues(position.getRow() - 1, position.getColumn()); // -1 (LINHA ACIMA DA PEÇA)
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO P EXISTIR E NÃO
																					// TIVER NENHUMA PEÇA NESSE LUGAR
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setRow(p.getRow() - 1); // SOBE 1 CASA
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// left
			p.setValues(position.getRow(), position.getColumn() - 1); // -1 (COLUNA A ESQUERDA DA PEÇA)
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setColumn(p.getColumn() - 1); // SOBE 1 CASA
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// right
			p.setValues(position.getRow(), position.getColumn() + 1); // +1 (COLUNA A DIREITA DA PEÇA)
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setColumn(p.getColumn() + 1); // SOBE 1 CASA
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// below
			p.setValues(position.getRow() + 1, position.getColumn()); // -1 (LINHA ACIMA DA PEÇA)
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setRow(p.getRow() + 1); // SOBE 1 CASA
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// NW
			p.setValues(position.getRow() - 1, position.getColumn() -1); // -1 (LINHA ACIMA DA PEÇA, DIAGONAL)
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO P EXISTIR E NÃO
																					// TIVER NENHUMA PEÇA NESSE LUGAR
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setValues(p.getRow() - 1, p.getColumn() -1); 
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// NE
			p.setValues(position.getRow() -1, position.getColumn() +1); 
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setValues(p.getRow() - 1, p.getColumn() +1); 
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// SW
			p.setValues(position.getRow() +1, position.getColumn() - 1);
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setValues(p.getRow() + 1, p.getColumn() +1); 
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// SE
			p.setValues(position.getRow() + 1, position.getColumn() +1); 
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true; // MOVIMENTO POSSIVEL
				p.setValues(p.getRow() +1, p.getColumn() +1); 
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			return mat;
		}
	}