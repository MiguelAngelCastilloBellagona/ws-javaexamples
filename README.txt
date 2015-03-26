              **********  WS-JavaExamples v2.0.4 **********

           Alberto Pan <apan@udc.es>, Jose Losada <jlosada@udc.es>
          Department of Information and Communications Technologies
                          University Of A Coruña
         https://campusvirtual.udc.es/moodle/course/view.php?id=49674

                        
Contents
--------

1. Software requirements
2. Basic installation and configuration of software packages
3. Environment variables
4. Building the examples
5. Executing the examples
6. Running Web services
7. Rapid Web service development

1. Software requirements
   ---------------------
   
   * Operating system. It should be possible to compile and execute this
     distribution in Unix-like and MS-Windows operating systems.
     
     Most of the instructions given in this file assume a Unix-like operating
     system, but they are similar for MS-Windows operating systems with
     minor modifications (e.g. "\" instead of "/", %ENVIRONMENT_VARIABLE%
     instead of $ENVIRONMENT_VARIABLE, ".bat" instead of ".sh", etc.).
     
   * An implementation of JDK 1.6.0 or higher.
   
   * Maven 2.2.0 or higher.
   
     To manage the project build.

   * To read the sources, you should use a text editor configured with
     *tab size* to *four* blanks.

   Even though they are *not* required to build and execute the examples, we
   have also used Apache Tomcat 6.0.20 (as an example of an application server
   for production) and Eclipse Web Tools Platform (as IDE).
        
2. Basic installation and configuration of software packages
   ---------------------------------------------------------
   
   Most software packages needed by WS-JavaExamples are very easy to install
   and configure for development. However, some of them must be installed with
   care. In this section it is commented how to do a basic installation and
   configuration of such packages. Check section 3 for environment variables
   when needed.
   
   2.1 General comments
       ----------------
       
   Whenever a software package is distributed both as a ".tar.gz" or ".zip" 
   file, Unix users should use the ".tar.gz" file and MS-Windows users the 
   ".zip" file. ".tar.gz" files preserve file permissions, and in particular,
   execution permissions in scripts. "tar.gz" files are not always unpacked
   properly with WinZip (e.g. empty directories are not created).
      
   2.2 Basic installation and configuration of Tomcat
       ----------------------------------------------
       
   After unpacking apache-tomcat-6.0.20.tar.gz:
   
   - You must have write permissions on the following directories (and the
     files/subdirectories they contain): conf, logs, temp, webapps and
     work.
     
   - Modify conf/tomcat-users.xml in order to create a user
     in the role of "manager". For example, the following line declares
     the user "tomcat" (with password "tomcat") in the roles of
     "tomcat" and "manager".
     
     <user name="tomcat" password="tomcat" roles="tomcat,manager"/>
     
     This allows you to access Tomcat Manager Web application as user "tomcat"
     and password "tomcat".

     * NOTE: By default, "tomcat" user is commented in conf/tomcat-users.xml.
    
3. Environment variables
   ---------------------
   
   This is an example of a "~/.bashrc" file. Adapt to your environment.
   
# -----------------------------------------------------------------------------
# Programming Tools.
# -----------------------------------------------------------------------------

# J2SE
# NOTE: This step is not necessary for Mac OS X 10.5.X (Java 1.5 is already in
# the PATH).
JAVA_HOME=/opt/jdk1.6.0_18
export JAVA_HOME
# For convenience.
PATH=$JAVA_HOME/bin:$PATH

# Maven
# NOTE: This step is not necessary for Mac OS X 10.5.X (Maven 2.0.6 is already
# in the PATH)
MAVEN_HOME=/opt/apache-maven-2.2.1
export MAVEN_HOME
PATH=$MAVEN_HOME/bin:$PATH

# -----------------------------------------------------------------------------
# WS-JavaExamples.
# -----------------------------------------------------------------------------
  
In MS-Windows similar environment variables must be defined by using the
control panel (System). For example:
  
JAVA_HOME=C:\java\jdk1.6.0_18

MAVEN_HOME=C:\java\apache-maven-2.2.1

PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%


4. Building the examples
   ---------------------
   
   Set up environment variables (section 3). Execute ". ~/.bashrc" if
   necessary.
   
   + Unpack ws-javaexamples-src-<version>.tar.gz
   
   + cd ws-javaexamples-<version>
   
   + mvn install
   
5. Executing the examples
   ----------------------
   
   5.1 JAXWSTutorial
       --------------
       
   + cd jaxwstutorial/service
   
   + mvn jetty:run   
   
   + Execute the client:
  
   + cd jaxwstutorial/client

   + mvn exec:java -Dexec.mainClass="es.udc.ws.jaxwstutorial.client.StockQuoteProviderClient" -Dexec.args="http://localhost:8080/ws-jaxwstutorial-service/services/StockQuotesService IBM SUN"
   
   5.2 Movies
       ------
       
   + cd movies/service
   
   + mvn jetty:run
      
   + Run clients:

   Select appropriate proxy:

   * cd ../client
   * Specify proxy in "src/main/resources/ConfigurationParameters.properties"
     (the JAXWS-service proxy is selected by default).
   * mvn compile (to copy resources to target/classes).
      
   Add movies to the repository:

   * mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.AdminClient" -Dexec.args="-a src/main/sampledocs/Movie-1.xml src/main/sampledocs/Movie-2.xml src/main/sampledocs/Movie-3.xml src/main/sampledocs/Movie-4.xml"
   
   Search the movies to be released the 19th October 2001:
   
   *  mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.SearchClient" -Dexec.args="7 9 2001"

6. Running Web services
   --------------------

   "jaxwstutorial/service" and "movies/service" modules are examples of Web
   services. The following sections provide instructions to run these services
   with the Jetty plugin for Maven and Tomcat.

   6.1 Jetty plugin for Maven
       ----------------------

   You can use the Jetty plugin for Maven to run a Web service as follows:

   + cd <module>
     Example: cd jaxwstutorial/service

   + mvn jetty:run

   + You can stop Jetty by pressing CTRL-C.

   6.2 Tomcat
       ------
   
   In the following instructions, TOMCAT_HOME is the directory where Tomcat is
   installed.

   + cd <module>
     Example: cd jaxwstutorial/service

   + cp target/<.war file> TOMCAT_HOME/webapps
     Example: cp target/ws-jaxwstutorial-service.war /opt/apache-tomcat-6.0.20/webapps
     
     Tomcat will unpack the WAR file in a directory with the same name as the
     WAR file without the .war extension ("ws-jaxwstutorial-service" in the 
     example).
     
     If the service was deployed previously, remove the Web application
     directory ("ws-jaxwstutorial-service" in the example).

   + cd $TOMCAT_HOME/bin

   + startup.sh
   
   + cd <module>

   + After playing with the service, stop Tomcat by executing "shutdown.sh".

   Other interesting things in Tomcat:

   + Log files. Tomcat maintains log files in "logs" directory. In particular,
     "catalina.out" is the most important one.

   + Tomcat Manager Web application. Available at the following URL:
     http://localhost:8080/manager/html

     Authenticate as user "tomcat" and password "tomcat". It allows you to
     manage Web services deployed in Tomcat.

7. Rapid Web service development
   -----------------------------

   You can use the Jetty plugin for Maven (section 6.1) for rapid Web
   service development by using "mvn jetty:run" on the Maven module. On the 
   one hand, this goal allows to execute a Web service without packing it in
   a WAR file (it understand the source distribution of a Maven project). In 
   fact, you have used it this way in sections 5.1 and 5.2. Note the initial
   part of the service URL will be
   http://localhost:8080/<<pom.xml's artifactId>> (e.g.
   http://localhost:8080/ws-jaxwstutorial-service for JAXWSTutorial service).
   On the other hand, Jetty can detect automatically changes to source files 
   and reloads the Web service in case of changes. The root "pom.xml" 
   configures the plugin to scan files for changes every 5 seconds. By default,
   Jetty scans files in "src/main/webapp/WEB-INF/web.xml", "target/classes" 
   and the libraries the Web service depends on (specified in "pom.xml"). To 
   scan other types of files, you can use the "scanTargets" and
   "scanTargetPatterns" tags in the configuration of the Jetty plugin. See
   http://jetty.mortbay.com/maven-plugin for further details.

   A typical way to execute this plugin is as follows:

   + cd <module>

   + mvn jetty:run

   + When you make a change to a Java source file ("src/main/java") or a 
     resource file ("src/main/resources") from Eclipse, Jetty detects 
     automatically the change because Eclipse generates the result (a ".class"
     file or a copy of the resource) in "target/classes", assuming you have
     created the project with "mvn eclipse:eclipse". If you do not use Eclipse
     or an IDE with a similar behaviour, you have to explicitally execute
     "mvn compile" for the result to be copied to "target/classes".

   + Access the Web service to test changes.

   You can also debug the Web service with an IDE. The are many possibilities
   depending on your IDE (e.g. launching "mvn jetty:run" as an external program
   and using remote debugging, using a Maven plugin for your IDE that allows
   you to run Maven from inside the IDE, etc.).

   To start Jetty on another port, you can set the "jetty.port" system property
   (e.g. "mvn -Djetty.port=8080 jetty:run"). This is useful when several
   Maven modules contain services, since you will have to start one instance
   of Jetty for each module containing a service.
         

