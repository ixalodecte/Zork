import java.util.ArrayList;

/**
 *  <p>Sac est une classe est une classe du jeu Zork</p>
 *  <p>Le "Sac" est une classe qui, dans ce jeu, ne possède qu'une seule
 *  instance. Il contient tout les objets que le joueurs possède, ces
 *  objets sont stocké dans un tableau dynamique (ArrayList)
 *  Sa capacité est limité.</p>
 *  
 * @author Léo Dechaumet
 * 
 */

public class Sac extends ConteneurObjetZork {
	
	private static Sac singletonSac = new Sac();
	
	static int capacitePlace = 10;
	static int capacitePoids = 100;

	
	/**
	 *  Initialise un sac vide.
	 */
	private Sac() {
		taillePlace = 0;
		taillePoids = 0;
		listeObjetZork = new ArrayList<ObjetZork>();
	}
	
	/**
	 *  fonction utilisé pour accéder au singleton sac
	 * 
	 * @return la seul instance de la classe Sac
	*/
	public static Sac getInstance(){
		return singletonSac;
	}
	

	/**
	 *  Affiche le contenu du sac. Tous les objets du sac sont
	 *  afficher séparé par un espace, puis un saut de ligne à la fin.
	 */
	public void AfficherContenu(){
		for (ObjetZork elt : listeObjetZork) {
			System.out.print(elt.get_nom() + "  ");
		}
		System.out.println(" ");
	}
	
	/**
	 *  Redefinition de la méthode "toString" de la classe Object
	 *  renvoie une chaine de charactère décrivant l'état du sac, au format:
	 *  Vous pouvez transporter 10 objet, vous possédez actuellement 1 objet.<br>
	 *  Vous pouvez transporter un poids maximal de 100, vous possédez actuellement un poid de 10<br>
	 *  Voici la liste des objets que vous transportez :<br>
	 *	ciseaux    10
	 * 
	 * @return	la chaine décrivant le sac
	 */
	
	@Override
	public String toString(){
		String resul;
		if (!listeObjetZork.isEmpty()){
			resul = "Vous pouvez transporter " + capacitePlace + " objet, vous possédez actuellement " + taillePlace + " objet.\n";
			resul += "Vous pouvez transporter un poids maximal de " + capacitePoids + ", vous possédez actuellement un poid de " + taillePoids + "\n";

			resul += "Voici la liste des objets que vous transportez : \n";
			for (ObjetZork elt : listeObjetZork) {
				resul += elt.get_nom() + "    " + elt.get_Poids() + "\n";
			}
		}
		else{
			resul = "Vous ne transportez aucun objet\n";
		}
		return resul;
		
	}
}
