package es.udc.ws.movies.service.http.servlets;

import es.udc.ws.movies.model.MovieInformationFacade;
import es.udc.ws.movies.service.rest.xml.ExceptionCodes;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RemoveMovieServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        
        /* Get movie identifier. */
        Long identifier;
        
        try {
            identifier = Long.valueOf(request.getParameter("identifier"));
        } catch (Exception e) {          
            ServletUtils.writeServiceResponse(
                    ExceptionCodes.INCORRECT_PARAMETERS,
                "Incorrect/missing 'identifier' parameter", response);            
            return;            
        }
        
        /* Remove movie from repository. */
        try {
            MovieInformationFacade.getInstance().removeMovie(identifier);
        } catch (InstanceNotFoundException e) {                     
            ServletUtils.writeServiceResponse(
                    ExceptionCodes.INSTANCE_NOT_FOUND,
                e.getMessage(), response);            
            return;            
        }
        
        /* Generate response. */       
        ServletUtils.writeServiceResponse(response);
        
    }

}
