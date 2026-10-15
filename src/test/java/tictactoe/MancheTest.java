package tictactoe;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MancheTest {

    @Test
    void testCreateMancheViaFactory() {
        MancheFactory factory = new MancheFactory();
        Manche manche = factory.createManche();
        assertNotNull(manche);
    }

    @Test
    void testPlayMancheEntreDeuxIA() {
        IA ia1 = new IA("X", new Random(42));
        IA ia2 = new IA("O", new Random(42));
        ia1.setOpponent(ia2);
        ia2.setOpponent(ia1);

        Manche manche = new Manche();
        Grille grille = new Grille();
        Joueur gagnant = manche.play(grille, ia1, ia2);

        // La partie doit se terminer soit par un gagnant, soit par un match nul
        assertTrue(grille.isGagnante() || grille.isPat());
        if (grille.isGagnante()) {
            assertNotNull(gagnant);
        } else {
            assertNull(gagnant);
        }
    }
}
