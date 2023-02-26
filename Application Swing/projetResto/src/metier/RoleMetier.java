package metier;

import javax.swing.table.DefaultTableModel;

import controller.RoleDao;
import model.Role;

public class RoleMetier {
	/*
	 * instanciation Class roleDao
	 */
	RoleDao roleDao = new RoleDao();
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
		String col[] = { "id", "Titre", "Description", "Statut" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (Role item : roleDao.read(txt)) {
			list.addRow(new Object[] { item.getId(), item.getNom(), item.getDescription(), item.getStatut() });
			totalM++;
		}
		return list;
	}
}
