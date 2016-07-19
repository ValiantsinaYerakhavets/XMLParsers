package xml.beans;

import java.util.Date;

public class Actor 
{
	private String name;
	private Date birthday;
	private String birthplace;
	private int totalFilms;
	
	public Actor() { }
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setDate(Date date)
	{
		this.birthday = date;
	}
	
	public Date getDate()
	{
		return this.birthday;
	}
	
	public void setBirthplace(String birthplace)
	{
		this.birthplace = birthplace;
	}
	
	public String getBirthplace()
	{
		return this.birthplace;
	}
	
	public void setTotalFilms(int totalFilms)
	{
		this.totalFilms = totalFilms;
	}
	
	public int getTotalFilms()
	{
		return this.totalFilms;
	}
	
}