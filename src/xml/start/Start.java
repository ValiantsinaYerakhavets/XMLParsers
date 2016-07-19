package xml.start;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import xml.beans.Film;
import xml.parsers.dom.DOMParser;
import xml.parsers.sax.SAXParser;
import xml.parsers.stax.StAXParser;

public class Start 
{
	public static void main(String[] args) throws SAXException, IOException
	{
		SAXParser sax = new SAXParser();
		sax.startStAX();
		List<Film> films = sax.getFilms();
		printFilms("SAX", films);
		
		StAXParser stax = new StAXParser();
		stax.startStAX();
		films = stax.getFilms();
		printFilms("StAX", films);
		
		DOMParser dom = new DOMParser();
		dom.startDOM();
		films = dom.getFilms();
		printFilms("DOM", films);
		
	}
	
	static void printFilms(String parser, List<Film> films)
	{
		System.out.println("******\n" + parser + "'s result\n");
		for(Film f: films)
		{
			System.out.println(ViewHelper.filmToString(f));
			System.out.println();
		}
		System.out.println("*****\n");
	}
}
