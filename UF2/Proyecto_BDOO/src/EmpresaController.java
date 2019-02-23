import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import objetos.Depart;
import objetos.Emple;

public class EmpresaController 
implements PanelBDOO.PanelBDOOListener
{

	JFrame mainFrame;
	JPanel mainPanel, statusBar;
	JButton atras, inicio;
	PanelBDOO panelActual;
	public static final String __DB__ = "EMPRESA.DB";
	public static final Font __FUENTE__ = new Font("Tahoma", Font.PLAIN, 30);
	
	public static void main(String[] args) {
		EmpresaController controller = new EmpresaController();
		try {
			controller.cargarUI();
		} catch (Exception e) {
			System.out.println("Ocurrió un error.");
			e.printStackTrace();
			System.exit(-1);
		}
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
	}

	private void prepararBotonDeAtras() {
		atras = new JButton();
		atras.setText(" ATRÁS ");
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
			mostrarInformacion("Ocurrió un error");
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
			mostrarInformacion("Ocurrió un error");
		}
	}
	
	@Override
	public void mostrarInsertarDepart() {
		try {
			mostrarPantalla(new InsertarDepart(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
		}
	}

	@Override
	public void mostrarListaDeEmpleados() {
		try {
			mostrarPantalla(new ListaDeEmpleados(this, obtenerEmpleados(), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
			e.printStackTrace();
		}
	}
	
	@Override
	public void mostrarListaDeDepartamentos() {
		try {
			mostrarPantalla(new ListaDeDepartamentos(this, obtenerDepartamentos(), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
		}
	}
	
	@Override
	public void mostrarConsultasProyecto() {
		try {
			mostrarPantalla(new ConsultasProyecto(this, __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
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
			mostrarInformacion("Ocurrió un error");
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
									 result.getFloat("comision"),
									 obtenerDepartamentoParaImportar(conexion, result.getInt("dept_no"))));
			}
			
			mostrarPantalla(new ConexionConDB(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class)));
		} catch (ClassNotFoundException e) {
			mostrarInformacion("No está instalado el driver JDBC");
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarInformacion("Ocurrió un error sql");
			e.printStackTrace();
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
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
			!(campos.get(Emple.__dir__).campo instanceof JComboBox) ||
			!(campos.get(Emple.__dept__).campo instanceof JComboBox)) 
		{
			mostrarInformacion("No se pudo insertar el Empleado");
			return;
		}
		int empNo;
		float comision;
		String apellido = ((JTextField)campos.get(Emple.__apellido__).campo).getText();
		String oficio = ((JTextField)campos.get(Emple.__oficio__).campo).getText();
		JComboBox<Depart> departamentosBox = (JComboBox<Depart>)campos.get(Emple.__dept__).campo;
		JComboBox<Emple> empleadosBox = (JComboBox<Emple>)campos.get(Emple.__dir__).campo;
		Depart dept = departamentosBox.getItemAt(departamentosBox.getSelectedIndex());
		Emple dir = empleadosBox.getItemAt(empleadosBox.getSelectedIndex());
		try {
			empNo = Integer.parseInt(((JTextField)campos.get(Emple.__empNo__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no válido para empNo");
			return;
		}
		try {
			comision = Float.parseFloat(((JTextField)campos.get(Emple.__comision__).campo).getText());
		} catch ( NumberFormatException e ) {
			mostrarInformacion("Valor no válido para comisión");
			return;
		}
		
		insertarEmpleado(new Emple(empNo, apellido, oficio, dir, new Date(System.currentTimeMillis()), comision, dept));
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
			mostrarInformacion("Valor no válido para deptNo");
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
		
		ODB odb = ODBFactory.open(__DB__);
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
			mostrarInformacion("Ocurrió un error");
			e.printStackTrace();
		} finally {
			odb.close();
		}
	}

	private boolean comprobarCamposEmpleado(Emple emple) {
		if (emple.getEmpNo() <= 0) {
			mostrarInformacion("El empNo debe ser mayor que 0");
			return false;
		}
		
		if ( emple.getDir().getEmpNo() <= 0 || !comprobarCamposDirector(emple.getDir())) {
			emple.setDir(null);
		} 
		
		if ( comprobarSiEmpleExiste(emple.getEmpNo(), emple.getApellido()) ) {
			mostrarInformacion("El empNo o apellido ya existe");
			return false;
		}
		
		if ( emple.getDept() == null ) {
			mostrarInformacion("No se ha seleccionado ningún departamento");
			return false;
		} else if ( emple.getDept().getDeptNo() <= 0 ) {
			mostrarInformacion("No se ha seleccionado ningún departamento");
			return false;
		}
		
		if ( emple.getFechaAlt() == null ) {
			emple.setFechaAlt(new Date(System.currentTimeMillis()));
		}
		
		return true;
	}

	private boolean comprobarCamposDirector(Emple dir) {
		if ( dir.getEmpNo() <= 0) {
			return false;
		} else {
			ICriterion criterio = Where.equal("empNo", dir.getEmpNo());
			CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
			ODB odb = ODBFactory.open(__DB__);
			Objects<Emple> empleados = odb.getObjects(query);
			odb.close();
			if (empleados.size() > 0) {
				Emple emple = empleados.getFirst();
				if (!(emple.getApellido().equals(dir.getApellido()))) {
					return false;
				}
			}
		}
		
		if (!comprobarCamposDirector(dir.getDir())) {
			dir.setDir(null);
		}
		
		if ( dir.getDept() == null ) {
			return false;
		} else if ( dir.getDept().getDeptNo() <= 0 ) {
			return false;
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
		
		ODB odb = ODBFactory.open(__DB__);
		try {
			odb.store(depart);
			odb.commit();
			mostrarInformacion("Departamento insertado");
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
			e.printStackTrace();
		} finally {
			odb.close();
		}
	}

	//Metodos para comprobar si ya existe un objecto en la base de datos
	private boolean comprobarSiEmpleExiste(int empNo, String apellido) {
		ICriterion criterio = new Or().add(Where.equal("empNo", empNo)).add(Where.equal("apellido", apellido));
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		ODB odb = ODBFactory.open(__DB__);
		Objects<Emple> empleados = odb.getObjects(query);
		odb.close();
		if (empleados.size() > 0)
			return true;
		return false;
	}

	private boolean comprobarSiDepartExiste(int deptNo, String dNombre) {
		ODB odb = ODBFactory.open(__DB__);
		ICriterion criterio = new Or().add(Where.equal("deptNo", deptNo)).add(Where.equal("dnombre", dNombre));
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		Objects<Depart> departamentos = odb.getObjects(query);
		odb.close();
		if (departamentos.size() > 0)
			return true;
		return false;
	}

	//Metodos para obtener una lista de los empleados
	@Override
	public Emple[] obtenerEmpleados() {
		ODB odb = ODBFactory.open(__DB__);
		Objects<Emple> emples = odb.getObjects(Emple.class);
		odb.close();
		
		return Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class);
	}
	
	@Override
	public Depart[] obtenerDepartamentos() {
		ODB odb = ODBFactory.open(__DB__);
		Objects<Depart> depts = odb.getObjects(Depart.class);
		odb.close();
		
		return Arrays.copyOf(depts.toArray(), depts.size(), Depart[].class);
	}
	
	//Metodos para realizar las consultas de la tercera parte del proyecto
	@Override
	public void consultaEmpleadosDept10() {
		ODB odb = ODBFactory.open(__DB__);
		ICriterion criterio = Where.equal("deptNo", 10);
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		Objects<Depart> departamentos = odb.getObjects(query);
		if (departamentos.size() == 0) {
			mostrarInformacion("El departamento 10 no existe");
			odb.close();
			return;
		}
		
		Depart depart = departamentos.next();
		criterio = Where.equal("dept", odb.getObjectId(depart));
		query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> emples = odb.getObjects(query);
		
		try {
			mostrarPantalla(new ListaDeEmpleados(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
		} finally {
			odb.close();
		}
	}

	@Override
	public void consultaNumeroEmpleadosDeVentas() {
		ICriterion criterio = Where.iequal("dnombre", "VENTAS");
		CriteriaQuery query = new CriteriaQuery(Depart.class, criterio);
		ODB odb = ODBFactory.open(__DB__);
		Objects<Depart> departamentos = odb.getObjects(query);
		if (departamentos.size() == 0) {
			mostrarInformacion("El departamento VENTAS no existe");
			odb.close();
			return;
		}
		
		Depart ventasDept = departamentos.getFirst();
		criterio = Where.equal("dept", odb.getObjectId(ventasDept));
		query = new CriteriaQuery(Emple.class, criterio);
		Objects<Emple> emples = odb.getObjects(query);
		mostrarInformacion("En el departamento de ventas hay " + emples.size() + " empleados");
		odb.close();
	}
	
	@Override
	public void consultaEmpleadosCuyoDirectorEsFernandez() {
		ICriterion criterio = Where.iequal("apellido", "FERNANDEZ");
		CriteriaQuery query = new CriteriaQuery(Emple.class, criterio);
		ODB odb = ODBFactory.open(__DB__);
		Objects<Emple> emples = odb.getObjects(query);
		
		if (emples.size() == 0) {
			mostrarInformacion("No hay ningún empleado llamado FERNANDEZ");
			odb.close();
			return;
		}
		
		Emple fernandez = emples.getFirst();
		
		criterio = Where.equal("dir", odb.getObjectId(fernandez));
		query = new CriteriaQuery(Emple.class, criterio);
		emples = odb.getObjects(query);
		
		if (emples.size() == 0) {
			mostrarInformacion("No hay ningún empleado cuyo director sea FERNANDEZ");
			odb.close();
			return;
		}
		
		try {
			mostrarPantalla(new ListaDeEmpleados(this, Arrays.copyOf(emples.toArray(), emples.size(), Emple[].class), __FUENTE__));
		} catch (Exception e) {
			mostrarInformacion("Ocurrió un error");
		} finally {
			odb.close();
		}
	}

	@Override
	public void consultaNumeroDeEmpleadosPorDepartamento() {
		ODB odb = ODBFactory.open(__DB__);
		Objects<Depart> departamentos = odb.getObjects(Depart.class);
		
		if (departamentos.size() == 0) {
			mostrarInformacion("No hay ningún departamento creado");
			odb.close();
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
		
		odb.close();
		
		JPanel panel = new JPanel(new GridLayout(0,1));
		JLabel titulo = new JLabel("Número de empleados por departamento");
		titulo.setFont(__FUENTE__);
		panel.add(titulo);
		panel.add(scrollPane);
		mostrarPanel(panel);
	}

	//Metodos de importacion
	public Emple obtenerDirectorParaImportar(Connection conexion, int empNo) throws SQLException {
		Emple dir = new Emple(0,"-","-",null,null,0f,null);
		
		if ( empNo != 0 ) {
			Statement sentencia = conexion.createStatement();
			ResultSet dirResult = sentencia.executeQuery("SELECT * FROM emple WHERE emp_no = " + empNo);
			dirResult.next();
			return new Emple(dirResult.getInt("emp_no"),
							 dirResult.getString("apellido"),
							 dirResult.getString("oficio"),
							 obtenerDirectorParaImportar(conexion, dirResult.getInt("dir")),
							 dirResult.getDate("fecha_alt"),
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

}
