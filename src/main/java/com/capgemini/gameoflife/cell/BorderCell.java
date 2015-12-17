package com.capgemini.gameoflife.cell;

import java.util.Collection;
import java.util.Collections;

import com.capgemini.gameoflife.board.utils.BoardPosition;

public final class BorderCell implements Cell {
	private static final BorderCell borderCell = new BorderCell();

	private BorderCell() {}

	public static BorderCell getInstance() {
		return borderCell;
	}

	@Override
	public void addNeighbour(Cell neighbour) { }
	@Override
	public void nextGeneration() { }
	@Override
	public void increaseLivingNeighboursCount() { }
	@Override
	public void decreaseLivingNeighboursCount() { }
	@Override
	public void evaluateNextGeneration() { }
	@Override
	public Collection<Cell> getNeighbours() {
		return Collections.emptySet();
	}
	@Override
	public LifeState getState() {
		return LifeState.DEAD;
	}
	@Override
	public String toString() {
		return " o ";
	}
	@Override
	public int getLivingNeighboursCount() {
		return 0;
	}
}