 /**
    Author: Icko Iben
    Date Created: 3/16/2024
    Version: 1.0.0

    This file is part of CocInfoScraper.

    CocInfoScraper is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free Software
    Foundation, either version 3 of the License, or (at your option) any later version.

    CocInfoScraper is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along with
    CocInfoScraper. If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.ickoxii.interfaces.implementations;

import io.github.ickoxii.interfaces.api.APIController;

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
