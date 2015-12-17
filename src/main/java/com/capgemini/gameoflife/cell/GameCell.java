package com.capgemini.gameoflife.cell;

import java.util.*;

public abstract class GameCell implements Cell {
	private Set<Cell> neighbours = new HashSet<Cell>(8, 1.0f);
	private LifeState currentState;
	private LifeState nextGenerationState;
	private int livingNeighbours;

	public GameCell(LifeState cellState) {
		currentState = cellState;
		nextGenerationState = cellState;
	}

	@Override
	public void evaluateNextGeneration() {
		if (livingNeighbours < 2 || livingNeighbours > 3) {
			nextGenerationState = LifeState.DEAD;
		}
		if (livingNeighbours == 3) {
			nextGenerationState = LifeState.ALIVE;
		}
	}

	public LifeState getState() {
		return currentState;
	}

	@Override
	public void nextGeneration() {
		if (currentState.equals(nextGenerationState) == false) {
			currentState = nextGenerationState;

			switch (currentState) {
			case ALIVE:
				neighbours.parallelStream()
						.forEach(cell -> cell.increaseLivingNeighboursCount());
				break;
			case DEAD:
				neighbours.parallelStream()
						.forEach(cell -> cell.decreaseLivingNeighboursCount());
				break;
			default:
				
			}
		}
	}
	
	@Override
	public void addNeighbour(Cell neighbour) {
		if (neighbours.add(neighbour) && LifeState.ALIVE.equals(neighbour.getState())) {
			livingNeighbours++;
		}
	}

	@Override
	public Collection<Cell> getNeighbours() {
		return neighbours;
	}

	@Override
	public void increaseLivingNeighboursCount() {
		livingNeighbours++;
	}

	@Override
	public void decreaseLivingNeighboursCount() {
		livingNeighbours--;
	}

	@Override
	public int getLivingNeighboursCount() {
		return livingNeighbours;
	}
	
	@Override
	public String toString() {
		return LifeState.ALIVE.equals(currentState) ? "|" + "X" + "|" :
			" " + livingNeighbours + " ";
	}
}