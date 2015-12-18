package com.capgemini.gameoflife.board;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import com.capgemini.gameoflife.board.utils.*;
import com.capgemini.gameoflife.cell.*;

public final class GameOfLifeBoard implements CellGameBoard {
	private Map<BoardPosition, Cell> cellsMap;
	private Map<BoardPosition, Cell> cellsToCalculate;

	public GameOfLifeBoard(Map<BoardPosition, Cell> cells) {
		this.cellsMap = cells;
		
		cellsToCalculate = cellsMap.entrySet().stream()
				.filter(entry -> entry.getValue() instanceof BorderCell == false)
				.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
		}

	@Override
	public void processRounds(int roundsAmount) {
		IntStream.range(0, roundsAmount).forEach(i -> iterate());
	}

	private void applyLinkingTo() {
		Map<BoardPosition, Cell> newNeighbours = new HashMap<>();
		cellsMap.entrySet().forEach(entry -> {
			if(entry.getValue() instanceof GameCell 
					&& entry.getValue().getNeighbours().size() < 8) {
				entry.getKey().getNeighbourPositions().forEach(pos -> {
					if (cellsMap.containsKey(pos) == false) {
						newNeighbours.put(pos, GameCell.deadAt(pos));
					}
				});
			}
		});
		cellsMap.putAll(newNeighbours);
		
		newNeighbours.entrySet().forEach(entry -> { 
				entry.getKey().getNeighbourPositions().forEach(pos -> {
				if (cellsMap.containsKey(pos)) {
					Cell neighbourCell = cellsMap.get(pos);
					neighbourCell.addNeighbour(entry.getValue());
					entry.getValue().addNeighbour(neighbourCell);
				}
			});
		});
	}
	
	private void populateWitnNeighbours() {
	}

	private void setCellNeighbours(Entry<BoardPosition, Cell> cellEntry) {
		Map<BoardPosition, Cell> neighboursToAdd = new HashMap<>(8, 1.0f);
		cellEntry.getKey().getNeighbourPositions().forEach(pos -> {
			if (!cellsMap.containsKey(pos)) {
				neighboursToAdd.put(pos, GameCell.deadAt(pos));
			}
			Cell neighbour = cellsMap.get(pos); 
			cellEntry.getValue().addNeighbour(neighbour);
			neighbour.addNeighbour(cellEntry.getValue());
		});
	}

	private void iterate() {
		cellsMap.values().forEach(cell -> cell.evaluateNextGeneration());
		cellsMap.values().forEach(cell -> cell.nextGeneration());
		filterCellsToCalculate();
		populateWitnNeighbours();
		applyLinkingTo();
//		addToBeBornCellsToCalculated();
	}

	private void filterCellsToCalculate() {
		Map<BoardPosition, Cell> cellsToDelete = cellsMap.entrySet().stream()
				.filter(entry -> LifeState.DEAD.equals(entry.getValue().getState())
						&& entry.getValue().getLivingNeighboursCount() != 3
						&& entry.getValue() instanceof GameCell)
				.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
		cellsToDelete.values().stream()
				.forEach(cell -> cell.getNeighbours()
						.forEach(neighbour -> neighbour.deleteNeighbour(cell)));
		cellsToDelete.keySet().forEach(key -> cellsMap.remove(key));
		
	}

	private void addToBeBornCellsToCalculated() {
		Collection<BoardPosition> neighbourPositions = new HashSet<>();
		cellsToCalculate.keySet()
					.forEach(pos -> neighbourPositions.addAll(pos.getNeighbourPositions()));
		neighbourPositions.stream()
					.filter(pos -> cellsMap.get(pos).getLivingNeighboursCount()==3)
					.forEach(pos -> cellsToCalculate.put(pos, cellsMap.get(pos)));
	}

	@Override
	public Collection<Cell> getCells() {
		return cellsMap.values();
	}

	@Override
	public Cell getCellAt(int row, int column) {
		return cellsMap.getOrDefault(BoardPosition.of(row, column),
				GameCell.deadAt(BoardPosition.of(row, column)));
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
				stringBuilder.append(Optional.ofNullable(cellsMap.get(BoardPosition.of(i, j)))
						.map(c -> c.toString()).orElse(" . "));
			});
			stringBuilder.append("\n");
		});
		return stringBuilder.toString();
	}
}