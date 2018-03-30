# Game of live

![logo](/src/main/resources/offline.jpg)

## Requerements:

* maven CLI
* java 1.8

## Instruction

1. import project
    ```bash
    git clone https://github.com/truemanD/game-of-live.git
    ```

2. build project
    ```bash
    cd game-of-live
    mvn clean package
    ```

3. execute help
    ```bash
    mvn exec:java
    ```

    3.1. execute in console mode

    ```bash
    mvn exec:java -Dexec.args="-s 10 -d 100 -m console"
    ```
    3.2. execute in online mode

    ```bash
    mvn exec:java -Dexec.args="-s 100 -d 100 -m online"
    ```

    3.3. execute in offline mode

    ```bash
    mvn exec:java -Dexec.args="-s 100 -d 100 -m offline"
    ```

### game parameters

* -d,--delay <arg>        delay betwean days in ms
* -m,--mode <arg>         game mode. Available values: console, online, offline, interactive; interactive is under construction;
* -s,--field-size <arg>   field size in points
