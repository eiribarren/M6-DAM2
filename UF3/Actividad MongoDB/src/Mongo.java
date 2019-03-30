import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Mongo {
	JFrame mainFrame;
	JPanel mainPanel, desplegableTablas, botones;
	JButton insertar, mostrar, borrar, modificar;
	JComboBox tablasComboBox;

	public static void main (String[] args) {
		new Mongo();
		String user = "mongoUser";
		String password = "austria";
		String host = "192.168.95.102";
		MongoClient cliente = new MongoClient(host);
		MongoCredential credential;
		credential = MongoCredential.createCredential(user, "nueva", password.toCharArray());
		MongoDatabase db = cliente.getDatabase ("nueva");
		MongoCollection <Document> empleados = db.getCollection("emple");
		MongoCursor<Document> cursor = empleados.find().iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			System.out.println(doc.toJson());
		}
		MongoCollection <Document> departamentos = db.getCollection("depart");
		cursor = departamentos.find().iterator();
		cursor.close();
	}
	
	public Mongo() {
		mostrarInterfaz();
	}

	private void mostrarInterfaz() {
		mainFrame = new JFrame();
		botones = new JPanel();
		mainPanel = new JPanel();
		desplegableTablas = new JPanel();
		insertar = new JButton("Insertar");
		mostrar = new JButton("Mostrar");
		borrar = new JButton("Borrar");
		modificar = new JButton("modificar");
		tablasComboBox = new JComboBox();
		tablasComboBox.addItem("emple");
		tablasComboBox.addItem("depart");
		botones.add(insertar);
		botones.add(mostrar);
		botones.add(borrar);
		botones.add(modificar);
		mainPanel.setLayout(new GridLayout(2,1));
		desplegableTablas.add(tablasComboBox);
		mainPanel.add(desplegableTablas);
		mainPanel.add(botones);
		mainFrame.getContentPane().add(mainPanel);
		mainFrame.setVisible(true);
		mainFrame.pack();
	}
}
