package objetos;

import java.util.regex.Pattern;

public class Campo<T> {
	int longitud;
	String nombre, expresionRegular;
	Class<T> clazz;
	T valorMinimo, valorMaximo, valor;
	boolean tieneLongitud, tieneRango, requerido;
	
	public Campo(Class<T> clazz, String nombre, boolean requerido) {
		this.nombre = nombre;
		this.tieneLongitud = false;
		this.tieneRango = false;
		this.requerido = requerido;
		this.expresionRegular = ""; 
		this.clazz = clazz;
	}
	
	public Campo(Class<T> clazz, String nombre, String expresionRegular, boolean requerido) {
		this(clazz, nombre, requerido);
		this.expresionRegular = expresionRegular;
	}
	
	public Campo(Class<T> clazz, String nombre, int longitud, boolean requerido) {
		this(clazz, nombre, requerido);
		this.longitud = longitud;
		this.tieneLongitud = true;
	}
	
	public Campo(Class<T> clazz, String nombre, int longitud, String expresionRegular, boolean requerido) {
		this(clazz, nombre, longitud, requerido);
		this.expresionRegular = expresionRegular;
	}
	
	public Campo(Class<T> clazz, String nombre, int longitud, T valorMinimo, T valorMaximo, boolean requerido) {
		this(clazz, nombre, longitud, requerido);
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.tieneRango = true;
	}
	
	public Campo(Class<T> clazz, String nombre, int longitud, T valorMinimo, T valorMaximo, String expresionRegular, boolean requerido) {
		this(clazz, nombre, longitud, valorMinimo, valorMaximo, requerido);
		this.expresionRegular = expresionRegular;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setValue(T valor) throws ValorNoValidoException {
		validarValor(valor);
		this.valor = valor;
	}
	
	public T getValue() {
		return this.valor;
	}
	
	public void validarValor(T valor) throws ValorNoValidoException {
		if (valor == null) {
			if (this.requerido) {
				throw new ValorNoValidoException("El campo " + this.nombre + " es requerido");
			} else {
				return;
			}
		}
		if (clazz == Integer.class) {
			try {
				Integer.parseInt((String)valor);
			} catch (Exception e) {
				throw new ValorNoValidoException("El campo " + this.nombre + " no es correcto");
			}
			if (this.tieneRango) {
				if ((int)valor > (int)this.valorMaximo || (int)valor < (int)this.valorMinimo) {
					throw new ValorNoValidoException("El campo " + this.nombre + " no está dentro del rango (" + this.valorMaximo + "-" + this.valorMaximo + ")");
				}
				if (this.tieneLongitud) {
					if (String.valueOf((int)valor).length() > this.longitud) {
						throw new ValorNoValidoException("El campo " + this.nombre + " excede la longitud máxima");
					}
				}
			}
		} else if (clazz == Float.class) {
			try {
				Float.parseFloat((String)valor);
			} catch (Exception e) {
				throw new ValorNoValidoException("El campo " + this.nombre + " no es correcto");
			}
			if (this.tieneRango) {
				if ((float)valor > (float)this.valorMaximo || (float)valor < (float)this.valorMinimo) {
					throw new ValorNoValidoException("El campo " + this.nombre + " no está dentro del rango (" + this.valorMinimo + "-" + this.valorMaximo +")");
				}
				if (this.tieneLongitud) {
					if (String.valueOf((float)valor).length() > this.longitud) {
						throw new ValorNoValidoException("El campo " + this.nombre + " excede la longitud máxima");
					}
				}
			}
		} else if (clazz == Double.class) {
			try {
				Double.parseDouble((String)valor);
			} catch (Exception e) {
				throw new ValorNoValidoException("El campo " + this.nombre + " no es correcto");
			}
			if (this.tieneRango) {
				if ((double)valor > (double)this.valorMaximo || (double)valor < (double)this.valorMinimo) {
					throw new ValorNoValidoException("El campo " + this.nombre + " no está dentro del rango (" + this.valorMinimo + "-" + this.valorMaximo + ")");
				}
				if (this.tieneLongitud) {
					if (String.valueOf((double)valor).length() > this.longitud) {
						throw new ValorNoValidoException("El campo " + this.nombre + " excede la longitud máxima");
					}
				}
			}
		} else if (clazz == String.class) {
			if (!this.expresionRegular.equals("")) {
				if (!Pattern.matches(this.expresionRegular, (String)valor)) {
					throw new ValorNoValidoException("El campo " + this.nombre + " no cumple con la expresión regular");
				}
			}
			
			if (this.tieneLongitud) {
				if ((valor.toString()).length() > this.longitud) {
					throw new ValorNoValidoException("El campo " + this.nombre + " excede la longitud máxima");
				}
			}
		} else {
			if (valor.getClass() != clazz) {
				throw new ValorNoValidoException("El campo " + this.nombre + " no es correcto");
			}
		}
		
	}
	
	public Class<T> getClazz() {
		return this.clazz;
	}
}
