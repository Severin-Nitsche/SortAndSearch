#!/bin/bash
files=$(find src -name '*.java');
javac $files
rsync -r src/ bin/ --exclude='*.java'
find src -name '*.class' -delete
cd doc
javadoc ../$files
