package pantallas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objetos.Depart;
import objetos.Emple;


public class ListaDeEmpleados extends PanelBDOO {
	
	JComboBox<Emple> empleadosBox;
	Emple[] empleados;
	PanelBDOOListener listener;
	JPanel informacion;
	String[] camposText = { Emple.__empNo__, Emple.__apellido__, Emple.__oficio__, Emple.__dir__, Emple.__fechaAlt__, Emple.__salario__, Emple.__comision__, Emple.__dept__ };
	LinkedHashMap<String, Campo> campos;
	
	public ListaDeEmpleados(PanelBDOOListener listener, Emple[] empleados) throws Exception {
		super();
		if ( listener == null ) {
			throw new Exception("El listener es null");
		}
		setLayout(new GridBagLayout());
		
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		
		this.listener = listener;
		this.empleados = empleados;
		this.campos = new LinkedHashMap<String, Campo>();
	}
	
	public ListaDeEmpleados(PanelBDOOListener listener, Emple[] empleados, Font fuente) throws Exception {
		this(listener, empleados);
		this.fuente = fuente;
	}

	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		agregarComboBox();
		agregarCamposDeInformacion();
		informacion.setPreferredSize(new Dimension(700, (informacion.getComponentCount()/2) * (this.fuente.getSize()+10)));
		if (empleados.length > 0) {
			cargarDatos(empleados[0]);
		}
	}

	private void agregarCamposDeInformacion() {
		this.informacion = new JPanel(new GridLayout(0,2));
		for (String campoText : camposText) {
			JTextField textField = new JTextField();
			textField.setFont(this.fuente);
			textField.setEditable(false);
			Campo campo = new Campo(campoText + ": ", textField);
			campo.etiqueta.setFont(this.fuente);
			informacion.add(campo.etiqueta);
			informacion.add(textField);
			campos.put(campoText, campo);
		}
		
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		this.add(informacion, c);
	}

	private void agregarComboBox() {
		this.empleadosBox = new JComboBox<Emple>(empleados);
		this.empleadosBox.setFont(this.fuente);
		this.empleadosBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Emple emple = (Emple)cb.getSelectedItem();
				cargarDatos(emple);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridy = 0;
		c.gridx = 0;
		this.add(empleadosBox, c);
	}
	
	private void cargarDatos(Emple emple) {
		((JTextField)campos.get(Emple.__empNo__).campo).setText(String.valueOf(emple.getEmpNo()));
		((JTextField)campos.get(Emple.__apellido__).campo).setText(emple.getApellido());
		((JTextField)campos.get(Emple.__oficio__).campo).setText(emple.getOficio());
		Emple dir = emple.getDir();
		String dirNombre;
		if (dir == null) {
			dirNombre = "null";
		} else {
			dirNombre = dir.getApellido();
		}
		((JTextField)campos.get(Emple.__dir__).campo).setText(dirNombre);
		((JTextField)campos.get(Emple.__fechaAlt__).campo).setText(emple.getFechaAlt().toString());
		((JTextField)campos.get(Emple.__salario__).campo).setText(String.valueOf(emple.getSalario()));
		((JTextField)campos.get(Emple.__comision__).campo).setText(String.valueOf(emple.getComision()));
		Depart dept = emple.getDept();
		String dnombre;
		if (dept == null) {
			dnombre = "null";
		} else {
			dnombre = emple.getDept().getDnombre();
		}
		((JTextField)campos.get(Emple.__dept__).campo).setText(dnombre);
	}
}
