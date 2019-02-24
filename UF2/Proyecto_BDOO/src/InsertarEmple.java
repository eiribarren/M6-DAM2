import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objetos.Depart;
import objetos.Emple;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class InsertarEmple extends PanelBDOO {
	
	PanelBDOOListener listener;
	String[] textoCampos = {Emple.__empNo__, Emple.__apellido__, Emple.__oficio__, Emple.__salario__, Emple.__comision__};
	LinkedHashMap<String, Campo> campos;
	JPanel formulario, mainPanel;
	JButton insertarEmple;
	GridLayout layout;
	
	public InsertarEmple(PanelBDOOListener listener) throws Exception {
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

	public InsertarEmple(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
	
	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		
		campos.forEach((etiqueta, campo) -> cargarCampo(campo));
		prepararDirField();
		prepararDeptField();
		agregarFormulario();
		agregarBotonInsertarEmple();
		this.add(mainPanel);
	}

	private void prepararCampos() {
		for (String textoCampo : textoCampos) {
			this.campos.put(textoCampo, new Campo(textoCampo + ": ", new JTextField()));
		}
		this.campos.put(Emple.__dir__, new Campo("Director: ", new JComboBox<Emple>()));
		this.campos.put(Emple.__dept__, new Campo("Departamento: ", new JComboBox<Depart>()));
	}

	private void prepararDirField() {
		JComboBox<Emple> dirField = (JComboBox<Emple>)campos.get(Emple.__dir__).campo;
		JLabel dirLabel = campos.get(Emple.__dir__).etiqueta;
		
		Emple[] empleados = listener.obtenerEmpleados();
		dirField.addItem(new Emple(0, "-", "-", null, null, 0, 0, null));
		for (Emple emple : empleados) {
			dirField.addItem(emple);
		}
		dirLabel.setFont(this.fuente);
		dirField.setFont(this.fuente);
		formulario.add(dirLabel);
		formulario.add(dirField);
	}

	private void prepararDeptField() {
		JComboBox<Depart> deptField = (JComboBox<Depart>)campos.get(Emple.__dept__).campo;
		JLabel deptLabel = campos.get(Emple.__dept__).etiqueta;
		deptLabel.setFont(this.fuente);
		deptField.setFont(this.fuente);
		
		Depart[] departamentos = listener.obtenerDepartamentos();
		deptField.addItem(new Depart(0, "-", "-"));
		for (Depart depart : departamentos) {
			deptField.addItem(depart);
		}
		formulario.add(deptLabel);
		formulario.add(deptField);
	}
	
	private void agregarFormulario() {
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		formulario.setPreferredSize(new Dimension(700, (formulario.getComponentCount()/2) * (this.fuente.getSize()+10)));
		mainPanel.add(formulario, c);
	}

	private void agregarBotonInsertarEmple() {
		insertarEmple = new JButton();
		insertarEmple.setText("INSERTAR");
		insertarEmple.setFont(this.fuente);
		insertarEmple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.insertarCamposEmple(campos);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridy = 2;
		c.gridx = 0;
		mainPanel.add(insertarEmple, c);
	}

	private void cargarCampo(Campo campo) {
		campo.etiqueta.setFont(this.fuente);
		campo.campo.setFont(this.fuente);
		formulario.add(campo.etiqueta);
		formulario.add(campo.campo);
	}
}
