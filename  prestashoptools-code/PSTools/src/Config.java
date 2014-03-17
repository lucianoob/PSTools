import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.awt.Font;


@SuppressWarnings("serial")
public class Config extends JFrame {
	
	private JFrame config;
	public String cfgAtivo = "1", cfgOferta = "0", cfgDisponivel = "1", cfgExibePreco = "1", cfgDeleteImagens = "1", cfgSomenteOnline = "1";
	private JCheckBox chckbxAtivo;
	private JCheckBox chckbxEmOferta;
	private JCheckBox chckbxDisponvel;
	private JCheckBox chckbxExibePreo;
	private JCheckBox chckbxDeleteImagens;
	private JCheckBox chckbxSomenteOnline;
	public Boolean isSave;
	private JLabel lblAtivo;
	private JLabel lblEmOferta;
	private JLabel lblDisponvel;
	private JLabel lblExibePreo;
	private JLabel lblDeleteImagens;
	private JLabel lblSomenteOnline;
	private JButton btnCancelar;
	private JButton btnSalvar;
	
	private ResourceBundle messages;
	private ImageIcon cancel;
	private ImageIcon save;

	/**
	 * Create the frame.
	 */
	public Config(ResourceBundle i18n) {
		
		config = this;		
		messages = i18n;
		
		save = new ImageIcon(Main.class.getResource("/assets/save.png"));
		cancel = new ImageIcon(Main.class.getResource("/assets/cancel.png"));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Config.class.getResource("/assets/config.png")));
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 377, 311);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelForm = new JPanel();
		scrollPane.setViewportView(panelForm);
		panelForm.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(69dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(19dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(35dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:34px:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblAtivo = new JLabel();
		lblAtivo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAtivo.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblAtivo, "2, 2, 3, 1");
		
		chckbxAtivo = new JCheckBox("");
		panelForm.add(chckbxAtivo, "6, 2");
		
		lblEmOferta = new JLabel();
		lblEmOferta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEmOferta.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblEmOferta, "2, 4, 3, 1");
		
		chckbxEmOferta = new JCheckBox("");
		panelForm.add(chckbxEmOferta, "6, 4");
		
		lblDisponvel = new JLabel();
		lblDisponvel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDisponvel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblDisponvel, "2, 6, 3, 1");
		
		chckbxDisponvel = new JCheckBox("");
		panelForm.add(chckbxDisponvel, "6, 6");
		
		lblExibePreo = new JLabel();
		lblExibePreo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblExibePreo.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblExibePreo, "2, 8, 3, 1");
		
		chckbxExibePreo = new JCheckBox("");
		panelForm.add(chckbxExibePreo, "6, 8");
		
		lblDeleteImagens = new JLabel();
		lblDeleteImagens.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDeleteImagens.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblDeleteImagens, "2, 10, 3, 1");
		
		chckbxDeleteImagens = new JCheckBox("");
		panelForm.add(chckbxDeleteImagens, "6, 10");
		
		lblSomenteOnline = new JLabel();
		lblSomenteOnline.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSomenteOnline.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForm.add(lblSomenteOnline, "2, 12, 3, 1");
		
		chckbxSomenteOnline = new JCheckBox("");
		panelForm.add(chckbxSomenteOnline, "6, 12");
		
		btnCancelar = new JButton();
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar.setMinimumSize(new Dimension(50, 34));
		btnCancelar.setMaximumSize(new Dimension(9999, 9999));
		btnCancelar.setPreferredSize(new Dimension(140, 34));
		btnCancelar.setIcon(cancel);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.setVisible(false);
			}
		});
		panelForm.add(btnCancelar, "2, 18, left, bottom");
		
		btnSalvar = new JButton();
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSalvar.setMaximumSize(new Dimension(999, 999));
		btnSalvar.setIcon(save);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isSave = true;
				setConfig();
				JOptionPane.showMessageDialog(config, messages.getString("config_message_save"), messages.getString("config_button_save"), JOptionPane.INFORMATION_MESSAGE);
				config.setVisible(false);
			}
		});
		btnSalvar.setPreferredSize(new Dimension(140, 34));
		btnSalvar.setMinimumSize(new Dimension(50, 34));
		panelForm.add(btnSalvar, "4, 18, 3, 1, right, bottom");
		
	}
	public void initConfig(ResourceBundle i18n) {
		messages = i18n;
		
		isSave = false;
		chckbxAtivo.setSelected(cfgAtivo.equals("1"));
		chckbxEmOferta.setSelected(cfgOferta.equals("1"));
		chckbxDisponvel.setSelected(cfgDisponivel.equals("1"));
		chckbxExibePreo.setSelected(cfgExibePreco.equals("1"));
		chckbxDeleteImagens.setSelected(cfgDeleteImagens.equals("1"));
		chckbxSomenteOnline.setSelected(cfgSomenteOnline.equals("1"));
		
		config.setTitle(messages.getString("config_title"));
		lblAtivo.setText(messages.getString("config_label_active"));
		lblEmOferta.setText(messages.getString("config_label_onsale"));
		lblDisponvel.setText(messages.getString("config_label_available"));
		lblExibePreo.setText(messages.getString("config_label_show_price"));
		lblDeleteImagens.setText(messages.getString("config_label_delete_images"));
		lblSomenteOnline.setText(messages.getString("config_label_only_online"));
		
		btnSalvar.setText(messages.getString("config_button_save"));
		btnCancelar.setText(messages.getString("config_button_cancel"));
	}
	public void setConfig() {
		cfgAtivo = chckbxAtivo.isSelected() ? "1" : "0";
		cfgOferta = chckbxEmOferta.isSelected() ? "1" : "0";
		cfgDisponivel = chckbxDisponvel.isSelected() ? "1" : "0";
		cfgExibePreco = chckbxExibePreo.isSelected() ? "1" : "0";
		cfgDeleteImagens = chckbxDeleteImagens.isSelected() ? "1" : "0";
		cfgSomenteOnline = chckbxSomenteOnline.isSelected() ? "1" : "0";
	}
}
