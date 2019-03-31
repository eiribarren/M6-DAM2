import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import objetos.*;
import vistas.*;
import vistas.Insertar.InsertarListener;
import vistas.Lista.ListaListener;

public class Mongo implements InsertarListener, ListaListener {
	JFrame mainFrame;
	JPanel mainPanel, body, desplegableobjetoDBs, botones, footer;
	JButton insertar, mostrar, borrar, modificar;
	JTextField hostField, userField, passwordField;
	JLabel hostLabel, userLabel, passwordLabel, error;
	JComboBox objetoDBsComboBox;
	MongoCredential credential;
	MongoClient cliente;

	public static void main (String[] args) {
		new Mongo();
	}
	
	public Mongo() {
		mostrarInterfaz();
	}

	// Métodos de interfaz
	protected void mostrarInterfaz() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		
		body = new JPanel();
		body.setPreferredSize(new Dimension(500, 420));
		footer = new JPanel();
		error = new JLabel();
		footer.add(error);
		JScrollPane footerScroll = new JScrollPane(footer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		footerScroll.setPreferredSize(new Dimension(470, 50));
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setPreferredSize(new Dimension(500,500));
		mainPanel.add(body);
		mainPanel.add(footerScroll);
		mainFrame.getContentPane().add(mainPanel);
		
		mainFrame.setVisible(true);
		
		mostrarFormularioConexionBaseDeDatos();
	}

	protected void mostrarFormularioConexionBaseDeDatos() {
		JPanel formulario = new JPanel();
		Dimension fieldDimension = new Dimension(200, 20);
		hostLabel = new JLabel("IP");
		hostField = new JTextField();
		hostField.setPreferredSize(fieldDimension);
		
		formulario.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		formulario.add(hostLabel, c);
		c.gridx = 1;
		formulario.add(hostField, c);
		
		JButton enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String host = hostField.getText();
				if (conectarBaseDeDatos(host)) {
					mostrarMenuPrincipal();
				}
			}
		});
		
		c.gridy = 1;
		c.gridx = 0;
		formulario.add(enviar, c);
		
		body.add(formulario);
		
		mainFrame.pack();
	}
	
	protected void mostrarMenuPrincipal() {
		body.removeAll();
		body.repaint();
		JPanel panel = new JPanel();
		
		botones = new JPanel();
		botones.setLayout(new GridLayout(2,2));
		desplegableobjetoDBs = new JPanel();
		insertar = new JButton("Insertar");
		mostrar = new JButton("Mostrar");
		borrar = new JButton("Borrar");
		modificar = new JButton("modificar");
		
		insertar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String objetoDB = (String)objetoDBsComboBox.getSelectedItem();
				if (objetoDB.equals("emple")) {
					mostrarFormularioInsertar(new Emple());
				} else {
					mostrarFormularioInsertar(new Depart());
				}
			}			
		});
		
		mostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String objetoDB = (String)objetoDBsComboBox.getSelectedItem();
				if (objetoDB.equals("emple")) {
					mostrarLista(new Emple(), false);
				} else {
					mostrarLista(new Depart(), false);
				}
				
			}			
		});
		
		modificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String objetoDB = (String)objetoDBsComboBox.getSelectedItem();
				if (objetoDB.equals("emple")) {
					mostrarLista(new Emple(), true);
				} else {
					mostrarLista(new Depart(), true);
				}
			}			
		});
		
		objetoDBsComboBox = new JComboBox();
		objetoDBsComboBox.addItem("emple");
		objetoDBsComboBox.addItem("depart");
		botones.add(insertar);
		botones.add(mostrar);
		botones.add(modificar);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		desplegableobjetoDBs.add(objetoDBsComboBox);
		panel.add(desplegableobjetoDBs, c);
		
		c.gridy = 1;
		panel.add(botones, c);
		body.add(panel);
		mainFrame.pack();
	}

	protected void mostrarLista(ObjetoDB objetoDB, boolean editable) {
		body.removeAll();
		body.repaint();
		body.add(new Lista(this, get(objetoDB), editable));
		mainFrame.pack();
	}

	protected void mostrarFormularioInsertar(ObjetoDB objetoDB) {
		body.removeAll();
		body.repaint();
		body.add(new Insertar(this, objetoDB));
		mainFrame.pack();
	}

	public void mostrarError(String error) {
		this.error.setText(error);
	}

	@Override
	public void atras() {
		mostrarMenuPrincipal();
	}
	
	/* +================================+ *
	 * |Métodos relacionados con MongoDB| *
	 * +================================+ */
	
	public boolean conectarBaseDeDatos(String host) {
		try {
			cliente = new MongoClient(host);
	
			/* Obtengo la lista de bases de datos para comprobar que
			 * se conectó correctamente
			 */
			MongoIterable<String> dbs = cliente.listDatabaseNames();
			
			/* Por algún motivo si no itero sobre el resultado el 
			 * programa continúa aunque haya algún error
			 */
			for (String db : dbs) {
			}
		} catch (Exception e) {
			mostrarError("No se pudo conectar al servidor");
			return false;
		} 
		
		return true;
	}
	
	/* Obtiene una entrada de la base de datos.
	 * Se debe indicar la colección utilizando el tipo ObjetoDB y el id.
	 */
	public ObjetoDB get(ObjetoDB objetoDB, String id) {
		MongoDatabase db = cliente.getDatabase("actividadMongo");
		MongoCollection collection = db.getCollection(objetoDB.getNombre());
		Document doc = (Document)collection.find(eq("_id", new ObjectId(id))).first();
		objetoDB.setId(id);
		/* Iteramos sobre los campos del objeto */
		llenarCampos(objetoDB, doc);
		return objetoDB;
	}
	
	/* Obtiene todas las entradas dentro de una colección.
	 * Se debe indicar la colección utilizando el tipo ObjetoDB
	 */
	public ObjetoDB[] get(ObjetoDB objetoDB) {
		MongoDatabase db = cliente.getDatabase("actividadMongo");
		MongoCollection collection = db.getCollection(objetoDB.getNombre());
		MongoCursor<Document> cursor = collection.find().iterator();
		ArrayList<ObjetoDB> objs = new ArrayList<ObjetoDB>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ObjetoDB obj = objetoDB.newInstance();
			llenarCampos(obj, doc);
			obj.setId(doc.get("_id").toString());
			objs.add(obj);
		}
		
		return objs.toArray(new ObjetoDB[objs.size()]);
	}
	
	private void llenarCampos(ObjetoDB objetoDB, Document doc) {
		for (Campo campo : objetoDB.getCampos()) {
			try {
				if (!ObjetoDB.class.isAssignableFrom(campo.getClazz())) {
					/* Asignamos el valor obtenido del documento al campo del
					 * objeto */
					campo.setValue(doc.get(campo.getNombre()));
				} else {
					/* Creamos una nueva instancia del tipo de la objetoDB
					 * pasada como argumento.
					 */
					ObjetoDB objetoRelacionado = (ObjetoDB)campo.getClazz().newInstance();
					
					/* Rellenamos los campos del objeto con el documento 
					 * obtenido.
					 */
					objetoRelacionado.llenar((Document)doc.get(campo.getNombre()));
					/* Asignamos el objeto relacionado al campo del
					 * objeto */
					campo.setValue(objetoRelacionado);
				}
			} catch (ValorNoValidoException e) {
				mostrarError(e.getMessage());
				continue;
			} catch (InstantiationException | IllegalAccessException e) {
				mostrarError("Se produjo un error");
			}
		}
	}
	
	/* Inserta un documento en la coleccion indicada 
	 * Para más detalles ver vistas.Insertar.insertar(ObjetoDB objetoDB)*/
	@Override
	public void insertar(String coleccion, Document valores) {
		MongoDatabase db = cliente.getDatabase("actividadMongo");
		MongoCollection collection = db.getCollection(coleccion);
		collection.insertOne(valores);
		mostrarError(coleccion + " insertado");
	}
	
	/* Modifica un documento con el id indicado en la coleccion indicada 
	 * Para más detalles ver vistas.Lista.modificar() */
	@Override
	public void modificar(String coleccion, String id, Document valores) {
		MongoDatabase db = cliente.getDatabase("actividadMongo");
		MongoCollection collection = db.getCollection(coleccion);
		collection.replaceOne(eq("_id",new ObjectId(id)), valores);
		mostrarError(coleccion + " modificado");
	}

	/* Borra un documento con el id indicado en la coleccion indicada */
	@Override
	public void borrar(String coleccion, String id) {
		MongoDatabase db = cliente.getDatabase("actividadMongo");
		MongoCollection collection = db.getCollection(coleccion);
		collection.deleteOne(eq("_id",new ObjectId(id)));
		mostrarError(coleccion + " eliminado");
	}

	/* Exporta el documento en un archivo con el nombre indicado */
	public void exportar(String nombre, Document valores) {
		/* Crea una carpeta en la home del usuario */
		File file = new File(System.getProperty("user.home") + File.separator + "ActividadMongo");
		if (!file.exists()) {
			file.mkdir();
		}
		
		/* Escribimos el fichero .json en la carpeta */
		file = new File(file.getAbsolutePath() + File.separator + nombre + ".json");
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(file));
			
			/* Convertimos el documento en un json y lo escribimos en el 
			 * fichero */
			fw.write(valores.toJson());
			
			fw.close();
			
			mostrarError("Se exportó el archivo en " + file.getAbsolutePath());
		} catch (IOException e) {
			mostrarError("No se pudo exportar el archivo");
		}
	}
}
