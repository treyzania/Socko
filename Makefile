all: client server

client:
	javac Client.java

server:
	javac Server.java

clean:
	rm *.class *.jar

.PHONY: clean
