package model;

public class Detail_commande {
	private int id;
	private Commande id_commande;
	private Produit id_produit;
	private int quantite;
	private Double prix_unitaire;

	/*
	 * Constructeur complet pour le findByID deatil_commande
	 */
	public Detail_commande(int id, Commande id_commande, Produit id_produit, int quantite, Double prix_unitaire) {
		this.id = id;
		this.id_commande = id_commande;
		this.id_produit = id_produit;
		this.quantite = quantite;
		this.prix_unitaire = prix_unitaire;
	}

	/*
	 * constructeur pour la methode create() qui sert a hydrater la base de donnée
	 * detail_commande
	 */

	public Detail_commande(Commande id_commande, Produit id_produit, Double prix_unitaire) {
		this.id_commande = id_commande;
		this.id_produit = id_produit;
		this.prix_unitaire = prix_unitaire;
	}

	/*
	 * constructeur pour instancier un detail lors de la prise de commande
	 */
	public Detail_commande(Commande id_commande, Produit id_produit) {
		this.id_commande = id_commande;
		this.id_produit = id_produit;
	}

	/*
	 * generation getters setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Commande getId_commande() {
		return id_commande;
	}

	public void setId_commande(Commande id_commande) {
		this.id_commande = id_commande;
	}

	public Produit getId_produit() {
		return id_produit;
	}

	public void setId_produit(Produit id_produit) {
		this.id_produit = id_produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Double getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(Double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}

}
