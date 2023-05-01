UNAME := $(shell uname)

ifeq ($(OS),Windows_NT)
	PATH_TO_FX = lib/javafx-sdk-windows-20.0.1/lib
endif
ifeq ($(UNAME),Darwin)
	PATH_TO_FX = lib/javafx-sdk-mac-20.0.1/lib
endif

ifeq ($(UNAME),Linux)
	PATH_TO_FX = lib/javafx-sdk-linux-20.0.1/lib
endif

runProgram: AirportPathFinderAppFD.class
	java --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderAppFD

AirportPathFinderAppFD.class: AirportPathFinderAppFD.java
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderAppFD.java

AirportPathFinderFrontendFD.class: AirportPathBDInterface.class AirportPath.class AirportPathFinderFrontendInterface.class AirportPathFinderFrontendFD.java
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderFrontendFD.java

AirportPath.class : PathInterface.class AirportInterface.class Path.class Airport.class  AirportPathInterface.class AirportPath.java AirportDatabase.class
	javac AirportPath.java

AirportDatabase.class: AirportDatabase.java
	javac AirportDatabase.java

Path.class : PathInterface.class Path.java
	javac Path.java

Airport.class : AirportInterface.class Airport.java
	javac Airport.java

AirportPathFinderFrontendInterface.class : AirportPathFinderFrontendInterface.java
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderFrontendInterface.java

AirportPathInterface.class: AirportPathBDInterface.java
	javac AirportPathInterface.java

PathInterface.class : PathInterface.java
	javac PathInterface.java

AirportInterface.class : AirportInterface.java
	javac AirportInterface.java


clean:
	rm *.class

