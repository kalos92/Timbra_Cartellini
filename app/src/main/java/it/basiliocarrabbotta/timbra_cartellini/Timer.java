package it.basiliocarrabbotta.timbra_cartellini;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kalos on 10/09/2017.
 */

public class Timer extends CountDownTimer implements TimerSubject {

    private final Animation anim;
    private Activity act;
    private TextView dot;
    private ArrayList<TimerObserver> Observers;

    public Timer(long millisInFuture, long countDownInterval, Activity act, TextView dot, Animation anim) {
        super(millisInFuture, countDownInterval);
        this.act=act;
        this.dot=dot;
        this.anim=anim;
        Observers = new ArrayList<>();
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

        notifyObservers();
    }

    @Override
    public void registerObserver(TimerObserver repositoryObserver) {
        if(!Observers.contains(repositoryObserver)) {
            Observers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(TimerObserver repositoryObserver) {
        if(Observers.contains(repositoryObserver)) {
            Observers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (TimerObserver observer: Observers) {
            observer.OnTimerFinished();
        }
    }
}
