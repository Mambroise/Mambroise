package metier;

import javax.swing.table.DefaultTableModel;

import controller.AdresseDao;
import controller.ClientDao;
import model.Adresse;
import model.Client;

public class AdresseMetier {
	/*
	 * instanciation Class roleDao
	 */
	AdresseDao adresseD = new AdresseDao();
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

		String col[] = { "Id", "rue", "CodPostal", "Ville", "Complement" };

		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		int cont = 0;
		for (Adresse item : adresseD.read(txt)) {

			list.addRow(new Object[] {

					item.getId(), item.getRue(), item.getCod_postal(), item.getVille(), item.getComplement()

			});
			System.out.println(item);
			totalM++;
		}
		System.out.println(list);
		return list;
	}
}
