import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objetos.Depart;

public class InsertarDepart extends PanelBDOO {
	PanelBDOOListener listener;
	String[] textoCampos = { Depart.__deptNo__, Depart.__dnombre__, Depart.__loc__ };
	LinkedHashMap<String, Campo> campos;
	JPanel formulario, mainPanel;
	JButton insertarDepart;
	GridLayout layout;
	
	public InsertarDepart(PanelBDOOListener listener) throws Exception {
		super();
		if (listener == null) {
			throw new Exception("El listener es null");
		}
		
		this.listener = listener;
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		formulario = new JPanel(new GridLayout(0,2));
		mainPanel = new JPanel(new GridBagLayout());
		campos = new LinkedHashMap<String, Campo>();
		
		prepararCampos();
	}

	private void prepararCampos() {
		for (String textoCampo : textoCampos) {
			campos.put(textoCampo, new Campo(textoCampo + ": ", new JTextField()));
		}
	}
	
	public InsertarDepart(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
	
	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		
		campos.forEach((etiqueta, campo) -> cargarCampo(campo));
		agregarFormulario();
		agregarBotonInsertarDepart();
		this.add(mainPanel);
	}
	
	private void agregarFormulario() {
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		formulario.setPreferredSize(new Dimension(700, (formulario.getComponentCount()/2) * (this.fuente.getSize()+10)));
		mainPanel.add(formulario, c);
	}
	
	private void cargarCampo(Campo campo) {
		JLabel label = campo.etiqueta;
		JTextField textField = (JTextField) campo.campo;
		
		label.setFont(this.fuente);
		textField.setFont(this.fuente);
		formulario.add(label);
		formulario.add(textField);
	}
	
	private void agregarBotonInsertarDepart() {
		insertarDepart = new JButton();
		insertarDepart.setText("INSERTAR");
		insertarDepart.setFont(this.fuente);
		insertarDepart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.insertarCamposDepart(campos);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridy = 2;
		c.gridx = 0;
		mainPanel.add(insertarDepart, c);
	}
}
