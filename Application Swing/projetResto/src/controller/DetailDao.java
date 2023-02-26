package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connetion.ConnectionSql;
import model.Cat_produit;
import model.Commande;
import model.Detail_commande;
import model.Produit;

public class DetailDao implements IDao<Detail_commande> {
	Connection conn = ConnectionSql.myConnection();
	PreparedStatement sql = null;
	ResultSet rs = null;

	@Override
	public Boolean create(Detail_commande detail) {
		try {
			sql = conn.prepareStatement(
					"INSERT INTO detail_commande (id_commande,id_produit,prix_unitaire) VALUES (?,?,?)");
			sql.setInt(1, detail.getId_commande().getId());
			sql.setInt(2, detail.getId_produit().getId());
			sql.setDouble(3, detail.getId_produit().getPrix());
			sql.execute();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Detail_commande> read(String txt) {

		return null;
	}

	@Override
	public ArrayList<Detail_commande> findById(int id) {
		ArrayList<Detail_commande> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT *,dc.id as idDetail,c.id as idCom, p.id as idProd,ct.id as idCat, p.nom as nomProd,ct.nom as nomCat,p.description as desProd  FROM detail_commande dc INNER JOIN commande c ON id_commande=c.id INNER JOIN produit p "
							+ "ON id_produit=p.id INNER JOIN cat_produit ct ON ct.id = id_cat_produit  WHERE c.id =?");
			sql.setInt(1, id);
			rs = sql.executeQuery();
			while (rs.next()) {
				Cat_produit catProd = new Cat_produit(rs.getInt("idCat"), rs.getString("nomCat"));
				Produit newProd = new Produit(rs.getInt("idProd"), catProd, rs.getString("nomProd"),
						rs.getString("type_statut"), rs.getString("desProd"));
				Commande commande = new Commande(id);
				Detail_commande detail = new Detail_commande(rs.getInt("id"), commande, newProd, rs.getInt("quantite"),
						rs.getDouble("prix_unitaire"));
				list.add(detail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public Boolean update(Detail_commande object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean updateQuantité(int qte, int id) {
		try {
			sql = conn.prepareStatement("UPDATE detail_commande SET quantite=? WHERE id=?");
			sql.setInt(1, qte);
			sql.setInt(2, id);
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
		try {
			sql = conn.prepareStatement("DELETE FROM detail_commande WHERE id=?");
			sql.setInt(1, id);
			sql.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Boolean DeleteAll(int id) {
		try {
			sql = conn.prepareStatement("DELETE FROM detail_commande WHERE id_commande=?");
			sql.setInt(1, id);
			sql.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean activer(Detail_commande object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean desactiver(Detail_commande object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isExist(String txt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int total() {
		int total = 0;
		try {
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM detail_commande");
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

}
