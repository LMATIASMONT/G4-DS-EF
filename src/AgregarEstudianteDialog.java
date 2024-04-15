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

public class AgregarEstudianteDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> comboUniversidad;
    private JComboBox<String> comboCarrera;
    private JButton btnCrear;

    public AgregarEstudianteDialog(Frame owner) {
        super(owner, "Agregar Estudiante", true);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(5, 2));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblEdad = new JLabel("Edad:");
        txtEdad = new JTextField();
        JLabel lblUniversidad = new JLabel("Universidad:");
        comboUniversidad = new JComboBox<>(new String[]{"UNIMINUTO", "EAN"});
        JLabel lblCarrera = new JLabel("Carrera:");
        comboCarrera = new JComboBox<>(new String[]{"ADMIN EMPRESAS", "ING SISTEMAS"});
        btnCrear = new JButton("Crear");

        add(lblNombre);
        add(txtNombre);
        add(lblEdad);
        add(txtEdad);
        add(lblUniversidad);
        add(comboUniversidad);
        add(lblCarrera);
        add(comboCarrera);
        add(btnCrear);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                int edad = Integer.parseInt(txtEdad.getText());
                String universidad = (String) comboUniversidad.getSelectedItem();
                String carrera = (String) comboCarrera.getSelectedItem();

                try {
                    Connection conn = ConexionBD.getConnection();

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO estudiantes (nombre, edad, universidad, carrera) VALUES (?, ?, ?, ?)");
                    stmt.setString(1, nombre);
                    stmt.setInt(2, edad);
                    stmt.setString(3, universidad);
                    stmt.setString(4, carrera);
                    stmt.executeUpdate();

                    conn.close();

                    JOptionPane.showMessageDialog(AgregarEstudianteDialog.this, "Estudiante creado correctamente");
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AgregarEstudianteDialog.this, "Error al crear estudiante");
                }
            }
        });
    }
}
