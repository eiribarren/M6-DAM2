package pantallas;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import objetos.Emple;


public class EliminarEmpleado extends PanelBDOO {
	
	PanelBDOOListener listener;
	Font fuente;
	JComboBox<Emple> empleados;
	JButton confirmar;
	
	public EliminarEmpleado(PanelBDOOListener listener) throws Exception {
		if (listener == null) {
			throw new Exception("El listener es null");
		}
		this.listener = listener;
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
	}
	
	public EliminarEmpleado(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
	
	@Override
	public void cargarUI() {
		agregarListaDeEmpleados();
		agregarBotonDeConfirmar();
	}

	private void agregarBotonDeConfirmar() {
		confirmar = new JButton();
		confirmar.setText("ELIMINAR");
		confirmar.setFont(fuente);
		confirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.eliminarEmpleado(empleados.getItemAt(empleados.getSelectedIndex()).getApellido());
			}
		});
		this.add(confirmar);
	}

	private void agregarListaDeEmpleados() {
		empleados = new JComboBox<Emple>(listener.obtenerEmpleados());
		empleados.setFont(fuente);
		this.add(empleados);
	}

}
