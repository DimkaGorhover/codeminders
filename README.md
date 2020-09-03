# CodeMinders Task.

## Description.

[Kata13: Counting Code Lines](http://codekata.com/kata/kata13-counting-code-lines/)

## How To Run.

### Using Docker.

#### Requirements.

* `Docker`
* `make` build tool

#### Run Test.
 
```sh
make run
```

### Using Local Environment.

#### Requirements.

* `Java 8`
* `Gradle`

#### 1. Init Gradle Wrapper.

```sh
gradle wrapper
```

If you don't have `gradle`, docker will help

```sh
make wrapper
```

#### 2. Build Jar File.

```sh
./gradlew clean shadowJar
```

#### 3. Run Java Application.

```sh
java -jar $(pwd)/build/libs/app.jar \
    --directory $(pwd)/src \
    --prefix    2 \
    --exclude   build,target,bin,out,resources'
```
