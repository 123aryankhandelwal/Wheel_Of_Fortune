package hw2;
public class HiddenText
{
	private char[] letters;
	private String word;
	/**
	 * 
	 * @param phrase
	 */
	public HiddenText(String phrase) 
	{	// TODO Auto-generated constructor stub
		word = new String(phrase);
		setLetters(word.toCharArray());
		for (int i = 0; i < letters.length; i++) 
		{
			if (Character.isLetter(letters[i]))
			{ 
				letters[i]='*';
			} 
		}
	}
	/**
	 * 
	 * @return displayed form of the hidden text
	 */
	public char[] getDisplayedText()
	{
		return letters;
	}
	/**
	 *  Description: Updates the displayed text to reveal all hidden letters.
	 */
	public void updateAllRemaining()
	{
		letters = new char[word.length()];
		letters = word.toCharArray();
	}
	/**
	 * 
	 * @return number of consonant positions not yet identified
	 */
	public int countHiddenConsonants()
	{
		int c = 0;
		for(int i = 0; i<letters.length; i++)
		{
			if(letters[i]=='*'&&!isVowel(word.charAt(i)))
			{
				c++;
			}
		}
		return c;
	}
	/**
	 * 
	 * @return number of characters positions not yet identified
	 */
	public int countHiddenLetters()
	{
		int c = 0;
		for(int i = 0; i<letters.length; i++)
		{
			if(letters[i]=='*')
			{
				c++;
			}
		}
		return c;
	}
	/**
	 * 
	 * @param guess
	 */
	public void update(char guess)
	{
		for (int i = 0; i < letters.length; i++) 
		{
			if (guess==word.charAt(i)) 
			{
				letters[i]=word.charAt(i);
			} 
		}
	}
	/**
	 * 
	 * @return the hidden text
	 */
	public String getHiddenText()
	{
		return word;
	}
	/**
	 * 
	 * @param letters
	 */
	private void setLetters(char[] letters)
	{
		this.letters = letters;
	}
	/**
	 * 
	 * @param guess
	 * @return number of occurrences of the given character in the hidden text
	 */
	public int letterCount(char guess)
	{
		int count = 0;
		for (int i = 0; i < letters.length; i++) 
		{
			if (guess==word.charAt(i)) 
			{
				count++;
			} 
		}
		return count;
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
	
	
}


