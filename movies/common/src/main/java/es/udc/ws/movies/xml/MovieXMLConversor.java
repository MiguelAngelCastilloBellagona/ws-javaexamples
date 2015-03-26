package es.udc.ws.movies.xml;

import es.udc.ws.movies.model.GenreOperations;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.util.exceptions.ParsingException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class MovieXMLConversor {

    public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/movies/xml");

    private MovieXMLConversor() {
    }

    public static MovieInformationTO toMovieInformation(InputStream in)
            throws ParsingException {

        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element movieElement = document.getRootElement();

            return toMovieInformation(movieElement);

        } catch (Exception e) {
            throw new ParsingException("Error deserializing instance of "
                    + MovieInformationTO.class, e);
        }

    }

    public static List<MovieInformationTO> toMovieInformationList(
            List<Element> movieElements) throws ParsingException {

        try {

            List<MovieInformationTO> movieInformationList = 
                    new ArrayList<MovieInformationTO>();

            for (Element e : movieElements) {
                movieInformationList.add(toMovieInformation(e));
            }

            return movieInformationList;

        } catch (Exception e) {
            throw new ParsingException("Error deserializing instance of List<"
                    + MovieInformationTO.class + '>', e);
        }

    }

    public static void toXML(MovieInformationTO movieInformation, 
                                   OutputStream out)
            throws ParsingException {

        try {

            Element movieElement = toXML(movieInformation);
            Document document = new Document(movieElement);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            outputter.output(document, out);

        } catch (Exception e) {
            throw new ParsingException("Error serializing instance of "
                    + MovieInformationTO.class, e);
        }

    }   
    
    public static List<Element> toXML(
            List<MovieInformationTO> movieInformationList) {

        List<Element> movieElements = new ArrayList<Element>();

        for (MovieInformationTO m : movieInformationList) {
            movieElements.add(toXML(m));
        }

        return movieElements;

    }

    /* ------ Helper methods for XML to Java conversion. -------------------- */
    @SuppressWarnings("unchecked")
	private static MovieInformationTO toMovieInformation(Element movieElement)
            throws DataConversionException {

        Element identifierElement = movieElement.getChild("identifier", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String title = movieElement.getChildTextNormalize("title", XML_NS);
        short runtime = Short.valueOf(
                movieElement.getChildTextTrim("runtime", XML_NS));
        Calendar releaseDate = getReleaseDate(
                movieElement.getChild("releaseDate", XML_NS));
        List<String> directorNames = getTextList(
                movieElement.getChildren("director", XML_NS));
        List<String> actorNames = getTextList(
                movieElement.getChildren("actor", XML_NS));
        List<String> genres = getTextList(
                movieElement.getChildren("genre", XML_NS));
        String synopsis = movieElement
                .getChildTextNormalize("synopsis", XML_NS);

        MovieInformationTO movieInformation = 
                new MovieInformationTO(identifier, title,
                runtime, releaseDate, directorNames, actorNames,
                GenreOperations.toListOfGenres(genres), synopsis);

        return movieInformation;

    }

    private static Calendar getReleaseDate(Element releaseDateElement)
            throws DataConversionException {

        int day = releaseDateElement.getAttribute("day").getIntValue();
        int month = releaseDateElement.getAttribute("month").getIntValue();
        int year = releaseDateElement.getAttribute("year").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);

        return releaseDate;

    }

    private static List<String> getTextList(List<Element> elementList) {

        List<String> textList = new ArrayList<String>();

        for (Element c : elementList) {
            textList.add(c.getTextNormalize());
        }

        return textList;

    }

    public static Element toXML(MovieInformationTO movieInformation) {

        Element movieElement = new Element("movie", XML_NS);

        if (movieInformation.getIdentifier() != null) {
            Element identifierElement = new Element("identifier", XML_NS);
            identifierElement.setText(movieInformation.getIdentifier().toString());
            movieElement.addContent(identifierElement);
        }

        Element titleElement = new Element("title", XML_NS);
        titleElement.setText(movieInformation.getTitle());
        movieElement.addContent(titleElement);

        Element runtimeElement = new Element("runtime", XML_NS);
        runtimeElement.setText(Short.toString(movieInformation.getRuntime()));
        movieElement.addContent(runtimeElement);

        Element releaseDateElement = getReleaseDate(movieInformation.getReleaseDate());
        movieElement.addContent(releaseDateElement);

        List<Element> directorElements =
                getElementList("director", XML_NS, movieInformation.getDirectorNames());
        movieElement.addContent(directorElements);

        List<Element> actorElements =
                getElementList("actor", XML_NS, movieInformation.getActorNames());
        movieElement.addContent(actorElements);

        List<Element> genreElements =
                getElementList("genre", XML_NS, movieInformation.getGenres());
        movieElement.addContent(genreElements);

        Element synopsisElement = new Element("synopsis", XML_NS);
        synopsisElement.setText(movieInformation.getSynopsis());
        movieElement.addContent(synopsisElement);

        return movieElement;

    }

    /* ------------- Helper methods for Java to XML conversion. ------------- */
    private static Element getReleaseDate(Calendar releaseDate) {

        Element releaseDateElement = new Element("releaseDate", XML_NS);
        int day = releaseDate.get(Calendar.DAY_OF_MONTH);
        int month = releaseDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = releaseDate.get(Calendar.YEAR);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));

        return releaseDateElement;

    }

    @SuppressWarnings("rawtypes")
	private static List<Element> getElementList(String elementName,
            Namespace namespace, List textList) {

        List<Element> elementList = new ArrayList<Element>();

        for (Object t : textList) {
            Element element = new Element(elementName, namespace);
            element.setText(t.toString());
            elementList.add(element);
        }

        return elementList;

    }

    /* ------------------------ Test code ----------------------------------- */
    public static void main(String[] args) {

        java.io.FileInputStream input = null;

        try {

            /* XML to Java. */
            System.out.println("------------- XML to Java -------------");
            input = new java.io.FileInputStream(args[0]);
            MovieInformationTO movieInformation = toMovieInformation(input);
            System.out.println(movieInformation);

            /* Java to XML. */
            System.out.println("------------- Java to XML-------------");
            toXML(movieInformation, System.out);

            /* Automatic test. */
            System.out.println("---------- Automatic test-------------");
            java.io.ByteArrayOutputStream xmlOutputStream = 
                    new java.io.ByteArrayOutputStream();
            toXML(movieInformation, xmlOutputStream);
            java.io.ByteArrayInputStream input2 =
                    new java.io.ByteArrayInputStream(
                            xmlOutputStream.toByteArray());
            MovieInformationTO movieInformation2 = toMovieInformation(input2);
            System.out.println();

            System.out.print("Automatic test result: ");
            if (movieInformation2.equals(movieInformation)) {
                System.out.println("SUCCESS");
            } else {
                System.out.println("ERROR");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
