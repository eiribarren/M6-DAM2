import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsultasProyecto extends PanelBDOO {
	
	PanelBDOOListener listener;
	JButton consultaUno, consultaDos, consultaTres, consultaCuatro;
	JPanel botonesPanel;
	JLabel titulo;
	
	public ConsultasProyecto(PanelBDOOListener listener) throws Exception {
		if ( listener == null ) {
			throw new Exception("El listener es null");
		}
		this.listener = listener;
		this.fuente = new Font("Tahoma", Font.PLAIN, 19);
	}

	public void cargarUI() {
		if (isLoaded)
			return;
		isLoaded = true;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		agregarTitulo();
		agregarPanelDeBotones();
		agregarBotonConsultaUno();
		agregarBotonConsultaDos();
		agregarBotonConsultaTres();
		agregarBotonConsultaCuatro();
	}
	
	private void agregarPanelDeBotones() {
		botonesPanel = new JPanel();
		botonesPanel.setLayout(new GridLayout(0,1));
		this.add(botonesPanel);
	}

	private void agregarBotonConsultaCuatro() {
		consultaCuatro = new JButton();
		consultaCuatro.setText("Número de empleados en cada departamento");
		consultaCuatro.setFont(this.fuente);
		consultaCuatro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.consultaNumeroDeEmpleadosPorDepartamento();				
			}
		});
		botonesPanel.add(consultaCuatro);
	}

	private void agregarBotonConsultaTres() {
		consultaTres = new JButton();
		consultaTres.setText("Empleados cuyo director es FERNÁNDEZ");
		consultaTres.setFont(this.fuente);
		consultaTres.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.consultaEmpleadosCuyoDirectorEsFernandez();
			}
		});
		botonesPanel.add(consultaTres);
	}

	private void agregarBotonConsultaDos() {
		consultaDos = new JButton();
		consultaDos.setText("Número de empleados en VENTAS");
		consultaDos.setFont(this.fuente);
		consultaDos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.consultaNumeroEmpleadosDeVentas();
			}
		});
		botonesPanel.add(consultaDos);
	}

	private void agregarBotonConsultaUno() {
		consultaUno = new JButton();
		consultaUno.setText("Empleados del departamento 10");
		consultaUno.setFont(this.fuente);
		consultaUno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				listener.consultaEmpleadosDept10();
			}
		});
		botonesPanel.add(consultaUno);
	}

	private void agregarTitulo() {
		this.titulo = new JLabel("Selecciona una consulta: ");
		this.titulo.setAlignmentX(CENTER_ALIGNMENT);
		this.titulo.setFont(this.fuente);
		this.add(this.titulo);
	}
	
	public ConsultasProyecto(PanelBDOOListener listener, Font fuente) throws Exception {
		this(listener);
		this.fuente = fuente;
	}
}
