package in.aplicatia.homeocure;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            }
        }, 5000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView myView = findViewById(R.id.main);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // get the center for the clipping circle
                    int cx = myView.getWidth() / 2;
                    int cy = myView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

                    // make the view visible and start the animation
                    myView.setBackground(getDrawable(R.drawable.gradient));
                    anim.setDuration(2000);
                    anim.start();
                } else {
                    // set the view to visible without a circular reveal animation below Lollipop
                    myView.setVisibility(View.VISIBLE);
                }
            }
        },3000);
// Check if the runtime version is at least Lollipop

    }
}
