import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class InterfazUsuario extends JFrame {
    private JTable tablaEstudiantes;
    private DefaultTableModel model;

    public InterfazUsuario() {
        setTitle("Registro de Estudiantes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        agregarColumnas();
        mostrarDatos();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        panel.add(panelBotones, BorderLayout.NORTH);

        model = new DefaultTableModel();
        tablaEstudiantes = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        tablaEstudiantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaEstudiantes.getSelectedRow();
                    int id = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 0);
                    String nombre = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 1);
                    int edad = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 2);
                    String universidad = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 3);
                    String carrera = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 4);

                    EditarEstudianteDialog dialog = new EditarEstudianteDialog(InterfazUsuario.this, id, nombre, edad, universidad, carrera);
                    dialog.setVisible(true);
                }
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarEstudianteDialog dialog = new AgregarEstudianteDialog(InterfazUsuario.this);
                dialog.setVisible(true);
                actualizarTabla();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaEstudiantes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int id = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 0);
                    String nombre = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 1);
                    int edad = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 2);
                    String universidad = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 3);
                    String carrera = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 4);

                    EditarEstudianteDialog dialog = new EditarEstudianteDialog(InterfazUsuario.this, id, nombre, edad, universidad, carrera);
                    dialog.setVisible(true);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(InterfazUsuario.this, "Selecciona un estudiante para editar");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaEstudiantes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int confirmacion = JOptionPane.showConfirmDialog(InterfazUsuario.this,
                            "¿Estás seguro de que deseas eliminar este registro?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        int id = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 0);
                        eliminarEstudiante(id);
                    }
                } else {
                    JOptionPane.showMessageDialog(InterfazUsuario.this, "Selecciona un estudiante para eliminar");
                }
            }
        });
    }

    private void agregarColumnas() {
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Edad");
        model.addColumn("Universidad");
        model.addColumn("Carrera");
    }

    private void mostrarDatos() {
        try {
            Connection conn = ConexionBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM estudiantes");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] fila = {rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"), rs.getString("universidad"), rs.getString("carrera")};
                model.addRow(fila);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(InterfazUsuario.this, "Error al cargar datos de estudiantes");
        }
    }

    private void eliminarEstudiante(int id) {
        try {
            Connection conn = ConexionBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM estudiantes WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            conn.close();
            actualizarTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(InterfazUsuario.this, "Error al eliminar estudiante");
        }
    }

    private void actualizarTabla() {
        model.setRowCount(0);

        mostrarDatos();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfazUsuario interfaz = new InterfazUsuario();
                interfaz.setVisible(true);
            }
        });
    }
}
