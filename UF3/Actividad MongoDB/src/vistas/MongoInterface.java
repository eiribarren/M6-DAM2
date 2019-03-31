package vistas;

import org.bson.Document;

import objetos.ObjetoDB;

public interface MongoInterface {

	public void mostrarError(String error);
	public void atras();
	public ObjetoDB[] get(ObjetoDB objetoDB);
	public ObjetoDB get(ObjetoDB objetoDB, String id);
	
}
