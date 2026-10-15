# language: fr
Fonctionnalité: Égalité à partir d'une grille vide
  Pour déterminer l'arrêt d'une partie
  En tant que joueur
  La partie s’arrête quand la grille est complète

  Scénario: Partie complète depuis une grille vide avec match nul
    Etant donné une grille vide
    Quand le joueur "X" joue en position (1,1)
    Et le joueur "O" joue en position (1,2)
    Et le joueur "X" joue en position (1,3)
    Et le joueur "O" joue en position (2,2)
    Et le joueur "X" joue en position (2,1)
    Et le joueur "O" joue en position (2,3)
    Et le joueur "X" joue en position (3,2)
    Et le joueur "O" joue en position (3,1)
    Et le joueur "X" joue en position (3,3)
    Alors aucun joueur n’est déclaré gagnant