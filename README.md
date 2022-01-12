# WoodPecker
A Uncaught Exception Handler library ~~studied~~ copied from [drakeet](https://github.com/drakeet)
![WoodPecker](https://github.com/peace710/WoodPecker/blob/master/screenshots/device-2019-08-30-154348.png)

#### Usage


Add the JitPack repository to your build file
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

To add a dependency using gradle:

```groovy
	dependencies {
	        implementation 'com.github.peace710:WoodPecker:4.0.0'
	}
```

In your Application class:
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val stringArray = resources.getStringArray(R.array.keys)
        val keys = ArrayList<String>()
        stringArray.forEach {
            keys.add(it)
        }
        instance().with(keys).jump(false).count(10).fly(this)
    }
}
```

That is all! Woodpecker will automatically show a Activity when your app crash with uncaught exceptions.

License
=======
MIT License

Copyright (c) 2021 peace710

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


