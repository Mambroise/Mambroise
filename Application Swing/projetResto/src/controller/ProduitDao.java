package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import connetion.ConnectionSql;
import model.Cat_produit;
import model.Produit;
import model.User;

public class ProduitDao implements IDao<Produit> {
	Connection conn = ConnectionSql.myConnection();
	PreparedStatement sql = null;
	ResultSet rs = null;

	@Override
	public Boolean create(Produit prod) {
		try {
			sql = conn.prepareStatement(
					"INSERT INTO produit (id_cat_produit,id_user,code,nom,Type_statut,description,prix,Date_mod) VALUES (?,?,?,?,?,?,?,NOW())");
			sql.setInt(1, prod.getId_cat_produit().getId());
			sql.setInt(2, prod.getId_user().getId());
			sql.setString(3, prod.getCode());
			sql.setString(4, prod.getNom());
			sql.setString(5, prod.getType_statut());
			sql.setString(6, prod.getDescription());
			sql.setDouble(7, prod.getPrix());
			sql.execute();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	/*
	 * Cette methode n'est plus utilisée (remplacer par Filtrer())
	 */
	public ArrayList<Produit> read(String txt) {
		ArrayList<Produit> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT *,p.id as idprod,ct.id as idcat,u.id as iduser,p.nom as nomprod,ct.nom as nomcat,u.nom as nomuser,p.description as descprod,p.statut as statprod"
							+ " FROM produit p INNER JOIN cat_produit ct ON id_cat_produit=ct.id INNER JOIN user u ON u.id=id_user WHERE statut ='Actif' AND p.nom LIKE ? ");
			sql.setString(1, "%" + txt + "%");
			rs = sql.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("iduser"), rs.getString("nomuser"), rs.getString("prenom"));
				Cat_produit cat = new Cat_produit(rs.getInt("idcat"), rs.getString("nomcat"));
				list.add(new Produit(rs.getInt("idprod"), cat, user, rs.getString("code"), rs.getString("nomprod"),
						rs.getString("type_statut"), rs.getString("descprod"), rs.getDouble("prix"),
						rs.getTimestamp("date_mod"), rs.getString("statprod")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object findById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * methode pour lister les produits triés par code et nom lors de la commande
	 * 
	 * @param code produit de la barre de recherche
	 * 
	 * @param nom produit de la barre de recherche
	 */
	public ArrayList<Produit> findByCode(String code, String nom) {
		ArrayList<Produit> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT *,p.id as idprod,ct.id as idcat,p.nom as nomprod,ct.nom as nomcat,p.description as descprod,p.statut as statprod"
							+ " FROM produit p INNER JOIN cat_produit ct ON id_cat_produit=ct.id WHERE p.statut = 'Actif' AND code LIKE ? AND p.nom LIKE ? ");
			sql.setString(1, "%" + code + "%");
			sql.setString(2, "%" + nom + "%");
			System.out.println("sql dernier requete " + sql);
			rs = sql.executeQuery();
			while (rs.next()) {

				Cat_produit cat = new Cat_produit(rs.getInt("idcat"), rs.getString("nomcat"));
				list.add(new Produit(rs.getInt("idprod"), cat, rs.getString("code"), rs.getString("nomprod"),
						rs.getString("descprod"), rs.getDouble("prix"), rs.getString("statprod")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Boolean update(Produit prod) {
		try {
			sql = conn.prepareStatement(
					"UPDATE produit SET id_cat_produit=?,id_user=?,code=?,nom=?,Type_statut=?,description=?,prix=?,Date_mod=NOW() WHERE id=?");

			sql.setInt(1, prod.getId_cat_produit().getId());
			sql.setInt(2, prod.getId_user().getId());
			sql.setString(3, prod.getCode());
			sql.setString(4, prod.getNom());
			sql.setString(5, prod.getType_statut());
			sql.setString(6, prod.getDescription());
			sql.setDouble(7, prod.getPrix());
			sql.setInt(8, prod.getId());
			if (sql.executeUpdate() > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Boolean Delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean activer(Produit prod) {
		try {
			sql = conn.prepareStatement("UPDATE produit SET id_user=?, date_mod=NOW(), statut = 'Actif' WHERE id=?");
			sql.setInt(1, prod.getId_user().getId());
			sql.setInt(2, prod.getId());
			if (sql.executeUpdate() > 0) {
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Boolean desactiver(Produit prod) {
		try {
			sql = conn.prepareStatement("UPDATE produit SET id_user=?, date_mod=NOW(), statut = 'Inactif' WHERE id=?");
			sql.setInt(1, prod.getId_user().getId());
			sql.setInt(2, prod.getId());
			if (sql.executeUpdate() > 0) {
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Boolean isExist(String txt) {
		try {
			sql = conn.prepareStatement("SELECT nom FROM produit WHERE nom=?");
			sql.setString(1, txt);
			rs = sql.executeQuery();
			while (rs.next()) {
				if (rs.getString("nom").equalsIgnoreCase(txt)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public int total() {
		int total = 0;
		try {
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM produit");
			rs = sql.executeQuery();
			while (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return total;
	}

	public ArrayList<Produit> filtrer(String txt, String tri) {
		ArrayList<Produit> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT *,p.id as idprod,ct.id as idcat,u.id as iduser,p.nom as nomprod,ct.nom as nomcat,u.nom as nomuser,p.description as descprod,p.statut as statprod"
							+ " FROM produit p INNER JOIN cat_produit ct ON id_cat_produit=ct.id INNER JOIN user u ON u.id=id_user WHERE ct.nom LIKE ? AND p.nom LIKE ?");
			sql.setString(1, "%" + tri + "%");
			sql.setString(2, "%" + txt + "%");
			rs = sql.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("iduser"), rs.getString("nomuser"), rs.getString("prenom"));
				Cat_produit cat = new Cat_produit(rs.getInt("idcat"), rs.getString("nomcat"));
				list.add(new Produit(rs.getInt("idprod"), cat, user, rs.getString("code"), rs.getString("nomprod"),
						rs.getString("type_statut"), rs.getString("descprod"), rs.getDouble("prix"),
						rs.getTimestamp("date_mod"), rs.getString("statprod")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
