package objetos;

public class Campo {
	int longitud, valorMinimo, valorMaximo;
	String nombre, valor, expresionRegular;
	boolean esNumero, tieneLongitud, tieneRango;
	
	public Campo(boolean esNumero, String nombre) {
		this.esNumero = esNumero;
		this.nombre = nombre;
		this.tieneLongitud = false;
		this.tieneRango = false;
	}
	
	public Campo(boolean esNumero, String nombre, String expresionRegular) {
		this(esNumero, nombre);
		this.expresionRegular = expresionRegular;
	}
	
	public Campo(boolean esNumero, String nombre, int longitud) {
		this(esNumero, nombre);
		this.longitud = longitud;
		this.tieneLongitud = true;
	}
	
	public Campo(boolean esNumero, String nombre, int longitud, String expresionRegular) {
		this(esNumero, nombre, longitud);
		this.expresionRegular = expresionRegular;
	}
	
	public Campo(boolean esNumero, String nombre, int longitud, int valorMinimo, int valorMaximo ) {
		this(esNumero, nombre, longitud);
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.tieneRango = true;
	}
	
	public Campo(boolean esNumero, String nombre, int longitud, int valorMinimo, int valorMaximo, String expresionRegular) {
		this(esNumero, nombre, longitud, valorMinimo, valorMaximo);
		this.expresionRegular = expresionRegular;
	}
	
	public void setValue(String valor) {
		if (this.validarValor(valor)) {
			this.valor = valor;
		}
	}
	
	public String getValue() {
		return this.valor;
	}
	
	private boolean validarValor(String valor) {
		if (esNumero) {
			int intValor;
			try {
				intValor = Integer.parseInt(valor);
			} catch (NumberFormatException e) {
				return false;
			}
			if (this.tieneRango) {
				if (intValor > this.valorMaximo || intValor < this.valorMinimo) {
					return false;
				}
			}
		}
		
		if (this.tieneLongitud) {
			if (valor.length() > this.longitud) {
				return false;
			}
		}
		
		return true;
	}
}
