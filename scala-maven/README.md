# Scala Maven

This is a toy example, building off of the scala-archetype-simple:1.7 Maven archetype.  Main purpose is to show Scala/Java interoperability in a Maven project.

## Explanation
The core functionality that allows you to compile scala code via Maven is the [Scala Maven Plugin](https://github.com/davidB/scala-maven-plugin).  In the pom.xml for this project, you can see the configuration for the scala plugin in the build/plugins configuration path.

```xml
<build>
   <plugins>
      <plugin>
        <!-- see http://davidb.github.com/scala-maven-plugin -->
        <groupId>net.alchim31.maven</groupId>
        <artifactId>scala-maven-plugin</artifactId>
        <version>3.3.2</version>
        <executions>
          <execution>
            <goals>
              <goal>compile</goal>
              <goal>testCompile</goal>
            </goals>
            <configuration>
              <args>
                <arg>-dependencyfile</arg>
                <arg>${project.build.directory}/.scala_dependencies</arg>
              </args>
            </configuration>
          </execution>
        </executions>
      </plugin>
   </plugins>
   ...
</build>
```

## Generating a project using mvn archetype
If you want to generate the base project structure for this example, you can run this command:
```
mvn archetype:generate
``` 

From there, select `net.alchim31.maven:scala-archetype-simple` as the archetype to use. 

Refer to the "[Scala With Maven](https://docs.scala-lang.org/tutorials/scala-with-maven.html)" reference docs for more details.