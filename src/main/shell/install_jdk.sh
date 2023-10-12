#!/bin/bash

mv jdk1.8.311.tar.gz ~/
cd ~
tar -xvzf jdk1.8.311.tar.gz
export JAVA_HOME=~/jdk1.8.311
export PATH=${PATH}:${JAVA_HOME}/bin
java -version