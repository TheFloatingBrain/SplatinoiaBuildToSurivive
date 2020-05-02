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

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author christophergreeley
 */

//TODO: Fill all of this in!//
public abstract class SolidManager
{
    private static final float COLLISION_VECTOR_SCALER = 32;
    private static final float GRAVITY = 5;
    private static final int PHYSICS_TIME = 20;
    private static final int LEFT = ( - 1 );
    private static final int RIGHT = 1;
    private static final int MIDDLE = 3;
    public static final XYZ NO_COLLISION = new XYZ( GameObject.NO_COLLISION_ON_AXIS, 
            GameObject.NO_COLLISION_ON_AXIS, GameObject.NO_COLLISION_ON_AXIS );
    private static ArrayDeque< Pair > collisionPairs;
    //Interface.//
    public static final void ProcessSolids()
    {
        collisionPairs = new ArrayDeque< Pair >();
        DetectCollisions();
        SimulatePhysics();
    }
    private static final void DetectCollisions()
    {
        
        //n^2, fix?//
        int size = GameObject.gameObjects.size();
        for( int i = 0; i < size; ++i )
        {
            for( int j = 0; j < size; ++j )
            {
                if( j != i && GameObject.gameObjects.get( j ).GetCollidable() == true && 
                        GameObject.gameObjects.get( i ).GetCollidable() == true )
                {
                    XYZ result = DetectCollision( GameObject.gameObjects.get( i ), 
                            GameObject.gameObjects.get( j ) );
                    if( result.GetX() != NO_COLLISION.GetX() && result.GetY() != NO_COLLISION.GetY() )
                    {
                        collisionPairs.add( new Pair( i, j ) );
                        GameObject.gameObjects.get( j ).SetColliding( result );
                        GameObject.gameObjects.get( j ).SetPhysics( PHYSICS_TIME );
                    }
                }
            }
        }
    }
    private static int DetectOnAxis( float aLength, float bLength, float aPosition, float bPosition )
    {
        if( aPosition + aLength < bPosition + bLength && 
            bPosition < aPosition + aLength )
                return LEFT;
        else if( aPosition < bPosition + bLength && 
            bPosition < aPosition )
                return RIGHT;
        else if( bPosition < ( aLength + aPosition ) && bPosition > aPosition )
            return MIDDLE;
        return 0;
    }
    private static final XYZ DetectCollision( GameObject firstCanidate, GameObject secondCanidate )
    {
        int xDetect = 0;
        int yDetect = 0;
        XYZ constructing = new XYZ();
        constructing.SetX( NO_COLLISION.GetX() );
        constructing.SetY( NO_COLLISION.GetY() );
        constructing.SetZ( NO_COLLISION.GetZ() );
        for( Box a : firstCanidate.GetBoxes() )
        {
            for( Box b : secondCanidate.GetBoxes() )
            {
                //Resultes are relitive to the second canidate.//
                //X - axis aligned collision detection.//
                xDetect = DetectOnAxis( a.GetWidth(), b.GetWidth(), 
                        firstCanidate.GetPosition().GetX(), secondCanidate.GetPosition().GetX() );
                //Y - axis aligned collision detection.//
                yDetect = DetectOnAxis( a.GetHeight(), b.GetHeight(), 
                        firstCanidate.GetPosition().GetY(), secondCanidate.GetPosition().GetY() );
                if( yDetect != 0 && xDetect != 0 )
                {
                    if( xDetect == LEFT )
                        constructing.SetX( GameObject.X_COLLISION_LEFT );
                    else
                        constructing.SetX( GameObject.X_COLLISION_RIGHT );
                    if( yDetect == LEFT )
                        constructing.SetY( GameObject.Y_COLLISION_UP );
                    else
                        constructing.SetY( GameObject.Y_COLLISION_DOWN );
                    firstCanidate.SetIdOfCollider( secondCanidate.GetID() );
                    secondCanidate.SetIdOfCollider( firstCanidate.GetID() );
                    return constructing;
                }
            }
        }
        return constructing;
    }
    //Simulate simple physics.//
    private static final void SimulatePhysics()
    {
        int size = collisionPairs.size();
        for( Pair i : collisionPairs )
        {
            SimilateGameObjectPhysics( 
                    new AtomicReference< GameObject >( GameObject.gameObjects.get( i.indexA ) ), 
                    new AtomicReference< GameObject >( GameObject.gameObjects.get( i.indexB ) ) );
        }
        for( GameObject i : GameObject.gameObjects )
            SimilateDefaultGameObjectPhysics( new AtomicReference< GameObject >( i ) );
    }
    private static final boolean MoveOutOfBounds( AtomicReference< GameObject > a, AtomicReference< GameObject > b )
    {
        if( a.get().GetStatic() == false )
        {
            //*cough* requrement *cough*//
            float[] faceMagnitudes = new float[ 4 ];
            float smallest = 999999999;
            int index = 0;
            faceMagnitudes[ 0 ] = ( a.get().GetPosition().GetX() + a.get().GetBoxes().getFirst().GetWidth() ) - b.get().GetPosition().GetX();
            faceMagnitudes[ 1 ] = ( b.get().GetPosition().GetX() + b.get().GetBoxes().getFirst().GetWidth() ) - 
                    a.get().GetPosition().GetX();
            faceMagnitudes[ 2 ] = ( a.get().GetPosition().GetY() + a.get().GetBoxes().getFirst().GetHeight() ) - b.get().GetPosition().GetY();
            faceMagnitudes[ 3 ] = ( b.get().GetPosition().GetY() + b.get().GetBoxes().getFirst().GetHeight() ) - 
                    a.get().GetPosition().GetY();
            for( int i = 0; i < 4; ++i )
            {
                if( faceMagnitudes[ i ] < smallest ) {
                    smallest = faceMagnitudes[ i ];
                    index = i;
                }
            }
            //Yuk, so... not abstract :-(//
            if( index == 0 )
                a.get().GetPosition().SetX( b.get().GetPosition().GetX() - a.get().GetBoxes().getFirst().GetWidth() );
            if( index == 1 )
                 a.get().GetPosition().SetX( b.get().GetPosition().GetX() + b.get().GetBoxes().getFirst().GetWidth() );
            if( index == 2 )
                a.get().GetPosition().SetY( b.get().GetPosition().GetY() - a.get().GetBoxes().getFirst().GetHeight() );
            if( index == 3 )
                 a.get().GetPosition().SetY( b.get().GetPosition().GetY() + b.get().GetBoxes().getFirst().GetHeight() );
            return true;
        }
        return false;
    }
    private static final void SimilateDefaultGameObjectPhysics( AtomicReference< GameObject > a )
    {
        if( a.get().GetApplyPhysics() == true && a.get().GetStatic() == false )
        {
            if( a.get().GetPhysics() > 0 )
            {
                a.get().GetPosition().SetX( a.get().GetPosition().GetX() - a.get().IsColliding().GetX() );
                a.get().GetPosition().SetY( a.get().GetPosition().GetY() - a.get().IsColliding().GetY() );
                a.get().SetPhysics( a.get().GetPhysics() - 1 );
            }
            else
                a.get().SetColliding( NO_COLLISION );
        }
        if( a.get().IsColliding() == NO_COLLISION && a.get().GetStatic() == false ) {
            //Gravity.//
            a.get().GetPosition().SetY( a.get().GetPosition().GetY() + GRAVITY );
        }
        //So it does not go below ground level.//
        if( a.get().GetPosition().GetY() > GameObject.GROUND )
            a.get().GetPosition().SetY( GameObject.GROUND - 1 );
    }
    private static final void SimilateGameObjectPhysics( AtomicReference< GameObject > a, AtomicReference< GameObject > b )
    {
        //Resolve the collision.//
        if( MoveOutOfBounds( a, b ) == true ) {
            //Make sure the collision does not happen twice.//
            a.get().SetColliding( NO_COLLISION );
        }
    }
}
