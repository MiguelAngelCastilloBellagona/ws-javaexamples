package es.udc.ws.movies.service.http.servlets;

import es.udc.ws.movies.model.DateOperations;
import es.udc.ws.movies.model.MovieInformationFacade;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.rest.xml.ExceptionCodes;
import es.udc.ws.movies.xml.MovieXMLConversor;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FindMoviesByReleaseDateServlet extends HttpServlet {
          
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        
        /* Get releaseDate. */  
        Calendar releaseDate;
        
        try {

            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            
            releaseDate = DateOperations.getDate(day, month, year);
            
        } catch (Exception e) {                      
            ServletUtils.writeServiceResponse(
                    ExceptionCodes.INCORRECT_PARAMETERS, 
                    "Incorrect/missing 'day', 'month', or 'year' parameter", 
                    response);            
            return;            
        }
                   
        /* Find movies. */
        List<MovieInformationTO> movieInformationList = 
            MovieInformationFacade.getInstance()
                .findMoviesByReleaseDate(releaseDate);        
        
        /* Generate response. */
        ServletUtils.writeServiceResponse(MovieXMLConversor
                .toXML(movieInformationList), response);  
            
    }
    
}
