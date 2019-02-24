import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objetos.Emple;

public class ModificarSalario extends PanelBDOO {
	
	PanelBDOOListener listener;
	Font fuente;
	JComboBox<Emple> empleados;
	JTextField salarioNuevo;
	JPanel formulario;
	JButton confirmar;
	
	public ModificarSalario(PanelBDOOListener listener) throws Exception {
		if (listener == null) {
			throw new Exception("El listener es null");
		}
		this.listener = listener;
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
		setLayout(new GridLayout(0,1));
	}
	
	public ModificarSalario(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}

	@Override
	void cargarUI() {
		agregarListaDeEmpleados();
		agregarFormulario();
		agregarBotonDeConfirmar();
	}

	private void agregarBotonDeConfirmar() {
		confirmar = new JButton();
		confirmar.setText("CONFIRMAR");
		confirmar.setFont(fuente);
		confirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.modificarSalario(empleados.getItemAt(empleados.getSelectedIndex()).getApellido(), salarioNuevo.getText());
			}
		});
		this.add(confirmar);
	}

	private void agregarFormulario() {
		JLabel salarioNuevoLabel = new JLabel("Salario nuevo: ");
		salarioNuevoLabel.setFont(fuente);
		this.formulario = new JPanel(new GridLayout(0,2));
		salarioNuevo = new JTextField();
		salarioNuevo.setFont(fuente);
		formulario.add(salarioNuevoLabel);
		formulario.add(salarioNuevo);
		this.add(formulario);
	}

	private void agregarListaDeEmpleados() {
		empleados = new JComboBox<Emple>(listener.obtenerEmpleados());
		empleados.setFont(fuente);
		this.add(empleados);
	}

}
