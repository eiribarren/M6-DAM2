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
        
        //Obtengo un producto, en este caso el que tiene el id n�mero 2
        BaseDatos db = new BaseDatos(__DB__);
        Producto product = db.getProducto(2);
        db.close();
        
        //Compruebo que el producto existe antes de hacer nada
        if (product != null) {
        	Pedido pedido = new Pedido();
        	
        	//A�ado ahora el producto al pedido
        	pedido.setProducto(product);
        	
        	//A�ado el propertychangelistener al producto
        	product.addPropertyChangeListener(pedido);
        	
        	//Asigno un stock bajo el m�nimo (5-4 < 2) al producto
        	product.setStockactual(4);
        } 

        //Muestro los productos para ver como no se ha alterado el stock
        System.out.println("\n" + "PRODUCTOS");
        ver.mostrarProductos();
        
        //Muestro los productos para ver como se ha creado un pedido
        System.out.println("\n" + "PEDIDOS");
        ver.mostrarPedidos();
    }
}


