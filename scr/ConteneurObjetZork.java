import java.util.ArrayList;


/**
 *  Classe abstraite, toutes les classes contenant des ObjetZorks
 *  doivent en hériter. Cette classes possède toutes les oppérations
 *  nécessaires pour gérer un tableau d'objet zork.
 * 
 * @author Léo DECHAUMET
 */
public abstract class ConteneurObjetZork{
	protected static int capacitePlace = Integer.MAX_VALUE;
	protected static int capacitePoids = Integer.MAX_VALUE;
	protected int taillePlace;
	protected int taillePoids;
	protected ArrayList<ObjetZork> listeObjetZork;
	

	
	
	/**
	 *  renvoie le nombre d'"ObjetZork" que contient le conteneur
	 * 
	 * @return  le nombre d'objet que contient le conteneur
	 */
	public int get_taillePlace(){
		return taillePlace;
	}
	
	/**
	 *  renvoie le poid total que pèse le conteneur
	 * 
	 * @return  le poids du sac
	 */
	public int get_taillePoids(){
		return taillePoids;
	}
	
	
	/**
	 *  ajoute l'objet passé en paramètre dans le conteneur
	 *  l'ajout de cet objet ne doit pas entrainer un dépassement de la capacité
	 *  du conteneur (pre condition)
	 * 
	 * @param  objZ l'objet à ajouter
	 */
	public void add_ObjetZork(ObjetZork objZ) {
		assert objZ.is_transportable() || capacitePoids==Integer.MAX_VALUE :
			"l'objet n'est pas transportable";
		assert taillePlace <= capacitePlace || capacitePlace==Integer.MAX_VALUE :
			"le sac est trop gros";
		assert objZ.get_Poids() <= capacitePoids - taillePoids || capacitePoids==Integer.MAX_VALUE :
			"le sac est trop lourd";
		listeObjetZork.add(objZ);
		taillePlace++;
		taillePoids += objZ.get_Poids();
	}
	
	/**
	 *  supprime l'objet passé en paramètre,
	 *  est appelée par la méthode "jeterObjetZork"
	 * 
	 * @param indice  l'indice de l'objet à retirer
	 * @return        l'objet suprimmé
	 */
	private ObjetZork jeterObjetZork(int indice) {
		taillePlace--;
		ObjetZork objZ_t = listeObjetZork.remove(indice);
		taillePoids -= objZ_t.get_Poids();
		return objZ_t;
	}
	
	/**
	 *  Retire l'objet correspondant au nom passé en paramètre,
	 *  l'objet en question est renvoyé par la méthode.
	 * 
	 * @param  nom  le nom de l'objet à retirer
	 * @return      l'objet suprimmé
	 */
	
	public ObjetZork jeterObjetZork(String nom) {
		return jeterObjetZork(listeObjetZork.lastIndexOf(ObjetZorkCorrespondant(nom)));
	}
	
	/**
	 *  Retire l'objet passé en paramètre,
	 *  l'objet en question est renvoyé par la méthode.
	 * 
	 * @param  obj  L'objet à retirer
	 * @return      l'objet suprimmé
	 */
	public ObjetZork jeterObjetZork(ObjetZork obj) {
		return jeterObjetZork(listeObjetZork.lastIndexOf(obj));
	}
	
	
	/**
	 *  Renvoie l'objet correspondant au nom passé en paramètre, si un
	 *  objet dans notre conteneur possède un nom égal à celui passé en paramètre.
	 *  Si ce n'est pas le cas, renvoie null
	 * 
	 * @param  nom  le nom de l'objet à retrouvé dans le conteneur
	 * @return      Un "ObjetZork" correspondant au nom
	 */
	public ObjetZork ObjetZorkCorrespondant(String nom) {
		for (ObjetZork elt : listeObjetZork) {
			if (elt.get_nom().equals(nom)) {
				return elt;
			}
		}
		return null;
	}
	
	/**
	 *  Teste si l'<code>ObjetZork</code> passé en parametre est dans
	 *  le conteneur.
	 * 
	 * @param  objt	L'objet à trouver
	 * @return      <code>true</code> si l'objet est dans le sac, <code>false</code> sinon
	 */
	public boolean contient(ObjetZork objt) {
		if (listeObjetZork.indexOf(objt) == -1) {
			return false;
		}
		return true;
	}
}
