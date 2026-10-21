package com.viki.adventures;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
  TextToSpeech speech;
  Forest forest;
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    getWindow().getDecorView().setSystemUiVisibility(5894 | 1024 | 512);
    forest = new Forest(); setContentView(forest);
    speech = new TextToSpeech(this,this);
  }
  @Override public void onInit(int status) {
    if(status==TextToSpeech.SUCCESS) {
      speech.setLanguage(new Locale("ru","RU"));
      speak("Добро пожаловать в волшебный лес! Нажми на зверюшку!");
    }
  }
  void speak(String s) { if(speech!=null) speech.speak(s,TextToSpeech.QUEUE_FLUSH,null,"viki"); }
  @Override protected void onDestroy() { if(speech!=null){speech.stop();speech.shutdown();} super.onDestroy(); }
  class Forest extends View {
    Canvas c; Paint p=new Paint(3); float w,h,t=0; int selected=-1;
    String[] names={"Зайчик","Лисичка","Мишка"};
    int[] body={0xffeeeeee,0xfff49b43,0xffa36b43};
    Forest(){super(MainActivity.this);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
    void oval(int color,float x,float y,float rx,float ry) {p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawOval(x-rx,y-ry,x+rx,y+ry,p);}
    void glow(float x,float y,float r,int color){p.setShader(new RadialGradient(x,y,r,new int[]{color,0x00ffffff},null,Shader.TileMode.CLAMP));c.drawCircle(x,y,r,p);p.setShader(null);}
    void cloud(float x,float y,float scale){c.save();c.translate(x,y);c.scale(scale,scale);oval(0x66ffffff,0,9,88,22);circle(0xfffdfaf1,-42,0,29);circle(0xffffffff,-5,-17,40);circle(0xffffffff,37,0,30);c.restore();}
    void tree(float x,float y,float scale){c.save();c.translate(x,y);c.scale(scale,scale);oval(0x44554c31,10,8,66,14);p.setShader(new LinearGradient(-22,-120,24,10,0xffad7748,0xff68462e,Shader.TileMode.CLAMP));c.drawRoundRect(-20,-160,20,0,12,12,p);p.setShader(null);circle(0xff327d4e,-45,-163,62);circle(0xff3e9958,28,-193,75);circle(0xff5bb66b,-12,-238,64);circle(0xff74c67b,46,-224,37);c.restore();}
    void flower(float x,float y,int color){line(0xff42884b,x,y+12,x,y-4,3);for(int a=0;a<5;a++){double v=a*Math.PI*2/5;circle(color,x+(float)Math.cos(v)*7,y-7+(float)Math.sin(v)*7,5);}circle(0xffffdd55,x,y-7,4);}
    void circle(int color,float x,float y,float r){p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawCircle(x,y,r,p);}
    void line(int color,float x,float y,float xx,float yy,float stroke){p.setColor(color);p.setStrokeWidth(stroke);p.setStrokeCap(Paint.Cap.ROUND);c.drawLine(x,y,xx,yy,p);}
    void text(String s,float x,float y,int size,int color){p.setColor(color);p.setTextAlign(Paint.Align.CENTER);p.setTypeface(Typeface.create("sans-serif-rounded",Typeface.BOLD));p.setTextSize(size);c.drawText(s,x,y,p);}
    @Override protected void onDraw(Canvas canvas){
      c=canvas; w=getWidth();h=getHeight(); c.save(); c.scale(w/1000f,h/600f); t+=0.035f;
      p.setShader(new LinearGradient(0,0,0,600,0xff83d8f5,0xfff7f5bb,Shader.TileMode.CLAMP));c.drawRect(0,0,1000,600,p);p.setShader(null);
      glow(850,100,145,0x88fff5bb);circle(0xffffe6a0,850,100,58);cloud(180+(float)Math.sin(t*.13f)*24,105,1.2f);cloud(660-(float)Math.sin(t*.09f)*20,154,.75f);
      for(int i=0;i<8;i++){float x=i*150-45;oval(0xff79bc86,x,360,110,175);oval(0xff5d9d6c,x+28,310,75,125);}
      tree(65,405,.85f);tree(935,412,1.0f);
      p.setColor(0xff79c879);c.drawRect(0,390,1000,600,p);
      oval(0xffb3db7a,500,570,610,135);
      for(int i=0;i<22;i++){float x=(i*79)%1000,y=460+(i*43)%125;flower(x,y,i%2==0?0xffffb6d4:0xfffff1a1);}
      for(int i=0;i<5;i++){float bx=110+i*185+(float)Math.sin(t*.7f+i)*24,by=215+(i%2)*90+(float)Math.cos(t+i)*17;oval(0x99ffb9de,bx-7,by-3,9,13);oval(0x99b9d9ff,bx+7,by-3,9,13);line(0xff795b72,bx,by-8,bx,by+10,3);}
      text("ВОЛШЕБНЫЙ ЛЕС",500,76,46,0xff356c58);
      text("Нажми на зверюшку!",500,117,26,0xff356c58);
      for(int i=0;i<3;i++)animal(i,230+i*270,390+(float)Math.sin(t+i)*5);
      c.restore();postInvalidateDelayed(32);
    }
    void animal(int i,float x,float y){
      c.save();c.translate(x,y);float bounce=selected==i?(float)Math.sin(t*4)*10:0;c.translate(0,bounce);
      oval(0x55446644,0,164,91,17);glow(-26,-48,110,0x44ffffff);
      oval(body[i],0,92,76,89);
      if(i==0){oval(0xffe9e9e9,-43,-89,22,87);oval(0xffe9e9e9,43,-89,22,87);oval(0xffffbdcc,-43,-89,11,67);oval(0xffffbdcc,43,-89,11,67);}
      else {circle(body[i],-65,-41,31);circle(body[i],65,-41,31);}
      circle(body[i],0,-18,79);
      oval(0xfffff2db,0,18,53,40);
      circle(0xff302d38,-29,-28,9);circle(0xff302d38,29,-28,9);
      circle(0xffffffff,-32,-32,3);circle(0xffffffff,26,-32,3);
      oval(0xfff68b9a,0,7,11,8);
      line(0xff77515a,0,15,-12,24,3);line(0xff77515a,0,15,12,24,3);
      circle(0xffffb0ae,-48,7,13);circle(0xffffb0ae,48,7,13);
      oval(body[i],-48,152,29,19);oval(body[i],48,152,29,19);
      text(names[i],0,208,25,0xff335b48);
      c.restore();
    }
    @Override public boolean onTouchEvent(android.view.MotionEvent e){
      if(e.getAction()==MotionEvent.ACTION_UP){
        float x=e.getX()*1000/getWidth(), y=e.getY()*600/getHeight();
        for(int i=0;i<3;i++)if(Math.abs(x-(230+i*270))<115 && y>215 && y<565){
          selected=i;
          speak(new String[]{"Привет! Я зайчик! Прыг-прыг!","Привет! Я лисичка!","Привет! Я мишка! Р-р-р!"}[i]);
          invalidate();break;
        }
        return true;
      }
      return true;
    }
  }
}
