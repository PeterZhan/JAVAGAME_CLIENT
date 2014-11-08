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
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Panel;
import javax.swing.SwingConstants;

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

	/*public DlgMain() {
		super();
		
		

	}*/

	public DlgMain(JFrame owner, boolean modal) {
		super(owner, modal);
		setTitle("Laser-War Game");
		setBounds(100, 100, 613, 239);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		ButtonGroup group = new ButtonGroup();
		
				JRadioButton rdbtnStartANew = new JRadioButton("start a new game");
				contentPanel.add(rdbtnStartANew);
				rdbtnStartANew.setActionCommand("NEW");
				rdbtnStartANew.addActionListener(this);
				rdbtnStartANew.setSelected(true);
				group.add(rdbtnStartANew);
				
				if (AGameSession.namelist.length > 0) {
					JRadioButton rdbtnJoinAnExisting = new JRadioButton(
							"join an existing game");
					rdbtnJoinAnExisting.setActionCommand("JOIN");
					rdbtnJoinAnExisting.addActionListener(this);
					contentPanel.add(rdbtnJoinAnExisting);
					group.add(rdbtnJoinAnExisting);
				}		
				
				
				
		
		JPanel panel_2 = new JPanel();
		contentPanel.add(panel_2);
		
		JLabel lblPictureDownloadedFrom = new JLabel("The background picture from  http://pixabay.com/en/hubble-space-telescope-atmosphere-89464/");
		panel_2.add(lblPictureDownloadedFrom);
		
		JLabel lblHttppixabaycomenspaceshipspacetravel = new JLabel("The spaceship picture from http://pixabay.com/en/space-ship-space-travel-148536/");
		contentPanel.add(lblHttppixabaycomenspaceshipspacetravel);
		
		JLabel lblAuthorHongliangZhang = new JLabel("Sound files from http://soundcli.ps/browse/laser");
		contentPanel.add(lblAuthorHongliangZhang);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPanel.add(panel);
		
		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1);
		
		JLabel label = new JLabel("Author: Hongliang Zhang ");
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("at Lambton College in Fall 2014");
		panel_1.add(label_1);
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		
		
		
		

		

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
