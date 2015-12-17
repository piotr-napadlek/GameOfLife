package com.capgemini.gameoflife.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capgemini.gameoflife.board.utils.GameBuilder;
import com.capgemini.gameoflife.cell.Cell;
import com.capgemini.gameoflife.cell.LifeState;

public class GameOfLifeBoardOscillatingTest {
	// caution! not specifying border will make board infinite!
	@Test
	public void toadOscillatorTestWithPeriodOf2Generations() {
		// given
		GameOfLifeBoard gameBoard = GameBuilder.getBuilder()
				.withLivingCellAtPosition(20, 20)
				.withLivingCellAtPosition(21, 20)
				.withLivingCellAtPosition(22, 20)
				.withLivingCellAtPosition(21, 21)
				.withLivingCellAtPosition(22, 21)
				.withLivingCellAtPosition(23, 21)
				.build();
		
		int randomMultiplier = (int)(Math.random() * 1000);
		final int patternPeriod = 2;
		// when
		List<Cell> initialState = new ArrayList<Cell>(gameBoard.getCells());
		gameBoard.processRounds(randomMultiplier * patternPeriod);
		// then
		assertEquals("After " + (randomMultiplier * patternPeriod) + " generations: ", 
				initialState.stream().filter(cell -> LifeState.ALIVE.equals(cell.getState())).count(),
				gameBoard.getCells().stream().filter(cell -> LifeState.ALIVE.equals(cell.getState())).count());	
	}
	
	@Test
	public void shouldGoBackToInitialStateAfterRandomPeriodGenerations() {
		// given
		GameOfLifeBoard gameBoard = GameBuilder.getBuilder()
				.withLivingCellAtPosition(10, 10)
				.withLivingCellAtPosition(11, 10)
				.withLivingCellAtPosition(10, 11)
				.withLivingCellAtPosition(11, 11)
				.withLivingCellAtPosition(12, 11)
				.withLivingCellAtPosition(13, 11)
				.withLivingCellAtPosition(14, 11)
				.withLivingCellAtPosition(15, 10)
				.withLivingCellAtPosition(15, 9)
				.withLivingCellAtPosition(14, 9)
				.withLivingCellAtPosition(13, 9)
				
				.withLivingCellAtPosition(10, 13)
				.withLivingCellAtPosition(10, 14)
				.withLivingCellAtPosition(11, 13)
				.withLivingCellAtPosition(11, 14)
				.withLivingCellAtPosition(12, 13)
				.withLivingCellAtPosition(13, 13)
				.withLivingCellAtPosition(14, 13)
				.withLivingCellAtPosition(15, 14)
				.withLivingCellAtPosition(15, 15)
				.withLivingCellAtPosition(14, 15)
				.withLivingCellAtPosition(13, 15)
				.build();
		int randomMultiplier = (int)(Math.random() * 100);
		final int patternPeriod = 14;
		// when
		List<Cell> initialState = new ArrayList<Cell>(gameBoard.getCells());
		gameBoard.processRounds(randomMultiplier * patternPeriod);
		// then
		assertEquals("After " + (randomMultiplier * patternPeriod) + " generations: ", 
				initialState.stream().filter(cell -> LifeState.ALIVE.equals(cell.getState())).count(),
				gameBoard.getCells().stream().filter(cell -> LifeState.ALIVE.equals(cell.getState())).count());		
	}

	@Test
	public void gliderShouldKeepGlidingOnInfiniteBoard() {
		// given
		GameOfLifeBoard gameBoard = GameBuilder.getBuilder()
				.withLivingCellAtPosition(0, 0)
				.withLivingCellAtPosition(0, 1)
				.withLivingCellAtPosition(0, 2)
				.withLivingCellAtPosition(-1, 2)
				.withLivingCellAtPosition(-2, 1)
				.build();
		// when
		gameBoard.processRounds(5000 * 4);
		// then
		long expectedLivingcellsAmount = 5;
		long actualLivingCells = gameBoard.getCells().stream()
				.filter(cell -> LifeState.ALIVE.equals(cell.getState())).count();
		assertEquals(expectedLivingcellsAmount, actualLivingCells);
	}
}