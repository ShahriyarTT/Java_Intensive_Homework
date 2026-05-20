package com.bookstore;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import com.bookstore.model.State;

public class StateMan {

    public static void save(State state, String filename) {

        try {

            FileOutputStream fileOut =
                    new FileOutputStream(filename);

            ObjectOutputStream out =
                    new ObjectOutputStream(fileOut);

            out.writeObject(state);

            out.close();
            fileOut.close();

            System.out.println("Application state saved.");

        } catch (Exception e) {

            System.out.println("Error while saving.");

            e.printStackTrace();
        }
    }

    public static State load(String filename) {

        try {

            FileInputStream fileIn =
                    new FileInputStream(filename);

            ObjectInputStream in =
                    new ObjectInputStream(fileIn);

            State state =
                    (State) in.readObject();

            in.close();
            fileIn.close();

            System.out.println("Application state loaded.");

            return state;

        } catch (Exception e) {

            System.out.println("No saved state found.");

            return null;
        }
    }

}
