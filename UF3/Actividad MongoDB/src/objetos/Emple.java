package objetos;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Emple implements ObjetoDB{
	protected String id;
	protected String nombre = "Emple";
	protected Campo[] campos = {
			new Campo<String>(String.class, "Apellido", 20, true),
			new Campo<String>(String.class, "Oficio", 10, true),
			new Campo<Float>(Float.class, "Salario", false),
			new Campo<Float>(Float.class, "Comision", false),
			new Campo<String>(String.class, "Email", 30, "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$", true),
			new Campo<Depart>(Depart.class, "Depart", true)
	};
	
	public Emple() {
		
	}
	
	public Emple(String apellido, String oficio, Float salario, Float comision, String email) throws ValorNoValidoException {
		campos[0].setValue(apellido);
		campos[1].setValue(oficio);
		campos[2].setValue(salario);
		campos[3].setValue(comision);
		campos[4].setValue(email);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public ObjetoDB newInstance() {
		return new Emple();
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
