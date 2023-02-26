package Moduls;

import Alumnos.AlumneController;
import Alumnos.Alumno;
import Alumnos.ModuloDAO;
import Professors.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Professors.ProfessorController;

public class ModuloController implements Initializable {

    Connection con;
    PreparedStatement pst;
    int myIndex;
    String id;

    public void Connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private TableColumn<Professor, String> columna_nom;

    @FXML
    private TableColumn<Professor, String> columna_cognom;

    @FXML
    private TableColumn<Professor, String> id_profesores;

    @FXML
    private TableColumn<Modulo, String> columna_idProfesor;

    @FXML
    private TableColumn<Modulo, String> columna_nom_modul;

    @FXML
    private TableColumn<Modulo, String> columna_professor;

    @FXML
    private TableView<Modulo> table;

    @FXML
    private TextField txt_modul;

    @FXML
    private TextField txt_idProfesor;

    @FXML
    void tableProfessors(){
        ProfessorController ps = new ProfessorController();
        ps.Connect();
        ps.table();
    }
    @FXML
    void Add(ActionEvent event) throws SQLException, ParseException {
        String nombre, idProfesor;
        Connect();
        nombre = txt_modul.getText();
        idProfesor = txt_idProfesor.getText();
        try {
            pst = con.prepareStatement("insert into modulos(nombre, idProfesor)values(?,?)");
            pst.setString(1, nombre);
            pst.setString(2, idProfesor);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK");
            alert.setHeaderText("Modulo inserido correctamente!");
            alert.showAndWait();
            table();
            txt_modul.setText("");
            txt_idProfesor.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Delete(ActionEvent event) throws SQLException {
        myIndex = table.getSelectionModel().getSelectedIndex();
        String idStr = table.getItems().get(myIndex).getId();
        id = String.valueOf(idStr);
        try {
            pst = con.prepareStatement("delete from modulos where id = ? ");
            pst.setString(1, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK");
            alert.setHeaderText("Modulo borrado correctamente!");
            alert.showAndWait();
            table();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Update(ActionEvent event) {
        String nombre, idProfesor;
        Connect();
        nombre = txt_modul.getText();
        idProfesor = txt_idProfesor.getText();
        myIndex = table.getSelectionModel().getSelectedIndex();
        String id = String.valueOf(table.getItems().get(myIndex).getId());
        try {
            pst = con.prepareStatement("update modulos set nombre = ?, idProfesor = ?  where id = ? ");
            pst.setString(1, nombre);
            pst.setString(2, idProfesor);
            pst.setString(3, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK");
            alert.setHeaderText("Modulo actualizado correctamente!");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void table() {
        Connect();
        ObservableList<Modulo> modulos = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT m.id, m.nombre, m.idProfesor, p.nombre as profesor  FROM modulos m inner join profesores p on idProfesor = p.id;");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                    Modulo st = new Modulo();
                    st.setId(rs.getString("id"));;
                    st.setNombre(rs.getString("nombre"));
                    st.setIdProfesor(rs.getString("idProfesor"));
                    st.setProfesor(rs.getString("profesor"));
                    modulos.add(st);
                }
            }
            table.setItems(modulos);
            columna_nom_modul.setCellValueFactory(f -> f.getValue().nombreProperty());
            columna_professor.setCellValueFactory(f -> f.getValue().profesorProperty());
            columna_idProfesor.setCellValueFactory(f -> f.getValue().idProfesorProperty());

        } catch (SQLException ex) {
            Logger.getLogger(ModuloController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory(tv -> {
            TableRow<Modulo> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    txt_modul.setText(table.getItems().get(myIndex).getNombre());
                    txt_idProfesor.setText(table.getItems().get(myIndex).getIdProfesor());
                }
            });
            return myRow;
        });
    }
    public void Exit(ActionEvent actionEvent) {
        Stage stageActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageActual.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connect();
        table();
    }
}