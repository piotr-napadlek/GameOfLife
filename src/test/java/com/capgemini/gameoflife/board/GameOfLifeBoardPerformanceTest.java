package com.capgemini.gameoflife.board;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import com.capgemini.gameoflife.board.utils.GameBuilder;
import com.capgemini.gameoflife.cell.Cell;
import com.capgemini.gameoflife.cell.LifeState;

public class GameOfLifeBoardPerformanceTest { 
	// any changes in code should not hurt performance more than stated in this test
	// caution! not specifying border will make board infinite!
	@Test(timeout = 8000)
	public void shouldIterateInDecentTimeOnLargeBoardHorizontalLine() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(400, 500)
				.withLivingCellAtPosition(2, 1)
				.withLivingCellAtPosition(2, 2)
				.withLivingCellAtPosition(2, 3)
				.withLivingCellAtPosition(2, 4)
				.withLivingCellAtPosition(2, 5)
				.withLivingCellAtPosition(2, 6)
				.withLivingCellAtPosition(2, 7)
				.withLivingCellAtPosition(2, 8)
				.withLivingCellAtPosition(2, 9)
				.withLivingCellAtPosition(2, 10)
				.withLivingCellAtPosition(2, 11)
				.withLivingCellAtPosition(2, 12)
				.withLivingCellAtPosition(2, 13)
				.withLivingCellAtPosition(2, 14)
				.withLivingCellAtPosition(2, 15)
				.withLivingCellAtPosition(2, 16)
				.withLivingCellAtPosition(2, 17)
				.withLivingCellAtPosition(2, 18)
				.withLivingCellAtPosition(2, 19)
				.withLivingCellAtPosition(2, 20)
				.withLivingCellAtPosition(2, 21)
				.build();
		// when
		gameBoard.processRounds(1000);
		// then
	}

	@Test(timeout = 2000)
	public void shouldIterateInDecentTimeOnLargeBoardExpandingPattern() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withLivingCellAtPosition(20, 20)
				.withLivingCellAtPosition(21, 20)
				.withLivingCellAtPosition(22, 20)
				.withLivingCellAtPosition(23, 21)
				.withLivingCellAtPosition(20, 21)
				.build();
		// when
		gameBoard.processRounds(200);

		long livingCellsCount = gameBoard.getCells().stream()
				.filter(cell -> cell.getState().equals(LifeState.ALIVE))
				.count();
		// then
		long expectedLivingCells = 55;
		assertEquals(expectedLivingCells, livingCellsCount);
	}
	
	@Test (timeout = 5000)
	public void shouldDieAfterTwoGenerationsWhenAllCellsAlive() {
		// given
		GameBuilder builder = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(200, 200);
		IntStream.range(0, 200).forEach(i -> IntStream.range(0, 200)
				.forEach(j -> builder.withLivingCellAtPosition(i, j)));
		CellGameBoard gameBoard = builder.build();
		// when
		gameBoard.processRounds(2);
		// then
		LifeState expectedState = LifeState.DEAD;
		for (Cell cellToTest : gameBoard.getCells()) {
			assertEquals(expectedState, cellToTest.getState());
		}
	}
	
	@Test (timeout = 8000)
	public void shouldHorizontalLineOf3CellsOnLargeBoardAlterToVertical() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(500, 500)
				.withLivingCellAtPosition(2, 1)
				.withLivingCellAtPosition(2, 2)
				.withLivingCellAtPosition(2, 3)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		Cell cellToTest = gameBoard.getCellAt(2, 2);
		LifeState expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(3, 2);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 2);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(2, 3);
		expectedState = LifeState.DEAD;
		assertEquals(expectedState, cellToTest.getState());
	}
	
	@Test (timeout = 10000)
	public void shouldSettleFinalLivingCellsAmountAfter1104GenerationsExpandingPattern() {
		// given
		GameOfLifeBoard gameBoard = GameBuilder.getBuilder()
				.withLivingCellAtPosition(800, 800)
				.withLivingCellAtPosition(800, 801)
				.withLivingCellAtPosition(801, 800)
				.withLivingCellAtPosition(801, 799)
				.withLivingCellAtPosition(802, 800)
				.build();
		
		int generationsToProcess = 1104;
		// when
		gameBoard.processRounds(generationsToProcess);
		// then
		int expectedSurvivedCellsAmount = 116;
		int actualSurvivedCellsAmount = (int)gameBoard.getCells().stream()
				.filter(cell -> cell.getState().equals(LifeState.ALIVE))
				.count();
		assertEquals(expectedSurvivedCellsAmount, actualSurvivedCellsAmount);	
	}
}