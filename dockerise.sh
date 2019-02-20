#!/usr/bin/env bash
set -e
docker login -u saiteja50 -p saiteja_50
docker build -t hopperadapterscala:1 .
docker tag hopperadapterscala:1 saiteja50/hopperadapterscala:1 
docker push saiteja50/hopperadapterscala:1

