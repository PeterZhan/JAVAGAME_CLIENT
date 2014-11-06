package ibm.game.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DlgMain extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();

	String gameWay = "NEW";

	/**
	 * Launch the application.
	 */

	/*
	 * public static void main(String[] args) { try { DlgMain dialog = new
	 * DlgMain(null, true);
	 * dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * Create the dialog.
	 */

	/*
	 * public DlgMain() { super();
	 * 
	 * 
	 * }
	 */
	public DlgMain(JFrame owner, boolean modal) {
		super(owner, modal);
		setBounds(100, 100, 450, 185);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		ButtonGroup group = new ButtonGroup();

		JRadioButton rdbtnStartANew = new JRadioButton("start a new game");
		rdbtnStartANew.setActionCommand("NEW");
		rdbtnStartANew.addActionListener(this);
		rdbtnStartANew.setSelected(true);
		contentPanel.add(rdbtnStartANew);
		group.add(rdbtnStartANew);

		if (AGameSession.namelist.length > 0) {
			JRadioButton rdbtnJoinAnExisting = new JRadioButton(
					"join an existing game");
			rdbtnJoinAnExisting.setActionCommand("JOIN");
			rdbtnJoinAnExisting.addActionListener(this);
			contentPanel.add(rdbtnJoinAnExisting);
			group.add(rdbtnJoinAnExisting);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);
				dispose();

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

	public void actionPerformed(ActionEvent e) {
		gameWay = e.getActionCommand();
	}

}
