package Morpion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrilleToolsUnitTest {

    // La méthode ligneGagnante à un argument (tableau de Joueur) fait appel à la méthode ligneGagnante à 2 arguments,
    // on commence donc par le test de cette deuxième méthode.

    @Test
    public void testLigneGagnanteSurLigneConnue() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, null, null,
                x, null, x,
                x, x, null,
                x, x, x
        }; // le tableau de Joueur n'est pas limité à 3 lignes (dans le jeux on utilise que les 3 premières lignes mais
        // la fonction ligneGagnante à 2 arguments peut utiliser plus de lignes.
        // Les 4 cas mentionnés dans chacune des lignes permettent de couvrir les conditions dans la décision (chaque
        // condition élémentaire est testée à vrai et à faux au moins une foi)
        Assertions.assertFalse(GrilleTools.ligneGagnante(0, tabJoueurs));
        Assertions.assertFalse(GrilleTools.ligneGagnante(1, tabJoueurs));
        Assertions.assertFalse(GrilleTools.ligneGagnante(2, tabJoueurs));
        Assertions.assertTrue(GrilleTools.ligneGagnante(3, tabJoueurs));
    }

    // Les tests sur ligneGagnante à 1 argument vont s'intéresser à garantir que l'itération considère bien toutes les
    // lignes de la grille. Les erreurs dans une itération sont fréquemment associées aux bornes de l'itération, ne pas
    // considerer le premier élément (pb d'initialisation de l'itérateur), ne pas considérer le dernier élément (arrêt
    // prématuré de l'itération) ou sortir du tableau (arrêt trop tardif de l'itération).
    // Un test avec la première ligne gagnante permet de s'assurer que l'itération commence à la première ligne
    // Un test avec la dernière ligne gagnante permet de s'assurer que l'itération va jusqu'à la dernière ligne
    // Pour la vérification de l'arrêt tardif, on peut soit ne rien faire et récupérer une exception de sortie de tableau
    // ou faire un test dans laquelle la 4° ligne est gagnante (mais ne doit pas être détectée car non atteinte)
    @Test
    public void testLigneGagnanteSurGrilleAvec1ereLigneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                x, x, x,
                null, null, null,
                x, null, x
        };
        Assertions.assertTrue(GrilleTools.ligneGagnante(tabJoueurs));
    }

    @Test
    public void testLigneGagnanteSurGrilleAvecDerniereLigneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, null, null,
                x, null, x,
                x, x, x
        };
        Assertions.assertTrue(GrilleTools.ligneGagnante(tabJoueurs));
    }

    @Test
    public void testLigneGagnanteSurGrilleSansLigneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, null, null,
                x, x, null,
                x, null, x,
                x, x, x   // une ligne gagnante ici permet de s'assurer que l'itération
                          // ne va pas au dela de la 3° ligne
        };
        Assertions.assertFalse(GrilleTools.ligneGagnante(tabJoueurs));
    }

    // De manière similaire, on teste colonneGagnante.
    // On ne peut toutefois pas ajouter une 4° colonne, il faut donc 2 grilles pour tester les 4 configurations de colonneGagnante
    @Test
    public void testColonneGagnanteSurColonneConnue() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, x, x,
                null, null, x,
                null, null, null
        };
        Assertions.assertFalse(GrilleTools.colonneGagnante(0, tabJoueurs));
        Assertions.assertFalse(GrilleTools.colonneGagnante(1, tabJoueurs));
        Assertions.assertFalse(GrilleTools.colonneGagnante(2, tabJoueurs));
        Joueur[] tabJoueurs2 = new Joueur[]{
                x, x, x,
                x, null, x,
                x, null, null
        };
        Assertions.assertTrue(GrilleTools.colonneGagnante(0, tabJoueurs2));
    }

    @Test
    public void testColonneGagnanteSurGrilleAvec1ereColonneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                x, x, null,
                x, null, null,
                x, null, x
        };
        Assertions.assertTrue(GrilleTools.colonneGagnante(tabJoueurs));
    }

    @Test
    public void testColonneGagnanteSurGrilleAvecDerniereColonneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, null, x,
                x, null, x,
                x, x, x
        };
        Assertions.assertTrue(GrilleTools.colonneGagnante(tabJoueurs));
    }

    @Test
    public void testColonneGagnanteSurGrilleSansColonneGagnante() {
        Joueur x = new Joueur("X");
        Joueur[] tabJoueurs = new Joueur[]{
                null, null, null,
                x, x, null,
                x, null, x,
                x   // L'ajout d'un x dans grid[9] permet de faire que la colonne 3 (coordonnéés (0,3), (1,3), (2,3)) soit
                //   gagnante, elle est constitué de grid[3], grid[6], et grid[9]
        };
        Assertions.assertFalse(GrilleTools.ligneGagnante(tabJoueurs));
    }

    // Le test de diagonale gagnante nécessite 3 tests pour atteindre la couverture de toutes les décisions :
    // - un cas avec la diagonale descendante gagnante
    // - un cas avec la diagonale ascendante gagnante
    // - un cas sans diagonale gagnante
    // Atteindre la couverture de toutes les conditions dans les décisions nécessite d'ajouter 2 cas supplémentaires sans diagonale gagnante

    @Test
    public void testDiagonaleDescendanteGagnante() {
        // couvre les 3 conditions de la première décision à true
        Joueur X = new Joueur("X");
        Joueur[] grille = new Joueur[]{
                X, null, X,
                null, X, null,
                null, null, X,
        };
        Assertions.assertTrue(GrilleTools.diagonaleGagnante(grille));
    }
    @Test
    public void testDiagonaleAscendanteGagnante() {
        // couvre la 1ere condition de la 1ere décision à false (1ere case de la grille null)
        // couvre les 3 conditions de la 2ème décision à true
        Joueur X = new Joueur("X");
        Joueur[] grille = new Joueur[]{
                null, null, X,
                null, X, null,
                X, null, X,
        };
        Assertions.assertTrue(GrilleTools.diagonaleGagnante(grille));
    }

    @Test
    public void testDiagonaleSansGagnante1() {
        // couvre la 1ere condition de la 1ere décision à true et la deuxième à false (case centrale de la grille null)
        // couvre la 1ere condition de la 2eme décision à false (case grid[2] à null)
        Joueur X = new Joueur("X");
        Joueur[] grille = new Joueur[]{
                X, null, null,
                null, null, null,
                null, null, X,
        };
        Assertions.assertFalse(GrilleTools.diagonaleGagnante(grille));
    }

    @Test
    public void testDiagonaleSansGagnante2() {
        // couvre la 1ere et la 2eme condition de la 1ere décision à true et la 3eme à false (dernière case de la grille null)
        // couvre la 1ere et la 2eme condition de la 2eme décision à true et la 3eme à false (case grid[6] à null)
        // les 3 conditions de la 1ere décision sont couvertes à vrai et à faux par ces 4 tests
        Joueur X = new Joueur("X");
        Joueur[] grille = new Joueur[]{
                X, null, X,
                null, X, null,
                null, null, null,
        };
        Assertions.assertFalse(GrilleTools.diagonaleGagnante(grille));
    }

    @Test
    public void testDiagonaleSansGagnante3() {
        // il reste la 2eme condition de la 2eme décision qui n'a été couverte qu'à vrai, il faut la couvrir à faux
        // il faut pour cela que la case centrale soit nulle et que la case grid[2] ne le soit pas.
        Joueur X = new Joueur("X");
        Joueur[] grille = new Joueur[]{
                X, null, X,
                null, null, null,
                null, null, null,
        };
        Assertions.assertFalse(GrilleTools.diagonaleGagnante(grille));
    }


}
