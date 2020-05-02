/*
   This file is part of Splatinoia: Build To Survive.

    Splatinoia: Build To Survive is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Splatinoia: Build To Survive is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Splatinoia: Build To Survive.  If not, see <http://www.gnu.org/licenses/>.

        Copyright 2012 Christopher Greeley
 */

/**
 *
 * @author christophergreeley
 */
public class Pair
{
    //Copy constructor.//
    public Pair() {}
    public Pair( int indexA_, int indexB_ ) {
        indexA = indexA_;
         indexB = indexB_;
    }
    public int indexA, indexB;
    public boolean IsProper( int size )
    {
        if( indexA < size && indexB < size && indexA >= 0 && indexB >= 0 )
            return true;
        return false;
    }
}