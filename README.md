#Todo Android

##Description

L'objectif est de créer une application TODO en Android.
L'utilisateur de l'application peut créer et consulter sa liste de tâches, stockée en base de données.
La création de la tâche se fait à partir d'une boite de dialogue, permettant de définir un titre et une description.

Pour chacune des tâches, l'utilisateur peut définir un status (en cours/terminé) en faisant glisser la tâche vers la droite ou la gauche. L'utilisateur peut également faire une recherche sur sa liste, en choisissant le status et/ou le nom des tâches créées.

L'application fera des appels à une base de données SQL, à l'aide d'une API REST créée avec le micro-framework  [Lumen](https://lumen.laravel.com/).

On prévoit d'intégrer (si on a le temps) un système de connexion/inscription pour permettre d'avoir la liste des tâches d'un utilisateur authentifié.

##Prérequis

 - Mise en place de l'API **(Thibault / Gwenaël)**
	 - Récupération des tâches
	 - Création d'une tâche
	 - Récupération d'une liste triée
	 - *Authentification*

##Activités 

 - Liste des tâches, champs de recherche + spinner pour status **(Enzo)**
	 - ListView
	 - Spinner
	 - Champ de recherche 
	 - Modal création
 - Activité de recherche **(Gwenaël)**
	 - Interrogation de l'API avec les critères de recherche 
 - Détail d'une tâche **(Thibault)**
 - *Connexion*
 - *Inscription*


##Équipe
Cruz Gwenaël - Granada Thibault - Muhlinghaus Enzo

