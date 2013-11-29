Victorio Morello - victorio.morello@mail.mcgill.ca
Michael Williams - michael.williams2@mail.mcgill.ca
Gabriel Petrella - gabriel.petrella@mail.mcgill.ca
Keith MacKinnon  - keith.mackinnon@mail.mcgill.ca

All rights reserved © Victorio Morello, Michael Williams, Gabriel Petrella, Keith MacKinnon

Github Repository: https://github.com/mcgill-ecse321/team-6

Dependencies: Java, JavaFX

Developed and tested code: Windows 7 and 8, ArchLinux, Oracle Java 1.7, Netbeans 7.4

Third Party Libraries:
	base64-2.3.7		http://www.java2s.com/Code/Jar/b/Downloadbase64237jar.htm	
	sqlite-jdbc-3.7.2 	https://bitbucket.org/xerial/sqlite-jdbc/downloads
	junit-4.11              https://github.com/junit-team/junit/wiki/Download-and-Install
	hamcrest-core-1.3 	https://github.com/junit-team/junit/wiki/Download-and-Install

Compile and Run:
	Makefile is included
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
