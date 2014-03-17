import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Desktop;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class Help extends JFrame {

	private JPanel contentPane;
	private JLabel lblConteudo;
	private JButton btnWiki;
	private ResourceBundle messages;

	/**
	 * Create the frame.
	 */
	public Help(ResourceBundle i18n) {
		messages = i18n;
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Help.class.getResource("/assets/help.png")));
		setAlwaysOnTop(true);
		setTitle(messages.getString("help_title"));
		setBounds(100, 100, 560, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		String conteudo = messages.getString("help_content01");
		conteudo += messages.getString("help_content02");
		conteudo += messages.getString("help_content03");
		conteudo += messages.getString("help_content04");
		conteudo += messages.getString("help_content05");
		conteudo += messages.getString("help_content06");
		conteudo += messages.getString("help_content07");
		conteudo += messages.getString("help_content08");
		conteudo += messages.getString("help_content09");
		conteudo += messages.getString("help_content10");
		conteudo += messages.getString("help_content11");
		conteudo += messages.getString("help_content12");
		conteudo += messages.getString("help_content13");

		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(119dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("254px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("420px:grow"),
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblConteudo = new JLabel(conteudo);
		contentPane.add(lblConteudo, "2, 2, 3, 1, fill, fill");
		
		btnWiki = new JButton(messages.getString("help_button_wiki"));
		btnWiki.setIcon(new ImageIcon(Help.class.getResource("/assets/wiki.png")));
		btnWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop d = Desktop.getDesktop();
					d.browse(new URI("http://sourceforge.net/p/prestashoptools/wiki/Home/"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnWiki.setPreferredSize(new Dimension(150, 25));
		contentPane.add(btnWiki, "2, 3, left, fill");
		
		JButton btnForum = new JButton(messages.getString("help_button_forum"));
		btnForum.setIcon(new ImageIcon(Help.class.getResource("/assets/support.png")));
		btnForum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop d = Desktop.getDesktop();
					d.browse(new URI("http://sourceforge.net/p/prestashoptools/discussion/"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnForum.setPreferredSize(new Dimension(150, 25));
		contentPane.add(btnForum, "4, 3, right, fill");
	}

}
