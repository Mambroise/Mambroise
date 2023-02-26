package vue;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import controller.RoleDao;
import controller.UserDao;
import metier.RoleMetier;
import metier.UserMetier;
import model.Role;
import model.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class VueUser extends JPanel {
	private JTextField textField;
	private JTable table;
	/*
	 * instanciation Class userMetier
	 */
	UserMetier userM = new UserMetier();
	/*
	 * instanciation Class userDao
	 */
	UserDao userD = new UserDao();
	RoleDao roleD = new RoleDao();
	Role roledeTest = new Role("", "");
	/*
	 * creation variable string role pour les differents messages d'affichage
	 * fenetre role
	 */
	String nomModel = "utilisateur(s)";
	/*
	 * creation variable pour les 2 actions create et update
	 */
	String action = "";
	/*
	 * creation variable pour recupere ancien nom lors de la modification de
	 * registre
	 */
	String ancienMail = "";

	private JTextField textNom;
	private JTextField textId;
	private JTextField textPrenom;
	private JTextField textEmail;
	private JPasswordField textPassword1;
	private JPasswordField textPassword2;
	private JTextField textUrl;
	private JPasswordField textPassword3;

	/**
	 * Create the panel.
	 * 
	 * @optionM 1-Pour administrer les utilisateurs 2-Pour changer le mot de pass de
	 *          utilisateur
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VueUser(int optionM) {

		setBounds(0, 0, 1136, 565);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1136, 565);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(VueFrameMain.class.getResource("/ressources/bateau_sushis.jpg")));
		lblNewLabel_1.setBounds(6, 19, 1122, 535);

		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Utilisateurs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMain.setBounds(0, 10, 1136, 555);
		panel.add(panelMain);
		panelMain.setLayout(null);

		/*
		 * creation double intercalaires
		 */
		JPanel panelGestion = new JPanel();
		JPanel panel_2 = new JPanel();
		// Pour charger des donnes dan le comboBox roles

		JButton btnAnnuler = new JButton("Annuler");
		JButton btnSauvegarder = new JButton("Sauvegarder");
		JLabel lblPassword1 = new JLabel("Mot de passe: (*)");
		JLabel lblPassword2 = new JLabel("Réécrivez votre Mot de passe : (*)");
		JLabel lblPassword3 = new JLabel("Réécrivez votre nouveau mot de passe : (*)");

		JComboBox cmbRoles = new JComboBox();
		DefaultComboBoxModel itemCmb = userM.selectCmb();
		cmbRoles.setModel(itemCmb);

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
		table.setModel(userM.lister(""));
		JLabel lblAffichage = new JLabel(
				"Affichage de " + userM.totalM + " " + nomModel + " sur un total de " + userD.total() + " registres");
		JButton btnChercher = new JButton("Chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(userM.lister(textField.getText()));
				lblAffichage.setText("Affichage de " + userM.totalM + " " + nomModel + " sur un total de "
						+ userD.total() + " registres");
				oculter();
				// Pour faire le order by de tableau Asc Desc
				TableRowSorter order = new TableRowSorter(table.getModel());
				table.setRowSorter(order);
			}
		});
		btnChercher.setBounds(476, 33, 106, 23);
		panelListe.add(btnChercher);
		/*
		 * conditions pour les create et update, en fonction de la variable Action
		 * initialisée plus haut
		 */

		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (action.equalsIgnoreCase("modifier")) {

					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textEmail.getText().equalsIgnoreCase("") || textEmail.getText().length() > 60) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Modification", JOptionPane.ERROR_MESSAGE);
					} else {
						if (ancienMail.equalsIgnoreCase(textEmail.getText())) {

							Role selectedRole = (Role) cmbRoles.getSelectedItem();
							User newUser = new User(Integer.parseInt(textId.getText()), selectedRole, textNom.getText(),
									textPrenom.getText(), textEmail.getText(), textUrl.getText());
							if (userD.update(newUser)) {
								JOptionPane.showMessageDialog(null,
										"Le " + nomModel + " " + newUser.getNom() + " a bien été enregistré",
										"Modification", JOptionPane.INFORMATION_MESSAGE);
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
								oculter();

							} else {
								JOptionPane.showMessageDialog(null, "Impossible de modifier le " + nomModel,
										"Modification", JOptionPane.ERROR_MESSAGE);
							}
						} else {

							if (!userD.isExist(textEmail.getText())) {

								Role selectedRole = (Role) cmbRoles.getSelectedItem();
								User newUser = new User(Integer.parseInt(textId.getText()), selectedRole,
										textNom.getText(), textPrenom.getText(), textEmail.getText(),
										textUrl.getText());

								if (userD.update(newUser)) {
									JOptionPane.showMessageDialog(null,
											"Le " + nomModel + " " + newUser.getNom() + " a bien été enregistré",
											"Modification", JOptionPane.INFORMATION_MESSAGE);
									tabbedPane.setEnabledAt(1, false);
									tabbedPane.setEnabledAt(0, true);
									tabbedPane.setSelectedIndex(0);
									oculter();
								} else {
									JOptionPane.showMessageDialog(null, "Impossible de modifier le " + nomModel,
											"Modification", JOptionPane.ERROR_MESSAGE);
								} // fin du update
							} else {
								JOptionPane.showMessageDialog(null, "Ce email existe déjà", "Création",
										JOptionPane.ERROR_MESSAGE);
							} // fin if isExiste
						} // fin if ancienNom egual new nom
					}
				} else if (action.equalsIgnoreCase("Sauvegarder")) {
					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textEmail.getText().equalsIgnoreCase("") || textEmail.getText().length() > 60
							|| textPassword1.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Création", JOptionPane.ERROR_MESSAGE);
					} else {
						if (userM.verifMailRegix(textEmail.getText())) {
							if (!userD.isExist(textEmail.getText())) {
								// Validation de password

								if (!textPassword1.getText().equals(textPassword2.getText())) {
									JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas",
											"Password", JOptionPane.ERROR_MESSAGE);
									textPassword1.requestFocus();
									return;
								}
								/*
								 * Pour recuperer le role selectinnée dan le ComboBox
								 */
								Role selectedRole = (Role) cmbRoles.getSelectedItem();
								User newUser = new User(selectedRole, textNom.getText(), textPrenom.getText(),
										textEmail.getText(), textUrl.getText(),
										String.valueOf(textPassword1.getText()));
								if (userD.create(newUser)) {
									JOptionPane.showMessageDialog(null,
											"Le " + nomModel + " " + newUser.getNom() + " a bien été enregistré",
											"Création", JOptionPane.INFORMATION_MESSAGE);
									tabbedPane.setEnabledAt(1, false);
									tabbedPane.setEnabledAt(0, true);
									tabbedPane.setSelectedIndex(0);
									oculter();

								} else {
									JOptionPane.showMessageDialog(null, "Impossible de créer le " + nomModel,
											"Création", JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Ce email existe déjà", "Création",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							// REgex
							JOptionPane.showMessageDialog(null,
									"Vous devez saisir un email valide Ex. mail@mail.com/fr.", "Error email",
									JOptionPane.WARNING_MESSAGE);
						}

					} // fin if name ==""
				} // fin if modifier or save
			}// fin ActionPerformed
		});// fin ActionListener

		/*
		 * passage vers la page creation de role
		 */
		JButton btnModifierPass = new JButton("Modifier");
		btnModifierPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!userD.isExistPass(Integer.parseInt(textId.getText()), String.valueOf(textPassword1.getText()))) {
					JOptionPane.showMessageDialog(null, "mot de passe invalide", "Mot de passe",
							JOptionPane.WARNING_MESSAGE);
					lblPassword1.setForeground(new Color(255, 0, 0));
					textPassword1.requestFocus();
					return;
				} else {
					if (String.valueOf(textPassword2.getText()).equals("")
							|| String.valueOf(textPassword3.getText()).equals("")) {
						JOptionPane.showMessageDialog(null, "Le champ (*) sont obligatoires", "Mot de passe",
								JOptionPane.WARNING_MESSAGE);
						lblPassword1.setForeground(new Color(0, 0, 0));
						textPassword2.requestFocus();
						lblPassword2.setForeground(new Color(255, 0, 0));
						lblPassword3.setForeground(new Color(255, 0, 0));
						return;

					}
					if (!String.valueOf(textPassword2.getText()).equals(String.valueOf(textPassword3.getText()))) {
						JOptionPane.showMessageDialog(null, "Les mots de passe doivent coïncider", "Mot de passe",
								JOptionPane.WARNING_MESSAGE);
						lblPassword1.setForeground(new Color(0, 0, 0));
						textPassword2.requestFocus();
						lblPassword2.setForeground(new Color(255, 0, 0));
						lblPassword3.setForeground(new Color(255, 0, 0));
						return;
					} else {
						lblPassword1.setForeground(new Color(0, 0, 0));
						lblPassword2.setForeground(new Color(0, 0, 0));
						lblPassword3.setForeground(new Color(0, 0, 0));

						if (userD.changePass(Integer.parseInt(textId.getText()),
								String.valueOf(textPassword2.getText()))) {
							JOptionPane.showMessageDialog(null, "Votre mot de passe a bien été modifié...", "Password",
									JOptionPane.INFORMATION_MESSAGE);
							textId.setEditable(true);
							textNom.setEditable(true);
							textPrenom.setEditable(true);
							textEmail.setEditable(true);
							btnAnnuler.setEnabled(true);
							btnSauvegarder.setEnabled(true);
							cmbRoles.setEnabled(true);
							panel_2.setVisible(false);
							textPassword3.setVisible(false);
							lblPassword3.setVisible(false);
							lblPassword1.setText("Mot de passe: (*)");
							lblPassword2.setText("Réécrivez votre Mot de passe : (*)");
							textPassword1.setText("");
							textPassword2.setText("");
							textPassword3.setText("");

							if (User.userLogin != null) {
								tabbedPane.removeAll();
								tabbedPane.repaint();
								tabbedPane.revalidate();

								panelMain.add(lblNewLabel_1);

							} else {
								tabbedPane.setEnabledAt(1, false);
								tabbedPane.setEnabledAt(0, true);
								tabbedPane.setSelectedIndex(0);
							}

						} else {
							JOptionPane.showMessageDialog(null, "Erreur, votre mot de passe n'a pas pu être modifié...",
									"Mot de passe", JOptionPane.ERROR_MESSAGE);
						}

					}

				}

			}
		});
		JButton btnAnullerPass = new JButton("Anuller");
		btnAnullerPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textId.setEditable(true);
				textNom.setEditable(true);
				textPrenom.setEditable(true);
				textEmail.setEditable(true);
				btnAnnuler.setEnabled(true);
				btnSauvegarder.setEnabled(true);
				cmbRoles.setEnabled(true);
				panel_2.setVisible(false);
				textPassword3.setVisible(false);
				lblPassword3.setVisible(false);
				lblPassword1.setText("Mot de passe : (*)");
				lblPassword2.setText("Réécrivez votre mot de passe : (*)");
				textPassword1.setText("");
				textPassword2.setText("");
				textPassword3.setText("");

				if (optionM == 2) {

					tabbedPane.removeAll();
					tabbedPane.repaint();
					tabbedPane.revalidate();
					panelMain.add(lblNewLabel_1);

				} else {
					tabbedPane.setEnabledAt(1, false);
					tabbedPane.setEnabledAt(0, true);
					tabbedPane.setSelectedIndex(0);
				}
			}
		});
		JButton btnNouveau = new JButton("Nouveau");
		btnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setEnabledAt(0, false);
				tabbedPane.setSelectedIndex(1);
				action = "Sauvegarder";
				btnSauvegarder.setText("Sauvegarder");
				btnModifierPass.setVisible(false);
				btnAnullerPass.setVisible(false);
			}
		});
		btnNouveau.setBounds(601, 33, 106, 23);
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
					textPrenom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					textEmail.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					ancienMail = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					textUrl.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
					// Chargement de Role
					Role SelectedRole = new Role(
							Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 5))),
							String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
					System.out.println(SelectedRole.getId() + " " + SelectedRole.getNom());

					ArrayList<Role> items = new ArrayList<>();
					items = roleD.selectRoles();
					System.err.println(items.size());

					// Pour recuperer le index de tableau pour afficher le nom de Role que
					// appartient au user
					int cont = 0;
					for (Role Itemrole : items) {
						if (Itemrole.getNom().equalsIgnoreCase(SelectedRole.getNom())) {
							break;
						}
						cont++;
					}
					// cmbRoles.setSelectedItem(SelectedRole);
					cmbRoles.setSelectedIndex(cont);
					// =================================================================================

					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);
					action = "Modifier";
					btnSauvegarder.setText("Modifier");
					panel_2.setVisible(false);

				}
			}
		});
		btnModifier.setBounds(717, 33, 106, 23);
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
					int idRow = (int) (table.getValueAt(table.getSelectedRow(), 5));
					if ((boolean) roleD.findById(idRow)) {
						JOptionPane.showMessageDialog(null,
								"Cet(te) employé(e) ne peut pas être activé(e) car son rôle est inactif", "Statut",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					/*
					 * recupération des données selectionnées avant instanciation
					 */
					String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					String nom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					String prenom = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
					String email = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					String url = String.valueOf(table.getValueAt(table.getSelectedRow(), 4));
					String idrol = String.valueOf(table.getValueAt(table.getSelectedRow(), 5));
					String nomrol = String.valueOf(table.getValueAt(table.getSelectedRow(), 6));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 7));
					if (stat.equalsIgnoreCase("actif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà actif", "Statut",
								JOptionPane.WARNING_MESSAGE);
					} else {
						User userNew = new User(Integer.parseInt(id), (Role) userD.findRolUser(Integer.parseInt(id)),
								nom, prenom, email, url, stat);
						if (userD.activer(userNew)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant actif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							oculter();
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
					String nom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					String prenom = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
					String email = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					String url = String.valueOf(table.getValueAt(table.getSelectedRow(), 4));
					String idrol = String.valueOf(table.getValueAt(table.getSelectedRow(), 5));
					String nomrol = String.valueOf(table.getValueAt(table.getSelectedRow(), 6));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 7));
					if (stat.equalsIgnoreCase("inactif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà inactif", "Statut",
								JOptionPane.WARNING_MESSAGE);

					} else {
						// Role roleuser = new Role(userD.findRolUser(Integer.parseInt(id)));

						User userNew = new User(Integer.parseInt(id), (Role) userD.findRolUser(Integer.parseInt(id)),
								nom, prenom, email, url, stat);
						if (userD.desactiver(userNew)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant inactif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							oculter();
						}
					}
				}
			}
		});
		btnDesactiver.setBounds(194, 18, 106, 23);
		panel_1.add(btnDesactiver);

		lblAffichage.setBounds(476, 387, 367, 19);
		panelListe.add(lblAffichage);

		JButton btnNewButton = new JButton("Changer le mot de passe");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Changemen de Password
				 * 
				 */
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Statut", JOptionPane.WARNING_MESSAGE);
				} else {

					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					textPrenom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					textEmail.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					ancienMail = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					textUrl.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
					// Chargement de Role
					Role SelectedRole = new Role(
							Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 5))),
							String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
					System.out.println(SelectedRole.getId() + " " + SelectedRole.getNom());

					ArrayList<Role> items = new ArrayList<>();
					items = roleD.selectRoles();
					System.err.println(items.size());

					// Pour recuperer le index de tableau pour afficher le nom de Role que
					// appartient au user
					int cont = 0;
					for (Role Itemrole : items) {
						if (Itemrole.getNom().equalsIgnoreCase(SelectedRole.getNom())) {
							break;
						}
						cont++;
					}
					// cmbRoles.setSelectedItem(SelectedRole);
					cmbRoles.setSelectedIndex(cont);

					// =================================================================================
					textId.setEditable(false);
					textNom.setEditable(false);
					textPrenom.setEditable(false);
					textEmail.setEditable(false);
					btnAnnuler.setEnabled(false);
					btnSauvegarder.setEnabled(false);
					cmbRoles.setEnabled(false);
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);
					action = "Modifier";
					btnSauvegarder.setText("Modifier");
					panel_2.setVisible(true);
					textPassword3.setVisible(true);
					lblPassword3.setVisible(true);
					lblPassword1.setText("Entrez votre mot de passe : (*)");
					lblPassword2.setText("Entrez votre nouveau mot de passe : (*)");

				}

			}
		});
		btnNewButton.setBounds(833, 33, 207, 23);
		panelListe.add(btnNewButton);

		tabbedPane.addTab("Gestion", null, panelGestion, null);
		panelGestion.setLayout(null);

		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setSelectedIndex(0);

		JLabel lblNom = new JLabel("Nom : (*)");
		lblNom.setBounds(10, 11, 70, 21);
		panelGestion.add(lblNom);

		textNom = new JTextField();
		textNom.setBounds(115, 12, 210, 20);
		panelGestion.add(textNom);
		textNom.setColumns(10);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(932, 11, 142, 20);
		panelGestion.add(textId);
		textId.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel("(*) Champs obligatoires");
		lblNewLabel_2.setBounds(10, 247, 142, 21);
		panelGestion.add(lblNewLabel_2);
		/*
		 * Annulation et retour a la page accueil role
		 */

		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setEnabledAt(1, false);
				tabbedPane.setEnabledAt(0, true);
				tabbedPane.setSelectedIndex(0);
				oculter();
			}
		});
		btnAnnuler.setBounds(70, 328, 106, 23);
		panelGestion.add(btnAnnuler);

		btnSauvegarder.setBounds(213, 328, 108, 23);
		panelGestion.add(btnSauvegarder);

		JLabel lblNewLabel = new JLabel("40 caractères maximum");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setBounds(115, 31, 119, 18);
		panelGestion.add(lblNewLabel);

		JLabel lblCaratresMaximum = new JLabel("Email unique");
		lblCaratresMaximum.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblCaratresMaximum.setBounds(115, 207, 119, 18);
		panelGestion.add(lblCaratresMaximum);

		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setBounds(10, 61, 70, 21);
		panelGestion.add(lblPrenom);

		textPrenom = new JTextField();
		textPrenom.setColumns(10);
		textPrenom.setBounds(115, 59, 210, 20);
		panelGestion.add(textPrenom);

		JLabel lblNewLabelchar = new JLabel("40 caractères maximum");
		lblNewLabelchar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabelchar.setBounds(115, 78, 119, 18);
		panelGestion.add(lblNewLabelchar);

		JLabel lblEmail = new JLabel("Email : (*)");
		lblEmail.setBounds(10, 187, 70, 21);
		panelGestion.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(115, 187, 210, 20);
		panelGestion.add(textEmail);

		cmbRoles.setBounds(115, 128, 210, 22);
		panelGestion.add(cmbRoles);

		JLabel lblRole = new JLabel("Role :");
		lblRole.setBounds(10, 129, 70, 21);
		panelGestion.add(lblRole);

		textUrl = new JTextField();
		textUrl.setColumns(10);
		textUrl.setBounds(932, 42, 142, 20);
		textUrl.setVisible(false);
		panelGestion.add(textUrl);

		panel_2.setBorder(new TitledBorder(null, "Mot de passe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(392, 64, 524, 204);
		panelGestion.add(panel_2);
		panel_2.setLayout(null);

		lblPassword1.setBounds(124, 27, 166, 21);
		panel_2.add(lblPassword1);
		textPassword1 = new JPasswordField();
		textPassword1.setBounds(281, 27, 176, 20);
		panel_2.add(textPassword1);

		textPassword2 = new JPasswordField();
		textPassword2.setBounds(281, 66, 176, 20);
		panel_2.add(textPassword2);

		lblPassword2.setBounds(45, 66, 245, 21);
		panel_2.add(lblPassword2);

		btnModifierPass.setBounds(292, 159, 91, 23);
		panel_2.add(btnModifierPass);

		textPassword3 = new JPasswordField();
		textPassword3.setBounds(281, 110, 176, 20);
		panel_2.add(textPassword3);
		textPassword3.setVisible(false);

		lblPassword3.setBounds(45, 110, 245, 21);
		panel_2.add(lblPassword3);

		btnAnullerPass.setBounds(149, 159, 91, 23);
		panel_2.add(btnAnullerPass);
		lblPassword3.setVisible(false);

		this.oculter();

		// Pour faire le order by de tableau Asc Desc
		TableRowSorter order = new TableRowSorter(table.getModel());
		table.setRowSorter(order);

		if (optionM == 2) {
			// =====Changement de fenêtre pour changer le
			// password================================
			tabbedPane.setSelectedIndex(1);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(0, false);
			// =====Chargement de donnes de utilisateur
			// actuelle=================================

			// ======================================
			textId.setEditable(false);
			textNom.setEditable(false);
			textPrenom.setEditable(false);
			textEmail.setEditable(false);
			btnAnnuler.setEnabled(false);
			btnSauvegarder.setEnabled(false);
			cmbRoles.setEnabled(false);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setSelectedIndex(1);
			action = "Modifier";
			btnSauvegarder.setText("Modifier");
			panel_2.setVisible(true);
			textPassword3.setVisible(true);
			lblPassword3.setVisible(true);
			lblPassword1.setText("Entrez votre mot de passe : (*)");
			lblPassword2.setText("Entrez votre nouveau mot de passe : (*)");

			textId.setText(String.valueOf(User.userLogin.getId()));
			textNom.setText(String.valueOf(User.userLogin.getNom()));
			textPrenom.setText(String.valueOf(User.userLogin.getPrenom()));
			textEmail.setText(String.valueOf(User.userLogin.getEmail()));
			ancienMail = User.userLogin.getEmail();
			textUrl.setText(String.valueOf(User.userLogin.getUrl()));
			// Chargement de Role
			Role SelectedRole = new Role(User.userLogin.getId_role().getId(), User.userLogin.getId_role().getNom());
			System.out.println(SelectedRole.getId() + " " + SelectedRole.getNom());

			ArrayList<Role> items = new ArrayList<>();
			items = roleD.selectRoles();
//		System.err.println(items.size());

			// Pour recuperer le index de tableau pour afficher le nom de Role que
			// appartient au user

//		ArrayList<Role> items = new ArrayList<>();
//		items = roleD.selectRoles();
//		System.err.println(items.size());
//		
//		//Pour recuperer le index de tableau pour afficher le nom de Role que appartient au user

//		int cont = 0;
//		for (Role Itemrole : items) {
//			if(Itemrole.getNom().equalsIgnoreCase(SelectedRole.getNom())) {
//				break;
//			}
//			cont++;
//		}
			cmbRoles.setSelectedItem(SelectedRole);
			// cmbRoles.setSelectedIndex(cont);
			// =================================================================================

		} else {
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.setSelectedIndex(0);
		}

	}

	public void oculter() {
		table.setModel(userM.lister(textField.getText()));
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
	}
}
