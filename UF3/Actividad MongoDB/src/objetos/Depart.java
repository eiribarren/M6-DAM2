package objetos;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Depart implements ObjetoDB{
	protected String id;
	protected String nombre = "Depart";
	protected Campo[] campos = {new Campo<String>(String.class, "Nombre", 10, true), 
										  new Campo<String>(String.class, "Localización", 20, false)};

	public Depart() {
		
	}
	
	public Depart(String nombre, String localizacion) throws ValorNoValidoException{
		campos[0].setValue(nombre);
		campos[1].setValue(localizacion);
	}
	
	public void setId(String id) {
		if (this.id == null) {
			this.id = id;
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public ObjetoDB newInstance() {
		return new Depart();
	}
	
	public Campo[] getCampos() {
		return this.campos;
	}
	
	public String getClave() {
		return (String)campos[0].getNombre();
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}
	
	public String toString() {
		if (campos[0].getValue() != null) {
			return campos[0].getValue().toString();
		}
		return this.getNombre();
	}
	
	public Document toDocument() {
		Document document = new Document();
		if (this.id != null && this.id != "") {
			document.put("_id", new ObjectId(this.id));
		}
		for (Campo campo : campos) {
			if (!ObjetoDB.class.isAssignableFrom(campo.getClazz())) {
				document.put(campo.getNombre(), campo.getValue());
			} else {
				document.put(campo.getNombre(), ((ObjetoDB)campo.getValue()).toDocument());
			}
		}
		return document;
	}
	
	public void llenar(Document doc) throws InstantiationException, IllegalAccessException, ValorNoValidoException{
		this.id = doc.get("_id").toString();
		for (Campo campo : this.campos) {
			if (!ObjetoDB.class.isAssignableFrom(campo.getClazz())) {
				campo.setValue(doc.get(campo.getNombre()));
			} else {
				ObjetoDB objetoRelacionado;
				objetoRelacionado = (ObjetoDB)campo.getClazz().newInstance();
				objetoRelacionado.llenar((Document)doc.get(campo.getNombre()));
			}
		}
	}
}
