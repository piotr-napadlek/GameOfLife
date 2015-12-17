package com.capgemini.gameoflife.cell;

import java.util.*;

public interface Cell {
	public LifeState getState();
	public void addNeighbour(Cell neighbour);
	public Collection<Cell> getNeighbours();
	
	public void evaluateNextGeneration();
	public void nextGeneration();
	
	void increaseLivingNeighboursCount();
	void decreaseLivingNeighboursCount();
	public int getLivingNeighboursCount();
}