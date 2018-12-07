# Scala Maven

This is a toy example, building off of the scala-archetype-simple:1.7 Maven archetype.  Main purpose is to show Scala/Java interoperability in a Maven project.

## Explanation
The core functionality that allows you to compile scala code via Maven is the [Scala Maven Plugin](https://github.com/davidB/scala-maven-plugin).  In the pom.xml for this project, you can see the configuration for the scala plugin in the build/plugins configuration path, as well as a scala library in the dependencies.

```xml
<dependencies>
    <dependency>
      <groupId>org.scala-lang</groupId>
      <artifactId>scala-library</artifactId>
      <version>${scala.version}</version>
    </dependency>
    ...
</dependencies>
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

Note that you will also need to explicitly include this plugin in build/plugins to compile Java.
```xml
      <!-- For Java -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>compile</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```


## Generating a project using mvn archetype
If you want to generate the base project structure for this example, you can run this command:
```
mvn archetype:generate
``` 

From there, select `net.alchim31.maven:scala-archetype-simple` as the archetype to use. 

Refer to the "[Scala With Maven](https://docs.scala-lang.org/tutorials/scala-with-maven.html)" reference docs for more details.