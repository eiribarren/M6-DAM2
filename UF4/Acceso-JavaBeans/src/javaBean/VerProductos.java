package javaBean;

import java.io.Serializable;

public class VerProductos implements Serializable {
	
	int longitudColumnas;
	
	public VerProductos() {}
	
	public VerProductos(int longitudColumnas) {
		this.longitudColumnas = longitudColumnas;
	}
	
	public void mostrarTodo() {
		mostrarProductos();
		
		mostrarPedidos();
	}

	public void mostrarPedidos() {
		mostrarColumnasPedido();
		mostrarDatosPedidos();
	}

	public void mostrarProductos() {
		mostrarColumnasProducto();
		mostrarDatosProductos();
	}

	public void mostrarDatosPedidos() {
		BaseDatos db = new BaseDatos("Producto_Ped.BD");
		for (Pedido pedido : db.getPedidos() ) {
			System.out.println("|" + rellenarTexto(String.valueOf(pedido.getNumeroPedido())) + "|"
					+ rellenarTexto(pedido.getProducto().toString()) + "|"
					+ rellenarTexto(String.valueOf(pedido.getFecha())) + "|"
					+ rellenarTexto(String.valueOf(pedido.getCantidad())) + "|");
		}
		db.close();
		System.out.println(linea(4));
	}

	public void mostrarColumnasPedido() {
		System.out.println(linea(4));
		System.out.println("|" + rellenarTexto("Numero de pedido") + "|" 
				+ rellenarTexto("Producto") + "|"
				+ rellenarTexto("Fecha") + "|"
				+ rellenarTexto("Cantidad") + "|");
		System.out.println(linea(4));
	}

	public void mostrarDatosProductos() {
		BaseDatos db = new BaseDatos("Producto_Ped.BD");
		for (Producto producto : db.getProductos() ) {
			System.out.println("|" + rellenarTexto(String.valueOf(producto.getIdproducto())) + "|"
					+ rellenarTexto(producto.getDescripcion()) + "|"
					+ rellenarTexto(String.valueOf(producto.getStockactual())) + "|"
					+ rellenarTexto(String.valueOf(producto.getStockminimo())) + "|"
					+ rellenarTexto(String.valueOf(producto.getPvp())) + "|");
		}
		db.close();
		System.out.println(linea(5));
	}

	public void mostrarColumnasProducto() {
		System.out.println(linea(5));
		System.out.println("|" + rellenarTexto("ID") + "|" 
				+ rellenarTexto("Descripcion") + "|"
				+ rellenarTexto("Stock actual") + "|"
				+ rellenarTexto("Stock mínimo") + "|"
				+ rellenarTexto("PVP") + "|");
		System.out.println(linea(5));
	}
	
	private String rellenarTexto(String string) {
	    return String.format("%-"+longitudColumnas+ "s", string);
	}
	
	private String linea(int columnas) {
		String linea = "";
		
		for (int i = 0 ; i < columnas ; i++) {
			linea += "+";
			for (int j = 0 ; j < longitudColumnas ; j++) {
				linea += "-";
			}
		}
		
		linea += "+";
		
		return linea;
	}
}
