package javaBean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable, PropertyChangeListener {
	int numeroVenta;
	Date fechaVenta;
	int cantidad;
	int idProducto;
	String observaciones;
	
	public Venta() {}

	public Venta(int numeroVenta, Date fechaVenta, int cantidad, int idProducto, String observaciones) {
		super();
		this.numeroVenta = numeroVenta;
		this.fechaVenta = fechaVenta;
		this.cantidad = cantidad;
		this.idProducto = idProducto;
		this.observaciones = observaciones;
	}

	public int getNumeroVenta() {
		return numeroVenta;
	}

	public void setNumeroVenta(int numeroVenta) {
		this.numeroVenta = numeroVenta;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("stockactual")) {
	    	BaseDatos db = new BaseDatos("Producto_Ped.BD");
	    	
	    	Venta venta = ((Producto)evt.getSource()).getVenta();
	    	venta.setObservaciones("Pendiente de pedido por falta de stock");
	    	db.updateVenta(venta);
	    	
	    	db.close();
    	}
	}

}
