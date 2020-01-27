

/**
 *  <p>ObjetZork est une classe du jeu Zork</p>
 *  <p>Les "ObjetZork"s sont des objets disséminé dans les pièces du jeu,
 *  ils possèdent plusieurs attributs comme leur poids ou leur nom, vous pouvez
 *  éventuellement les transporter s'ils sont "transportable".</p>
 *  <p>Cette classe est immuable, après initialisation vous ne pouvez
 *  plus la modifier</p>
 *  Les classes filles doivent être aussi immuables.
 *  Classe commencé en TP, puis enrichie.
 * 
 * @author Léo Dechaumet
 * 
 */
public class ObjetZork {
	/**
	 * Declaration des attributs de la classe
	 */
	 
	private final String nom;				//le nom de l'objet
	private final String description; 		//une courte description de l'objet
	private final int poids;				//le poids de l'objet
	private final boolean transportable;	//transportabilité de l'objet
	private final ObjetZork ApresUtilisation;		//Objet qui doit remplacer cet objet après son utilisation
	
	/**
	 *  Constructeur ObjetZork, Avec definition du poids
	 * @param  nom               nom de l'objet
	 * @param  description       description de l'objetZork
	 * @param  poids             poids de l'ObjetZork (en kg)
	 * @param  ApresUtilisation  Ce par quoi doit être remplacé l'objet 
	 *                           après son utilisation.
	 */
	 
	public ObjetZork(String nom, String description, int poids, ObjetZork ApresUtilisation){
		this.nom = nom;
		this.description = description;
		
		if (poids != -1){
			this.poids=poids;
			this.transportable = true;
		}
		else {
			this.poids = Integer.MAX_VALUE;
			this.transportable = false;
		}
		this.ApresUtilisation = ApresUtilisation;
	}
	
	
	/**
	 * @return description courte de l'ObjetZork
	 */
	public String get_description(){
		return description;
	}
	
	/**
	 * @return Renvoie le poids de l'ObjetZork (un entier positif)
	 * ou la valeur <code>Integer.MAX_VALUE</code>, si l'objet n'est pas transportable
	 */
	public int get_Poids(){
		return poids;
	}
	
	/**
	 * @return  le nom de l'ObjetZork
	 */
	public String get_nom(){
		return nom;
	}
	
	/**
	 * @return true si l'objet est transportable, false sinon.
	 */
	public boolean is_transportable() {
		return transportable;
	}
	
	/**
		renvoie l'objet qui doit remplacer l'objet après son utilisation
	 */
	public ObjetZork get_ApresUtilisation() {
		return ApresUtilisation;
	}
	
	/**
	 * Methode qui renvoie la description d'un ObjetZork
	 * Si l'objet est transportable, alors sa description contient son poids
	 * Si l'objet n'est pas transportable, la mention "non transportable" est ajouté
	 * @return descriptionLongue retourne la description détaillé de l'ObjetZork
	 */
	public String descriptionLongue() {
		String descriptionLongue = description + "(";
		
		if(transportable) {
			descriptionLongue += poids;
		}
		
		else {
			descriptionLongue += "non transportable";
		}
		return descriptionLongue + ")";
	}

	
	/**
	 *  Change l'objet par celui defini dans le constructeur dans le sac
	 *  s'il est situé dans le sac, ou dans la piece courante s'il est situé
	 *  dans la pièce courante.
	 *  Si l'objet passé en paramètre dans le constructeur est <code>null</code>,
	 *  alors l'objet est supprimé du sac, ou de la pièce.
	 *  Cette méthode doit être appelé par les classes filles dans la méthode
	 *  actionne.
	 */
	protected void changerApresUtilisation(){
		if (Sac.getInstance().contient(this)){
			Sac.getInstance().jeterObjetZork(this);
			if (ApresUtilisation != null){
				Sac.getInstance().add_ObjetZork(ApresUtilisation);
			}
		}
		
		if (Piece.get_pieceCourante().contient(this)){
			Piece.get_pieceCourante().jeterObjetZork(this);
			if (ApresUtilisation != null){
				Piece.get_pieceCourante().add_ObjetZork(ApresUtilisation);
			}
		}
	}
	
	/** 
	 *  méthode qui doit être redéfinie dans les classe fille.
	 *  La méthode redefinie doit appeler la méthode changerApresUtilisation.
	 */
	public void actionne() {
		System.out.println("Cet objet ne possède pas d'action spécifique");
	}
	
}


