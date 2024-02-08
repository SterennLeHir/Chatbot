# Projet Avatar

Le projet Avatar consiste à développer une application de messagerie instantanée où l'utilisateur pourra rechercher l'adresse d'un lieu situé à Rennes. Les requêtes et les réponses seront sous une forme simplifiée et formulées dans un langage naturel. L'interface de l'avatar sera sous la forme d'une messagerie avec une zone de saisie de texte pour l'utilisateur et une zone de présentation graphique avec les échanges précédents. 

## Diagramme UML

![](https://gitlab.istic.univ-rennes1.fr/atonnerre/projet_avatar_gen/-/raw/ReadMe/diagUml.png)

![](https://gitlab.istic.univ-rennes1.fr/atonnerre/projet_avatar_gen/-/raw/ReadMe/UiUml.png)

## Statut de chaque fonctionnalité

|Fonctionnalités| Etat |
|--|--|
|F1 | :white_check_mark: |
|F2 | :white_check_mark: |
|F3 | :white_check_mark: |
|F4 | :white_check_mark: |
|F5 | :x: |
|F6 | :large_orange_diamond: Implémentation faite, mais il manque la base de données en F5 |
|F7 | :x: |
|F8 | :white_check_mark: |

### F1 : Première base de données 

L'avatar répondra à des demandes concernant 4 lieux dans Rennes :

 - le Théâtre National de Bretagne
 - la Gare
 - le Théâtre la Paillette
 - la Mairie

L'avatar renvoie les adresses des 4 lieux ci-dessus, qui sont données dans le fichier **donneesInitiales.txt**.  Nous avons créé notre base de données à l'aide d'une **maps** que l'on initialise à chaque compilation. De plus, si la requête est incompréhensible, l'avatar renvoie un message d'erreur au client. 

### F2 : Tolérance aux fautes

L'avatar est capable de répondre aux demandes contenant certaines fautes. Grâce à la méthode de **Levenshtein**, l'avatar tolère un mot ayant **une seule faute** ; c'est-à-dire une lettre en moins ou en plus, ou le changement d'une d'entre elles. Il accepte également l'oubli d'accents, de majuscules et de mots de liaison.

### F3 : Salutations

L'avatar renvoie un message de **salutation** s'il y en a un dans la requête du client. Les mots de salutation tolérés par l'avatar sont : bonjour, salut et bonsoir.

### F4 : Cinq langues

L'avatar est capable de comprendre l'utilisateur dans 5 langues différentes, soit : **le français, l'anglais, l'espagnol, l'allemand et l'italien**. La langue prédéfinie de l'avatar est le français. Le changement se fait quand l'avatar détecte un mot dans une autre langue que le français. Alors, il demande confirmation au client pour le changement dans la nouvelle langue. Si le client ne valide pas, l'avatar lui proposera une autre langue jusqu'à ce que celui-ci confirme (dans l'ordre ci-dessus). Si la requète est composé de mot de langue différentes, c'est la langue du premier mot qui est gardée.

### F5 : Base de données "vivre à Rennes"

L'avatar traite les recherches des adresses à l'aide de la base de données openData **"vivre à Rennes"** fournie sur l'[adresse](http://www.data.rennes-metropole.fr/). L'avatar exploite seulement des adresses existantes à Rennes et les renvoie telles qu'elles figurent dans la base. La base de données nous est fournie sous la forme du ficher **vAr.xml** . 

### F6 : Plusieurs réponses 

L'avatar est capable de proposer plusieurs réponses quand la requête du client n'est pas précise. Les réponses sont envoyées au client dans **l'ordre alphabétique** avec des **numéros** attribués. Le client peut donc choisir une proposition en communicant le numéro de celle-ci. Si le client n'envoie pas de numéro, l'avatar renvoie le message d'erreur habituellement utilisé et retourne en mode de recherche initial.

### F7 : Recherches de restaurants

Pour la requête d'un **restaurant**, d'une **crêperie** ou d'une **pizzeria**, l'avatar n'utilise pas la base de données pour trouver l'adresse mais cherche directement sur le site de [Linternaute](https://www.linternaute.com/). 

### F8 : Génération de paroles

A chaque message envoyé sur l'interface, la réponse est lue à haute voix. 

## L'interface graphique

![](https://gitlab.istic.univ-rennes1.fr/atonnerre/projet_avatar_gen/-/raw/ReadMe/Screen_UI_légendé.png)

|Numéro| Légende |
|--|--|
|1 | Message de l'utilisateur |
|2 | Réponse du bot en un ou plusieurs messages |
|3 | Chatbox |
|4 | Entrée de texte pour l'utilisateur |
|5 | Bouton d'envoi du message (la touche ENTRER marche aussi pour l'envoi) |
