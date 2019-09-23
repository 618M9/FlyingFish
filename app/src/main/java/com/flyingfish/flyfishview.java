package com.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyfishview extends View {

    private Bitmap fish [] = new Bitmap[2];
    private  int fishx = 10;
    private  int fishy;
    private int fishSpeed;
    private  int canvasWidth,canvasHeight;

    private int YellowX,YellowY,YellowSpeed = 16;
    private  Paint YellowPaint =  new Paint();

    private  int GreenX,GreenY, GreenSpeed = 20;
    private  Paint GreenPain = new Paint();

    private  int RedX,RedY, RedSpeed = 25;
    private  Paint RedPain = new Paint();
    private  int score,lifeCounterOffish;//life counteroffish  is variable for red ball life
    private  boolean touch = false;
    private Bitmap backgroundImage;
    private Paint socorpaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public flyfishview(Context context) {
        super(context);
        fish [0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish [1] = BitmapFactory.decodeResource(getResources(),R.drawable.f2);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        YellowPaint.setColor(Color.YELLOW);
        YellowPaint.setAntiAlias(false);

        GreenPain.setColor(Color.GREEN);
        GreenPain.setAntiAlias(false);

        RedPain.setColor(Color.RED);
        RedPain.setAntiAlias(false);



        socorpaint.setColor(Color.WHITE);
        socorpaint.setTextSize(70);
        socorpaint.setTypeface(Typeface.DEFAULT_BOLD);
        socorpaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        fishy = 550;
        score = 0;
        lifeCounterOffish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        int minFishy = fish[0].getHeight();
        int maxFishy = canvasHeight - fish[0].getHeight() * 3;
        fishy = fishy + fishSpeed;
        if (fishy<minFishy)
        {
            fishy = minFishy;
        }
        if (fishy>maxFishy)
        {
            fishy = maxFishy;
        }
        fishSpeed = fishSpeed + 2;

        if (touch)
        {
            canvas.drawBitmap(fish[1],fishx,fishy,null);
            touch = false;

        }
        else
        {
            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }

// this is yellow ball
        YellowX = YellowX - YellowSpeed;


        if (hitBallChecker(YellowX,YellowY))
        {
            score = score +10;
            YellowX =-100;
        }

        if (YellowX < 0)
        {
            YellowX = canvasWidth + 21;
            YellowY = (int) Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;


        }
        canvas.drawCircle(YellowX,YellowY,25,YellowPaint);
// this is green ball

        GreenX = GreenX - GreenSpeed;


        if (hitBallChecker(GreenX,GreenY))
        {
            score = score +20;
            GreenX =-100;
        }

        if (GreenX < 0)
        {
            GreenX= canvasWidth + 21;
            GreenY = (int) Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;


        }
        canvas.drawCircle(GreenX,GreenY,25,GreenPain);

        // this is red ball

        RedX = RedX - RedSpeed;


        if (hitBallChecker(RedX,RedY))
        {
            RedX =-100;
            lifeCounterOffish --;
            if (lifeCounterOffish == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent GameOverIntent = new Intent(getContext(),GameOverActivity.class);
                GameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(GameOverIntent);
                
            }
        }

        if (RedX < 0)
        {
            RedX= canvasWidth + 21;
           RedY = (int) Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;


        }
        canvas.drawCircle(RedX,RedY,30,RedPain);
        canvas.drawText("Score :" + score, 20, 60, socorpaint);
        // life counter
        for (int i =0;i<3;i++)
        {
            int x = (int) (533 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < lifeCounterOffish)
            {
                canvas.drawBitmap(life[0], x, y, null);

            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

        // xml design

       // canvas.drawBitmap(life[0], 530, 10, null);
       // canvas.drawBitmap(life[0], 630, 10, null);
       // canvas.drawBitmap(life[0], 730, 10, null);
    }

    public boolean hitBallChecker(int x,int y)
    {
        if (fishx < x && x < (fishx + fish[0].getHeight()) && fishy < y && y < (fishy + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN);
        {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}

