package es.udc.ws.movies.service.http.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationFacade;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.rest.xml.ExceptionCodes;
import es.udc.ws.movies.xml.MovieXMLConversor;
import es.udc.ws.util.exceptions.ParsingException;

@SuppressWarnings("serial")
public class AddMovieServlet extends HttpServlet {
          
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        
        MovieInformationTO movieInformation;
        
        /* Get movie from request. */
        try {
            movieInformation =
                MovieXMLConversor.toMovieInformation(request.getInputStream());
        } catch (ParsingException e) {          
            ServletUtils.writeServiceResponse(
                    ExceptionCodes.XML_INCORRECT_FORMAT,
                e.getMessage(), response);            
            return;            
        }
        
        /* Add movie to repository. */
        Long identifier;
        
        try {
            identifier = 
                MovieInformationFacade.getInstance().addMovie(movieInformation);
        } catch (MovieInformationException e) {
            ServletUtils.writeServiceResponse(
                    ExceptionCodes.INCORRECT_MOVIE_INFORMATION,
                e.getMessage(), response);            
            return;
        }
      
        /* Generate response. */
        Element identifierElement = new Element("identifier", 
                MovieXMLConversor.XML_NS);
        
        identifierElement.setText(identifier.toString());        
        ServletUtils.writeServiceResponse(identifierElement, response);
               
    }

}
