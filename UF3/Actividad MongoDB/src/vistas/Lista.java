package vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.bson.Document;

import com.mongodb.Cursor;
import com.mongodb.client.MongoCursor;

import objetos.Campo;
import objetos.ObjetoDB;
import objetos.ValorNoValidoException;

public class Lista extends JPanel{
	HashMap<String, JComponent> campos;
	HashMap<String, ObjetoDB> comboBoxItems = new HashMap<String, ObjetoDB>();
	JComboBox<ObjetoDB> filas;
	ListaListener listener;
	
	public Lista(ListaListener listener, ObjetoDB[] objetosDB, boolean editable) {
		this.listener = listener;
		this.setLayout(new GridBagLayout());
		
		filas = new JComboBox<ObjetoDB>();
		
		for (ObjetoDB objetoDB : objetosDB) {
			filas.addItem(objetoDB);
		}
		
		campos = new HashMap<String, JComponent>();
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		ObjetoDB objetoDB = (ObjetoDB) filas.getSelectedItem();
		if (objetoDB == null) {
			listener.atras();
			listener.mostrarError("La colección no existe todavía");
			return;
		}
		this.add(filas, c);
		Dimension fieldDimension = new Dimension(200, 20);
		for (Campo campo : objetoDB.getCampos()) {
			c.gridx = 0;
			c.gridy++;
			c.weightx = 1;
			this.add(new JLabel(campo.getNombre()), c);
			c.gridx = 1;
			c.weightx = 4;
			if (!ObjetoDB.class.isAssignableFrom(campo.getClazz()) || !editable) {
				JTextField textField = new JTextField();
				textField.setEditable(editable);
				textField.setPreferredSize(fieldDimension);
				if (campo.getValue() != null) {
					textField.setText(campo.getValue().toString());
				} else {
					System.out.println(campo.getNombre());
				}
				this.add(textField, c);
				campos.put(campo.getNombre(), textField);
			} else {
				JComboBox comboBox = new JComboBox();
				comboBox.setPreferredSize(fieldDimension);
				ObjetoDB[] objetosRelacionados = new ObjetoDB[0];
				try {
					objetosRelacionados = listener.get((ObjetoDB)campo.getClazz().newInstance());
				} catch (InstantiationException | IllegalAccessException e1) {
					listener.mostrarError("Se produjo un error");
				}
				for (ObjetoDB objetoRelacionado : objetosRelacionados) {
					comboBox.addItem(objetoRelacionado);
					comboBoxItems.put(objetoRelacionado.getId(), objetoRelacionado);
					comboBox.setSelectedItem(comboBoxItems.get(((ObjetoDB)campo.getValue()).getId()));
				}
				this.add(comboBox, c);
				campos.put(campo.getNombre(), comboBox);
			}
			
		}
		
		filas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ObjetoDB objetoDB = (ObjetoDB)e.getItem();
				for (Campo campo : objetoDB.getCampos()) {
					if (!ObjetoDB.class.isAssignableFrom(campo.getClazz()) || !editable) {
						if (campo.getValue() != null) {
							((JTextField)campos.get(campo.getNombre())).setText(campo.getValue().toString());
						} else {
							((JTextField)campos.get(campo.getNombre())).setText("");
						}
					} else {
						if (campo.getValue() != null) {
							((JComboBox)campos.get(campo.getNombre())).setSelectedItem(comboBoxItems.get(((ObjetoDB)campo.getValue()).getId()));
						}
					}
				}
				repaint();
			}
		});
		
		JButton atras = new JButton("Atrás");
		
		atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.atras();
			}
		});
		c.gridy++;
		c.weightx = 1;
		c.gridx = 0;
		this.add(atras, c);
		if (editable) {
			JButton modificar = new JButton("Modificar");
			JButton borrar = new JButton("Borrar");
			
			modificar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modificar();
				}
			});
			
			borrar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ObjetoDB objetoDB = (ObjetoDB)filas.getSelectedItem();
					listener.borrar(objetoDB.getNombre(), objetoDB.getId());
					listener.atras();
				}
			});
			
			c.gridx = 1;
			this.add(modificar, c);
			c.gridx = 2;
			this.add(borrar, c);
		} else {
			JButton exportar = new JButton("Exportar");
			exportar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ObjetoDB objetoDB = (ObjetoDB)filas.getSelectedItem();
					listener.exportar(objetoDB.getId(), objetoDB.toDocument());
				}
			});
			c.gridx = 1;
			this.add(exportar, c);
		}
	}

	/* Método que prepara el documento para modificar en la base de datos */
	protected void modificar() {
		/* Obtiene el objeto seleccionado */
		ObjetoDB objetoDB = (ObjetoDB)filas.getSelectedItem();
		
		/* Rellena los campos del objeto con los valores introducidos */
		for (Campo campo : objetoDB.getCampos()) {
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
				listener.mostrarError("Se produjo un error");
			}
		}
		
		/* Le indica al controlador la colección y el id y le pasa el objeto en 
		 * forma de documento
		 */
		listener.modificar(objetoDB.getNombre(), objetoDB.getId(), objetoDB.toDocument());
		listener.atras();
	}
	
	public interface ListaListener extends MongoInterface {
		public void modificar(String coleccion, String id, Document valores);
		public void borrar(String coleccion, String id);
		public void exportar(String nombre, Document valores);
	}
}
