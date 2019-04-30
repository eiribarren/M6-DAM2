package javaBean;
import java.beans.*;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Producto implements Serializable {

	public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
	private String descripcion;
	private int idproducto;
	private int stockactual;
	private int stockminimo;
	private float pvp;
	
	private PropertyChangeSupport propertySupport;
	
	public Producto () {
		propertySupport = new PropertyChangeSupport (this);
    }
	
	public Producto (int idproducto, String descripcion, int stockactual, int stockminimo, float pvp) {
		propertySupport = new PropertyChangeSupport(this);
		this.idproducto = idproducto;
		this.descripcion = descripcion;
		this. stockactual = stockactual;
		this.stockminimo = stockminimo;
		this.pvp = pvp;
	}
	
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener (PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public int getStockactual() {
		return stockactual;
	}

	public void setStockactual(int nuevoValor) {
		int valorAnterior = this.stockactual;
		this.stockactual = nuevoValor;
		
		if (this.stockactual < getStockminimo()) // hay que realizar pedido
		{
			propertySupport.firePropertyChange("stockactual", valorAnterior, this.stockactual);
			this.stockactual = valorAnterior; // dejamos el stock anterior, no actualizamos
		}
	}
	
	public void vender(int cantidad) {
		int valorAnterior = this.stockactual;
		this.stockactual -= cantidad;
		
		BaseDatos db = new BaseDatos("Producto_Ped.BD");
		Venta venta = db.crearVenta(this.idproducto, cantidad);
		
		if (this.stockactual < getStockminimo()) // hay que realizar pedido
		{
			propertySupport.firePropertyChange("stockactual", valorAnterior, this.stockactual);
			this.stockactual = valorAnterior; // dejamos el stock anterior, no actualizamos
			venta.setObservaciones("Pendiente para realizar el pedido");
			db.updateVenta(venta);
		}
		
		db.close();
	}

	public int getStockminimo() {
		return stockminimo;
	}

	public void setStockminimo(int stockminimo) {
		this.stockminimo = stockminimo;
	}

	public float getPvp() {
		return pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public PropertyChangeSupport getPropertySupport() {
		return propertySupport;
	}

	public void setPropertySupport(PropertyChangeSupport propertySupport) {
		this.propertySupport = propertySupport;
	}
	
	public String toString() {
		return this.descripcion;
	}

}
