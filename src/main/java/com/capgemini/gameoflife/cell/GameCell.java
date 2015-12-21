package com.capgemini.gameoflife.cell;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.capgemini.gameoflife.board.utils.BoardPosition;

public class GameCell implements Cell {
	public static GameCell deadAt(BoardPosition position) {
		return new GameCell(LifeState.DEAD, position);
	}
	
	public static GameCell livingAt(BoardPosition position) {
		return new GameCell(LifeState.ALIVE, position);
	}
	
	private final Set<Cell> neighbours = new HashSet<Cell>(8, 1.0f);
	private final BoardPosition position;
	private LifeState currentState;
	private LifeState nextGenerationState;
	private int livingNeighbours;

	private GameCell(LifeState cellState, BoardPosition position) {
		currentState = cellState;
		nextGenerationState = cellState;
		this.position = position;
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

	public BoardPosition getPosition() {
		return position;
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