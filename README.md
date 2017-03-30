# Fethcer 

Fetcher is a library for pick  image/video from phone in Andorid platform. use mvp architecture and easy refactor.

[demo_apk](https://raw.githubusercontent.com/twolight/fetcher/master/apk/app-debug.apk)

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









 







