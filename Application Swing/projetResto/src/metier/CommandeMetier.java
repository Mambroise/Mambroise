package metier;

import javax.swing.table.DefaultTableModel;

import controller.CommandeDao;
import model.Commande;

public class CommandeMetier {
	/*
	 * instanciation Class roleDao
	 */
	CommandeDao commandeD = new CommandeDao();
	/*
	 * instanciation variable qui compte le nbr total de registres dans la bdd
	 */
	public int totalM;
	/*
	 * méthode qui affiche la iste la bdd role et l'affiche dans vue role
	 * 
	 * @param txt: contenu de la barre recherche si on faire un findByName
	 */

	public DefaultTableModel lister(String txt) {
		String col[] = { "N° Commande", "Client", "Hors Taxes", "Total", "Type de Paiement", "Etat commande",
				"idClient" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode read(), qui devient un
		 * findByName
		 */
		for (Commande item : commandeD.read(txt)) {
			list.addRow(new Object[] { item.getId(),
					item.getId_client().getNom() + " " + item.getId_client().getPrenom(), item.getTotalHt(),
					item.getTotal(), item.getType_paiement(), item.getEtat(), item.getId_client().getId() });
			totalM++;
		}
		return list;
	}

	public DefaultTableModel listerArchive(String txt) {
		String col[] = { "N° Commande", "Client", "Total", "Type de Paiement", "Etat commande", "Date Commande" };
		DefaultTableModel list = new DefaultTableModel(null, col);
		totalM = 0;
		/*
		 * injection du param txt (recherche dans méthode achive(), qui devient un
		 * findByID si besoin
		 */
		for (Commande item : commandeD.archive(txt)) {
			list.addRow(
					new Object[] { item.getId(), item.getId_client().getNom() + " " + item.getId_client().getPrenom(),
							item.getTotal(), item.getType_paiement(), item.getEtat(), item.getDate_comm() });
			totalM++;
		}
		return list;
	}
}
