package tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrilleTest {

    private Grille grille;
    private Joueur joueurX;
    private Joueur joueurO;

    @BeforeEach
    void setUp() {
        grille = new Grille();
        joueurX = new Human("X", null);
        joueurO = new Human("O", null);
    }

    @Test
    void testGrilleInitialeEstVide() {
        assertTrue(grille.isVide());
        assertEquals(9, grille.casesVides().size());
        for (int i = 0; i < 9; i++) {
            assertTrue(grille.estLibre(i));
        }
        assertFalse(grille.isGagnante());
        assertFalse(grille.isPat());
        assertNull(grille.gagnant());
    }

    @Test
    void testJoueEtAnnuleCoup() throws CaseOccupeeException {
        grille.joue(4, joueurX);
        assertFalse(grille.isVide());
        assertFalse(grille.estLibre(4));
        assertEquals(8, grille.casesVides().size());
        assertTrue(grille.casesJoueur(joueurX).contains(4));
        assertTrue(grille.casesAdverses(joueurO).contains(4));

        grille.annuleCoup(4);
        assertTrue(grille.isVide());
        assertTrue(grille.estLibre(4));
    }

    @Test
    void testJoueSurCaseOccupeeLanceException() throws CaseOccupeeException {
        grille.joue(0, joueurX);
        assertThrows(CaseOccupeeException.class, () -> grille.joue(0, joueurO));
    }

    @Test
    void testVictoireLignes() throws CaseOccupeeException {
        // Ligne 0
        grille.joue(0, joueurX);
        grille.joue(1, joueurX);
        grille.joue(2, joueurX);
        assertTrue(grille.isGagnante());
        assertEquals(joueurX, grille.gagnant());

        // Ligne 1
        Grille g1 = new Grille();
        g1.joue(3, joueurO);
        g1.joue(4, joueurO);
        g1.joue(5, joueurO);
        assertTrue(g1.isGagnante());
        assertEquals(joueurO, g1.gagnant());

        // Ligne 2
        Grille g2 = new Grille();
        g2.joue(6, joueurX);
        g2.joue(7, joueurX);
        g2.joue(8, joueurX);
        assertTrue(g2.isGagnante());
        assertEquals(joueurX, g2.gagnant());
    }

    @Test
    void testVictoireColonnes() throws CaseOccupeeException {
        // Colonne 0
        grille.joue(0, joueurX);
        grille.joue(3, joueurX);
        grille.joue(6, joueurX);
        assertTrue(grille.isGagnante());
        assertEquals(joueurX, grille.gagnant());

        // Colonne 1
        Grille g1 = new Grille();
        g1.joue(1, joueurO);
        g1.joue(4, joueurO);
        g1.joue(7, joueurO);
        assertTrue(g1.isGagnante());
        assertEquals(joueurO, g1.gagnant());

        // Colonne 2
        Grille g2 = new Grille();
        g2.joue(2, joueurX);
        g2.joue(5, joueurX);
        g2.joue(8, joueurX);
        assertTrue(g2.isGagnante());
        assertEquals(joueurX, g2.gagnant());
    }

    @Test
    void testVictoireDiagonales() throws CaseOccupeeException {
        // Diagonale principale (0, 4, 8)
        grille.joue(0, joueurX);
        grille.joue(4, joueurX);
        grille.joue(8, joueurX);
        assertTrue(grille.isGagnante());
        assertEquals(joueurX, grille.gagnant());

        // Diagonale secondaire (2, 4, 6)
        Grille g2 = new Grille();
        g2.joue(2, joueurO);
        g2.joue(4, joueurO);
        g2.joue(6, joueurO);
        assertTrue(g2.isGagnante());
        assertEquals(joueurO, g2.gagnant());
    }

    @Test
    void testPartiePat() throws CaseOccupeeException {
        // X O X
        // X O O
        // O X X
        grille.joue(0, joueurX);
        grille.joue(1, joueurO);
        grille.joue(2, joueurX);
        grille.joue(3, joueurX);
        grille.joue(4, joueurO);
        grille.joue(5, joueurO);
        grille.joue(6, joueurO);
        grille.joue(7, joueurX);
        grille.joue(8, joueurX);

        assertFalse(grille.isGagnante());
        assertTrue(grille.isPat());
        assertNull(grille.gagnant());
    }

    @Test
    void testGetLigneColonneDiagonale() throws CaseOccupeeException {
        grille.joue(0, joueurX);
        grille.joue(4, joueurO);
        grille.joue(8, joueurX);

        List<Joueur> ligne0 = grille.getLigne(0);
        assertEquals(1, ligne0.size());
        assertEquals(joueurX, ligne0.get(0));

        List<Joueur> col1 = grille.getColonne(1);
        assertEquals(1, col1.size());
        assertEquals(joueurO, col1.get(0));

        List<Joueur> diag0 = grille.getDiagonale(0);
        assertEquals(3, diag0.size());

        List<Joueur> diag1 = grille.getDiagonale(1);
        assertEquals(1, diag1.size());
        assertEquals(joueurO, diag1.get(0));
    }

    @Test
    void testDisplay() throws CaseOccupeeException {
        grille.joue(0, joueurX);
        assertDoesNotThrow(() -> grille.display());
    }
}
