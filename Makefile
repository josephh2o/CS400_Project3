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

clean:
	rm *.class

