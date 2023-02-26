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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import controller.AdresseDao;
import controller.ClientDao;
import metier.AdresseMetier;
import metier.ClientMetier;
import model.Adresse;
import model.Client;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class VueClient extends JPanel {
	private JTextField textField;
	private JTable table;
	/*
	 * instanciation Class roleMetier
	 */
	ClientMetier clientM = new ClientMetier();
	AdresseMetier adresseM = new AdresseMetier();
	/*
	 * instanciation Class roleDao
	 */
	ClientDao clientD = new ClientDao();
	AdresseDao adresseDao = new AdresseDao();
	/*
	 * creation variable string role pour les differents messages d'affichage
	 * fenetre role
	 */
	String nomModel = "Client";
	/*
	 * creation variable pour les 2 actions create et update
	 */
	String action = "";
	String actionAdresse = "";
	/*
	 * creation variable pour recupere ancien nom lors de la modification de
	 * registre
	 */
	String ancienNom = "";

	private JTextField textNom = new JTextField();
	private JTextField textId = new JTextField();
	private JTextField textPrenom = new JTextField();
	private JTextField textTel = new JTextField();
	private JTextField textCodPostal = new JTextField();
	private JTextField textVille = new JTextField();
	private JTable tableAdresse = new JTable();
	private JTextField textIdAdresse = new JTextField();
	AdresseDao adresseD = new AdresseDao();
	private JTable tableAdresseListe = new JTable();
	JLabel lblTotalAdresse = new JLabel("");
	JButton btnAdresseEffacer = new JButton("Effacer Adresse");
	JButton btnAdresseMod = new JButton("Modifie Adresse");
	/*
	 * optionGestion = 1 -> Local de gestion Client
	 */

	/**
	 * Create the panel.
	 */

	public VueClient(int optionGestion) {
		System.out.println("ini " + Client.clientLast);
		//
		// optionGestion = 1 -> Menu option Gestion de clients.
		// optionGestion = 2 -> Menu option Commandes nouvelle Cliente.
		// optionGestion = 3 -> Menu option Commandes nouvelle Adresse.
		// optionGestion = 4 -> Menu option Commandes Modification Adresse.
		if (optionGestion == 1 || optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {

		}
		setBounds(0, 0, 1136, 565);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1136, 565);
		add(panel);
		panel.setLayout(null);

		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clients",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMain.setBounds(0, 10, 1136, 555);
		panel.add(panelMain);
		panelMain.setLayout(null);
		/*
		 * creation double intercalaires
		 */

		JTextArea textRue = new JTextArea();
		// textCodPostal = new JTextField();
		JPanel panelAdresseClient = new JPanel();
		JPanel panelListeAdresse = new JPanel();
		// textVille = new JTextField();
		JTextArea textComplement = new JTextArea();
		JButton btnSauvegarderAdresse = new JButton("Sauvegarder Adresse");

		btnAdresseEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableAdresse.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner une adresse dans le tableau", "Adresse",
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (adresseD.Delete(Integer
							.parseInt(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 0))))) {
						JOptionPane.showMessageDialog(null,
								"L'adresse de client " + textNom.getText() + " a bien été supprimé", "Adresse",
								JOptionPane.INFORMATION_MESSAGE);
						tableAdresse.setModel(adresseM.lister(textId.getText()));
						updatetadresse();
					} else {
						JOptionPane.showMessageDialog(null,
								"Impossible de supprimé l'adresse de client " + textNom.getText(), "Adresse",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JButton btnClientNoVisible = new JButton("Modifie");
		JPanel panelClient = new JPanel();
		JButton btnAnnuler = new JButton("Annuler");
		JButton btnSauvegarder = new JButton("Sauvegarder");
		JButton btnContinuer = new JButton("Continuer->");
		JButton btnReturner = new JButton("<-Returner");
		JButton btnAdresseNouvelle = new JButton("Nouvelle Adresse");
		JButton btnAnnulerAdresse = new JButton("Annuler");

		btnAdresseNouvelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAdresse = "Sauvegarder";

				textNom.setEditable(false);
				textPrenom.setEditable(false);
				textTel.setEditable(false);
				textRue.setEditable(true);
				textCodPostal.setEditable(true);
				textVille.setEditable(true);
				textComplement.setEditable(true);
				btnSauvegarderAdresse.setEnabled(true);
				btnAnnulerAdresse.setEnabled(true);
				btnAnnuler.setEnabled(false);
				btnSauvegarder.setEnabled(false);
				btnClientNoVisible.setVisible(true);

				btnAdresseMod.setEnabled(false);
				btnAdresseNouvelle.setEnabled(false);
				btnAdresseEffacer.setEnabled(false);

				tableAdresse.setModel(adresseM.lister(textId.getText()));
				textRue.requestFocus();

				if (optionGestion == 1) {
					btnReturner.setEnabled(false);
				} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
					btnContinuer.setVisible(false);
					btnContinuer.setEnabled(false);
					btnReturner.setVisible(true);
				}
			}
		});

		btnReturner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.setEnabledAt(1, false);
				tabbedPane.setEnabledAt(0, true);
				tabbedPane.setSelectedIndex(0);
				table.setModel(clientM.lister(textField.getText()));
				tableAdresseListe.setModel(adresseM.lister(""));

			}
		});

		btnAnnulerAdresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (optionGestion == 1) {
					int dialogButton1 = JOptionPane.showConfirmDialog(null,
							"Voulez-vous retourner à la page de liste clients, sans sauvegarder l'adresse ?", "WARNING",
							JOptionPane.YES_NO_OPTION);
					System.out.println("dialogButton Apres Click: " + dialogButton1);
					if (dialogButton1 == JOptionPane.YES_OPTION) {
						tabbedPane.setEnabledAt(1, false);
						tabbedPane.setEnabledAt(0, true);
						tabbedPane.setSelectedIndex(0);
						textIdAdresse.setText("");
						textRue.setText("");
						textCodPostal.setText("");
						textVille.setText("");
						textComplement.setText("");
						textRue.setEditable(false);
						textCodPostal.setEditable(false);
						textVille.setEditable(false);
						textComplement.setEditable(false);
						table.setModel(clientM.lister(textField.getText()));
						tableAdresseListe.setModel(adresseM.lister(""));
					}
					if (dialogButton1 == JOptionPane.NO_OPTION) {
						return;
					}
				} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
					int dialogButton1 = JOptionPane.showConfirmDialog(null,
							"Voulez-vous retourner à la page de commandes, sans sauvegarder l'adresse ?", "WARNING",
							JOptionPane.YES_NO_OPTION);
					if (dialogButton1 == JOptionPane.YES_OPTION) {
						panel.removeAll();
						panel.add(new VueCommande(1));
						panel.repaint();
						panel.revalidate();
					}
					if (dialogButton1 == JOptionPane.NO_OPTION) {
						System.out.println("Return NO: ");
						return;
					}
				}

			}
		});

		btnClientNoVisible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// panelClient.setEnabled(true);
				btnClientNoVisible.setVisible(false);

				btnContinuer.setEnabled(true);

				textNom.setEditable(true);

				textPrenom.setEditable(true);
				textTel.setEditable(true);
				btnAnnuler.setEnabled(true);
				btnSauvegarder.setEnabled(true);
				////
				// panelAdresseClient.setEnabled(false);
				textIdAdresse.setEditable(false);
				textRue.setEditable(false);
				textCodPostal.setEditable(false);
				textVille.setEditable(false);
				textComplement.setEditable(false);
				btnSauvegarderAdresse.setEnabled(false);

				// panelListeAdresse.setEnabled(false);
				btnAdresseMod.setEnabled(false);
				btnAdresseEffacer.setEnabled(false);
				btnAdresseNouvelle.setEnabled(false);

				action = "modifier";
				btnSauvegarder.setText("Modifier");
				textNom.requestFocus();
			}
		});

		tabbedPane.setBounds(10, 31, 1116, 514);
		panelMain.add(tabbedPane);

		JPanel panelListe = new JPanel();
		tabbedPane.addTab("Liste", null, panelListe, null);
		panelListe.setLayout(null);

		JPanel panelGestion = new JPanel();
		tabbedPane.addTab("Gestion Client", null, panelGestion, null);
		panelGestion.setLayout(null);

		JLabel lblRecherche = new JLabel("Rechercher par nom :");
		lblRecherche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRecherche.setBounds(20, 11, 124, 27);
		panelListe.add(lblRecherche);
		textField = new JTextField();
		textField.setBounds(194, 16, 237, 19);
		panelListe.add(textField);
		textField.setColumns(10);

		/*
		 * Creation table liste Role
		 */

		table = new JTable();
		JLabel lblAffichage_1 = new JLabel("Affichage d'un total de 0 adresse(s)");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				tableAdresseListe
						.setModel(adresseM.lister(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

				lblAffichage_1.setText("Affichage d'un total de "
						+ adresseD.totalA(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))))
						+ " adresse(s)");
				;

			}
		});
		table.setModel(clientM.lister(""));
		JLabel lblAffichage = new JLabel("Affichage d'un total de " + clientD.total() + " client(s)");
		JButton btnChercher = new JButton("Chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(clientM.lister(textField.getText()));
				@SuppressWarnings("rawtypes")
				TableRowSorter order = new TableRowSorter(table.getModel());
				table.setRowSorter(order);
				lblAffichage.setText("Affichage de " + clientM.totalM + " registres sur un total de " + clientD.total()
						+ " registres");
				tableAdresseListe.setModel(adresseM.lister(""));
				lblAffichage_1.setText("Affichage d'un total de 0 adresse(s)");
			}
		});
		btnChercher.setBounds(467, 15, 106, 23);
		panelListe.add(btnChercher);
		/*
		 * conditions pour les create et update, en fonction de la variable Action
		 * initialisée plus haut
		 */

		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (action.equalsIgnoreCase("modifier")) {
					actionAdresse = "Modifier";
					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textPrenom.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Modification", JOptionPane.ERROR_MESSAGE);
					} else {
						String tel = textTel.getText().trim();
						if (clientM.checkRegexTel(tel)) {
							Client newClient = new Client(Integer.parseInt(textId.getText()), textNom.getText(),
									textPrenom.getText(), textTel.getText());
							if (clientD.update(newClient)) {
								JOptionPane.showMessageDialog(null,
										"Le client " + newClient.getNom() + " a bien été enregistré", "Création",
										JOptionPane.INFORMATION_MESSAGE);
								updatetadresse();
								btnAdresseMod.setEnabled(true);
								btnAdresseEffacer.setEnabled(true);
								btnAdresseNouvelle.setEnabled(true);
								textNom.setEditable(false);
								textPrenom.setEditable(false);
								textTel.setEditable(false);

								btnAnnuler.setEnabled(false);
								btnSauvegarder.setEnabled(false);
								btnClientNoVisible.setVisible(true);

								if (optionGestion == 1) {

									btnReturner.setEnabled(true);
								} else {

									btnContinuer.setEnabled(true);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Impossible de créer le " + nomModel, "Création",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"le numéro de téléphone doit être composé uniquement de chiffres " + nomModel,
									"Création", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				// Nouvelle client
				else if (action.equalsIgnoreCase("Sauvegarder")) {
					if (textNom.getText().equalsIgnoreCase("") || textNom.getText().length() > 40
							|| textPrenom.getText().length() > 100) {
						JOptionPane.showMessageDialog(null,
								"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
								"Création", JOptionPane.ERROR_MESSAGE);
					} else {

						String tel = textTel.getText().trim();
						if (clientM.checkRegexTel(tel)) {
							Client newClient = new Client(textNom.getText().trim(), textPrenom.getText().trim(), tel);
							if (clientD.create(newClient)) {
								JOptionPane.showMessageDialog(null,
										"Le client " + newClient.getNom() + " a bien été enregistré", "Création",
										JOptionPane.INFORMATION_MESSAGE);

								textId.setText(String.valueOf(clientD.dernierIdClient()));
								updatetadresse();

								textNom.setEditable(false);
								textPrenom.setEditable(false);
								textTel.setEditable(false);
								btnAnnuler.setEnabled(false);
								btnSauvegarder.setEnabled(false);
								btnClientNoVisible.setVisible(true);

								textRue.setEditable(true);

								textCodPostal.setEditable(true);
								textVille.setEditable(true);
								textComplement.setEditable(true);
								btnSauvegarderAdresse.setEnabled(true);
								btnAnnulerAdresse.setEnabled(true);
								// panelListeAdresse.setEnabled(false);
								btnAdresseNouvelle.setEnabled(false);
								btnAdresseMod.setEnabled(false);
								btnAdresseEffacer.setEnabled(false);
								btnAnnuler.setEnabled(false);
								btnSauvegarder.setEnabled(false);
								btnClientNoVisible.setVisible(true);

								btnContinuer.setVisible(false);
								btnReturner.setVisible(true);
								btnReturner.setEnabled(false);

								tableAdresse.setModel(adresseM.lister(textId.getText()));

								if (optionGestion == 1) {

									btnReturner.setEnabled(true);
								} else {
									btnContinuer.setEnabled(true);
								}
								textRue.requestFocus();
							} else {
								JOptionPane.showMessageDialog(null, "Impossible de créer le " + nomModel, "Création",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"le numéro de téléphone doit être composé uniquement de chiffres " + nomModel,
									"Création", JOptionPane.ERROR_MESSAGE);
						}

//================================

					} // fin if name ==""
				} // fin if modifier or save
			}// fin ActionPerformed
		});// fin ActionListener

		/*
		 * passage vers la page creation de role
		 */
		/*
		 * passage vers la page modification de role
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 63, 795, 120);
		panelListe.add(scrollPane);
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Statuts Client", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(20, 438, 348, 49);
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
					String prenom = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
					String tel = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 4));
					if (stat.equalsIgnoreCase("active")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà actif", "Statut",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Client newClient = new Client(Integer.parseInt(id), nom, prenom, tel, stat);
						if (clientD.activer(newClient)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant actif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							table.setModel(clientM.lister(textField.getText()));
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
					String tel = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					String stat = String.valueOf(table.getValueAt(table.getSelectedRow(), 4));
					if (stat.equalsIgnoreCase("inactif")) {
						JOptionPane.showMessageDialog(null, "Le " + nomModel + " est déjà inactif", "Statut",
								JOptionPane.WARNING_MESSAGE);

					} else {
						Client newClient = new Client(Integer.parseInt(id), nom, prenom, tel, stat);
						if (clientD.desactiver(newClient)) {
							JOptionPane.showMessageDialog(null, "Le " + nomModel + " est maintenant inactif", "Statut",
									JOptionPane.INFORMATION_MESSAGE);
							table.setModel(clientM.lister(textField.getText()));
						}
					}
				}
			}
		});
		btnDesactiver.setBounds(194, 18, 106, 23);
		panel_1.add(btnDesactiver);

		lblAffichage.setBounds(494, 194, 367, 19);
		panelListe.add(lblAffichage);

		if (optionGestion == 1) {
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.setSelectedIndex(0);

			btnReturner.setVisible(true);
			btnReturner.setEnabled(false);

			btnContinuer.setVisible(false);
			btnContinuer.setEnabled(false);
			System.out.println("In option 1");

		} else if (optionGestion == 2) {
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setSelectedIndex(1);
			action = "Sauvegarder";

			textNom.setText("");
			textPrenom.setText("");
			textTel.setText(Client.newClientTel);
			textNom.setEditable(true);
			textPrenom.setEditable(true);
			textTel.setEditable(true);
			btnAnnuler.setEnabled(true);
			btnSauvegarder.setEnabled(true);
			btnClientNoVisible.setVisible(false);

			btnSauvegarder.setText("Sauvegarder");
			textRue.setEditable(false);
			textCodPostal.setEditable(false);
			textVille.setEditable(false);
			textComplement.setEditable(false);
			btnSauvegarderAdresse.setEnabled(false);
			// panelListeAdresse.setEnabled(false);
			btnAdresseMod.setEnabled(false);
			btnAdresseEffacer.setEnabled(false);
			btnAdresseNouvelle.setEnabled(false);

			btnContinuer.setVisible(true);
			btnContinuer.setEnabled(false);
			btnReturner.setVisible(false);

			btnAnnulerAdresse.setEnabled(false);
			textNom.requestFocus();
			System.out.println("In option 2");
		} else if (optionGestion == 3) {
			System.out.println("Avant tabbed" + Client.clientLast);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setSelectedIndex(1);
			System.out.println("Apres tabbed" + Client.clientLast);
			actionAdresse = "Sauvegarder";
			textId.setText(String.valueOf(Client.clientLast.getId()));
			textNom.setText(Client.clientLast.getNom());
			textPrenom.setText(Client.clientLast.getPrenom());
			textTel.setText(Client.clientLast.getNumtel());

			textNom.setEditable(false);
			textPrenom.setEditable(false);
			textTel.setEditable(false);

			textRue.setEditable(true);
			textCodPostal.setEditable(true);
			textVille.setEditable(true);
			textComplement.setEditable(true);
			btnSauvegarderAdresse.setEnabled(true);
			// panelListeAdresse.setEnabled(false);
			btnAdresseNouvelle.setEnabled(false);
			btnAdresseMod.setEnabled(false);
			btnAdresseEffacer.setEnabled(false);

			btnAnnuler.setEnabled(false);
			btnSauvegarder.setEnabled(false);
			btnClientNoVisible.setVisible(true);

			btnContinuer.setVisible(true);
			btnContinuer.setEnabled(false);
			btnReturner.setVisible(false);
			tableAdresse.setModel(adresseM.lister(textId.getText()));
			textRue.requestFocus();
			System.out.println("In option 3");

		} else {
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setSelectedIndex(1);
			actionAdresse = "Modifier";
			textId.setText(String.valueOf(Client.clientLast.getId()));
			textNom.setText(Client.clientLast.getNom());
			textPrenom.setText(Client.clientLast.getPrenom());
			textTel.setText(Client.clientLast.getNumtel());

			textNom.setEditable(false);
			textPrenom.setEditable(false);
			textTel.setEditable(false);

			textRue.setEditable(true);

			textCodPostal.setEditable(true);
			textVille.setEditable(true);
			textComplement.setEditable(true);
			btnSauvegarderAdresse.setEnabled(true);

			textIdAdresse.setText(String.valueOf(Adresse.adresseLast.getId()));
			textRue.setText(Adresse.adresseLast.getRue());

			textCodPostal.setText(Adresse.adresseLast.getCod_postal());
			textVille.setText(Adresse.adresseLast.getVille());
			textComplement.setText(Adresse.adresseLast.getComplement());
			btnSauvegarderAdresse.setEnabled(true);

			btnAdresseMod.setEnabled(false);
			btnAdresseEffacer.setEnabled(false);
			btnAdresseNouvelle.setEnabled(false);

			btnAnnuler.setEnabled(false);
			btnSauvegarder.setEnabled(false);
			btnClientNoVisible.setVisible(true);

			btnContinuer.setVisible(true);
			btnContinuer.setEnabled(false);
			btnReturner.setVisible(false);
			tableAdresse.setModel(adresseM.lister(textId.getText()));
			textRue.requestFocus();
			System.out.println("In option 4");
		}

		JLabel lblNom = new JLabel("Nom : (*)");
		lblNom.setBounds(10, 76, 70, 21);
		panelGestion.add(lblNom);

		JLabel lblDescription = new JLabel("Prénom :");
		lblDescription.setBounds(10, 137, 70, 21);
		panelGestion.add(lblDescription);

		// textNom = new JTextField();
		textNom.setBounds(76, 76, 188, 20);
		panelGestion.add(textNom);
		textNom.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("(*) Champs obligatoires");
		lblNewLabel_2.setBounds(23, 262, 142, 21);
		panelGestion.add(lblNewLabel_2);
		/*
		 * Annulation et retour a la page accueil
		 */

		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (optionGestion == 1) {
					int dialogButton1 = JOptionPane.showConfirmDialog(null,
							"Voulez-vous retourner à la page de liste clients, sans Sauvegarder le client ?", "WARNING",
							JOptionPane.YES_NO_OPTION);
					System.out.println("dialogButton Apres Click: " + dialogButton1);
					if (dialogButton1 == JOptionPane.YES_OPTION) {
						tabbedPane.setEnabledAt(1, false);
						tabbedPane.setEnabledAt(0, true);
						tabbedPane.setSelectedIndex(0);
						table.setModel(clientM.lister(textField.getText()));
						tableAdresseListe.setModel(adresseM.lister(""));
					}
					if (dialogButton1 == JOptionPane.NO_OPTION) {
						return;
					}
				} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
					int dialogButton1 = JOptionPane.showConfirmDialog(null,
							"Voulez-vous retourner à la page de commandes, sans Sauvegarder le client ?", "WARNING",
							JOptionPane.YES_NO_OPTION);
					if (dialogButton1 == JOptionPane.YES_OPTION) {
						panel.removeAll();
						panel.add(new VueCommande(1));
						panel.repaint();
						panel.revalidate();
					}
					if (dialogButton1 == JOptionPane.NO_OPTION) {
						return;
					}
				}

			}
		});
		btnAnnuler.setBounds(34, 361, 106, 23);
		panelGestion.add(btnAnnuler);

		btnSauvegarder.setBounds(150, 361, 108, 23);
		panelGestion.add(btnSauvegarder);

		JLabel lblNewLabel = new JLabel("40 caractères maximum");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setBounds(76, 93, 119, 18);
		panelGestion.add(lblNewLabel);

		// textPrenom = new JTextField();
		textPrenom.setColumns(10);
		textPrenom.setBounds(76, 137, 188, 20);
		panelGestion.add(textPrenom);

		JLabel lblTlphone = new JLabel("Téléphone: (*)");
		lblTlphone.setBounds(10, 198, 95, 21);
		panelGestion.add(lblTlphone);

		// textTel = new JTextField();
		textTel.setColumns(10);
		textTel.setBounds(115, 198, 149, 20);
		panelGestion.add(textTel);

		JLabel lblNewLabel_1 = new JLabel("40 caractères maximum");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(76, 155, 119, 18);
		panelGestion.add(lblNewLabel_1);

		JPanel panelAdresse = new JPanel();
		panelAdresse.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Adresse Client", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAdresse.setBounds(274, 21, 837, 454);
		panelGestion.add(panelAdresse);
		panelAdresse.setLayout(null);
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Nouvell Adresse
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		panelAdresseClient.setBorder(
				new TitledBorder(null, "Nouvelle Adresse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdresseClient.setBounds(10, 22, 289, 421);
		panelAdresse.add(panelAdresseClient);
		panelAdresseClient.setLayout(null);

		btnSauvegarderAdresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textRue.getText().equals("") || textCodPostal.getText().equals("") || textVille.getText().equals("")
						|| textRue.getText().length() > 60 || textCodPostal.getText().length() > 5
						|| textVille.getText().length() > 40 || textComplement.getText().length() > 60) {
					JOptionPane.showMessageDialog(null,
							"Merci de remplir les champs obligatoire(*) et de respecter le nombre de caractères",
							"Adresse Client", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (actionAdresse.equalsIgnoreCase("Modifier")) {
					System.out.println("Entra al if modificar");
					Adresse adresse = new Adresse(Integer.parseInt(textIdAdresse.getText()),
							Integer.parseInt(textId.getText()), textRue.getText(), textCodPostal.getText(),
							textVille.getText(), textComplement.getText());
					System.out.println("Antes del update");
					if (adresseD.update(adresse)) {
						JOptionPane.showMessageDialog(null,
								"L'adresse de client " + textNom.getText() + " a bien été modifié", "Adresse",
								JOptionPane.INFORMATION_MESSAGE);

						updatetadresse();
						textRue.setText("");
						textCodPostal.setText("");
						textVille.setText("");
						textComplement.setText("");
						textRue.setEditable(false);
						textCodPostal.setEditable(false);
						textVille.setEditable(false);
						textComplement.setEditable(false);
						btnSauvegarderAdresse.setEnabled(false);
						btnAnnulerAdresse.setEnabled(false);
						btnAdresseMod.setEnabled(true);
						btnAdresseEffacer.setEnabled(true);
						btnAdresseNouvelle.setEnabled(true);
						btnAnnulerAdresse.setEnabled(false);
						tableAdresse.setModel(adresseM.lister(textId.getText()));

						if (optionGestion == 1) {
							btnReturner.setEnabled(true);
						} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
							btnContinuer.setVisible(true);
							btnContinuer.setEnabled(true);
							btnReturner.setVisible(false);
						}

					} else {
						JOptionPane.showMessageDialog(null,
								"Impossible de modifié l'adresse de client " + textNom.getText(), "Adresse",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					Adresse adresse = new Adresse(Integer.parseInt(textId.getText()), textRue.getText(),
							textCodPostal.getText(), textVille.getText(), textComplement.getText());
					System.out.println("Antes de crear la nueva direccion del nuevo user");
					System.out.println("ID " + Integer.parseInt(textId.getText()));
					if (adresseD.create(adresse)) {
						JOptionPane.showMessageDialog(null,
								"L'adresse de client " + textNom.getText() + " a bien été enregistré", "Adresse",
								JOptionPane.INFORMATION_MESSAGE);
						updatetadresse();
						textRue.setText("");
						textCodPostal.setText("");
						textVille.setText("");
						textComplement.setText("");
						// textIdAdresse.setText("");
						textRue.setEditable(false);
						textCodPostal.setEditable(false);
						textVille.setEditable(false);
						textComplement.setEditable(false);
						btnSauvegarderAdresse.setEnabled(false);

						btnAdresseMod.setEnabled(true);
						btnAdresseEffacer.setEnabled(true);
						btnAdresseNouvelle.setEnabled(true);
						btnAnnulerAdresse.setEnabled(false);
						System.out.println("Despues de guardar la adresse intenta actualiza " + textId.getText());
						tableAdresse.setModel(adresseM.lister(textId.getText()));

						if (optionGestion == 1) {
							btnReturner.setEnabled(true);

						} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
							btnContinuer.setVisible(true);
							btnContinuer.setEnabled(true);
							btnReturner.setVisible(false);
						}

					} else {
						JOptionPane.showMessageDialog(null,
								"Impossible de créer l'adresse de client " + textNom.getText(), "Adresse",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		btnSauvegarderAdresse.setBounds(139, 375, 146, 23);
		panelAdresseClient.add(btnSauvegarderAdresse);

		textComplement.setBounds(10, 272, 256, 73);
		panelAdresseClient.add(textComplement);

		JLabel lblComplement = new JLabel("Complement:");
		lblComplement.setBounds(10, 247, 123, 14);
		panelAdresseClient.add(lblComplement);

		JLabel lblVille = new JLabel("Ville: (*)");
		lblVille.setBounds(10, 219, 85, 14);
		panelAdresseClient.add(lblVille);

		textVille.setBounds(120, 216, 146, 20);
		panelAdresseClient.add(textVille);
		textVille.setColumns(10);

		textCodPostal.setBounds(120, 167, 146, 20);
		panelAdresseClient.add(textCodPostal);
		textCodPostal.setColumns(10);

		JLabel lblCodPostal = new JLabel("Code Postal: (*)");
		lblCodPostal.setBounds(10, 170, 100, 14);
		panelAdresseClient.add(lblCodPostal);

		textRue.setBounds(10, 65, 256, 73);
		panelAdresseClient.add(textRue);

		JLabel lblRue = new JLabel("Rue: (*)");
		lblRue.setBounds(10, 40, 64, 14);
		panelAdresseClient.add(lblRue);

		// textIdAdresse = new JTextField();
		textIdAdresse.setBounds(160, 22, 106, 20);
		panelAdresseClient.add(textIdAdresse);
		textIdAdresse.setColumns(10);

		JLabel lblNewLabel_1_1_1 = new JLabel("60 chiffres maximum");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1_1_1.setBounds(147, 138, 119, 18);
		panelAdresseClient.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("5 chiffres maximum");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1_1_1_1.setBounds(147, 187, 119, 18);
		panelAdresseClient.add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("40 chiffres maximum");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1_1_1_1_1.setBounds(147, 232, 119, 18);
		panelAdresseClient.add(lblNewLabel_1_1_1_1_1);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("60 chiffres maximum");
		lblNewLabel_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1_1_1_2.setBounds(147, 346, 119, 18);
		panelAdresseClient.add(lblNewLabel_1_1_1_2);

		btnAnnulerAdresse.setBounds(23, 375, 106, 23);
		panelAdresseClient.add(btnAnnulerAdresse);
		textIdAdresse.setVisible(false);

		panelListeAdresse
				.setBorder(new TitledBorder(null, "Liste Adresse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelListeAdresse.setBounds(303, 22, 524, 421);
		panelAdresse.add(panelListeAdresse);
		panelListeAdresse.setLayout(null);

		btnAdresseMod.setBounds(212, 286, 146, 23);
		panelListeAdresse.add(btnAdresseMod);

		btnAdresseEffacer.setBounds(368, 286, 146, 23);
		panelListeAdresse.add(btnAdresseEffacer);

		JScrollPane scrollPaneAdresse = new JScrollPane();
		scrollPaneAdresse.setBounds(10, 39, 504, 210);
		panelListeAdresse.add(scrollPaneAdresse);

		// tableAdresse = new JTable();
		tableAdresse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		scrollPaneAdresse.setViewportView(tableAdresse);

		btnContinuer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// panel.setVisible(false);
				if (tableAdresse.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner une adresse dans le tableau", "Adresse",
							JOptionPane.WARNING_MESSAGE);
				} else {

					Adresse.adresseLast = new Adresse((int) (tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 0)),
							Integer.parseInt(textId.getText()),
							String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 1)),
							String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 2)),
							String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 3)),
							String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 4)));

					Client.clientLast = new Client(Integer.parseInt(textId.getText()), textNom.getText(),
							textPrenom.getText(), textTel.getText());

					System.out.println("ID: " + Client.clientLast.getId());
					System.out.println("NOM: " + Client.clientLast.getNom());
					System.out.println("PRENOM: " + Client.clientLast.getPrenom());
					System.out.println("TEL: " + Client.clientLast.getNumtel());

					panel.removeAll();
					panel.add(new VueCommande(2));
					panel.repaint();
					panel.revalidate();

				}

			}
		});
		btnContinuer.setBounds(401, 387, 113, 23);
		panelListeAdresse.add(btnContinuer);

		btnReturner.setBounds(401, 360, 113, 23);
		panelListeAdresse.add(btnReturner);

		btnAdresseNouvelle.setBounds(56, 286, 146, 23);
		panelListeAdresse.add(btnAdresseNouvelle);

		lblTotalAdresse.setBounds(172, 260, 327, 14);
		panelListeAdresse.add(lblTotalAdresse);

		panelClient.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Client",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelClient.setBounds(0, 21, 276, 454);
		panelGestion.add(panelClient);
		panelClient.setLayout(null);

		// textId = new JTextField();
		textId.setBounds(10, 423, 106, 20);
		panelClient.add(textId);
		textId.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("10 chiffres maximum");
		lblNewLabel_1_1.setBounds(114, 195, 119, 18);
		panelClient.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 9));

		btnClientNoVisible.setBounds(92, 371, 106, 23);
		panelClient.add(btnClientNoVisible);
		btnClientNoVisible.setVisible(false);
		textId.setVisible(false);
		btnAdresseMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableAdresse.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner une adresse dans le tableau", "Adresse",
							JOptionPane.WARNING_MESSAGE);
				} else {
					actionAdresse = "Modifier";

					textRue.setEditable(true);
					textCodPostal.setEditable(true);
					textVille.setEditable(true);
					textComplement.setEditable(true);
					btnSauvegarderAdresse.setEnabled(true);
					btnAnnulerAdresse.setEnabled(true);
					panelAdresseClient.setEnabled(true);

					textIdAdresse.setText(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 0)));
					textRue.setText(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 1)));
					textCodPostal.setText(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 2)));
					textVille.setText(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 3)));
					textComplement.setText(String.valueOf(tableAdresse.getValueAt(tableAdresse.getSelectedRow(), 4)));

					btnAdresseMod.setEnabled(false);
					btnAdresseNouvelle.setEnabled(false);
					btnAdresseEffacer.setEnabled(false);

					tableAdresse.setModel(adresseM.lister(textId.getText()));
					textRue.requestFocus();

					if (optionGestion == 1) {
						btnReturner.setEnabled(false);
					} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
						btnContinuer.setVisible(false);
						btnContinuer.setEnabled(false);
						btnReturner.setVisible(true);
					}

				}
			}
		});

		/*
		 * Tri asc desc pour le tabeau produit
		 */

		TableRowSorter order = new TableRowSorter(table.getModel());
		table.setRowSorter(order);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 253, 795, 103);
		panelListe.add(scrollPane_1);

		tableAdresseListe = new JTable();
		scrollPane_1.setViewportView(tableAdresseListe);

		JButton btnNouvelleAdresse = new JButton("Nouvelle Adresse");
		btnNouvelleAdresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Client-Adresse", JOptionPane.WARNING_MESSAGE);
				} else {
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);

					actionAdresse = "Sauvegarder";
					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					ancienNom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					textPrenom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					textTel.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					tableAdresse.setModel(adresseM.lister(textId.getText()));
					updatetadresse();
					textNom.setEditable(false);
					textPrenom.setEditable(false);
					textTel.setEditable(false);

					textRue.setEditable(true);

					textCodPostal.setEditable(true);
					textVille.setEditable(true);
					textComplement.setEditable(true);
					btnSauvegarderAdresse.setEnabled(true);
					btnAnnulerAdresse.setEnabled(true);
					// panelListeAdresse.setEnabled(false);
					btnAdresseNouvelle.setEnabled(false);
					btnAdresseMod.setEnabled(false);
					btnAdresseEffacer.setEnabled(false);
					btnAdresseMod.setEnabled(false);
					btnAdresseEffacer.setEnabled(false);
					btnAnnuler.setEnabled(false);
					btnSauvegarder.setEnabled(false);
					btnClientNoVisible.setVisible(true);

					btnContinuer.setVisible(false);
					btnReturner.setVisible(true);
					btnReturner.setEnabled(false);

					tableAdresse.setModel(adresseM.lister(textId.getText()));
					textRue.requestFocus();
				}
			}
		});
		btnNouvelleAdresse.setBounds(825, 260, 144, 23);
		panelListe.add(btnNouvelleAdresse);

		JButton btnModifierAdresse = new JButton("Modifier Adresse");
		btnModifierAdresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableAdresseListe.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner une adresse dans le tableau",
							"Client-Adresse", JOptionPane.WARNING_MESSAGE);
				} else {
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);

					actionAdresse = "modifier";
					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					ancienNom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					textPrenom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					textTel.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					tableAdresse.setModel(adresseM.lister(textId.getText()));
					updatetadresse();
					textNom.setEditable(false);
					textPrenom.setEditable(false);
					textTel.setEditable(false);

					// tableAdresseListe
					textIdAdresse.setText(
							String.valueOf(tableAdresseListe.getValueAt(tableAdresseListe.getSelectedRow(), 0)));
					textRue.setText(
							String.valueOf(tableAdresseListe.getValueAt(tableAdresseListe.getSelectedRow(), 1)));
					textCodPostal.setText(
							String.valueOf(tableAdresseListe.getValueAt(tableAdresseListe.getSelectedRow(), 2)));
					textVille.setText(
							String.valueOf(tableAdresseListe.getValueAt(tableAdresseListe.getSelectedRow(), 3)));
					textComplement.setText(
							String.valueOf(tableAdresseListe.getValueAt(tableAdresseListe.getSelectedRow(), 4)));

					textRue.setEditable(true);

					textCodPostal.setEditable(true);
					textVille.setEditable(true);
					textComplement.setEditable(true);
					btnSauvegarderAdresse.setEnabled(true);
					btnAnnulerAdresse.setEnabled(true);
					// panelListeAdresse.setEnabled(false);
					btnAdresseNouvelle.setEnabled(false);
					btnAdresseMod.setEnabled(false);
					btnAdresseEffacer.setEnabled(false);

					btnAnnuler.setEnabled(false);
					btnSauvegarder.setEnabled(false);
					btnClientNoVisible.setVisible(true);

					btnContinuer.setVisible(false);
					btnReturner.setVisible(true);
					btnReturner.setEnabled(false);

					tableAdresse.setModel(adresseM.lister(textId.getText()));
					textRue.requestFocus();
				}
			}
		});
		btnModifierAdresse.setBounds(825, 294, 144, 23);
		panelListe.add(btnModifierAdresse);

		lblAffichage_1.setBounds(494, 367, 367, 19);
		panelListe.add(lblAffichage_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Liste Clients", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 40, 995, 183);
		panelListe.add(panel_2);
		panel_2.setLayout(null);
		JButton btnNouveau = new JButton("Nouveau Client");
		btnNouveau.setBounds(814, 34, 143, 23);
		panel_2.add(btnNouveau);
		JButton btnModifier = new JButton("Modifier Client");
		btnModifier.setBounds(814, 70, 143, 23);
		panel_2.add(btnModifier);
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumnCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de selectionner un " + nomModel + " dans le tableau",
							"Statut", JOptionPane.WARNING_MESSAGE);
				} else {

					textId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					textNom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
					ancienNom = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
					textPrenom.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
					textTel.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
					tableAdresse.setModel(adresseM.lister(textId.getText()));
					updatetadresse();
					textNom.setEditable(true);
					textPrenom.setEditable(true);
					textTel.setEditable(true);
					btnAnnuler.setEnabled(true);
					btnSauvegarder.setEnabled(true);
					btnClientNoVisible.setVisible(false);
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(0, false);
					tabbedPane.setSelectedIndex(1);

					action = "Modifier";

					btnSauvegarder.setText("Modifier");
					lblAffichage_1.setText("Affichage de un total de 0 adresse(s)");

					textIdAdresse.setEditable(false);
					textRue.setEditable(false);
					textCodPostal.setEditable(false);
					textVille.setEditable(false);
					textComplement.setEditable(false);
					btnSauvegarderAdresse.setEnabled(false);

					// panelListeAdresse.setEnabled(false);
					btnAdresseMod.setEnabled(false);
					btnAdresseEffacer.setEnabled(false);
					btnAdresseNouvelle.setEnabled(false);
					btnAnnulerAdresse.setEnabled(false);

					if (optionGestion == 1) {
						btnReturner.setEnabled(false);
					} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
						btnContinuer.setEnabled(false);
						btnContinuer.setVisible(false);
					}
				}
				textNom.grabFocus();
				textNom.requestFocus();

			}
		});
		btnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setEnabledAt(0, false);
				tabbedPane.setSelectedIndex(1);
				action = "Sauvegarder";
				btnSauvegarder.setText("Sauvegarder");

				textNom.setText("");
				textPrenom.setText("");
				textTel.setText("");
				textNom.setEditable(true);
				textPrenom.setEditable(true);
				textTel.setEditable(true);
				btnAnnuler.setEnabled(true);
				btnSauvegarder.setEnabled(true);
				btnClientNoVisible.setVisible(false);

				// panel1
				// panelAdresseClient.setEnabled(false);
				textRue.setEditable(false);
				textCodPostal.setEditable(false);
				textVille.setEditable(false);
				textComplement.setEditable(false);
				btnSauvegarderAdresse.setEnabled(false);
				// panelListeAdresse.setEnabled(false);
				btnAdresseMod.setEnabled(false);
				btnAdresseEffacer.setEnabled(false);
				btnAnnulerAdresse.setEnabled(false);
				btnAdresseNouvelle.setEnabled(false);
				lblAffichage_1.setText("Affichage de un total de 0 adresse(s)");
				if (optionGestion == 1) {
					btnReturner.setEnabled(false);
				} else if (optionGestion == 2 || optionGestion == 3 || optionGestion == 4) {
					btnContinuer.setEnabled(false);
					btnContinuer.setVisible(false);
				}

				textNom.requestFocus();

			}
		});

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Liste Adresse de Client", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2_1.setBounds(10, 234, 995, 183);
		panelListe.add(panel_2_1);
		panel_2_1.setLayout(null);

	}

	public void updatetadresse() {
		if (textId.getText().equalsIgnoreCase("") || textId.getText().equalsIgnoreCase(null)) {
			textId.setText("0");
		}
		System.out.println("ID: " + textId.getText());
		lblTotalAdresse.setText("Le client " + textNom.getText() + " dispose d'un total de "
				+ adresseD.totalA(Integer.parseInt(textId.getText())) + " adresse(s)");
		if (adresseD.totalA(Integer.parseInt(textId.getText())) > 0) {
			btnAdresseEffacer.setEnabled(true);
			btnAdresseMod.setEnabled(true);
		} else {
			btnAdresseEffacer.setEnabled(false);
			btnAdresseMod.setEnabled(false);
		}
	}
}