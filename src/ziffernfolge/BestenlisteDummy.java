package ziffernfolge;

import java.awt.Button;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class BestenlisteDummy extends JPanel {
	private JTextField textField;
	private Steuerung steuerung;
	private JPanel bestenlistePanel;
	private JPanel nameEingabePanel;
	private String name;
	private TextArea textArea;

	public BestenlisteDummy(Steuerung steuerung) {
		this.steuerung = steuerung;
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		bestenlistePanel = new JPanel();
		bestenlistePanel.setVisible(false);
		bestenlistePanel.setBounds(0, 0, 500, 500);
		bestenlistePanel.setPreferredSize(new Dimension(500, 500));
		bestenlistePanel.setBackground(Color.LIGHT_GRAY);
		this.add(bestenlistePanel);
		bestenlistePanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Bestenliste");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(80, 47, 225, 78);
		bestenlistePanel.add(lblNewLabel_1);
		
		textArea = new TextArea();
		textArea.setBounds(80, 119, 138, 91);
		bestenlistePanel.add(textArea);

		Button neuesSpielButton = new Button("Neues Spiel");
		neuesSpielButton.setBounds(219, 311, 100, 21);
		neuesSpielButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bestenlistePanel.setVisible(false);
				nameEingabePanel.setVisible(true);
				steuerung.neues_Spiel();

			}
		});
		bestenlistePanel.add(neuesSpielButton);
		nameEingabePanel = new JPanel();
		nameEingabePanel.setBounds(0, 0, 500, 500);
		nameEingabePanel.setLayout(null);
		nameEingabePanel.setPreferredSize(new Dimension(500, 500));
		nameEingabePanel.setBackground(Color.WHITE);
		this.add(nameEingabePanel);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(107, 239, 96, 19);
		nameEingabePanel.add(textField);

		JLabel lblNewLabel = new JLabel("Name eingeben");
		lblNewLabel.setBounds(30, 242, 72, 13);
		nameEingabePanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Bestaetigen");
		btnNewButton.setBounds(219, 238, 85, 21);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				name = textField.getText();
				steuerung.name_eingegeben();

			}
		});
		nameEingabePanel.add(btnNewButton);

	}

	public void zeige_Liste_an() {
		this.nameEingabePanel.setVisible(false);
		this.bestenlistePanel.setVisible(true);
		textArea.setText(name);

	}

	public void sichtbar(boolean isSichtbar) {
		this.setVisible(isSichtbar);
	}

}
