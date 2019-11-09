#!/bin/bash

heroku deploy:jar ../../../../target/logger-api.jar -a heroku-logger-api
