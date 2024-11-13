/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import HILOS.Car;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrafficPanel extends JPanel {
    private TrafficLight verticalLight, horizontalLight; // Semáforos para el tráfico vertical y horizontal
    private List<Car> cars;                               // Lista de autos en la simulación
    private Image cityImage;                              // Imagen de la ciudad para el área de fondo superior
    private Image cityImage1;                             // Imagen de la ciudad para el área de fondo inferior

    private volatile boolean isIntersectionOccupiedHorizontal = false; // Indica si hay autos cruzando en horizontal
    private volatile boolean isIntersectionOccupiedVertical = false;   // Indica si hay autos cruzando en vertical

    // Constructor de la clase TrafficPanel
    public TrafficPanel() {
        setBackground(new Color(189, 189, 189));  // Fondo gris claro para simular el pavimento

        // Carga las imágenes de fondo para el área de la ciudad
        cityImage = new ImageIcon("src/GUI/carre.jpg").getImage();
        cityImage1 = new ImageIcon("src/GUI/carre1.jpg").getImage();

        // Configuración de las posiciones de los semáforos
        verticalLight = new TrafficLight(550, 180, "VERTICAL");
        horizontalLight = new TrafficLight(450, 480, "HORIZONTAL");
        cars = new ArrayList<>();  // Inicializa la lista de autos
    }

    // Dentro de la clase TrafficPanel
    public void startSimulation() {
        new Thread(() -> {
            try {
                while (true) {
                    // Configuración de los semáforos para permitir tráfico vertical
                    isIntersectionOccupiedVertical = true; // Indica que los autos en vertical pueden cruzar
                    verticalLight.setColor(Color.GREEN);
                    horizontalLight.setColor(Color.RED);
                    Thread.sleep(7000); // Incrementa el tiempo de espera a 7 segundos

                    verticalLight.setColor(Color.YELLOW);
                    Thread.sleep(3000); // Incrementa el tiempo en amarillo a 3 segundos

                    isIntersectionOccupiedVertical = false; // Libera la intersección para tráfico horizontal
                    verticalLight.setColor(Color.RED);
                    horizontalLight.setColor(Color.GREEN);
                    isIntersectionOccupiedHorizontal = true; // Indica que los autos en horizontal pueden cruzar
                    Thread.sleep(7000); // Incrementa el tiempo de espera a 7 segundos

                    horizontalLight.setColor(Color.YELLOW);
                    Thread.sleep(3000); // Incrementa el tiempo en amarillo a 3 segundos

                    isIntersectionOccupiedHorizontal = false; // Libera la intersección para tráfico vertical
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Inicia un temporizador para refrescar la pantalla cada 30 ms y mantener la animación fluida
        new Timer(50, e -> repaint()).start();
    }

    // Agrega un auto a la simulación
    public void addCar() {
        Car car = new Car(verticalLight, horizontalLight, this);
        cars.add(car);
        new Thread(car).start(); // Cada auto corre en un hilo separado
    }

    // Método para obtener la lista de autos en la simulación
    public List<Car> getCars() {
        return cars;
    }

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja las imágenes de fondo en áreas específicas de la pantalla
        if (cityImage != null) {
            g.drawImage(cityImage, 0, 0, 580, 270, this);  // Imagen en el área superior izquierda
        }
        if (cityImage != null) {
            g.drawImage(cityImage, 740, 0, 625, 270, this); // Imagen en el área superior derecha
        }
        if (cityImage1 != null) {
            g.drawImage(cityImage1, 0, 475, 610, 270, this); // Imagen en el área inferior izquierda
        }
        if (cityImage1 != null) {
            g.drawImage(cityImage1, 630, 475, 780, 270, this); // Imagen en el área inferior derecha
        }

        // Dibuja las carreteras y marcas viales
        drawRoads(g);

        // Dibuja los semáforos
        verticalLight.draw(g);
        horizontalLight.draw(g);

        // No es necesario llamar a car.draw(g) ya que los autos son JLabels
    }


    // Método para dibujar las carreteras, carriles y pasos peatonales
    private void drawRoads(Graphics g) {
        g.setColor(new Color(128, 128, 128));
        g.fillRect(getWidth() / 2 - 100, 0, 200, getHeight());
        g.fillRect(0, getHeight() / 2 - 100, getWidth(), 200);

        g.setColor(Color.WHITE);
        for (int i = 0; i < 200; i += 20) {
            g.fillRect(getWidth() / 2 - 110 + i, getHeight() / 2 - 110, 10, 40);
            g.fillRect(getWidth() / 2 - 110 + i, getHeight() / 2 + 70, 10, 40);
            g.fillRect(getWidth() / 2 - 110, getHeight() / 2 - 110 + i, 40, 10);
            g.fillRect(getWidth() / 2 + 70, getHeight() / 2 - 110 + i, 40, 10);
        }

        for (int i = 0; i < getHeight(); i += 40) {
            g.fillRect(getWidth() / 2 - 2, i, 5, 20);
            g.fillRect(getWidth() / 2 + 2, i, 5, 20);
        }
        for (int i = 0; i < getWidth(); i += 40) {
            g.fillRect(i, getHeight() / 2 - 2, 20, 5);
            g.fillRect(i, getHeight() / 2 + 2, 20, 5);
        }
    }

    // Métodos para obtener el estado de la intersección
    public boolean isIntersectionOccupiedHorizontal() {
        return isIntersectionOccupiedHorizontal;
    }

    public boolean isIntersectionOccupiedVertical() {
        return isIntersectionOccupiedVertical;
    }
}