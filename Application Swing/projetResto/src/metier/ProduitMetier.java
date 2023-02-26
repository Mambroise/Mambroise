package metier;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import controller.Cat_produitDao;
import controller.ProduitDao;
import model.Cat_produit;
import model.Produit;
import model.Role;

public class ProduitMetier {
	ProduitDao prodD = new ProduitDao();
	Cat_produitDao catprodD = new Cat_produitDao();
	/*
	 * instanciation variable qui compte le nbr total de registres dans la bdd
	 */
	public int totalM;

	/*
	 * méthode qui affiche la iste la bdd produit et l'affiche dans vue Produit
	 * gestion
	 * 
	 * @param txt: contenu de la barre recherche si on faire un findByName
	 */
	public DefaultTableModel lister(String txt, String tri) {
		String col[] = { "id", "Nom", "Description", "Code", "Categorie", "Type", "Prix", "modifiant", "Date-modif",
				"statut" };
		DefaultTableModel tableau = new DefaultTableModel(null, col);
		totalM = 0;
		for (Produit item : prodD.filtrer(txt, tri)) {
			tableau.addRow(new Object[] { item.getId(), item.getNom(), item.getDescription(), item.getCode(),
					item.getId_cat_produit().getNom(), item.getType_statut(), item.getPrix(),
					item.getId_user().getNom(), item.getDate_mod(), item.getStatut() });
			totalM++;
		}
		return tableau;
	}

	/*
	 * methode qui affiche les produits lors de la commande
	 */
	public DefaultTableModel listeProdCommande(String code, String nom) {
		String col[] = { "id", "Nom", "Description", "Code", "Categorie", "Prix" };
		DefaultTableModel tableau = new DefaultTableModel(null, col);
		totalM = 0;
		for (Produit item : prodD.findByCode(code, nom)) {
			tableau.addRow(new Object[] { item.getId(), item.getNom(), item.getDescription(), item.getCode(),
					item.getId_cat_produit().getNom(), item.getPrix() });
			totalM++;
		}
		return tableau;
	}

	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel selectCmb() {
		DefaultComboBoxModel itemCmb = new DefaultComboBoxModel();
		ArrayList<Cat_produit> listCat = new ArrayList<>();
		listCat = catprodD.selectRoles();
		for (Cat_produit item : listCat) {
			itemCmb.addElement(new Cat_produit(item.getId(), item.getNom()));
		}
		return itemCmb;
	}

}
