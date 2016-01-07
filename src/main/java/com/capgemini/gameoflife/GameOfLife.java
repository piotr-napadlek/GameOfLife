package com.capgemini.gameoflife;

import java.util.stream.IntStream;

import org.apache.log4j.*;

import com.capgemini.gameoflife.board.GameOfLifeBoard;
import com.capgemini.gameoflife.board.utils.GameBuilder;

public class GameOfLife {
	private static final Logger logger = Logger.getLogger(GameOfLife.class.getName());

	public static void main(String[] args) {
		BasicConfigurator.configure();

		GameOfLifeBoard gameBoard = GameBuilder.getBuilder()
				.withBorderSizeWidthXHeight(40, 30)
				.withLivingCellAtPosition(15, 20)
				.withLivingCellAtPosition(16, 20)
				.withLivingCellAtPosition(17, 20)
				.withLivingCellAtPosition(15, 21)
				.withLivingCellAtPosition(16, 19)
				.build();

		IntStream.range(0, 320).forEach(i -> {
			logger.info(gameBoard);
			gameBoard.processRounds(1);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// this comment should trigger an automated Jenkins build
		// based on github repository
		
	}
}