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

public class ListaDeDepartamentos extends PanelBDOO {
	JComboBox<Depart> departamentosBox;
	Depart[] departamentos;
	PanelBDOOListener listener;
	JPanel informacion;
	JLabel deptNoLabel, dNombreLabel, locLabel;
	JTextField deptNoField, dNombreField, locField;
	
	public ListaDeDepartamentos(PanelBDOOListener listener, Depart[] departamentos) throws Exception {
		super();
		if ( listener == null ) {
			throw new Exception("El listener es null");
		}
		setLayout(new GridBagLayout());
		
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		
		this.listener = listener;
		this.informacion = new JPanel(new GridLayout(0,2));
		this.departamentos = departamentos;
	}
	
	public ListaDeDepartamentos(PanelBDOOListener listener, Depart[] departamentos, Font fuente) throws Exception {
		this(listener, departamentos);
		this.fuente = fuente;
	}
	
	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		
		agregarCamposDeInformacion();
		agregarComboBox();
		informacion.setPreferredSize(new Dimension(700, (informacion.getComponentCount()/2) * (this.fuente.getSize()+10)));
		if (departamentos.length > 0) {
			cargarDatos(departamentos[0]);
		}
	}

	private void agregarCamposDeInformacion() {
		this.deptNoLabel = new JLabel("deptNo: ");
		this.deptNoLabel.setFont(this.fuente);
		this.deptNoField = new JTextField();
		this.deptNoField.setFont(this.fuente);
		this.deptNoField.setEditable(false);
		this.informacion.add(deptNoLabel);
		this.informacion.add(deptNoField);
		
		this.dNombreLabel = new JLabel("Nombre: ");
		this.dNombreLabel.setFont(this.fuente);
		this.dNombreField = new JTextField();
		this.dNombreField.setFont(this.fuente);
		this.dNombreField.setEditable(false);
		this.informacion.add(dNombreLabel);
		this.informacion.add(dNombreField);
		
		this.locLabel = new JLabel("Localización: ");
		this.locLabel.setFont(this.fuente);
		this.locField = new JTextField();
		this.locField.setFont(this.fuente);
		this.locField.setEditable(false);
		this.informacion.add(locLabel);
		this.informacion.add(locField);
		
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		this.add(informacion, c);
	}

	private void agregarComboBox() {
		this.departamentosBox = new JComboBox<Depart>(departamentos);
		this.departamentosBox.setFont(this.fuente);
		this.departamentosBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Depart depart = (Depart)cb.getSelectedItem();
				cargarDatos(depart);
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridy = 0;
		c.gridx = 0;
		this.add(departamentosBox, c);
	}
	
	private void cargarDatos(Depart depart) {
		deptNoField.setText(String.valueOf(depart.getDeptNo()));
		dNombreField.setText(depart.getDnombre());
		locField.setText(depart.getLoc());
	}
}
