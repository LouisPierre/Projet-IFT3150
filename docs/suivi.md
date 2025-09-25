# Suivi de projet

## Semaine 1

??? note "Mettre en place l'environnement"

!!! info "Notes"
    - Il est possible que nous révisions les exigences après le prototypage


!!! abstract "Prochaines étapes"
    - Démarrer l’analyse du problème
    - Créer la structure de `etudes_preliminaires.md`
    - Présenter le projet à Guy Bois et lui demander s'il veut le co-superviser 

---

## Semaine 2

Recherche sur les solutions déjà existante

Rédaction de la section Études Préliminaires


## Semaine 3


!!! abstract "Prochaines étapes"
    - Faire un choix pour la direction du projet :

       - Faire un format de sauvegarde homogène pour les différents logiciels d'hébergement de 'plugins' 

    Ou
    
       - Continuer avec la gestion de traitement du signal analogique/numérique

    Ensuite

    - Modification du schéma du circuit de contrôle analogique pour pouvoir inverser l'ordre des boucles au besion
    - Commencement du développement du prototype

    - Demander à Guy Bois s'il peut contacter son collegue dans le domaine de l'audio numérique pour savoir s'il pourrait répondre à mes questions au besoin.


## Semaine 4
  - Achats matériel
    - [x] 74CH595 pour multiplier les sorties du micro-controlleur
    - [x] jumper cables male -> femele pour connecter le micro-controlleur aux relais
  - Finir circuit loopswitcher
  - Faire le lien avec l'arduino 
  
!!! warning "Difficultés rencontrées"
  
      - Ça prends des interrupteurs momentanés et non pas des triple poles double throw
      - Les numéros des relais dans le schéma sont dans le mauvais ordre par rapport à la disposition des relais, ce qui a engendré un spaghetti de fils.
      - Pour étendre le nombre de sorties du micro-contrôlleur, ça prend un 'shift register' et non pas un décodeur ou un multiplexeur/dé-multiplexeur.
      - Problème d'initialisation des "pins" sur le arduino faisait que les relais ne s'acivaient pas

!!! abstract "Prochaines étapes"
    - Changer l'ordre des relais dans le schéma et l'implémenter avec les fils pour eviter que lesdit fils s'entre-mêlent.
    - Intégrer les boutons pressoir au circuit pour pouvoir changer les états des boucles en temps réel.
    - Utiliser le 74HC595 shift register pour multiplier les ports disponnibles sur le micro-contrôlleur
    - Mettre le circuit dans un boîte en aluminium 
    - Faire le circuit pour l'envoi de signaux MIDI
## Semaine 5

## Semaine 6

## Semaine 7

## Semaine 8

## Semaine 9

## Semaine 10

## Semaine 11

## Semaine 12

## Semaine 13
