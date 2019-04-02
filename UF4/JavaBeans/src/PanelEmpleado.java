import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class PanelEmpleado implements Serializable, VetoableChangeListener{
	private int limiteVariacionSueldo;
	private String[] listaDeCargos = {"Junior", "SemiSenior", "Analista", "CEO"};

	public PanelEmpleado() {
		this.limiteVariacionSueldo = 10;
	}
	
	public PanelEmpleado(int limiteVariacionSueldo) {
		if (this.limiteVariacionSueldo >= 10 && this.limiteVariacionSueldo <= 50)
			this.limiteVariacionSueldo = limiteVariacionSueldo;
	}
	
	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		if (evt.getPropertyName().equals("sueldo")) {
			float variacion = Math.abs((int)evt.getNewValue()-(int)evt.getOldValue());
			float porcentajeVariacion = ((variacion/(float)(int)evt.getOldValue()) * 100);
			if (porcentajeVariacion > this.limiteVariacionSueldo) {
				throw new PropertyVetoException("Se superó el limite de variación de sueldo: " + this.limiteVariacionSueldo, evt);
			}
		} else if (evt.getPropertyName().equals("cargo")) {
			if (!Arrays.stream(this.listaDeCargos).anyMatch(((String)evt.getNewValue())::equals)) {
				throw new PropertyVetoException("El cargo introducido no existe", evt);
			}
		}
	}
	
}
