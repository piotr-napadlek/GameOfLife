package com.capgemini.gameoflife.board;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import com.capgemini.gameoflife.board.utils.GameBuilder;
import com.capgemini.gameoflife.cell.Cell;
import com.capgemini.gameoflife.cell.LifeState;

public class GameOfLifeBoardBasicTest {
	// caution! not specifying border will make board infinite!
	@Test
	public void shouldEmptyBoardRemainEmpty() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(100, 100)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		LifeState expectedState = LifeState.DEAD;
		for (Cell cellToTest : gameBoard.getCells()) {
			assertEquals(expectedState, cellToTest.getState());
		}
	}
	
	@Test
	public void shouldLargeBoardWithRandomSingleElementsBecomeEmpty() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(50, 50)
				.withLivingCellAtPosition(5, 15)
				.withLivingCellAtPosition(10, 20)
				.withLivingCellAtPosition(20, 21)
				.withLivingCellAtPosition(5, 6)
				.withLivingCellAtPosition(49, 40)
				.withLivingCellAtPosition(17, 21)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		LifeState expectedState = LifeState.DEAD;
		for (Cell cellToTest : gameBoard.getCells()) {
			assertEquals(expectedState, cellToTest.getState());
		}
	}

	@Test
	public void shouldOneSingleCellOn1x1BoardDie() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(1, 1)
				.withLivingCellAtPosition(0, 0)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		Cell cellToTest = gameBoard.getCellAt(0, 0);
		LifeState expectedState = LifeState.DEAD;
		assertEquals(expectedState, cellToTest.getState());
	}

	@Test
	public void shouldTwoCellsOn2x2BoardDie() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(2, 2)
				.withLivingCellAtPosition(0, 0)
				.withLivingCellAtPosition(1, 1)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		Cell cellToTest = gameBoard.getCellAt(0, 0);
		LifeState expectedState = LifeState.DEAD;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 1);
		expectedState = LifeState.DEAD;
		assertEquals(expectedState, cellToTest.getState());
	}

	@Test
	public void shouldFourCellsOn2x5BoardSurvive() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(2, 5)
				.withLivingCellAtPosition(0, 0)
				.withLivingCellAtPosition(1, 1)
				.withLivingCellAtPosition(1, 0)
				.withLivingCellAtPosition(0, 1)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		Cell cellToTest = gameBoard.getCellAt(0, 0);
		LifeState expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 1);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());
	}

	@Test
	public void shouldFourCellsInSquareOn15x15BoardSurvive() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(15, 15)
				.withLivingCellAtPosition(0, 0)
				.withLivingCellAtPosition(1, 1)
				.withLivingCellAtPosition(1, 0)
				.withLivingCellAtPosition(0, 1)
				.build();
		// when
		gameBoard.processRounds(1);
		// then
		Cell cellToTest = gameBoard.getCellAt(0, 0);
		LifeState expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 1);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());
	}

	@Test
	public void shouldHorizontalLineOf3CellsOnLargeBoardNotChange1000Generations() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(3, 3)
				.withLivingCellAtPosition(1, 0)
				.withLivingCellAtPosition(1, 1)
				.withLivingCellAtPosition(1, 2)
				.build();
		// when
		gameBoard.processRounds(1000);
		// then
		Cell cellToTest = gameBoard.getCellAt(1, 0);
		LifeState expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 1);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(1, 2);
		expectedState = LifeState.ALIVE;
		assertEquals(expectedState, cellToTest.getState());

		cellToTest = gameBoard.getCellAt(2, 1);
		expectedState = LifeState.DEAD;
		assertEquals(expectedState, cellToTest.getState());
	}

	@Test
	public void shouldHorizontalLineOf3CellsOnLargeBoardAlter3Generations() {
		// given
		CellGameBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(500, 200)
				.withLivingCellAtPosition(2, 1)
				.withLivingCellAtPosition(2, 2)
				.withLivingCellAtPosition(2, 3)
				.build();
		// when
		gameBoard.processRounds(3);
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

	@Test
	public void shouldCornerCellsAfterOneGenerationSurviveWhenAllCellsAlive() {
		// given
		GameBuilder builder = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(100, 100);
		IntStream.range(0, 100).forEach(i -> IntStream.range(0, 100)
				.forEach(j -> builder.withLivingCellAtPosition(i, j)));
		CellGameBoard gameBoard = builder.build();
		// when
		gameBoard.processRounds(1);
		// then
		LifeState expectedState = LifeState.ALIVE;
		Cell cellToTest = gameBoard.getCellAt(0, 0);
		assertEquals(expectedState, cellToTest.getState());
		
		cellToTest = gameBoard.getCellAt(99, 0);
		assertEquals(expectedState, cellToTest.getState());
		
		cellToTest = gameBoard.getCellAt(0, 99);
		assertEquals(expectedState, cellToTest.getState());
		
		cellToTest = gameBoard.getCellAt(99, 99);
		assertEquals(expectedState, cellToTest.getState());
		
		expectedState = LifeState.DEAD;
		cellToTest = gameBoard.getCellAt(1, 1);
		assertEquals(expectedState, cellToTest.getState());
	}
}