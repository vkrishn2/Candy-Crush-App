package pogilmogil.eecs40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.util.Random;

public class Game extends AppCompatActivity implements View.OnTouchListener{

    OurView v;
    Bitmap screen,orb1,orb2,orb3,orb4,orb5,orb6;
    int initx,inity,finalx,finaly = 0;
    int temp1,temp2,temp3,temp4 = 0;
    Integer[][] gridArray = new Integer[9][9];
    Paint value = new Paint();
    Integer point_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new OurView(this);
        v.setOnTouchListener(this);
        screen = BitmapFactory.decodeResource(getResources(),R.drawable.board);
        orb1 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb1);
        orb2 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb2);
        orb3 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb3);
        orb4 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb4);
        orb5 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb5);
        orb6 =  BitmapFactory.decodeResource(getResources(),R.drawable.orb6);
        int min = 1;
        int max = 6;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Random r = new Random();
                gridArray[i][j] = r.nextInt(max - min + 1) + min;
            }
        }
        setContentView(v);
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        int x = (int) event.getX()/ 160;
        int y = (int) (event.getY()-400)/ 160;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "touched down");
                initx = (int) event.getX()/ 160;
                inity = (int) (event.getY()-400)/ 160;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("TAG", "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "touched up");
                finalx = (int) event.getX()/ 160;
                finaly = (int) (event.getY()-400)/ 160;
                break;
        }


        return true;
    }

    public class OurView extends SurfaceView implements Runnable{

        Thread thread = null;
        SurfaceHolder holder;
        boolean isOk = false;

        public OurView(Context context) {
            super(context);
            holder = getHolder();
        }

        @Override
        public void run() {
            while(isOk){
                if(!holder.getSurface().isValid()){
                    continue;
                }
                Canvas c = holder.lockCanvas();
                Paint Paint = new TextPaint();
                Paint.setTextSize(120);
                Paint.setTextAlign(android.graphics.Paint.Align.LEFT);
                Paint.setColor(Color.WHITE);
                c.drawBitmap(screen,0,0,null);
                c.drawText("Score:", 25, 250, Paint);
                for(int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        switch(gridArray[i][j]){
                            case 1:
                                c.drawBitmap(orb1,j*160,(i*160)+400,null);
                                break;
                            case 2:
                                c.drawBitmap(orb2,j*160,(i*160)+400,null);
                                break;
                            case 3:
                                c.drawBitmap(orb3,j*160,(i*160)+400,null);
                                break;
                            case 4:
                                c.drawBitmap(orb4,j*160,(i*160)+400,null);
                                break;
                            case 5:
                                c.drawBitmap(orb5,j*160,(i*160)+400,null);
                                break;
                            case 6:
                                c.drawBitmap(orb6,j*160,(i*160)+400,null);
                                break;
                        }
                    }
                }
                holder.unlockCanvasAndPost(c);
            }
        }

        public void pause(){
            isOk = false;
            while(true){
                try{
                    thread.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            thread = null;
        }

        public void resume(){
            isOk = true;
            thread = new Thread(this);
            thread.start();
        }
    }
}
