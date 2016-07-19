package xml.parsers.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import xml.beans.Film;

public class SAXParser 
{
	private List<Film> films;
	
	public SAXParser()
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
		try 
		{
			XMLReader reader = XMLReaderFactory.createXMLReader();
			FilmSaxHandler handler = new FilmSaxHandler();
			reader.setContentHandler(handler);
		
			reader.parse(new InputSource("films.xml"));
			
			reader.setFeature("http://xml.org/sax/features/validation", true);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setFeature("http://xml.org/sax/features/string-interning", true);
			reader.setFeature("http://apache.org/xml/features/validation/schema", false);
			
			films = handler.getFilms();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
	}
}
