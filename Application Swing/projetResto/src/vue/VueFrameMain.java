package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;

import model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VueFrameMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueFrameMain frame = new VueFrameMain();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VueFrameMain() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1180, 779);
		JDesktopPane desktopPane = new JDesktopPane();
		/*
		 * creation menu
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		/*
		 * Gestion menu carte
		 */
		ImageIcon imageIcon = new ImageIcon(VueFrameMain.class.getResource("/ressources/carte.png")); // load the image
																										// to a
																										// imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back

		JMenu mnCarte = new JMenu("Carte");
		mnCarte.setIcon(new ImageIcon(newimg));
		menuBar.add(mnCarte);
		repaint();
		/*
		 * Gestion sous-menu produit
		 */
		JMenuItem mntmProduit = new JMenuItem("Produits");
		mntmProduit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueProduit());
				desktopPane.repaint();
				desktopPane.revalidate();
			}
		});
		mntmProduit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		mnCarte.add(mntmProduit);
		/*
		 * Gestion sous-menu Catégorie
		 */
		JMenuItem mntmCategories = new JMenuItem("Catégories");
		mntmCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueCat_produit());
				desktopPane.repaint();
				desktopPane.revalidate();
			}
		});
		mntmCategories.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnCarte.add(mntmCategories);
		/*
		 * Gestion menu commandes
		 */
		imageIcon = new ImageIcon(VueFrameMain.class.getResource("/ressources/commandes.png")); // load the image to a
																								// imageIcon
		image = imageIcon.getImage(); // transform it
		newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_REPLICATE); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		JMenu mnCommandes = new JMenu("Commandes");
		mnCommandes.setIcon(imageIcon);
		menuBar.add(mnCommandes);
		/*
		 * Gestion sous-menu clients
		 */
		JMenuItem mntmClients = new JMenuItem("Clients");
		mntmClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueClient(1));
				desktopPane.repaint();
				desktopPane.revalidate();
			}
		});
		mntmClients.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnCommandes.add(mntmClients);
		/*
		 * Gestion sous-menu commandes
		 */
		JMenuItem mntmCreerCom = new JMenuItem("Créer Commandes");
		mntmCreerCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueCommande(1));
				desktopPane.repaint();
				desktopPane.revalidate();

			}
		});
		mntmCreerCom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		mnCommandes.add(mntmCreerCom);

		JMenuItem mntmNewMenuItem = new JMenuItem("Commandes en cours");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueLivraison());
				desktopPane.repaint();
				desktopPane.revalidate();
			}
		});
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		mnCommandes.add(mntmNewMenuItem);
		/*
		 * Gestion menu Accès
		 */
		JMenu mnAcces = new JMenu("Accès");
		imageIcon = new ImageIcon(VueFrameMain.class.getResource("/ressources/acces.png")); // load the image to a
																							// imageIcon
		image = imageIcon.getImage(); // transform it
		newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		mnAcces.setIcon(imageIcon);
		menuBar.add(mnAcces);
		/*
		 * Gestion sous-menu users
		 */
		JMenuItem mntmUser = new JMenuItem("Utilisateurs");
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueUser(1));
				desktopPane.repaint();
				desktopPane.revalidate();
			}
		});
		mntmUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		mnAcces.add(mntmUser);
		/*
		 * Gestion sous-menu roles
		 */
		JMenuItem mntmRole = new JMenuItem("Rôles");
		mntmRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new VueRole());
				desktopPane.repaint();
				desktopPane.revalidate();

			}
		});

		JMenuItem mntmChangePassword = new JMenuItem("Changer password");
		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				desktopPane.removeAll();
				desktopPane.add(new VueUser(2));
				desktopPane.repaint();
				desktopPane.revalidate();

			}
		});
		mntmChangePassword.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
		mnAcces.add(mntmChangePassword);
		mntmRole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		mnAcces.add(mntmRole);
		/*
		 * Gestion menu sortir
		 */
		JMenu mnExit = new JMenu("Sortir");
		imageIcon = new ImageIcon(VueFrameMain.class.getResource("/ressources/sortir.png")); // load the image to a
																								// imageIcon
		image = imageIcon.getImage(); // transform it
		newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		mnExit.setIcon(imageIcon);
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		menuBar.add(mnExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		/*
		 * Titre dynamique restaurant
		 */
		JLabel lblNomResto = new JLabel("Nom du resto");
		lblNomResto.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomResto.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNomResto.setBounds(350, 10, 426, 57);
		contentPane.add(lblNomResto);
		/*
		 * Nom dynamique de l'utilisateur
		 */
		JLabel lblNomUtilisateur = new JLabel("Bienvenue Nom User");
		lblNomUtilisateur.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNomUtilisateur.setBounds(10, 87, 269, 28);
		contentPane.add(lblNomUtilisateur);
		/*
		 * creation panneau qui recois toutes les autres vues
		 */
		desktopPane.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Restaurant Chipontha\u00EF", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		desktopPane.setBackground(SystemColor.control);
		desktopPane.setBounds(20, 125, 1136, 565);
		contentPane.add(desktopPane);
		/*
		 * label image accueil
		 */

		// ImageIcon imageIcon2 = new
		// ImageIcon(VueFrameMain.class.getResource("/ressources/wallpaper.PNG")); //
		// load the image to a imageIcon
		// Image image2 = imageIcon2.getImage(); // transform it
		// Image newimg2 = image2.getScaledInstance(1152 , 768,
		// java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		// imageIcon2 = new ImageIcon(newimg2); // transform it back

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VueFrameMain.class.getResource("/ressources/wallpaper.png")));
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 19, 1122, 535);
		desktopPane.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(64, 96, 183, 126);
		desktopPane.add(lblNewLabel);

		JLabel label = new JLabel("New label");
		label.setBounds(39, 322, 283, 220);
		desktopPane.add(label);

		if (User.userLogin != null) {
			lblNomResto.setText(User.userLogin.nomEtablissement);
			lblNomUtilisateur.setText("Bienvenue " + User.userLogin);
		}
		// VISIBILITE DE MENU PAR ROLES
		if(User.userLogin.getId_role().getId()==1) {
			mntmProduit.setEnabled(true);
			mntmCategories.setEnabled(true);
		
			mntmClients.setEnabled(true);
			mntmCreerCom.setEnabled(true);
			
			mntmUser.setEnabled(true);
			mntmChangePassword.setEnabled(true);
			mntmRole.setEnabled(true);
		}else {
			mntmProduit.setEnabled(false);
			mntmCategories.setEnabled(false);
		
			mntmClients.setEnabled(true);
			mntmCreerCom.setEnabled(true);
			
			mntmUser.setEnabled(false);
			mntmChangePassword.setEnabled(true);
			mntmRole.setEnabled(false);
		}

	}
}
