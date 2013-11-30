Victorio Morello - victorio.morello@mail.mcgill.ca
Michael Williams - michael.williams2@mail.mcgill.ca
Gabriel Petrella - gabriel.petrella@mail.mcgill.ca
Keith MacKinnon  - keith.mackinnon@mail.mcgill.ca

All rights reserved © Victorio Morello, Michael Williams, Gabriel Petrella, Keith MacKinnon

Github Repository: https://github.com/mcgill-ecse321/team-6

Dependencies: Java, JavaFX

Build Dependencies: GNU make, POSIX environment (Cygwin if running Windows)

Developed and tested code: Windows 7 and 8, ArchLinux, Oracle Java 1.7, Netbeans 7.4

Third Party Libraries:
	base64-2.3.7		http://www.java2s.com/Code/Jar/b/Downloadbase64237jar.htm	
	sqlite-jdbc-3.7.2 	https://bitbucket.org/xerial/sqlite-jdbc/downloads
	junit-4.11              https://github.com/junit-team/junit/wiki/Download-and-Install
	hamcrest-core-1.3 	https://github.com/junit-team/junit/wiki/Download-and-Install

Compile and Run:
	Makefile is included
	Environment variable JAVA_HOME should be correctly set.
	On *NIX machines, this should have been set when Java was installed,
	otherwise set it with your shell's environment setting function.
	On Windows machines, it should be set already,
	if not, look at http://stackoverflow.com/questions/2619584/how-to-set-java-home-on-windows-7
	Build works fine under Linux, some issues with Paths under Windows.
	To build jar:
		make dist
		(LightRacer.jar and required libs will be found in the folder "dist")
	To run tests:
		make check
	To make docs:
		make docs
	To clean build folder:
		make clean
	To clean out docs folder:
	make clean-docs
