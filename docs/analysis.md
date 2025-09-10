# Études préliminaires

## Analyse du problème

Avec les progrès fait dans le domaine de l&rsquo;audio numérique, de plus
 en plus de solutions digitales s&rsquo;offrent aux musiciens (simulateur
 d&rsquo;amplificateur, d&rsquo;éffets sonores). Mais les solutions analogue offrent
 des options qui ne sont parfois pas accessibles autrement. Or un usager pourrait
 vouloir avoir une partie de son signal qui est traité de manière analogique
 et l&rsquo;autre de manière numérique. Quand un musicien performe, il n&rsquo;a pas
 le temps de pèser sur plusieurs boutons pour changer le chemin qu&rsquo;emprunte
 son signal ou de cliquer avec une souris sur son ordinateur pour changer
 les paramètres des instruments virtuels ou plugins qu'il utilise.  

## Exigences

### Besoins Fonctionnel (ce que le système doit faire)
- Offrir la possibilité à l'utilisateur d'accèder et de changer de son de manière instantanée peu importe si ce son es modulé  de manière analogique, numérique ou les 2 combinés.
- Le système doit être assez robuste(physiquement parlant) et compact
- Héberger les logiciels de traitement sonore numérique, donc permettre aux utilisateurs de charger les logiciels de traitement numérique à même le dispositif.
- Le système doit être assez rapide dans le traitement du signal pour ne pas engendrer de délai entre l'input du musicien et ce qui entendu.
### Besoins non Fonctionnel (comment il doit le faire)
- Interaction de l'utilisateur à l'aide d'intérrupteurs aux pieds (les mains du musicien sont occupées à faire autre chose).
- Diriger le signal analogique à l'aide de transistors ou relais, eux mêmes controllés par un microcontrolleur pour d'éterminer quels processeurs d'effets analogiques seront utilisés et dans quel ordre. 
- Convertir le signal analogique en numérique pour le traitement numérique et le reconvertir en signal analogique une fois le traitement numérique fait.

## Recherche de solutions

## Contrôle de signal analogique

### Midi activated loopswitcher

Ces solutions sont une surprise pour moi et je crois qu'une
partie du résultat que je voudrais obtenir pourrait se faire
avec ces dispositifs. Seul défaut est qu'avec le Hydra4x et le MorningStar qu'il faut 2 dispositifs
(un controlleur midi et un midiloopswitcher) pour y arriver. Il
faudrait aussi un ordinateur pour l'hébergement des plugins digitals.  

_HYDRA4X_   
[oscillatordevices](https://oscillatordevices.com/hydra/)  

_MorningStar MIDI loopswitcher_   
[MorningStar](https://www.morningstar.io/loop-switcher-comparison)  

_MasterMind pcb_   
Fait pas mal tout ce que je voudrais sauf l'hébergement de pluggins  
[rjmmusic](https://shop.rjmmusic.com/mastermind-pbc-6x/)

## Contrôle de signal numérique

### VST Hosts

_Logiciels qui permettent l'hébergement de plugins audio numérique_  

-Compatible avec linux


#### Reaper DAW
[Reaper](https://www.reaper.fm/)

#### Kushview Element SE
[kushview](https://kushview.net/element/)

### Hardware qui permet l'hébergement de plugin pour traitement du son

_Quad Cortex_  
[Neural DSP Quad Cortex](https://neuraldsp.com/quad-cortex)  
Compatibilite de plugin propriétaire seulement  
[Quad Cortex Compatibility](https://neuraldsp.com/pcom)  

_AxeFX_  
[Fractal Audio AXE-FX III](https://www.fractalaudio.com/iii/)

_Fender Tone Master Pro_  
[Tone Master Pro](https://intl.fender.com/products/tone-master-pro?variant=45947071463646)

Tous ces dispositifs restreignent l'utilisateur à n'utiliser que le traitement numérique propriétaire vendu par chaque compagnie respective.


### Convertisseurs analogiques digital/digital analogique (AD DA)
[CS4272](https://www.cirrus.com/products/cs4272)


## Conclusion et choix retenu

Les solutions déjà existantes étant dispendieuses et propriétaires, nous croyons qu'un usager pourrait bénifissier d'une plateforme plus ouverte.

### Commutateurs pour circuits analogiques
Nous avons choisi d'utiliser des relai pour l'acheminement du signal analogique car il est possible de faire des contacts "true bypass" avec des relai [amplified parts](https://www.amplifiedparts.com/tech-articles/relay-true-bypass-switching-1) et leur implémentation semble assez simple avec un micro controlleur. 

### Envoi de signaux midi
l'envoi de signaux midi peut se faire assez facilement par le biais d'un micro controlleur et d'une librairie MIDI

### Hébergement de logiciels audio numérique
- Nécessite d'avantage de recherches  

- Solutions envisagée de manière préliminaires serait possiblement un UDOOX86  
  [UDOOX86](https://www.udoo.org/docs-x86/Introduction/Introduction.html)

mais l'évaluation de performances et devra être faite pour voir si cette option est viable.  

Le convertisseur AD DA CS4272 ayant déjà fait ses preuves commercialement serait une option envisageable et pas trop coûteuse

## Méthodologie

Séparer le projet en 4 étapes

1. Contrôle analogique
2. Envoi de signaux MIDI pour le controle numérique
3. Hébergement de logiciels audio numériques
4. Développement d'une interface pour permettre à l'utilisateur de configurer l'appareil comme il le veut
