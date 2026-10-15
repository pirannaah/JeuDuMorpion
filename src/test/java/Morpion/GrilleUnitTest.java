package Morpion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrilleUnitTest {

    // Déterminons combien de test sont nécessaire pour couvrir la méthode estGagnée. La méthode ne comportant qu'une
    // seule instruction - return (expression booléenne) - un test permet d'obtenir la couverture tous-chemins. Toutefois,
    // afin d'assurer une couverture fonctionnelle, il est utile de vérifier que la fonction peut répondre true et false.
    // La combinaison des conditions de victoire avec des || incite à vérifier 3 cas gagnants et un cas perdant qui permettent
    // de s'assurer qu'il n'y a pas d'erreur dans l'expression booléenne

    // La fonction estGagnee fait appel à 3 fonctions. La logique voudrait que l'on commence par tester chacune de
    // ces fonctions avant de revenir sur les tests de estGagnée. Pour des raisons de progression pédagogique, nous testerons
    // les 3 fonctions plus tard.

    // Vérifier que la fonction estGagnee renvoie faux lorsqu'elle est appelée sur une grille vide
    @Test
    public void testEstGagnee() {
        // define
        Grille grille = new Grille();
        //when
        boolean resultat = grille.estGagnee();
        // then
        Assertions.assertFalse(resultat);
    }

    @Test
    public void testEstGagneeSurLigneGagnante() {
        // define
        Grille grille = new Grille();
        Joueur x = new Joueur("X");  // Afin de jouer dans chaque case de la ligne avec le même joueur, il faut
        // une variable ayant le joueur pour valeur
        grille.joue(x, new Coordonnee(0, 0));
        grille.joue(x, new Coordonnee(0, 1));
        grille.joue(x, new Coordonnee(0, 2));
        //when
        boolean resultat = grille.estGagnee();
        // then
        Assertions.assertTrue(resultat);

    }

    @Test
    public void testEstGagneeSurColonneGagnante() {
        // define
        Grille grille = new Grille();
        Joueur x = new Joueur("X");
        grille.joue(x, new Coordonnee(0, 1));
        grille.joue(x, new Coordonnee(1, 1));
        grille.joue(x, new Coordonnee(2, 1));
        //when
        boolean resultat = grille.estGagnee();
        // then
        Assertions.assertTrue(resultat);
    }

    @Test
    public void testEstGagneeSurDiagonaleGagnante() {
        // define
        Grille grille = new Grille();
        Joueur x = new Joueur("X");
        grille.joue(x, new Coordonnee(0, 0));
        grille.joue(x, new Coordonnee(1, 1));
        grille.joue(x, new Coordonnee(2, 2));
        //when
        boolean resultat = grille.estGagnee();
        // then
        Assertions.assertTrue(resultat);
    }
    // La fonction estGagnee est testée (au test des fonctions appelées près)

    // La fonction estPat qui est utilisée dans la condition d'arrêt du jeu, retourne vrai s'il n'est plus possible de
    // jouer dans la grille et qu'il n'y a pas de gagnant. Pour ce faire, elle commence par vérifier s'il reste des
    // cases vides dans la grille, puis s'il n'en reste aucune vérifie si la grille n'est pas gagnée
    // Pour couvrir les 2 instructions return (ligne 18 et 20 de la classe Grille) il faut vérifier que la fonction
    // renvoie False sur une grille non pleine (ligne 18 - classe Grille) et qu'elle renvoie True sur une grille pleine
    // et non gagnante. Le choix d'utilser une grille non gagnante pour exercer la ligne 20 de Grille est motivé par l'idée
    // que la ligne 20 est la seule où le retour peut être True assurant ainsi de ne pas être sorti par la ligne 18. Le
    // choix d'une grille pleine et gagnante ne permet pas d'assurer ce point.
    @Test
    public void testEstPat() {
        Grille grille = new Grille();
        grille.joue(new Joueur("a"), new Coordonnee(0, 0));
        grille.joue(new Joueur("b"), new Coordonnee(0, 1));
        grille.joue(new Joueur("c"), new Coordonnee(0, 2));
        grille.joue(new Joueur("d"), new Coordonnee(1, 0));
        grille.joue(new Joueur("e"), new Coordonnee(1, 1));
        grille.joue(new Joueur("f"), new Coordonnee(1, 2));
        grille.joue(new Joueur("h"), new Coordonnee(2, 1));
        grille.joue(new Joueur("g"), new Coordonnee(2, 0));
        Assertions.assertFalse(grille.estPat());
        grille.joue(new Joueur("i"), new Coordonnee(2, 2));
        Assertions.assertTrue(grille.estPat());
    }

    // La méthode casesVides construit la liste des coordonnées des cases vides de la grille. Pour vérifier qu'elle fonctionne
    // correctement, on choisi une approche aux limites (une grille vide, une grille pleine) auquel on ajoute un cas intermédiaire.
    // La comparaison du résultat de la fonction à un oracle de test nécessite de comparer 2 listes de Coordonnées. La comparaison
    // par assertEquals de 2 listes active la comparaison par equals des éléments de même rang des 2 listes. Pour que la
    // comparaison des coordonnées puissent se faire, nous générons une méthode equals dans la classe Coordonnées.

    @Test
    public void testCasesVides() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("a");
        List<Coordonnee> toutesLesCases = new ArrayList<>(Arrays.asList(
                new Coordonnee(0, 0), new Coordonnee(0, 1),
                new Coordonnee(0, 2), new Coordonnee(1, 0),
                new Coordonnee(1, 1), new Coordonnee(1, 2),
                new Coordonnee(2, 0), new Coordonnee(2, 1),
                new Coordonnee(2, 2)));
        Assertions.assertEquals(toutesLesCases, grille.casesVides());
        // les 2 lignes suivantes sont ajoutées après coup pour tester la fonction joue de Grille. On vérifie ici
        // que la fonction joue remplie bien la case qui lui est passé en paramètre et pas une autre.
        grille.joue(joueur, new Coordonnee(2, 1));
        Assertions.assertFalse(grille.casesVides().contains(new Coordonnee(2, 1)));
        grille.joue(joueur, new Coordonnee(0, 0));
        grille.joue(joueur, new Coordonnee(2, 0));
        grille.joue(joueur, new Coordonnee(1, 1));
        grille.joue(joueur, new Coordonnee(1, 2));
        grille.joue(joueur, new Coordonnee(0, 1));
        Assertions.assertEquals(Arrays.asList(new Coordonnee(0, 2),
                        new Coordonnee(1, 0),
                        new Coordonnee(2, 2)),
                grille.casesVides());
        grille.joue(joueur, new Coordonnee(1, 0));
        grille.joue(joueur, new Coordonnee(0, 2));
        grille.joue(joueur, new Coordonnee(2, 2));
        Assertions.assertTrue(grille.casesVides().isEmpty());
    }

    // Après la méthode casesVides, on se pose la question des tests à mener sur la méthode joue. Sur cette méthode
    // on veut vérifier qu'elle joue bien au bon endroit et avec le bon joueur. Pour vérifier la position jouée, on
    // utilise casesVides qui nous permet de vérifier après avoir joué un coup que la case n'est plus dans les cases vides.
    // On ajoute les lignes 119 à 112 dans le test précédent pour vérifier la position joué.
    // Pour ce qui est de vérifier que la méthode utilise le bon joueur, il faut dans un premier temps constater qu'aucune
    // méthode de la classe ne permet de récupérer le contenu d'une case particulière, mais que la méthode gagnant permet
    // sous certaines conditions de récupérer le motif associé au joueur d'une case particulière. Le fait que la méthode
    // gagnant retourne une chaine de caractère n'est pas en harmonie avec le reste de la classe. La classe Grille travaille
    // avec des objets Joueur sur toutes ses méthodes sauf la méthode gagnant. Afin de revenir au même niveau d'abstraction
    // sur la méthode gagnant, nous allons modifier le type de retour de la méthode pour retourner un Joueur. La récupération
    // du motif du joueur se fera à l'extérieur de la méthode, au point d'appel de la méthode. Cette modification permet
    // en plus de rendre la Grille indépendante de la structure interne du Joueur, on peut décider de changer l'attribut de
    // Joueur pour mettre une image sans qu'il n'y ait d'impact sur Grille.
    // Après refactoring, la méthode gagnant nous permet donc de récupérer sous certaines conditions (victoire), le joueur
    // contenu dans une case particulière. Ceci nous permet donc de tester que la méthode joue utilise bien le joueur qui
    // lui est passé en paramètre pour jouer. Les tests de joue se font donc au travers des tests de casesVides et gagnant.


    // La méthode gagnant comporte 7 instructions return et le premier return peut être atteint selon 2 conditions. On proposera
    // donc 8 tests pour couvrir chaque return selon les conditions de leur accessibilité
    // - 1ere ligne ou 1ere colonne gagnante : 2 tests
    // - 2° ligne gagnante : 1 test
    // - 3° ligne gagnante : 1 test
    // - 2° colonne gagnante : 1 test
    // - 3° colonne gagnante : 1 test
    // - une diagonale gagnante : 1 test
    // - aucune configuration gagnante : 1 test

    @Test
    public void testGagnantSurPremiereLigneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(0, 0));
        grille.joue(joueur, new Coordonnee(0, 1));
        grille.joue(joueur, new Coordonnee(0, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurPremiereColonneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(0, 0));
        grille.joue(joueur, new Coordonnee(1, 0));
        grille.joue(joueur, new Coordonnee(2, 0));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurDeuxiemeLigneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(1, 0));
        grille.joue(joueur, new Coordonnee(1, 1));
        grille.joue(joueur, new Coordonnee(1, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurTroisiemeLigneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(2, 0));
        grille.joue(joueur, new Coordonnee(2, 1));
        grille.joue(joueur, new Coordonnee(2, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurDeuxiemeColonneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(0, 1));
        grille.joue(joueur, new Coordonnee(1, 1));
        grille.joue(joueur, new Coordonnee(2, 1));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurTroisiemeColonneGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(0, 2));
        grille.joue(joueur, new Coordonnee(1, 2));
        grille.joue(joueur, new Coordonnee(2, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurDiagonaleGagnante() {
        Grille grille = new Grille();
        Joueur joueur = new Joueur("X");
        grille.joue(joueur, new Coordonnee(0, 0));
        grille.joue(joueur, new Coordonnee(1, 1));
        grille.joue(joueur, new Coordonnee(2, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testGagnantSurGrilleSansGagnant() {
        Grille grille = new Grille();
        Assertions.assertNull(grille.gagnant());
    }

    // On peut choisir de regrouper ces 8 tests au sein d'une seule méthode de test
    @Test
    public void testGagnant() {
        Grille grille = new Grille();
        Assertions.assertNull(grille.gagnant()); // test le retour en l'absence de gagnant

        Joueur joueur = new Joueur("X");
        // test sur les 3 lignes
        for (int ligne = 0; ligne < 3; ligne++) {
            grille = new Grille();
            grille.joue(joueur, new Coordonnee(ligne, 0));
            grille.joue(joueur, new Coordonnee(ligne, 1));
            grille.joue(joueur, new Coordonnee(ligne, 2));
            Assertions.assertEquals(joueur, grille.gagnant());
        }
        // test sur les 3 colonnes
        for (int colonne = 0; colonne < 3; colonne++) {
            grille = new Grille();
            grille.joue(joueur, new Coordonnee(0, colonne));
            grille.joue(joueur, new Coordonnee(1, colonne));
            grille.joue(joueur, new Coordonnee(2, colonne));
            Assertions.assertEquals(joueur, grille.gagnant());
        }
        // test sur la diagonale
        grille = new Grille();
        grille.joue(joueur, new Coordonnee(0, 0));
        grille.joue(joueur, new Coordonnee(1, 1));
        grille.joue(joueur, new Coordonnee(2, 2));
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testDisplay() {
        Grille grille = new Grille();
        grille.joue(new Joueur("X"), new Coordonnee(0, 0));
        Assertions.assertDoesNotThrow(() -> grille.display());
    }
}
