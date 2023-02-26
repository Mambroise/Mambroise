package model;

import java.sql.Timestamp;

public class Produit {
	private int id;
	private Cat_produit id_cat_produit;
	private User id_user;
	private String code;
	private String nom;
	private String Type_statut;
	private String description;
	private Double prix;
	private Timestamp date_mod;
	private String statut;

	/*
	 * @author Moris constructeur complet
	 */

	public Produit(int id, Cat_produit id_cat_produit, User id_user, String code, String nom, String type_statut,
			String description, Double prix, Timestamp date_mod, String statut) {
		this.id = id;
		this.id_cat_produit = id_cat_produit;
		this.id_user = id_user;
		this.code = code;
		this.nom = nom;
		Type_statut = type_statut;
		this.description = description;
		this.prix = prix;
		this.date_mod = date_mod;
		this.statut = statut;
	}

	/*
	 * contructeur du create
	 */
	public Produit(Cat_produit id_cat_produit, User id_user, String code, String nom, String type_statut,
			String description, Double prix) {
		this.id_cat_produit = id_cat_produit;
		this.id_user = id_user;
		this.code = code;
		this.nom = nom;
		Type_statut = type_statut;
		this.description = description;
		this.prix = prix;
	}

	public Produit(int id, Cat_produit id_cat_produit, User id_user, String code, String nom, String type_statut,
			String description, Double prix) {
		this.id = id;
		this.id_cat_produit = id_cat_produit;
		this.id_user = id_user;
		this.code = code;
		this.nom = nom;
		Type_statut = type_statut;
		this.description = description;
		this.prix = prix;
	}
	/*
	 * contructeur du Activer/desactiver
	 */

	public Produit(int id, User id_user) {
		this.id = id;
		this.id_user = id_user;
	}

	/*
	 * constructeur pour la methode qui liste les produits par code et nom
	 */
	public Produit(int id, Cat_produit id_cat_produit, String code, String nom, String description, Double prix,
			String statut) {

		this.id = id;
		this.id_cat_produit = id_cat_produit;
		this.code = code;
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.statut = statut;
	}

	/*
	 * constructeur pour la methode findById du detail de commande
	 */
	public Produit(int id, Cat_produit id_cat_produit, String nom, String type_statut, String description) {
		this.id = id;
		this.id_cat_produit = id_cat_produit;
		this.nom = nom;
		Type_statut = type_statut;
		this.description = description;
	}

	/*
	 * constructeurlors de l'instanciation d'un objet produit lors de la création
	 * d'une ligne de Détail_commande VueCommande ligne 322
	 */

	public Produit(int id, User id_user, Double prix) {
		this.id = id;
		this.id_user = id_user;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cat_produit getId_cat_produit() {
		return id_cat_produit;
	}

	public void setId_cat_produit(Cat_produit id_cat_produit) {
		this.id_cat_produit = id_cat_produit;
	}

	public User getId_user() {
		return id_user;
	}

	public void setId_user(User id_user) {
		this.id_user = id_user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType_statut() {
		return Type_statut;
	}

	public void setType_statut(String type_statut) {
		Type_statut = type_statut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Timestamp getDate_mod() {
		return date_mod;
	}

	public void setDate_mod(Timestamp date_mod) {
		this.date_mod = date_mod;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Produit [nom=" + nom + ", description=" + description + ", prix=" + prix + "]";
	}

}
