/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.*;

public class TrafficLight {
    private int x, y;             // Coordenadas de posición del semáforo
    private Color color;          // Color actual de la luz del semáforo (rojo, amarillo o verde)
    private String orientation;   // Orientación del semáforo: "VERTICAL" o "HORIZONTAL"

    // Constructor de la clase TrafficLight
    public TrafficLight(int x, int y, String orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.color = Color.RED;  // Inicialmente, el semáforo está en rojo
    }

    // Método para obtener el color actual del semáforo
    public Color getColor() {
        return color;
    }

    // Método para establecer el color del semáforo
    public void setColor(Color color) {
        this.color = color;
    }

    // Método para dibujar el semáforo en la pantalla
    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // Dibuja el marco del semáforo en color negro

        if (orientation.equals("VERTICAL")) {
            // Dibuja el marco del semáforo en orientación vertical
            g.fillRect(x, y, 40, 120);  // Marco de 40x120 px

            // Dibuja las luces
            g.setColor(color == Color.RED ? Color.RED : Color.DARK_GRAY);    // Luz roja
            g.fillOval(x + 10, y + 10, 20, 20);  // Luz roja en el marco
            g.setColor(color == Color.YELLOW ? Color.YELLOW : Color.DARK_GRAY); // Luz amarilla
            g.fillOval(x + 10, y + 45, 20, 20);  // Luz amarilla en el marco
            g.setColor(color == Color.GREEN ? Color.GREEN : Color.DARK_GRAY); // Luz verde
            g.fillOval(x + 10, y + 80, 20, 20);  // Luz verde en el marco
        } else if (orientation.equals("HORIZONTAL")) {
            // Dibuja el marco del semáforo en orientación horizontal
            g.fillRect(x, y, 120, 40);  // Marco de 120x40 px

            // Dibuja las luces
            g.setColor(color == Color.RED ? Color.RED : Color.DARK_GRAY);    // Luz roja
            g.fillOval(x + 10, y + 10, 20, 20);  // Luz roja en el marco
            g.setColor(color == Color.YELLOW ? Color.YELLOW : Color.DARK_GRAY); // Luz amarilla
            g.fillOval(x + 45, y + 10, 20, 20);  // Luz amarilla en el marco
            g.setColor(color == Color.GREEN ? Color.GREEN : Color.DARK_GRAY); // Luz verde
            g.fillOval(x + 80, y + 10, 20, 20);  // Luz verde en el marco
        }
    }
}
