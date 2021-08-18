package hw2;

public class Wheel
{
  /**
   * Constant representing the "bankrupt" wheel segment.
   */
  public static final int BANKRUPT = -1;
  
  /**
   * Constant representing the "free play" wheel segment.
   */
  public static final int FREE_PLAY = 0;
  
  /**
   * Constant representing the "lose a turn" wheel segment.
   */
  public static final int LOSE_A_TURN = 1;

  /**
   * Numeric values for the wheel segments.
   */
  private int curRotation;
  private static final int[] SEGMENT_VALUES = 
  {
      500, // 0 degrees
      900, // 15 degrees
      700,
      300,
      800,
      550,
      400,
      500,
      600,
      350,
      500,
      900,
      BANKRUPT,
      650,
      FREE_PLAY,
      700,
      LOSE_A_TURN,
      800,
      500,
      450,
      500,
      300,
      BANKRUPT,
      5000  // 345 degrees
   };
  
  /**
   *  Description: Constructs a new game wheel with an initial rotation of 359 degrees.
   */
  	public Wheel()
  	{
  		curRotation = 359;
  	}
  	/**
  	 * 
  	 * @return Returns the value for the segment at the wheel's current rotation.
  	 */
  	public int getSegmentValue() 
  	{
  		if(curRotation<15)
  		{
			 return SEGMENT_VALUES[0];
		}
		else if(curRotation<30)
		{
			 return SEGMENT_VALUES[1];
		}
		else if(curRotation<45)
		{
			 return SEGMENT_VALUES[2];
		}
		else if(curRotation<60)
		{
			 return SEGMENT_VALUES[3];
		}
		else if(curRotation<75)
		{
			 return SEGMENT_VALUES[4];
		}
		else if(curRotation<90)
		{
			 return SEGMENT_VALUES[5];
		}
		else if(curRotation<105)
		{
			 return SEGMENT_VALUES[6];
		}
		else if(curRotation<120)
		{
			 return SEGMENT_VALUES[7];
		}
		else if(curRotation<135)
		{
			 return SEGMENT_VALUES[8];
		}
		else if(curRotation<150)
		{
			 return SEGMENT_VALUES[9];
		}
		else if(curRotation<165)
		{
			 return SEGMENT_VALUES[10];
		}
		else if(curRotation<180)
		{
			 return SEGMENT_VALUES[11];
		}
		else if(curRotation<195)
		{
			 return SEGMENT_VALUES[12];
		}
		else if(curRotation<210)
		{
			 return SEGMENT_VALUES[13];
		}
		else if(curRotation<225)
		{
			 return SEGMENT_VALUES[14];
		}
		else if(curRotation<240)
		{
			 return SEGMENT_VALUES[15];
		}
		else if(curRotation<255)
		{
			 return SEGMENT_VALUES[16];
		}
		else if(curRotation<270)
		{
			 return SEGMENT_VALUES[17];
		}
		else if(curRotation<285)
		{
			 return SEGMENT_VALUES[18];
		}
		else if(curRotation<300)
		{
			 return SEGMENT_VALUES[19];
		}
		else if(curRotation<315)
		{
			 return SEGMENT_VALUES[20];
		}
		else if(curRotation<330)
		{
			 return SEGMENT_VALUES[21];
		}
		else if(curRotation<345)
		{
			 return SEGMENT_VALUES[22];
		}
		else
		{
			 return SEGMENT_VALUES[23];
		}
  	}
  	/**
  	 * 
  	 * @return Returns the current rotation of the wheel.
  	 */
  	public int getRotation()
  	{
  		return curRotation;
  	}
  	/**
  	 * 
  	 * @param degrees
  	 */
  	public void spin(int degrees)
  	{
  		curRotation += degrees;
  		curRotation%=360;
  	}
}
