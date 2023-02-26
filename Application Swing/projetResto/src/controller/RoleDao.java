package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import connetion.ConnectionSql;
import model.Role;

public class RoleDao implements IDao<Role> {
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
	public Boolean create(Role role) {
		try {
			sql = conn.prepareStatement("INSERT INTO role (nom,description) VALUES (?,?)");
			sql.setString(1, role.getNom());
			sql.setString(2, role.getDescription());
			sql.execute();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	/*
	 * methode Read du CRUD, qui peut devenir FindByName grace au parametre txt
	 */
	@Override
	public ArrayList<Role> read(String txt) {
		ArrayList<Role> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement("SELECT * FROM role WHERE nom LIKE ?");
			sql.setString(1, "%" + txt + "%");
			rs = sql.executeQuery();

			while (rs.next()) {
				list.add(new Role(rs.getInt("id"), rs.getString("nom"), rs.getString("description"),
						rs.getString("statut")));
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
		ArrayList<Role> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement("SELECT * FROM role WHERE id= ? AND statut='Inactif'");
			sql.setInt(1, id);
			rs = sql.executeQuery();

			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return false;
	}

	/*
	 * méthode update du CRUD
	 */
	@Override
	public Boolean update(Role role) {
		try {
			sql = conn.prepareStatement("UPDATE role SET nom = ?,description = ? WHERE id=?");
			sql.setString(1, role.getNom());
			sql.setString(2, role.getDescription());
			sql.setInt(3, role.getId());
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

	/*
	 * méthode delete du CRUD
	 */
	@Override
	public Boolean Delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * méthode pour rendre le role actif
	 */
	@Override
	public Boolean activer(Role role) {
		try {
			sql = conn.prepareStatement("UPDATE role SET statut = 'Actif' WHERE id=?");
			sql.setInt(1, role.getId());
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

	/*
	 * méthode pour rendre le role inactif
	 */
	@Override
	public Boolean desactiver(Role role) {
		try {
			sql = conn.prepareStatement("UPDATE role SET statut = 'Inactif' WHERE id=?");
			sql.setInt(1, role.getId());
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

	/*
	 * méthode pour vérifier l'existence d'un objet role avant INSERT INTO
	 */
	@Override
	public Boolean isExist(String txt) {
		try {
			sql = conn.prepareStatement("SELECT nom FROM role WHERE nom=?");
			sql.setString(1, txt.trim());
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

	/*
	 * méthode pour compte le nombre de registe total de la bdd role
	 */
	@Override
	public int total() {
		int total = 0;
		try {
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM role");
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

	public ArrayList<Role> selectRoles() {
		ArrayList<Role> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement("SELECT id, nom FROM role");
			System.out.println(sql);
			rs = sql.executeQuery();

			while (rs.next()) {
				list.add(new Role(rs.getInt("id"), rs.getString("nom")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

}
