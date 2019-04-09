package javaBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LlenarProductos {
	
	public static void main(String[] args) {
		List<Producto> productos = Arrays.asList(new Producto[] {
													new Producto(1, "Duruss Cobalt", 10, 3, 220), 
													new Producto(2, "Varlion Avant Carbon", 5, 2, 176),
													new Producto(3, "Star Vie Pyramid R50", 20, 5, 193),
													new Producto(4, "Dunlop Titan", 8, 3, 85),
													new Producto(5, "Vision King", 7, 1, 159),
													new Producto(6, "Slazenger Reflex Pro", 5, 2, 80)
													}
												);
		
		BaseDatos db = new BaseDatos("Producto_Ped.BD");
		
		for ( Producto producto : productos ) {
			db.insertarProducto(producto);
		}
		
		db.close();

	}
	
}
