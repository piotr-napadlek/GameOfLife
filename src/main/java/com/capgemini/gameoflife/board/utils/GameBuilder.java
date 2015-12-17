package com.capgemini.gameoflife.board.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.capgemini.gameoflife.board.GameOfLifeBoard;
import com.capgemini.gameoflife.cell.*;

public class GameBuilder {
	private BiMap<BoardPosition, Cell> boardCells;
	
	private GameBuilder() { 
		boardCells = new ConcurrentBiHashMap<BoardPosition, Cell>();
	}

	public static GameBuilder getBuilder() {
		return new GameBuilder();
	}

	public GameBuilder withBorderSizeWidthXHeight(int width, int height) {
	 	Map<BoardPosition, Cell> borderCells = 
	 			new HashMap<BoardPosition, Cell>(((width + height) + 2) * 2);
		IntStream.range(-1, width + 1).forEach(i -> 
				borderCells.put(new BoardPosition(-1, i), BorderCell.getInstance()));
		IntStream.range(-1, width + 1).forEach(i -> 
				borderCells.put(new BoardPosition(height, i), BorderCell.getInstance()));
		IntStream.range(0, height).forEach(i ->
				borderCells.put(new BoardPosition(i, -1), BorderCell.getInstance()));
		IntStream.range(0, height).forEach(i ->
				borderCells.put(new BoardPosition(i, width), BorderCell.getInstance()));
		boardCells.putAll(borderCells);
		
		return this;
	}

	public GameBuilder withLivingCellAtPosition(int row, int column) {
		boardCells.put(new BoardPosition(row, column), new LivingCell());
		return this;
	}

	public GameOfLifeBoard build() {
		Set<Cell> initialLivingCells = boardCells.valueSet().stream()
				.filter(cell -> BorderCell.class.equals(cell.getClass()) == false)
				.collect(Collectors.toSet());
		initialLivingCells.forEach(cell -> {
			boardCells.getByValue(cell).getNeighbourPositions().forEach(pos -> {
			if (boardCells.containsKey(pos) == false) {
				boardCells.put(pos, new DeadCell());
			}
			Cell neighbour = boardCells.get(pos);
			cell.addNeighbour(neighbour);
			neighbour.addNeighbour(cell);
			});
		});
		return new GameOfLifeBoard(boardCells);
	}
}