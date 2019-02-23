import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class Campo {
	public JLabel etiqueta;
	public JComponent campo;
	
	public Campo(String textoEtiqueta, JComponent campo) {
		etiqueta = new JLabel(textoEtiqueta);
		this.campo = campo;
	}

}
