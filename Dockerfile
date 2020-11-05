FROM gcc:10
WORKDIR /app/
COPY ./* ./
RUN gcc third.c -o program
RUN chmod +x program
