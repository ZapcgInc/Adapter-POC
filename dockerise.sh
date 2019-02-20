#!/usr/bin/env bash
set -e
docker login -u saiteja50 -p saiteja_50
docker build -t hopperadapterscala:$BUILD_NUMBER .
docker tag hopperadapterscala:$BUILD_NUMBER saiteja50/hopperadapterscala:$BUILD_NUMBER 
docker push saiteja50/hopperadapterscala:$BUILD_NUMBER

