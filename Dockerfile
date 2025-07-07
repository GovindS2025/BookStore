FROM ubuntu:latest
LABEL authors="govin"

ENTRYPOINT ["top", "-b"]