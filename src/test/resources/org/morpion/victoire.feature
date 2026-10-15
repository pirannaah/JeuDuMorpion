# language: fr
Fonctionnalité: Victoire à partir d'une grille vide
  Afin de déterminer le gagnant
  En tant que joueur
  La partie s’arrête dès qu’un joueur aligne 3 symboles sur une ligne, une colonne ou une diagonale, à partir d'une grille vide.

  Scénario: Partie depuis une grille vide avec victoire sur ligne
    Etant donné une grille vide
    Quand le joueur "X" joue en position (1,1)
    Et le joueur "O" joue en position (2,1)
    Et le joueur "X" joue en position (1,2)
    Et le joueur "O" joue en position (2,2)
    Et le joueur "X" joue en position (1,3)
    Alors le joueur "X" est déclaré gagnant

  Scénario: Partie depuis une grille vide avec victoire sur colonne
    Etant donné une grille vide
    Quand le joueur "O" joue en position (1,1)
    Et le joueur "X" joue en position (1,2)
    Et le joueur "O" joue en position (2,1)
    Et le joueur "X" joue en position (2,2)
    Et le joueur "O" joue en position (3,1)
    Alors le joueur "O" est déclaré gagnant

  Scénario: Partie depuis une grille vide avec victoire sur diagonale
    Etant donné une grille vide
    Quand le joueur "X" joue en position (1,1)
    Et le joueur "O" joue en position (1,2)
    Et le joueur "X" joue en position (2,2)
    Et le joueur "O" joue en position (1,3)
    Et le joueur "X" joue en position (3,3)
    Alors le joueur "X" est déclaré gagnant

