/**
 * <p>Objet qui contient un autre objet.</p>
 * Cette classe est immuable.
 * @author  Léo DECHAUMET
 *
 */


public class ObjetZorkCle extends ObjetZork{
	private final ObjetZork objCache;  //L'objet à débloqué
	
	/**
	 * Constructeur ObjetZorkCle
	 * @param  nom               nom de l'objet
	 * @param  description       description de l'objetZork
	 * @param  poids             poids de l'ObjetZork (en kg)
	 * @param  ApresUtilisation  Ce par quoi doit être remplacé l'objet 
	 *                           après son utilisation
	 * @param  objCache          l'objet à ajouter quand l'objet est utilisé.
	 */
	public ObjetZorkCle(String nom, String description, int poids, ObjetZork ApresUtilisation, ObjetZork objCache){
		super(nom, description, poids, ApresUtilisation);
		this.objCache = objCache;
	}
	
	/**
	 * Place un objet dans la pièce courante et détruit cet objet
	 * si il doit être détruit après son utilisation.
	 * Suppose qu'on possède la clé pour dévérouiller l'objet.
	 */
	@Override
	public void actionne(){

		System.out.println("Vous avez débloqué un nouvel objet : ");
		System.out.println((objCache).descriptionLongue());
		super.changerApresUtilisation();
		Piece.get_pieceCourante().add_ObjetZork(objCache);
		
	}
}
