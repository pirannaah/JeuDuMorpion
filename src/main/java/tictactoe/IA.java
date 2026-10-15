package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IA implements Joueur {

    private Random random;
    private static final int MINIMUM = -5000;
    private static final int MAXIMUM = 5000;
    private String motif;
    private int profondeur = 9;
    private Joueur opponent;

    public IA(String motif, Random random) {
        this.motif = motif;
        this.random = random;
    }

    public void joue(Grille grille) {
        List<Integer> coupsPossibles = calculIA(grille, profondeur);
        int index = coupsPossibles.get(random.nextInt(coupsPossibles.size()));
        try {
            grille.joue(index, this);
        } catch (CaseOccupeeException e) {
            throw new RuntimeException("impossible", e);
        }

    }

    @Override
    public void setOpponent(Joueur opponent) {
        this.opponent = opponent;
    }

    private List<Integer> calculIA(Grille grille, int profondeur) {
        int max = MINIMUM-1;
        List<Integer> coupJoue = new ArrayList<>();
        List<Integer> coupsPossibles = grille.casesVides();
        for (Integer coupsPossible : coupsPossibles) {
            try {
                grille.joue(coupsPossible, this);
            } catch (CaseOccupeeException e) {
                e.printStackTrace();
            }
            int newMax = calculMax(grille, profondeur - 1);
            if (newMax > max) {
                max = newMax;
                coupJoue.clear();
                coupJoue.add(coupsPossible);
            } else if (newMax == max)
                coupJoue.add((coupsPossible));
            grille.annuleCoup(coupsPossible);
        }
        return coupJoue;
    }

    private int calculMax(Grille grille, int profondeur) {
        int max = MAXIMUM;

        if (grille.isGagnante() || grille.isPat() || profondeur == 0)
            return eval(grille);
        else {
            List<Integer> coupsPossibles = grille.casesVides();
            for (Integer coupsPossible : coupsPossibles) {
                try {
                    grille.joue(coupsPossible, this.opponent);
                } catch (CaseOccupeeException e) {
                    e.printStackTrace();
                }
                int tmp = calculMin(grille, profondeur - 1);
                if (tmp < max) max = tmp;
                grille.annuleCoup(coupsPossible);
            }
            return max;
        }
    }

    private int calculMin(Grille grille, int profondeur) {
        int min = MINIMUM;

        if (grille.isGagnante() || grille.isPat() || profondeur == 0)
            return eval(grille);
        else {
            List<Integer> coupsPossibles = grille.casesVides();
            for (Integer coupsPossible : coupsPossibles) {
                try {
                    grille.joue(coupsPossible, this);
                } catch (CaseOccupeeException e) {
                    e.printStackTrace();
                }
                int tmp = calculMax(grille, profondeur - 1);
                if (tmp > min) min = tmp;
                grille.annuleCoup(coupsPossible);
            }
            return min;
        }
    }

    private int eval(Grille grille) {
        if (grille.isGagnante()) {
            if (grille.gagnant() == this) return MAXIMUM-(9-grille.casesVides().size());
            else
                return MINIMUM+(9-grille.casesVides().size());
        }
        if (grille.isPat())
            return 0;

        int score =0;
        score += scoreLigne(grille);
        score += scoreColonne(grille);
        score += scoreDiagonale(grille);
        return score;
    }

    private int scoreLigne(Grille grille) {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            List<Joueur> ligne = grille.getLigne(i);
            if (ligne.isEmpty() || ligne.size()==3) continue;
            int cmptPions = 0;
            for (Joueur joueur : ligne) {
                if (joueur== this) cmptPions++;
                else cmptPions--;
            }
            if (ligne.size() == 1) score+=cmptPions*10;
            else score+=cmptPions*30;
        }
       return score;
    }

    private int scoreColonne(Grille grille) {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            List<Joueur> ligne = grille.getColonne(i);
            if (ligne.isEmpty() || ligne.size()==3) continue;
            int cmptPions = 0;
            for (Joueur joueur : ligne) {
                if (joueur== this) cmptPions++;
                else cmptPions--;
            }
            if (ligne.size() == 1) score+=cmptPions*10;
            else score+=cmptPions*30;
        }
        return score;
    }

    private int scoreDiagonale(Grille grille) {
        int score = 0;
        for (int i = 0; i < 2; i++) {
            List<Joueur> ligne = grille.getDiagonale(i);
            if (ligne.isEmpty() || ligne.size()==3) continue;
            int cmptPions = 0;
            for (Joueur joueur : ligne) {
                if (joueur== this) cmptPions++;
                else cmptPions--;
            }
            if (ligne.size() == 1) score+=cmptPions*10;
            else score+=cmptPions*30;
        }
        return score;
    }
    @Override
    public String toString() {
        return motif;
    }
}
