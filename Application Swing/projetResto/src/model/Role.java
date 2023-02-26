package model;

public class Role {

	/*
	 * Initialisation des elements de la class MODELE Role
	 */

	private int id;
	private String nom;
	private String description;
	private String statut;

	/**
	 * @param id
	 * @param nom
	 * @param description
	 * @param statut @
	 */

	/*
	 * Initialisation de constructors
	 */
	public Role() {

	}

	public Role(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}

	public Role(String nom, String description, String statut) {
		this.nom = nom;
		this.description = description;
		this.statut = statut;
	}

	public Role(int id, String nom, String description, String statut) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.statut = statut;
	}

	public Role(int id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
	}

	public Role(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	/*
	 * Génération de getters et setters
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

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	/*
	 * Génération de toString de la classe Role
	 */

	@Override
	public String toString() {
		return nom;
	}

}
