import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	JLabel empNoLabel, apellidoLabel, oficioLabel, dirLabel, fechaAltLabel, comisionLabel, deptLabel;
	JTextField empNoField, apellidoField, oficioField, dirField, fechaAltField, comisionField, deptField;
	
	public ListaDeEmpleados(PanelBDOOListener listener, Emple[] empleados) throws Exception {
		super();
		if ( listener == null ) {
			throw new Exception("El listener es null");
		}
		setLayout(new GridBagLayout());
		
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		
		this.listener = listener;
		this.empleados = empleados;
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
		
		this.empNoLabel = new JLabel("empNo: ");
		this.empNoLabel.setFont(this.fuente);
		this.empNoField = new JTextField();
		this.empNoField.setFont(this.fuente);
		this.empNoField.setEditable(false);
		this.informacion.add(empNoLabel);
		this.informacion.add(empNoField);
		
		this.apellidoLabel = new JLabel("Apellido: ");
		this.apellidoLabel.setFont(this.fuente);
		this.apellidoField = new JTextField();
		this.apellidoField.setFont(this.fuente);
		this.apellidoField.setEditable(false);
		this.informacion.add(apellidoLabel);
		this.informacion.add(apellidoField);
		
		this.oficioLabel = new JLabel("Oficio: ");
		this.oficioLabel.setFont(this.fuente);
		this.oficioField = new JTextField();
		this.oficioField.setFont(this.fuente);
		this.oficioField.setEditable(false);
		this.informacion.add(oficioLabel);
		this.informacion.add(oficioField);
		
		this.dirLabel = new JLabel("Director: ");
		this.dirLabel.setFont(this.fuente);
		this.dirField = new JTextField();
		this.dirField.setFont(this.fuente);
		this.dirField.setEditable(false);
		this.informacion.add(dirLabel);
		this.informacion.add(dirField);
		
		this.fechaAltLabel = new JLabel("Fecha de alta: ");
		this.fechaAltLabel.setFont(this.fuente);
		this.fechaAltField = new JTextField();
		this.fechaAltField.setFont(this.fuente);
		this.fechaAltField.setEditable(false);
		this.informacion.add(fechaAltLabel);
		this.informacion.add(fechaAltField);
		
		this.comisionLabel = new JLabel("Comisión: ");
		this.comisionLabel.setFont(this.fuente);
		this.comisionField = new JTextField();
		this.comisionField.setFont(this.fuente);
		this.comisionField.setEditable(false);
		this.informacion.add(comisionLabel);
		this.informacion.add(comisionField);
		
		this.deptLabel = new JLabel("Departamento: ");
		this.deptLabel.setFont(this.fuente);
		this.deptField = new JTextField();
		this.deptField.setFont(this.fuente);
		this.deptField.setEditable(false);
		this.informacion.add(deptLabel);
		this.informacion.add(deptField);
		
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
		empNoField.setText(String.valueOf(emple.getEmpNo()));
		apellidoField.setText(emple.getApellido());
		oficioField.setText(emple.getOficio());
		Emple dir = emple.getDir();
		String dirNombre;
		if (dir == null) {
			dirNombre = "null";
		} else {
			dirNombre = dir.getApellido();
		}
		dirField.setText(dirNombre);
		fechaAltField.setText(emple.getFechaAlt().toString());
		comisionField.setText(String.valueOf(emple.getComision()));
		Depart dept = emple.getDept();
		String dnombre;
		if (dept == null) {
			dnombre = "null";
		} else {
			dnombre = emple.getDept().getDnombre();
		}
		deptField.setText(dnombre);
	}
}
