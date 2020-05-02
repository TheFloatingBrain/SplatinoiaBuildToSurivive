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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author christophergreeley
 */
//Waste of an instantation :-(//
public final class Renderer extends JPanel
{
    private static boolean instructionsOpen = true;
    public static XYZ camera;
    public static ArrayDeque< PositionedBox > boxes = new ArrayDeque< PositionedBox >();
    private static ArrayDeque< PositionedBox > clouds = new ArrayDeque< PositionedBox >();
    public static PositionedBox ghost;
    public Renderer() {
        setFocusable( true );
    }
    public Renderer( KeyListener listener, MouseListener mListener )
    {
        ghost = new PositionedBox( Color.LIGHT_GRAY, 64, 64, new XYZ( 0, 0, 0 ) );
        camera = new XYZ();
        setFocusable( true );
        this.addKeyListener( listener );
        this.addMouseListener( mListener );
    }
    private static void DrawEssenchailInfo( AtomicReference< Graphics > g, int startingY )
    {
        g.get().drawString( "You have " + Manager.blocksLeft + " remaining.", 20, startingY + 20 );
        g.get().drawString( "You will receive 10 blocks in " + ( Manager.BLOCK_RESUPPLY_CAP - Manager.refreshBlockSupply ) + " frames.", 20, startingY + 40 );
        g.get().drawString( "Health: ", 20, startingY + 60 );
        g.get().setColor( new Color( 175 - ( int ) GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetHealth(), ( int ) GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetHealth(), 
                100 - ( int ) GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetHealth() ) );
        g.get().fillRect( 70, startingY + 48, ( int ) GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetHealth(), 16 );
    }
    private static void DrawGUI( AtomicReference< Graphics > g )
    {
        if( InputHandler.currentKeyInput == Input.CLOSE_BOX )
        {
            if( instructionsOpen == true )
                instructionsOpen = false;
            else
                instructionsOpen = true;
        }
        //Psudo GUI stuff.//
        if( instructionsOpen == true )
        {
            g.get().setColor( Color.BLACK );
            g.get().drawString( "Note: This game is recommended for play in full screen only.", 20, 20 );
            g.get().drawString( "To create box, left click.", 20, 40 );
            g.get().drawString( "To delete a box, right click.", 20, 60 );
            g.get().drawString( "To jump, press 'W' or the up arrow key.", 20, 80 );
            g.get().drawString( "To move, press 'A' and 'D' respectively or use the left and right keys respectively.", 20, 100 );
            g.get().drawString( "To toggle these instructions, press 'H'.", 20, 120 );
            DrawEssenchailInfo( g, 120 );
        }
        else {
            g.get().setColor( Color.BLACK );
            DrawEssenchailInfo( g, 0 );
        }
    }
    private static void MakeClouds()
    {
        double chance = Math.random() * 20;
        if( chance > 9 && chance < 11 )
        {
            //Do not worry, this math was *cough* not *cough* well thought out. XD//
            float x = GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetPosition().GetX() + 
                    ( float ) ( Math.sin( Math.random() * 10 ) * Math.random() * 1000 );
            float y = GameObject.gameObjects.get( Manager.PLAYER_INDEX ).GetPosition().GetX() + 
                    ( float ) ( Math.sin( Math.random() * 10 ) * Math.random() * 1000 );
            if( ( y + 500 ) > GameObject.GROUND )
                y -= 1000;
            clouds.add( new PositionedBox( new Color( 255, 255, 255 ), 300, 200, 
                    new XYZ( x, y, 0 ) ) );
        }
    }
    public void paintComponent( Graphics g )
    {
        Manager.Inturupt();
        //Well... that is innoficant...//
        g.setColor( new Color( 135, 206, 250 ) );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        for( PositionedBox cloud : Renderer.clouds ) {
            cloud.GetPosition().SetX( cloud.GetPosition().GetX() + ( float ) .3 );
            RenderBox( cloud, new AtomicReference< Graphics >( g ) );
        }
        for( PositionedBox b : Renderer.boxes )
            RenderBox( b, new AtomicReference< Graphics >( g ) );
        for( GameObject o : GameObject.gameObjects )
            RenderGameObject( o, new AtomicReference< Graphics >( g ) );
        MakeClouds();
        //Draw a "ghost" block.//
        RenderBox( ghost, new AtomicReference< Graphics >( g ) );
        DrawGUI( new AtomicReference< Graphics >( g ) );
        //Put the gost block on the cursors position.//
        ghost.SetPosition( new XYZ( ( float ) MouseInfo.getPointerInfo().getLocation().getX() + Renderer.camera.GetX(), 
                        ( float ) MouseInfo.getPointerInfo().getLocation().getY() + ( Renderer.camera.GetY() - 24 ), 0 ) );
        try {
            Thread.sleep( 100 );
        }
        catch( java.lang.InterruptedException e ) {
            System.out.println( e.getMessage() );
        }
        repaint();
    }
    //Render a box not attached to a GameObject.//
    public static void RenderBox( PositionedBox toRender, AtomicReference< Graphics > g )
    {
        g.get().setColor( toRender.GetColor() );
        g.get().fillRect( ( int ) toRender.GetPosition().GetX() - ( int ) camera.GetX(), ( int ) toRender.GetPosition().GetY() - ( int ) camera.GetY(), 
                ( int ) toRender.GetWidth(), ( int ) toRender.GetHeight() );
    }
    //Render a box on a GameObject.//
    public static void RenderBox( GameObject object, Box toRender, AtomicReference< Graphics > g )
    {
        g.get().setColor( toRender.GetColor() );
        g.get().fillRect( ( int ) object.GetPosition().GetX() - ( int ) camera.GetX(), ( int ) object.GetPosition().GetY() - ( int ) camera.GetY(), 
                ( int ) toRender.GetWidth(), ( int ) toRender.GetHeight() );
    }
    //Render a GameObject.//
    public static void RenderGameObject( GameObject object, AtomicReference< Graphics > g )  {
        for( Box box : object.GetBoxes() )
            RenderBox( object, box, g );
    }
}
