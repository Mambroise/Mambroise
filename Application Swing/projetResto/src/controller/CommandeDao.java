package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connetion.ConnectionSql;
import model.Client;
import model.Commande;
import model.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class CommandeDao implements IDao<Commande> {

	Connection conn = ConnectionSql.myConnection();
	PreparedStatement sql = null;
	ResultSet rs = null;

	@Override
	public Boolean create(Commande commande) {

		try {
			sql = conn.prepareStatement("INSERT INTO commande (id_client,id_user,date_comm) VALUES (?,?,now())");
			sql.setObject(1, Client.clientLast.getId());
			sql.setObject(2, User.userLogin.getId());
			sql.execute();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Commande> read(String txt) {
		ArrayList<Commande> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT * ,C.id as idCom FROM Commande c INNER JOIN client cl ON id_client=cl.id  WHERE C.id LIKE? AND etat= 'validée' ORDER BY date_comm DESC ");
			sql.setString(1, "%" + txt + "%");
			rs = sql.executeQuery();
			while (rs.next()) {

				Client client = new Client(rs.getInt("id_client"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("numTel"));
				Commande commande = new Commande(rs.getInt("idCom"), client, rs.getDouble("totalHt"),
						rs.getDouble("total"), rs.getTimestamp("date_comm"), rs.getString("type_paiement"),
						rs.getString("etat"));
				list.add(commande);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Commande> archive(String txt) {
		ArrayList<Commande> list = new ArrayList<>();
		try {
			sql = conn.prepareStatement(
					"SELECT * ,C.id as idCom FROM Commande c INNER JOIN client cl ON id_client=cl.id  WHERE C.id LIKE ? ORDER BY date_comm DESC ");
			sql.setString(1, "%" + txt + "%");
			rs = sql.executeQuery();
			while (rs.next()) {

				Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("numTel"));
				Commande commande = new Commande(rs.getInt("idCom"), client, rs.getDouble("totalHt"),
						rs.getDouble("total"), rs.getTimestamp("date_comm"), rs.getString("type_paiement"),
						rs.getString("etat"));
				list.add(commande);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Commande findById(int id) {
		Commande newCommande = null;

		try {
			sql = conn.prepareStatement("SELECT * FROM commande WHERE id=?");
			sql.setInt(1, id);
			rs = sql.executeQuery();
			while (rs.next()) {
				newCommande = new Commande(rs.getInt("id"), Client.clientLast, User.userLogin,
						rs.getTimestamp("date_comm"), rs.getString("etat"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newCommande;

	}

	@Override
	public Boolean update(Commande commande) {
		try {
			sql = conn.prepareStatement("UPDATE commande SET totalHt=?,total=?,type_paiement=?,etat=? WHERE id=?");
			sql.setDouble(1, commande.getTotalHt());
			sql.setDouble(2, commande.getTotal());
			sql.setString(3, commande.getType_paiement());
			sql.setString(4, commande.getEtat());
			sql.setInt(5, commande.getId());
			if (sql.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean Delete(int id) {
		try {
			sql = conn.prepareStatement("DELETE FROM commande WHERE id=?");
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
	public int total() {
		int total = 0;
		try {
			sql = conn.prepareStatement("SELECT COUNT(*) as total FROM commande");
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

	public Double totalCommande(int id) {
		Double total = (double) 0;
		try {
			sql = conn.prepareStatement(
					"SELECT SUM(prix_unitaire*quantite)as totalCom FROM detail_commande WHERE id_commande=?");
			sql.setInt(1, id);
			rs = sql.executeQuery();
			while (rs.next()) {
				total = rs.getDouble("totalCom");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("total commande: " + total);
		return total;
	}

	public int dernierIdCommande() {
		int id = 0;
		try {
			sql = conn.prepareStatement("SELECT LAST_INSERT_ID() as 'ID' FROM commande");
			rs = sql.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public void rapportFacture(int commande_id, Double taxe, int idAdresse) {
		Map p = new HashMap();
		p.put("commande_id", commande_id);
		p.put("taxe", taxe);
		p.put("adresse", idAdresse);
		JasperReport report;
		JasperPrint print;
		try {
			report = JasperCompileManager
					.compileReport(new File("").getAbsolutePath() + "/src/ressources/rapports/RptFacture.jrxml");
			print = JasperFillManager.fillReport(report, p, ConnectionSql.myConnection());
			JasperViewer view = new JasperViewer(print, false);
			view.setTitle("FACTURE");
			view.setVisible(true);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void rapportVente(String dateA, String dateB) {
		Map p = new HashMap();
		p.put("dateA", dateA);
		p.put("dateB", dateB);
		JasperReport report;
		JasperPrint print;
		try {
			report = JasperCompileManager
					.compileReport(new File("").getAbsolutePath() + "/src/ressources/rapports/RptVente.jrxml");
			print = JasperFillManager.fillReport(report, p, ConnectionSql.myConnection());
			JasperViewer view = new JasperViewer(print, false);
			view.setTitle("VENTES");
			view.setVisible(true);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * methode de calcul du total hors taxes qui est renvoyé vers commande, puis
	 * dans la BDD
	 */
	public Double totalHt(int id) {
		Double totalHt = totalCommande(id) - (totalCommande(id) * Commande.TVA / 100);
		return totalHt;
	}

	@Override
	public Boolean activer(Commande object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean desactiver(Commande object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isExist(String txt) {
		// TODO Auto-generated method stub
		return null;
	}
}
