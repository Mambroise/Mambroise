package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;
import controller.Cat_produitDao;
import controller.ProduitDao;
import metier.ProduitMetier;
import model.Cat_produit;
import model.Produit;
import model.User;

@SuppressWarnings("serial")
public class VueProduit extends JPanel {
	private JTextField textField;
	private JTable table;
	/*
	 * instanciation Class ProduitMetier
	 */
	ProduitMetier prodM = new ProduitMetier();
	/*
	 * instanciation Class ProduitDao
	 */
	ProduitDao prodD = new ProduitDao();
	/*
	 * creation variable string Produit pour les differents messages d'affichage
	 * fenetre role
	 */
	String nomModel = "Produit(s)";
	/*
	 * creation variable pour les 2 actions create et update
	 */
	String action = "";
	/*
	 * creation variable pour recupere ancien nom lors de la modification de
	 * registre
	 */
	String ancienNom = "";
	Cat_produitDao cat_produitDao = new Cat_produitDao();

	private JTextField textNom;
	private JTextField textId;
	private JTextField textCode;
	private JTextField textPrix;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VueProduit() {

		setBounds(0, 0, 1136, 565);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1136, 565);
		add(panel);
		panel.setLayout(null);

		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Produits",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		panelListe.setLayout(null);

		JLabel lblRecherche = new JLabel("Rechercher par nom :");
		lblRecherche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRecherche.setBounds(20, 30, 124, 27);
		panelListe.add(lblRecherche);

		textField = new JTextField();
		textField.setBounds(142, 35, 237, 19);
		panelListe.add(textField);
		textField.setColumns(10);

		/*
		 * Creation table liste Role
		 */
		table = new JTable();
		table.setModel(prodM.lister("", ""));

		/*
		 * Les 3 combobox
		 */
		DefaultComboBoxModel itemCmb = prodM.selectCmb();
		JComboBox comboBoxCat = new JComboBox(itemCmb);

		comboBoxCat.setBounds(472, 213, 82, 20);
		JComboBox comboBoxType = new JComboBox();

		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { "Chaud", "Froid" }));
		comboBoxType.setBounds(470, 137, 82, 20);

		JComboBox comboBoxTri = new JComboBox();
		comboBoxTri.setModel(itemCmb);
		comboBoxTri.setBounds(274, 65, 106, 19);
		panelListe.add(comboBoxTri);
		Cat_produit cat = new Cat_produit(0, "Tous");

		comboBoxTri.addItem(cat);
		comboBoxTri.setSelectedItem(cat);

		JLabel lblAffichage = new JLabel(
				"Affichage de " + prodM.totalM + " " + nomModel + " sur un total de " + prodD.total() + " registres");
		JButton btnChercher = new JButton("Chercher");
		JTextArea textDescription = new JTextArea();
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tri = String.valueOf(comboBoxTri.getSelectedItem());
				if (tri.equalsIgnoreCase("Tous")) {
					tri = "";
				}
				table.setModel(prodM.lister(textField.getText(), tri));
				TableRowSorter order = new TableRowSorter(table.getModel());
				table.setRowSorter(order);
				lblAffichage.setText("Affichage de " + prodM.totalM + " " + nomModel + " sur un total de "
						+ prodD.total() + " registres");
			}
		});
		btnChercher.setBounds(405, 33, 106, 23);
		panelListe.add(btnChercher);
		/*
		 * conditions pour les create et update, en fonction de la variable Action
		 * initialisée plus haut
		 */
		JButton btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Variable contenant la combobox type de plat Method pour retourner l'objet
				 * cat_produit en fonction pendant l'instanciation de Poduit
				 */
				String typePlatcat = String.valueOf(comboBoxType.getSelectedItem());
				Cat_produit Cat_produit = cat_produitDao.findByName(String.valueOf(comboBoxCat.getSelectedItem()));
				if (action.equalsIgnoreCase("modifier")) {

					if (textNom.getText().equalsIgnoreCase("") || textCode.getText().equalsIgnoreCase("")
							|| textPrix.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textDescription.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Modification", JOptionPane.ERROR_MESSAGE);
					} else {
						if (ancienNom.equalsIgnoreCase(textNom.getText())) {
							Produit newProd = new Produit(Integer.parseInt(textId.getText()), Cat_produit,
									User.userLogin, textCode.getText(), textNom.getText(), typePlatcat,
									textDescription.getText(), (Double.parseDouble(textPrix.getText())));
							if (prodD.update(newProd)) {
								JOptionPane.showMessageDialog(null,
										"Le produit " + newProd.getNom() + " a bien été enregistré", "Modification",
										JOptionPane.INFORMATION_MESSAGE);
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
								String tri = String.valueOf(comboBoxTri.getSelectedItem());
								table.setModel(prodM.lister(textField.getText(), tri));
							} else {
								JOptionPane.showMessageDialog(null, "Impossible de modifier le " + nomModel,
										"Modification", JOptionPane.ERROR_MESSAGE);
							}
						} else {

							if (!prodD.isExist(textNom.getText())) {
								Produit newProd = new Produit(Integer.parseInt(textId.getText()), Cat_produit,
										User.userLogin, textCode.getText(), textNom.getText(), typePlatcat,
										textDescription.getText(), (Double.parseDouble(textPrix.getText())));
								if (prodD.update(newProd)) {
									JOptionPane.showMessageDialog(null,
											"Le produit " + newProd.getNom() + " a bien été enregistré", "Modification",
											JOptionPane.INFORMATION_MESSAGE);
									tabbedPane.setEnabledAt(1, false);
									tabbedPane.setEnabledAt(0, true);
									tabbedPane.setSelectedIndex(0);
									String tri = String.valueOf(comboBoxTri.getSelectedItem());
									table.setModel(prodM.lister(textField.getText(), tri));
								} else {
									JOptionPane.showMessageDialog(null, "Impossible de modifier le " + nomModel,
											"Modification", JOptionPane.ERROR_MESSAGE);
								} // fin du update
							} else {
								JOptionPane.showMessageDialog(null, "Ce " + nomModel + " existe déjà", "Modification",
										JOptionPane.ERROR_MESSAGE);
							} // fin if isExiste
						} // fin if ancienNom egual new nom
					}
				} else if (action.equalsIgnoreCase("Sauvegarder")) {
					if (textNom.getText().equalsIgnoreCase("") || textCode.getText().equalsIgnoreCase("")
							|| textPrix.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textDescription.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Création", JOptionPane.ERROR_MESSAGE);
					} else {
						if (!prodD.isExist(textNom.getText())) {
							Produit newProd = new Produit(Cat_produit, User.userLogin, textCode.getText(),
									textNom.getText(), typePlatcat, textDescription.getText(),
									(Double.parseDouble(textPrix.getText())));
							if (prodD.create(newProd)) {
								JOptionPane.showMessageDialog(null,
										"Le produit " + newProd.getNom() + " a bien été enregistré", "Création",
										JOptionPane.INFORMATION_MESSAGE);
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
								comboBoxTri.setSelectedIndex(0);
							} else {
								JOptionPane.showMessageDialog(null, "Impossible de créer le " + nomModel, "Création",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Ce " + nomModel + " existe déjà", "Création",
									JOptionPane.ERROR_MESSAGE);
						}
					} // fin if name ==""
				} // fin if modifier or save
			}// fin ActionPerformed
		});// fin ActionListener

		/*
		 * passage vers la page creation de role
		 */
		JButton btnNouveau = new JButton("Nouveau");
		btnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setEnabledAt(0, false);
				tabbedPane.setSelectedIndex(1);
				textNom.setText("");
				textDescription.setText("");
				textCode.setText("");
				textId.setText("");
				textPrix.setText("");
				action = "Sauvegarder";
				btnSauvegarder.setText("Sauvegarder");
				System.out.println(comboBoxCat.getSelectedItem());
			}
		});
		btnNouveau.setBounds(592, 33, 106, 23);
		panelListe.add(btnNouveau);
		/*
		 * passage vers la page modification de role
		 */
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Statut", JOptionPane.WARNING_MESSAGE);
				} else {
					comboBoxCat.setModel(prodM.selectCmb());
					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					textPrix.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
					textCode.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					ancienNom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					comboBoxCat.getModel().setSelectedItem((Object) table.getValueAt(table.getSelectedRow(), 4));
					comboBoxType.setSelectedItem(table.getValueAt(table.getSelectedRow(), 5));
					textDescription.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);
					action = "Modifier";
					btnSauvegarder.setText("Modifier");
				}
			}
		});
		btnModifier.setBounds(708, 33, 106, 23);
		panelListe.add(btnModifier);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 95, 1069, 282);
		panelListe.add(scrollPane);
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Statuts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 438, 346, 49);
		panelListe.add(panel_1);
		panel_1.setLayout(null);
		/*
		 * condition (choix d'une ligne dans tableau)et activation (statut) d'un role
		 */
		JButton btnActiver = new JButton("Activer");
		btnActiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Statut", JOptionPane.WARNING_MESSAGE);
				} else {
					/*
					 * recupération des données selectionnées avant instanciation
					 */
					String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 9));
					if (stat.equalsIgnoreCase("actif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà actif", "Statut",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Produit newProd = new Produit(Integer.parseInt(id), User.userLogin);
						if (prodD.activer(newProd)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant actif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							String tri = String.valueOf(comboBoxTri.getSelectedItem());
							table.setModel(prodM.lister(textField.getText(), ""));
						}
					}
				}
			}
		});
		btnActiver.setBounds(40, 18, 106, 23);
		panel_1.add(btnActiver);
		/*
		 * condition (choix d'une ligne dans tableau)et desactivation (statut) d'un role
		 */
		JButton btnDesactiver = new JButton("Desactiver");
		btnDesactiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Statut", JOptionPane.WARNING_MESSAGE);
				} else {
					/*
					 * recupération des données selectionnées avant instanciation
					 */
					String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 9));
					if (stat.equalsIgnoreCase("inactif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà inactif", "Statut",
								JOptionPane.WARNING_MESSAGE);

					} else {
						Produit newProd = new Produit(Integer.parseInt(id), User.userLogin);
						if (prodD.desactiver(newProd)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant inactif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							String tri = String.valueOf(comboBoxTri.getSelectedItem());
							table.setModel(prodM.lister(textField.getText(), ""));
						}
					}
				}
			}
		});
		btnDesactiver.setBounds(194, 18, 106, 23);
		panel_1.add(btnDesactiver);

		lblAffichage.setBounds(476, 387, 367, 19);
		panelListe.add(lblAffichage);

		JLabel lblNewLabel_1 = new JLabel("Filtrer la recherche");
		lblNewLabel_1.setBounds(131, 68, 127, 13);
		panelListe.add(lblNewLabel_1);

		JPanel panelGestion = new JPanel();
		tabbedPane.addTab("Gestion", null, panelGestion, null);
		panelGestion.setLayout(null);

		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setSelectedIndex(0);

		JLabel lblNom = new JLabel("Nom : (*)");
		lblNom.setBounds(10, 76, 70, 21);
		panelGestion.add(lblNom);

		JLabel lblDescription = new JLabel("Description :");
		lblDescription.setBounds(10, 137, 70, 21);
		panelGestion.add(lblDescription);

		textNom = new JTextField();
		textNom.setBounds(115, 77, 210, 20);
		panelGestion.add(textNom);
		textNom.setColumns(10);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(818, 77, 142, 20);
		panelGestion.add(textId);
		textId.setVisible(false);

		textDescription.setBounds(115, 135, 210, 133);
		panelGestion.add(textDescription);

		JLabel lblNewLabel_2 = new JLabel("(*) Champs obligatoires");
		lblNewLabel_2.setBounds(10, 304, 142, 21);
		panelGestion.add(lblNewLabel_2);
		/*
		 * Annulation et retour a la page accueil role
		 */
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setEnabledAt(1, false);
				tabbedPane.setEnabledAt(0, true);
				tabbedPane.setSelectedIndex(0);
				String tri = String.valueOf(comboBoxTri.getSelectedItem());
				System.out.println("tri avant " + tri);
				if (tri.equalsIgnoreCase("Tous")) {
					tri = "";
				}
				System.out.println("tri apres " + tri);
				table.setModel(prodM.lister(textField.getText(), tri));
			}
		});
		btnAnnuler.setBounds(92, 388, 106, 23);
		panelGestion.add(btnAnnuler);

		btnSauvegarder.setBounds(270, 389, 108, 23);
		panelGestion.add(btnSauvegarder);

		JLabel lblNewLabel = new JLabel("40 caractères maximum");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setBounds(115, 96, 119, 18);
		panelGestion.add(lblNewLabel);

		JLabel lblCaratresMaximum = new JLabel("100 caractères maximum");
		lblCaratresMaximum.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblCaratresMaximum.setBounds(115, 266, 119, 18);
		panelGestion.add(lblCaratresMaximum);

		JLabel lblNom_1 = new JLabel("Code : (*)");
		lblNom_1.setBounds(378, 76, 70, 21);
		panelGestion.add(lblNom_1);

		JLabel lblNom_2 = new JLabel("Type de plat : ");
		lblNom_2.setBounds(378, 137, 82, 21);
		panelGestion.add(lblNom_2);

		textCode = new JTextField();
		textCode.setColumns(10);
		textCode.setBounds(469, 77, 210, 20);
		panelGestion.add(textCode);

		JLabel lblNom_2_1 = new JLabel("Catégorie : ");
		lblNom_2_1.setBounds(378, 213, 70, 21);
		panelGestion.add(lblNom_2_1);

		JLabel lblNom_1_1 = new JLabel("Prix: (*)");
		lblNom_1_1.setBounds(727, 137, 70, 21);
		panelGestion.add(lblNom_1_1);

		textPrix = new JTextField();
		textPrix.setColumns(10);
		textPrix.setBounds(818, 138, 142, 20);
		panelGestion.add(textPrix);
		panelGestion.add(comboBoxCat);
		panelGestion.add(comboBoxType);
		textField.requestFocus();

	}
}
