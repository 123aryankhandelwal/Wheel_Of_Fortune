package gui;

import hw2.Wheel;
import hw2.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Custom panel for animating the spinning of the wheel. This
 * is based on the wheel image from Wikipedia:
 * 
 * http://en.wikipedia.org/wiki/File:WheelofFortuneSeason30-Round4.png
 * 
 * which should ideally be scaled down to fit the window, presumably
 * WheelOfFortuneUI.WHEEL_SIZE x WheelOfFortuneUI.WHEEL_SIZE.
 */
public class WheelCanvas extends JPanel
{
  /**
   * Milliseconds between frames, about 30 fps.
   */
  private static final int SPEED = 40;
  
  /**
   * Degrees of rotation per frame; this number must divide
   * evenly into 360.
   */
  private static final int INCREMENT = 5;
  
  /**
   * Reference to the main UI panel, since we have to notify it when
   * the animation is complete.
   */
  private GameUI ui;
  
  /**
   * Current rotation of the image.
   */
  private int rotation;

  /**
   * Rotation while animating.
   */
  private int tempRotation;

  /**
   * Reference to the game.
   */
  private Game game;
  
  /**
   * The wheel image.
   */
  private Image image;
  
  /**
   * Swing animation timer.
   */
  private Timer timer;
  
  /**
   * Width of the wheel image.
   */
  private int imageWidth;
  
  /**
   * Height of the wheel image.
   */
  private int imageHeight;
  
  /**
   * Constructs the panel and loads the image.
   * @param ui GameUI object
   * @param game Game object
   */
  public WheelCanvas(GameUI ui, Game game)
  {
    this.ui = ui;
    this.game = game;
    timer = new Timer(SPEED, new WheelCallback());
    URL imageURL = GameUI.class.getResource(GameUI.WHEEL_FILENAME);
//    System.out.println(imageURL);
    ImageIcon i = null;    
    if (imageURL != null)
    {
      i = new ImageIcon(imageURL);
    }
    
    // Make sure image is loaded
    if (i == null || i.getImageLoadStatus() != MediaTracker.COMPLETE)
    {
      image = null;
    }
    else
    {
      image = i.getImage();
      imageWidth = i.getIconWidth();
      imageHeight = i.getIconHeight();
    }
  }
  
  /**
   * Initiate animation of a wheel spin that will end up with 
   * the game's current wheel rotation.
   */
  public void animateSpin()
  {
    // start with current rotation, this will increment each frame
    tempRotation = rotation;  
    
    // where we need to end up
    rotation = game.getWheelRotation();
    
    // make sure tempRotation is smaller (possibly negative)
    while (tempRotation > rotation)
    {
      tempRotation -= 360;
    }
    
    // angle through which to rotate
    int angle = rotation - tempRotation;
    
    // we increment only INCREMENT degrees each frame, so if the angle of rotation
    // isn't a multiple of INCREMENT, then add the extra degrees here 
    int extra = angle % INCREMENT;
    //tempRotation = rotation + extra;  
    tempRotation += extra;  
    
    // through at least 45 degrees
    if (rotation - tempRotation < 45)
    {
      tempRotation -= 360;
    }
    
    timer.start();
  }
  
  /**
   * Returns true if an animation is in progress.
   * @return if animation is in progress
   */   
  public boolean isAnimating()
  {
    return rotation - tempRotation > 0;
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int currentRotation;
    if (isAnimating())
    {
      currentRotation = tempRotation;
    }
    else
    {
      currentRotation = rotation;
    }

    g2.setBackground(Color.WHITE);
    g2.clearRect(0, 0, getWidth(), getHeight());
    AffineTransform transform = new AffineTransform();
    transform.translate(imageWidth / 2, imageHeight / 2);
    transform.rotate(-((currentRotation) * Math.PI / 180.0)); // counterclockwise
    transform.translate(-imageWidth / 2, -imageHeight / 2);
    if (image != null)
    {
      g2.drawImage(image, transform, this);
    }
    else
    {
      // simulate animation with a rotating cursor
      if (isAnimating())
      {

        String text = "|";
        // try to center it, (x, y) is bottom left corner of text
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
        g2.setFont(font);     
        FontMetrics metrics = g.getFontMetrics(font);
        int height = metrics.getHeight();
        int width = metrics.stringWidth(text);
        int x = (getWidth() - width) / 2;
        int y = (getHeight() + height) / 2; 

        // draw the text
        g2.translate(getWidth() / 2, getHeight() / 2);
        g2.rotate(currentRotation);
        g2.translate(-getWidth() / 2, -getHeight() / 2);
        g2.drawString(text, x, y);
      }

    }
    if (!isAnimating())
    {

      // get the wheel state
      String text;
      int result = game.getWheelValue();
      if (result == Wheel.BANKRUPT)
      {
        text = "BANKRUPT";
      }
      else if (result == Wheel.LOSE_A_TURN)
      {
        text = "LOSE TURN";
      }
      else
      {
        text = "$" + result;
      }      

      // try to center it, (x, y) is bottom left corner of text
      Font font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
      g2.setFont(font);     
      FontMetrics metrics = g.getFontMetrics(font);
      int height = metrics.getHeight();
      int width = metrics.stringWidth(text);
      int x = (getWidth() - width) / 2;
      int y = (getHeight() + height) / 2; 

      // draw the text
      g2.drawString(text, x, y);
    }
  }

  
  /**
   * Timer callback.
   */
  private class WheelCallback implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      tempRotation += INCREMENT;
      if (tempRotation >= rotation)
      {
        // done, notify the UI and stop the timer
        ui.wheelReady();
        timer.stop();
      }
      repaint();
    }     
  }


}
