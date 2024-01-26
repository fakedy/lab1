package src;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D WORLD");


        Game game = new TruckGame();
        //CarGame carGame = new CarGame();



        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.startGameThread();



    }




}
