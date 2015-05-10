package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JProgressBar progressBar = new JProgressBar();
		sl_contentPane.putConstraint(SpringLayout.WEST, progressBar, 121, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, progressBar, 267, SpringLayout.WEST, contentPane);
		contentPane.add(progressBar);
		
		JPanel configPanel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, configPanel, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, configPanel, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, configPanel, -6, SpringLayout.NORTH, progressBar);
		sl_contentPane.putConstraint(SpringLayout.EAST, configPanel, 334, SpringLayout.WEST, contentPane);
		configPanel.setBorder(new TitledBorder(null, "Konfiguracja po\u0142\u0105czenia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(configPanel);
		configPanel.setLayout(new MigLayout("", "[114px,grow]", "[14px][][][][][]"));
		
		JLabel lblAdresSerwera = new JLabel("Adres serwera");
		configPanel.add(lblAdresSerwera, "cell 0 0");
		
		textField = new JTextField();
		configPanel.add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		
		JLabel lblKodowanie = new JLabel("Kodowanie");
		configPanel.add(lblKodowanie, "cell 0 4");
		
		JRadioButton rdbtnBase = new JRadioButton("base64");
		configPanel.add(rdbtnBase, "cell 0 5");
	}
}
