package io.github.ickoxii.interfaces.implementations;

import io.github.ickoxii.interfaces.APIController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class APIImpl implements APIController {

    @Override
    public String getAPIToken(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        if(is == null) {
            System.err.println("File not found: " + file);
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String token = new String();


        try {
            // if (DEBUG) System.out.println("Reading token");
            token = br.readLine();
            // if (DEBUG) System.out.println("Done reading token");
        } catch(IOException ex) {
            System.err.println("Error reading token");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        return token;
    }

}
