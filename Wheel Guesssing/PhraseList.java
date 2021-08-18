package hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PhraseList
{
	private File file;
	/**
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public PhraseList(String filename) throws FileNotFoundException 
	{
		// TODO Auto-generated constructor stub
		file = new File(filename);
	}
	/**
	 * 
	 * @param nextInt
	 * @return the nth phrase
	 */
	public String getPhrase(int nextInt) 
	{
		BufferedReader br=null;
		String Word = null;
		try {
			br=new BufferedReader(new FileReader(file));
			int count=1;
			
			while((Word=br.readLine())!=null) {
				if(count==nextInt) {
					
					br.close();
				}
				count++;
			}
			br.close();
		}
		catch (FileNotFoundException e) {
	        System.out.println("File not found: " + file.toString());
	    } catch (IOException e) {}
		return Word;
	}
	/**
	 * 
	 * @return number of strings in this list
	 */
	public int getSize()
	{
		int Totallines=-1;
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(file));
			while(br.readLine()!=null)
			{
				Totallines++;
			}
			br.close();
	    } catch (IOException e) {}
		return Totallines;
	}

}
