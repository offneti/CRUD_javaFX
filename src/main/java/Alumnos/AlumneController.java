package Alumnos;

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

public class AlumneController implements Initializable {
    Connection con;
    PreparedStatement pst;
    int myIndex;
    String idAlumno;

    public void Connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private TableColumn<Alumno, String> columna_cognom;

    @FXML
    private TableColumn<Alumno, String> columna_curs;

    @FXML
    private TableColumn<Alumno, String> columna_data;

    @FXML
    private TableColumn<Alumno, String> columna_nom;

    @FXML
    private TableColumn<Alumno, String> columna_id;

    @FXML
    private TableView<Alumno> table;

    @FXML
    private TextField txt_cognom;

    @FXML
    private TextField txt_curs;

    @FXML
    private TextField txt_data;

    @FXML
    private TextField txt_nom;
    @FXML
    private Button btn_crear;
    @FXML
    private Button btn_actualitzar;
    @FXML
    private Button btn_eliminar;

    @FXML
    void Add(ActionEvent event) throws SQLException, ParseException {
        String nombre, apellido, curso_actual, fecha_nacimiento;
        Connect();
        nombre = txt_nom.getText();
        apellido = txt_cognom.getText();
        curso_actual = txt_curs.getText();
        fecha_nacimiento = txt_data.getText();
        try {
            pst = con.prepareStatement("insert into alumnos(nombre, apellido, curso_actual, fecha_nacimiento)values(?,?,?,?)");
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, curso_actual);
            pst.setString(4, fecha_nacimiento);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK!");
            alert.setHeaderText("Alumno registrado correctamente!");
            alert.showAndWait();
            table();
            txt_nom.setText("");
            txt_cognom.setText("");
            txt_curs.setText("");
            txt_data.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Delete(ActionEvent event) throws SQLException {
        myIndex = table.getSelectionModel().getSelectedIndex();
        String idStr = table.getItems().get(myIndex).getId();
        String idStr1 = String.valueOf(idStr);
        int idAlumno = Integer.parseInt(idStr1);

        try {
            pst = con.prepareStatement("delete from alumnos where id = ? ");
            pst.setInt(1, idAlumno);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK!");
            alert.setHeaderText("Alumno borrado correctamente!");
            alert.showAndWait();
            table();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Update(ActionEvent event) {
        String nombre, apellido, curso_actual, fecha_nacimiento;
        Connect();
        nombre = txt_nom.getText();
        apellido = txt_cognom.getText();
        curso_actual = txt_curs.getText();
        fecha_nacimiento = txt_data.getText();
        myIndex = table.getSelectionModel().getSelectedIndex();
        idAlumno = String.valueOf(table.getItems().get(myIndex).getId());
        try {
            pst = con.prepareStatement("update alumnos set nombre = ?,apellido = ? ,curso_actual = ?, fecha_nacimiento = ? where id = ? ");
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, curso_actual);
            pst.setString(4, fecha_nacimiento);
            pst.setString(5, idAlumno);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK!");
            alert.setHeaderText("Alumno actualizado correctamente");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void table() {
        Connect();
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("select * from alumnos");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                    Alumno st = new Alumno();
                    st.setId(rs.getString("id"));;
                    st.setNombre(rs.getString("nombre"));
                    st.setApellido(rs.getString("apellido"));
                    st.setCurso(rs.getString("curso_actual"));
                    st.setFecha(rs.getString("fecha_nacimiento"));
                    alumnos.add(st);
                }
            }
            table.setItems(alumnos);
            columna_nom.setCellValueFactory(f -> f.getValue().nombreProperty());
            columna_cognom.setCellValueFactory(f -> f.getValue().apellidoProperty());
            columna_curs.setCellValueFactory(f -> f.getValue().curso_actualProperty());
            columna_data.setCellValueFactory(f -> f.getValue().fecha_nacimientoProperty());
            columna_id.setCellValueFactory(f -> f.getValue().idProperty());


        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory(tv -> {
            TableRow<Alumno> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    //idAlumno = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txt_nom.setText(table.getItems().get(myIndex).getNombre());
                    txt_cognom.setText(table.getItems().get(myIndex).getApellido());
                    txt_curs.setText(table.getItems().get(myIndex).getCurso());
                    txt_data.setText(table.getItems().get(myIndex).getFecha());
                }
            });
            return myRow;
        });
    }
    public void initialize(URL url, ResourceBundle rb) {
        Connect();
        table();
    }
    public void Exit(ActionEvent actionEvent) {
        Stage stageActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageActual.close();
    }
}