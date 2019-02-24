import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class MenuPrincipal extends PanelBDOO {
	
	JButton insertarDepartButton, insertarEmpleButton, listaDeEmpleados, listaDeDepartamentos, consultasProyecto, importarBaseDeDatos, modificarSalario, eliminarEmpleado;
	JLabel textoPrincipal;
	JPanel botonesPanel;
	PanelBDOOListener listener;
	
	/**
	 * @wbp.parser.constructor
	 */
	public MenuPrincipal(PanelBDOOListener listener) throws Exception {
		super();
		if (listener == null) {
			throw new Exception("El listener es null");
		}
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		this.listener = listener;
	}
	
	public MenuPrincipal(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
	
	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		agregarTextoPrincipal();
		agregarPanelDeBotones();
		agregarBotonInsertarUsuario();
		agregarBotonInsertarDepart();
		agregarBotonListaDeEmpleados();
		agregarBotonListaDeDepartamentos();
		agregarBotonConsultasProyecto();
		agregarBotonImportarBaseDeDatos();
		agregarBotonModificarSalario();
		agregarBotonEliminarEmpleado();
	}

	private void agregarBotonEliminarEmpleado() {
		eliminarEmpleado = new JButton();
		eliminarEmpleado.setText("Eliminar empleado");
		eliminarEmpleado.setFont(this.fuente);
		eliminarEmpleado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.mostrarEliminarEmpleado();
			}
		});
		botonesPanel.add(eliminarEmpleado);
	}

	private void agregarBotonModificarSalario() {
		modificarSalario = new JButton();
		modificarSalario.setText("Modificar salario");
		modificarSalario.setFont(this.fuente);
		modificarSalario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.mostrarModificarSalario();
			}
		});
		botonesPanel.add(modificarSalario);
	}

	private void agregarBotonConsultasProyecto() {
		consultasProyecto = new JButton();
		consultasProyecto.setText("Ver consultas del proyecto");
		consultasProyecto.setFont(this.fuente);
		consultasProyecto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.mostrarConsultasProyecto();
			}
		});
		botonesPanel.add(consultasProyecto);
	}

	private void agregarBotonListaDeEmpleados() {
		listaDeEmpleados = new JButton();
		listaDeEmpleados.setText("Ver empleados");
		listaDeEmpleados.setFont(this.fuente);
		listaDeEmpleados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.mostrarListaDeEmpleados();
			}
		});
		botonesPanel.add(listaDeEmpleados);
	}
	
	private void agregarBotonListaDeDepartamentos() {
		listaDeDepartamentos = new JButton();
		listaDeDepartamentos.setText("Ver departamentos");
		listaDeDepartamentos.setFont(this.fuente);
		listaDeDepartamentos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.mostrarListaDeDepartamentos();
			}
		});
		botonesPanel.add(listaDeDepartamentos);
	}

	private void agregarTextoPrincipal() {
		textoPrincipal = new JLabel();
		textoPrincipal.setAlignmentX(CENTER_ALIGNMENT);
		textoPrincipal.setText("Selecciona una acción");
		textoPrincipal.setFont(this.fuente);
		this.add(textoPrincipal);
	}
	
	private void agregarPanelDeBotones() {
		botonesPanel = new JPanel();
		botonesPanel.setLayout(new GridLayout(0,2));
		this.add(botonesPanel);
	}

	private void agregarBotonInsertarUsuario() {
		insertarEmpleButton = new JButton();
		insertarEmpleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.mostrarInsertarEmple();
			}
		});
		insertarEmpleButton.setText("Insertar Empleado");
		insertarEmpleButton.setFont(this.fuente);
		botonesPanel.add(insertarEmpleButton);
	}
	
	private void agregarBotonInsertarDepart() {
		insertarDepartButton = new JButton();
		insertarDepartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				listener.mostrarInsertarDepart();
			}
		});
		insertarDepartButton.setText("Insertar Departamento");
		insertarDepartButton.setFont(this.fuente);
		botonesPanel.add(insertarDepartButton);
	}

	private void agregarBotonImportarBaseDeDatos() {
		importarBaseDeDatos = new JButton();
		importarBaseDeDatos.setText("Importar desde MySql");
		importarBaseDeDatos.setFont(this.fuente);
		importarBaseDeDatos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.mostrarFormularioBaseDeDatos();
			}
		});
		botonesPanel.add(importarBaseDeDatos);
	}
}
