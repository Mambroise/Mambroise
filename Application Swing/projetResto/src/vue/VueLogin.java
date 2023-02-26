package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.SoftBevelBorder;

import controller.UserDao;
import metier.UserMetier;
import model.User;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import java.awt.Label;
import java.awt.Panel;

public class VueLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textEmailLogin;
	private JPasswordField textPasswordLogin;
	private User userLogin;
	private UserMetier userM = new UserMetier();
	private UserDao userD = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueLogin frame = new VueLogin();
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
	public VueLogin() {

		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		User user = new User();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new CompoundBorder());
		panel.setBounds(10, 11, 784, 306);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblEmailLogin = new JLabel("Email: (*)");
		lblEmailLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmailLogin.setBounds(73, 65, 114, 46);
		panel.add(lblEmailLogin);

		textEmailLogin = new JTextField();
		textEmailLogin.setBounds(179, 78, 239, 25);
		panel.add(textEmailLogin);
		textEmailLogin.setColumns(10);

		JLabel lblPassword = new JLabel("Password: (*)");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(43, 122, 159, 46);
		panel.add(lblPassword);

		textPasswordLogin = new JPasswordField();
		textPasswordLogin.setBounds(179, 132, 239, 25);
		panel.add(textPasswordLogin);

		JButton btnLoginEntrer = new JButton("Entrer");
		btnLoginEntrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textEmailLogin.getText().equals("") || String.valueOf(textPasswordLogin.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "Merci de remplir les champs obligatoire(*) ", "Login",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (!userM.verifMailRegix(textEmailLogin.getText())) {
					JOptionPane.showMessageDialog(null, "Vous devez saisir un email valide Ex. mail@mail.com/fr.",
							"Login", JOptionPane.WARNING_MESSAGE);
					textEmailLogin.requestFocus();
					return;
				}

				if (userD.loginPass(textEmailLogin.getText(), String.valueOf(textPasswordLogin.getPassword()))) {
					System.out.println("Termina");
					JOptionPane.showMessageDialog(null, "Bienvenue " + User.userLogin, "Login",
							JOptionPane.INFORMATION_MESSAGE);

					dispose();
					VueFrameMain frm = new VueFrameMain();
					frm.toFront();
					frm.setVisible(true);
					frm.setLocationRelativeTo(null);
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez saisir un email et un password correct ", "Login",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			}
		});
		btnLoginEntrer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoginEntrer.setBounds(99, 195, 139, 37);
		panel.add(btnLoginEntrer);

		JButton btnLoginSortir = new JButton("Sortir");
		btnLoginSortir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnLoginSortir.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoginSortir.setBounds(261, 195, 139, 37);
		panel.add(btnLoginSortir);

		ImageIcon imageIcon = new ImageIcon(VueFrameMain.class.getResource("/ressources/logo.jpg")); // load the image
																										// to a
																										// imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back

		Panel panel_1 = new Panel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(450, 11, 3, 285);
		panel.add(panel_1);
		panel_1.setLayout(null);

		Panel panel_2 = new Panel();
		panel_2.setBackground(new Color(0, 0, 0));
		panel_2.setBounds(10, 260, 551, 2);
		panel.add(panel_2);
		panel_2.setLayout(null);

		Panel panel_2_1 = new Panel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(Color.BLACK);
		panel_2_1.setBounds(663, 260, 100, 2);
		panel.add(panel_2_1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(imageIcon);
		lblNewLabel.setBounds(462, 11, 294, 263);
		panel.add(lblNewLabel);
	}
}
