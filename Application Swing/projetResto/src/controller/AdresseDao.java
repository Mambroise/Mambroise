package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connetion.ConnectionSql;
import model.Adresse;
import model.Client;

public class AdresseDao implements IDao<Adresse> {
	Client client = new Client();
	Adresse adresse = new Adresse();
	Connection conn = ConnectionSql.myConnection();
	PreparedStatement sql = null;
	ResultSet rs = null;

	@Override
	public Boolean create(Adresse adresse) {
		try {
			sql = conn.prepareStatement(
					"INSERT INTO adresse (id_client, rue, cod_postal, ville, complement) VALUES (?,?,?,?,?)");
			sql.setInt(1, adresse.getId_client());
			sql.setString(2, adresse.getRue());
			sql.setString(3, adresse.getCod_postal());
			sql.setString(4, adresse.getVille());
			sql.setString(5, adresse.getComplement());
			System.out.println("SQL Adresse Create: " + sql);
			sql.execute();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public ArrayList<Adresse> read(String txt) {
		ArrayList<Adresse> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT ad.id, ad.id_client, ad.rue, ad.cod_postal, ad.ville, ad.complement  FROM adresse as ad WHERE ad.id_client=?");
			txt.trim();
			if (txt.equalsIgnoreCase("") || txt.equals(null)) {
				txt = "0";
			}
			sql.setInt(1, Integer.parseInt(txt));
			rs = sql.executeQuery();
			while (rs.next()) {
				list.add(new Adresse(rs.getInt("id"), rs.getInt("id_client"), rs.getString("rue"),
						rs.getString("cod_postal"), rs.getString("ville"), rs.getString("complement")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int dernierIdAdress() {
		int id = 0;
		try {
			sql = conn.prepareStatement("SELECT LAST_INSERT_ID() as 'ID' FROM adresse");
			rs = sql.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Object findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(Adresse adresse) {
		try {
			sql = conn.prepareStatement("UPDATE adresse SET rue = ?, cod_postal = ?, ville=?, complement=? WHERE id=?");
			sql.setString(1, adresse.getRue());
			sql.setString(2, adresse.getCod_postal());
			sql.setString(3, adresse.getVille());
			sql.setString(4, adresse.getComplement());
			sql.setInt(5, adresse.getId());
			System.out.println("SQL Adresse update: " + sql);
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
			sql = conn.prepareStatement("DELETE FROM adresse WHERE id=?");
			sql.setInt(1, id);

			System.out.println("SQL Adresse delete: " + sql);
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
	public Boolean activer(Adresse object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean desactiver(Adresse object) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	public int totalA(int id) {
		int total = 0;
		try {
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM adresse WHERE id_client=?");
			sql.setInt(1, id);
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
