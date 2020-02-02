import java.util.Map;
import java.util.HashMap;


/**
 *  Classe principale du jeu "Zork". <p>
 *
 *  Zork est un jeu d'aventure très rudimentaire avec une interface en mode
 *  texte: les joueurs peuvent juste se déplacer parmi les différentes pieces.
 *  Ce jeu nécessite vraiment d'etre enrichi pour devenir intéressant!</p> <p>
 *
 *  Pour jouer a ce jeu, créer une instance de cette classe et appeler sa
 *  méthode "jouer". </p> <p>
 *
 *  Cette classe crée et initialise des instances de toutes les autres classes:
 *  elle crée toutes les pieces, crée l'analyseur syntaxique et démarre le jeu.
 *  Elle se charge aussi d'exécuter les commandes que lui renvoie l'analyseur
 *  syntaxique.</p>
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @author     Léo DÉCHAUMET (modification et ajout de méthodes)
 * @version    2.0
 * @since      March 2000
 */

public class Jeu {
	private AnalyseurSyntaxique analyseurSyntaxique;
	private Piece pieceCourante;
	private String directionRetour; //contient la direction pour retourner une
	//piece en arrière. Contient null si le joueur n'a pas encore bouger.
	private Sac sac;
	private ObjetZork objetVictoire;
	
	
	
	private Map<ObjetZorkCle,ObjetZork> CorrespondanceCle;


	/**
	 *  Crée le jeu et initialise la carte du jeu (i.e. les pièces).
	 */
	public Jeu() {
		CorrespondanceCle = new HashMap<ObjetZorkCle,ObjetZork>();
		creerPieces();
		analyseurSyntaxique = new AnalyseurSyntaxique();
		sac = Sac.getInstance();
	}


	/**
	 *  Crée toutes les pieces et relie leurs sorties les unes aux autres.
	 *  Crée tout les objets et les places dans les pieces.
	 *  @author  Léo DECHAUMET (création des objets)
	 */
	public void creerPieces() {
		Piece dehors;
		Piece salleTD;
		Piece taverne;
		Piece batimentC;
		Piece burreau;
		Piece bibliotheque;
		Piece caverne;
		
		
		ObjetZork ciseaux = null;
		ObjetZork styleau = null;
		ObjetZork livre   = null;
		ObjetZork etagere = null;
		ObjetZork clePortail = null;
		ObjetZork portailFonctionnel = null;

		ObjetZorkNote note = null;
		
		ObjetZorkCode coffre = null;
		ObjetZork     coffreApres = null;
		
		ObjetZorkCle  portail = null;

		// création des pieces
		dehors = new Piece("devant le batiment C");
		salleTD = new Piece("une salle de TD dans le batiment G");
		taverne = new Piece("la taverne");
		batimentC = new Piece("le batiment C");
		burreau = new Piece("le secrétariat");
		bibliotheque = new Piece("la bibliothèque");
		caverne = new Piece("la caverne, un lieu mysterieux où sont déposé plein d'objet étrange");

		// initialise les sorties des pieces
		// (nord, est, sud, ouest)
		dehors.setSorties(taverne, salleTD, batimentC, null);
		salleTD.setSorties(bibliotheque, null, null, dehors);
		taverne.setSorties(null, null, dehors, null);
		batimentC.setSorties(dehors, burreau, caverne, null);
		burreau.setSorties(null, null, null, batimentC);
		bibliotheque.setSorties(null, null, salleTD, null);
		caverne.setSorties(batimentC, null, null, null);
		
		//création des objet, si un objet contient un autre objet (comme un coffre), initialiser le
		//conteneur en deuxieme
		ciseaux = new ObjetZork("ciseaux", "une paire de ciseaux", 120, ciseaux);
		styleau = new ObjetZork("stylo", "un stylo pour écrire", 20, styleau);
		livre = new ObjetZork("livre", "un livre sur la mécanique quantique, utile si vous étudiez la mécanique quantique...", 30, livre);
		note = new ObjetZorkNote("note", "un papier froissé sur lequel sont grifoné des choses... ", 10, note, "ranger les course \n code: 80526");
		etagere = new ObjetZork("etagere", "une étagere, pour entreposer des livres", -1, etagere);
		clePortail = new ObjetZork("cle", "la cle vous permettant de vous évader", 5, clePortail);
		
		coffreApres = new ObjetZork("coffre ouvert", "le coffre est ouvert, il est totalement vide maintenant", -1, coffreApres);
		coffre = new ObjetZorkCode("coffre", "un coffre en acier à code, quasiment impossible à forcer", -1, coffreApres, clePortail, "80526");

		portailFonctionnel = new ObjetZork("portail fonctionnel", "le portail qui vous permet de gagner", -1, portailFonctionnel);
		portail = new ObjetZorkCle("portail", "un objet mysterieux, vous avez besoin d'une clé pour l'activer", -1, null, portailFonctionnel);
		
		//décrit les correspondances clés-objets
		CorrespondanceCle.put(portail, clePortail);
		
		//indique l'objet qui permet la victoire
		objetVictoire = portailFonctionnel;
		
		// place les objets dans les pièces
		dehors.add_ObjetZork(ciseaux);
		dehors.add_ObjetZork(styleau);
		bibliotheque.add_ObjetZork(livre);
		bibliotheque.add_ObjetZork(etagere);
		batimentC.add_ObjetZork(note);
		taverne.add_ObjetZork(coffre);
		caverne.add_ObjetZork(portail);
		

		// le jeu commence dehors
		pieceCourante = dehors;
		Piece.set_pieceCourante(pieceCourante);
	}


	/**
	 *  Pour lancer le jeu. Boucle jusqu'a la fin du jeu.
	 */
	public void jouer() {
		afficherMsgBienvennue();

		// Entrée dans la boucle principale du jeu. Cette boucle lit
		// et exécute les commandes entrées par l'utilisateur, jusqu'a
		// ce que la commande choisie soit la commande "quitter"
		boolean termine = false;
		while (!termine) {
			Commande commande = analyseurSyntaxique.getCommande();
			termine = traiterCommande(commande);
			System.out.println("---------------------------------------------------");
		}
		System.out.println("Merci d'avoir jouer.  Au revoir.");
	}


	/**
	 *  Affiche le message d'accueil pour le joueur.
	 *  @author Léo DECHAUMET
	 */
	public void afficherMsgBienvennue() {
		System.out.println();
		System.out.println("Bienvennue dans le monde de Zork !");
		System.out.println("Vous vous trouvez dans l'université de villetaneuse");
		System.out.println("Vous avez recement découvert, dans une pièce de l'université nomée \"la caverne\", un mysterieux objet, assez grand et en forme de O...");
		System.out.println("On vous a dit que cet objet était là avant la création de l'université, que personne ne connaissait son identité ni de quoi il était fait...");
		System.out.println("Vous décidez alors d'enqueter sur ce mystere...");
		System.out.println("Tapez 'aide' si vous avez besoin d'aide.");
		System.out.println();
		System.out.println(pieceCourante.descriptionLongue());
	}


	/**
	 *  Exécute la commande spécifiée. Si cette commande termine le jeu, la valeur
	 *  true est renvoyée, sinon false est renvoyée.
	 *  Les commandes permettant de finir le jeu peuvent être la commande
	 *  quitter, ou une commande qui amène à une situation gagnante
	 *
	 * @param  commande  La commande a exécuter
	 * @return           true si cette commande termine le jeu ; false sinon.
	 * @author           Léo DECHAUMET (Commande ajouter)
	 */
	public boolean traiterCommande(Commande commande) {
		if (commande.estInconnue()) {
			System.out.println("Je ne comprends pas ce que vous voulez...");
			return false;
		}

		String motCommande = commande.getMotCommande();
		if (motCommande.equals("aide")) {
			afficherAide();
		} else if (motCommande.equals("aller")) {
			deplacerVersAutrePiece(commande);
		} else if (motCommande.equals("prendre")){
			prendreObjetZork(commande);
		} else if (motCommande.equals("poser")){
			poserObjetZork(commande);
		} else if (motCommande.equals("examiner")){
			examiner(commande);
		} else if (motCommande.equals("retour")){
			retour();
		} else if (motCommande.equals("utiliser")){
			utiliser(commande);
		} else if (motCommande.equals("quitter")) {
			if (commande.aSecondMot()) {
				System.out.println("Quitter quoi ?");
			} else {
				return true;
			}
		}
		if (gagne()) {
			afficherVictoire();
			return true;
		}
		return false;
	}


	// implementations des commandes utilisateur:

	/**
	 *  Affichage de l'aide. Affiche notament la liste des commandes utilisables.
	 *  
	 * @author Léo DECHAUMET
	 */
	public void afficherAide() {
		System.out.println("Vous etes perdu. Vous etes seul. Vous errez");
		System.out.println("sur le campus de l'Université Paris 13.");
		System.out.println();
		System.out.println("Les commandes reconnues sont:");
		analyseurSyntaxique.afficherToutesLesCommandes();
		System.out.println("Pour plus d'info sur les commande taper : examiner commande");
	}


	/**
	 *  Tente d'aller dans la direction spécifiée par la commande. Si la piece
	 *  courante possède une sortie dans cette direction, la piece correspondant a
	 *  cette sortie devient la piece courante, dans les autres cas affiche un
	 *  message d'erreur.
	 *
	 * @param  	commande  Commande dont le second mot spécifie la direction a suivre
	 * @author  Léo DECHAUMET pour l'implémentation de la commande retour
	 */
	public void deplacerVersAutrePiece(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne contient pas de second mot, nous ne
			// savons pas ou aller..
			System.out.println("Aller où ?");
			return;
		}

		String direction = commande.getSecondMot();

		// Tentative d'aller dans la direction indiquée.
		Piece pieceSuivante = pieceCourante.pieceSuivante(direction);

		if (pieceSuivante == null) {
			System.out.println("Il n'y a pas de porte dans cette direction!");
			
		} 
		
		//recherche de la direction opposé au déplacement,
	    //utile si on utilise plus tard la commande retour
		else {
			switch(direction) {
				case "nord":
					directionRetour = "sud";
					break;
				case "sud":
					directionRetour = "nord";
					break;
				case "est":
					directionRetour = "ouest";
					break;
				case "ouest":
					directionRetour = "est";
					break;
			}
			pieceCourante = pieceSuivante;
			Piece.set_pieceCourante(pieceCourante);
			System.out.println(pieceCourante.descriptionLongue());
		}
	}
	
	/**
	 *  Tente de prendre l'objet spécifié par la commande. 
	 *  l'objet est ajouté dans le sac et est retiré de la piece si les conditions
	 *  suivante sont réunies : l'objet se situe dans la piece courrante, l'objet est
	 *  transportable, la prise de l'objet n'entrainerait pas un dépacement de la
	 *  taille maximale du sac ou du poids maximum du sac.
	 *  Dans les autres cas affiche un message d'erreur.
	 *  
	 * @param  commande  Commande dont le second mot spécifie le nom de l'objet
	 *                   à prendre
	 * @author Léo DECHAUMET
	 */
	
	public void prendreObjetZork(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne possède pas de second mot, nous ne
			// savons pas quoi prendre...
			System.out.println("Prendre quoi ?");
			return;
		}
		
		//recherche l'objet dans la pièce.
		String objetAPrendre = commande.getSecondMot();
		ObjetZork objetZork = pieceCourante.ObjetZorkCorrespondant(objetAPrendre);
		
		//Aucun objet ne correspond au nom donné:
		if (objetZork == null) {
			System.out.println("Cet objet n'est pas dans cette piece...");
		}
		
		//L'objet n'est pas transportable:
		else if (!objetZork.is_transportable()){
			System.out.println("Cet objet ne peut pas être transporté.");
		}
		
		//Le sac a atteint sa capacité maximale:
		else if (sac.get_taillePlace() == Sac.capacitePlace) {
			System.out.print("Il n'y a plus d'emplacement disponible dans votre sac,");
			System.out.println("Vous devez vous delester de certains objets...");
		}
		
		//Le sac a atteint son poids maximale:
		else if (sac.get_taillePoids() + objetZork.get_Poids() > Sac.capacitePoids) {
			System.out.println("Cet objet est trop lourd, vous devez lacher des objets...");
		}
		
		else{
			sac.add_ObjetZork(objetZork);
			pieceCourante.jeterObjetZork(objetAPrendre);
		}
		
	}
	
	/**
	 *  Tente de poser l'objet spécifié par la commande. 
	 *  Si le sac contient l'objet spécifié, l'objet est posé dans la piece 
	 *  et est retiré du sac.
	 *  Dans les autres cas affiche un message d'erreur.
	 *  
	 * @param  commande  Commande dont le second mot spécifie le nom de l'objet
	 *                   à poser
	 * @author Léo DECHAUMET
	 */
	
	public void poserObjetZork(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne possède pas de second mot, nous ne
			// savons pas quoi poser...
			System.out.println("Poser quoi ?");
			return;
		}
		
		//recherche l'objet dans notre sac.
		String objetAPoser = commande.getSecondMot();
		ObjetZork objetZork = sac.ObjetZorkCorrespondant(objetAPoser);
		
		//Si aucun objet dans le sac ne correspond au nom donné:
		if (objetZork == null) {
			System.out.println("Vous ne disposer pas de cet objet...");
			return;
		}
		
		else{
			pieceCourante.add_ObjetZork(objetZork);
			sac.jeterObjetZork(objetAPoser);
		}
	}
	
	/**
	 *  Si la commande possède un second mot : tente d'afficher une description
	 *  de l'objet spécifié par la commande. si le sac ou la pièce contient l'objet,
	 *  la commande affiche une description détaillé de l'objet. Si l'objet n'est pas
	 *  dans le sac ou la pièce affiche un message d'erreur.
	 *  Si la commande ne possède pas de second mot, alors une description détaillé
	 *  de l'état du jeu est afficher (description de la pièce, déscription des sorties,
	 *  contenu et état du sac.
	 *  
	 * @param  commande  Commande dont le second mot, si il existe, spécifie le nom de 
	 *                   l'objet à examiner
	 * @author Léo DECHAUMET
	 * 
	 */
	
	public void examiner(Commande commande){
		if (!commande.aSecondMot()) {
			// si la commande ne possède pas de second mot,
			// on affiche une description de la pièce courante.
			System.out.println("\n" + pieceCourante.descriptionLongue() + "\n" + sac);
			return;
		}
		
		if (commande.getSecondMot().equals("commande")){
			System.out.println("Voici un descriptif des commandes : ");
			analyseurSyntaxique.afficherAideCommande();
		}
		
		//recherche l'objet dans notre sac et dans la pièce.
		String objetAExaminer = commande.getSecondMot();
		ObjetZork objetZorkPiece = pieceCourante.ObjetZorkCorrespondant(objetAExaminer);
		ObjetZork objetZorkSac = sac.ObjetZorkCorrespondant(objetAExaminer);
		
		
		if (objetZorkPiece != null) {
			//Si un objet correspondant au nom se situe dans la pièce:
			//On affiche sa description
			System.out.println("L'objet suivant se situe dans la pièce : ");
			System.out.println(objetZorkPiece.descriptionLongue());
		}

		if (objetZorkSac != null) {
			//Si un objet correspondant au nom se situe dans le sac:
			//On affiche sa description
			System.out.println("L'objet suivant est dans votre inventaire");
			System.out.println(objetZorkSac.descriptionLongue());
		}
		
		if (objetZorkSac == null && objetZorkPiece == null){
			System.out.println("Aucun objet de ce nom ici");
		}
	}
	
	/**
	 *  Retourne dans la pièce précedente. La pièce précedente devient alors la
	 *  pièce courante. Dans le cas où le joueur n'a pas encore changer de pièce,
	 *  affiche seulement la description de la piece. Si le joueur appelle plusieurs
	 *  fois de suite la commande retour, la position du joueur alterne sur
	 *  deux pièce (pas d'historique des déplacement).
	 * 
	 * @author Léo DECHAUMET
	 */
	
	public void retour() {
		String t;
		t=null;
		
		//actualisation
		if (directionRetour != null){
			switch(directionRetour) {
				case "nord":
					t = "sud";
					break;
				case "sud":
					t = "nord";
					break;
				case "est":
					t = "ouest";
					break;
				case "ouest":
					t = "est";
					break;
			}
			pieceCourante = pieceCourante.pieceSuivante(directionRetour);
			Piece.set_pieceCourante(pieceCourante);
			directionRetour = t;
		}
		System.out.println(pieceCourante.descriptionLongue());
	}
	
	/**
	 *  Tente de réaliser l'action de l'objet spécifié par la commande.
	 *  si le sac ou la pièce contient l'objet, la commande réalise l'action de l'objet.
	 *  si l'objet a besoin d'une clé pour être actionné, la méthode verifie
	 *  si la clé est bien dans le sac du joueur. Si c'est le cas, l'objet est
	 *  actionné. Sinon, affiche un message d'erreur.
	 *  Sinon affiche un message d'erreur.
	 *  
	 * @param  commande  Commande dont le second mot, si il existe, spécifie le nom de 
	 *                   l'objet à examiner
	 * @author Léo DECHAUMET
	 */
	
	public void utiliser (Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne possède pas de second mot,
			// on ne sait pas quoi faire.
			System.out.println("utiliser quoi ?");
			return;
		}
		
		String objetAExaminer = commande.getSecondMot();
		ObjetZork objetZorkPiece = pieceCourante.ObjetZorkCorrespondant(objetAExaminer);
		ObjetZork objetZorkSac = sac.ObjetZorkCorrespondant(objetAExaminer);
		ObjetZork objetAUtiliser;
		if (objetZorkSac == null && objetZorkPiece == null){
			System.out.println("Aucun objet de ce nom ici");
			return;
		}
		
		else if (objetZorkSac != null) {
			objetAUtiliser = objetZorkSac;
		}

		else {
			objetAUtiliser = objetZorkPiece;
		}
		
		if (CorrespondanceCle.containsKey(objetAUtiliser)){
			// On entre dans la condition si l'objet à besoin d'une clé pour être utiliser
			if (!sac.contient(CorrespondanceCle.get(objetAUtiliser))) {
				// si on ne possède pas la clé on affiche un message d'erreur et on quitte la méthode
				System.out.println("Vous n'avez pas la clé pour actionner cet objet");
				return;
			}
			// Si on possède la clé on la supprime car elle ne servira plus (l'objet va être actionné à l'aide de la clé)
			sac.jeterObjetZork(CorrespondanceCle.get(objetAUtiliser).get_nom());
			pieceCourante.jeterObjetZork(objetAUtiliser.get_nom());
		}
		objetAUtiliser.actionne();
	}
	
	/**
	 *  fonction qui indique si le joueur a gagné la partie ou non.
	 *  Dans cette méthode doit être effectué les tests pour savoir si le
	 *  joueur est gagnant, ou si le jeu continue. La méthode est appelé
	 *  dans la méthode traiterCommande.
	 * 
	 * @return <code>true</code> si le joueur a gagné, <code>false</code> sinon.
	 * @author Léo DECHAUMET
	 */
	public boolean gagne() {
		
		//La situation gagnante:
		if (pieceCourante.contient(objetVictoire)) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Affiche le mesage de victoire.
	 *  Est appelée si et seulement si le joueur à gagner (donc la méthode
	 *  "gagne" renvoie true.
	 * 
	 * @author Léo DECHAUMET
	 */
	public void afficherVictoire() {
		System.out.println("Vous venez d'activer le portail");
		System.out.println("Il emmet un legé bruit ainsi qu'une petite lumière bleu");
		System.out.println("Vous entré à l'interieur, vous découvrez alors une planète totalement inconnus,");
		System.out.println("Bravo, vous avez gagné!");
	}
}

