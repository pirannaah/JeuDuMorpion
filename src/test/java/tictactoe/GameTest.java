package tictactoe;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testGamePlayAvecVictoireDeterministe() {
        Joueur j1 = new Human("X", null);
        Joueur j2 = new Human("O", null);

        // Factory créant une Manche qui donne la victoire directement à j1
        MancheFactory factory = new MancheFactory() {
            @Override
            public Manche createManche() {
                return new Manche() {
                    @Override
                    public Joueur play(Grille grid, Joueur p1, Joueur p2) {
                        return j1;
                    }
                };
            }
        };

        Game game = new Game(j1, j2, factory);
        assertFalse(game.isWin());

        Joueur winner = game.play();
        assertEquals(j1, winner);
        assertTrue(game.isWin());
        assertEquals(j1, game.winner());
    }

    @Test
    void testWinnerQuandJoueur2Gagne() {
        Joueur j1 = new Human("X", null);
        Joueur j2 = new Human("O", null);

        MancheFactory factory = new MancheFactory() {
            @Override
            public Manche createManche() {
                return new Manche() {
                    @Override
                    public Joueur play(Grille grid, Joueur p1, Joueur p2) {
                        return j2;
                    }
                };
            }
        };

        Game game = new Game(j1, j2, factory);
        Joueur winner = game.play();
        assertEquals(j2, winner);
    }
}
