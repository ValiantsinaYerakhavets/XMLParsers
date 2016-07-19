package xml.parsers.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import xml.beans.Actor;
import xml.beans.CommonProperties;
import xml.beans.Film;
import xml.beans.Genre;
import xml.beans.TagName;

public class StAXParser implements CommonProperties
{
	private List<Film> films;
	
	public StAXParser()
	{
		films = new ArrayList<Film>();
	}
	
	public List<Film> getFilms()
	{
		return this.films;
	}
	
	////////////////////////////////////
	
	public void startStAX()
	{
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try
		{
			InputStream input = new FileInputStream(inputFile);
			XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
			
			films = process(reader);
		}
		catch(XMLStreamException | FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private List<Film> process(XMLStreamReader reader) throws XMLStreamException
	{
		Film film = null;
		TagName tag = null;
		Actor actor = null;
		String country = null;
		List<Genre> genres = null;
		String role = null;
		TreeMap<String, Actor> cast = null;
		
		String localName = null;
		
		while(reader.hasNext())
		{
			int type = reader.next();
			switch(type)
			{
			case XMLStreamConstants.START_ELEMENT:
				localName = reader.getLocalName();
				tag = TagName.valueOf(localName.toUpperCase().replaceAll(" ", "_"));
				
				switch(tag)
				{
				case COUNTRY:
					country = reader.getAttributeValue(null, "name");
					break;
				case FILM:
					film = new Film();
					film.setTitle(reader.getAttributeValue(null, "title"));
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
					role = reader.getAttributeValue(null, "role");
				default:
					break;
				}
				break;
				
			case XMLStreamConstants.CHARACTERS:
				String text = reader.getText().trim();
				
				if(text.isEmpty())
				{
					break;
				}
				
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
				default:
					break;
				}
				break;
			
			case XMLStreamConstants.END_ELEMENT:
				localName = reader.getLocalName();
				tag = TagName.valueOf(localName.toUpperCase().replaceAll(" ", "_"));
				
				switch(tag)
				{
				case GENRE:
					film.setGenre(genres);
					genres = null;
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
				break;
			}
		}
		return films;
	}
}
