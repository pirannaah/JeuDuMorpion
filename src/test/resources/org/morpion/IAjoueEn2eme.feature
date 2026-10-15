# language: fr
Fonctionnalité: Stratégie de l’IA quand elle joeu en 2ème
  Pour augmenter les chances de gagner
  En tant qu’IA
  On veut prendre le milieu pour assurer un match nul ou une victoire

  Scénario: Réponse optimale après un coup adverse
    Etant donné une partie en cours avec la grille suivante
      | O | . | . |
      | . | . | . |
      | . | . | . |
    Et que c’est au tour de l’IA
    Alors la grille devient la suivante
      | O | . | . |
      | . | X | . |
      | . | . | . |

      # /!\ random, donc test non complétement déterministe