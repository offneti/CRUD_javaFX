package Alumnos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public interface ModuloDAO {
    ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
    void Add(ActionEvent event) throws Exception;
    void Delete(ActionEvent event) throws Exception;
    void Update(ActionEvent event) throws Exception;
}