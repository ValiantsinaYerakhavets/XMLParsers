package xml.start;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import xml.beans.Actor;
import xml.beans.Film;
import xml.beans.Genre;

public class ViewHelper 
{
	static String filmToString(Film f)
	{
		StringBuffer s = new StringBuffer("Title:\t\t" + f.getTitle() + "\n");
		
		s.append("Year:\t\t" + f.getYear() + "\n");
		s.append("Country:\t" + f.getCountry() + "\n");
		s.append("Director:\t" + f.getDirector() + "\n");
		
		s.append("Genre:\t\t");
		List<Genre> genres = f.getGenre();
		for(Genre g: genres)
		{
			s.append(g.toString() + " ");
		}
		
		s.append("\nDuration:\t" + f.getDuration().toMinutes() + "\n");
		s.append("Cast\n");
		
		for(Map.Entry<String, Actor> m: f.getCast().entrySet())
		{
			s.append(actorToString(m.getValue()));
			s.append("\tas " + m.getKey() + "\n");
		}
		
		return s.toString();
	}
	
	static String actorToString(Actor a)
	{
		StringBuffer s = new StringBuffer("Actor:\t" + a.getName());
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		s.append(" (" + format.format(a.getDate()) + "; ");
		s.append(a.getBirthplace() + "; ");
		s.append(a.getTotalFilms() + " films)\n");

		return s.toString();
	}
}
