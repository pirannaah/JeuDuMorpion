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
            saisieOK = true;
            System.out.print("coordonnée : ");
            String line = scanner.nextLine();
            String[] split = line.split(",");
            int ligne = Integer.valueOf(split[0]) - 1;
            int colone = Integer.valueOf(split[1]) -1;
            try {
                grille.joue(ligne*3+colone, this);
            } catch (CaseOccupeeException e) {
                System.out.println("Case occupée, rejouez");
                saisieOK = false;
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
