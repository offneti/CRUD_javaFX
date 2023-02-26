package Professors;

import Alumnos.Alumno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public interface ProfessorDAO {

    ObservableList<Alumno> professors = FXCollections.observableArrayList();

    void Add(ActionEvent event) throws Exception;

    void Delete(ActionEvent event) throws Exception;

    void Update(ActionEvent event) throws Exception;

}