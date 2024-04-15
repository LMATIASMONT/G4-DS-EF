import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditarEstudianteDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> comboUniversidad;
    private JComboBox<String> comboCarrera;
    private JButton btnGuardar;
    private int idEstudiante;

    public EditarEstudianteDialog(Frame owner, int id, String nombre, int edad, String universidad, String carrera) {
        super(owner, "Editar Estudiante", true);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(5, 2));
        idEstudiante = id;

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(nombre);
        JLabel lblEdad = new JLabel("Edad:");
        txtEdad = new JTextField(String.valueOf(edad));
        JLabel lblUniversidad = new JLabel("Universidad:");
        comboUniversidad = new JComboBox<>(new String[]{"UNIMINUTO", "EAN"});
        comboUniversidad.setSelectedItem(universidad);
        JLabel lblCarrera = new JLabel("Carrera:");
        comboCarrera = new JComboBox<>(new String[]{"ADMIN EMPRESAS", "ING SISTEMAS"});
        comboCarrera.setSelectedItem(carrera);
        btnGuardar = new JButton("Guardar");

        add(lblNombre);
        add(txtNombre);
        add(lblEdad);
        add(txtEdad);
        add(lblUniversidad);
        add(comboUniversidad);
        add(lblCarrera);
        add(comboCarrera);
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarEstudiante();
            }
        });
    }

    private void editarEstudiante() {
        String nombre = txtNombre.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String universidad = (String) comboUniversidad.getSelectedItem();
        String carrera = (String) comboCarrera.getSelectedItem();

        try {
            Connection conn = ConexionBD.getConnection();

            PreparedStatement stmt = conn.prepareStatement("UPDATE estudiantes SET nombre = ?, edad = ?, universidad = ?, carrera = ? WHERE id = ?");
            stmt.setString(1, nombre);
            stmt.setInt(2, edad);
            stmt.setString(3, universidad);
            stmt.setString(4, carrera);
            stmt.setInt(5, idEstudiante);
            stmt.executeUpdate();

            conn.close();

            JOptionPane.showMessageDialog(EditarEstudianteDialog.this, "Estudiante actualizado correctamente");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(EditarEstudianteDialog.this, "Error al actualizar estudiante");
        }
    }
}