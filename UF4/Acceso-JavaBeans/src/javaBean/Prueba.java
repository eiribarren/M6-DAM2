package javaBean;


//import javaBean.Pedido;
//import javaBean.Producto;
public class Prueba {

	public static final String __DB__ = "Producto_Ped.BD";
	
    public static void main(String[] args) {
    	
        System.out.println("PRUEBAS" + "\n" +
        				   "=======" + "\n\n" +
        				   "PRODUCTOS");
        
        VerProductos ver = new VerProductos(30); 
        
        ver.mostrarProductos();
        
        System.out.println("\n" + "PEDIDOS");
        
        ver.mostrarPedidos();
        
        //Obtengo un producto, en este caso el que tiene el id número 2
        BaseDatos db = new BaseDatos(__DB__);
        Producto product = db.getProducto(2);
        db.close();
        
        //Compruebo que el producto existe antes de hacer nada
        if (product != null) {
        	Pedido pedido = new Pedido();
        	Venta venta = new Venta();
        	
        	//Añado ahora el producto al pedido
        	pedido.setProducto(product);
        	
        	//Añado el propertychangelistener al producto
        	product.addPropertyChangeListener(pedido);
        	product.addPropertyChangeListener(venta);
        	product.setVenta(venta);
        	
        	//Resto una cantidad al stock para que quede bajo el mínimo (5-4 < 2)
        	product.vender(4);
        	
        	db = new BaseDatos(__DB__);
        	db.updateProducto(product);
        	db.close();
        } 

        //Muestro los productos para ver como no se ha alterado el stock
        System.out.println("\n" + "PRODUCTOS");
        ver.mostrarProductos();
        
        //Muestro los productos para ver como se ha creado un pedido
        System.out.println("\n" + "PEDIDOS");
        ver.mostrarPedidos();
        
        System.out.println("\n" + "VENTAS");
        ver.mostrarVentas();
    }
}


