package com.capgemini.gameoflife.board.utils;

import java.util.*;

public class BoardPosition {
	private final int row;
	private final int column;

	private BoardPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static BoardPosition of(int row, int column) {
		return new BoardPosition(row, column);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	private BoardPosition left() {
		return new BoardPosition(row, column - 1);
	}
	
	private BoardPosition right() {
		return new BoardPosition(row, column + 1);
	}
	
	private BoardPosition upper() {
		return new BoardPosition(row - 1, column);
	}
	
	private BoardPosition lower() {
		return new BoardPosition(row + 1, column);
	}
	
	private BoardPosition lowerLeft() {
		return new BoardPosition(row + 1, column - 1);
	}
	
	private BoardPosition lowerRight() {
		return new BoardPosition(row + 1, column + 1);
	}
	
	private BoardPosition upperLeft() {
		return new BoardPosition(row - 1, column - 1);
	}	
	
	private BoardPosition upperRight() {
		return new BoardPosition(row - 1, column + 1);
	}
	
	public Set<BoardPosition> getNeighbourPositions() {
		return new HashSet<BoardPosition>(Arrays.asList(
				left(),
				right(),
				upper(),
				lower(),
				lowerLeft(),
				lowerRight(),
				upperLeft(),
				upperRight()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime + column) + row;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof BoardPosition)) {
			return false;
		}
		BoardPosition other = (BoardPosition) obj;
		return column == other.column && row == other.row;
	}
}