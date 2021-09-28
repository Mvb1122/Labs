# compile to a runnable JAR since I was too lazy to fix
# the class-loading errors.
javac Main.java shapes/*.java -cp gpdraw.jar 
jar cvfm RectangleLab.jar manifest.txt *.class shapes/*.class gpdraw.jar

echo 

# Run said jar.
java -jar RectangleLab.jar

# Remove it to ensure a clean compile every time.
rm RectangleLab.jar