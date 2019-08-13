# WoodPecker
A Uncaught Exception Handler library ~~studied~~ copied from [drakeet](https://github.com/drakeet)
![WoodPecker](https://github.com/peace710/WoodPecker/blob/master/screenshots/device-2019-08-13-202539.png)

#### Usage

To add a dependency using Gradle:

```groovy
compile 'me.peace:crashpecker:1.1.0'
```

In your Application class:
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WoodPecker.instance().fly(this);
    }
}
```

That is all! Woodpecker will automatically show a Activity when your app crash with uncaught exceptions.


