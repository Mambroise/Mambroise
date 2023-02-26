package model;

public class Cat_produit {
	private int id;
	private String nom;
	private String description;

	/*
	 * constructeur complet pour update(), delete()...
	 */
	public Cat_produit(int id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
	}

	/*
	 * Constructeur sans id pour create()
	 */
	public Cat_produit(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}
	/*
	 * constructeur pour le ProduitDao.read()
	 */

	public Cat_produit(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	/*
	 * getters/setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return nom;
	}

}
