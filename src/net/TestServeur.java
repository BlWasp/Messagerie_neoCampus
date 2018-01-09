package net;

import java.io.IOException;

public class TestServeur {
    public static void main(String[] args) {
        Serveur serveur = new Serveur();
        try {
            serveur.start();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
