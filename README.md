# Scala Executable Spike
This demonstrates how a simple runnable jar can be compiled using a few simple plugins.  Deployables can be created and executed with minimal effort in Scala.

## Compiling
To compile, execute:
```sbtshell
sbt assembly
```
This will create an executable in `target/scala-2.12/`.  This executable is the fatjar, with a prepended launch script that allows for direct execution.
```sbtshell
# Execute the runnable jar like this:
./target/scala-2.12/scala-executable-spike-0.1
```
