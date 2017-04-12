# Fethcer 

Fetcher is a library for pick  image/video from phone in Andorid platform. use mvp architecture and easy refactor.

[demo_apk](https://raw.githubusercontent.com/twolight/fetcher/master/apk/app-debug.apk)

# Download

use Gradle:

```

compile 'com.twolight.fetcher:fetcher:1.0.0'

```

Or Maven:

```

<dependency>
  <groupId>com.twolight.fetcher</groupId>
  <artifactId>fetcher</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>

```




# Support:

image/video

single/multiple pick

# Start

`Fetch.start()` open the pick view.

>```
>Fetch.start(this, new Setting() {
>    @Override
>    public boolean isSingle() {
>        return false;
>    }
>
>    @Override
>    public void load(Context context, ImageView imageView, String path) {
>		Glide.with(context).load(path).into(imageView);
>    }
>
>    @Override
>    public int type() {
>        return Type.IMAGE;
>    }
>
>    @Override
>    public int resultCode() {
>        return 100;
>    }
>
>    @Override
>    public int requestCode() {
>        return 101;
>    }
>});
>```



And get result in `onActivityResult`.



> ```
> @Override
> protected void onActivityResult(int requestCode, int resultCode, Intent data) {
>     super.onActivityResult(requestCode, resultCode, data);
>
>     if(resultCode == 100){
>         ArrayList<Entity> dataList = data.getExtras().getParcelableArrayList("data");
>     }
> }
> ```



# Screen Capture



![](https://raw.githubusercontent.com/twolight/fetcher/master/image/32533.png)



![](https://raw.githubusercontent.com/twolight/fetcher/master/image/1949.png)









 







