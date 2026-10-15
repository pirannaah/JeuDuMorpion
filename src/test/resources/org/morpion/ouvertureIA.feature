# language: fr
Fonctionnalité: Stratégie de l’IA quand elle joue en première
  Pour augmenter les chances de gagner
  En tant qu’IA
  On joue d'abord dans un coin, puis dans le coin opposé, dans le but de créer une double menace.

  Scénario: Création d’une double menace par l’IA
    Etant donné une partie en cours avec la grille suivante
      | X | . | . |
      | . | O | . |
      | O | . | X |
    Quand l’adversaire joue en défense
      | X | . | . |
      | . | O | . |
      | O | . | X |
    Et que c’est au tour de l’IA
    Alors la grille devient la suivante
      | X | . | X |
      | . | O | . |
      | O | . | X |
#    Et l’IA dispose d’une double menace possible en (1,2) et (2,3)
      # /!\ random, donc test non complétement déterministe