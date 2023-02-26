package metier;

import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import controller.ClientDao;
import model.Client;

public class ClientMetier {
	/*
	 * instanciation Class roleDao
	 */
	ClientDao clientD = new ClientDao();
	/*
	 * instanciation variable qui compte le nbr total de registres dans la bdd
	 */
	public int totalM = 0;
	/*
	 * méthode qui affiche la iste la bdd role et l'affiche dans vue role
	 * 
	 * @param txt: contenu de la barre recherche si on faire un findByName
	 */

	public DefaultTableModel lister(String txt) {
		String col[] = { "id", "Nom", "Prenom", "Telephone", "Statut" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (Client item : clientD.read(txt)) {
			list.addRow(
					new Object[] { item.getId(), item.getNom(), item.getPrenom(), item.getNumtel(), item.getStatut() });
			totalM++;
		}
		return list;
	}

	/*
	 * méthode qui affiche la iste de client ayant le numero de telephone du
	 * parametre
	 * 
	 * @param txt: num telephone du client lors de la prise de commande
	 */
	public DefaultTableModel clientExist(String tel) {
		String col[] = { "id", "Nom", "Prenom" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (Client item : clientD.findByNum(tel)) {
			list.addRow(new Object[] { item.getId(), item.getNom(), item.getPrenom()

			});
			System.out.println(item);
			totalM++;
		}
		return list;
	}

	/*
	 * methode de vérification du numero de téléphone: savoir s'il est au format
	 * francais
	 */
	public Boolean checkRegexTel(String tel) {
		System.out.println(tel);
		Boolean test = Pattern.matches("(0)[1-9]([-. ]?[0-9]{2}){4}", tel);
		System.out.println(test);
		return test;

	}
}
