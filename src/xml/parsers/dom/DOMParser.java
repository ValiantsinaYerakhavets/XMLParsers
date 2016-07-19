package xml.parsers.dom;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import xml.beans.Actor;
import xml.beans.CommonProperties;
import xml.beans.Film;
import xml.beans.Genre;

public class DOMParser implements CommonProperties
{
	private List<Film> films;
	
	public DOMParser()
	{
		films = new ArrayList<Film>();
	}
	
	public List<Film> getFilms()
	{
		return this.films;
	}
	
	////////////////////////////
	
	public void startDOM()
	{		
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try 
		{
			document = builder.build(inputFile);
		} 
		catch (JDOMException | IOException e) 
		{
			e.printStackTrace();
		}
		
		Element root = document.getRootElement();
		List<Element> countries = root.getChildren();
		Iterator<Element> it = countries.iterator();
		
		while(it.hasNext())
		{
			Element temp = it.next();
			String country = temp.getAttributeValue("name");
			List<Element> films = temp.getChildren();
			parseFilm(films, country);
		}
	}
	
	void parseFilm(List<Element> filmsElement, String country)
	{
		Iterator<Element> it = filmsElement.iterator();
		
		while(it.hasNext())
		{
			Element temp = it.next();
			Film film = new Film();
			film.setCountry(country);
			film.setTitle(temp.getAttributeValue("title"));
			
			List<Element> e = temp.getChildren();
			
			film.setYear(Integer.parseInt(e.get(0).getText()));
			film.setDirector(e.get(1).getText());
			
			List<Element> genres = e.get(2).getChildren();
			parseGenre(film, genres);
			
			film.setDuration(Duration.ofMinutes(Integer.parseInt(e.get(3).getText())));
			
			List<Element> cast = e.get(4).getChildren();
			parseCast(film, cast);
			
			films.add(film);
		}
	}
	
	void parseGenre(Film film, List<Element> genres)
	{
		Iterator<Element> it = genres.iterator();
		List<Genre> result = new ArrayList<Genre>();
		
		while(it.hasNext())
		{
			Element temp = it.next();
			result.add(Genre.valueOf(temp.getText().toUpperCase()));
		}
		film.setGenre(result);
	}
	
	void parseCast(Film film, List<Element> cast)
	{
		Iterator<Element> it = cast.iterator();
		TreeMap<String, Actor> result = new TreeMap<String, Actor>();
		
		while(it.hasNext())
		{
			Element temp = it.next();
			String role = temp.getAttributeValue("role");
			
			Actor actor = new Actor();
			List<Element> actorValues = temp.getChildren();
			actor.setName(actorValues.get(0).getText());
			
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try 
			{
				date = format.parse(actorValues.get(1).getText());
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			actor.setDate(date);
			
			actor.setBirthplace(actorValues.get(2).getText());
			
			int total = Integer.parseInt(actorValues.get(3).getText());
			actor.setTotalFilms(total);
			
			result.put(role, actor);
		}
		film.setCast(result);
	}

}
