#!/bin/bash
./compile.cmd
files=$(find bin -name '*.class');
jar cf build/build.jar $files
