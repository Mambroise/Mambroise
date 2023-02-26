package model;

public class Adresse {

	private int id;
	private int id_client;
	private String rue;
	private String cod_postal;
	private String ville;
	private String complement;

	public static Adresse adresseLast = new Adresse();

	public Adresse() {

	}

	public Adresse(int id, int id_client, String rue, String cod_postal, String ville, String complement) {
		this.id = id;
		this.id_client = id_client;
		this.rue = rue;
		this.cod_postal = cod_postal;
		this.ville = ville;
		this.complement = complement;
	}

	public Adresse(int id_client, String rue, String cod_postal, String ville, String complement) {
		this.id_client = id_client;
		this.rue = rue;
		this.cod_postal = cod_postal;
		this.ville = ville;
		this.complement = complement;
	}

	/*
	 * contructeur pur modifier l'adresse lors de la prise commande
	 */
//	public Adresse(int id, String rue, String cod_postal, String ville, String complement) {
//	
//		this.id = id;
//		this.rue = rue;
//		this.cod_postal = cod_postal;
//		this.ville = ville;
//		this.complement = complement;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCod_postal() {
		return cod_postal;
	}

	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Override
	public String toString() {
		return ville;
	}

}
