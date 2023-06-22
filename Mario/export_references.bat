@Echo off
javac Changer.java
java Changer

cd mario

::Load the exporter
mvn clean compile exec:java -Dexec.mainClass="com.com.com.Engine.Utils.Exporter"
