# Live Chat Application

## Installation
Make sure you have Java, Gradle and MongoDB installed on your system.

## To run
You need to first make a MongoDB connection. <br>
Then add the URI of the connection to your environment variable. For that refer to the below steps:<br>
1. Create a new file with filename "application.yml" in src/main/resources.
2. Add the URI in the YAML file like the below order:<br>
&emsp;app:<br>
&emsp;&emsp;service:<br>
&emsp;&emsp;&emsp;uri: \<URI\>
