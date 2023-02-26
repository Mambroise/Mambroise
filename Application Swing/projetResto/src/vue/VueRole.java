package vue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import controller.RoleDao;
import controller.UserDao;
import metier.RoleMetier;
import model.Role;

public class VueRole extends JPanel {
	private JTextField textField;
	private JTable table;
	/*
	 * instanciation Class roleMetier
	 */
	RoleMetier roleM = new RoleMetier();
	/*
	 * instanciation Class roleDao
	 */
	RoleDao roleD = new RoleDao();
	/*
	 * instanciation Class UserDao
	 */
	UserDao userD = new UserDao();
	/*
	 * creation variable string role pour les differents messages d'affichage
	 * fenetre role
	 */
	String nomModel = "rôle(s)";
	/*
	 * creation variable pour les 2 actions create et update
	 */
	String action = "";
	/*
	 * creation variable pour recupere ancien nom lors de la modification de
	 * registre
	 */
	String ancienNom = "";

	private JTextField textNom;
	private JTextField textId;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public VueRole() {
		setBounds(0, 0, 1136, 565);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1136, 565);
		add(panel);
		panel.setLayout(null);

		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(null, "R\u00F4les", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		textField.setBounds(194, 35, 237, 19);
		panelListe.add(textField);
		textField.setColumns(10);

		/*
		 * Creation table liste Role
		 */

		table = new JTable();
		table.setModel(roleM.lister(""));
		JLabel lblAffichage = new JLabel(
				"Affichage de " + roleM.totalM + " " + nomModel + " sur un total de " + roleD.total() + " registres");
		JButton btnChercher = new JButton("Chercher");
		JTextArea textDescription = new JTextArea();
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(roleM.lister(textField.getText()));
				TableRowSorter order = new TableRowSorter(table.getModel());
				table.setRowSorter(order);
				lblAffichage.setText("Affichage de " + roleM.totalM + " " + nomModel + " sur un total de "
						+ roleD.total() + " registres");
			}
		});
		btnChercher.setBounds(518, 34, 106, 23);
		panelListe.add(btnChercher);
		/*
		 * conditions pour les create et update, en fonction de la variable Action
		 * initialisée plus haut
		 */
		JButton btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (action.equalsIgnoreCase("modifier")) {

					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textDescription.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Modification", JOptionPane.ERROR_MESSAGE);
					} else {
						if (ancienNom.equalsIgnoreCase(textNom.getText())) {
							Role newRole = new Role(Integer.parseInt(textId.getText()), textNom.getText(),
									textDescription.getText());
							if (roleD.update(newRole)) {
								JOptionPane.showMessageDialog(null,
										"Le rôle " + newRole.getNom() + " a bien été enregistré", "Modification",
										JOptionPane.INFORMATION_MESSAGE);
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
								table.setModel(roleM.lister(textField.getText()));
							} else {
								JOptionPane.showMessageDialog(null, "Impossible de modifier le " + nomModel,
										"Modification", JOptionPane.ERROR_MESSAGE);
							}
						} else {

							if (!roleD.isExist(textNom.getText())) {
								Role newRole = new Role(Integer.parseInt(textId.getText()), textNom.getText(),
										textDescription.getText());

								if (roleD.update(newRole)) {
									JOptionPane.showMessageDialog(null,
											"Le rôle " + newRole.getNom() + " a bien été enregistré", "Modification",
											JOptionPane.INFORMATION_MESSAGE);
									tabbedPane.setEnabledAt(1, false);
									tabbedPane.setEnabledAt(0, true);
									tabbedPane.setSelectedIndex(0);
									table.setModel(roleM.lister(textField.getText()));
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
					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textDescription.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Création", JOptionPane.ERROR_MESSAGE);
					} else {
						if (!roleD.isExist(textNom.getText())) {
							Role newRole = new Role(textNom.getText(), textDescription.getText());
							if (roleD.create(newRole)) {
								JOptionPane.showMessageDialog(null,
										"Le role " + newRole.getNom() + " a bien été enregistré", "Création",
										JOptionPane.INFORMATION_MESSAGE);
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
								table.setModel(roleM.lister(textField.getText()));
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
				textId.setText("");
				action = "Sauvegarder";
				btnSauvegarder.setText("Sauvegarder");
			}
		});
		btnNouveau.setBounds(653, 34, 106, 23);
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

					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					ancienNom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					textDescription.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);
					action = "Modifier";
					btnSauvegarder.setText("Modifier");
				}
			}
		});
		btnModifier.setBounds(786, 34, 106, 23);
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
					String nom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					String desc = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					if (stat.equalsIgnoreCase("actif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà actif", "Statut",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Role newRole = new Role(Integer.parseInt(id), nom, desc, stat);
						if (roleD.activer(newRole)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant actif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							table.setModel(roleM.lister(textField.getText()));
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
					int idRow = (int) (table.getValueAt(table.getSelectedRow(), 0));
					if ((boolean) userD.findById(idRow)) {
						JOptionPane.showMessageDialog(null,
								"Le rôle ne peut pas être désactiver car un employé possède ce rôle ", "Statut",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					/*
					 * recupération des données selectionnées avant instanciation
					 */

					String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					String nom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					String desc = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					if (stat.equalsIgnoreCase("inactif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà inactif", "Statut",
								JOptionPane.WARNING_MESSAGE);

					} else {
						Role roleNew = new Role(Integer.parseInt(id), nom, desc, stat);
						if (roleD.desactiver(roleNew)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant inactif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							table.setModel(roleM.lister(textField.getText()));
						}
					}
				}
			}
		});
		btnDesactiver.setBounds(194, 18, 106, 23);
		panel_1.add(btnDesactiver);

		lblAffichage.setBounds(476, 387, 367, 19);
		panelListe.add(lblAffichage);

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
		textId.setBounds(368, 76, 142, 20);
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
				table.setModel(roleM.lister(textField.getText()));
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
		/*
		 * Tri asc desc pour le tabeau produit
		 */
		@SuppressWarnings("rawtypes")
		TableRowSorter order = new TableRowSorter(table.getModel());
		table.setRowSorter(order);
	}
}
