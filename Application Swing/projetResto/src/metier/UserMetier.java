package metier;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import controller.RoleDao;
import controller.UserDao;
import model.Role;
import model.User;

public class UserMetier {
	/*
	 * instanciation Class userDao et roleDao
	 */
	UserDao userDao = new UserDao();
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

	public boolean verifMailRegix(String email) {
		String regex = "^[A-Za-z0-9][A-Za-z0-9.-_]+[A-Za-z0-9][@][A-Za-z0-9][A-Za-z0-9.-_]+[A-Za-z0-9]?[\\.][A-Za-z0-9]{2,3}$";
		Pattern patternVerif = Pattern.compile(regex);
		Matcher matcher = patternVerif.matcher(email);
		return matcher.matches();
	}

	public DefaultComboBoxModel selectCmb() {
		DefaultComboBoxModel itemCmb = new DefaultComboBoxModel();
		ArrayList<Role> listRoles = new ArrayList();
		listRoles = roleDao.selectRoles();
		for (Role item : listRoles) {
			itemCmb.addElement(new Role(item.getId(), item.getNom()));
		}

		return itemCmb;
	}

	public DefaultTableModel lister(String txt) {
		String col[] = { "id", "Nom", "Prenom", "email", "url", "IdRol", "Role", "Statut" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (User item : userDao.read(txt)) {
			list.addRow(new Object[] { item.getId(), item.getNom(), item.getPrenom(), item.getEmail(), item.getUrl(),
					item.getId_role().getId(), item.getId_role().getNom(), item.getStatut() });
			totalM++;
		}
		return list;
	}

}
