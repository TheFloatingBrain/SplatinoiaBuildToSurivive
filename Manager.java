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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author christophergreeley
 */

//*cough* requrement *cough*//

//Waste of an instantation :-(//
public final class Manager
{
    private JFrame window;
    //The player index will always be 0.//
    public static final int PLAYER_INDEX = 0;
    public static AtomicReference< JFrame > currentWindow;
    public static InputHandler input;
    public static int frames;
    public static int score;
    public static int blocksLeft;
    public static int refreshBlockSupply;
    public static final int BLOCK_RESUPPLY_CAP = 1000;
    public Manager( int width, int height )
    {
        blocksLeft = 200;
        refreshBlockSupply = 0;
        frames = 0;
        int colorOffset;
        GameObject.AddGameObject( new Player( 0 ) );
        //Mostly standard code.. just sort of, nicely "packaged up".//
        window = new JFrame( "Splatinoia: Build To Survive" );
        //So when you "hit - x" the window will close.//
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        input =  new InputHandler( true );
        window.add( new Renderer( input, input ) );
        /*Create the ground. By my calculations it would take almost a week
        to get to either end of the world from the starting position XD.*/
        for( int i = -150000; i < 150000; i += 32 )
        {
            //Nasty! *cough* requirement *cough*//
            colorOffset = ( int ) ( ( double ) Math.random() * ( double ) 20 );
            //To create the grass.//
            Renderer.boxes.add( new PositionedBox( new Color( 100, 150, 0 ), 
                    32, 16, new XYZ( ( float ) i, ( float ) 768, ( float ) 0 ) ) );
            //To create the dirt.//
            // +/- colorOffsetTo get a nice variation of colors.//
            Renderer.boxes.add( new PositionedBox( new Color( 255 - colorOffset, 222 + colorOffset, 173 + colorOffset ), 
                    32, 800, new XYZ( ( float ) i, ( float ) 785, ( float ) 0 ) ) );
        }
        //Set the size of the window.//
        window.setSize( width, height );
        window.setExtendedState( JFrame.MAXIMIZED_BOTH );
        window.setUndecorated( false );
        currentWindow = new AtomicReference< JFrame >( window );
        /*Open the window and start the program
        (well from a end-user perspective).*/
        window.setVisible( true );
    }
    public static final Manager ExecuteObjects()
    {
        int track = 0;
        for( GameObject i : GameObject.gameObjects )
        {
            if( i.GetState() == GOState.INIT ) {
                i.Init();
                i.SetState( GOState.UPDATE );
            }
            else if( i.GetState() == GOState.UPDATE )
                i.Update();
            else
            {
                //Restart the game if the player dies.//
                if( i.GetID().compareTo( "Player".toUpperCase() ) == 0 )
                {
                    score = frames;
                    SwingUtilities.invokeLater( new Runnable()
                    {
                        public void run() {
                            JOptionPane.showMessageDialog( null, "Good job! You survived " + ( score / 12.0 ) + " seconds!", 
                                    "Good Job!", JOptionPane.PLAIN_MESSAGE );
                        }
                    } );                    currentWindow.get().setVisible( false );
                    currentWindow.get().dispose();
                    GameObject.gameObjects.clear();
                    Renderer.boxes.clear();
                    return new Manager( 1400, 800 );
                }
                i.Des();
                GameObject.DeleteGameObject( track );
            }
            ++track;
        }
        return null;
    }
    public static final void SpawnSplat()
    {
        double random = ( Math.random() * 150 );
        if( random > 9 && random < 11 )
            GameObject.AddGameObject( new SplatBox( 0 ) );
    }
    public static final void Inturupt()
    {
        SpawnSplat();
        ExecuteObjects();
        SolidManager.ProcessSolids();
        ++frames;
        ++refreshBlockSupply;
        if( refreshBlockSupply >= BLOCK_RESUPPLY_CAP ) {
            blocksLeft += 10;
            refreshBlockSupply = 0;
        }
    }
}
