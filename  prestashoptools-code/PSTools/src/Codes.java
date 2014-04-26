import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.apache.commons.lang3.ArrayUtils;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Codes extends JFrame {

	private Codes codes;
	private JPanel contentPane;
	private JTextField textFieldFolder;
	private JTextField textFieldReference;
	private JLabel lblFirst;
	private JTextField textFieldFirst;
	private JButton btnGenerate;
	private JFileChooser chooserFolder;
	public File folder;
	private ResourceBundle messages;

	/**
	 * Create the frame.
	 */
	public Codes(ResourceBundle i18n, final String path) {
		codes = this;
		messages = i18n;
		
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle(messages.getString("code_title"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Codes.class.getResource("/assets/code.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 896, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(43dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(123dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(88dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),}));
		
		JLabel lblFolder = new JLabel(messages.getString("code_label_folder"));
		lblFolder.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(lblFolder, "2, 2, right, fill");
		
		textFieldFolder = new JTextField(path);
		textFieldFolder.setToolTipText(messages.getString("code_tooltip_folder"));
		textFieldFolder.setEditable(false);
		textFieldFolder.setFont(new Font("Dialog", Font.PLAIN, 14));
		contentPane.add(textFieldFolder, "4, 2, 3, 1, fill, fill");
		textFieldFolder.setColumns(10);
		
		folder = new File(path);
		JButton btnFolder = new JButton(messages.getString("code_button_folder"));
		btnFolder.setIcon(new ImageIcon(Codes.class.getResource("/assets/select_folder.png")));
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooserFolder = new JFileChooser(); 
			    chooserFolder.setCurrentDirectory(new File(path));
			    chooserFolder.setDialogTitle(messages.getString("choose_path"));
			    chooserFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooserFolder.setAcceptAllFileFilterUsed(false);
			    if (chooserFolder.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
			    	folder = chooserFolder.getSelectedFile();
			    	textFieldFolder.setText(folder.getAbsolutePath());
			    }
			}
		});
		btnFolder.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(btnFolder, "8, 2, default, fill");
		
		JLabel lblReference = new JLabel(messages.getString("code_label_reference"));
		lblReference.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(lblReference, "2, 4, right, fill");
		
		textFieldReference = new JTextField();
		textFieldReference.setText("REF###");
		textFieldReference.setToolTipText(messages.getString("code_tooltip_reference"));
		textFieldReference.setFont(new Font("Dialog", Font.PLAIN, 14));
		contentPane.add(textFieldReference, "4, 4, fill, fill");
		textFieldReference.setColumns(10);
		
		lblFirst = new JLabel(messages.getString("code_label_first"));
		lblFirst.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(lblFirst, "2, 6, right, fill");
		
		textFieldFirst = new JTextField();
		textFieldFirst.setText("1");
		textFieldFirst.setToolTipText(messages.getString("code_tooltip_first"));
		textFieldFirst.setFont(new Font("Dialog", Font.PLAIN, 14));
		contentPane.add(textFieldFirst, "4, 6, left, fill");
		textFieldFirst.setColumns(10);
		
		btnGenerate = new JButton(messages.getString("code_button_generate"));
		btnGenerate.setIcon(new ImageIcon(Codes.class.getResource("/assets/code.png")));
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ArrayList<String> extensions = new ArrayList<String>(Arrays.asList(".jpg", ".jpeg", ".gif", ".png"));
		        File[] files = folder.listFiles();
		        String path, file, extension, newName, reference = textFieldReference.getText();
		        int padding, first = Integer.parseInt(textFieldFirst.getText()), counter;
		        
		        for (int i = files.length-1; i >= 0; i--) {
		        	file = files[i].getAbsolutePath().replace(folder.getAbsolutePath()+"/", "");
		        	
		        	if(file.indexOf(".") != -1) {
	            		extension = file.substring(file.lastIndexOf("."), file.length());
	            		if(!extensions.contains(extension.toLowerCase())) {
	            			files = ArrayUtils.remove(files, i);
	            		}
		        	}
		        }
		        
		        path = folder.getAbsolutePath();
		        padding = reference.length() - reference.replace("#", "").length();
		        reference = reference.replace("#", "");

		        for (int i = 0; i < files.length; i++) {
		            if (files[i].isFile()) {
		            	counter = first+i;
		            	file = files[i].getAbsolutePath().replace(folder.getAbsolutePath()+"/", "");
		            	if(file.indexOf(".") != -1) {
		            		extension = file.substring(file.lastIndexOf("."), file.length());
			            	newName = path+"/"+reference+String.format("%0"+padding+"d", counter)+extension;
			                files[i].renameTo(new File(newName));
			            	//System.out.println(newName);
		            	}
		            }
		        }
		        setVisible(false);
		        JOptionPane.showMessageDialog(codes, messages.getString("code_message_generate"), messages.getString("code_title_generate"), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnGenerate.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(btnGenerate, "8, 8, default, fill");
	}

}
