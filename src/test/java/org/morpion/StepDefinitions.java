package org.morpion;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Etque;
import io.cucumber.java.fr.Quand;
import org.junit.jupiter.api.Assertions;
import tictactoe.*;

import java.util.*;

public class StepDefinitions {

    private Grille grille;
    private Joueur joueurX;
    private Joueur joueurO;
    private IA ia;
    private Joueur gagnant;


    // =========================================================
    // Etantdonné / initialisation
    // =========================================================

    /** Initialise une grille vide avec les joueurs et l'IA */
    @Etantdonné("une grille vide")
    public void une_grille_vide() {
        grille = new Grille();
        joueurX = new Human("X", null);
        joueurO = new Human("O", null);
        ia = new IA("X", new Random());
        ia.setOpponent(joueurO);
        joueurO.setOpponent(ia);
        joueurX.setOpponent(joueurO);
    }

    /** Initialise une grille à partir d'une table donnée */
    @Etantdonné("une partie en cours avec la grille suivante")
    public void une_partie_en_cours_avec_la_grille_suivante(DataTable table) throws Exception {
        grille = new Grille();
        joueurX = new Human("X", null);
        joueurO = new Human("O", null);
        ia = new IA("X", new Random());
        ia.setOpponent(joueurO);
        joueurO.setOpponent(ia);
        joueurX.setOpponent(joueurO);
        initialiserGrilleDepuisTable(table);
    }



    // =========================================================
    // Quand / actions du jeu
    // =========================================================

    /** Remplit la grille avec les coups de l’adversaire en défense */
    @Quand("l’adversaire joue en défense")
    public void l_adversaire_joue_en_defense(DataTable table) throws Exception {
        initialiserGrilleDepuisTable(table);
    }

    /** L’IA joue son coup sur la grille actuelle */
    @Etque("c’est au tour de l’IA")
    public void que_c_est_au_tour_de_l_ia() {
        ia.joue(grille);
    }

    /** Un joueur joue à une position donnée ; met à jour le gagnant si nécessaire */
    @Quand("le joueur {string} joue en position \\({int},{int})")
    public void le_joueur_joue_en_position(String symbole, Integer ligne, Integer colonne) throws Exception {
        Joueur joueur = symbole.equals("X") ? joueurX : joueurO;
        int index = (ligne - 1) * 3 + (colonne - 1);
        if (!grille.estLibre(index)) {
            throw new RuntimeException("Case déjà prise");
        }
        grille.joue(index, joueur);
        if (grille.isGagnante()) {
            gagnant = grille.gagnant();
        }
    }


    // =========================================================
    // Alors / vérifications
    // =========================================================

    /** Vérifie que la grille correspond à celle attendue */
    @Alors("la grille devient la suivante")
    public void la_grille_devient_la_suivante(DataTable table) throws Exception {
        Grille grilleAttendue = new Grille();
        List<List<String>> rows = table.asLists(String.class);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                String symbole = rows.get(i).get(j);
                if (symbole.equals("X")) {
                    grilleAttendue.joue(i * 3 + j, joueurX);
                } else if (symbole.equals("O")) {
                    grilleAttendue.joue(i * 3 + j, joueurO);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Joueur attendu = grilleAttendue.getLigne(i).size() > j ? grilleAttendue.getLigne(i).get(j) : null;
                Joueur reel = grille.getLigne(i).size() > j ? grille.getLigne(i).get(j) : null;
                String attenduSymbole = attendu == null ? "." : attendu.toString();
                String reelSymbole = reel == null ? "." : reel.toString();
                Assertions.assertEquals(attenduSymbole, reelSymbole, "Difference a la case (" + (i+1) + "," + (j+1) + ")");
            }
        }
    }

    /** Vérifie que l’IA dispose d’une double menace sur deux cases données */
    @Alors("l’IA dispose d’une double menace possible en \\({int},{int}) et \\({int},{int})")
    public void l_ia_dispose_d_une_double_menace(Integer ligne1, Integer col1, Integer ligne2, Integer col2) {
        List<int[]> doublesMenaces = new ArrayList<>();
        for (Integer index : grille.casesVides()) {
            int l = index / 3;
            int c = index % 3;
            int lignesGagnantes = 0;

            List<Joueur> ligne = grille.getLigne(l);
            if (ligne.size() == 2 && ligne.stream().allMatch(j -> j == ia)) lignesGagnantes++;

            List<Joueur> colonne = grille.getColonne(c);
            if (colonne.size() == 2 && colonne.stream().allMatch(j -> j == ia)) lignesGagnantes++;

            if (l == c) {
                List<Joueur> diag0 = grille.getDiagonale(0);
                if (diag0.size() == 2 && diag0.stream().allMatch(j -> j == ia)) lignesGagnantes++;
            }
            if (l + c == 2) {
                List<Joueur> diag1 = grille.getDiagonale(1);
                if (diag1.size() == 2 && diag1.stream().allMatch(j -> j == ia)) lignesGagnantes++;
            }

            if (lignesGagnantes >= 2) {
                doublesMenaces.add(new int[]{l + 1, c + 1});
            }
        }
        boolean contient1 = doublesMenaces.stream().anyMatch(m -> m[0] == ligne1 && m[1] == col1);
        boolean contient2 = doublesMenaces.stream().anyMatch(m -> m[0] == ligne2 && m[1] == col2);

        Assertions.assertTrue(contient1 && contient2, "La double menace attendue n’est pas présente");
    }

    /** Vérifie que l’IA a joué au centre de la grille */
    @Alors("l’IA joue au centre")
    public void lIAJoueAuCentre(DataTable table) {
        List<List<String>> expected = table.asLists(String.class);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String expectedCell = expected.get(i).get(j);
                Joueur cell = grille.getLigne(i).size() > j ? grille.getLigne(i).get(j) : null;
                String actual = cell == null ? "." : cell.toString();
                Assertions.assertEquals(expectedCell, actual);
            }
        }
    }

    /** Vérifie que le joueur attendu est déclaré gagnant */
    @Alors("le joueur {string} est déclaré gagnant")
    public void le_joueur_est_declare_gagnant(String symboleAttendu) {
        Assertions.assertNotNull(gagnant, "Pas de gagnant trouvé");
        Assertions.assertEquals(symboleAttendu, gagnant.toString());
    }

    /** Vérifie qu’aucun joueur n’est déclaré gagnant */
    @Alors("aucun joueur n’est déclaré gagnant")
    public void aucun_joueur_n_est_declare_gagnant() {
        Assertions.assertNull(gagnant, "Gagnant trouvé alors qu’il ne devrait pas y en avoir");
    }

    /** Vérifie la grille après le coup de l’IA */
    @Alors("l’IA joue")
    public void l_ia_joue(io.cucumber.datatable.DataTable table) {
        List<List<String>> expected = table.asLists(String.class);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String expectedCell = expected.get(i).get(j);
                Joueur cell = grille.getLigne(i).size() > j ? grille.getLigne(i).get(j) : null;
                String actual = cell == null ? "." : cell.toString();
                Assertions.assertEquals(expectedCell, actual);
            }
        }
    }

    /** Vérifie que l’IA choisit une action prioritaire (victoire avant blocage) */
    @Alors("l’IA joue le coup gagnant prioritaire")
    public void l_ia_joue_le_coup_gagnant_prioritaire() {
        Joueur gagnantApresCoup = grille.gagnant();
        Assertions.assertNotNull(gagnantApresCoup,
                "L’IA n’a pas joué son coup gagnant alors qu’elle le pouvait");
        Assertions.assertEquals(ia.toString(), gagnantApresCoup.toString(),
                "L’IA n’a pas priorisé la victoire alors qu’une opportunité existait");
    }


    // =========================================================
    /** Initialise la grille à partir d’une DataTable */
    private void initialiserGrilleDepuisTable(DataTable table) throws Exception {
        grille = new Grille();
        List<List<String>> rows = table.asLists();
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                String symbole = rows.get(i).get(j);
                if (symbole.equals("X")) {
                    grille.joue(i * 3 + j, ia != null ? ia : joueurX);
                } else if (symbole.equals("O")) {
                    grille.joue(i * 3 + j, joueurO);
                }
            }
        }
    }

}
