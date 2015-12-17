package com.capgemini.gameoflife.cell;

import java.util.*;

import com.capgemini.gameoflife.board.utils.BoardPosition;

public interface Cell {
	public LifeState getState();
	public BoardPosition getPosition();
	public void addNeighbour(Cell neighbour);
	public Collection<Cell> getNeighbours();
	
	public void evaluateNextGeneration();
	public void nextGeneration();
	
	void increaseLivingNeighboursCount();
	void decreaseLivingNeighboursCount();
	public int getLivingNeighboursCount();
}