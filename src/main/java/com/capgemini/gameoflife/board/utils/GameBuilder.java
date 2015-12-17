package com.capgemini.gameoflife.board.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.*;

import com.capgemini.gameoflife.board.GameOfLifeBoard;
import com.capgemini.gameoflife.cell.*;

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
		Map<BoardPosition, Cell> initialLivingCells = boardCells.entrySet().stream()
				.filter(cell -> BorderCell.class.equals(cell.getValue().getClass()) == false)
				.collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
		initialLivingCells.entrySet().forEach(cell -> {
			cell.getKey().getNeighbourPositions().forEach(pos -> {
			if (boardCells.containsKey(pos) == false) {
				boardCells.put(pos, GameCell.deadAt(pos));
			}
			Cell neighbour = boardCells.get(pos);
			cell.getValue().addNeighbour(neighbour);
			neighbour.addNeighbour(cell.getValue());
			});
		});
		return new GameOfLifeBoard(boardCells);
	}
}