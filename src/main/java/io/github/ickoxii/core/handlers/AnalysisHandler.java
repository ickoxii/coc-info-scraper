 /**
    Author: Icko Iben
    Date Created: 3/20/2024
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

package io.github.ickoxii.core.handlers;

import io.github.ickoxii.models.Pair;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class AnalysisHandler {
    private double mean;
    private double median;
    private Pair<Integer, Integer> mode;    // <value, freq>
    private double stddev;

    public AnalysisHandler(List<Integer> values) {
        Collections.sort(values);
        int size = values.size();

        median = (size%2 == 0) ? (values.get(size/2 - 1) + values.get(size/2)) / 2.0 : values.get(size/2);

        // <value, frequency>
        Map<Integer, Integer> freq = new HashMap<>();
        double total = 0;
        mode = new Pair<>(0, 0);
        for(Integer value : values) {
            total += value;

            if(freq.containsKey(value)) {
                freq.put(value, freq.get(value) + 1);
            } else {
                freq.put(value, 1);
            }

            if(freq.get(value) > mode.getFirst()) {
                mode = new Pair<>(value, freq.get(value));
            }
        }
        mean = total / values.size();

        double sumOfSquaredDifferences = 0;
        for(Integer value : values) {
            sumOfSquaredDifferences += Math.pow(value - mean, 2);
        }
        stddev = Math.sqrt(sumOfSquaredDifferences / size);
    }

    private double getMean() {
        return mean;
    }

    private double getMedian() {
        return median;
    }

    private Pair<Integer, Integer> getMode() {
        return mode;
    }

    private double getStddev() {
        return stddev;
    }
}
