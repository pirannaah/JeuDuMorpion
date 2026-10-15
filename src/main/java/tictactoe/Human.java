package tictactoe;

import java.util.Scanner;


public class Human implements Joueur {

    private String motif;
    private Scanner scanner;
    private Joueur opponent;

    public Human(String motif, Scanner scanner) {
        this.motif = motif;
        this.scanner = scanner;
    }

    public void joue(Grille grille)  {
        boolean saisieOK;
        do {
            saisieOK = false;
            System.out.print("coordonnée : ");
            String line = scanner.nextLine();
            String[] split = line.split(",");
            if (split.length < 2) {
                System.out.println("Format invalide. Entrez ligne,colonne (ex: 1,2)");
                continue;
            }
            try {
                int ligne = Integer.parseInt(split[0].trim()) - 1;
                int colone = Integer.parseInt(split[1].trim()) - 1;
                if (ligne >= 0 && ligne < 3 && colone >= 0 && colone < 3) {
                    grille.joue(ligne * 3 + colone, this);
                    saisieOK = true;
                } else {
                    System.out.println("Coordonnées hors limites (1 à 3)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format numérique invalide");
            } catch (CaseOccupeeException e) {
                System.out.println("Case occupée, rejouez");
            }
        } while (!saisieOK);
    }

    @Override
    public void setOpponent(Joueur opponent) {
        this.opponent = opponent;
    }


    @Override
    public String toString() {
        return motif;
    }
}
