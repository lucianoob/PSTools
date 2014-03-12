import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;

import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ibm.icu.impl.StringUCharacterIterator;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.SwingConstants;

import java.awt.Toolkit;

import javax.swing.UIManager;

import java.awt.event.KeyAdapter;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Cursor;
import java.awt.Rectangle;

import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;


@SuppressWarnings("unused")
public class Main {

	private static Main window;
	public JFrame frmPstoolsV;
	private static ResourceBundle messages;
	private static Locale locale;
	private static String appTitle;
	private static String appVersion = "v1.78";
	private static String appDescription;
	private static String appDeveloper;
	private static String diretorio;
	private static JTextField textFieldNome;
	private static JTextField textFieldUrl;
	private static JTextField textFieldCaminho;
	private static JTable tableProdutos;
	private static DefaultTableModel model;
	private File pathFotos;
	private static JScrollPane panelFoto;
	private static JButton btnRemover, btnCategoria, btnPreco, btnPeso, btnQuantidade;
	public static JButton btnSalvar;
	private int rowsSelected[];
	private static ImageIcon save_ok, save_alert, save, cancel, select_folder, delete, export, category, weight, price, quantity;
	private static JDialog dialogImage;
	private JSeparator separator;
	private static JButton btnExportar;
	private static JButton btnSelecionarPasta;
	private static JLabel lblCaminho;
	private static JLabel lblNome;
	private static JLabel lblUrl;
	private static JMenuItem mntmConfiguraes;
	private static JMenuItem mntmSair;
	private static JMenu mnAjuda;
	private static JMenuItem mntmSobre;
	private static Config config;
	private static Help ajuda;
	private static JMenu mnArquivo;
	private static JMenu mnFerramentas;
	private static JMenu mnIdioma;
	private static JCheckBoxMenuItem chckbxmntmPortugus;
	private static JCheckBoxMenuItem chckbxmntmIngls;
	private static JFileChooser chooserPasta;
	private static JMenuItem mntmSuporte;
	private static JMenuItem mntmUpdate;
	private static JMenuItem mntmContedo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					diretorio = System.getProperty("user.dir")+"/";
					
					save_ok = new ImageIcon(Main.class.getResource("/assets/save_ok.png"));
					save_alert = new ImageIcon(Main.class.getResource("/assets/save_alert.png"));
					select_folder = new ImageIcon(Main.class.getResource("/assets/select_folder.png"));
					delete = new ImageIcon(Main.class.getResource("/assets/delete.png"));
					export = new ImageIcon(Main.class.getResource("/assets/export.png"));
					category = new ImageIcon(Main.class.getResource("/assets/category.png"));
					weight = new ImageIcon(Main.class.getResource("/assets/weight.png"));
					price = new ImageIcon(Main.class.getResource("/assets/price.png"));
					quantity = new ImageIcon(Main.class.getResource("/assets/quantity.png"));
					
					window = new Main();
					window.frmPstoolsV.setVisible(true);
					
					locale = Locale.getDefault();
					setLocale(locale.getLanguage());
					if(locale.getLanguage().equals("pt"))
						chckbxmntmPortugus.setSelected(true);
					else if(locale.getLanguage().equals("en"))
						chckbxmntmIngls.setSelected(true);
					
					DocumentBuilderFactory dbFactory;
					DocumentBuilder dBuilder;
					Document doc;
					
					File xmlConfig = new File(diretorio+"config.xml");
					if(xmlConfig.exists()) {
						dbFactory = DocumentBuilderFactory.newInstance();
						dBuilder = dbFactory.newDocumentBuilder();
						doc = dBuilder.parse(xmlConfig);
					 
						doc.getDocumentElement().normalize();
					 
						//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					 
						//System.out.println("Name : " + doc.getElementsByTagName("name").item(0).getTextContent());
						//System.out.println("Url : " + doc.getElementsByTagName("url").item(0).getTextContent());
						//System.out.println("Path : " + doc.getElementsByTagName("path").item(0).getTextContent());
						
						textFieldNome.setText(doc.getElementsByTagName("name").item(0).getTextContent());
						
						textFieldUrl.setText(doc.getElementsByTagName("url").item(0).getTextContent());
						
						textFieldCaminho.setText(doc.getElementsByTagName("path").item(0).getTextContent());
						
						config.cfgAtivo = doc.getElementsByTagName("active").item(0).getTextContent();
						config.cfgOferta = doc.getElementsByTagName("on_sale").item(0).getTextContent();
						config.cfgDisponivel = doc.getElementsByTagName("available").item(0).getTextContent();
						config.cfgExibePreco = doc.getElementsByTagName("show_price").item(0).getTextContent();
						config.cfgDeleteImagens = doc.getElementsByTagName("delete_images").item(0).getTextContent();
						config.cfgSomenteOnline = doc.getElementsByTagName("only_online").item(0).getTextContent();
						
					}
					
					File xmlData = new File(diretorio+"data.xml");
					if(xmlData.exists()) {
						dbFactory = DocumentBuilderFactory.newInstance();
						dBuilder = dbFactory.newDocumentBuilder();
						doc = dBuilder.parse(xmlData);
					 
						doc.getDocumentElement().normalize();
						
						int rows = model.getRowCount(); 
				    	for(int i = rows - 1; i >=0; i--)
				    	   model.removeRow(i); 
						
						// "Refer\u00EAncia", "Nome", "Pre\u00E7o", "Peso", "Quantidade", "Descri\u00E7\u00E3o", "Fotos"
						 
						for (int i = 0; i < doc.getElementsByTagName("reference").getLength(); i++) {
							
							String reference = doc.getElementsByTagName("reference").item(i).getTextContent();
							String name = doc.getElementsByTagName("name").item(i).getTextContent();
							String category = doc.getElementsByTagName("category").item(i).getTextContent();
							String price = doc.getElementsByTagName("price").item(i).getTextContent();
							String weight = doc.getElementsByTagName("weight").item(i).getTextContent();
							String quantity = doc.getElementsByTagName("quantity").item(i).getTextContent();
							String description = doc.getElementsByTagName("description").item(i).getTextContent();
							String photos = doc.getElementsByTagName("photos").item(i).getTextContent();
							
							/*System.out.println("Reference : " + reference);
							System.out.println("Name : " + name);
							System.out.println("Price : " + price);
							System.out.println("Weight : " + weight);
							System.out.println("Quantity : " + quantity);
							System.out.println("Description : " + description);
							System.out.println("Photos : " + photos);*/
							
							model.addRow(new Object[]{reference, name, category, price, weight, quantity, description, photos});
						}
						model.addTableModelListener(new TableModelListener() {
							public void tableChanged (TableModelEvent e) {
								btnSalvar.setIcon(save_alert);
							}
						});
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	private void initialize() {
		
		frmPstoolsV = new JFrame();
		frmPstoolsV.setPreferredSize(new Dimension(1280, 768));
		frmPstoolsV.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/assets/pstools-icon256.png")));
		frmPstoolsV.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 14));
		frmPstoolsV.setFont(new Font("Verdana", Font.PLAIN, 16));
		frmPstoolsV.setTitle(appTitle+" "+appVersion+" - "+appDescription);
		frmPstoolsV.setSize(new Dimension(1280, 768));
		frmPstoolsV.setMinimumSize(new Dimension(1280, 768));
		frmPstoolsV.setBounds(100, 100, 450, 300);
		frmPstoolsV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmPstoolsV.setJMenuBar(menuBar);
		
		mnArquivo = new JMenu();
		mnArquivo.setFont(new Font("Verdana", Font.BOLD, 14));
		menuBar.add(mnArquivo);
		
		mntmSair = new JMenuItem();
		mntmSair.setFont(new Font("Verdana", Font.BOLD, 14));
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmPstoolsV.setVisible(false);
				System.exit(0);
			}
		});
		
		config = new Config(messages);
		config.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				frmPstoolsV.setEnabled(true);
				if(config.isSave)
					saveWarning();
			}
		});
		mntmSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnArquivo.add(mntmSair);
		
		mnFerramentas = new JMenu();
		menuBar.add(mnFerramentas);
		
		mnIdioma = new JMenu();
		mnFerramentas.add(mnIdioma);
		
		chckbxmntmPortugus = new JCheckBoxMenuItem();
		chckbxmntmPortugus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxmntmIngls.setSelected(!chckbxmntmPortugus.isSelected());
				setLocale("pt");
			}
		});
		mnIdioma.add(chckbxmntmPortugus);
		
		chckbxmntmIngls = new JCheckBoxMenuItem();
		chckbxmntmIngls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxmntmPortugus.setSelected(!chckbxmntmIngls.isSelected());
				setLocale("en");
			}
		});
		mnIdioma.add(chckbxmntmIngls);
		mntmConfiguraes = new JMenuItem();
		mnFerramentas.add(mntmConfiguraes);
		mntmConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = frmPstoolsV.getX()+((frmPstoolsV.getWidth()/2)-(config.getWidth()/2));
				int y = frmPstoolsV.getY()+((frmPstoolsV.getHeight()/2)-(config.getHeight()/2));
				
				frmPstoolsV.setEnabled(false);
				config.initConfig(messages);
				config.setLocation(x, y);
				config.setVisible(true);
			}
		});
		mntmConfiguraes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		
		mnAjuda = new JMenu();
		mnAjuda.setFont(new Font("Verdana", Font.BOLD, 14));
		menuBar.add(mnAjuda);
		
		mntmSobre = new JMenuItem();
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sobre = appTitle+" "+appVersion;
				sobre += "\n\n"+appDescription;
				sobre += "\n"+appDeveloper;
				JOptionPane.showMessageDialog(frmPstoolsV, sobre, messages.getString("menuitem_about"), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		mntmSuporte = new JMenuItem();
		mntmSuporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop d = Desktop.getDesktop();
					d.browse(new URI("https://sourceforge.net/p/prestashoptools/discussion/"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		mntmUpdate = new JMenuItem();
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					frmPstoolsV.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					String sfXML = "", inputLine;
					DocumentBuilderFactory dbFactory;
					DocumentBuilder dBuilder;
					Document doc;
					String[] sfStr;
					
					URL sfRSS = new URL("http://sourceforge.net/api/file/index/project-id/2122487/rss");
			        BufferedReader sfIn = new BufferedReader(new InputStreamReader(sfRSS.openStream()));
			        
			        while ((inputLine = sfIn.readLine()) != null)
			        	sfXML += inputLine+"\n";
			        sfIn.close();
			        //System.out.println(xmlSourceForge);
					
					InputSource sfIs = new InputSource();
					sfIs.setCharacterStream(new StringReader(sfXML));
				
					dbFactory = DocumentBuilderFactory.newInstance();
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(sfIs);			 
					doc.getDocumentElement().normalize();
					
					float appVersionFloat = Float.parseFloat(appVersion.replace("v", "")), sfVersionFloat;
					
					String versionUpdate = ""; 
				 
					for (int i = 0; i < doc.getElementsByTagName("item").getLength(); i++) {
						
						sfStr = doc.getElementsByTagName("item").item(i).getTextContent().split("\n");
						
						sfVersionFloat = Float.parseFloat(sfStr[1].replace("/PSTools_v", "").replace(".zip", ""));
						
						if(appVersionFloat < sfVersionFloat)
							versionUpdate = sfStr[1].replace("/", "").trim();
						
						System.out.println("appVersion : " + appVersionFloat + " sfVersion: " + sfVersionFloat);
					}
					
					frmPstoolsV.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					if(!versionUpdate.equals("")) {
						int confirm = JOptionPane.showConfirmDialog(frmPstoolsV, MessageFormat.format(messages.getString("confirm_update"), versionUpdate), messages.getString("confirm_update_title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(confirm == JOptionPane.YES_OPTION) {
							Desktop d = Desktop.getDesktop();
							d.browse(new URI("http://sourceforge.net/p/prestashoptools/"));
						}
					} else
						JOptionPane.showMessageDialog(frmPstoolsV, messages.getString("dialog_update"), messages.getString("confirm_update_title"), JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mntmContedo = new JMenuItem();
		mntmContedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mntmContedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajuda = new Help(messages);
				ajuda.setVisible(true);
			}
		});
		mnAjuda.add(mntmContedo);
		mntmUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		mnAjuda.add(mntmUpdate);
		mntmSuporte.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
		mnAjuda.add(mntmSuporte);
		mntmSobre.setFont(new Font("Verdana", Font.BOLD, 14));
		mntmSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.SHIFT_MASK));
		mnAjuda.add(mntmSobre);
		frmPstoolsV.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("78px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(96dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(82dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(130dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("300px:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				RowSpec.decode("10dlu"),}));
		
		lblNome = new JLabel();
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(lblNome, "2, 2, fill, center");
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Verdana", Font.PLAIN, 14));
		frmPstoolsV.getContentPane().add(textFieldNome, "4, 2, 9, 1, left, top");
		textFieldNome.setColumns(30);
		
		lblUrl = new JLabel();
		lblUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUrl.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(lblUrl, "2, 4, right, center");
		
		textFieldUrl = new JTextField();
		textFieldUrl.setFont(new Font("Verdana", Font.PLAIN, 14));
		frmPstoolsV.getContentPane().add(textFieldUrl, "4, 4, 9, 1, fill, top");
		textFieldUrl.setColumns(80);
		
		lblCaminho = new JLabel();
		lblCaminho.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCaminho.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(lblCaminho, "2, 6, right, default");
		
		textFieldCaminho = new JTextField();
		textFieldCaminho.setDisabledTextColor(Color.BLACK);
		textFieldCaminho.setEditable(false);
		textFieldCaminho.setFont(new Font("Verdana", Font.PLAIN, 14));
		frmPstoolsV.getContentPane().add(textFieldCaminho, "4, 6, 7, 1, fill, default");
		textFieldCaminho.setColumns(80);
		
		btnSelecionarPasta = new JButton();
		btnSelecionarPasta.setIcon(select_folder);
		btnSelecionarPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooserPasta = new JFileChooser(); 
			    chooserPasta.setCurrentDirectory(new java.io.File(textFieldCaminho.getText()));
			    chooserPasta.setDialogTitle(messages.getString("choose_path"));
			    chooserPasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooserPasta.setAcceptAllFileFilterUsed(false);
			    if (chooserPasta.showOpenDialog(frmPstoolsV) == JFileChooser.APPROVE_OPTION) {
			    	
			    	pathFotos = chooserPasta.getSelectedFile();
			    	textFieldCaminho.setText(pathFotos.getAbsolutePath());
			    	String[] categoria = pathFotos.getAbsolutePath().split("/");
			    	
			    	int rows = model.getRowCount(); 
			    	for(int i = rows - 1; i >=0; i--)
			    	   model.removeRow(i); 
			    	
			    	ArrayList<String> filenames = new ArrayList<String>(); 
			    	ArrayList<String> fotos = new ArrayList<String>(); 
			    	ArrayList<File> files = new ArrayList<File>(Arrays.asList(pathFotos.listFiles()));
			    	Collections.sort(files);
			    	for (File file : files) {
			    		if(file.getName().indexOf("-ORIGINAL") == -1 && file.getName().toUpperCase().indexOf(".JPG") != -1) {
				    		String[] filename = file.getName().toUpperCase().replace(".JPG", "").split("-");
				    		if(!filenames.contains((String) filename[0])) { 
				    			filenames.add(filename[0]);
				    			fotos.add(file.getName());
				    		} else {
			    				fotos.set(filenames.indexOf(filename[0]), fotos.get(filenames.indexOf(filename[0]))+","+file.getName());
				    		}
			    		}
			    	}
			    	for(int i=0; i<filenames.size(); i++)
			    		model.addRow(new Object[]{filenames.get(i), "", categoria[categoria.length-1], "", 0.2, 1, "", String.valueOf(fotos.get(i))});
			    	
			    	btnRemover.setEnabled(false);
					btnCategoria.setEnabled(false);
					btnPreco.setEnabled(false);
		    		btnPeso.setEnabled(false);
		    		btnQuantidade.setEnabled(false);
					panelFoto.setViewportView(null);
			    	
			    }
			}
		});
		
		btnSelecionarPasta.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(btnSelecionarPasta, "12, 6, right, fill");
		
		panelFoto = new JScrollPane();
		panelFoto.setViewportBorder(null);
		panelFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				viewImageDialog(tableProdutos.getSelectedRow());
			}
		});
		panelFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelFoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFoto.setBackground(Color.WHITE);
		panelFoto.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		frmPstoolsV.getContentPane().add(panelFoto, "14, 2, 1, 5, fill, fill");
		
		separator = new JSeparator();
		frmPstoolsV.getContentPane().add(separator, "2, 8, 13, 1");
		
		btnCategoria = new JButton();
		btnCategoria.setIcon(category);
		btnCategoria.setEnabled(false);
		btnCategoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rowsSelected = tableProdutos.getSelectedRows();
				if(rowsSelected.length > 0) {
					String categoria = JOptionPane.showInputDialog(frmPstoolsV, messages.getString("category_new_text"), messages.getString("category_new_title"), JOptionPane.QUESTION_MESSAGE);
					if(categoria != null) {
						if(!categoria.equals("")) {
							for(int i : rowsSelected) {
								model.setValueAt(categoria, i, 2);
							}
						}
					}
				}
			}
		});
		frmPstoolsV.getContentPane().add(btnCategoria, "4, 10, left, default");
		
		btnPreco = new JButton();
		btnPreco.setIcon(price);
		btnPreco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rowsSelected = tableProdutos.getSelectedRows();
				if(rowsSelected.length > 0) {
					String preco = JOptionPane.showInputDialog(frmPstoolsV, messages.getString("price_new_text"), messages.getString("price_new_title"), JOptionPane.QUESTION_MESSAGE);
					if(preco != null) {
						if(!preco.equals("")) {
							for(int i : rowsSelected) {
								model.setValueAt(preco, i, 3);
							}
						}
					}
				}
			}
		});
		btnPreco.setEnabled(false);
		frmPstoolsV.getContentPane().add(btnPreco, "6, 10, left, default");
		
		btnPeso = new JButton();
		btnPeso.setIcon(weight);
		btnPeso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rowsSelected = tableProdutos.getSelectedRows();
				if(rowsSelected.length > 0) {
					String peso = JOptionPane.showInputDialog(frmPstoolsV, messages.getString("weight_new_text"), messages.getString("weight_new_title"), JOptionPane.QUESTION_MESSAGE);
					if(peso != null) {
						if(!peso.equals("")) {
							for(int i : rowsSelected) {
								model.setValueAt(peso, i, 4);
							}
						}
					}
				}
			}
		});
		btnPeso.setEnabled(false);
		frmPstoolsV.getContentPane().add(btnPeso, "8, 10, left, default");
		
		btnQuantidade = new JButton();
		btnQuantidade.setIcon(quantity);
		btnQuantidade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rowsSelected = tableProdutos.getSelectedRows();
				if(rowsSelected.length > 0) {
					String quantidade = JOptionPane.showInputDialog(frmPstoolsV, messages.getString("quantity_new_text"), messages.getString("quantity_new_title"), JOptionPane.QUESTION_MESSAGE);
					if(quantidade != null) {
						if(!quantidade.equals("")) {
							for(int i : rowsSelected) {
								model.setValueAt(quantidade, i, 5);
							}
						}
					}
				}
			}
		});
		btnQuantidade.setEnabled(false);
		frmPstoolsV.getContentPane().add(btnQuantidade, "10, 10, left, default");
		
		btnSalvar = new JButton();
		btnSalvar.setIcon(save_ok);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					TransformerFactory transformerFactory;
					Transformer transformer;
					DOMSource source;
					StreamResult result;
					
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder;
					
					docBuilder = docFactory.newDocumentBuilder();
					
					// root elements
					Document doc = docBuilder.newDocument();
					Element configElement = doc.createElement("config");
					doc.appendChild(configElement);
			 
					// name element
					Element name = doc.createElement("name");
					name.appendChild(doc.createTextNode(textFieldNome.getText()));
					configElement.appendChild(name);
					
					// url element
					Element url = doc.createElement("url");
					url.appendChild(doc.createTextNode(textFieldUrl.getText()));
					configElement.appendChild(url);
					
					// path element
					Element path = doc.createElement("path");
					path.appendChild(doc.createTextNode(textFieldCaminho.getText()));
					configElement.appendChild(path);
					
					// active element
					Element active = doc.createElement("active");
					active.appendChild(doc.createTextNode(config.cfgAtivo));
					configElement.appendChild(active);

					// on_sale element
					Element on_sale = doc.createElement("on_sale");
					on_sale.appendChild(doc.createTextNode(config.cfgOferta));
					configElement.appendChild(on_sale);
					
					// available element
					Element available = doc.createElement("available");
					available.appendChild(doc.createTextNode(config.cfgDisponivel));
					configElement.appendChild(available);
					
					// show_price element
					Element show_price = doc.createElement("show_price");
					show_price.appendChild(doc.createTextNode(config.cfgExibePreco));
					configElement.appendChild(show_price);
					
					// delete_images element
					Element delete_images = doc.createElement("delete_images");
					delete_images.appendChild(doc.createTextNode(config.cfgDeleteImagens));
					configElement.appendChild(delete_images);
					
					// only_online element
					Element only_online = doc.createElement("only_online");
					only_online.appendChild(doc.createTextNode(config.cfgSomenteOnline));
					configElement.appendChild(only_online);
					
					transformerFactory = TransformerFactory.newInstance();
					transformer = transformerFactory.newTransformer();
					source = new DOMSource(doc);
					result = new StreamResult(new File(diretorio+"config.xml"));
			 
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);
			 
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.transform(source, result);
					
					docFactory = DocumentBuilderFactory.newInstance();
					docBuilder = docFactory.newDocumentBuilder();
					
					// root elements
					doc = docBuilder.newDocument();
					Element dataElement = doc.createElement("data");
					doc.appendChild(dataElement);
					
					Element rowElement;
					
					for (int i = 0; i < tableProdutos.getRowCount(); i++) {
						rowElement = doc.createElement("row");
						dataElement.appendChild(rowElement);
						
						// "Refer\u00EAncia", "Nome", "Categoria", "Pre\u00E7o", "Peso", "Quantidade", "Descri\u00E7\u00E3o", "Fotos"
						
						// reference element
						Element reference = doc.createElement("reference");
						reference.appendChild(doc.createTextNode((String) tableProdutos.getValueAt(i, 0)));
						rowElement.appendChild(reference);
						
						// name element
						Element named = doc.createElement("name");
						named.appendChild(doc.createTextNode((String) tableProdutos.getValueAt(i, 1)));
						rowElement.appendChild(named);
						
						// category element
						Element category = doc.createElement("category");
						category.appendChild(doc.createTextNode((String) tableProdutos.getValueAt(i, 2)));
						rowElement.appendChild(category);
						
						// price element
						Element price = doc.createElement("price");
						price.appendChild(doc.createTextNode((String) tableProdutos.getValueAt(i, 3)));
						rowElement.appendChild(price);
						
						// weight element
						Element weight = doc.createElement("weight");
						weight.appendChild(doc.createTextNode(String.valueOf(tableProdutos.getValueAt(i, 4))));
						rowElement.appendChild(weight);
						
						// quantity element
						Element quantity = doc.createElement("quantity");
						quantity.appendChild(doc.createTextNode(String.valueOf(tableProdutos.getValueAt(i, 5))));
						rowElement.appendChild(quantity);
						
						// description element
						Element description = doc.createElement("description");
						description.appendChild(doc.createTextNode((String) tableProdutos.getValueAt(i, 6)));
						rowElement.appendChild(description);
						
						// photos element
						Element photos = doc.createElement("photos");
						photos.appendChild(doc.createTextNode(String.valueOf(tableProdutos.getValueAt(i, 7))));
						rowElement.appendChild(photos);
					}
			 
					transformerFactory = TransformerFactory.newInstance();
					transformer = transformerFactory.newTransformer();
					source = new DOMSource(doc);
					result = new StreamResult(new File(diretorio+"data.xml"));
			 
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.transform(source, result);
					
					JOptionPane.showMessageDialog(frmPstoolsV, messages.getString("dialog_save"), messages.getString("dialog_save_title"), JOptionPane.INFORMATION_MESSAGE);
					
					btnSalvar.setIcon(save_ok);
					btnSalvar.setToolTipText(messages.getString("button_save_ok"));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnRemover = new JButton();
		btnRemover.setIcon(delete);
		btnRemover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rowsSelected = tableProdutos.getSelectedRows();
				if(rowsSelected.length > 0) {
					String codProduto = "";
					for (int i : rowsSelected) {
						codProduto += ((String) tableProdutos.getValueAt(i, 0))+" ";
					}
					int confirm = JOptionPane.showConfirmDialog(frmPstoolsV, MessageFormat.format(messages.getString("confirm_delete"), codProduto), messages.getString("confirm_delete_title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(confirm == JOptionPane.YES_OPTION) {
						String[] termFoto;
						File fileFoto;
						
						int delete = JOptionPane.showConfirmDialog(frmPstoolsV, messages.getString("confirm_delete_images"), messages.getString("confirm_delete_title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						for (int j = rowsSelected.length-1; j >= 0; j--) {
							model.removeRow(rowsSelected[j]);
							if(delete == JOptionPane.YES_OPTION) {
								termFoto = ((String) tableProdutos.getValueAt(rowsSelected[j], 7)).split(",");
								for (int k = 0; k < termFoto.length; k++) {
						            fileFoto = new File(textFieldCaminho.getText()+"/"+termFoto[k]);
						            System.out.println(fileFoto.getAbsolutePath());
						            if(fileFoto.exists())
						            	fileFoto.delete();
								}
							}
						}
						btnRemover.setEnabled(false);
						btnCategoria.setEnabled(false);
						btnPreco.setEnabled(false);
			    		btnPeso.setEnabled(false);
			    		btnQuantidade.setEnabled(false);
						panelFoto.setViewportView(null);
					}
				}
			}
		});
		
		btnRemover.setEnabled(false);
		frmPstoolsV.getContentPane().add(btnRemover, "14, 10, right, default");
		btnSalvar.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(btnSalvar, "4, 14, 3, 1, left, default");
		
		btnExportar = new JButton();
		btnExportar.setIcon(export);
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					SimpleDateFormat dateFormat = null;
					if(locale.getLanguage().equals("pt"))
						dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					else if(locale.getLanguage().equals("en"))
						dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					
					Date date = new Date();
					String filename = diretorio+"prestashop_"+dateFormat.format(date)+".csv";
					
				    FileWriter writer = new FileWriter(new File(filename));
				    
				     
				    String header = "ID;Active (0/1);Name *;Categories (x,y,z...);Price tax excluded or Price tax included;Tax rules ID;Wholesale price;On sale (0/1);Discount amount;Discount percent;";
					header += "Discount from (yyyy-mm-dd);Discount to (yyyy-mm-dd);Reference #;Supplier reference #;Supplier;Manufacturer;EAN13;UPC;Ecotax;Width;Height;Depth;Weight;";
					header += "Quantity;;;;;;Short description;Description;Tags (x,y,z...);Meta title;Meta keywords;Meta description;URL rewritten;Text when in stock;Text when backorder allowed;";
					header += "Available for order (0 = No, 1 = Yes);Product available date;Product creation date;Show price (0 = No, 1 = Yes);Image URLs (x,y,z...);";
					header += "Delete existing images (0 = No, 1 = Yes);Feature(Name:Value:Position);Available online only (0 = No, 1 = Yes);Condition;Customizable (0 = No, 1 = Yes);";
					header += "Uploadable files (0 = No, 1 = Yes);Text fields (0 = No, 1 = Yes);Out_of_stock;Advanced stock management;ID / Name of shop";
				    //String[] headers = header.split(";");
				    
				    ArrayList<String> headers = new ArrayList<String>(Arrays.asList(header.split(";"))); 
				    
				    writer.append(StringUtils.join(headers, ";")+'\n');
				    
				    // "Refer\u00EAncia", "Nome", "Categoria", "Pre\u00E7o", "Peso", "Quantidade", "Descri\u00E7\u00E3o", "Fotos"
				    
				    for (int i = 0; i < tableProdutos.getRowCount(); i++) {
				    	String[] row = new String[headers.size()];
				    	
				    	row[headers.indexOf("Active (0/1)")] = config.cfgAtivo;
				    	row[headers.indexOf("Reference #")] = (String) tableProdutos.getValueAt(i, 0);
				    	row[headers.indexOf("Name *")] = (String) tableProdutos.getValueAt(i, 1);
				    	row[headers.indexOf("Categories (x,y,z...)")] = (String) tableProdutos.getValueAt(i, 2);
				    	row[headers.indexOf("Price tax excluded or Price tax included")] = ((String) tableProdutos.getValueAt(i, 3)).replace(",", ".");
				    	row[headers.indexOf("Tax rules ID")] = "0";
				    	row[headers.indexOf("Wholesale price")] = ((String) tableProdutos.getValueAt(i, 3)).replace(",", ".");
				    	row[headers.indexOf("On sale (0/1)")] = config.cfgOferta;
				    	row[headers.indexOf("EAN13")] = "0";
				    	row[headers.indexOf("Weight")] = ((String) tableProdutos.getValueAt(i, 4)).replace(",", ".");
				    	row[headers.indexOf("Quantity")] = (String) tableProdutos.getValueAt(i, 5);
				    	row[headers.indexOf("Short description")] = (String) tableProdutos.getValueAt(i, 6);
				    	row[headers.indexOf("Description")] = (String) tableProdutos.getValueAt(i, 6);
				    	String urlrew = Normalizer.normalize((String) tableProdutos.getValueAt(i, 1), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
				    	urlrew = urlrew.toLowerCase().replaceAll(" ", "-");
				    	row[headers.indexOf("URL rewritten")] = urlrew;
				    	row[headers.indexOf("Available for order (0 = No, 1 = Yes)")] = config.cfgDisponivel;
				    	row[headers.indexOf("Show price (0 = No, 1 = Yes)")] = config.cfgExibePreco;
				    	String[] images = ((String) tableProdutos.getValueAt(i, 7)).split(",");
				    	for (int j = 0; j < images.length; j++) {
				    		images[j] = textFieldUrl.getText()+"img/tmp/"+images[j];
						}
				    	row[headers.indexOf("Image URLs (x,y,z...)")] = StringUtils.join(images, ",");
				    	row[headers.indexOf("Delete existing images (0 = No, 1 = Yes)")] = config.cfgDeleteImagens;
				    	row[headers.indexOf("Available online only (0 = No, 1 = Yes)")] = config.cfgSomenteOnline;
				    	row[headers.indexOf("Condition")] = "new";
				    	
					    //writer.append((String) tableProdutos.getValueAt(i, 0) + ";");
			            writer.append(StringUtils.join(row, ";")+'\n');
				    }
			 
				    //generate whatever data you want
				    writer.flush();
				    writer.close();
				    
				    JOptionPane.showMessageDialog(frmPstoolsV, MessageFormat.format(messages.getString("dialog_export"), filename), messages.getString("dialog_export_title"), JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e)
				{
				     e.printStackTrace();
				}
			}
		});
		btnExportar.setFont(new Font("Verdana", Font.BOLD, 14));
		frmPstoolsV.getContentPane().add(btnExportar, "14, 14, right, default");
		
		JScrollPane scrollPane = new JScrollPane();
		frmPstoolsV.getContentPane().add(scrollPane, "4, 12, 11, 1, fill, fill");
		
		tableProdutos = new JTable();
		tableProdutos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				viewImage(tableProdutos.getSelectedRow());
			}
		});
		tableProdutos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	            viewImage(tableProdutos.rowAtPoint(e.getPoint()));
			}
		});
		
		tableProdutos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableProdutos.setRowMargin(2);
		tableProdutos.setRowHeight(25);
		tableProdutos.setFont(new Font("Verdana", Font.BOLD, 14));
		tableProdutos.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Refer\u00EAncia", "Nome", "Categoria", "Pre\u00E7o", "Peso", "Quantidade", "Descri\u00E7\u00E3o", "Fotos"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableProdutos.getColumnModel().getColumn(0).setResizable(false);
		tableProdutos.getColumnModel().getColumn(0).setPreferredWidth(90);
		tableProdutos.getColumnModel().getColumn(0).setMinWidth(90);
		tableProdutos.getColumnModel().getColumn(0).setMaxWidth(90);
		tableProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableProdutos.getColumnModel().getColumn(1).setMinWidth(200);
		tableProdutos.getColumnModel().getColumn(2).setPreferredWidth(90);
		tableProdutos.getColumnModel().getColumn(2).setMinWidth(90);
		tableProdutos.getColumnModel().getColumn(2).setMaxWidth(90);
		tableProdutos.getColumnModel().getColumn(3).setMinWidth(75);
		tableProdutos.getColumnModel().getColumn(3).setMaxWidth(75);
		tableProdutos.getColumnModel().getColumn(4).setMinWidth(75);
		tableProdutos.getColumnModel().getColumn(4).setMaxWidth(75);
		tableProdutos.getColumnModel().getColumn(5).setPreferredWidth(80);
		tableProdutos.getColumnModel().getColumn(5).setMinWidth(80);
		tableProdutos.getColumnModel().getColumn(5).setMaxWidth(80);
		tableProdutos.getColumnModel().getColumn(6).setPreferredWidth(300);
		tableProdutos.getColumnModel().getColumn(6).setMinWidth(75);
		tableProdutos.getColumnModel().getColumn(7).setPreferredWidth(100);
		tableProdutos.getColumnModel().getColumn(7).setMinWidth(100);
		
		model = (DefaultTableModel) tableProdutos.getModel();
		
		tableProdutos.setAutoCreateRowSorter(true);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tableProdutos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tableProdutos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableProdutos.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tableProdutos.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		
		
		DefaultTableCellRenderer fixRenderer = new DefaultTableCellRenderer();
		fixRenderer.setBackground(new Color(204, 204, 204));
		fixRenderer.setHorizontalAlignment( JLabel.CENTER );
		tableProdutos.getColumnModel().getColumn(0).setCellRenderer(fixRenderer);
		tableProdutos.getColumnModel().getColumn(7).setCellRenderer(fixRenderer);
		
		
		scrollPane.setViewportView(tableProdutos);
		
	}
	private void viewImage(int lastRow) {
		try {
			if(lastRow > -1) {
				
	        	String[] termFoto = ((String) tableProdutos.getValueAt(lastRow, 7)).split(",");
	        	
	        	btnRemover.setEnabled(true);
	        	btnCategoria.setEnabled(true);
	        	btnPreco.setEnabled(true);
	    		btnPeso.setEnabled(true);
	    		btnQuantidade.setEnabled(true);
	        	
	            String path;
	            File fileFoto; 
	            Image imageFoto;
	            ImageIcon iconFoto;
	            
	            //System.out.println(fileFoto.getAbsolutePath());
	            panelFoto.setViewportView(null);
	            JPanel fundoFoto = new JPanel();
	            
	            for (int i = 0; i < termFoto.length; i++) {
	            	
	            	path =  textFieldCaminho.getText()+"/"+termFoto[i];
		            fileFoto = new File(path);
		            
	            	if(fileFoto.exists()) {
		            	
			            imageFoto = ImageIO.read(fileFoto);
			            float ph = (float)panelFoto.getHeight(), fh = (float)imageFoto.getHeight(null);
			            int width = (int) ((ph/fh)*imageFoto.getWidth(null));
			            //System.out.println(ph+"/"+fh+"="+width);
			            iconFoto = new ImageIcon(imageFoto.getScaledInstance(width, panelFoto.getHeight(), 0));
			            
						JLabel labelFoto = new JLabel("", iconFoto, JLabel.CENTER);
						
						labelFoto.setSize(panelFoto.getWidth(), panelFoto.getHeight());
						
						fundoFoto.add(labelFoto);
		            }
				}
	            
	            panelFoto.setViewportView(fundoFoto);
	    	} else {
	    		btnRemover.setEnabled(false);
	    		btnCategoria.setEnabled(false);
	    		btnPreco.setEnabled(false);
	    		btnPeso.setEnabled(false);
	    		btnQuantidade.setEnabled(false);
	    	}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void viewImageDialog(int lastRow) {
		try {
			if(lastRow > -1) {
				
	        	String[] termFoto = ((String) tableProdutos.getValueAt(lastRow, 7)).split(",");
	        	
	            String path;
	            File fileFoto; 
	            Image imageFoto;
	            ImageIcon iconFoto;
	            
	            dialogImage = new JDialog(frmPstoolsV);
	            dialogImage.getContentPane().setLayout(new FlowLayout());
	            
	            for (int i = 0; i < termFoto.length; i++) {
	            	
	            	path =  textFieldCaminho.getText()+"/"+termFoto[i];
		            fileFoto = new File(path);
		            
	            	if(fileFoto.exists()) {
		            	
			            imageFoto = ImageIO.read(fileFoto);
			            iconFoto = new ImageIcon(imageFoto);
			            
						JLabel labelFoto = new JLabel("", iconFoto, JLabel.CENTER);
						
						dialogImage.getContentPane().add( labelFoto );
						
						//System.out.println(fileFoto.getAbsolutePath());
		            }
				}
	            dialogImage.pack();
				dialogImage.setVisible(true);
				dialogImage.addComponentListener(new ComponentListener() {
					  public void componentHidden(ComponentEvent e)
					  {
						  frmPstoolsV.setEnabled(true);
					  }
					  public void componentMoved(ComponentEvent e)
					  {
						  System.out.println("dialog moved");
					  }
					  public void componentResized(ComponentEvent e)
					  {
						  System.out.println("dialog resized");
					  }
					  public void componentShown(ComponentEvent e)
					  {
						  System.out.println("dialog shown");
					  }
				});
				frmPstoolsV.setEnabled(false);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void saveWarning() {
		btnSalvar.setIcon(save_alert);
		btnSalvar.setToolTipText(messages.getString("button_save_alert"));
	}
	public static void setLocale(String lang) {
		locale = new Locale(lang);
		Locale.setDefault(locale);
		messages = ResourceBundle.getBundle("i18n.lang", locale);
		
		appTitle = messages.getString("app_title");
		appDescription = messages.getString("app_description");
		appDeveloper = messages.getString("app_developer");
		
		window.frmPstoolsV.setTitle(appTitle+" "+appVersion+" - "+appDescription);
		
		mnArquivo.setText(messages.getString("menu_file"));
		mntmSair.setText(messages.getString("menuitem_exit"));
		mnFerramentas.setText(messages.getString("menu_tools"));
		mnIdioma.setText(messages.getString("menu_language"));
		chckbxmntmPortugus.setText(messages.getString("menuitem_pt"));
		chckbxmntmIngls.setText(messages.getString("menuitem_en"));
		mntmConfiguraes.setText(messages.getString("menuitem_config"));
		mnAjuda.setText(messages.getString("menu_help"));
		mntmContedo.setText(messages.getString("menuitem_content"));
		mntmUpdate.setText(messages.getString("menuitem_update"));
		mntmSuporte.setText(messages.getString("menuitem_support"));
		mntmSobre.setText(messages.getString("menuitem_about"));
		
		lblNome.setText(messages.getString("label_name"));
		lblUrl.setText(messages.getString("label_url"));
		lblCaminho.setText(messages.getString("label_path"));
		
		btnSelecionarPasta.setText(messages.getString("button_select_folder"));
		btnCategoria.setText(messages.getString("button_category"));
		btnPeso.setText(messages.getString("button_weight"));
		btnPreco.setText(messages.getString("button_price"));
		btnQuantidade.setText(messages.getString("button_quantity"));
		btnRemover.setText(messages.getString("button_delete"));
		btnSalvar.setText(messages.getString("button_save"));
		btnExportar.setText(messages.getString("button_export"));
		
		if(btnSalvar.getIcon().equals(save_ok))
			btnSalvar.setToolTipText(messages.getString("button_save_ok"));
		else
			btnSalvar.setToolTipText(messages.getString("button_save_alert"));
		
		panelFoto.setToolTipText(messages.getString("panel_images"));
		
		tableProdutos.getColumnModel().getColumn(0).setHeaderValue(messages.getString("table_header0"));
		tableProdutos.getColumnModel().getColumn(1).setHeaderValue(messages.getString("table_header1"));
		tableProdutos.getColumnModel().getColumn(2).setHeaderValue(messages.getString("table_header2"));
		tableProdutos.getColumnModel().getColumn(3).setHeaderValue(messages.getString("table_header3"));
		tableProdutos.getColumnModel().getColumn(4).setHeaderValue(messages.getString("table_header4"));
		tableProdutos.getColumnModel().getColumn(5).setHeaderValue(messages.getString("table_header5"));
		tableProdutos.getColumnModel().getColumn(6).setHeaderValue(messages.getString("table_header6"));
		tableProdutos.getColumnModel().getColumn(7).setHeaderValue(messages.getString("table_header7"));
		
	}
}
