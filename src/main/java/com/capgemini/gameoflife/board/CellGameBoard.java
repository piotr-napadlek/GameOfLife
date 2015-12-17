package com.capgemini.gameoflife.board;

import java.util.Collection;

import com.capgemini.gameoflife.cell.Cell;

public interface CellGameBoard {
	public void processRounds(int times);
	public Collection<Cell> getCells();
	public Cell getCellAt(int row, int column);
	public int width();
	public int height();
}