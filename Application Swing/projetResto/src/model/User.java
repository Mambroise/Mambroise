package model;

public class User {

	/*
	 * Initialisation des elements de la class MODELE Role
	 */

	private int id;
	private Role id_role;
	private String nom;
	private String prenom;
	private String email;
	private String url;
	private String password;
	private String statut;

	public static User userLogin = new User();
	// public static User userLogin = new User(1,"Ambroise","Moris");
	public static String nomEtablissement;

	/**
	 * @param id
	 * @param id_role
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param url
	 * @param password
	 * @param statut @
	 */

	/*
	 * Initialisation de constructors
	 */
	public User() {

	}

	public User(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public User(int id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	public User(Role id_role, String nom, String prenom, String email, String url, String password, String statut) {
		this.id_role = id_role;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.url = url;
		this.password = password;
		this.statut = statut;
	}

	public User(int id, Role id_role, String nom, String prenom, String email, String url, String statut) {
		this.id = id;
		this.id_role = id_role;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.url = url;
		this.statut = statut;
	}

	public User(Role id_role, String nom, String prenom, String email, String url, String password) {
		this.id_role = id_role;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.url = url;
		this.password = password;
	}

	public User(int id, Role id_role, String nom, String prenom, String email, String url) {
		this.id = id;
		this.id_role = id_role;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.url = url;
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

	public Role getId_role() {
		return id_role;
	}

	public void setId_role(Role id_role) {
		this.id_role = id_role;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return nom + " " + prenom;
	}

}
