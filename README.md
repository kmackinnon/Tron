team-6
======

Course project in ECSE 321: Intro to Software Engineering at McGill University

Steps to open the project and build with Netbeans
-------------------------------------------------

In NetBeans, create a new project of type JavaFX Application.
In the Git shell, navigate to the repo and execute:

1. git init
2. git remote add origin git@github.com:mcgill-ecse321/team-6.git
3. git pull origin master

Back in NetBeans, click on Source->Scan for External Changes.
Then add the 'res' folder as a source by right clicking on Source Packages and pressing properties
At this point, you should be able to build without any problems.

Setting up SQLite JDBC support (with Netbeans)
----------------------------------------------
For SQLite JDBC we'll be using xerial's implementation (which is a fork of an older implementation by Zentus)

I've already placed the latest stable version in the base folder of the repository, so all that needs to be done is add it to your library list.
To do that, from the project tab, right click on the "Libraries" folder (below Test Packages) and click "Add JAR/Folder", and navigate to and select the sqlite-jdbc-3.7.2.jar file.

In non-Netbeans environments, append the file to your classpath at runtime with either
` -classpath ".:sqlite-jdbc-3.7.2.jar"` for *nix environments or 
` -classpath ".;sqlite-jdbc-3.7.2.jar"` for Windows environments

Should you end up wanting a newer version of the SQLite JDBC implementation some time in the future, it can be found [here](https://bitbucket.org/xerial/sqlite-jdbc/downloads "SQLite-JDBC Downloads")

Setting up Base64 Support (with Netbeans)
-----------------------------------------
Much the same as with SQLite, but this time with Base64-2.7.3.jar instead.
This Implementation is both fast, and in the Public Domain.
This addition will break your old copies of the DB though