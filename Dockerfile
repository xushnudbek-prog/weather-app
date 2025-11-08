FROM ubuntu:latest
LABEL authors="redcrow"

ENTRYPOINT ["top", "-b"]