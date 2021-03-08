# GitJarUpdate
Presents a popup at the startup of a program when there is a new Github Release
## Usage
build.gradle
        
        
        plugins {
          id 'com.palantir.git-version' version '0.12.3'
        }
        repositories {
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
