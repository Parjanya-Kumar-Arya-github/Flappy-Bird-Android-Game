package com.example.flappy;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import android.os.Handler;


import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Random;




@SuppressWarnings("ALL")
class GameView extends View {


    public static String MSG="Score";
    String score_text = "0";
    int times_intended = 0;

    Handler h = new Handler();
    Runnable runnable;
    Intent intent = new Intent(getContext(), game_over.class);
    Context context = getContext();
    Point point;
    Display display;
    int dwidht, dheight, birdX, birdY, gravity = 3, velocity = 0, bothpipeX1, bothpipeX2, bothpipeX3, pipevel = 15, pipeMidPos, playerMidPos, score = 0, pipeMidPos2, pipeMidPos3, level = 1;
    Bitmap background, base, bird, pipe1, pipe_rev1, pipe2, pipe_rev2;
    Rect bg_rect;
    ArrayList<Integer> rand_pipe1 = new ArrayList<>(2);
    ArrayList<Integer> rand_pipe2 = new ArrayList<>(2);
    Random random = new Random();
    int[] pipeY = new int[2];
    int[] pipeY2 = new int[2];
    int[] pipeY3 = new int[2];
    boolean gamestate=true;

    int tens_occured=0;
    int[] sounds= {R.raw.die,R.raw.hit,R.raw.point,R.raw.swooshing,R.raw.wing};
    MediaPlayer die,hit,point_sound,swooshing,wing;



//    Sleep sleep = new Sleep();


    public GameView(Context context) {
        super(context);

        System.out.println(dheight);
        System.out.println(dwidht);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        die=MediaPlayer.create(getContext(),sounds[0]);
        hit=MediaPlayer.create(getContext(),sounds[1]);
        point_sound=MediaPlayer.create(getContext(),sounds[2]);
        swooshing=MediaPlayer.create(getContext(),sounds[3]);
        wing=MediaPlayer.create(getContext(),sounds[4]);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        base = BitmapFactory.decodeResource(getResources(), R.drawable.base);
        pipe1 = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);
        pipe_rev1 = BitmapFactory.decodeResource(getResources(), R.drawable.pipe_rev);
        pipe2 = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);
        pipe_rev2 = BitmapFactory.decodeResource(getResources(), R.drawable.pipe_rev);
        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dwidht = point.x;
        dheight = point.y;
        bg_rect = new Rect(0, 0, dwidht, dheight);
        birdX = 20;
        birdY = dheight / 2 - 400;
        bothpipeX1 = dwidht;
        bothpipeX2 = bothpipeX1 + dwidht / 2;
        bothpipeX3 = bothpipeX2 + dwidht / 2;

        playerMidPos = birdX + bird.getWidth() / 2;

        int sub_val=dheight-background.getHeight();
        rand_pipe1.add(0, getpipes(sub_val+235)[0]);
        rand_pipe2.add(0, getpipes(sub_val+235)[0]);
        pipeY[0] = rand_pipe1.get(0);
        pipeY2[1] = rand_pipe2.get(0);
        pipeY3[1] = rand_pipe2.get(0);






    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        playerMidPos = birdX + bird.getWidth() / 2;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);
        if (gamestate){
            level = ((int)score/10)+1;
            pipevel=15+(level-1)*5;}


        if (score%10==0 && score!=0)
        {
            swooshing.start();
        }




        pipeMidPos = bothpipeX1 + pipe1.getWidth() / 2;




        canvas.drawBitmap(background, null, bg_rect, null);


        paint.setColor(Color.BLACK);


        if ((birdY < dheight - base.getHeight() || velocity < 0)&& gamestate) {
            velocity += gravity;
            birdY += velocity;
        }

        if (birdX < dwidht / 2 - bird.getWidth()-200) {
            birdX += 10;
        }
        if (bothpipeX1 + pipe1.getWidth() < 0) {

            bothpipeX1 = bothpipeX3 + dwidht / 2;
            rand_pipe1.add(0, getpipes(dheight-background.getHeight()+200)[0]);
            rand_pipe2.add(0, getpipes(dheight-background.getHeight()+200)[0]);

            pipeY[0] = rand_pipe1.get(0);
            pipeY[1] = rand_pipe2.get(0);


        }

        if (bothpipeX1 + pipe1.getWidth() != 0) {
            bothpipeX1 -= pipevel;
            if (pipeY[0] + pipe_rev1.getHeight() +  dwidht/3 <= dheight - base.getHeight() + 100) {
                canvas.drawBitmap(pipe_rev1, bothpipeX1, pipeY[0], null);
                canvas.drawBitmap(pipe1, bothpipeX1, pipeY[0] + pipe_rev1.getHeight() +  dwidht/3, null);


            }


        }

        if (bothpipeX2 + pipe1.getWidth() < 0) {
            bothpipeX2 = bothpipeX1 + dwidht / 2;
            rand_pipe1.add(0, getpipes(dheight-background.getHeight()+200)[0]);
            rand_pipe2.add(0, getpipes(dheight-background.getHeight()+200)[0]);

            pipeY2[1] = rand_pipe2.get(0);

        }

        if (bothpipeX2 + pipe1.getWidth() != 0) {
            bothpipeX2 -= pipevel;
            if (pipeY[0] + pipe_rev1.getHeight() +  dwidht/3 < dheight - base.getHeight() + 100) {
                canvas.drawBitmap(pipe_rev2, bothpipeX2, pipeY2[1], null);
                canvas.drawBitmap(pipe2, bothpipeX2, pipeY2[1] + pipe_rev1.getHeight() +  dwidht/3, null);
            }
        }
        if (bothpipeX3 + pipe1.getWidth() < 0) {
            bothpipeX3 = bothpipeX2 + dwidht / 2;
            rand_pipe1.add(0, getpipes(dheight-background.getHeight()+200)[0]);
            rand_pipe2.add(0, getpipes(dheight-background.getHeight()+200)[0]);

            pipeY3[1] = rand_pipe2.get(0);

        }

        if (bothpipeX3 + pipe1.getWidth() != 0) {
            bothpipeX3 -= pipevel;

            canvas.drawBitmap(pipe_rev2, bothpipeX3, pipeY3[1], null);
            canvas.drawBitmap(pipe2, bothpipeX3, pipeY3[1] + pipe_rev1.getHeight() +  dwidht/3, null);

        }


        canvas.drawBitmap(bird, birdX, birdY, null);


        canvas.drawBitmap(base, 0, dheight - base.getHeight() + 100, null);


        if (pipeMidPos <= playerMidPos) {
            if (playerMidPos < pipeMidPos+pipevel) {
                if (score!=10){
                    score += 1;
                }
                else if (score==10 && tens_occured==0){
                    tens_occured++;
                }
                else if (score==10 && tens_occured==1){
                    score+=1;
                }


                score_text = "" + score;
                point_sound.start();




            }

        }


        pipeMidPos2 = bothpipeX2 + pipe1.getWidth() / 2;
        if (pipeMidPos2 <= playerMidPos) {
            if (playerMidPos < pipeMidPos2 + pipevel) {
                if (score!=10){
                    score += 1;
                }
                else if (score==10 && tens_occured==0){
                    tens_occured++;
                }
                else if (score==10 && tens_occured==1){
                    score+=1;
                }
                score_text = "" + score;
                point_sound.start();


            }
        }
        pipeMidPos3 = bothpipeX3 + pipe1.getWidth() / 2;
        if (pipeMidPos3 <= playerMidPos) {
            if (playerMidPos < pipeMidPos3 + pipevel) {
                if (score!=10){
                    score += 1;
                }
                else if (score==10 && tens_occured==0){
                    tens_occured++;
                }
                else if (score==10 && tens_occured==1){
                    score+=1;
                }                score_text = "" + score;
                point_sound.start();


            }
        }
        intent.putExtra(MSG,""+score);


        if (birdY > dheight - base.getHeight() || birdY < 0) {
            if (times_intended == 0) {
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();

                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();

            }
            times_intended++;

        }


        if (birdY < pipe_rev1.getHeight() + pipeY[0] && Math.abs(birdX - bothpipeX1) < pipe1.getWidth()-50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();
            }
            times_intended++;


        }
        if ((birdY + bird.getHeight() > pipeY[0] + pipe_rev1.getHeight() +  dwidht/3) && Math.abs(birdX - bothpipeX1) < pipe1.getWidth() - 50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();
            }
            times_intended++;

        }
        if (birdY < pipe_rev1.getHeight() + pipeY2[1] && Math.abs(birdX - bothpipeX2) < pipe1.getWidth()-50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();
            }
            times_intended++;


        }
        if ((birdY + bird.getHeight() > pipeY2[1] + pipe_rev1.getHeight() +  dwidht/3) && Math.abs(birdX - bothpipeX2) < pipe1.getWidth() - 50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();
            }
            times_intended++;

        }
        if (birdY < pipe_rev1.getHeight() + pipeY3[1] && Math.abs(birdX - bothpipeX3) < pipe1.getWidth()-50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);

                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();
            }
            times_intended++;


        }
        if ((birdY + bird.getHeight() > pipeY3[1] + 900 + dheight/3) && Math.abs(birdX - bothpipeX3) < pipe1.getWidth() - 50) {
            if (times_intended == 0) {
//                sleep.start();
                gravity=0;
                gamestate=false;
                pipevel=0;
                point_sound.stop();
                die.start();
                hit.start();
                point_sound.stop();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {

                        context.startActivity(intent);


                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();


            }
            times_intended++;

        }
        Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.creepster);
        paint.setTypeface(typeface);


        paint.setTextSize(dwidht/8);
        canvas.drawText("Score "+score_text,
                0,
                dwidht/4,
                paint);
        paint.setTextSize(dwidht/8);
        canvas.drawText("Level " + level, 0, dwidht/8, paint);





        h.postDelayed(runnable, 30);



    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent (MotionEvent event){
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            velocity = -30;
        }
        wing.start();
        return super.onTouchEvent(event);
    }



    public int[] getpipes(int sub_val){

        int pipeHeight = pipe_rev1.getHeight();
        int offset = dheight / 3;
        int range = (int) (dheight - base.getHeight() - 1.2 * offset);
        int y2 = offset + random.nextInt(range);

        int y1 = pipeHeight - y2 + offset;



        return new int[]{-y1};
    }


}






