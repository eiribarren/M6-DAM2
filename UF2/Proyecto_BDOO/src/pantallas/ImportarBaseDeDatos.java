package pantallas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImportarBaseDeDatos extends PanelBDOO {

	PanelBDOOListener listener;
	public static final String _IP_ = "IP";
	public static final String _PUERTO_ = "Puerto";
	public static final String _DB_ = "Base de datos";
	public static final String _USUARIO_ = "Usuario";
	public static final String _PASSWORD_ = "Contraseña";
	String[] textCampos = { "IP", "Puerto", "Base de datos", "Usuario", "Contraseña"};
	LinkedHashMap<String, Campo> campos;
	JPanel formulario, mainPanel;
	JButton conectarConDB;
	
	public ImportarBaseDeDatos(PanelBDOOListener listener) throws Exception {
		if (listener == null) {
			throw new Exception("El listener es null");
		}
		this.listener = listener;
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		this.campos = new LinkedHashMap<String, Campo>();
		this.mainPanel = new JPanel(new GridBagLayout());
		this.formulario = new JPanel(new GridLayout(0,2));
	}
	
	public ImportarBaseDeDatos(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
	
	@Override
	public void cargarUI() {
		if (isLoaded)
			return;
		
		isLoaded = true;
		agregarCamposFormulario();
		agregarFormulario();
		agregarBotonConectarConDB();
		this.add(mainPanel);
	}

	private void agregarBotonConectarConDB() {
		GridBagConstraints c = new GridBagConstraints();
		conectarConDB = new JButton();
		conectarConDB.setText("Conectar");
		conectarConDB.setFont(this.fuente);
		conectarConDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.mostrarListaDeEmpleadosImportacion(((JTextField)campos.get(_IP_).campo).getText(),
											  ((JTextField)campos.get(_PUERTO_).campo).getText(),
											  ((JTextField)campos.get(_DB_).campo).getText(),
											  ((JTextField)campos.get(_USUARIO_).campo).getText(),
											  ((JTextField)campos.get(_PASSWORD_).campo).getText());
			}
		});
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(conectarConDB, c);
	}

	private void agregarFormulario() {
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy = 0;
		c.gridx = 0;
		mainPanel.add(formulario, c);
		formulario.setPreferredSize(new Dimension(700, (formulario.getComponentCount()/2) * (this.fuente.getSize()+10)));
	}

	private void agregarCamposFormulario() {
		for (String textCampo : textCampos) {
			campos.put(textCampo, new Campo(textCampo, new JTextField()));
		}
		campos.forEach((etiqueta, campo) -> cargarCampo(campo));
	}
	
	void cargarCampo(Campo campo) {
		campo.etiqueta.setFont(this.fuente);
		campo.campo.setFont(this.fuente);
		formulario.add(campo.etiqueta);
		formulario.add(campo.campo);
	}

}
