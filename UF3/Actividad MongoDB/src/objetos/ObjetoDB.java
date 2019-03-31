package objetos;

import org.bson.Document;

public interface ObjetoDB {
	Campo[] campos = {};
	String nombre = "";
	
	public void setId(String id);
	
	public Campo[] getCampos();
	
	public String getId();
	
	public String getNombre();
	
	public String getClave();
	
	public ObjetoDB newInstance();
	
	public Document toDocument();
	
	public void llenar(Document doc) throws InstantiationException, IllegalAccessException, ValorNoValidoException;
}
