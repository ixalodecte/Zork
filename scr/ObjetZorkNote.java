/**
 * <p>ObjetZorkNote est une classe qui hérite de ObjetZork</p>
 * <p>Les "ObjetZorkNote"s sont des objets qui possèdent en plus un
 * attribut de type texte, lorsqu'il sont utilisés ils affichent
 * cet attribut à l'écran.</p>
 * <p>Cette classe est immuable, après initialisation vous ne pouvez
 * plus la modifier</p>
 * @author Léo Dechaumet
 * 
 */

public class ObjetZorkNote extends ObjetZork{
	private final String note;
	
	/**
	 * Constructeur ObjetZorkCle
	 * @param  nom               nom de l'objet
	 * @param  description       description de l'objetZork
	 * @param  poids             poids de l'ObjetZork (en kg)
	 * @param  ApresUtilisation  Ce par quoi doit être remplacé l'objet 
	 *                           après son utilisation
	 * @param                    note ce qui est à afficher quand l'objet est utilisé
	 */
	public ObjetZorkNote(String nom, String description, int poids, ObjetZork ApresUtilisation, String note){
		super(nom, description, poids, ApresUtilisation);
		this.note = note;
	}
	
	/**
	 * Méthode qui lit ce qui est écrit sur la note.
	 * Détruit la feuille si elle doit être détruite après son utilisation
	 */
	@Override
	public void actionne(){
		System.out.println("Il y est écrit :");
		System.out.println(note);
		super.changerApresUtilisation();
	}
}
