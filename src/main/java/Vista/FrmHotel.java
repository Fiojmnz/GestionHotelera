/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

/**
 *
 * @author HP I3
 */
public class FrmHotel extends javax.swing.JFrame {

    private static boolean procesosEnEjecucion = false;
    private static final Object BLOQUEO_HILOS = new Object();

    private void iniciarProcesos() {

        synchronized (BLOQUEO_HILOS) {
            if (procesosEnEjecucion) {
                return;
            }

            procesosEnEjecucion = true;
        }

        java.util.concurrent.atomic.AtomicInteger terminados
                = new java.util.concurrent.atomic.AtomicInteger(0);

        Runnable finalizarProceso = () -> {
            if (terminados.incrementAndGet() == 2) {

                javax.swing.SwingUtilities.invokeLater(() -> {
                    actualizarIndicadores();

                    synchronized (BLOQUEO_HILOS) {
                        procesosEnEjecucion = false;
                    }

                    System.out.println("Los dos procesos finalizaron");
                });
            }
        };

        Thread hiloSupervisor = new Thread(() -> {

            try {
                Hilos.SupervisorReservas supervisor
                        = new Hilos.SupervisorReservas(
                                mensaje -> System.out.println(mensaje),
                                datos -> System.out.println(
                                        "Reservas revisadas: " + datos[0]
                                )
                        );

                supervisor.run();

            } finally {
                finalizarProceso.run();
            }

        });

        Thread hiloIndicadores = new Thread(() -> {

            try {
                Hilos.ActualizadorIndicadores actualizador = new Hilos.ActualizadorIndicadores(
                        datos -> javax.swing.SwingUtilities.invokeLater(() -> {

                            txtTotalHabitaciones.setText(
                                    String.valueOf(datos[0])
                            );

                            txtOcupadas.setText(
                                    String.valueOf(datos[1])
                            );

                            txtDisponibles.setText(
                                    String.valueOf(datos[2])
                            );

                            txtConfirmadas.setText(
                                    String.valueOf(datos[3])
                            );

                            txtIngresosEsperados.setText(
                                    "₡" + String.format("%,d", datos[5])
                            );

                        })
                );

                actualizador.run();

            } finally {
                finalizarProceso.run();
            }

        });

        hiloSupervisor.start();
        hiloIndicadores.start();
    }

    /**
     * Creates new form FrmHotel
     */
    public FrmHotel() {
        initComponents();

        lblNombreRecepcionista.setText(
                Modelo.GestorHotel.getInstancia().getRecepcionistaActivo()
        );

        actualizarIndicadores();
        iniciarProcesos();

    }

    private void actualizarIndicadores() {

        Modelo.GestorHotel gestor = Modelo.GestorHotel.getInstancia();

        java.util.List<Modelo.Habitacion> habitaciones = gestor.getHabitaciones();
        java.util.List<Modelo.Reserva> reservas = gestor.getReservas();

        int total = habitaciones.size();
        int ocupadas = 0;
        int confirmadas = 0;

        for (Modelo.Habitacion h : habitaciones) {

            for (Modelo.Reserva r : reservas) {

                if (r.getHabitacion().getNumero() == h.getNumero()
                        && r.getEstado().equals("Hospedado")) {

                    ocupadas++;
                    break;
                }
            }
        }

        for (Modelo.Reserva r : reservas) {

            if (r.getEstado().equals("Confirmada")) {
                confirmadas++;
            }
        }

        int disponibles = total - ocupadas;

        txtTotalHabitaciones.setText(String.valueOf(total));
        txtOcupadas.setText(String.valueOf(ocupadas));
        txtDisponibles.setText(String.valueOf(disponibles));
        txtConfirmadas.setText(String.valueOf(confirmadas));

        long ingresos = calcularIngresosRecursivo(reservas, 0);

        txtIngresosEsperados.setText(
                "₡" + String.format("%,d", ingresos)
        );
    }

    private long calcularIngresosRecursivo(
            java.util.List<Modelo.Reserva> reservas, int indice) {

        if (indice >= reservas.size()) {
            return 0;
        }

        long monto = 0;

        if (reservas.get(indice).getEstado().equals("Confirmada")) {
            monto = reservas.get(indice).getMontoTotal();
        }

        return monto + calcularIngresosRecursivo(reservas, indice + 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblRecepcionista = new javax.swing.JLabel();
        lblNombreRecepcionista = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTotalHabitaciones = new javax.swing.JTextField();
        txtOcupadas = new javax.swing.JTextField();
        txtDisponibles = new javax.swing.JTextField();
        txtConfirmadas = new javax.swing.JTextField();
        btnEstadias = new javax.swing.JButton();
        btnHuespedes = new javax.swing.JButton();
        btnReservas = new javax.swing.JButton();
        btnHabitaciones = new javax.swing.JButton();
        btnSimulacion = new javax.swing.JButton();
        btnDisponibilidad = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtIngresosEsperados = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblTitulo.setText("          SISTEMA DE GESTIÓN HOTELERA");

        lblRecepcionista.setText("Recepcionista:");

        lblNombreRecepcionista.setText("Sin usuario");

        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jLabel1.setText("Habitaciones disponibles");

        jLabel2.setText("Reservas confirmadas");

        jLabel3.setText("Habitaciones ocupadas");

        jLabel4.setText("Total de habitaciones");

        txtOcupadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOcupadasActionPerformed(evt);
            }
        });

        txtDisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDisponiblesActionPerformed(evt);
            }
        });

        btnEstadias.setText("Gestión de Estadias");
        btnEstadias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadiasActionPerformed(evt);
            }
        });

        btnHuespedes.setText("Registro de Huespedes");
        btnHuespedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuespedesActionPerformed(evt);
            }
        });

        btnReservas.setText("Registro De Reservas");
        btnReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservasActionPerformed(evt);
            }
        });

        btnHabitaciones.setText("Registro de Habitaciones");
        btnHabitaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabitacionesActionPerformed(evt);
            }
        });

        btnSimulacion.setText("Simulación de Vencimientos");
        btnSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulacionActionPerformed(evt);
            }
        });

        btnDisponibilidad.setText("Consultar Disponibilidad");
        btnDisponibilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponibilidadActionPerformed(evt);
            }
        });

        jLabel5.setText("Ingresos esperados:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(lblRecepcionista)
                        .addGap(41, 41, 41)
                        .addComponent(lblNombreRecepcionista))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(btnCerrarSesion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDisponibilidad)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnHuespedes)
                                .addGap(9, 9, 9)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEstadias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSimulacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(137, 137, 137))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTotalHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                        .addComponent(txtOcupadas)
                                        .addComponent(txtDisponibles)
                                        .addComponent(txtConfirmadas))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtIngresosEsperados, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(471, 471, 471)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRecepcionista)
                    .addComponent(lblNombreRecepcionista))
                .addGap(18, 18, 18)
                .addComponent(btnCerrarSesion)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotalHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtOcupadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtConfirmadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtIngresosEsperados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuespedes)
                    .addComponent(btnHabitaciones)
                    .addComponent(btnReservas))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDisponibilidad)
                    .addComponent(btnEstadias)
                    .addComponent(btnSimulacion))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOcupadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOcupadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcupadasActionPerformed

    private void txtDisponiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDisponiblesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDisponiblesActionPerformed

    private void btnHuespedesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuespedesActionPerformed
        FrmRegistroHuespedes ventana = new FrmRegistroHuespedes();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnHuespedesActionPerformed

    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed
        FrmRegistroReserva ventana = new FrmRegistroReserva();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnReservasActionPerformed

    private void btnDisponibilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponibilidadActionPerformed
        FrmConsultaDisponibilidad ventana = new FrmConsultaDisponibilidad();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnDisponibilidadActionPerformed

    private void btnEstadiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadiasActionPerformed
        FrmGestionEstadias ventana = new FrmGestionEstadias();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnEstadiasActionPerformed

    private void btnSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulacionActionPerformed
        FrmSimulacionVencimientos ventana = new FrmSimulacionVencimientos();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnSimulacionActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar la sesión?",
                "Cerrar sesión",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();

            FrmLogin login = new FrmLogin();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnHabitacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabitacionesActionPerformed

        FrmRegistroHabitaciones ventana = new FrmRegistroHabitaciones();
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);

    }//GEN-LAST:event_btnHabitacionesActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        actualizarIndicadores();
    }//GEN-LAST:event_formWindowGainedFocus

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
            java.util.logging.Logger.getLogger(FrmHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmHotel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnDisponibilidad;
    private javax.swing.JButton btnEstadias;
    private javax.swing.JButton btnHabitaciones;
    private javax.swing.JButton btnHuespedes;
    private javax.swing.JButton btnReservas;
    private javax.swing.JButton btnSimulacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblNombreRecepcionista;
    private javax.swing.JLabel lblRecepcionista;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtConfirmadas;
    private javax.swing.JTextField txtDisponibles;
    private javax.swing.JTextField txtIngresosEsperados;
    private javax.swing.JTextField txtOcupadas;
    private javax.swing.JTextField txtTotalHabitaciones;
    // End of variables declaration//GEN-END:variables
}
