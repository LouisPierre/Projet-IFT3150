# Études préliminaires

## Analyse du problème

- Décrire le problème à résoudre.

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

- Présenter les solutions existantes et justifier le choix retenu.
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

## Méthodologie

Séparer le projet en 4 étapes

1. Contrôle analogique
2. Envoi de signaux MIDI pour le controle numérique
3. Hébergement de logiciels audio numériques
4. Développement d'une interface pour permettre à l'utilisateur de configurer l'appareil comme il le veut
