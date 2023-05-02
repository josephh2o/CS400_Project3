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

AirportPathFinderAppFD.class: AirportPathFinderAppFD.java AirportPath.class
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderAppFD.java

AirportPathFinderFrontendFD.class: AirportPathBDInterface.class AirportPath.class AirportPathFinderFrontendInterface.class AirportPathFinderFrontendFD.java
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderFrontendFD.java

AirportPath.class : PathInterface.class AirportInterface.class PathDW.class Airport.class  AirportPathInterface.class AirportPath.java AirportDatabase.class AirportPathGraph.class
	javac AirportPath.java

AirportDatabase.class: AirportDatabase.java
	javac AirportDatabase.java

PathDW.class : PathInterface.class PathDW.java
	javac PathDW.java

Airport.class : AirportInterface.class Airport.java
	javac Airport.java

AirportPathFinderFrontendInterface.class : AirportPathFinderFrontendInterface.java
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls AirportPathFinderFrontendInterface.java

AirportPathInterface.class: AirportPathInterface.java
	javac AirportPathInterface.java

PathInterface.class : PathInterface.java
	javac PathInterface.java

AirportInterface.class : AirportInterface.java
	javac AirportInterface.java

AirportPathGraph.class: AirportPathGraph.java
	javac AirportPathGraph.java

runTests: runAlgorithmEngineerTests runDataWranglerTests runBackendDeveloperTests runFrontendDeveloperTests

runAlgorithmEngineerTests: #AlgorithmEngineerTests.class
	javac -cp .:lib/junit5.jar AlgorithmEngineerTests.java
	java -jar lib/junit5.jar -cp . --select-class=AlgorithmEngineerTests

# AlgorithmEngineerTests.class: AlgorithmEngineerTests.java AirportPathGraph.class
	# javac -cp .:lib/junit5.jar AlgorithmEngineerTests.java

runDataWranglerTests: #DataWranglerTests.class
	javac -cp .:lib/junit5.jar DataWranglerTests.java
	java -jar lib/junit5.jar -cp . --select-class=DataWranglerTests

# DataWranglerTests.class: DataWranglerTests.java
	# javac -cp .:lib/junit5.jar DataWranglerTests.java

DataWranglerTests.java: AirportDatabaseDW.class

runBackendDeveloperTests: #BackendDeveloperTests.class
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls -cp .:lib/junit5.jar:./lib/JavaFXTester.jar BackendDeveloperTests.java
	java --module-path $(PATH_TO_FX) --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar lib/junit5.jar -cp .:lib/JavaFXTester.jar --select-class=BackendDeveloperTests

runFrontendDeveloperTests: #FrontendDeveloperTests.class
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls -cp .:./lib/junit5.jar:./lib/JavaFXTester.jar FrontendDeveloperTests.java
	java --module-path $(PATH_TO_FX) --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar lib/junit5.jar -cp .:lib/JavaFXTester.jar -c FrontendDeveloperTests

# FrontendDeveloperTests.class : AirportPathFinderFrontendFD.class FrontendDeveloperTests.java
	# javac --module-path $(PATH_TO_FX) --add-modules javafx.controls -cp .:./lib/junit5.jar:./lib/JavaFXTester.jar FrontendDeveloperTests.java


# BackendDeveloperTests.class: BackendDeveloperTests.java
	# javac -cp .:lib/junit5.jar BackendDeveloperTests.java

# BackendDeveloperTests.java: AirportPath.class

clean:
	rm *.class

