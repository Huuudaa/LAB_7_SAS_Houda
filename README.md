# TP Lab 7 — Galerie Vedettes (RecyclerView + Glide + SearchView)

## Description

Application Android affichant une galerie de célébrités sous forme de liste avec
images circulaires et notes. L'utilisateur peut filtrer par nom, partager l'application,
et modifier la note d'une vedette via un popup. Le projet est structuré en couches
logiques (DAO / Service / Adapter / UI).

---

## Démonstration

<img width="400" height="887" alt="A" src="https://github.com/user-attachments/assets/8b981c34-b6a6-4e8f-b972-fb4cb0540701" />

---

## Fonctionnalités

- Splash Screen animé au démarrage (rotation, zoom, translation, disparition)
- Liste des vedettes avec image circulaire, nom en majuscules et note (RatingBar)
- Chargement des images distantes via **Glide**
- Filtrage dynamique par nom via la **SearchView** dans la Toolbar
- Menu de **partage** de l'application via `ShareCompat`
- Clic sur une vedette → **popup** pour modifier sa note en temps réel
- Mise à jour instantanée via `notifyItemChanged()`
- Architecture en couches : `beans` / `dao` / `service` / `adapter` / `ui`
- Gestion des données en mémoire via le pattern **Singleton**

---

## Architecture

```
IDao<T>  (interface générique)
    │
    └── VedetteService  (Singleton — stockage en mémoire)
                │
                └── VedetteAdapter  (lie les données au RecyclerView)
                            │
                            ├── GalerieActivity  (liste + recherche + partage)
                            │           │
                            │           └── popup AlertDialog (modifier note)
                            │
                            └── SplashActivity  (animation + redirection)
```

## Fichiers principaux

### `beans/Vedette.java`

Modèle de données représentant une célébrité :

| Champ | Type | Rôle |
|---|---|---|
| `id` | `int` | Identifiant auto-incrémenté |
| `prenom` | `String` | Nom de la célébrité |
| `photo` | `String` | URL de l'image distante |
| `note` | `float` | Note de 0 à 5 |
| `compteur` | `static int` | Compteur partagé pour l'auto-incrément |

### `dao/IDao.java`

Interface générique CRUD :

| Méthode | Rôle |
|---|---|
| `create(T)` | Ajouter un élément |
| `update(T)` | Modifier un élément |
| `delete(T)` | Supprimer un élément |
| `findById(int)` | Chercher par ID |
| `findAll()` | Récupérer tous les éléments |

### `service/VedetteService.java`

- Pattern **Singleton** : une seule instance via `getInstance()`
- Méthode `charger()` : préremplissage de 5 vedettes avec URLs Wikipedia
- Données stockées dans une `ArrayList` en mémoire

### `adapter/VedetteAdapter.java`

- Étend `RecyclerView.Adapter` et implémente `Filterable`
- `catalogue` : liste complète, `catalogueFiltré` : liste filtrée affichée
- `onCreateViewHolder()` : crée la vue + gère le clic (popup)
- `onBindViewHolder()` : charge l'image via Glide, affiche nom et note
- Classe interne `FiltreVedette` : filtre par début de nom (`startsWith`)
- `publishResults()` : met à jour `catalogueFiltré` et notifie le RecyclerView

### `ui/SplashActivity.java`

- 4 animations enchaînées : rotation 360°, zoom 50%, translation bas, disparition
- Thread de 5 secondes avant de lancer `GalerieActivity`
- `finish()` empêche de revenir sur le splash

### `ui/GalerieActivity.java`

- Configure la `Toolbar` via `setSupportActionBar()`
- Charge les vedettes via `VedetteService.getInstance().findAll()`
- `onCreateOptionsMenu()` : inflate `menu_galerie.xml` et initialise la SearchView
- `onQueryTextChange()` : appelle `vedetteAdapter.getFilter().filter(newText)`
- `onOptionsItemSelected()` : gère le bouton partager via `ShareCompat.IntentBuilder`

---

## Flux de navigation

```
Démarrage
    ↓
SplashActivity (5 sec — animations)
    ↓
GalerieActivity  ──── clic sur une vedette ────▶  Popup AlertDialog
    │                                                      │
    │   SearchView (filtre en temps réel)      Confirmer → note mise à jour
    │   Bouton Partager → ShareCompat                      │
    └──────────────────────────────────────────────────────┘
```

---

## Menu de la Toolbar

| Item | ID | Rôle |
|---|---|---|
| Loupe | `menu_recherche` | Ouvre la SearchView pour filtrer |
| Partager | `menu_partager` | Ouvre le sélecteur de partage système |

---

## Concepts utilisés

| Concept | Description |
|---|---|
| `RecyclerView` | Liste performante avec recyclage des vues |
| `ViewHolder` | Optimise l'accès aux vues de chaque item |
| `Filterable` | Interface permettant le filtrage dynamique |
| `Filter` | Classe interne gérant `performFiltering` et `publishResults` |
| `Glide` | Chargement et mise en cache d'images distantes |
| `CircleImageView` | Affichage d'images sous forme circulaire |
| `SearchView` | Barre de recherche intégrée à la Toolbar |
| `AlertDialog` | Fenêtre popup personnalisée |
| `ShareCompat` | Partage de contenu via les apps installées |
| `notifyItemChanged()` | Rafraîchit un seul item du RecyclerView |
| `Singleton` | Instance unique du service partagée dans toute l'app |
| `animate()` | Animations fluides sur les vues Android |

---

## Environnement

- **IDE** : Android Studio
- **Langage** : Java
- **Minimum SDK** : API 24 (Android 7.0)
- **API cible** : 36.1
- **Émulateur** : Medium Phone API 36.1
