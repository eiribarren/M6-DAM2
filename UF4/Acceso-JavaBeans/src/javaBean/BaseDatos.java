package javaBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

public class BaseDatos {
	private ODB db;
	
	public BaseDatos(String db) {
		this.db = ODBFactory.open(db);
	}
	
	public void insertarProducto(Producto producto) {
		this.db.store(producto);
		this.db.commit();
	}
	
	public void insertarPedido(Pedido pedido) {
		Producto producto = this.getProducto(pedido.getProducto().getIdproducto());
		pedido.setProducto(producto);
		
		this.db.store(pedido);
		this.db.commit();
	}
	
	public List<Producto> getProductos() {
		List<Producto> result = new ArrayList<Producto>();
		Objects<Producto> productos = this.db.getObjects(Producto.class);
		while ( productos.hasNext() ) {
			result.add(productos.next());
		}
		return result;
	}
	
	public List<Pedido> getPedidos() {
		List<Pedido> result = new ArrayList<Pedido>();
		Objects<Pedido> pedidos = this.db.getObjects(Pedido.class);
		while ( pedidos.hasNext() ) {
			result.add(pedidos.next());
		}
		return result;
	}
	
	public List<Venta> getVentas() {
		List<Venta> result = new ArrayList<Venta>();
		Objects<Venta> ventas = this.db.getObjects(Venta.class);
		while ( ventas.hasNext() ) {
			result.add(ventas.next());
		}
		return result;
	}
	
	public Producto getProducto(int id) {
		ICriterion criterio = Where.equal("idproducto", id);
		CriteriaQuery query = new CriteriaQuery(Producto.class, criterio);
		Objects<Producto> producto = this.db.getObjects(query);
		
		if (producto.hasNext()) {
			return producto.getFirst();
		} else {
			return null;
		}
	}
	
	public Producto getPedido(int id) {
		ICriterion criterio = Where.equal("numeroPedido", id);
		CriteriaQuery query = new CriteriaQuery(Pedido.class, criterio);
		Objects<Producto> producto = this.db.getObjects(query);
		
		if (producto.hasNext()) {
			return producto.getFirst();
		} else {
			return null;
		}
	}
	
	public void updateProducto(Producto product) {
		Producto productoDB = this.getProducto(product.getIdproducto());
		
		if (productoDB != null) {
			productoDB.setDescripcion(product.getDescripcion());
			productoDB.setStockactual(product.getStockactual());
			productoDB.setStockminimo(product.getStockminimo());
			
			this.db.store(productoDB);
			this.db.commit();
		}
	}
	
	public Venta crearVenta(int idProducto, int cantidad) {
		Venta venta = new Venta(this.getNuevoIdVenta(), new Date(System.currentTimeMillis()), cantidad, idProducto, "");
		
		this.db.store(venta);
		this.db.commit();
		
		return venta;
	}
	
	public int getNuevoIdVenta() {
		Values vals = this.db.getValues(new ValuesCriteriaQuery(Venta.class).max("numeroVenta"));
		
		return ((BigDecimal)vals.next().getByIndex(0)).intValue() + 1;
	}
	
	public Venta getVenta(int id) {
		ICriterion criterio = Where.equal("numeroVenta", id);
		CriteriaQuery query = new CriteriaQuery(Venta.class, criterio);
		Objects<Venta> ventas = this.db.getObjects(query);
		
		if (ventas.hasNext()) {
			return ventas.getFirst();
		} else {
			return null;
		}
	}
	
	public void updateVenta(Venta venta) {
		Venta ventaDB = this.getVenta(venta.getNumeroVenta());
		
		if (ventaDB != null) {
			ventaDB.setCantidad(venta.getCantidad());
			ventaDB.setFechaVenta(venta.getFechaVenta());
			ventaDB.setIdProducto(venta.getIdProducto());
			ventaDB.setObservaciones(venta.getObservaciones());
			
			this.db.store(ventaDB);
			this.db.commit();
		}
	}
	
	public int getNuevoIdProducto() {
		Values vals = this.db.getValues(new ValuesCriteriaQuery(Producto.class).max("idproducto"));
		
		return ((BigDecimal)vals.next().getByIndex(0)).intValue() + 1;
	}
	
	public int getNuevoIdPedido() {
		Values vals = this.db.getValues(new ValuesCriteriaQuery(Pedido.class).max("numeroPedido"));
		
		return ((BigDecimal)vals.next().getByIndex(0)).intValue() + 1;
	}
	
	public void close() {
		this.db.close();
	}
	
}
