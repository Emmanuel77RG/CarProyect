/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HILOS;
 
import GUI.TrafficLight;
import GUI.TrafficPanel;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car implements Runnable {
    private int x, y;                   // Coordenadas de posición del auto
    private int speed;                  // Velocidad del auto
    private String direction;           // Dirección de movimiento del auto ("VERTICAL" o "HORIZONTAL")
    private TrafficLight verticalLight; // Semáforo que controla el movimiento en la dirección vertical
    private TrafficLight horizontalLight; // Semáforo que controla el movimiento en la dirección horizontal
    private TrafficPanel panel;         // Panel de tráfico para verificar el estado de la intersección
    private JLabel carLabel;            // JLabel que contiene la imagen del auto

    // Rutas a las imágenes de autos horizontales
private static final String[] HORIZONTAL_CAR_IMAGES = {
    "src/GUI/Hcoche_1.png",
    "src/GUI/HCM_preview_rev_1.png",
    "src/GUI/HCM1_preview_rev_1.png",
    "src/GUI/Hcarro.png",
    "src/GUI/Htaxi.png",
    "src/GUI/HHambulancia.png",
    "src/GUI/Hcamioneta_5.png",
    "src/GUI/Hcamioneta_7.png",
    "src/GUI/Hcamioneta_8.png",
    "src/GUI/HC2_preview_rev_1.png",
    "src/GUI/HCM1_preview_rev_1.png",
    "src/GUI/HCM_preview_rev_1.png",
    "src/GUI/Hcarro-deportivo_preview_rev_1.png"
};

// Rutas a las imágenes de autos verticales
private static final String[] VERTICAL_CAR_IMAGES = {
    "src/GUI/VC_preview_rev_1.png",
    "src/GUI/VCAR_preview_rev_1.png",
    "src/GUI/VCARR_preview_rev_1.png",
    "src/GUI/VCARROO_preview_rev_1.png",
    "src/GUI/VCCC_preview_rev_1.png",
    "src/GUI/VCRR_preview_rev_1.png",
    "src/GUI/V12593544_preview_rev_1.png",
    "src/GUI/V66464081.png",
    "src/GUI/Vimages_1_preview_rev_1.png",
    "src/GUI/Vimages_preview_rev_1.png"
};

    private static final int HORIZONTAL_STOP_X = 830; // Línea roja para autos horizontales
    private static final int VERTICAL_STOP_Y = 490;   // Línea naranja para autos verticales
    private static final int SAFE_DISTANCE = 200;     // Incremento de distancia segura entre autos para reducir colisiones
    private boolean hasCrossedIntersection = false;

    public Car(TrafficLight verticalLight, TrafficLight horizontalLight, TrafficPanel panel) {
        this.verticalLight = verticalLight;
        this.horizontalLight = horizontalLight;
        this.panel = panel;
        this.speed = 4;  // Aumenta la velocidad

        Random rand = new Random();

        // Determina la dirección y selecciona la imagen adecuada
        if (rand.nextBoolean()) {
            this.direction = "VERTICAL";
            ImageIcon carIcon = new ImageIcon(VERTICAL_CAR_IMAGES[rand.nextInt(VERTICAL_CAR_IMAGES.length)]);
            carLabel = new JLabel(carIcon);
            carLabel.setSize(40, 80); // Tamaño del JLabel para autos verticales
            x = rand.nextBoolean() ? 710 : 610;
            y = 1000;
        } else {
            this.direction = "HORIZONTAL";
            ImageIcon carIcon = new ImageIcon(HORIZONTAL_CAR_IMAGES[rand.nextInt(HORIZONTAL_CAR_IMAGES.length)]);
            carLabel = new JLabel(carIcon);
            carLabel.setSize(80, 40); // Tamaño del JLabel para autos horizontales
            x = 1420;
            y = rand.nextBoolean() ? 300 : 400;
        }

        // Establece la posición inicial del JLabel
        carLabel.setLocation(x, y);

        // Añade el JLabel al panel
        panel.add(carLabel);
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(20); // Control de velocidad ajustado
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        if (direction.equals("HORIZONTAL")) {
            if (!hasCrossedIntersection && panel.isIntersectionOccupiedVertical()) {
                return;
            }
            if (!hasCrossedIntersection && x <= HORIZONTAL_STOP_X) {
                if (horizontalLight.getColor() == Color.RED || !canMoveForward()) {
                    return;
                } else {
                    hasCrossedIntersection = true;
                }
            }
            if (canMoveForward()) {
                x -= speed;
                carLabel.setLocation(x, y); // Actualiza la posición del JLabel
            }
        } else if (direction.equals("VERTICAL")) {
            if (!hasCrossedIntersection && panel.isIntersectionOccupiedHorizontal()) {
                return;
            }
            if (!hasCrossedIntersection && y <= VERTICAL_STOP_Y) {
                if (verticalLight.getColor() == Color.RED || !canMoveForward()) {
                    return;
                } else {
                    hasCrossedIntersection = true;
                }
            }
            if (canMoveForward()) {
                y -= speed;
                carLabel.setLocation(x, y); // Actualiza la posición del JLabel
            }
        }
    }

    private boolean canMoveForward() {
        for (Car otherCar : panel.getCars()) {
            if (otherCar != this && otherCar.direction.equals(this.direction)) {
                if (this.direction.equals("HORIZONTAL")) {
                    if (Math.abs(this.x - otherCar.x) < SAFE_DISTANCE && this.y == otherCar.y && this.x > otherCar.x) {
                        return false;
                    }
                } else if (this.direction.equals("VERTICAL")) {
                    if (Math.abs(this.y - otherCar.y) < SAFE_DISTANCE && this.x == otherCar.x && this.y > otherCar.y) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
