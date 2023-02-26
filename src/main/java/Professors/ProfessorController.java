package Professors;

import Alumnos.AlumneController;
import com.example.crudfx_neti.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;

public class ProfessorController implements Initializable {


    @FXML
    private Button btn_exit;

    @FXML
    private TableColumn<Professor,String> columna_cognom;

    @FXML
    private TableColumn<Professor,String> columna_nom;

    @FXML
    private TableColumn<Professor, String> id_profesores;

    @FXML
    private TableView<Professor> table;

    @FXML
    private TextField txt_cognom;

    @FXML
    private TextField txt_nom;
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;

    public void Connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Add(ActionEvent event) throws SQLException, ParseException {
        String nombre, apellido;
        Connect();
        nombre = txt_nom.getText();
        apellido = txt_cognom.getText();
        try {
            pst = con.prepareStatement("insert into profesores(nombre, apellido)values(?,?)");
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Professor registrado correctamente!");
            alert.showAndWait();
            table();
            txt_nom.setText("");
            txt_cognom.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void Delete(ActionEvent event) throws SQLException {
        myIndex = table.getSelectionModel().getSelectedIndex();
        String idStr = table.getItems().get(myIndex).getId();
        String idStr1 = String.valueOf(idStr);
        int id = Integer.parseInt(idStr1);
        try {
            pst = con.prepareStatement("delete from profesores where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Professor borrado correctamente!");
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
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
        try {
            pst = con.prepareStatement("update profesores set nombre = ?,apellido = ? where id = ? ");
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setInt(3, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OK!");
            alert.setHeaderText("Professor actualizado!");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(AlumneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void table() {
        Connect();
        ObservableList<Professor> professors = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("select * from profesores");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                    Professor st = new Professor();
                    st.setId(rs.getString("id"));
                    st.setNombre(rs.getString("nombre"));
                    st.setApellido(rs.getString("apellido"));
                    professors.add(st);
                }
            }
            table.setItems(professors);
            columna_nom.setCellValueFactory(f -> f.getValue().nombreProperty());
            columna_cognom.setCellValueFactory(f -> f.getValue().apellidoProperty());
            id_profesores.setCellValueFactory(f -> f.getValue().idProperty());

        } catch (SQLException ex) {
            Logger.getLogger(ProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory(tv -> {
            TableRow<Professor> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    //idAlumno = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txt_nom.setText(table.getItems().get(myIndex).getNombre());
                    txt_cognom.setText(table.getItems().get(myIndex).getApellido());
                }
            });
            return myRow;
        });
    }
    public void Exit(ActionEvent actionEvent) throws IOException {
        Stage stageActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageActual.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connect();
        table();
    }
}