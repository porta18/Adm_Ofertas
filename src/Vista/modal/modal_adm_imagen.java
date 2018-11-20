/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.modal;

import Negocio.FTPUploader;
import Negocio.Funciones;
import Servicios.WsProducto;
import Vista.adm_productos;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class modal_adm_imagen extends javax.swing.JFrame {

    /**
     * Creates new form modal_adm_imagen
     */
    private static adm_productos first = null;

    private static modal_adm_imagen obj = null;
    private static String nombre_archivo = "";
    private static Integer id = 0;
    private static String rutaImagen = "0";
    private static Integer idImagen = 0;
    private JFrame frame;

    public modal_adm_imagen() {
        initComponents();
        //pnl_imagen.setVisible(false);
        this.setLocationRelativeTo(null);
        lbl_mensaje.setVisible(false);
        CargarImagen();
    }

    public static modal_adm_imagen getObj(adm_productos f, String nombre, Integer idCod, String rImagen, Integer idImg) {
        first = f;
        nombre_archivo = nombre;
        rutaImagen = rImagen;
        idImagen = idImg;
        id = idCod;
        if (obj == null) {
            obj = new modal_adm_imagen();
        }
        return obj;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_archivo = new javax.swing.JButton();
        lbl_mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btn_archivo.setText("Cargar");
        btn_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_archivoActionPerformed(evt);
            }
        });

        lbl_mensaje.setText("Sin imagen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(lbl_mensaje)
                .addContainerGap(289, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_archivo)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_mensaje)
                .addGap(18, 18, 18)
                .addComponent(btn_archivo)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        obj = null;
    }//GEN-LAST:event_formWindowClosing

    private void btn_archivoActionPerformed(java.awt.event.ActionEvent evt) {

        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        fileChooser.setMultiSelectionEnabled(true);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String archivo = fileChooser.getSelectedFile().toString();

            FTPUploader ftpUploader;
            try {

                String fecha = java.time.LocalDateTime.now().toString().replace(":", "_").replace(".", "_").replace("-", "_");
                ftpUploader = new FTPUploader("ftp.site4now.net", "misofertas", "misofertas2018");
                ftpUploader.uploadFile(archivo, nombre_archivo +"_"+ fecha+".jpg", "/Content/img/product/");
                ftpUploader.disconnect();

              

                //Image image = null;
                /*
                try {
                    URL url = new URL(ftpUrl);
                    URLConnection conn = url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
                    image = ImageIO.read(iis);

                    
                    String line = null;
                    System.out.println("--- START ---");
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("--- END ---");
                     
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                 */
                Image image = null;
                URL url;

                try {
                    //url = new URL("ftp://misofertas:misofertas2018@site4now.net/Content/img/product/" + nombre_archivo + ".jpg");
                    url = new URL("http://fvsoluciones-001-site8.ftempurl.com/Content/img/product/" + nombre_archivo +"_"+ fecha+".jpg");
                    //url = new URL(ftpUrl);
                    try {
                        URLConnection con = url.openConnection();
                        InputStream is = con.getInputStream();

                        ImageInputStream iis = ImageIO.createImageInputStream(is);
                        image = ImageIO.read(is);
                    } catch (Exception ex) {

                    }

                } catch (IOException e) {

                }
                try {
                    frame.dispose();
                } catch (Exception ex) {
                }
                frame = new JFrame();
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setAlwaysOnTop(true);

                JLabel label = new JLabel(new ImageIcon(image));
                frame.add(label);
                frame.setVisible(true);

                lbl_mensaje.setVisible(true);
                lbl_mensaje.setText(nombre_archivo);
                //pnl_imagen.add(lblimage);
                //pnl_imagen.setVisible(true);

                String pathImagen = "~/Content/img/product/" + nombre_archivo + "_" + fecha + ".jpg";

                GuardarImagen(pathImagen, id);

                JOptionPane.showMessageDialog(this, "Imagen guardada correctamente");

            } catch (Exception ex) {
                Logger.getLogger(modal_adm_imagen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void GuardarImagen(String patch, Integer prd_id) {
        WsProducto ws = new WsProducto();
        String retorno;
        if (idImagen > 0) {
            ws.WSfn_EliminarImagenProducto(idImagen);
        }
        retorno = ws.WSfn_GuardarImagenProducto("do", patch, prd_id, 0);

    }

    public void CargarImagen() {
        if (!rutaImagen.toString().equals("") & !rutaImagen.toString().equals("0")) {
            Image image = null;
            URL url;
            String ruta = rutaImagen.replace("~", "");
            try {

                url = new URL("http://fvsoluciones-001-site8.ftempurl.com" + ruta);

                try {
                    URLConnection con = url.openConnection();
                    InputStream is = con.getInputStream();

                    ImageInputStream iis = ImageIO.createImageInputStream(is);
                    image = ImageIO.read(is);
                } catch (Exception ex) {

                }

            } catch (IOException e) {

            }

            try {
                frame.dispose();
            } catch (Exception ex) {
            }

            frame = new JFrame();
            frame.setSize(400, 400);
            //frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
            frame.setVisible(true);

            JLabel lblimage = new JLabel(new ImageIcon(image));
            lbl_mensaje.setVisible(true);
            lbl_mensaje.setText(nombre_archivo);
        } else {
            lbl_mensaje.setVisible(true);
            lbl_mensaje.setText("Sin imagen");
        }
    }

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
            java.util.logging.Logger.getLogger(modal_adm_imagen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modal_adm_imagen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modal_adm_imagen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modal_adm_imagen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new modal_adm_imagen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_archivo;
    private javax.swing.JLabel lbl_mensaje;
    // End of variables declaration//GEN-END:variables
}