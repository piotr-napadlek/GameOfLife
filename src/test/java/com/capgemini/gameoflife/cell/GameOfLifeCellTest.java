package com.capgemini.gameoflife.cell;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

public class GameOfLifeCellTest {

	@Test
	public void shouldBeDeadAfterEvaluationWhenAlive() {
		// given
		Cell cell = new LivingCell();
		// when
		IntStream.range(0, 4).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 4).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.DEAD, state);
	}

	@Test
	public void shouldBeAliveAfterEvaluationWhenAlive() {
		// given
		Cell cell = new LivingCell();
		// when
		IntStream.range(0, 3).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 2).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.ALIVE, state);
	}

	@Test
	public void shouldBeAliveAfterEvaluationWhenDead() {
		// given
		Cell cell = new DeadCell();
		// when
		IntStream.range(0, 3).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 2).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.ALIVE, state);
	}

	@Test
	public void shouldNotChangeStateWhenAliveAnd3NeighboursAlive() {
		// given
		Cell cell = new LivingCell();
		// when
		IntStream.range(0, 3).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 5).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.ALIVE, state);
	}
	
	@Test
	public void shouldNotChangeStateWhenAliveAnd2NeighboursAlive() {
		// given
		Cell cell = new LivingCell();
		// when
		IntStream.range(0, 2).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 6).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.ALIVE, state);
	}

	@Test
	public void shouldNotChangeStateWhenDeadAnd1NeighbourAlive() {
		// given
		Cell cell = new DeadCell();
		// when
		IntStream.range(0, 1).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 2).forEach(i -> cell.addNeighbour(new DeadCell()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.DEAD, state);
	}

	@Test
	public void shouldDealWithBorderCellsAsNeighbours() {
		// given
		Cell cell = new LivingCell();
		// when
		IntStream.range(0, 1).forEach(i -> cell.addNeighbour(new LivingCell()));
		IntStream.range(0, 2).forEach(i -> cell.addNeighbour(new DeadCell()));
		IntStream.range(0, 5).forEach(i -> cell.addNeighbour(BorderCell.getInstance()));
		cell.evaluateNextGeneration();
		cell.nextGeneration();
		// then
		LifeState state = cell.getState();
		assertEquals(LifeState.DEAD, state);
	}
}
