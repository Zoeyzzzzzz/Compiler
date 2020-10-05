FROM gcc:10
WORKDIR /app/
COPY ./* ./
RUN gcc word.c -o program
RUN chmod +x program
