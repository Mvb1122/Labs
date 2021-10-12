# compile to a runnable JAR since I was too lazy to fix
# the class-loading errors.
javac Main.java -cp gpdraw.jar 
jar cvfm run.jar manifest.txt *.class gpdraw.jar

echo Running.

# Run said jar.
java -jar run.jar

# Remove it to ensure a clean compile every time.
rm run.jar
echo JAR removed.