package Alumnos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alumno {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty curso_actual;
    private final StringProperty  fecha_nacimiento;


    public Alumno()
    {
        id = new SimpleStringProperty(this, "id");
        nombre = new SimpleStringProperty(this, "nombre");
        apellido = new SimpleStringProperty(this, "apellido");
        curso_actual = new SimpleStringProperty(this, "curso_actual");
        fecha_nacimiento = new SimpleStringProperty(this, "fecha_nacimiento");

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


    public StringProperty curso_actualProperty() { return curso_actual; }
    public String getCurso() { return curso_actual.get(); }
    public void setCurso(String nuevoCurso) { curso_actual.set(nuevoCurso); }


    public StringProperty fecha_nacimientoProperty() { return fecha_nacimiento; }
    public String getFecha() { return fecha_nacimiento.get(); }
    public void setFecha(String nuevaFecha) { fecha_nacimiento.set(nuevaFecha); }
}