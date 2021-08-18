package hw2;

public class Player
{
	private int globalMoney;
	private int money;
	private String name;

	/**
	 * 
	 * @param name
	 */
	public Player(String name)
	{
		globalMoney = 0;
		money = 0;
		this.name = name;
	}

	/**
	 * Description: Increases the player's round balance by the given amount. This method does not check whether the amount to add is positive.
	 * @param int m
	 */
	public void addToRoundBalance(int m)
	{
		money += m;
	}
	/**
	 *  Description: Resets the player's round balance to zero
	 */
	public void clearRoundBalance()
	{
		money = 0;
	}
	/**
	 * Description: Subtracts m from player's money
	 * 
	 * @param int m
	 */
	public void subtractFromRoundBalance(int m)
	{
		money -= m;
	}
	/**
	 * @return the money
	 */
	public int getRoundBalance()
	{
		return money;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @return the globalMoney
	 */
	public int getGameBalance()
	{
		return globalMoney;
	}
	/**
	 *  Description: Adds the player's round balance to the game balance (without modifying the round balance).
	 */
	public void winRound()
	{
		globalMoney = globalMoney+money;
	}
}