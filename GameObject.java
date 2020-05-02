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
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author christophergreeley
 */
public abstract class GameObject
{
    //No object found with a particular ID.//
    protected static final int NO_ID = ( - 1 );
    //Ground level.//
    public static final float GROUND = 704;
    //To improve clarity.//
    public static final float X_COLLISION_LEFT = ( - 1 );
    public static final float X_COLLISION_RIGHT = 1;
    public static final float Y_COLLISION_UP = 1;
    public static final float Y_COLLISION_DOWN = ( - 1 );
    public static final float X_COLLISION_MIDDLE = 3;
    public static final float Y_COLLISION_MIDDLE = 3;
    public static final float NO_COLLISION_ON_AXIS = 0;
    //I am a big fan of the facade pattern :-) .//
    //*cough* requrement *cough*//
    public static CopyOnWriteArrayList< GameObject > gameObjects = new CopyOnWriteArrayList< GameObject >();
    //Apply physics to the object?//
    protected boolean applyPhysics;
    //Will the object remain still?//
    protected boolean static__;
    //Will objects still interact with this object physically?//
    protected boolean collidable;
    protected XYZ position;
    protected String ID;
    //In case I want multiple boxes.//
    //*cough* requrement *cough*//
    protected ArrayDeque< Box > boxes;
    GOState state;
    //Is the object colliding? If so on what axis?//
    protected XYZ colliding;
    //How long should physics be simulated for?//
    protected int physics;
    //What am I colliding whith?//
    protected String idOfCollider;
    //The objects health.//
    protected float health;


    
    /*A little against my normal style, but type clarity can get dicy
    in a hetorogenous array. And this is Java, its all about OOP.*/
    public abstract void Init();
    public abstract void Update();
    public abstract void Des();

    public static final int FindObjectByID( String id )
    {
        id = id.toUpperCase();
        for( int i = 0; i < gameObjects.size(); ++i )
        {
            //*cough* requrement *cough*//
            if( gameObjects.get( i ).GetID().compareTo( id ) == 0 )
                return i;
        }
        return NO_ID;
    }
    protected final void DefaultConstruct()
    {
        idOfCollider = "";
        physics = 0;
        state = GOState.INIT;
        position = new XYZ();
        boxes = new ArrayDeque< Box >();
        colliding = SolidManager.NO_COLLISION;
    }
    //Simplify things.//
    public final boolean AddBox( Box toAdd )
    {
        if( boxes.add( toAdd ) == true )
            return true;
        return false;
    }
    public final boolean DeleteBox( int atIndex )
    {
        if( atIndex < boxes.size() ) {
            if( boxes.remove( atIndex ) == true )
                return true;
        }
        return false;
    }
    //Simplify things.//
    public static final boolean AddGameObject( GameObject toAdd )
    {
        if( gameObjects.add( toAdd ) == true )
            return true;
        return false;
    }
    public static final boolean DeleteGameObject( int atIndex )
    {
        if( atIndex < gameObjects.size() ) {
            gameObjects.remove( atIndex );
            return true;
        }
        return false;
    }
    //Encapsulation.//
    public final void SetBoxes( ArrayDeque<Box> boxes ) {
        this.boxes = boxes;
    }
    public void SetState( GOState state ) {
        this.state = state;
    }
    public void SetCollidable(boolean collidable) {
        this.collidable = collidable;
    }
    public final void SetID( String ID ) {
        //*cough* requrement *cough*//
        this.ID = ID.toUpperCase();
    }
    public final void SetApplyPhysics( boolean applyPhysics ) {
        this.applyPhysics = applyPhysics;
    }
    public final void SetPosition( XYZ position ) {
        this.position = position;
    }
    public final void SetStatic( boolean static__ ) {
        this.static__ = static__;
    }
    public void SetColliding( XYZ colliding ) {
        this.colliding = colliding;
    }
    public void SetPhysics( int physics ) {
        this.physics = physics;
    }
    public void SetHealth( float health ) {
        this.health = health;
    }
    public void SetIdOfCollider( String idOfCollider ) {
        this.idOfCollider = idOfCollider;
    }
    public float GetHealth() {
        return health;
    }

    public String GetIdOfCollider() {
        return idOfCollider;
    }

    public int GetPhysics() {
        return physics;
    }
    public XYZ IsColliding() {
        return colliding;
    }
    public GOState GetState() {
        return state;
    }

    public final boolean GetApplyPhysics() {
        return applyPhysics;
    }
    public boolean GetCollidable() {
        return collidable;
    }
    public final XYZ GetPosition() {
        return position;
    }
    public final boolean GetStatic() {
        return static__;
    }
    public final String GetID() {
        return ID.toUpperCase();
    }
    public final ArrayDeque<Box> GetBoxes() {
        return boxes;
    }
}
