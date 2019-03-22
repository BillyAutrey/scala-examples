# Scala + Java Interoperation and Fortify Scans
This project contains a very small demonstration of scanning code via Fortify, in a situation where you want to scan Java via SBT.  It also includes interoperability, to illustrate how you might scan Scala and Java code together.

## Code operation
The code itself does nothing important.  It sums from 1 to a random integer, as a stream.  It also executes this stream summation every few seconds, via `Stream.tick`.  The Java code is the entry point, and it calls Scala.  This was done purely to emulate a particular situation, and has important implications on the order of Fortify translation.

## How to Scan
Execute the following tasks in order:

```sbtshell
translate
scan
```

If you would like to clean the project, you can execute `cleanFortify`.  This will execute `clean`, as well as a `sourceanalyzer -b buildId -clean`.

An example of a full workflow:

```sbtshell
sbt:scala-java-fortify> cleanFortify
[success] Total time: 1 s, completed Mar 18, 2019 4:14:31 PM

sbt:scala-java-fortify> translate
sbt:scala-fortify> translate
[info] Running Scala translation
[info] Updating ...
[info] Done updating.
[info] Compiling 1 Scala source and 1 Java source to /Users/billibuster/dev/accounts/spglobal/scala-examples/scala-java-fortify/target/scala-2.12/classes ...
FortifyPlugin 1.0.13, licensed to <user> (expires: 2099-01-01T00:00Z[UTC])
[info] Done compiling.
[info] Packaging /userdir/scala-java-fortify/target/scala-2.12/scala-fortify_2.12-0.1.0-SNAPSHOT.jar ...
[info] Done packaging.
[info] Running Java translation on /userdir/scala-java-fortify/src/main/java/example/Main.java
[success] Total time: 5 s, completed Mar 19, 2019 5:18:22 PM

sbt:scala-java-fortify> scan
[success] Total time: 66 s, completed Mar 18, 2019 4:16:07 PM
```