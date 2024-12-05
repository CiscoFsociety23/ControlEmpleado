/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import dao.AsistenciaDao;
import dao.ComunaDao;
import dao.TurnoDao;
import dao.UsuariosDao;
import dto.Atrasos;
import dto.Ausencia;
import dto.Comuna;
import dto.Empleado;
import dto.EmpleadoCreationReq;
import dto.SalidasAnticipadas;
import dto.Turno;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class Principal extends javax.swing.JFrame {
    
    private AsistenciaDao asistenciaService = new AsistenciaDao();
    private UsuariosDao usuarioService = new UsuariosDao();
    private ComunaDao comunaService = new ComunaDao();
    private TurnoDao turnoService = new TurnoDao();
    public Empleado empleado = new Empleado();

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        
        iniciarActualizacionDeHora();
        
        inicializarFechaSeleccionada();
        
        llenarTabla();
        llenarTablaAtrasos();
        llenarTablaSalidasAnticipadas();
        llenarTablaInasistencias();
        
        llenarComboBoxComunas();
        llenarComboBoxRoles();
        llenarComboBoxContrato();
        llenarComboBoxTurnos();
        llenarComboBoxDepartamentos();
        llenarComboBoxActivo();
        
        empleadosTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Evitar ejecutar dos veces por cambios en selección
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = empleadosTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Extraer los valores de la fila seleccionada
                        String rut = empleadosTable.getValueAt(selectedRow, 0).toString();
                        String nombre = empleadosTable.getValueAt(selectedRow, 1).toString();
                        String apellidoPaterno = empleadosTable.getValueAt(selectedRow, 2).toString();
                        String apellidoMaterno = empleadosTable.getValueAt(selectedRow, 3).toString();
                        String correo = empleadosTable.getValueAt(selectedRow, 4).toString();
                        boolean activo = Boolean.parseBoolean(empleadosTable.getValueAt(selectedRow, 5).toString());
                        String rolSistema = empleadosTable.getValueAt(selectedRow, 6).toString();
                        String tipoContrato = empleadosTable.getValueAt(selectedRow, 7).toString();
                        String turno = empleadosTable.getValueAt(selectedRow, 8).toString();
                        String departamento = empleadosTable.getValueAt(selectedRow, 9).toString();
                        String direccionCompleta = empleadosTable.getValueAt(selectedRow, 10).toString();

                        // Separar comuna y dirección
                        String[] direccionPartes = direccionCompleta.split(",");
                        String direccion = direccionPartes[0].trim();
                        String comuna = direccionPartes[1].split(";")[0].trim();

                        // Asignar valores a los campos
                        rutField.setText(rut);
                        nombreField.setText(nombre);
                        apellidoPaternoField.setText(apellidoPaterno);
                        apellidoMaternoField.setText(apellidoMaterno);
                        correoField.setText(correo);
                        direccionField.setText(direccion);
                        listaComuna.setSelectedItem(comuna);
                        listaTipoContrato.setSelectedItem(tipoContrato);
                        listaRolSistema.setSelectedItem(rolSistema);
                        listaDepartamento.setSelectedItem(departamento);
                        listaTurno.setSelectedItem(turno);
                        listaActivo.setSelectedItem(activo ? "true" : "false");
                    }
                }
            }
        });
        
        this.setLocationRelativeTo(null);
    }
    
    private void iniciarActualizacionDeHora() {
        // Crear un Timer que actualice la hora cada 1000 milisegundos (1 segundo)
        Timer timer = new Timer(1000, e -> {
            // Obtener la fecha y hora actuales
            Date fechaActual = new Date();

            // Formatear la fecha
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = formatoFecha.format(fechaActual);

            // Formatear la hora
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            String horaFormateada = formatoHora.format(fechaActual);

            // Mostrar la fecha y hora en los JLabels
            labelFecha.setText(fechaFormateada);
            labelHora.setText(horaFormateada);
        });

        // Iniciar el Timer
        timer.start();
    }
    
    public void llenarTabla() {
        try {
            // Obtener la lista de empleados
            List<Empleado> empleados = this.usuarioService.obtenerEmpleados();

            if (empleados != null) {
                // Modelo de la tabla
                DefaultTableModel model = (DefaultTableModel) empleadosTable.getModel();

                // Limpiar datos previos en la tabla
                model.setRowCount(0);

                // Llenar la tabla con los datos de empleados
                for (Empleado empleado : empleados) {
                    model.addRow(new Object[]{
                        empleado.getRut(),
                        empleado.getNombre(),
                        empleado.getApellidoPaterno(),
                        empleado.getApellidoMaterno(),
                        empleado.getCorreo(),
                        empleado.getActivo().toString(),
                        empleado.getRol(),
                        empleado.getContrato(),
                        empleado.getTurno(),
                        empleado.getDepartamento(),
                        empleado.getDireccion()
                    });
                }
            } else {
                System.out.println("llenarTabla():No se pudieron obtener los empleados.");
            }
        } catch (Exception e) {
            System.out.println("llenarTabla(): Error al llenar la tabla: " + e.getMessage());
        }
    }
    
    public void llenarTablaAtrasos(){
        try {
            // Obtener la lista de empleados
            List<Atrasos> atrasos = this.asistenciaService.obtenerAtrasos();

            if (atrasos != null) {
                // Modelo de la tabla
                DefaultTableModel model = (DefaultTableModel) registrosAtrasosTable.getModel();

                // Limpiar datos previos en la tabla
                model.setRowCount(0);

                // Llenar la tabla con los datos de empleados
                for (Atrasos atraso : atrasos) {
                    model.addRow(new Object[]{
                        atraso.getFechaRegistro(),
                        atraso.getNombreEmpleado(),
                        atraso.getRutEmpleado(),
                        atraso.getTurnoEmpleado(),
                        atraso.getHoraEntrada()
                    });
                }
            } else {
                System.out.println("llenarTablaAtrasos():No se pudieron obtener los empleados.");
            }
        } catch (Exception e) {
            System.out.println("llenarTablaAtrasos(): Error al llenar la tabla: " + e.getMessage());
        }
    }
    
    public void llenarTablaSalidasAnticipadas(){
        try {
            // Obtener la lista de empleados
            List<SalidasAnticipadas> salidas = this.asistenciaService.obtenerSalidasAnticipadas();

            if (salidas != null) {
                // Modelo de la tabla
                DefaultTableModel model = (DefaultTableModel) registrosSalidasAntTable.getModel();

                // Limpiar datos previos en la tabla
                model.setRowCount(0);

                // Llenar la tabla con los datos de empleados
                for (SalidasAnticipadas salida : salidas) {
                    model.addRow(new Object[]{
                        salida.getFechaRegistro(),
                        salida.getNombreEmpleado(),
                        salida.getRutEmpleado(),
                        salida.getTurnoEmpleado(),
                        salida.getHoraSalida()
                    });
                }
            } else {
                System.out.println("llenarTablaAtrasos():No se pudieron obtener los empleados.");
            }
        } catch (Exception e) {
            System.out.println("llenarTablaAtrasos(): Error al llenar la tabla: " + e.getMessage());
        }
    }
    
    public void llenarTablaInasistencias(){
        try {
            // Obtener la lista de empleados
            List<Ausencia> ausencias = this.asistenciaService.obtenerInasistencias(fechaSeleccionada.getText());

            if (ausencias != null) {
                // Modelo de la tabla
                DefaultTableModel model = (DefaultTableModel) registrosAusenciasTable.getModel();

                // Limpiar datos previos en la tabla
                model.setRowCount(0);

                // Llenar la tabla con los datos de empleados
                for (Ausencia ausencia : ausencias) {
                    model.addRow(new Object[]{
                        ausencia.getFechaRegistro(),
                        ausencia.getNombreEmpleado(),
                        ausencia.getRut(),
                        ausencia.getTurno()
                    });
                }
            } else {
                System.out.println("llenarTablaInasistencias():No se pudieron obtener los empleados.");
            }
        } catch (Exception e) {
            System.out.println("llenarTablaInasistencias(): Error al llenar la tabla: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxComunas() {
        try {
            // Llamar al método para obtener la lista de comunas
            List<Comuna> comunas = this.comunaService.obtenerComunas();

            if (comunas != null) {
                // Crear un modelo para el JComboBox
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

                // Agregar las comunas al modelo
                for (Comuna comuna : comunas) {
                    comboBoxModel.addElement(comuna.getNombreComuna());
                }

                // Asignar el modelo al JComboBox
                listaComuna.setModel(comboBoxModel);
            } else {
                System.out.println("llenarComboBoxComunas(): No se pudieron obtener las comunas.");
            }
        } catch (Exception e) {
            System.out.println("llenarComboBoxComunas(): Error al llenar el JComboBox: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxRoles() {
        try {
            // Crear un modelo para el JComboBox con valores estáticos
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            // Agregar opciones "ADMIN" y "USER"
            comboBoxModel.addElement("ADMIN");
            comboBoxModel.addElement("USER");

            // Asignar el modelo al JComboBox
            listaRolSistema.setModel(comboBoxModel);
        } catch (Exception e) {
            System.out.println("Error al llenar el JComboBox de roles: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxContrato() {
        try {
            // Crear un modelo para el JComboBox con valores estáticos
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            // Agregar opciones "Plazo Fijo" y "Plazo Indefinido"
            comboBoxModel.addElement("Plazo Fijo");
            comboBoxModel.addElement("Plazo Indefinido");

            // Asignar el modelo al JComboBox
            listaTipoContrato.setModel(comboBoxModel);
        } catch (Exception e) {
            System.out.println("Error al llenar el JComboBox de roles: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxTurnos() {
        try {
            // Llamar al método para obtener la lista de comunas
            List<Turno> turnos = this.turnoService.obtenerTurnos();

            if (turnos != null) {
                // Crear un modelo para el JComboBox
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

                // Agregar las comunas al modelo
                for (Turno turno : turnos) {
                    comboBoxModel.addElement(turno.getTurno());
                }

                // Asignar el modelo al JComboBox
                listaTurno.setModel(comboBoxModel);
            } else {
                System.out.println("llenarComboBoxTurnos(): No se pudieron obtener las comunas.");
            }
        } catch (Exception e) {
            System.out.println("llenarComboBoxTurnos(): Error al llenar el JComboBox: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxDepartamentos() {
        try {
            // Crear un modelo para el JComboBox con valores estáticos
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            // Agregar opciones de Departamentos
            comboBoxModel.addElement("Tecnologia");
            comboBoxModel.addElement("Recursos Humanos");
            comboBoxModel.addElement("Ventas");
            comboBoxModel.addElement("Finanzas");

            // Asignar el modelo al JComboBox
            listaDepartamento.setModel(comboBoxModel);
        } catch (Exception e) {
            System.out.println("Error al llenar el JComboBox de roles: " + e.getMessage());
        }
    }
    
    public void llenarComboBoxActivo() {
        try {
            // Crear un modelo para el JComboBox con valores estáticos
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

            // Agregar opciones de Activo
            comboBoxModel.addElement("true");
            comboBoxModel.addElement("false");

            // Asignar el modelo al JComboBox
            listaActivo.setModel(comboBoxModel);
        } catch (Exception e) {
            System.out.println("Error al llenar el JComboBox de roles: " + e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnEntradaSalida = new javax.swing.JButton();
        btnGestionUsuarios = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnTurnos = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnReportes1 = new javax.swing.JButton();
        panelPrincipal = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        labelFecha = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        btnMarcarSalida = new javax.swing.JButton();
        btnMarcarEntrada = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        datosPersonales = new javax.swing.JPanel();
        labelRut = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelApellidoPaterno = new javax.swing.JLabel();
        labelApellidoMaterno = new javax.swing.JLabel();
        labelCorreo = new javax.swing.JLabel();
        rutField = new javax.swing.JTextField();
        nombreField = new javax.swing.JTextField();
        apellidoPaternoField = new javax.swing.JTextField();
        apellidoMaternoField = new javax.swing.JTextField();
        correoField = new javax.swing.JTextField();
        labelDireccion = new javax.swing.JLabel();
        labelComuna = new javax.swing.JLabel();
        listaComuna = new javax.swing.JComboBox<>();
        direccionField = new javax.swing.JTextField();
        datosLaborales = new javax.swing.JPanel();
        labelRolSistema = new javax.swing.JLabel();
        labelTipoContrato = new javax.swing.JLabel();
        labelTurno = new javax.swing.JLabel();
        listaTipoContrato = new javax.swing.JComboBox<>();
        listaTurno = new javax.swing.JComboBox<>();
        listaRolSistema = new javax.swing.JComboBox<>();
        labelDepartamento = new javax.swing.JLabel();
        listaDepartamento = new javax.swing.JComboBox<>();
        labelActivo = new javax.swing.JLabel();
        listaActivo = new javax.swing.JComboBox<>();
        btnCrearUsuario = new javax.swing.JButton();
        btnModificarUsuario = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        empleadosTable = new javax.swing.JTable();
        btnRefrescar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrosSalidasAntTable = new javax.swing.JTable();
        labelAtraso = new javax.swing.JLabel();
        labelSalidaAnticipada = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        registrosAtrasosTable = new javax.swing.JTable();
        labelInsasistencias = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        registrosAusenciasTable = new javax.swing.JTable();
        btnAtrasoExport = new javax.swing.JButton();
        btnSalidaAntExport = new javax.swing.JButton();
        btnAusExport = new javax.swing.JButton();
        btnActualizarAtrasReport = new javax.swing.JButton();
        btnActualizarReportSalidAnt = new javax.swing.JButton();
        btnActualizarAus = new javax.swing.JButton();
        calendarioReporte = new com.toedter.calendar.JCalendar();
        fechaInasistencia = new javax.swing.JLabel();
        fechaSeleccionada = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnEntradaSalida.setBackground(new java.awt.Color(51, 204, 255));
        btnEntradaSalida.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnEntradaSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/puerta2.png"))); // NOI18N
        btnEntradaSalida.setText("Entrada Salida");
        btnEntradaSalida.setBorder(null);
        btnEntradaSalida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntradaSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaSalidaActionPerformed(evt);
            }
        });

        btnGestionUsuarios.setBackground(new java.awt.Color(51, 204, 255));
        btnGestionUsuarios.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnGestionUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usu1.png"))); // NOI18N
        btnGestionUsuarios.setText("Gestion Usuarios");
        btnGestionUsuarios.setBorder(null);
        btnGestionUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionUsuariosActionPerformed(evt);
            }
        });

        btnReportes.setBackground(new java.awt.Color(51, 204, 255));
        btnReportes.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reportes2.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setBorder(null);
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnTurnos.setBackground(new java.awt.Color(51, 204, 255));
        btnTurnos.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnTurnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/diablo2.png"))); // NOI18N
        btnTurnos.setText("Correcciones");
        btnTurnos.setBorder(null);
        btnTurnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnosActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(51, 204, 255));
        btnCerrar.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar2.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setBorder(null);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo3.png"))); // NOI18N

        btnReportes1.setBackground(new java.awt.Color(51, 204, 255));
        btnReportes1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnReportes1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reportes2.png"))); // NOI18N
        btnReportes1.setText("Turnos");
        btnReportes1.setBorder(null);
        btnReportes1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportes1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEntradaSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReportes1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(btnEntradaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGestionUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReportes1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha y Hora Actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        labelFecha.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelFecha.setText("FECHA");

        labelHora.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        labelHora.setText("HORA");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFecha)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(labelHora)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelHora)
                .addGap(29, 29, 29))
        );

        btnMarcarSalida.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarSalida.setText("Marcar Salida");
        btnMarcarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarSalidaActionPerformed(evt);
            }
        });

        btnMarcarEntrada.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarEntrada.setText("Marcar Entrada");
        btnMarcarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarEntradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(412, 412, 412)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(459, 459, 459)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMarcarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMarcarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(453, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(btnMarcarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMarcarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Control Asistencia", jPanel4);

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        datosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        labelRut.setText("Rut:");

        labelNombre.setText("Nombre:");

        labelApellidoPaterno.setText("Apellido Paterno:");

        labelApellidoMaterno.setText("Apellido Materno:");

        labelCorreo.setText("Correo:");

        labelDireccion.setText("Direccion:");

        labelComuna.setText("Comuna:");

        listaComuna.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout datosPersonalesLayout = new javax.swing.GroupLayout(datosPersonales);
        datosPersonales.setLayout(datosPersonalesLayout);
        datosPersonalesLayout.setHorizontalGroup(
            datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datosPersonalesLayout.createSequentialGroup()
                            .addComponent(labelCorreo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                            .addComponent(correoField, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, datosPersonalesLayout.createSequentialGroup()
                            .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelRut, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelApellidoPaterno, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelApellidoMaterno, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(33, 33, 33)
                            .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rutField, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                .addComponent(nombreField)
                                .addComponent(apellidoPaternoField)
                                .addComponent(apellidoMaternoField))))
                    .addGroup(datosPersonalesLayout.createSequentialGroup()
                        .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(datosPersonalesLayout.createSequentialGroup()
                                .addComponent(labelComuna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(83, 83, 83))
                            .addGroup(datosPersonalesLayout.createSequentialGroup()
                                .addComponent(labelDireccion)
                                .addGap(77, 77, 77)))
                        .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(listaComuna, 0, 247, Short.MAX_VALUE)
                            .addComponent(direccionField))))
                .addContainerGap())
        );
        datosPersonalesLayout.setVerticalGroup(
            datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelRut)
                    .addComponent(rutField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(nombreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelApellidoPaterno)
                    .addComponent(apellidoPaternoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelApellidoMaterno)
                    .addComponent(apellidoMaternoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(correoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCorreo))
                .addGap(18, 18, 18)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDireccion)
                    .addComponent(direccionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelComuna))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        datosLaborales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Laborales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        labelRolSistema.setText("Rol Sistema:");

        labelTipoContrato.setText("Tipo Contrato:");

        labelTurno.setText("Turno:");

        listaTipoContrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        listaTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        listaRolSistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelDepartamento.setText("Departamento:");

        listaDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelActivo.setText("Activo:");

        listaActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout datosLaboralesLayout = new javax.swing.GroupLayout(datosLaborales);
        datosLaborales.setLayout(datosLaboralesLayout);
        datosLaboralesLayout.setHorizontalGroup(
            datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosLaboralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDepartamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelRolSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTipoContrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listaTipoContrato, 0, 187, Short.MAX_VALUE)
                    .addComponent(listaRolSistema, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listaTurno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listaDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listaActivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        datosLaboralesLayout.setVerticalGroup(
            datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datosLaboralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRolSistema)
                    .addComponent(listaRolSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaTipoContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTipoContrato))
                .addGap(18, 18, 18)
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTurno))
                .addGap(18, 18, 18)
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listaDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(datosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listaActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCrearUsuario.setText("Ingresar");
        btnCrearUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCrearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearUsuarioActionPerformed(evt);
            }
        });

        btnModificarUsuario.setText("Modificar");
        btnModificarUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnModificarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUsuarioActionPerformed(evt);
            }
        });

        empleadosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "RUT", "Nombre", "Apellido Paterno", "Apellido Materno", "Correo", "Activo", "Perfil", "Contrato", "Turno", "Departamento", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(empleadosTable);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(datosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datosLaborales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 340, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(datosLaborales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(datosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelPrincipal.addTab("Gestion Usuarios", jPanel5);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1114, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        panelPrincipal.addTab("Turnos", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1114, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        panelPrincipal.addTab("Correcion Asistencia", jPanel8);

        registrosSalidasAntTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha Registro", "Nombre Empleado", "Rut Empleado", "Turno Empleado", "Hora Salida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(registrosSalidasAntTable);

        labelAtraso.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        labelAtraso.setText("Registros de Atrasos");
        labelAtraso.setToolTipText("");

        labelSalidaAnticipada.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        labelSalidaAnticipada.setText("Registro de Salida Anticipada");
        labelSalidaAnticipada.setToolTipText("");

        registrosAtrasosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha Registro", "Nombre Empleado", "Rut Empleado", "Turno Empleado", "Hora Entrada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(registrosAtrasosTable);
        if (registrosAtrasosTable.getColumnModel().getColumnCount() > 0) {
            registrosAtrasosTable.getColumnModel().getColumn(3).setHeaderValue("Turno Empleado");
            registrosAtrasosTable.getColumnModel().getColumn(4).setHeaderValue("Hora Entrada");
        }

        labelInsasistencias.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        labelInsasistencias.setText("Registro de Inasistencias");
        labelInsasistencias.setToolTipText("");

        registrosAusenciasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha Registro", "Nombre Empleado", "Rut Empleado", "Turno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(registrosAusenciasTable);

        btnAtrasoExport.setText("Exportar");

        btnSalidaAntExport.setText("Exportar");

        btnAusExport.setText("Exportar");

        btnActualizarAtrasReport.setText("Actualizar Datos");
        btnActualizarAtrasReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAtrasReportActionPerformed(evt);
            }
        });

        btnActualizarReportSalidAnt.setText("Actualizar Datos");
        btnActualizarReportSalidAnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarReportSalidAntActionPerformed(evt);
            }
        });

        btnActualizarAus.setText("Actualizar Datos");
        btnActualizarAus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAusActionPerformed(evt);
            }
        });

        calendarioReporte.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioReportePropertyChange(evt);
            }
        });

        fechaInasistencia.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        fechaInasistencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fechaInasistencia.setToolTipText("");

        fechaSeleccionada.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        fechaSeleccionada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fechaSeleccionada.setText("seleccionar fecha");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(labelInsasistencias)
                        .addGap(18, 18, 18)
                        .addComponent(fechaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fechaInasistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizarAus)
                        .addGap(18, 18, 18)
                        .addComponent(btnAusExport))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(labelAtraso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarAtrasReport)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtrasoExport))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(labelSalidaAnticipada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarReportSalidAnt)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalidaAntExport))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(calendarioReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAtraso)
                    .addComponent(btnAtrasoExport)
                    .addComponent(btnActualizarAtrasReport))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSalidaAnticipada)
                    .addComponent(btnSalidaAntExport)
                    .addComponent(btnActualizarReportSalidAnt))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInsasistencias)
                    .addComponent(btnAusExport)
                    .addComponent(btnActualizarAus)
                    .addComponent(fechaInasistencia)
                    .addComponent(fechaSeleccionada))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calendarioReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Reportes", jPanel6);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Control de Asistencia");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(283, 283, 283))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPrincipal))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelPrincipal)
                .addContainerGap())
        );

        jPanel2.getAccessibleContext().setAccessibleName("Opciones de Uso");
        panelPrincipal.getAccessibleContext().setAccessibleName("Reporte Asistencias");
        jPanel3.getAccessibleContext().setAccessibleName("Panel de acceso para el personal de RRHH"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEntradaSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaSalidaActionPerformed
       panelPrincipal.setSelectedIndex(0);
    }//GEN-LAST:event_btnEntradaSalidaActionPerformed

    private void btnGestionUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionUsuariosActionPerformed
        panelPrincipal.setSelectedIndex(1);
    }//GEN-LAST:event_btnGestionUsuariosActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        panelPrincipal.setSelectedIndex(4);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnosActionPerformed
        panelPrincipal.setSelectedIndex(3);
    }//GEN-LAST:event_btnTurnosActionPerformed

    private void btnMarcarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarEntradaActionPerformed
        // TODO add your handling code here:
        Boolean marca = this.asistenciaService.registarMarcaje(this.empleado, "Entrada");
        if(marca){
            JOptionPane.showMessageDialog(null, "Ha registrado la Entrada con exito");
        } else {
            JOptionPane.showMessageDialog(null, "No es posible registrar la marca en este momento");
        }
    }//GEN-LAST:event_btnMarcarEntradaActionPerformed

    private void btnMarcarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarSalidaActionPerformed
        // TODO add your handling code here:
        Boolean marca = this.asistenciaService.registarMarcaje(this.empleado, "Salida");
        if(marca){
            JOptionPane.showMessageDialog(null, "Ha registrado la Salida con exito");
        } else {
            JOptionPane.showMessageDialog(null, "No es posible registrar la marca en este momento");
        }
    }//GEN-LAST:event_btnMarcarSalidaActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        llenarTabla();
        llenarComboBoxComunas();
        llenarComboBoxRoles();
        llenarComboBoxContrato();
        llenarComboBoxTurnos();
        llenarComboBoxDepartamentos();
        llenarComboBoxActivo();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearUsuarioActionPerformed
        // TODO add your handling code here:
        String rut = rutField.getText();
        String nombre = nombreField.getText();
        String apellidoPaterno = apellidoPaternoField.getText();
        String apellidoMaterno = apellidoMaternoField.getText();
        String correo = correoField.getText();
        Boolean activo = Boolean.parseBoolean(listaActivo.getSelectedItem().toString());
        String contrato = listaTipoContrato.getSelectedItem().toString();
        String rol = listaRolSistema.getSelectedItem().toString();
        String departamento = listaDepartamento.getSelectedItem().toString();
        String turno = listaTurno.getSelectedItem().toString();
        String direccion = direccionField.getText();
        String comuna = listaComuna.getSelectedItem().toString();
        
        if(rut.isEmpty() || nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || correo.isEmpty() || direccion.isEmpty()){
            JOptionPane.showMessageDialog(null, "No es posible registrar el usuario, debe completar todos los campos");
        } else {
            EmpleadoCreationReq empleado = new EmpleadoCreationReq(rut, nombre, apellidoPaterno, apellidoMaterno, correo, activo, contrato, rol, departamento, turno, direccion, comuna);
            Empleado create = this.usuarioService.crearEmpleado(empleado);
            if(create.getRut().equals(rut)){
                JOptionPane.showMessageDialog(null, "El empleado fue creado con exito");
                llenarTabla();
                rutField.setText("");
                nombreField.setText("");
                apellidoPaternoField.setText("");
                apellidoMaternoField.setText("");
                correoField.setText("");
                direccionField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No es posible registrar el usuario, verifique que no se encuentre creado (rut, correo)");
            }
        }
    }//GEN-LAST:event_btnCrearUsuarioActionPerformed

    private void btnModificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuarioActionPerformed
        // TODO add your handling code here:
        String rut = rutField.getText();
        String nombre = nombreField.getText();
        String apellidoPaterno = apellidoPaternoField.getText();
        String apellidoMaterno = apellidoMaternoField.getText();
        String correo = correoField.getText();
        Boolean activo = Boolean.parseBoolean(listaActivo.getSelectedItem().toString());
        String contrato = listaTipoContrato.getSelectedItem().toString();
        String rol = listaRolSistema.getSelectedItem().toString();
        String departamento = listaDepartamento.getSelectedItem().toString();
        String turno = listaTurno.getSelectedItem().toString();
        String direccion = direccionField.getText();
        String comuna = listaComuna.getSelectedItem().toString();
        
        if(rut.isEmpty() || nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || correo.isEmpty() || direccion.isEmpty()){
            JOptionPane.showMessageDialog(null, "No es posible actualizar el usuario, debe completar todos los campos");
        } else {
            EmpleadoCreationReq empleado = new EmpleadoCreationReq(rut, nombre, apellidoPaterno, apellidoMaterno, correo, activo, contrato, rol, departamento, turno, direccion, comuna);
            Empleado update = this.usuarioService.actualizarEmpleado(empleado);
            if(update.getRut().equals(rut)){
                JOptionPane.showMessageDialog(null, "El empleado fue actualizado con exito");
                llenarTabla();
                rutField.setText("");
                nombreField.setText("");
                apellidoPaternoField.setText("");
                apellidoMaternoField.setText("");
                correoField.setText("");
                direccionField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No es posible actualizar el usuario, verifique que no se encuentre creado (rut, correo)");
            }
        }
    }//GEN-LAST:event_btnModificarUsuarioActionPerformed

    private void calendarioReportePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioReportePropertyChange
        if(evt.getOldValue() != null){
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            fechaSeleccionada.setText(formatoFecha.format(calendarioReporte.getCalendar().getTime()));
            llenarTablaInasistencias();
        }
    }//GEN-LAST:event_calendarioReportePropertyChange

    private void inicializarFechaSeleccionada() {
        // Comprueba si el calendario tiene una fecha seleccionada
        if (calendarioReporte.getDate() != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            fechaSeleccionada.setText(formatoFecha.format(calendarioReporte.getCalendar().getTime()));
        }
    }
    
    private void btnActualizarAusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAusActionPerformed
        // TODO add your handling code here:
        llenarTablaInasistencias();
    }//GEN-LAST:event_btnActualizarAusActionPerformed

    private void btnActualizarReportSalidAntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarReportSalidAntActionPerformed
        // TODO add your handling code here:
        llenarTablaSalidasAnticipadas();
    }//GEN-LAST:event_btnActualizarReportSalidAntActionPerformed

    private void btnActualizarAtrasReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAtrasReportActionPerformed
        // TODO add your handling code here:
        llenarTablaAtrasos();
    }//GEN-LAST:event_btnActualizarAtrasReportActionPerformed

    private void btnReportes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportes1ActionPerformed
        // TODO add your handling code here:
        panelPrincipal.setSelectedIndex(2);
    }//GEN-LAST:event_btnReportes1ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidoMaternoField;
    private javax.swing.JTextField apellidoPaternoField;
    private javax.swing.JButton btnActualizarAtrasReport;
    private javax.swing.JButton btnActualizarAus;
    private javax.swing.JButton btnActualizarReportSalidAnt;
    private javax.swing.JButton btnAtrasoExport;
    private javax.swing.JButton btnAusExport;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCrearUsuario;
    private javax.swing.JButton btnEntradaSalida;
    private javax.swing.JButton btnGestionUsuarios;
    private javax.swing.JButton btnMarcarEntrada;
    private javax.swing.JButton btnMarcarSalida;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnReportes1;
    private javax.swing.JButton btnSalidaAntExport;
    private javax.swing.JButton btnTurnos;
    private com.toedter.calendar.JCalendar calendarioReporte;
    private javax.swing.JTextField correoField;
    private javax.swing.JPanel datosLaborales;
    private javax.swing.JPanel datosPersonales;
    private javax.swing.JTextField direccionField;
    private javax.swing.JTable empleadosTable;
    private javax.swing.JLabel fechaInasistencia;
    private javax.swing.JLabel fechaSeleccionada;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelActivo;
    private javax.swing.JLabel labelApellidoMaterno;
    private javax.swing.JLabel labelApellidoPaterno;
    private javax.swing.JLabel labelAtraso;
    private javax.swing.JLabel labelComuna;
    private javax.swing.JLabel labelCorreo;
    private javax.swing.JLabel labelDepartamento;
    private javax.swing.JLabel labelDireccion;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel labelInsasistencias;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelRolSistema;
    private javax.swing.JLabel labelRut;
    private javax.swing.JLabel labelSalidaAnticipada;
    private javax.swing.JLabel labelTipoContrato;
    private javax.swing.JLabel labelTurno;
    private javax.swing.JComboBox<String> listaActivo;
    private javax.swing.JComboBox<String> listaComuna;
    private javax.swing.JComboBox<String> listaDepartamento;
    private javax.swing.JComboBox<String> listaRolSistema;
    private javax.swing.JComboBox<String> listaTipoContrato;
    private javax.swing.JComboBox<String> listaTurno;
    private javax.swing.JTextField nombreField;
    private javax.swing.JTabbedPane panelPrincipal;
    private javax.swing.JTable registrosAtrasosTable;
    private javax.swing.JTable registrosAusenciasTable;
    private javax.swing.JTable registrosSalidasAntTable;
    private javax.swing.JTextField rutField;
    // End of variables declaration//GEN-END:variables
}
