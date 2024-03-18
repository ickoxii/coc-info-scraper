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

package io.github.ickoxii.interfaces.api;

public interface JWTAPIController {

    String getSigningAlgorithm(String token);

    String getAPIToken(String file);

}
