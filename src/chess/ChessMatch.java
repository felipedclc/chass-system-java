package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch { // CLASSE ONDE CONTEM AS REGRAS DO JOGO

	private int turn;
	private Color currentPlayer;
	private Board board; // IMPORT BOARD (TABULEIRO)
	private boolean check; // boolean JA COME�A VALENDO FALSE
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() { // CONSTRUTOR DA PARTIDA
		board = new Board(8, 8); // DIMENS�O DO TABULEIRO
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece[][] getPieces() { // RETORNA UMA MATRIZ DE PE�AS DE XADREZ CORRESPONDENTE � "CHESS MATCH"
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcepoPosition) {
		Position position = sourcepoPosition.toPosition(); // CONVERTENDO A POSI��O DE LETRAS(a1) PARA MATRIZ
		validateSourcePosition(position); // VALIDANDO A POSICAO DE ORIGEM
		return board.piece(position).possibleMoves(); // POSI��ES POSSIVEIS
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { // POSI��O DE
																										// ORIGEM E
																										// DESTINO
		Position source = sourcePosition.toPosition(); // CONVERTENDO O SOURCE PARA POSI��O DA MATRIZ
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // VALIDANDO A POSI��O DE ORIGEM
		validaTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); // REALIZA O MOVIMENTO DA PE�A PELA MATRIZ

		if (testCheck(currentPlayer)) { // SE A PE�A(REI) ESTIVER SE COLOCANDO EM CHEQUE
			undoMove(source, target, capturedPiece); // DESFAZENDO O MOVIMENTO
			throw new ChessException("You can't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		check = (testCheck(opponent(currentPlayer))) ? true : false; // TESTANDO SE O OPONENTE ESTA EM CHEQUE

		if (testCheckMate(opponent(currentPlayer))) { // TESTANDO SE HOUVE CHECK MATE
			checkMate = true; // END GAME
		} else { // JOGO CONTINUA
			nextTurn();
		}

		// MOVIMENTO EN PASSAN
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); // RETIRANDO A PE�A QUE ESTAVA NA POSI��O DE ORIGEM
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); // PE�A CAPTURADA NA VARIAVEL (CAPTURED PIECE)
		board.placePiece(p, target); // SUBSTITUI��O DAS PE�AS (P - ORIGEM)

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // SE O REI SAIR DA POSI��O INICIAL E ANDAR 2 CASAS
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // POSI��O INICIAL TORRE
			Position targetT = new Position(target.getRow(), target.getColumn() + 1); // MESMA POSI��O DO REI, MAIS 1 COLUNA PARA DIREITA
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // RETIRANDO A POSI��O INICIAL DA TORRE
			board.placePiece(rook, targetT); // MUDANDO A PE�A DE POSI��O
			rook.increaseMoveCount(); // INCREMENTO DA CONTAGEM DE MOVIMENTOS DA TORRE
		}

		// ROQUE GRANDE
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(target.getRow(), target.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// EN PASSAN
		if (p instanceof Pawn) { // SE A PE�A FOR UM PEAO
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // ANDAR NA DIAGONAL E NAO CAPTURAR PE�A
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // POSI��O DO PEAO QUE SERA CAPTURADO
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // POSI��O DO PEAO QUE SERA CAPTURADO
				}
				capturedPiece = board.removePiece(pawnPosition); // INSTANCIANDO A CAPTURA
				capturedPieces.add(capturedPiece); // ADD O PEAO NA LISTA DE PE�AS CAPTURADAS
				piecesOnTheBoard.remove(capturedPiece); // REMOVENDO DO TABULEIRO
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target); // REMOVENDO A PE�A DO DESTINO FINAL
		p.decreaseMoveCount();
		board.placePiece(p, source); // RETORNA A PE�A PARA A POSI�AO DE ORIGEM

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target); // VOLTANDO A PE�A CAPTURADA PARA O JOGO
			capturedPieces.remove(capturedPiece); // REMOVENDO A PE�A DA LISTA DE CAPTURADAS
			piecesOnTheBoard.add(capturedPiece); // VOLTANDO A PE�A CAPTURADA PARA A LISTA DO TABULEIRO
		}

		// ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // SE O REI SAIR DA POSI��O INICIAL E ANDAR 2 CASAS
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // POSI��O INICIAL TORRE
			Position targetT = new Position(target.getRow(), target.getColumn() + 1); // MESMA POSI��O DO REI, MAIS COLUNA PARA DIREITA
			ChessPiece rook = (ChessPiece) board.removePiece(targetT); // RETIRANDO A POSI��O INICIAL DA TORRE
			board.placePiece(rook, sourceT); // MUDANDO A PE�A DE POSI��O
			rook.decreaseMoveCount(); // INCREMENTO DA CONTAGEM DE MOVIMENTOS DA TORRE
		}

		// ROQUE GRANDE
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(target.getRow(), target.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// EN PASSAN
		if (p instanceof Pawn) { // SE A PE�A FOR UM PEAO
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { 
				ChessPiece pawn = (ChessPiece)board.removePiece(target); // REMOVENDO O PEAO QUE FOI DEVOLVIDO ONDE O PEAO RIVAL PAROU 
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn()); //POSI��O PE�A PRETA
				} else {
					pawnPosition = new Position(4, target.getColumn()); // POSI��O PE�A BRANCA
				}
				board.placePiece(pawn, pawnPosition); // VOLTANDO O PEAO PARA O PAWN POSITION 
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { // SE N�O HOUVER UMA PE�A NA POSI��O DE ORIGEM
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) { // TESTANDO A COR DA PE�A E DO
																				// ADVERSARIO OU A SUA
			throw new ChessException("The chosen piece is not yours");
		}

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validaTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) { // SE O MOVIMENTO DE ORIGEM PARA O DESTINO N�O � POSS�VEL
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // TROCA A COR DO JOGADOR A CADA
																					// JOGADA
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // SE A COR FOR BRANCA VAI RETORNAR O
																	// PRETO(OPONENTE), CASO CONTRARIO RETORNA UM BRANCO
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList()); // FILTRANDO A LISTA PELA COR
		for (Piece p : list) {
			if (p instanceof King) { // SE P FOR O REI
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}

	private boolean testCheck(Color color) { // TESTANDO SE O REI ESTA EM CHEQUE
		Position kingPosition = king(color).getChessPosition().toPosition(); // PEGANDO A POSI��O EM MATRIZ DO REI
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList()); // PEGANDO A
																											// LSITA DE
																											// OPONENTES
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); // MATRIZ DE MOVIMENTOS POSSIVEIS DA PE�A ADVERSARIA P
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // SE TIVER MOVIMENTOS POSSIVEIS EM DIRE��O AO
																		// P(REI)
				return true;
			}
		}
		return false; // SE PASSAR DO IF ELE RETORNA FALSE
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves(); // CRIANDO UMA MATRIZ DE MOVIMENTOS POSSIVEIS
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) { // SE O MOVIMENTO FOR POSSIVEL
						Position source = ((ChessPiece) p).getChessPosition().toPosition(); // CONVERTENDO A PE�A P(REI)
																							// PARA MATRIZ
						Position target = new Position(i, j); // POSI��O DE DESTINO NA MATRIZ
						Piece capturedPiece = makeMove(source, target); // FAZENDO O MOVIMENTO DENTRO DA MATRIZ
						boolean testCheck = testCheck(color); // TESTANDO SE O REI AINDA ESTA EM CHECK
						undoMove(source, target, capturedPiece); // DESFAZENDO O MOVIMENTO DE TESTE
						if (!testCheck) { // SE N�O ESTA MAIS EM CHEQUE
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) { // RECEBE AS COORDENADAS EM LETRAS (a1)
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // COLOCANDO AS PE�AS NO TABULEIRO
		piecesOnTheBoard.add(piece); // ADICIONANDO AS PE�AS NA LISTA
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}