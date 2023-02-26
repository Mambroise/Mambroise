package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.AdresseDao;
import controller.CommandeDao;
import controller.DetailDao;
import metier.CommandeMetier;
import metier.DateLabelFormatter;
import metier.DetailMetier;
import model.Adresse;
import model.Commande;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class VueLivraison extends JPanel {
	private JTextField textField;
	private JTable tableCommande;

	/*
	 * instanciation Class CommandeMetier
	 */
	CommandeDao commandeD = new CommandeDao();
	/*
	 * instanciation Class CommandeDao
	 */
	CommandeMetier commandeM = new CommandeMetier();
	/*
	 * instanciation Class DetailMetier
	 */
	DetailMetier detailM = new DetailMetier();
	/*
	 * instanciation Class DetailDao
	 */
	DetailDao detailD = new DetailDao();
	/*
	 * instanciation adresse controleur pour appeler le read et récupérer l'adresse
	 * du client
	 */
	AdresseDao adresseD = new AdresseDao();
	/*
	 * creation variable string Commande pour les differents messages d'affichage
	 * fenetre role
	 */
	String nomModel = "Commande(s)";
	String nomModel2 = "détail(s)de commande";
	/*
	 * creation variable pour les 2 actions create et update
	 */
	String action = "";
	/*
	 * creation variable pour recupere ancien nom lors de la modification de
	 * registre
	 */
	int idCommande;
	private JTable tableDetail;
	private JTextField textField_1;
	private JTable tableArchive;

	/**
	 * Create the panel.
	 */
	public VueLivraison() {
		setBounds(0, 0, 1136, 565);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1136, 565);
		add(panel);
		panel.setLayout(null);

		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Tableau de bord commandes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMain.setBounds(0, 10, 1136, 555);
		panel.add(panelMain);
		panelMain.setLayout(null);
		/*
		 * creation double intercalaires
		 */

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 31, 1116, 514);
		panelMain.add(tabbedPane);

		JPanel panelListe = new JPanel();
		tabbedPane.addTab("Liste", null, panelListe, null);
		tabbedPane.setEnabledAt(0, true);
		panelListe.setLayout(null);

		JLabel lblRecherche = new JLabel("Rechercher par id :");
		lblRecherche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRecherche.setBounds(20, 10, 124, 27);
		panelListe.add(lblRecherche);

		textField = new JTextField();
		textField.setBounds(194, 15, 237, 19);
		panelListe.add(textField);
		textField.setColumns(10);

		JLabel lblAffichage_1 = new JLabel("");
		lblAffichage_1.setBounds(533, 454, 367, 19);
		panelListe.add(lblAffichage_1);

		JLabel lblAffichage = new JLabel("");
		lblAffichage.setBounds(533, 188, 367, 19);
		panelListe.add(lblAffichage);

		JLabel lblLabel1 = new JLabel("Adresse de livraison :");
		lblLabel1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblLabel1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 0));
		panel_3.setBounds(927, 47, 174, 396);
		panelListe.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 10, 154, 159);

		panel_3.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(-106, -92, 260, 292);
		lblNewLabel_1.setIcon(new ImageIcon(VueFrameMain.class.getResource("/ressources/menu.gif")));
		panel_4.add(lblNewLabel_1);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 179, 154, 207);
		panel_3.add(panel_5);
		panel_5.setLayout(null);

		lblLabel1.setBounds(10, 10, 134, 18);
		panel_5.add(lblLabel1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(63, 13, 81, 15);
		panel_5.add(lblNewLabel);

		JLabel lblRue = new JLabel("");
		lblRue.setBounds(10, 57, 134, 18);
		panel_5.add(lblRue);

		JLabel lblCode = new JLabel("");
		lblCode.setBounds(10, 95, 55, 18);
		panel_5.add(lblCode);

		JLabel lblVille = new JLabel("");
		lblVille.setBounds(10, 138, 134, 18);
		panel_5.add(lblVille);

		JLabel lblComplement = new JLabel("");
		lblComplement.setBounds(10, 166, 134, 18);
		panel_5.add(lblComplement);

		tableCommande = new JTable();
		tableCommande.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idCommande = (int) tableCommande.getValueAt(tableCommande.getSelectedRow(), 0);
				tableDetail(idCommande);
				lblAffichage_1.setText("Affichage de " + detailM.totalM + " " + nomModel2 + " sur un total de "
						+ detailD.total() + " registres");
				Adresse clientAdresse = findAddress(
						String.valueOf(tableCommande.getValueAt(tableCommande.getSelectedRow(), 6)));
				lblRue.setText(clientAdresse.getRue());
				lblCode.setText(clientAdresse.getCod_postal());
				lblVille.setText(clientAdresse.getVille());
				lblComplement.setText(clientAdresse.getComplement());
				;
			}
		});
		tableCommande("");
		lblAffichage.setText("Affichage de " + commandeM.totalM + " " + nomModel + " sur un total de "
				+ commandeD.total() + " registres");
		JButton btnChercher = new JButton("Chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableCommande(textField.getText());
				lblAffichage.setText("Affichage de " + commandeM.totalM + " " + nomModel + " sur un total de "
						+ commandeD.total() + " registres");
			}
		});
		btnChercher.setBounds(518, 14, 106, 23);
		panelListe.add(btnChercher);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 67, 877, 89);
		panelListe.add(scrollPane);
		scrollPane.setViewportView(tableCommande);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 206, 206));
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(0, 0, 0)),
				"D\u00E9tails de la Commande ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(20, 230, 897, 214);
		panelListe.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 22, 877, 182);
		panel_2.add(scrollPane_1);

		tableDetail = new JTable();
		scrollPane_1.setViewportView(tableDetail);
		JButton btnExpedier = new JButton("Expédiée");
		btnExpedier.setBounds(30, 181, 114, 32);
		panelListe.add(btnExpedier);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(
				new TitledBorder(null, "Commandes en cours", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBackground(new Color(255, 206, 206));
		panel_6.setBounds(20, 47, 897, 118);
		panelListe.add(panel_6);

		btnExpedier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableCommande.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Vous devez selectionner une commande", "Expediée",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Double total = (Double) tableCommande.getValueAt(tableCommande.getSelectedRow(), 3);
					Double Ht = (Double) tableCommande.getValueAt(tableCommande.getSelectedRow(), 2);
					String type = String.valueOf(tableCommande.getValueAt(tableCommande.getSelectedRow(), 4));
					String etat = "Expédiée";
					Commande commandeEnCours = new Commande(idCommande, Ht, total, type, etat);
					commandeD.update(commandeEnCours);
					tableCommande(textField.getText());
					idCommande = 0;
					tableDetail(idCommande);
				}
			}
		});

		JPanel panelArchive = new JPanel();
		tabbedPane.addTab("Archive", null, panelArchive, null);
		tabbedPane.setEnabledAt(1, true);
		panelArchive.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Archives", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(26, 69, 906, 356);
		panelArchive.add(panel_1);
		panel_1.setLayout(null);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(200, 37, 237, 19);
		panelArchive.add(textField_1);

		JLabel lblRecherche_1 = new JLabel("Rechercher par id :");
		lblRecherche_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRecherche_1.setBounds(26, 32, 124, 27);
		panelArchive.add(lblRecherche_1);

		JLabel lblAffichageArchive = new JLabel("");
		lblAffichageArchive.setBounds(547, 435, 367, 19);
		panelArchive.add(lblAffichageArchive);

		tableArchive = new JTable();
		tableArchive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tableArchive("");
		lblAffichageArchive.setText("Affichage de " + commandeM.totalM + " " + nomModel + " sur un total de "
				+ commandeD.total() + " registres");

		JButton btnChercherArchive = new JButton("Chercher");
		btnChercherArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableArchive(textField_1.getText());
				lblAffichageArchive.setText("Affichage de " + commandeM.totalM + " registres sur un total de "
						+ commandeD.total() + " registres");
			}
		});
		btnChercherArchive.setBounds(458, 35, 106, 23);
		panelArchive.add(btnChercherArchive);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 22, 886, 324);
		panel_1.add(scrollPane_2);
		scrollPane_2.setViewportView(tableArchive);

		JButton btnGnrerUnPdf = new JButton("Générer une facture");
		btnGnrerUnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableArchive.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Vous devez selectionner une commande", "Expediée",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int idAchive = (int) tableArchive.getValueAt(tableArchive.getSelectedRow(), 0);
					commandeD.rapportFacture(idAchive, Commande.TVA, Adresse.adresseLast.getId());
				}
			}
		});
		btnGnrerUnPdf.setBounds(26, 437, 152, 23);
		panelArchive.add(btnGnrerUnPdf);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(
				new TitledBorder(null, "Rapport des ventes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(574, 0, 527, 71);
		panelArchive.add(panel_7);
		panel_7.setLayout(null);
		UtilDateModel model = new UtilDateModel();
		UtilDateModel model1 = new UtilDateModel();
		// model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		panel_7.add(datePicker);

		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePicker1.setBounds(240, 29, 133, 32);
		datePicker.setBounds(69, 29, 133, 32);

		panel_7.add(datePicker1);

		JButton btnRapport = new JButton("Editer rapport");
		btnRapport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date dateDebut = (Date) datePanel.getModel().getValue();
				Date dateFin = (Date) datePanel1.getModel().getValue();
				int diffDate = dateDebut.compareTo(dateFin);
				System.out.println("Diff " + diffDate);
				if (diffDate < 0) {
					commandeD.rapportVente(datePicker.getJFormattedTextField().getText(),
							datePicker1.getJFormattedTextField().getText());
					System.out.println("From: " + datePicker.getJFormattedTextField().getText());
					System.out.println("to: " + datePicker1.getJFormattedTextField().getText());
				} else {
					JOptionPane.showMessageDialog(null,
							"La date de fin doit être postérieure ou égale à la date du début", "Rapport",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnRapport.setBounds(397, 29, 120, 21);
		panel_7.add(btnRapport);

		JLabel lblNewLabel_2 = new JLabel("DU: ");
		lblNewLabel_2.setBounds(34, 37, 34, 13);
		panel_7.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("AU: ");
		lblNewLabel_2_1.setBounds(212, 37, 34, 13);
		panel_7.add(lblNewLabel_2_1);
	}

	public void tableCommande(String txt) {

		tableCommande.setModel(commandeM.lister(txt));
		tableCommande.getColumnModel().getColumn(0).setMaxWidth(80);
		tableCommande.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(80);
		tableCommande.getColumnModel().getColumn(2).setMaxWidth(80);
		tableCommande.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(80);
		tableCommande.getColumnModel().getColumn(6).setMinWidth(0);
		tableCommande.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		tableCommande.getColumnModel().getColumn(6).setMaxWidth(0);
		tableCommande.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		TableRowSorter order = new TableRowSorter(tableCommande.getModel());
		tableCommande.setRowSorter(order);
	}

	public void tableDetail(int id) {

		tableDetail.setModel(detailM.listeDetail1(id));
		tableDetail.setRowHeight(30);
		tableDetail.getColumnModel().getColumn(0).setMaxWidth(80);
		tableDetail.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(80);
		tableDetail.getColumnModel().getColumn(1).setMaxWidth(80);
		tableDetail.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(80);
		tableDetail.getColumnModel().getColumn(2).setMinWidth(120);
		tableDetail.getTableHeader().getColumnModel().getColumn(2).setMinWidth(120);
		tableDetail.getColumnModel().getColumn(2).setMaxWidth(120);
		tableDetail.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(120);
		tableDetail.getColumnModel().getColumn(3).setMaxWidth(80);
		tableDetail.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(80);
		tableDetail.getColumnModel().getColumn(4).setMaxWidth(30);
		tableDetail.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(30);
		TableRowSorter order = new TableRowSorter(tableDetail.getModel());
		tableDetail.setRowSorter(order);
	}

	public void tableArchive(String txt) {

		tableArchive.setModel(commandeM.listerArchive(txt));
		tableArchive.getColumnModel().getColumn(0).setMaxWidth(80);
		tableArchive.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(80);
	}

	/*
	 * methode pour retrouver l'adresse du client correspondant à la commande
	 * selectionnée afin d'injecter les données adresse dans la vue
	 */
	public Adresse findAddress(String txt) {

		Adresse adresse = null;

		for (Adresse item : adresseD.read(txt)) {
			adresse = new Adresse(item.getId_client(), item.getRue(), item.getCod_postal(), item.getVille(),
					item.getComplement());
		}
		return adresse;
	}
}
