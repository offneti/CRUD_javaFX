package Moduls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Modulo {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty idProfesor;
    private final StringProperty profesor;

    public Modulo()
    {
        id = new SimpleStringProperty(this, "id");
        nombre = new SimpleStringProperty(this, "nombre");
        idProfesor = new SimpleStringProperty(this, "idProfesor");
        profesor = new SimpleStringProperty(this, "profesor");
    }

    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }


    public StringProperty nombreProperty() { return nombre; }
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nuevoNombre) { nombre.set(nuevoNombre); }


    public StringProperty idProfesorProperty() { return idProfesor; }
    public String getIdProfesor() { return idProfesor.get(); }
    public void setIdProfesor(String nuevoIdProfesor) { idProfesor.set(nuevoIdProfesor); }


    public StringProperty profesorProperty() { return profesor; }
    public String getProfesor() { return profesor.get(); }
    public void setProfesor(String newProfesor) { profesor.set(newProfesor); }

}