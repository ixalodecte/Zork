/**
 *  <p>
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Classe répertoriant l'ensemble des mots-clé utilisables comme commande dans
 *  le jeu. Elle est utilisée pour vérifier la validité des commandes de
 *  l'utilisateur.
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @version    1.0
 * @since      July 1999
 */

public class MotCleCommande {
	/**
	 *  Un tableau constant contenant tous les mots-clé valides comme commandes.
	 */
	private final static String commandesValides[] = {"aller", "retour", "prendre", "poser", "examiner", "utiliser", "quitter", "aide"};

	private final static String descriptifCommande[] = {
		"aller : se deplace dans la piece situé dans la direction indiqué par le paramètre (nord, est, ouest ou sud)",
		"retour : se deplace dans la piece situé dans la direction opposé au précédent déplacement",
		"prendre : prend l'objet passé en parametre de la commande",
		"poser : pose l'objet passé en parametre de la commande dans la piece courante",
		"examiner : decrit l'objet passé en paramètre, si aucun paramètre n'est précisé affiche l'état du jeu",
		"utiliser : réalise l'action de l'objet passé en paramètre",
		"quitter : quitte le jeu",
		"aide : affiche l'aide"};
	/**
	 *  Initialise la liste des mots-clé utilisables comme commande.
	 */
	public MotCleCommande() { }


	/**
	 *  Teste si la chaine de caractères spécifiée est un mot-clé de commande
	 *  valide. Check whether a given String is a valid command word. Return true
	 *  if it is, false if it isn't.
	 *
	 * @param  aString  Chaine de caractères a tester
	 * @return          true si le paramètre est une commande valide, false sinon
	 */
	public boolean estCommande(String aString) {
		for (int i = 0; i < commandesValides.length; i++) {
			if (commandesValides[i].equals(aString)) {
				return true;
			}
		}
		return false;
	}


	/**
	 *  Affiche toutes les commandes (i.e. les mots-clé) valides.
	 */
	public void afficherToutesLesCommandes() {
		for (int i = 0; i < commandesValides.length; i++) {
			System.out.print(commandesValides[i] + "  ");
		}
		System.out.println();
	}
	
	public void afficherAideCommande() {
		for (int i = 0; i < descriptifCommande.length; i++) {
			System.out.println(" - " + descriptifCommande[i]);
		}
	}
}

