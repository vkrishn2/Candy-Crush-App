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
    Bitmap screen,orb0,orb1,orb2,orb3,orb4,orb5,orb6;
    int initx,inity,finalx,finaly = 0;
    int x,y = 0;
    Integer[][] gridArray = new Integer[9][9];
    Paint value = new Paint();
    Integer valueofscore = 0;
    String point_score = "0";
    int counter = 0;
    int temp_grid, temp_grid2;
    int off1, off2, off3, off4, off5, off6 = 0;
    int tempoff = 0;
    int morethan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new OurView(this);
        v.setOnTouchListener(this);
        screen = BitmapFactory.decodeResource(getResources(),R.drawable.board);
        orb0 =  BitmapFactory.decodeResource(getResources(),R.drawable.orbnull);
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

        for(int i = 0; i < 10; i++){
            checkBoard();
            dropBoard();
        }
        valueofscore = 0;
        setContentView(v);
    }

    protected void fixStartBoard(){
        for(int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                if(i>0 && i<7){
                    if((gridArray[i+1][j].equals(gridArray[i+2][j]))&&(gridArray[i][j].equals(gridArray[i+1][j]))){
                        gridArray[i+2][j] += 1;
                        gridArray[i+2][j] %= 7;
                        if(gridArray[i+2][j] == 0){
                            gridArray[i+2][j] += 1;
                        }
                    }
                }
                if(j>0 && j<7){
                    if((gridArray[i][j+1].equals(gridArray[i][j+2]))&&(gridArray[i][j].equals(gridArray[i][j+1]))){
                        gridArray[i][j+2] += 1;
                        gridArray[i][j+2] %= 7;
                        if(gridArray[i][j+2] == 0){
                            gridArray[i][j+2] += 1;
                        }
                    }
                }
            }
        }
    }

    protected void checkBoard(){
        int current_orb;
        morethan = 0;
        for(int i=0; i<9; i++) {
            for (int j = 0; j < 7; j++) {
                current_orb = gridArray[i][j];
                if (gridArray[i][j+1] == current_orb){
                    if (gridArray[i][j+2] == current_orb){
                        gridArray[i][j] = 0;
                        gridArray[i][j+1] = 0;
                        gridArray[i][j+2] = 0;
                        morethan = 1;
                        for (int k = j+3; k < 9; k++) {
                            if ((gridArray[i][k] == current_orb)) {
                                gridArray[i][k] = 0;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
            }
        }
        for(int j=0; j<9; j++) {
            for (int i = 0; i < 7; i++) {
                current_orb = gridArray[i][j];
                if (gridArray[i+1][j] == current_orb){
                    if (gridArray[i+2][j] == current_orb){
                        gridArray[i][j] = 0;
                        gridArray[i+1][j] = 0;
                        gridArray[i+2][j] = 0;
                        morethan = 1;
                        for (int k = i+3; k < 9; k++) {
                            if ((gridArray[k][j] == current_orb)) {
                                gridArray[k][j] = 0;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    protected void dropBoard(){
        int i, j, all;
        Random random_num = new Random();
        //checking for 0/no images starting bottom then up
        for (all = 0; all < 9; all++) {
            for (i = 8; i >= 0; i--) {
                for (j = 8; j > 0; j--) {
                    if (gridArray[i][j] == 0) {
                        try{
                            Thread.sleep(100);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        gridArray[i][j] = gridArray[i][j - 1];
                        gridArray[i][j - 1] = 0;
                    }
                }
            }
            j=0;
            for (i = 0; i < 9; i++) {
                if (gridArray[i][j] == 0) {
                    valueofscore += 100;
                    gridArray[i][j] = random_num.nextInt(6) + 1;
                }
            }
        }
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(counter == 0){
                    x = (int) event.getX();
                    y = (int) event.getY();
                    initx = (int) event.getX()/160;
                    inity = (int) (event.getY()-400)/160;
                    if((initx == 7 || initx == 8) && (inity == -1 || inity == -2)){
                        finish();
                    }
                    if(initx < 9 && inity < 9 && initx > -1 && inity > -1) {
                        Log.i("TAG", "First touch success!");
                        Log.i("TAG", "X and Y coords are: "+initx+" "+inity);
                        temp_grid = gridArray[initx][inity];
                        switch (temp_grid){
                            case 1:
                                off1 = 10;
                                break;
                            case 2:
                                off2 = 10;
                                break;
                            case 3:
                                off3 = 10;
                                break;
                            case 4:
                                off4 = 10;
                                break;
                            case 5:
                                off5 = 10;
                                break;
                            case 6:
                                off6 = 10;
                                break;
                        }
                        Log.i("TAG", "First grid id number is: "+temp_grid);
                        counter += 1;
                    }
                    else{
                        Log.i("TAG", "First touch fail!");
                        Log.i("TAG", "X and Y coords are: "+initx+" "+inity);
                        counter = 0;
                    }
                }
                else if(counter == 1) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                    finalx = (int) event.getX()/160;
                    finaly = (int) (event.getY()-400)/160;
                    if(finalx < 9 && finaly < 9 && finalx > -1 && finaly > -1){
                        if(((finalx == initx+1 || finalx == initx-1)&&(finaly == inity)) || ((finaly == inity+1 || finaly == inity-1)&&(finalx == initx))){
                            Log.i("TAG", "Second touch success!");
                            Log.i("TAG", "X and Y coords are: "+finalx+" "+finaly);
                            temp_grid2 = gridArray[finalx][finaly];
                            Log.i("TAG", "First grid id number is: "+temp_grid);
                            Log.i("TAG", "Second grid id number is: "+temp_grid2);
                            gridArray[initx][inity] = temp_grid2;
                            gridArray[finalx][finaly] = temp_grid;
                            checkBoard();
                            dropBoard();
                            if(morethan == 0){
                                gridArray[initx][inity] = temp_grid;
                                gridArray[finalx][finaly] = temp_grid2;
                            }
                            else{
                                for(int i = 0; i < 10; i++){
                                    checkBoard();
                                    dropBoard();
                                }
                            }

                            Log.i("TAG", "First grid id number is: "+gridArray[initx][inity]);
                            Log.i("TAG", "Second grid id number is: "+gridArray[finalx][finaly]);
                            off1 = off2 = off3 = off4 = off5 = off6 = 0;


                            
                        }
                    }
                    else {
                        Log.i("TAG", "Second touch fail!");
                        Log.i("TAG", "X and Y coords are: "+finalx+" "+finaly);
                    }
                    counter = 0;
                }

        }


        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "touched down");
                x = (int) event.getX();
                y = (int) event.getY();
                initx = (int) event.getX();
                inity = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                x = (int) event.getX();
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "touched up");
                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }*/

        return false;
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
                if(point_score.equals("YOU WIN!!!")){
                    try{
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finish();
                }
                Canvas c = holder.lockCanvas();
                Paint Paint = new TextPaint();
                Paint.setTextSize(120);
                Paint.setTextAlign(android.graphics.Paint.Align.LEFT);
                Paint.setColor(Color.YELLOW);
                c.drawBitmap(screen,0,0,null);
                c.drawText("Score:", 25, 250, Paint);
                if(valueofscore >= 3000){
                    point_score = "YOU WIN!!!";
                    c.drawText(point_score, 400, 250, Paint);
                }
                else {
                    point_score = String.valueOf(valueofscore);
                    c.drawText(point_score, 400, 250, Paint);
                }
                for(int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        switch(gridArray[i][j]){
                            case 1:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off1;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb1,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 2:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off2;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb2,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 3:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off3;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb3,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 4:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off4;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb4,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 5:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off5;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb5,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 6:
                                if((i == initx && j == inity) && counter == 1){
                                    tempoff = off6;
                                }
                                else{
                                    tempoff = 0;
                                }
                                c.drawBitmap(orb6,(i*160)+tempoff,(j*160)+400+tempoff,null);
                                break;
                            case 0:
                                c.drawBitmap(orb0,(i*160),(j*160)+400,null);
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
