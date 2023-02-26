package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import model.Commande;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VuePaiement extends JPanel {

	/**
	 * Create the panel.
	 */
	public VuePaiement() {
		setBounds(0, 0, 260, 200);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 260, 200);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Type de paiement");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(49, -2, 159, 27);
		panel.add(lblNewLabel);

		JLabel lblCarte = new JLabel("New label");
		lblCarte.setBounds(10, 24, 120, 78);
		lblCarte.setIcon(new ImageIcon(VueFrameMain.class.getResource("/ressources/CB.jfif")));
		panel.add(lblCarte);

		JLabel lblPiece = new JLabel("New label");
		lblPiece.setBounds(10, 112, 120, 78);
		lblPiece.setIcon(new ImageIcon(VueFrameMain.class.getResource("/ressources/piece.png")));
		panel.add(lblPiece);

		JRadioButton rdbtnCarte = new JRadioButton("CB/carte restaurant");
		rdbtnCarte.setFont(new Font("Tahoma", Font.PLAIN, 9));
		rdbtnCarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Commande.ready = 1;
				VueCommande.newCommande.setType_paiement("Carte");
			}
		});
		rdbtnCarte.setBounds(136, 53, 136, 21);
		panel.add(rdbtnCarte);

		JRadioButton rdbtnEspece = new JRadioButton("Espèce");
		rdbtnEspece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Commande.ready = 1;
				VueCommande.newCommande.setType_paiement("Espèce");
			}
		});
		rdbtnEspece.setBounds(136, 142, 103, 21);
		panel.add(rdbtnEspece);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCarte);
		group.add(rdbtnEspece);

	}
}
