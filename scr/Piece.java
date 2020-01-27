import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *  Une piece dans un jeu d'aventure. <p>
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Une "Piece" represente un des lieux dans lesquels se déroule l'action du
 *  jeu. Elle est reliée a au plus quatre autres "Piece" par des sorties. Les
 *  sorties sont étiquettées "nord", "est", "sud", "ouest". Pour chaque
 *  direction, la "Piece" possède une référence sur la piece voisine ou null
 *  s'il n'y a pas de sortie dans cette direction.</p>
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @version    1.1
 * @since      August 2000
 */

public class Piece extends ConteneurObjetZork{
	
	private static Piece pieceCourante;
	private String description;

	// mémorise les sorties de cette piece.
	private Map<String, Piece> sorties;


	/** 
	 * @return la piece 
	 */
	public static Piece get_pieceCourante() {
		return pieceCourante;
	}
	
	public static void set_pieceCourante(Piece p) {
		pieceCourante = p;
	}

	/**
	 *  Initialise une piece décrite par la chaine de caractères spécifiée.
	 *  Initialement, cette piece ne possède aucune sortie. La description fournie
	 *  est une courte phrase comme "la bibliothèque" ou "la salle de TP".
	 *
	 * @param  description  Description de la piece.
	 */
	public Piece(String description) {
		this.description = description;
		sorties = new HashMap<String, Piece>();
		listeObjetZork = new ArrayList<ObjetZork>();
	}


	/**
	 *  Définie les sorties de cette piece. A chaque direction correspond ou bien
	 *  une piece ou bien la valeur null signifiant qu'il n'y a pas de sortie dans
	 *  cette direction.
	 *
	 * @param  nord   La sortie nord
	 * @param  est    La sortie est
	 * @param  sud    La sortie sud
	 * @param  ouest  La sortie ouest
	 */
	public void setSorties(Piece nord, Piece est, Piece sud, Piece ouest) {
		if (nord != null) {
			sorties.put("nord", nord);
		}
		if (est != null) {
			sorties.put("est", est);
		}
		if (sud != null) {
			sorties.put("sud", sud);
		}
		if (ouest != null) {
			sorties.put("ouest", ouest);
		}
	}


	/**
	 *  Renvoie la description de cette piece (i.e. la description spécifiée lors
	 *  de la création de cette instance).
	 *
	 * @return    Description de cette piece
	 */
	public String descriptionCourte() {
		return description;
	}


	/**
	 *  Renvoie une description de cette piece mentionant ses sorties et
	 *  directement formatée pour affichage, de la forme: <pre>
	 *  Vous etes dans la bibliothèque.
	 *  Sorties: nord ouest</pre> Cette description utilise la chaine de caractères
	 *  renvoyée par la méthode descriptionSorties pour décrire les sorties de
	 *  cette piece.
	 *
	 * @return    Description affichable de cette piece
	 */
	public String descriptionLongue() {
		return "Vous etes dans " + description + ".\n" + descriptionObjetZork() + ".\n" +  descriptionSortiesLongue();
	}


	/**
	 *  Renvoie une description des sorties de cette piece, de la forme: <pre>
	 *  Sorties: nord ouest</pre>
	 *
	 * @return    Une description des sorties de cette pièce.
	 */
	public String descriptionSorties() {
		String resulString = "Sorties:";
		Set<String> keys = sorties.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
			resulString += " " + iter.next();
		}
		return resulString;
	}
	
	/**
	 *  Renvoie une description longues des sorties de cette piece,
	 *  de la forme: <pre>Sorties: nord : bibliotheque ouest :
	 *  taverne</pre> Cette description est utilisée dans la
	 *  description longue d'une piece.
	 *
	 * @return  Une description des sorties de cette pièce.
	 * @author  Léo DECHAUMET
	 */
	public String descriptionSortiesLongue() {
		String resulString = "";
		String key;
		Set<String> keys = sorties.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
			key = iter.next();
			resulString += key + " : " + sorties.get(key).descriptionCourte() + "\n";
		}
		return resulString;
	}
	
	/**
	 *  Renvoie une description des objets de cette piece, de la forme: <pre>
	 *  Objet présent: [description longue d'un objetZork]</pre> ou <pre>Aucun Objet ici...</pre> Cette description est utilisée dans la
	 *  description longue d'une piece.
	 *
	 * @return  Une description des objets de cette pièce.
	 * @author  Léo DECHAUMET
	 */
	public String descriptionObjetZork() {
		String resulString;
		if (!listeObjetZork.isEmpty()) {
			resulString = "Objet présent: \n";
			for (ObjetZork objZ : listeObjetZork){
				if (objZ.is_transportable()){ 
					resulString += "  - " + objZ.get_nom() + "   (" + objZ.get_Poids() + ")\n";
				}
				else{
					resulString += "  - " + objZ.get_nom() + "   (" + "non transportable" + ")\n";
				}
			}
		}
		else {
			resulString = "Aucun Objet ici..";
		}
		return resulString;
	}


	/**
	 *  Renvoie la piece atteinte lorsque l'on se déplace a partir de cette piece
	 *  dans la direction spécifiée. Si cette piece ne possède aucune sortie dans cette direction,
	 *  renvoie null.
	 *
	 * @param  direction  La direction dans laquelle on souhaite se déplacer
	 * @return            Piece atteinte lorsque l'on se déplace dans la direction
	 *                    spécifiée ou null.
	 */
	public Piece pieceSuivante(String direction) {
		return sorties.get(direction);
	}

}
