# GitJarUpdate
Presents a popup at the start of a program when there is a new Github Release.  Then restarts the updated program.
## Usage
build.gradle
        
```gradle
        plugins {
	  // get git infos into Manifest
          id 'com.palantir.git-version' version '0.12.3'
        }
        repositories {
	  //load repo from git via gradle
          maven { url "https://jitpack.io" }
        }
        dependencies {
       	  implementation 'com.github.APN-Pucky:GitJarUpdate:0.0.3'
        }
        
        task fatJar(type: Jar) {
	  manifest {
            attributes 'Implementation-Title': project.name,
        	  'Implementation-Version': versionDetails().lastTag,
        	  'Main-Class': 'YOUR.MAIN.CLASS.HERE'
          }
          baseName = project.name + '-all'
          from { 
            configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }{
            //disable signing from 3rd party
            exclude 'META-INF/*.RSA', 'META-INF/*.SF', 'META-INF/*.DSA'
          }
          with jar
        }
```
At the start of your program run:
```java
Update.loadUpdate("PROJECTNAME-all.jar", "GITHUBUSER", "GITHUBPROJECTNAME");
```

An example with github actions publishing jar files is ![TeXCalc](https://github.com/APN-Pucky/TeXCalc) Project

## Note
The Git/Manifest info is not (correctly) included if the code is run from an IDE instead of a built jar. Therefore an Update-Popup will show.
