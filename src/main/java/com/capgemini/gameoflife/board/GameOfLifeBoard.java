package com.capgemini.gameoflife.board;

import java.util.*;
import java.util.stream.*;

import com.capgemini.gameoflife.board.utils.*;
import com.capgemini.gameoflife.cell.*;

public final class GameOfLifeBoard implements CellGameBoard {
	private BiMap<BoardPosition, Cell> cellsMap;
	private Set<Cell> cellsToCalculate;

	public GameOfLifeBoard(BiMap<BoardPosition, Cell> cells) {
		this.cellsMap = cells;
		
		cellsToCalculate = cellsMap.valueSet().stream()
				.filter(cell -> cell instanceof BorderCell == false)
				.collect(Collectors.toSet());
		}

	@Override
	public void processRounds(int roundsAmount) {
		IntStream.range(0, roundsAmount).forEach(i -> iterate());
	}

	private void applyLinkingTo(Collection<Cell> cells) {
		cells.forEach(cell -> { 
			if (cell.getNeighbours().size() < 8) {
				setCellNeighbours(cellsMap.getByValue(cell), cell);
			}
		});
	}

	private void setCellNeighbours(BoardPosition position, Cell cell) {
		position.getNeighbourPositions().forEach(pos -> {
			if (!cellsMap.containsKey(pos)) {
				cellsMap.put(pos, new DeadCell());
			}
			Cell neighbour = cellsMap.get(pos); 
			cell.addNeighbour(neighbour);
			neighbour.addNeighbour(cell);
		});
	}

	private void iterate() {
		cellsToCalculate.forEach(cell -> cell.evaluateNextGeneration());
		cellsToCalculate.forEach(cell -> cell.nextGeneration());
		filterCellsToCalculate();
		applyLinkingTo(cellsToCalculate);
		addNeighboursOfLivingToCalculated();
	}

	private void filterCellsToCalculate() {
		cellsToCalculate.removeIf(cell -> LifeState.DEAD.equals(cell.getState()));
	}

	private void addNeighboursOfLivingToCalculated() {
		cellsToCalculate.addAll(cellsToCalculate.parallelStream()
				.flatMap(cell -> cell.getNeighbours().stream())
				.filter(neighbour -> neighbour.getLivingNeighboursCount() == 3)
				.collect(Collectors.toSet()));
	}

	@Override
	public Collection<Cell> getCells() {
		return cellsMap.valueSet();
	}

	@Override
	public Cell getCellAt(int row, int column) {
		return cellsMap.getOrDefault(new BoardPosition(row, column),
				new DeadCell());
	}

	@Override
	public int width() {
		return maxColumn() - minColumn();
	}

	@Override
	public int height() {
		return maxRow() - minRow();
	}

	private int minRow() {
		return cellsMap.keySet().stream().mapToInt(pos -> pos.getRow()).min().orElse(0);
	}
	
	private int maxRow() {
		return cellsMap.keySet().stream().mapToInt(pos -> pos.getRow()).max().orElse(0);
	}
	
	private int minColumn() {
		return cellsMap.keySet().stream().mapToInt(pos -> pos.getColumn()).min().orElse(0);
	}
	
	private int maxColumn() {
		return cellsMap.keySet().stream().mapToInt(pos -> pos.getColumn()).max().orElse(0);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("\n");
		IntStream.rangeClosed(minRow(), maxRow()).forEach(i -> {
			IntStream.rangeClosed(minColumn(), maxColumn()).forEach(j -> {
				stringBuilder.append(Optional.ofNullable(cellsMap.get(new BoardPosition(i, j)))
						.map(c -> c.toString()).orElse(" . "));
			});
			stringBuilder.append("\n");
		});
		return stringBuilder.toString();
	}
}