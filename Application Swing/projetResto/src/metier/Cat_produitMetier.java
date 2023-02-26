package metier;

import javax.swing.table.DefaultTableModel;
import controller.Cat_produitDao;
import model.Cat_produit;

public class Cat_produitMetier {
	/*
	 * instanciation Class roleDao
	 */
	Cat_produitDao CatProdD = new Cat_produitDao();
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
		String col[] = { "id", "nom", "Description" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (Cat_produit item : CatProdD.read(txt)) {
			list.addRow(new Object[] { item.getId(), item.getNom(), item.getDescription(), });
			totalM++;
		}
		return list;
	}

}
