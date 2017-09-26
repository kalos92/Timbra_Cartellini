package it.basiliocarrabbotta.timbra_cartellini;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Created by Kalos on 10/09/2017.
 */

public class Timer extends CountDownTimer {

    private final Animation anim;
    private Activity act;
    private TextView dot;
    public Timer(long millisInFuture, long countDownInterval, Activity act, TextView dot, Animation anim) {
        super(millisInFuture, countDownInterval);
        this.act=act;
        this.dot=dot;
        this.anim=anim;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {


        if(act!=null)
        act.finish();

        if(dot!=null && anim != null)
            dot.startAnimation(anim);
    }
}
