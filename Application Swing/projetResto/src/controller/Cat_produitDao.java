package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connetion.ConnectionSql;
import model.Cat_produit;

public class Cat_produitDao implements IDao<Cat_produit> {
	/*
	 * Connection a la bdd instanciation des class plusieurs fois utilisées
	 */
	Connection conn = ConnectionSql.myConnection();
	PreparedStatement sql = null;
	ResultSet rs = null;

	/*
	 * methode Create du CRUD
	 */
	@Override
	public Boolean create(Cat_produit prod) {
		try {
			sql = conn.prepareStatement("INSERT INTO cat_produit (nom,description) VALUES (?,?)");
			sql.setString(1, prod.getNom());
			sql.setString(2, prod.getDescription());
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
	public ArrayList<Cat_produit> read(String txt) {
		ArrayList<Cat_produit> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement("SELECT * FROM cat_produit WHERE nom LIKE ?");
			sql.setString(1, "%" + txt + "%");
			System.out.println(sql);
			rs = sql.executeQuery();

			while (rs.next()) {
				list.add(new Cat_produit(rs.getInt("id"), rs.getString("nom"), rs.getString("description")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public Object findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cat_produit findByName(String nom) {
		Cat_produit catProd = null;
		try {
			sql = conn.prepareStatement("SELECT * FROM cat_produit WHERE nom=?");
			sql.setString(1, nom);
			rs = sql.executeQuery();
			while (rs.next()) {
				catProd = new Cat_produit(rs.getInt("id"), rs.getString("nom"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catProd;
	}

	@Override
	public Boolean update(Cat_produit prod) {
		try {
			sql = conn.prepareStatement("UPDATE cat_produit SET nom = ?,description = ? WHERE id=?");
			sql.setString(1, prod.getNom());
			sql.setString(2, prod.getDescription());
			sql.setInt(3, prod.getId());
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
	public Boolean activer(Cat_produit prod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean desactiver(Cat_produit prod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isExist(String txt) {
		try {
			sql = conn.prepareStatement("SELECT nom FROM cat_produit WHERE nom=?");
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
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM cat_produit");
			rs = sql.executeQuery();
			while (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return total;

	}

	public ArrayList<Cat_produit> selectRoles() {
		ArrayList<Cat_produit> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement("SELECT id, nom FROM Cat_produit");
			rs = sql.executeQuery();

			while (rs.next()) {
				list.add(new Cat_produit(rs.getInt("id"), rs.getString("nom")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

}
