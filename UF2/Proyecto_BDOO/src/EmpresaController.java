import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import org.neodatis.odb.ClassRepresentation;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import com.sun.glass.events.WindowEvent;

import objetos.Depart;
import objetos.Emple;

public class EmpresaController 
implements PanelBDOO.PanelBDOOListener
{

	JFrame mainFrame;
	JPanel mainPanel, statusBar;
	JButton atras, inicio;
	PanelBDOO panelActual;
	public ODB odb;
	public static final String __DB__ = "EMPRESA.DB";
	public static final Font __FUENTE__ = new Font("Tahoma", Font.PLAIN, 30);
	
	public static void main(String[] args) {
		EmpresaController controller = new EmpresaController();
		try {
			controller.cargarUI();
		} catch (Exception e) {
			System.out.println("Ocurri� un error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public EmpresaController() {
		this.odb = ODBFactory.open(__DB__);
	}
	
	public void cargarUI()  {
		prepararFramePrincipal();
		prepararBotonDeInicio();
		prepararBotonDeAtras();
		mostrarMenuPrincipal();
	}
	
	//Metodos de interfaz
	private void prepararFramePrincipal() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		statusBar = new JPanel();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 500);
		mainFrame.setJMenuBar(new JMenuBar());
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(mainPanel);
		statusBar.setPreferredSize(new Dimension(mainFrame.getWidth(), 40));
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		mainFrame.add(statusBar, BorderLayout.SOUTH);
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		mainFrame.setTitle("Proyecto BDOO");
		mainFrame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
			}
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (odb != null)
					odb.close();
		        System.exit(0);
			}
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {}
			@Override
			public void windowIconified(java.awt.event.WindowEvent e) {}
			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) {}
			@Override
			public void windowActivated(java.awt.event.WindowEvent e) {}
			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) {}
		});
	}

	private void prepararBotonDeAtras() {
		atras = new JButton();
		atras.setText(" ATR�S ");
		atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (panelActual.anterior != null) {
					mostrarPantalla(panelActual.anterior);
				}
			}
		});
		mainFrame.getJMenuBar().add(atras);
	}
	
	private void prepararBotonDeInicio() {
		inicio = new JButton();
		inicio.setText(" INICIO ");
		inicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (panelActual.anterior != null) {
					mostrarMenuPrincipal();
				}
			}
		});
		mainFrame.getJMenuBar().add(inicio);
	}

	//Metodos para actualizar la pantalla
	private void mostrarMenuPrincipal() {
		try {
			mostrarPantalla(new MenuPrincipal(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		}
	}

	private void mostrarPantalla(PanelBDOO pantalla) {
		statusBar.removeAll();
		statusBar.repaint();
		mainPanel.removeAll();
		pantalla.cargarUI();
		mainPanel.add(pantalla);
		mainPanel.repaint();
		pantalla.anterior = panelActual;
		panelActual = pantalla;
		mainFrame.revalidate();
	}

	@Override
	public void mostrarInsertarEmple() {
		try {
			mostrarPantalla(new InsertarEmple(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		}
	}
	
	@Override
	public void mostrarInsertarDepart() {
		try {
			mostrarPantalla(new InsertarDepart(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		}
	}

	@Override
	public void mostrarListaDeEmpleados() {
		try {
			mostrarPantalla(new ListaDeEmpleados(this, obtenerEmpleados(), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
			e.printStackTrace();
		}
	}
	
	@Override
	public void mostrarListaDeDepartamentos() {
		try {
			mostrarPantalla(new ListaDeDepartamentos(this, obtenerDepartamentos(), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
			e.printStackTrace();
		}
	}
	
	@Override
	public void mostrarConsultasProyecto() {
		try {
			mostrarPantalla(new ConsultasProyecto(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		}
	}

	public void mostrarInformacion(String text) {
		JLabel info = new JLabel(text);
		info.setFont(__FUENTE__);
		statusBar.removeAll();
		statusBar.add(info);
		statusBar.repaint();
		mainFrame.revalidate();
	}

	@Override
	public void mostrarImportarBaseDeDatos() {
		try {
			mostrarPantalla(new ImportarBaseDeDatos(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		}
	}
	
	@Override
	public void mostrarConexionConDB(String ip, String puerto, String db, String usuario, String password) {
		Connection conexion = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conexion = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/" + db,
															   usuario, 
															   password);
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT * FROM emple ORDER BY emp_no";
			ResultSet result = sentencia.executeQuery(sql);
			List<Emple> emples = new ArrayList<Emple>();
			while(result.next()) {
				emples.add(new Emple(result.getInt("emp_no"),
									 result.getString("apellido"),
									 result.getString("oficio"),
									 obtenerDirectorParaImportar(conexion, result.getInt("dir")),
									 result.getDate("fecha_alt"),
									 result.getFloat("salario"),
									 result.getFloat("comision"),
									 obtenerDepartamentoParaImportar(conexion, result.getInt("dept_no"))));
			}
			
			mostrarPantalla(new ConexionConDB(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class)));
		} catch (ClassNotFoundException e) {
			mostrarInformacion("No est� instalado el driver JDBC");
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarInformacion("Ocurri� un error sql");
			e.printStackTrace();
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	@Override
	public void mostrarModificarSalario() {
		try {
			mostrarPantalla(new ModificarSalario(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
			e.printStackTrace();
		}
	}
	
	@Override
	public void mostrarEliminarEmpleado() {
		try {
			mostrarPantalla(new EliminarEmpleado(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
			e.printStackTrace();
		}
	}
	
	public void mostrarPanel(JPanel panel) {
		statusBar.removeAll();
		statusBar.repaint();
		mainPanel.removeAll();
		EmptyPanel ePanel = new EmptyPanel(panel);
		ePanel.anterior = panelActual;
		mainPanel.add(ePanel);
		panelActual = ePanel;
		mainPanel.repaint();
		mainFrame.revalidate();
	}

	//Metodos para obtener los datos del formulario
	@Override
	public void insertarCamposEmple(HashMap<String, Campo> campos) {
		if (!(campos.get(Emple.__empNo__).campo instanceof JTextField) ||
			!(campos.get(Emple.__apellido__).campo instanceof JTextField) ||
			!(campos.get(Emple.__comision__).campo instanceof JTextField) ||
			!(campos.get(Emple.__oficio__).campo instanceof JTextField) ||
			!(campos.get(Emple.__salario__).campo instanceof JTextField) ||
			!(campos.get(Emple.__dir__).campo instanceof JComboBox) ||
			!(campos.get(Emple.__dept__).campo instanceof JComboBox)) 
		{
			mostrarInformacion("No se pudo insertar el Empleado");
			return;
		}
		int empNo;
		float comision, salario;
		String apellido = ((JTextField)campos.get(Emple.__apellido__).campo).getText();
		String oficio = ((JTextField)campos.get(Emple.__oficio__).campo).getText();
		JComboBox<Depart> departamentosBox = (JComboBox<Depart>)campos.get(Emple.__dept__).campo;
		JComboBox<Emple> empleadosBox = (JComboBox<Emple>)campos.get(Emple.__dir__).campo;
		Depart dept = departamentosBox.getItemAt(departamentosBox.getSelectedIndex());
		Emple dir = empleadosBox.getItemAt(empleadosBox.getSelectedIndex());
		try {
			empNo = Integer.parseInt(((JTextField)campos.get(Emple.__empNo__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no v�lido para empNo");
			return;
		}
		try {
			comision = Float.parseFloat(((JTextField)campos.get(Emple.__comision__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no v�lido para comisi�n");
			return;
		}
		
		try {
			salario = Float.parseFloat(((JTextField)campos.get(Emple.__salario__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no v�lido para el salario");
			return;
		}
		
		insertarEmpleado(new Emple(empNo, apellido, oficio, dir, new Date(System.currentTimeMillis()), salario, comision, dept));
	}
	
	@Override
	public void insertarCamposDepart(HashMap<String, Campo> campos) {
		if (!(campos.get(Depart.__deptNo__).campo instanceof JTextField) ||
			!(campos.get(Depart.__dnombre__).campo instanceof JTextField) ||
			!(campos.get(Depart.__loc__).campo instanceof JTextField)) 
		{
			mostrarInformacion("No se pudo insertar el Departamento");
			return;
		}
		
		int deptNo;
		String dNombre = ((JTextField)campos.get(Depart.__dnombre__).campo).getText();
		String loc = ((JTextField)campos.get(Depart.__loc__).campo).getText();
		try {
			deptNo = Integer.parseInt(((JTextField)campos.get(Depart.__deptNo__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no v�lido para deptNo");
			return;
		}
		
		insertarDepartamento(new Depart(deptNo, dNombre, loc));
	}

	//Metodos para insertar en la base de datos
	@Override
	public void insertarEmpleado(Emple emple) {
		if (!comprobarCamposEmpleado(emple)) {
			
			return;
		};
		
		ICriterion criterio;
		CriteriaQuery query;
		try {
			if (emple.getDir() != null ) {
				criterio = Where.equal("empNo", emple.getDir().getEmpNo());
				query = new CriteriaQuery(Emple.class, criterio);
				Objects<Emple> director = odb.getObjects(query);
				if (director.size() > 0) {
					emple.setDir(director.getFirst());
				} 
			}
			
			criterio = Where.equal("deptNo", emple.getDept().getDeptNo());
			query = new CriteriaQuery(Depart.class, criterio);
			Objects<Depart> departamento = odb.getObjects(query);
			if (departamento.size() > 0) {
				emple.setDept(departamento.getFirst());
			}
			
			odb.store(emple);
			odb.commit();
			mostrarInformacion("Empleado insertado");
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
			e.printStackTrace();
		} finally {
			
		}
	}

	private boolean comprobarCamposEmpleado(Emple emple) {
		if (emple.getEmpNo() <= 0) {
			mostrarInformacion("El empNo debe ser mayor que 0");
			return false;
		}
		
		if ( emple.getDir().getEmpNo() <= 0 || !comprobarCamposDirector(emple, emple.getDir())) {
			emple.setDir(null);
		} 
		
		if ( comprobarSiEmpleExiste(emple.getEmpNo(), emple.getApellido()) ) {
			mostrarInformacion("El empNo o apellido ya existe");
			return false;
		}
		
		if ( emple.getDept() == null ) {
			mostrarInformacion("No se ha seleccionado ning�n departamento");
			return false;
		} else if ( emple.getDept().getDeptNo() <= 0 ) {
			mostrarInformacion("No se ha seleccionado ning�n departamento");
			return false;
		}
		
		if ( emple.getFechaAlt() == null ) {
			emple.setFechaAlt(new Date(System.currentTimeMillis()));
		}
		
		return true;
	}

	private boolean comprobarCamposDirector(Emple emple, Emple dir) {
		if ( dir.getEmpNo() <= 0) {
			return false;
		} else {
			ICriterion criterio = Where.equal("empNo", dir.getEmpNo());
			CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
			Objects<Emple> empleados = odb.getObjects(query);
			if (empleados.size() > 0) {
				Emple empleado = empleados.getFirst();
				if (!(empleado.getApellido().equals(dir.getApellido()))) {
					return false;
				} else {
					emple.setDir(empleado);
					return true;
				}
			}
		}
		
		if (!comprobarCamposDirector(dir, dir.getDir())) {
			dir.setDir(null);
		}
		
		if ( dir.getDept() == null ) {
			return false;
		} else if ( dir.getDept().getDeptNo() <= 0 ) {
			return false;
		} else {
			ICriterion criterio = Where.equal("deptNo", dir.getDept().getDeptNo());
			CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
			Objects<Depart> departamento = odb.getObjects(query);
			if (departamento.size() > 0) {
				dir.setDept(departamento.getFirst());
			} else {
				insertarDepartamento(dir.getDept());
			}
		}
		
		if ( dir.getFechaAlt() == null ) {
			dir.setFechaAlt(new Date(System.currentTimeMillis()));
		}
		
		return true;
	}

	@Override
	public void insertarDepartamento(Depart depart) {
		if (depart.getDeptNo() <= 0) {
			mostrarInformacion("El deptNo debe ser mayor que 0");
			
			return;
		}
		
		if ( comprobarSiDepartExiste(depart.getDeptNo(), depart.getDnombre()) ) {
			mostrarInformacion("El deptNo o dNombre ya existe");
			
			return;
		}
		
		try {
			odb.store(depart);
			odb.commit();
			mostrarInformacion("Departamento insertado");
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
			e.printStackTrace();
		} finally {
			
		}
	}

	//Metodos para comprobar si ya existe un objecto en la base de datos
	private boolean comprobarSiEmpleExiste(int empNo, String apellido) {
		ICriterion criterio = new Or().add(Where.equal("empNo", empNo)).add(Where.equal("apellido", apellido));
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> empleados = odb.getObjects(query);
		if (empleados.size() > 0)
			return true;
		return false;
	}

	private boolean comprobarSiDepartExiste(int deptNo, String dNombre) {
		ICriterion criterio = new Or().add(Where.equal("deptNo", deptNo)).add(Where.equal("dnombre", dNombre));
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		Objects<Depart> departamentos = odb.getObjects(query);
		if (departamentos.size() > 0)
			return true;
		return false;
	}

	//Metodos para obtener una lista de los empleados
	@Override
	public Emple[] obtenerEmpleados() {
		Objects<Emple> emples = odb.getObjects(Emple.class);
		
		
		return Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class);
	}
	
	@Override
	public Depart[] obtenerDepartamentos() {
		Objects<Depart> depts = odb.getObjects(Depart.class);
		
		
		return Arrays.copyOf(depts.toArray(), depts.size(), Depart[].class);
	}
	
	//Metodos para realizar las consultas de la tercera parte del proyecto
	@Override
	public void consultaEmpleadosDept10() {
		ICriterion criterio = Where.equal("deptNo", 10);
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		Objects<Depart> departamentos = odb.getObjects(query);
		if (departamentos.size() == 0) {
			mostrarInformacion("El departamento 10 no existe");
			
			return;
		}
		
		Depart depart = departamentos.next();
		criterio = Where.equal("dept", odb.getObjectId(depart));
		query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> emples = odb.getObjects(query);
		
		try {
			mostrarPantalla(new ListaDeEmpleados(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		} finally {
			
		}
	}

	@Override
	public void consultaNumeroEmpleadosDeVentas() {
		ICriterion criterio = Where.iequal("dnombre", "VENTAS");
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		Objects<Depart> departamentos = odb.getObjects(query);
		if (departamentos.size() == 0) {
			mostrarInformacion("El departamento VENTAS no existe");
			
			return;
		}
		
		Depart ventasDept = departamentos.getFirst();
		criterio = Where.equal("dept", odb.getObjectId(ventasDept));
		query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> emples = odb.getObjects(query);
		mostrarInformacion("En el departamento de ventas hay " + emples.size() + " empleados");
		
	}
	
	@Override
	public void consultaEmpleadosCuyoDirectorEsFernandez() {
		ICriterion criterio = new Or().add(Where.iequal("apellido", "FERNANDEZ")).add(Where.iequal("apellido", "FERNÁNDEZ"));
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> emples = odb.getObjects(query);
		
		if (emples.size() == 0) {
			mostrarInformacion("No hay ning�n empleado llamado FERNANDEZ");
			
			return;
		}
		
		Emple fernandez = emples.getFirst();
		
		criterio = Where.equal("dir", odb.getObjectId(fernandez));
		query = new CriteriaQuery(Emple.class, criterio);
		emples = odb.getObjects(query);
		
		if (emples.size() == 0) {
			mostrarInformacion("No hay ning�n empleado cuyo director sea FERNANDEZ");
			
			return;
		}
		
		try {
			mostrarPantalla(new ListaDeEmpleados(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurri� un error");
		} finally {
			
		}
	}

	@Override
	public void consultaNumeroDeEmpleadosPorDepartamento() {
		Objects<Depart> departamentos = odb.getObjects(Depart.class);
		
		if (departamentos.size() == 0) {
			mostrarInformacion("No hay ning�n departamento creado");
			
			return;
		}
		
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(0,1));
		JScrollPane scrollPane = new JScrollPane(info, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		while (departamentos.hasNext()) {
			Depart depart = departamentos.next();
			ICriterion criterio = Where.equal("dept", odb.getObjectId(depart));
			CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
			BigInteger numeroEmpleados = odb.count(query);
			JLabel texto = new JLabel(depart.getDnombre() + ": " + numeroEmpleados);
			texto.setFont(__FUENTE__);
			info.add(texto);
		}
		
		JPanel panel = new JPanel(new GridLayout(0,1));
		JLabel titulo = new JLabel("N�mero de empleados por departamento");
		titulo.setFont(__FUENTE__);
		panel.add(titulo);
		panel.add(scrollPane);
		mostrarPanel(panel);
	}

	//Metodos de importacion
	public Emple obtenerDirectorParaImportar(Connection conexion, int empNo) throws SQLException {
		Emple dir = new Emple(0,"-","-",null,null, 0f,0f,null);
		
		if ( empNo != 0 ) {
			Statement sentencia = conexion.createStatement();
			ResultSet dirResult = sentencia.executeQuery("SELECT * FROM emple WHERE emp_no = " + empNo);
			dirResult.next();
			return new Emple(dirResult.getInt("emp_no"),
							 dirResult.getString("apellido"),
							 dirResult.getString("oficio"),
							 obtenerDirectorParaImportar(conexion, dirResult.getInt("dir")),
							 dirResult.getDate("fecha_alt"),
							 dirResult.getFloat("salario"),
							 dirResult.getFloat("comision"),
							 obtenerDepartamentoParaImportar(conexion, dirResult.getInt("dept_no")));
		}
		
		return dir;
	}
	
	public Depart obtenerDepartamentoParaImportar(Connection conexion, int deptNo) throws SQLException{
		Depart depart = new Depart(0, "-", "-");
		
		if ( deptNo != 0 ) {
			Statement sentencia = conexion.createStatement();
			ResultSet deptResult = sentencia.executeQuery("SELECT * FROM depart WHERE dept_no = " + deptNo);
			deptResult.next();
			return new Depart(deptResult.getInt("dept_no"),
							  deptResult.getString("dnombre"),
							  deptResult.getString("loc"));
		}
		
		return depart;
	}

	@Override
	public void modificarSalario(String apellido, String salario) {
		float salarioFloat;
		try {
			salarioFloat = Float.parseFloat(salario);
		} catch (NumberFormatException e) {
			mostrarInformacion("Valor no válido para salario");
			return;
		}
		
		ICriterion criterio = Where.iequal("apellido", apellido);
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> result = odb.getObjects(query);
		
		Emple emple = null;
		if (result.size() > 0) {
			emple = result.getFirst();
			emple.setSalario(salarioFloat);
		} else {
			mostrarInformacion("No se encontró el empleado");
			return;
		}
		
		odb.store(emple);
		mostrarInformacion("Se modificó el salario de " + apellido);
	}

	@Override
	public void eliminarEmpleado(String apellido) {
		ICriterion criterio = Where.iequal("apellido", apellido);
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> result = odb.getObjects(query);
		
		Emple emple = null;
		if (result.size() > 0) {
			emple = result.getFirst();
		} else {
			mostrarInformacion("No se encontró el empleado");
			return;
		}
		
		odb.delete(emple);
		mostrarInformacion("Se eliminó el empleado " + apellido);
	}

}
