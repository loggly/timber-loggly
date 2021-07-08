<h1>timber-loggly <a href='https://tony19.ci.cloudbees.com/job/timber-loggly/'><a href='https://tony19.ci.cloudbees.com/job/timber-loggly/job/timber-loggly-SNAPSHOT/'><img src='https://tony19.ci.cloudbees.com/buildStatus/icon?job=timber-loggly/timber-loggly-SNAPSHOT'></a></a></h1>
<sup>v1.0.1</sup>

A [Timber][2] tree for asynchronously posting log messages to [Loggly][1].

Usage
-----
1. Plant a `LogglyTree` with your [authorization token][4] from Loggly.
 ```java
 import android.app.Application;
 import com.github.tony19.timber.loggly.LogglyTree;
 import timber.log.Timber;

 public class ExampleApp extends Application {

     @Override
     public void onCreate() {
         super.onCreate();

         final String LOGGLY_TOKEN = /* your loggly token */;
         Timber.plant(new LogglyTree(LOGGLY_TOKEN));
     }
 }
 ```

2. Use Timber API to log an event via `LogglyTree`...
 ```java
 Timber.tag("foo");
 Timber.i("hello world");
 ```


##### Limit on WiFi (optional)
If you want to reduce cellular data traffic you can limit logging only when connected to a WiFi network. If this s the case you need to add the `android.permission.ACCESS_NETWORK_STATE` in your AndroidManifest and Plant the `LogglyTree` with the limit.
```java
LogglyTree wifiLimitedLogglyTree = new LogglyTree(LOGGLY_TOKEN).limitWifi(this);
Timber.plant(wifiLimitedLogglyTree);
```

Download
--------

[timber-loggly-1.0.1.jar][5]

#### Gradle

```
compile 'com.github.tony19:timber-loggly:1.0.1'
compile 'com.squareup.retrofit:retrofit:1.9.0'
```

#### Maven

```xml
<dependency>
  <groupId>com.github.tony19</groupId>
  <artifactId>timber-loggly</artifactId>
  <version>1.0.1</version>
</dependency>
<dependency>
  <groupId>com.squareup.retrofit</groupId>
  <artifactId>retrofit</artifactId>
  <version>1.9.0</version>
</dependency>
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][3].


[1]: http://loggly.com
[2]: https://github.com/JakeWharton/timber
[3]: https://oss.sonatype.org/content/repositories/snapshots/com/github/tony19/timber-loggly/
[4]: https://www.loggly.com/docs/customer-token-authentication-token/
[5]: http://goo.gl/HXJIvn
