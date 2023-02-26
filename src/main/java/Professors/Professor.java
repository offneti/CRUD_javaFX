package Professors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Professor {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty apellido;

    public Professor()
    {
        id = new SimpleStringProperty(this, "id");
        nombre = new SimpleStringProperty(this, "nombre");
        apellido = new SimpleStringProperty(this, "apellido");
    }

    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }

    public StringProperty nombreProperty() { return nombre; }
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nuevoNombre) { nombre.set(nuevoNombre); }


    public StringProperty apellidoProperty() { return apellido; }
    public String getApellido() { return apellido.get(); }
    public void setApellido(String nuevoApellido) { apellido.set(nuevoApellido); }

}