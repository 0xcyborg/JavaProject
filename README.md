## JavaProject

This repository is an implementation of the **Java/OOP** concepts that **Dr. Hariati** is teaching us. All details can be found in **Guide.pdf**.

### Development Commands:

```sh
javac -d MyProject/Classes MyProject/Main.java

java -cp MyProject/Classes:Database/mysql-connector-j-9.2.0.jar MyProject.Main
```

### Compilation Commands:

```sh
jar cfm MyProject.jar manifest.txt -C MyProject/Classes .

jar uf MyProject.jar -C Database .
```
