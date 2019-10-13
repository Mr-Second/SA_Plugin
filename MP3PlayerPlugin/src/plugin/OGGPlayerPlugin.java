package plugin;

public class OGGPlayerPlugin 
{
	public boolean loadFile(String filepath)
	{
		if (filepath.length()>0 && filepath.endsWith(".ogg"))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public void play()
	{
		System.out.println("The OGG music is Playing！");
	}
	public void stop()
	{
		System.out.println("The OGG music has stopped！");
	}
	public void pause()
	{
		System.out.println("The OGG music has paused！");
	}
}
