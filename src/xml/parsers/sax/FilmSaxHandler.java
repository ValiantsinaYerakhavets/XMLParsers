package xml.parsers.sax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xml.beans.Actor;
import xml.beans.Film;
import xml.beans.Genre;
import xml.beans.TagName;

public class FilmSaxHandler extends DefaultHandler
{
	private List<Film> films;
	private StringBuilder text;
	
	private Film film;
	private String country;
	private Actor actor;
	private List<Genre> genres;
	private Map<String, Actor> cast;
	private String role;
	
	public List<Film> getFilms()
	{
		return this.films;
	}
	
	public void startDocument()
	{
		
	}
	
	public void endDocument()
	{
		
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		text = new StringBuilder();
		
		TagName tag = TagName.valueOf(qName.toUpperCase().replaceAll(" ", "_"));
		
		switch(tag)
		{
		case FILMS:
			films = new ArrayList<Film>();
			break;
		case COUNTRY:
			country = attributes.getValue("name");
			break;
		case FILM:
			film = new Film();
			film.setTitle(attributes.getValue("title"));
			film.setCountry(country);
			break;
		case GENRE:
			genres = new ArrayList<Genre>();
			break;
		case CAST:
			cast = new TreeMap<String, Actor>();
			break;
		case ACTOR:
			actor = new Actor();
			role = attributes.getValue("role");
		default:
			break;
		}
	}
	
	public void characters(char[] buffer, int start, int length)
	{
		text.append(buffer, start, length);
		text.trimToSize();
	}
	
	public void endElement(String uri, String localName, String qName) 
			throws SAXException 
	{
		TagName tag = TagName.valueOf(qName.toUpperCase().replaceAll(" ", "_"));
		switch(tag)
		{
		case YEAR:
			film.setYear(Integer.parseInt(text.toString()));
			break;
		case DIRECTOR:
			film.setDirector(text.toString());
			break;
		case GENRE_NAME:
			genres.add(Genre.valueOf(text.toString().toUpperCase()));
			break;
		case GENRE:
			film.setGenre(genres);
			genres = null;
			break;
		case DURATION:
			int min = Integer.parseInt(text.toString());
			film.setDuration(Duration.ofMinutes(min));
			break;
		case NAME:
			actor.setName(text.toString());
			break;
		case BIRTHDAY:
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try 
			{
				date = format.parse(text.toString());
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			actor.setDate(date);
			break;
		case BIRTHPLACE:
			actor.setBirthplace(text.toString());
			break;
		case TOTAL_FILMS:
			int total = Integer.parseInt(text.toString());
			actor.setTotalFilms(total);
			break;
		case ACTOR:
			cast.put(role, actor);
			actor = null;
			role = null;
			break;
		case CAST:
			film.setCast(cast);
			break;
		case FILM:
			films.add(film);
			film = null;
			break;
		default:
			break;
		}
	}
	
}
