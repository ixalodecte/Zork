

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ObjetZorkCode extends ObjetZork{
	private final ObjetZork objCache;
	private final String password;
	
	/**
	 *  Constructeur ObjetZorkCle
	 * 
	 * @param  nom               nom de l'objet
	 * @param  description       description de l'objetZork
	 * @param  poids             poids de l'ObjetZork (en kg)
	 * @param  ApresUtilisation  Ce par quoi doit être remplacé l'objet 
	 *                           après son utilisation
	 * @param  objCache	         l'objet à ajouter quand l'objet est utilisé,
	 *                           et que le code entré est correct.
	 * @param  password	         mot de passe correct, pour actionner l'objet
	 */
	public ObjetZorkCode(String nom, String description, int poids, ObjetZork ApresUtilisation, ObjetZork objCache, String password){
		super(nom, description, poids, ApresUtilisation);
		this.objCache = objCache;
		this.password = password;
	}
	
	/**
	 * Demande un code à l'utilisateur. Si ce code est conforme à celui de l'objet,
	 * un objet sera placé dans la pièce courante et cet objet sera détruit
	 * si il doit être détruit après son utilisation.
	 * sinon affiche le message "<code>    XXX  code incorect  XXX   </code>",
	 * et l'objet n'est pas détruit.
	 */
	@Override
	public void actionne(){
		// Capture du code entré par l'utilisateur
		// pour mémoriser la ligne entrée par l'utilisateur
		String ligneEntree = "";


		// affiche l'invite de commande
		System.out.print(">>> Code : ");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));
		try {
			ligneEntree = reader.readLine();
		} catch (java.io.IOException exc) {
			System.out.println("Une erreur est survenue pendant la lecture de votre commande: "
				 + exc.getMessage());
		}
		
		if (ligneEntree.equals(password)){
			System.out.println("    -->  code corect  <--   ");
			System.out.println("Vous avez débloqué un nouvel objet : ");
			System.out.println((objCache).descriptionLongue());
			super.changerApresUtilisation();
			Piece.get_pieceCourante().add_ObjetZork(objCache);
		}
		else{
			System.out.println("    XXX  code incorect  XXX   ");
		}
	}
}
