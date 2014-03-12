CS542 Design Patterns
Spring 2014
PROJECT 1 README FILE

Due Date: Sunday, February 23, 2014
Submission Date: Sunday, February 23, 2014
Grace Period Used This Project: 0 Days
Grace Period Remaining: 0 Days
Author(s): Lingjie Meng
e-mail(s): lmeng4@binghamton.edu


PURPOSE:


  Application of design patterns/principles for a simple multi-threaded
  application.


PERCENT COMPLETE:


  I believe I have completed 100% of this project.
  
  Reason to choose ArrayList data structure to store data that read by multiple
  threads over vector: 1. Vector is form Java 1.1, it's thread safe, however slow;
  2. Although ArrayList is not thread safe, but it can be thread safe by 
  Collection.synchronizedList and it's faster than vector.


PARTS THAT ARE NOT COMPLETE:


    None.


BUGS:


	None.


FILES:


  Included with this project are 19 files:
  
  Info.java, the interface of StudentInfo
  StudentInfo.java, the elements of a record from dataFile
  Store.java, the interface of data structure that stores data of dataFile
  RegistrationStore.java, the data structure to store registration data
  Reader.java, the interface to read file
  ReaderFile.java, responsible to read data from files
  Worker.java, the interface to generates multiple threads
  PopulateWorker.java, the file that reads data from dataFile with multi-thread
  SearchWorker.java, the file that reads data from searchFile with multi-thread
  ExceptionHandler.java, interface that handles exceptions
  ExceptionHandlerImp.java, the file that implements ExceptionHandler
  Logger.java, the file that debugs code with different debug values
  Driver.java, the driver file
  Results.java, the file that stores the search results
  README.txt, the text file you are presently reading
  build.xml, the buildfile
  filegenerator.sh, script file to generate dataFile
  dataFile, file contains the registration information
  searchFile, contains words to be searched


SAMPLE OUTPUT:


  bingsuns2% ant run -Darg0=dataFile -Darg1=3 -Darg2=searchFile -Darg3=4 -Darg4=4
  SOME OUTPUT
  WAHT CONSTRUCTORS ARE CALLED
  ===========Results from Driver===========
  SEARCH RESULTS
  BUILD SUCCESSFUL
  Total time: 9 seconds
  bingsuns2% 


TO COMPILE:


  Just extract the files and then do a "ant compile".


TO RUN:


  Please run as: ant run -Darg0=<DATAFILE> -Darg1=<THRDNUM1> -Darg2=<SRCHFILE> -Darg=<THRDNUM2> -Darg4=<DEBUGVALUE> 
  For example:   ant run -Darg0=dataFile -Darg1=1 -Darg2=searchFile -Darg3=5 -Darg4=4


EXTRA CREDIT:


  N/A



BIBLIOGRAPHY:

This serves as evidence that we are in no way intending Academic Dishonesty.
Lingjie Meng


  * http://ant.apache.org/manual/using.html

  * http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html


ACKNOWLEDGEMENT:


  During the coding process one or more of the following people may have been
  consulted. Their help is greatly appreciated.

  D. Han

