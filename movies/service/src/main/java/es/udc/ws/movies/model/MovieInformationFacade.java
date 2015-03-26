package es.udc.ws.movies.model;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.configuration.MissingConfigurationParameterException;
import es.udc.ws.util.configuration.UnavailableConfigurationParametersException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * Provides operations to access the repository of information about movies. The
 * implementation maintains <code>MovieInformationTO</code>s in property files
 * (with the format specified by <code>java.util.Properties</code>). In a real
 * application, <code>MovieInformation</code> would be saved in a database.
 */
public class MovieInformationFacade {

/*
 * In order to keep the implementation easy to understand, it does not check
 * for nulls in the fields of "MovieInformation" and the result of
 * "Property.getProperty". However, try-catch blocks have been inserted in
 * order to throw an "MovieInformationFacadeException" if a null is detected.
 */

    /* Property names. */
    private final static String IDENTIFIER_PROPERTY_NAME = "identifier";
    private final static String TITLE_PROPERTY_NAME = "title";
    private final static String RUNTIME_PROPERTY_NAME = "runtime";
    private final static String RELEASE_DATE_PROPERTY_NAME = "releaseDate";
    private final static String DIRECTOR_NAMES_PROPERTY_NAME = "directorNames";
    private final static String ACTORS_NAMES_PROPERTY_NAME = "actorNames";
    private final static String GENRES_PROPERTY_NAME = "genres";
    private final static String SYNOPSIS_PROPERTY_NAME = "synopsis";

    /* Sequence file name. */
    private final static String SEQUENCE_FILE_SIMPLE_NAME = "sequence";

    /* Configuration parameters. */
    private final static String DIRECTORY_PARAMETER =
            "MovieInformationFacade/directoryName";

    private String directoryName;
    private static MovieInformationFacade instance = 
            new MovieInformationFacade();

    private MovieInformationFacade() {
    }

    public static MovieInformationFacade getInstance() {
        return instance;
    }

    public synchronized List<MovieInformationTO> findMoviesByReleaseDate(
            Calendar releaseDate) throws MovieInformationFacadeException {

        List<MovieInformationTO> movieInformationList;

        try {

            List<Long> movieInformationIdentifiers = 
                    getAllMovieInformationIdentifiers();
            movieInformationList = new ArrayList<MovieInformationTO>();

            for (Long i : movieInformationIdentifiers) {

                MovieInformationTO movieInformation = findMovie(i);

                if (DateOperations.datesAreEqual(
                        movieInformation.getReleaseDate(), releaseDate)) {
                    movieInformationList.add(movieInformation);
                }

            }

        } catch (Exception e) {
            throw new MovieInformationFacadeException(e);
        }

        return movieInformationList;

    }

    public synchronized Long addMovie(MovieInformationTO movieInformation)
            throws MovieInformationException, MovieInformationFacadeException {

        /* Validate movieInformation. */
        validateMovieInformation(movieInformation, false);

        /* Add movie. */
        Long identifier;

        try {

            identifier = generateIdentifier();
            storeMovie(movieInformation, identifier);

        } catch (Exception e) {
            throw new MovieInformationFacadeException(e);
        }

        return identifier;

    }

    public synchronized void updateMovie(MovieInformationTO movieInformation)
            throws InstanceNotFoundException, MovieInformationException,
            MovieInformationFacadeException {

        /* Validate movieInformation. */
        validateMovieInformation(movieInformation, true);

        Long identifier = movieInformation.getIdentifier();

        /* Check if movie exists. */
        if (!movieExists(identifier)) {
            throw new InstanceNotFoundException(identifier,
                    MovieInformationTO.class.getName());
        }

        /* Update movie. */
        try {
            storeMovie(movieInformation, identifier);
        } catch (Exception e) {
            throw new MovieInformationFacadeException(e);
        }

    }

    public synchronized void removeMovie(Long movieInformationIdentifier)
            throws InstanceNotFoundException, MovieInformationFacadeException {

        /* Check if movie exists. */
        if (!movieExists(movieInformationIdentifier)) {
            throw new InstanceNotFoundException(movieInformationIdentifier,
                    MovieInformationTO.class.getName());
        }

        /* Remove movie. */
        File file = new File(getMovieFileName(movieInformationIdentifier));
        if (!file.delete()) {
            throw new MovieInformationFacadeException("can not delete" +
                    " movie with identifier = " + movieInformationIdentifier);
        }

    }

    public synchronized MovieInformationTO findMovie(Long movieInformationIdentifier)
            throws InstanceNotFoundException, IOException {

        FileInputStream movieInformationFile = null;

        if (!movieExists(movieInformationIdentifier)) {
            throw new InstanceNotFoundException(movieInformationIdentifier,
                    MovieInformationTO.class.getName());
        }

        try {

            String fileName = getMovieFileName(movieInformationIdentifier);
            movieInformationFile = new FileInputStream(fileName);
            Properties properties = new Properties();
            properties.load(movieInformationFile);

            return fromProperties(properties);

        } finally {
            if (movieInformationFile != null) {
                movieInformationFile.close();
            }
        }

    }

    private List<Long> getAllMovieInformationIdentifiers() {

        String[] movieInformationIdentifiersAsString =
                new File(getDirectoryName()).list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        try {
                            Integer.parseInt(name);
                            return true;
                        } catch (Exception ex) {
                            return false;
                        }
                    }
                });
        List<Long> movieInformationIdentifiers = new ArrayList<Long>();

        for (int i = 0; i < movieInformationIdentifiersAsString.length; i++) {
            Long movieInformationIdentifier = new Long(
                    movieInformationIdentifiersAsString[i]);
            movieInformationIdentifiers.add(movieInformationIdentifier);
        }

        return movieInformationIdentifiers;

    }

    private void storeMovie(MovieInformationTO movieInformation, 
                            Long identifier)
            throws IOException {

        FileOutputStream movieInformationFile = null;
        try {

            movieInformationFile = 
                    new FileOutputStream(getMovieFileName(identifier));
            Properties properties = toProperties(movieInformation, identifier);
            properties.store(movieInformationFile, null);

        } finally {

            if (movieInformationFile != null) {
                movieInformationFile.close();
            }

        }

    }

    private String getDirectoryName()
            throws MissingConfigurationParameterException,
            UnavailableConfigurationParametersException {

        if (directoryName == null) {
            directoryName = ConfigurationParametersManager.getParameter(
                    DIRECTORY_PARAMETER);
        }

        return directoryName;

    }

    private boolean movieExists(Long movieInformationIdentifier) {
        File file = new File(getMovieFileName(movieInformationIdentifier));
        return file.exists();
    }

    private String getMovieFileName(Long movieInformationIdentifier)
            throws MissingConfigurationParameterException,
            UnavailableConfigurationParametersException {

        return getDirectoryName() + File.separator +
                movieInformationIdentifier;

    }

    private String getSequenceFileName()
            throws MissingConfigurationParameterException,
            UnavailableConfigurationParametersException {

        return getDirectoryName() + File.separator +
                SEQUENCE_FILE_SIMPLE_NAME;

    }

    private Properties toProperties(MovieInformationTO movieInformation,
                                    Long identifier) {

        Properties properties = new Properties();

        String identifierAsString = identifier.toString();
        String runtimeAsString =
                new Short(movieInformation.getRuntime()).toString();

        properties.setProperty(IDENTIFIER_PROPERTY_NAME, identifierAsString);
        properties.setProperty(TITLE_PROPERTY_NAME,
                movieInformation.getTitle());
        properties.setProperty(RUNTIME_PROPERTY_NAME, runtimeAsString);
        addDate(properties, RELEASE_DATE_PROPERTY_NAME,
                movieInformation.getReleaseDate());
        addListOfValues(properties, DIRECTOR_NAMES_PROPERTY_NAME,
                movieInformation.getDirectorNames());
        addListOfValues(properties, ACTORS_NAMES_PROPERTY_NAME,
                movieInformation.getActorNames());
        addListOfValues(properties, GENRES_PROPERTY_NAME,
                movieInformation.getGenres());
        properties.setProperty(SYNOPSIS_PROPERTY_NAME,
                movieInformation.getSynopsis());

        return properties;

    }

    private MovieInformationTO fromProperties(Properties properties) {

        String identifierAsString =
                properties.getProperty(IDENTIFIER_PROPERTY_NAME);
        Long identifier = new Long(identifierAsString);
        String title = properties.getProperty(TITLE_PROPERTY_NAME);
        String runtimeAsString =
                properties.getProperty(RUNTIME_PROPERTY_NAME);
        short runtime = Short.parseShort(runtimeAsString);
        Calendar releaseDate = getDate(properties, RELEASE_DATE_PROPERTY_NAME);
        List<String> directorNames = getListOfString(properties,
                DIRECTOR_NAMES_PROPERTY_NAME);
        List<String> actorNames = getListOfString(properties,
                ACTORS_NAMES_PROPERTY_NAME);
        List<String> genres = getListOfString(properties, GENRES_PROPERTY_NAME);
        String synopsis = properties.getProperty(SYNOPSIS_PROPERTY_NAME);

        return new MovieInformationTO(identifier, title, runtime, releaseDate,
                directorNames, actorNames, 
                GenreOperations.toListOfGenres(genres), synopsis);

    }

    @SuppressWarnings("rawtypes")
	private void addListOfValues(Properties properties, String propertyName,
                                 List propertyValues) {

        int i = 0;

        for (Object v : propertyValues) {
            properties.setProperty(propertyName + i, v.toString());
            i++;
        }

    }

    private List<String> getListOfString(Properties properties, String propertyName) {

        List<String> listOfValues = new ArrayList<String>();
        int i = 0;
        String propertyValue;

        do {

            propertyValue = properties.getProperty(propertyName + i);

            if (propertyValue != null) {
                listOfValues.add(propertyValue);
                i++;
            }

        } while (propertyValue != null);

        return listOfValues;

    }

    private void addDate(Properties properties, String propertyName,
                         Calendar date) {

        String day = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
        String month = new Integer(
                date.get(Calendar.MONTH) - Calendar.JANUARY + 1).toString();
        String year = new Integer(date.get(Calendar.YEAR)).toString();

        properties.setProperty(propertyName + ".day", day);
        properties.setProperty(propertyName + ".month", month);
        properties.setProperty(propertyName + ".year", year);

    }

    private Calendar getDate(Properties properties, String propertyName) {

        String day = properties.getProperty(propertyName + ".day");
        String month = properties.getProperty(propertyName + ".month");
        String year = properties.getProperty(propertyName + ".year");

        return DateOperations.getDate(day, month, year);

    }

    private Long generateIdentifier() throws IOException {

        DataInputStream in = null;
        DataOutputStream out = null;
        long currentIdentifier = 0;
        File sequenceFile = new File(getSequenceFileName());

        /* Get current value (still not used). */
        if (sequenceFile.exists()) {

            try {
                in = new DataInputStream(
                        new FileInputStream(getSequenceFileName()));
                currentIdentifier = in.readLong();
            } finally {
                if (in != null) {
                    in.close();
                }
            }

        }

        /* Write next value. */
        try {
            out = new DataOutputStream(
                    new FileOutputStream(getSequenceFileName()));
            out.writeLong(++currentIdentifier);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        /* Return current value. */
        return currentIdentifier;

    }

    private static void validateMovieInformation(
            MovieInformationTO movieInformation, boolean forUpdate)
            throws MovieInformationException {

        if (forUpdate && movieInformation.getIdentifier() == null) {
            throw new MovieInformationException("Identifier is null");
        }

        if (movieInformation.getRuntime() <= 0) {
            throw new MovieInformationException("Runtime <= 0");
        }

        if (movieInformation.getDirectorNames().isEmpty()) {
            throw new MovieInformationException("Director names not specified");
        }

        if (movieInformation.getActorNames().isEmpty()) {
            throw new MovieInformationException("Actor names not specified");
        }

        if (movieInformation.getGenres().isEmpty()) {
            throw new MovieInformationException("Genres not specified");
        }

    }

    /* ------------------------ Test code -------------------------------- */

    /**
     * Convenience constructor for testing purposes.
     */
    private MovieInformationFacade(String directoryName) {
        this.directoryName = directoryName;
    }

    private void deleteSequenceFile() {

        File file = new File(getSequenceFileName());

        if (!file.delete()) {
            throw new RuntimeException("can not delete sequence file");
        }

    }

    public static void main(String[] args) {

        try {

            /* Create "movieInformation1". */
            List<String> directorNames1 =
                    java.util.Arrays.asList("Director1-1", "Director1-2");
            List<String> actorNames1 =
                    java.util.Arrays.asList("Actor1-1", "Actor1-2", "Actor1-3");
            List<MovieInformationTO.Genre> genres1 =
                    java.util.Arrays.asList(MovieInformationTO.Genre.COM);
            Calendar releaseDate1 = Calendar.getInstance();

            MovieInformationTO movieInformation1 =
                    new MovieInformationTO(new Long(1), "Title1", (short) 120,
                            releaseDate1, directorNames1, actorNames1, genres1,
                            "Title1 talks about ...");

            /* Create "movieInformation2". */
            List<String> directorNames2 =
                    java.util.Arrays.asList("Director2-1", "Director2-2");
            List<String> actorNames2 =
                    java.util.Arrays.asList("Actor2-1", "Actor2-2", "Actor2-3");
            List<MovieInformationTO.Genre> genres2 = java.util.Arrays.asList(
                    MovieInformationTO.Genre.HOR, MovieInformationTO.Genre.THR);
            Calendar releaseDate2 = (Calendar) releaseDate1.clone();

            MovieInformationTO movieInformation2 =
                    new MovieInformationTO(new Long(2), "Title2", (short) 120,
                            releaseDate2, directorNames2, actorNames2, genres2,
                            "Title2 talks about ...");

            /* Create "movieInformation3". */
            List<String> directorNames3 =
                    java.util.Arrays.asList("Director3-1", "Director3-2");
            List<String> actorNames3 =
                    java.util.Arrays.asList("Actor3-1", "Actor3-2", "Actor3-3");
            List<MovieInformationTO.Genre> genres3 = java.util.Arrays.asList(
                    MovieInformationTO.Genre.ROM, MovieInformationTO.Genre.SFI);
            Calendar releaseDate3 = Calendar.getInstance();
            releaseDate3.setTime(releaseDate3.getTime());
            releaseDate3.add(Calendar.DAY_OF_MONTH, 7); // One week later.

            MovieInformationTO movieInformation3 =
                    new MovieInformationTO(new Long(3), "Title3", (short) 120,
                            releaseDate3, directorNames3, actorNames3, genres3,
                            "Title3 talks about ...");

            /* Create an instance of this class. */
            String directory = "MovieInformation";
            MovieInformationFacade movieInformationFacade =
                    new MovieInformationFacade(directory);

            /* Create movie information directory. */
            File movieInformationDirectory =
                    new File(directory);
            if (!movieInformationDirectory.mkdir()) {
                throw new Exception("can not create movie information" +
                        " directory");
            }

            /* Test 'addMovie(MovieInformationTO)'. */
            System.out.println("Testing 'addMovie(MovieInformationTO)'");
            movieInformationFacade.addMovie(movieInformation1);
            movieInformationFacade.addMovie(movieInformation2);
            movieInformationFacade.addMovie(movieInformation3);

            /* Test 'findMoviesByReleaseDate(Calendar)'. */
            System.out.println("Testing 'findMoviesByReleaseDate(Calendar)'");
            List<MovieInformationTO> movieInformationList2 =
                    movieInformationFacade.findMoviesByReleaseDate(
                    Calendar.getInstance());

            for (MovieInformationTO m : movieInformationList2) {
                System.out.println(m);
                System.out.println("--------------");
            }

            /* Test 'updateMovie(MovieInformationTO)'. */
            System.out.println("Testing 'updateMovie(MovieInformationTO)'");
            movieInformation1.setTitle(movieInformation1.getTitle() + "-NEW");
            movieInformationFacade.updateMovie(movieInformation1);
            System.out.println(movieInformationFacade.findMovie(
                    movieInformation1.getIdentifier()));

            /* Test 'removeMovie(MovieInformationTO)'. */
            System.out.println("Testing 'removeMovie(MovieInformationTO)'");
            movieInformationFacade.removeMovie(movieInformation1.getIdentifier());
            movieInformationFacade.removeMovie(movieInformation2.getIdentifier());
            movieInformationFacade.removeMovie(movieInformation3.getIdentifier());
            movieInformationFacade.deleteSequenceFile();
            if (!movieInformationDirectory.delete()) {
                throw new Exception("Can not remove movie information" +
                        " directory");
            }

            System.out.println("Tests OK");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("REMEMBER TO REMOVE MOVIE INFORMATION" +
                    " DIRECTORY !!!");
        }

    }

}
