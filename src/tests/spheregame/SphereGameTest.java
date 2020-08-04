package tests.spheregame;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import games.spheregame.model.SphereGame;

class SphereGameTest {

	@Test
	void testIsOverTrueCondition() {
		SphereGame game = new SphereGame(3);
		game.setExactState(new String[] {"M","M","M","A","A","-","A",});
		assertTrue(game.isOver());
		game.setExactState(new String[] {"M","M","M","A","-","A","A",});
		assertTrue(game.isOver());
		game.setExactState(new String[] {"M","M","M","-","A","A","A",});
		assertTrue(game.isOver());
		game.setExactState(new String[] {"M","M","-","M","A","A","A",});
		assertTrue(game.isOver());
		game.setExactState(new String[] {"M","-","M","M","A","A","A",});
		assertTrue(game.isOver());
		game.setExactState(new String[] {"-","M","M","M","A","A","A",});
		assertTrue(game.isOver());
	}
	
	@Test
	void testIsOverFalseCondition() {
		SphereGame game = new SphereGame(3);
		game.setExactState(new String[] {"M","M","M","A","A","A","-",});
		assertFalse(game.isOver());
		game.setExactState(new String[] {"M","-","M","A","M","A","A",});
		assertFalse(game.isOver());
		game.setExactState(new String[] {"M","M","-","A","M","A","A",});
		assertFalse(game.isOver());
		game.setExactState(new String[] {"A","M","-","M","-","A","A",});
		assertFalse(game.isOver());
		game.setExactState(new String[] {"M","A","M","M","-","A","A",});
		assertFalse(game.isOver());
		game.setExactState(new String[] {"-","A","M","M","A","A","A",});
		assertFalse(game.isOver());
	}

}
