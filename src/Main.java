package src;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D WORLD");


        CarGame carGame = new CarGame();
        window.add(carGame);

        window.pack();


        window.setLocationRelativeTo(null);
        window.setVisible(true);

        carGame.startGameThread();



    }




}
