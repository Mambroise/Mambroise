package metier;

import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import controller.DetailDao;
import model.Detail_commande;
import model.Produit;

public class DetailMetier {
	DetailDao detailD = new DetailDao();

	/*
	 * instanciation variable qui compte le nbr total de registres dans la bdd
	 */
	public int totalM;

	public DefaultTableModel listeDetail(int id) {
		String col[] = { "id", "Commande N°", "Produit", "Catégorie", "Quantité", "Prix unitaire" };
		DefaultTableModel tableau = new DefaultTableModel(null, col);

		for (Detail_commande item : detailD.findById(id)) {
			tableau.addRow(new Object[] { item.getId(), item.getId_commande().getId(), item.getId_produit().getNom(),
					item.getId_produit().getId_cat_produit().getNom(), item.getQuantite(), item.getPrix_unitaire() });
			totalM++;
		}
		return tableau;
	}

	/*
	 * methode pour lister les details d'une commande selectionnée, en insistant sur
	 * la description des produit à livrer
	 */
	public DefaultTableModel listeDetail1(int id) {
		String col[] = { "id", "Catégorie", "Produit", "Type de produit", "Quantité", "Description" };
		DefaultTableModel tableau = new DefaultTableModel(null, col);
		totalM = 0;
		for (Detail_commande item : detailD.findById(id)) {
			tableau.addRow(new Object[] { item.getId(), item.getId_produit().getId_cat_produit().getNom(),
					item.getId_produit().getNom(), item.getId_produit().getType_statut(), item.getQuantite(),
					item.getId_produit().getDescription() });
			totalM++;
		}
		return tableau;
	}

	/*
	 * Methode de verification pour le changement de quantite de produit dans la
	 * commande. seul les chifres sont autorisés
	 */
	public Boolean checkRegexQte(String txt) {
		Boolean test = Pattern.matches("[1-9]", txt);
		return test;

	}
}
