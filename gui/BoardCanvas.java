package gui;


import hw2.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Custom panel for the game board for WheelOfFortuneUI
 */
public class BoardCanvas extends JPanel
{
  /**
   * Number of rows in the grid.
   */
  private final int BOARD_ROWS = 4;
  
  /**
   * Number of columns in the grid.
   */
  private final int BOARD_COLUMNS = 14;
  
  /**
   * Reference to the game object.
   */
  private Game game;
  
  /**
   * The labels for the grid.
   */
  private MyLabel[][] letters;
  
  /**
   * Constructs the components of the panel.
   * @param game
   *   the WheelOfFortuneGame whose secret phrase
   *   will be displayed
   */
  public BoardCanvas(Game game)
  {
    this.game = game;
    letters = new MyLabel[BOARD_ROWS][BOARD_COLUMNS];
    this.setBackground(Color.WHITE);
    this.setLayout(new GridLayout(BOARD_ROWS, BOARD_COLUMNS, 2, 2));
    this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
    for (int row = 0; row < BOARD_ROWS; ++row)
    {
      for (int col = 0; col < BOARD_COLUMNS; ++col)
      {
        letters[row][col] = new MyLabel();
        letters[row][col].setFont(new Font("Serif", Font.PLAIN, 40));
        letters[row][col].setVerticalAlignment(SwingConstants.CENTER);
        letters[row][col].setHorizontalAlignment(SwingConstants.CENTER);
        this.add(letters[row][col]);
      }      
    }  
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g);
    setAllText(game.getDisplay());
  }

  /**
   * Displays the string s on the board.  Spaces in the string
   * are rendered as blanks in the background color, and instances
   * of SecretPhrase.PLACEHOLDER are rendered as blanks in the
   * foreground color.
   * @param displayText
   *   the character array to be displayed
   */
  public void setAllText(char[] displayText)
  {
    // Start by clearing the board
    for (int row = 0; row < BOARD_ROWS; ++row)
    {
      for (int col = 0; col < BOARD_COLUMNS; ++col)
      {
        letters[row][col].clear();
      }
    }
    
    // Here is the tricky part, the string has to be broken up
    // into rows to try to fit it onto the game board.  If there
    // isn't enough space, the string will just be truncated.
    
    // Start with an empty string for each row
    String[] pieces = new String[BOARD_ROWS];
    for (int i = 0; i < BOARD_ROWS; ++i)
    {
      pieces[i] = "";
    }
    
    
    // split the string at the blanks
    String s = new String(displayText);
    String[] tokens = s.split("\\s");  // whitespace characters
    int index = 0;
    int row = 0;
    int currLength = 0;
    
    // fit as many tokens as possible on each line, until
    // we run out of rows
    while (index < tokens.length && row < BOARD_ROWS)
    {
      int nextLength = tokens[index].length();
      String nextToken = tokens[index];
      if (nextLength > BOARD_COLUMNS)
      {
        // string is too long even if on a row by itself, so cut it off
        // and keep trying
        nextToken = nextToken.substring(0, BOARD_COLUMNS);
        nextLength = BOARD_COLUMNS;
      }
      if (currLength == 0)
      {
        // we're at the beginning of a row, so it has to fit
        pieces[row] += nextToken;
        currLength += nextLength;
        index += 1;
      }
      else
      {
        // see if it fits, allowing for a space
        if (currLength + nextLength + 1 <= BOARD_COLUMNS)
        {
          // it fits
          pieces[row] += " " + nextToken;
          currLength += nextLength + 1;
          index += 1;
        }
        else
        {
          // doesn't fit, try the next row
          ++row;
          currLength = 0;
        }
      }
    }
    
    // got the string organized into a piece for each row,
    // now update the labels
    for (row = 0; row < BOARD_ROWS; ++row)
    {
      String line = pieces[row];
      for (int i = 0; i < line.length(); ++i)
      {
        char c = line.charAt(i);
        if (c == '*')
        {
          // setting empty string updates the background color
          letters[row][i].setLetter("");
        }
        else if (c != ' ')
        {
          letters[row][i].setLetter("" + c);
        }
      }
    }
  }

  /**
   * Custom subtype of JLabel that allows us to change
   * the background color.
   */
  private static class MyLabel extends JLabel
  {
    /**
     * Current color for this label.
     */
    private Color color;
    
    /**
     * Current text for this label.
     */
    private String text;

    /**
     * Constructs a new label.
     */
    public MyLabel()
    {
      super();
      color = Color.DARK_GRAY;
    }
   
    /**
     * Clears the cell and sets the background color.
     */
    public void clear()
    {
      this.setText("");
      this.color = Color.DARK_GRAY;
    }
    
    /**
     * Sets the text on this label and sets
     * the background color to indicate a missing letter.
     * @param s
     *   text to display
     */
    public void setLetter(String s)
    {
      this.setText(s);
      this.color = Color.WHITE;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      
      // Draws a rectangle with a 2 pixel black border
      g2.setBackground(Color.BLACK);
      g2.clearRect(0, 0, getWidth(), getHeight());
      g2.setBackground(color);
      g2.clearRect(2, 2, getWidth()-4, getHeight()-4);

      // then do the regular label stuff, like drawing the text
      super.paintComponent(g2);
    }
  }
  

}
