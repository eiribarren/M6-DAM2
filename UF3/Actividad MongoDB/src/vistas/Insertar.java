package vistas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bson.Document;

import objetos.Campo;
import objetos.Depart;
import objetos.ObjetoDB;
import objetos.ValorNoValidoException;

public class Insertar extends JPanel{
	
	HashMap<String, JComponent> campos;
	InsertarListener listener;

	public Insertar(InsertarListener listener, ObjetoDB objetoDB) {
		this.listener = listener;
		this.setLayout(new GridBagLayout());
		
		campos = new HashMap<String, JComponent>();
		GridBagConstraints c = new GridBagConstraints();
		int contador = 0;
		for (Campo campo : objetoDB.getCampos()) {
			c.gridx = 0;
			c.gridy = contador;
			c.weightx = 1;
			this.add(new JLabel(campo.getNombre()), c);
			c.gridx = 1;
			c.weightx = 4;
			if (!ObjetoDB.class.isAssignableFrom(campo.getClazz())) {
				JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(200, 20));
				this.add(textField, c);
				campos.put(campo.getNombre(), textField);
			} else {
				JComboBox<ObjetoDB> comboBox = new JComboBox<ObjetoDB>();
				ObjetoDB[] objetosRelacionados = new ObjetoDB[0];
				try {
					objetosRelacionados = listener.get((ObjetoDB)campo.getClazz().newInstance());
				} catch (InstantiationException | IllegalAccessException e1) {
					listener.mostrarError("Se produjo un error");
				}
				for (ObjetoDB objetoRelacionado : objetosRelacionados) {
					comboBox.addItem(objetoRelacionado);
				}
				this.add(comboBox, c);
				campos.put(campo.getNombre(), comboBox);
			}
			contador++;
		}
		
		JButton insertar = new JButton("Insertar " + objetoDB.getNombre());
		JButton atras = new JButton("Atrás");
		
		atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.atras();
			}
		});
		insertar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertar(objetoDB);
			}
		});
		c.gridy = contador;
		c.weightx = 1;
		c.gridx = 0;
		this.add(atras, c);
		c.gridx = 1;
		this.add(insertar, c);
	}
	
	/* Prepara un objetoDB para ser insertado en la base de datos */ 
	protected void insertar(ObjetoDB objetoDB) {
		/* Crea una nueva instancia del tipo del objetoDB */
		ObjetoDB nueva = objetoDB.newInstance();
		
		/* Rellena los campos */
		for (Campo campo : nueva.getCampos()) {
			try {
				if (!ObjetoDB.class.isAssignableFrom(campo.getClazz())) {
					campo.setValue(((JTextField)campos.get(campo.getNombre())).getText());
				} else {
					campo.setValue(listener.get((ObjetoDB)campo.getClazz().newInstance(), ((ObjetoDB)((JComboBox)campos.get(campo.getNombre())).getSelectedItem()).getId()));
				}
			} catch (ValorNoValidoException ex) {
				listener.mostrarError(ex.getMessage());
				return;
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				listener.mostrarError("Ocurrió un error");
			}
		}
		
		/* Le indica al controlador la colección y le pasa el objeto en 
		 * forma de documento
		 */
		listener.insertar(nueva.getNombre(), nueva.toDocument());
	}
	
	public interface InsertarListener extends MongoInterface {
		
		public void insertar(String coleccion, Document valores);
		
	}
	
}
