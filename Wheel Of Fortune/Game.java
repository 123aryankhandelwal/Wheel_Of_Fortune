package hw2;

public class Game
{   
	private int numPlayers;
	private Wheel w;
	private HiddenText sp;
	private Player[] players;
	private int whoseTurn;
	private boolean rover, nspin;
	public static final int VOWEL_COST = 250;
	/**
	 * 
	 * @param names
	 */
	public Game(String[] names)
	{
		sp=null;
		w = new Wheel();
		players = new Player[names.length];
		for(int i=0;i<names.length;i++)
		{
			players[i]=new Player(names[i]);
		}
		numPlayers=players.length;
	}
	/**
	 * 
	 * @param whoseturn
	 * @param secretPhrase
	 */
	public void startRound(int whoseturn ,HiddenText secretPhrase) 
	{
		sp=secretPhrase;
		for(int i = 0; i<numPlayers; i++)
		{
			players[i].clearRoundBalance();
		}
		rover = false;
		
	}
	/**
	 * 
	 * @param guess
	 * @return true if the guess matches the hidden text
	 */
	public boolean guessPhrase(String guess)
	{
		if(sp.getHiddenText().equalsIgnoreCase(guess))
		{
			int c = sp.countHiddenConsonants();
			players[whoseTurn].addToRoundBalance(w.getSegmentValue()*c);
			sp.updateAllRemaining();
			for(int i = 0; i<numPlayers; i++)
			{
				if(i!=whoseTurn)
				{
					players[i].clearRoundBalance();
				}
			}
			players[whoseTurn].winRound();
			rover = true;
			return rover;
		}
		changeTurn();
		return rover;
	}
	/**
	 * 
	 * @param guess
	 * @return the number of times the given consonant appears in the hidden text
	 */
	public int guessConsonant(char guess)
	{
		if(!isVowel(guess))
		{
			nspin = true;
			int noc =  sp.letterCount(guess);
			sp.update(guess);
			if(noc!=0)
			{
				players[whoseTurn].addToRoundBalance(noc*w.getSegmentValue());
			}
			else
			{
				changeTurn();
			}
			return noc;
		}
		else
		{
			return 0;
		}
		
	}
	/**
	 * 
	 * @param guess
	 * @return the number of times the given vowel appears in the hidden text
	 */
	public int buyVowel(char guess)
	{
		if(isVowel(guess))
		{
			nspin = false;
			players[whoseTurn].subtractFromRoundBalance(VOWEL_COST);
			int nov =  sp.letterCount(guess);
			sp.update(guess);
			if(nov == 0)
			{
				changeTurn();
			}
			return nov;
		}
		else
		{
			return 0;
		}
		
	}
	/**
	 * 
	 * @return current wheel rotation
	 */
	public int getWheelRotation()
	{
		return w.getRotation();
	}
	/**
	 * 
	 * @return the current state of the wheel for this game
	 */
	public int getWheelValue()
	{
		return w.getSegmentValue();
	}
	/**
	 * 
	 * @return index of the current player
	 */
	public int whoseTurn()
	{
		return whoseTurn;
	}
	/**
	 * 
	 * @param whoseTurn
	 * @return round balance for the indicated player
	 */
	public int getRoundBalance(int whoseTurn)
	{
		return players[whoseTurn].getRoundBalance();
	}
	/**
	 * 
	 * @return number of players in this game
	 */
	public int getNumPlayers()
	{
		return numPlayers;
	}
	/**
	 * 
	 * @param whoseTurn
	 * @return name of the indicated player
	 */
	public String getPlayerName(int whoseTurn)
	{
		return players[whoseTurn].getName();
	}
	/**
	 * 
	 * @return displayed form of the secret phrase
	 */
	public char[] getDisplay()
	{
		return sp.getDisplayedText();
	}
	/**
	 * 
	 * @param i
	 * @return game balance for the indicated player
	 */
	public int getGameBalance(int i)
	{
		return players[i].getGameBalance();
	}
	/**
	 * 
	 * @return true if the round is over, false otherwise
	 */
	public boolean roundOver()
	{
		return rover;
	}
	/**
	 * 
	 * @return true if the current player needs to spin the wheel, false otherwise
	 */
	public boolean needsSpin() 
	{
		if(w.getSegmentValue()==Wheel.BANKRUPT||w.getSegmentValue()==Wheel.LOSE_A_TURN||w.getSegmentValue()==Wheel.FREE_PLAY)
		{
			return true;
		}
			
		return nspin;
	}
	/**
	 * 
	 * @param c
	 * @return true for vowel
	 */
	private boolean isVowel(char c)
	{
	    if(c=='a' || c=='A' || c=='e' || c=='E' || c=='i' || c=='I' || c=='o' || c=='O' ||c=='u' || c=='U')
	    {    
	        return true;
	    }    
	    else
	    {
	        return false;
	    }    
	}
	/**
	 * 
	 * @param degrees
	 */
	public void spinWheel(int degrees)
	{
		w.spin(degrees);
		if(w.getSegmentValue()==Wheel.BANKRUPT||w.getSegmentValue()==Wheel.LOSE_A_TURN)
		{
			if(w.getSegmentValue()==Wheel.BANKRUPT)
			{
				players[whoseTurn].clearRoundBalance();
			}
			changeTurn();
		}
	}
	/**
	 *  Used to change whoseTurn
	 */
	private void changeTurn()
	{
		whoseTurn= (whoseTurn+1)%numPlayers;
	}	
	/**
	 * 
	 * @return solved form of the secret phrase
	 */
	public String getAnswer()
	{
		return sp.getHiddenText();
	}
}


