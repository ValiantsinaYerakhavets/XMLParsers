package xml.beans;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Film 
{
	private String title;
	private String director;
	private int year;
	private String country;
	private List<Genre> genre; 
	private Duration duration;
	private Map<String, Actor> cast;
	
	public Film() { }
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setDirector(String director)
	{
		this.director = director;
	}
	
	public String getDirector()
	{
		return this.director;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}

	public int getYear()
	{
		return this.year;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getCountry()
	{
		return this.country;
	}
	
	public void setGenre(List<Genre> genre)
	{
		this.genre = genre;
	}
	
	public List<Genre> getGenre()
	{
		return this.genre;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}
	
	public Duration getDuration()
	{
		return this.duration;
	}
	
	public void setCast(Map<String, Actor> cast)
	{
		this.cast = cast;
	}
	
	public Map<String, Actor> getCast()
	{
		return this.cast;
	}
	
}
