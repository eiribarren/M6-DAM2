import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objetos.Emple;

public class ConexionConDB extends PanelBDOO {

	PanelBDOOListener listener;
	JLabel titulo;
	JButton importar, deseleccionar;
	JPanel mainPanel, botonesPanel;
	JList<Emple> lista;
	
	public ConexionConDB (PanelBDOOListener listener, Emple[] empleados) throws Exception {
		if (listener == null) 
			throw new Exception("El listener es null");
		this.listener = listener;
		this.lista = new JList<Emple>(empleados);
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		this.mainPanel = new JPanel(new GridBagLayout());
		this.botonesPanel = new JPanel(new GridLayout(0,2));
	}
	
	public ConexionConDB (PanelBDOOListener listener, Emple[] empleados, Font fuente) throws Exception {
		this(listener, empleados);
		this.fuente = fuente;
	}
	
	@Override
	void cargarUI() {
		if (isLoaded)
			return;
		
		isLoaded = true;
		agregarTitulo();
		agregarListaDeEmpleados();
		agregarBotonDeseleccionar();
		agregarBotonImportar();
		agregarBotonesPanel();
		this.add(mainPanel);
	}

	private void agregarBotonesPanel() {
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridy = 2;
		c.gridx = 0;
		mainPanel.add(botonesPanel, c);
	}

	private void agregarBotonDeseleccionar() {
		deseleccionar = new JButton();
		deseleccionar.setText("DESELECCIONAR TODO");
		deseleccionar.setFont(this.fuente);
		deseleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lista.clearSelection();
			}
		});
		botonesPanel.add(deseleccionar);
	}

	private void agregarBotonImportar() {
		importar = new JButton();
		importar.setText("IMPORTAR");
		importar.setFont(this.fuente);
		importar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Emple> emples = lista.getSelectedValuesList();
				for (Emple emple : emples) {
					listener.insertarEmpleado(emple);
				}
			}
		});
		botonesPanel.add(importar);
	}

	private void agregarTitulo() {
		GridBagConstraints c = new GridBagConstraints();
		titulo = new JLabel("Importar empleados de MySql a BDOO");
		titulo.setFont(this.fuente);
		c.weighty = 0.2;
		c.gridy = 0;
		c.gridx = 0;
		mainPanel.add(titulo, c);
	}

	private void agregarListaDeEmpleados() {
		GridBagConstraints c = new GridBagConstraints();
		JScrollPane scrollPane = new JScrollPane(this.lista, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		lista.setFont(this.fuente);
		scrollPane.setPreferredSize(new Dimension(500,200));
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		mainPanel.add(scrollPane, c);
	}

}
