package Morpion;

 class GrilleTools {
    static boolean ligneGagnante(Joueur[] grid) {
        for (int ligne = 0; ligne < 3; ligne++) {
            if (ligneGagnante(ligne, grid)) return true;
        }
        return false;
    }

    static boolean ligneGagnante(int ligne, Joueur[] grid) {
        if (grid[ligne * 3] != null && grid[ligne * 3] == grid[ligne * 3 + 1] && grid[ligne * 3] == grid[ligne * 3 + 2])
            return true;
        return false;
    }

    static boolean colonneGagnante(Joueur[] grid) {
        for (int colonne = 0; colonne < 3; colonne++) {
            if (colonneGagnante(colonne, grid)) return true;
        }
        return false;
    }

    static boolean colonneGagnante(int colonne, Joueur[] grid) {
        if (grid[colonne] != null && grid[colonne] == grid[3 + colonne] && grid[colonne] == grid[6 + colonne])
            return true;
        return false;
    }

    static boolean diagonaleGagnante(Joueur[] grid) {
        if (grid[0] != null && grid[0] == grid[4] && grid[0] == grid[8])
            return true;
        if (grid[2] != null && grid[2] == grid[4] && grid[2] == grid[6])
            return true;
        return false;
    }
}