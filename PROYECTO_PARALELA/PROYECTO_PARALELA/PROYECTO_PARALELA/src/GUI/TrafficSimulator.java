/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TrafficSimulator {

    private JFrame frame;            // Ventana principal de la simulación
    private TrafficPanel trafficPanel; // Panel personalizado donde se dibuja el tráfico

    // Constructor de la clase TrafficSimulator
    public TrafficSimulator() {
        // Configuración de la ventana principal (JFrame)
        frame = new JFrame("Simulador de Tráfico");
        trafficPanel = new TrafficPanel(); // Instancia del panel que contiene el tráfico

        // Configura la operación de cierre para que termine la aplicación al cerrar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establece el tamaño de la ventana de simulación
        frame.setSize(1420, 1000);

        // Agrega el panel de tráfico a la ventana
        frame.add(trafficPanel);

        // Hace visible la ventana
        frame.setVisible(true);

        // Agrega un KeyListener para detectar cuando se presiona una tecla en la ventana
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Si la tecla presionada es 'a', se añade un auto a la simulación
                if (e.getKeyChar() == 'a') {
                    trafficPanel.addCar();
                }
            }
        });
        // Inicia la simulación de tráfico (se inician los semáforos y el refresco de pantalla)
        trafficPanel.startSimulation();
    }

    // Método principal para ejecutar la simulación
    public static void main(String[] args) {
        new TrafficSimulator(); // Crea una nueva instancia de TrafficSimulator
    }
}
