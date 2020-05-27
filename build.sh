#!/bin/bash

# Construye el JAR
mvn package

# Crea la imagen de Docker (utilizando el Dockerfile)
docker build -t nomina:0.0.1 .
