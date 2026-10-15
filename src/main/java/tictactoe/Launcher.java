package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws CaseOccupeeException {
        Random random = new Random();
        Joueur human = new Human("O", new Scanner(System.in));
       // Joueur human = new IA("O");
        Joueur ia =new IA("X", new Random());
        Game game = new Game(human, ia, new MancheFactory());
        game.play();
        System.out.println("Match gagné par :"+game.winner());
    }
}
