package Morpion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Random;

public class JoueurUnitTest {

    // Pour tester la méthode joue, on doit vérifier que :
    // - la case choisie est choisie au hasard
    // - c'est bien le joueur qui est utilisé pour jouer (on le retrouve
    // dans la grille)

    @Test
    public void testJoue() {
        // Dans cette première version, on controle le comportement du
        // générateur aléatoire en réimplémentant la méthode nextInt(bound)
        // (qui est la méthode qui est utilisé dans la fonction joue de Joueur)
        // pour qu'elle renvoie successivement des valeurs permettant de jouer
        // sur une diagonale
        Joueur joueur = new Joueur("X");
        Grille grille = new Grille();
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                if (bound == 9)
                    return 4;
                else if (bound == 8)
                    return 0;
                else if (bound == 7)
                    return 6;
                return 0;
            }
        };


        joueur.joue(grille, random);
        Assertions.assertFalse(grille.casesVides().contains(new Coordonnee(1, 1)));
     // On vérifie par l'assertion ci-dessus que la fonction joue a joué dans la bonne case
        joueur.joue(grille, random);
        joueur.joue(grille, random);
        Assertions.assertEquals(joueur, grille.gagnant());
        // On vérifie par cette assertion que la fonction joue a utilisée 3 fois le joueur pour
        // remplir les cases de la diagonale
    }

    @Test
    public void testJoueAvecMockSurRandom() {
        // Dans cette 2eme version, on utilise un mock pour Random plutôt que d'utiliser
        // une classe anonyme
        Joueur joueur = new Joueur("X");
        Grille grille = new Grille();

        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(4,0,6);
//        Mockito.when(random.nextInt(9)).thenReturn(4);
//        Mockito.when(random.nextInt(8)).thenReturn(0);
//        Mockito.when(random.nextInt(7)).thenReturn(6);

        joueur.joue(grille, random);
        Assertions.assertFalse(grille.casesVides().contains(new Coordonnee(1, 1)));
        joueur.joue(grille, random);
        joueur.joue(grille, random);
        Assertions.assertEquals(joueur, grille.gagnant());
    }

    @Test
    public void testJoueEnIsolation() {
        // Dans cette 3eme version, on souhaite etre en complète isolation
        // de la classe Joueur, on remplace donc toutes les AUTRES par des mocks
        // Ici, Random et Grille seront remplacées par des mocks
        Joueur joueur = new Joueur("X");
        Grille grille = Mockito.mock(Grille.class);
// la fonction joue fait appel à grille.caseVide(), il faut donc définir ce que
        // doit retourner la fonction lors de l'appel
        Mockito.when(grille.casesVides()).thenReturn(
                Arrays.asList(
                        new Coordonnee(0,0), new Coordonnee(0,1), new Coordonnee(0,2),
                        new Coordonnee(1,0), new Coordonnee(1,1), new Coordonnee(1,2),
                        new Coordonnee(2,0), new Coordonnee(2,1), new Coordonnee(2,2)
                        )
        );
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(4,0,6);

        // La suite du test telle qu'elle était écrite dans les tests précédents ne
        // fonctionne plus. En effet la fonction joue de Grille ne fait rien (elle ne
        // met pas à jour l'attribut grid de Grille). L'état de la grille n'est pas mémorisé.
        // On ne peut donc plus faire jouer 3 fois et vérifier une diagonale gagnante.
        // En revanche on peut controler l'appel qui est fait sur le mock de grille lors
        // de l'appel de grille.joue dans joueur.joue
        joueur.joue(grille, random);
        Mockito.verify(grille).joue(joueur, new Coordonnee(1,1));
        // on vérifie que la méthode joue a été appelée sur le mock grille avec
        // en paramètre joueur et (1,1)
        // Au niveau du random, nous n'avons plus besoin que du premier nombre
    }
}
