package com.capgemini.gameoflife.board.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.capgemini.gameoflife.board.GameOfLifeBoard;
import com.capgemini.gameoflife.cell.BorderCell;
import com.capgemini.gameoflife.cell.Cell;
import com.capgemini.gameoflife.cell.GameCell;

public class GameBuilder {
	private Map<BoardPosition, Cell> boardCells = new HashMap<BoardPosition, Cell>();
	
	private GameBuilder() {}

	public static GameBuilder getBuilder() {
		return new GameBuilder();
	}

	public GameBuilder withBorderSizeWidthXHeight(int width, int height) {
	 	Map<BoardPosition, Cell> borderCells = 
	 			new HashMap<BoardPosition, Cell>(((width + height) + 2) * 2);
		IntStream.range(-1, width + 1).forEach(i -> 
				borderCells.put(BoardPosition.of(-1, i), BorderCell.getInstance()));
		IntStream.range(-1, width + 1).forEach(i -> 
				borderCells.put(BoardPosition.of(height, i), BorderCell.getInstance()));
		IntStream.range(0, height).forEach(i ->
				borderCells.put(BoardPosition.of(i, -1), BorderCell.getInstance()));
		IntStream.range(0, height).forEach(i ->
				borderCells.put(BoardPosition.of(i, width), BorderCell.getInstance()));
		boardCells.putAll(borderCells);
		return this;
	}

	public GameBuilder withLivingCellAtPosition(int row, int column) {
		BoardPosition newPosition = BoardPosition.of(row, column);
		boardCells.put(newPosition, GameCell.livingAt(newPosition));
		return this;
	}

	public GameOfLifeBoard build() {
		Set<Cell> initialLivingCells = boardCells.values().stream()
				.filter(cell -> BorderCell.class.equals(cell.getClass()) == false)
				.collect(Collectors.toSet());
		initialLivingCells.forEach(cell -> {
			cell.getPosition().getNeighbourPositions().forEach(pos -> {
			if (boardCells.containsKey(pos) == false) {
				boardCells.put(pos, GameCell.deadAt(pos));
			}
			Cell neighbour = boardCells.get(pos);
			cell.addNeighbour(neighbour);
			neighbour.addNeighbour(cell);
			});
		});
		return new GameOfLifeBoard(boardCells);
	}
}