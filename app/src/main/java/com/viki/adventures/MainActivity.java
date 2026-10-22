package com.viki.adventures;
import android.Manifest;import android.app.*;import android.os.*;import android.content.*;import android.content.pm.PackageManager;import android.media.*;import android.view.*;import android.widget.*;import java.io.*;import java.util.*;import java.util.zip.*;
public class MainActivity extends Activity{
int scene=1,line=1;String[] sceneNames={"Знакомство","Летняя поляна","Наступила осень","Выбор материалов","Домик из соломы","Домик из веток","Кирпичный дом","Весёлые братья","Появление волка","Соломенный дом","Побег к брату","Дом из веток","У Наф-Нафа","Крепкий дом","Волк и труба","Счастливый конец"};int[] counts={6,4,5,5,6,5,6,5,6,6,4,6,6,5,6,6};MediaRecorder rec;MediaPlayer player;EditText text;TextView label;File dir;boolean recording=false;
String[][] script={{"01_001","Рассказчик","Жили-были три поросёнка, три весёлых брата: Ниф-Ниф, Нуф-Нуф и Наф-Наф."},
{"01_002","Рассказчик","У каждого был розовый пятачок, маленькие ушки и хвостик крючком."},
{"01_003","Рассказчик","Давай познакомимся! Нажми на каждого поросёнка."},
{"01_004","Ниф-Ниф","Привет! Я Ниф-Ниф!"},
{"01_005","Нуф-Нуф","А я Нуф-Нуф!"},
{"01_006","Наф-Наф","А я Наф-Наф!"},
{"02_001","Рассказчик","Всё лето братья играли, прыгали и веселились на зелёной поляне."},
{"02_002","Рассказчик","Они бегали к ручью, собирали ягоды и грелись на солнышке."},
{"02_003","Рассказчик","Ой! А куда спрятались поросята? Помоги их найти!"},
{"02_004","Рассказчик","Вот они! Молодец!"},
{"03_001","Рассказчик","Но лето закончилось. Наступила осень. Листья пожелтели, а ветер стал холоднее."},
{"03_002","Наф-Наф","Братья! Скоро зима. Пора построить тёплые дома!"},
{"03_003","Ниф-Ниф","Успеется!"},
{"03_004","Нуф-Нуф","Давайте ещё погуляем!"},
{"03_005","Рассказчик","Посмотри, как кружатся листья! Нажми на них."},
{"04_001","Рассказчик","Наконец братья решили построить каждый свой дом."},
{"04_002","Ниф-Ниф","Я возьму солому! Это быстро!"},
{"04_003","Нуф-Нуф","А я построю дом из веток!"},
{"04_004","Наф-Наф","Я выберу кирпичи. Мой дом будет крепким!"},
{"04_005","Рассказчик","Помоги братьям выбрать материалы!"},
{"05_001","Рассказчик","Ниф-Ниф собрал солому и быстро принялся строить домик."},
{"05_002","Ниф-Ниф","Помоги мне поставить стены!"},
{"05_003","Ниф-Ниф","А теперь крышу!"},
{"05_004","Ниф-Ниф","И дверь!"},
{"05_005","Рассказчик","Домик готов!"},
{"05_006","Ниф-Ниф","Ура! Можно отдыхать!"},
{"06_001","Рассказчик","Нуф-Нуф собрал ветки и стал строить свой дом."},
{"06_002","Нуф-Нуф","Поставим стены!"},
{"06_003","Нуф-Нуф","Теперь крышу!"},
{"06_004","Нуф-Нуф","Вот какой дом получился!"},
{"06_005","Рассказчик","Нуф-Нуф тоже закончил работу и отправился гулять."},
{"07_001","Рассказчик","А Наф-Наф трудился долго и старательно. Он носил кирпичи и выкладывал прочные стены."},
{"07_002","Наф-Наф","Положим первый кирпич!"},
{"07_003","Наф-Наф","Теперь следующий!"},
{"07_004","Наф-Наф","Поставим крепкую дверь и крышу."},
{"07_005","Рассказчик","Наконец кирпичный дом был готов."},
{"07_006","Наф-Наф","Теперь нам будет тепло и безопасно!"},
{"08_001","Рассказчик","Ниф-Ниф и Нуф-Нуф уже веселились на поляне."},
{"08_002","Ниф-Ниф","Наф-Наф, хватит работать!"},
{"08_003","Нуф-Нуф","Пойдём играть!"},
{"08_004","Наф-Наф","Сначала закончу дом, братья!"},
{"08_005","Рассказчик","А два брата побежали дальше по тропинке."},
{"09_001","Рассказчик","Вдруг за деревьями что-то зашуршало."},
{"09_002","Рассказчик","Из кустов вышел большой серый волк!"},
{"09_003","Волк","Ага! Вот вы где, поросята!"},
{"09_004","Ниф-Ниф","Ой, волк!"},
{"09_005","Нуф-Нуф","Скорее бежим!"},
{"09_006","Рассказчик","Помоги поросятам спрятаться!"},
{"10_001","Рассказчик","Ниф-Ниф забежал в соломенный домик и закрыл дверь."},
{"10_002","Волк","Ниф-Ниф! Открывай!"},
{"10_003","Ниф-Ниф","Не открою!"},
{"10_004","Волк","Тогда я как дуну!"},
{"10_005","Рассказчик","Волк набрал воздуха и сильно подул."},
{"10_006","Рассказчик","Солома разлетелась, и домик развалился!"},
{"11_001","Рассказчик","Ниф-Ниф бросился бежать к брату Нуф-Нуфу."},
{"11_002","Ниф-Ниф","Нуф-Нуф! Открывай скорее!"},
{"11_003","Нуф-Нуф","Заходи, брат!"},
{"11_004","Рассказчик","Оба поросёнка спрятались в доме из веток."},
{"12_001","Рассказчик","Но волк добежал и до второго домика."},
{"12_002","Волк","Открывайте дверь!"},
{"12_003","Нуф-Нуф","Не откроем!"},
{"12_004","Волк","Тогда я сдую и этот дом!"},
{"12_005","Рассказчик","Волк подул изо всех сил. Ветки затрещали, и дом развалился."},
{"12_006","Ниф-Ниф","Бежим к Наф-Нафу!"},
{"13_001","Рассказчик","Два брата прибежали к крепкому кирпичному дому."},
{"13_002","Ниф-Ниф","Наф-Наф! Спаси нас!"},
{"13_003","Нуф-Нуф","За нами волк!"},
{"13_004","Наф-Наф","Скорее заходите!"},
{"13_005","Рассказчик","Наф-Наф впустил братьев и крепко закрыл дверь."},
{"13_006","Наф-Наф","Не бойтесь. Здесь мы в безопасности!"},
{"14_001","Волк","Открывайте!"},
{"14_002","Наф-Наф","Нет, волк!"},
{"14_003","Рассказчик","Волк дул и дул, но кирпичный дом даже не пошевелился."},
{"14_004","Волк","Ух! Какой крепкий дом!"},
{"14_005","Рассказчик","Волк устал, но придумал новую хитрость."},
{"15_001","Рассказчик","Волк заметил трубу на крыше и решил пробраться внутрь."},
{"15_002","Волк","Ага! Вот как я попаду в дом!"},
{"15_003","Наф-Наф","Братья, приготовим котёл у камина!"},
{"15_004","Рассказчик","Волк полез в трубу, услышал шум внизу, испугался и поспешил обратно."},
{"15_005","Волк","Ой-ой-ой! Убегаю!"},
{"15_006","Рассказчик","Волк выскочил на крышу и убежал далеко в лес."},
{"16_001","Рассказчик","Больше волк не беспокоил трёх поросят."},
{"16_002","Ниф-Ниф","Спасибо, Наф-Наф!"},
{"16_003","Нуф-Нуф","Теперь мы тоже будем строить крепкие дома!"},
{"16_004","Наф-Наф","Вместе нам ничего не страшно!"},
{"16_005","Рассказчик","С тех пор братья жили дружно и счастливо."},
{"16_006","Рассказчик","Вот и сказочке конец! А ты молодец!"}};
String defaultLine(int s,int l){String key=String.format(Locale.US,"%02d_%03d",s,l);for(String[] row:script)if(row[0].equals(key))return row[2];return "";}String[] scripted(){String key=String.format(Locale.US,"%02d_%03d",scene,line);for(String[] row:script)if(row[0].equals(key))return row;return new String[]{"","",""};}
String id(){return String.format(Locale.US,"scene_%02d_line_%02d",scene,line);}File audio(){return new File(dir,id()+".m4a");}
public void onCreate(Bundle b){super.onCreate(b);dir=new File(getFilesDir(),"recordings");dir.mkdirs();draw();}
void btn(LinearLayout root,String caption,Runnable action){Button b=new Button(this);b.setText(caption);root.addView(b);b.setOnClickListener(v->action.run());}
void choosePhrase(){if(recording)stop();String[] names=new String[script.length];for(int i=0;i<script.length;i++)names[i]=script[i][0]+"  "+script[i][1]+" — "+script[i][2];new AlertDialog.Builder(this).setTitle("Выберите реплику").setItems(names,(dialog,which)->{String[] parts=script[which][0].split("_");save();scene=Integer.parseInt(parts[0]);line=Integer.parseInt(parts[1]);refresh();dialog.dismiss();}).setNegativeButton("Отмена",null).show();}
TextView progress,status;Button recButton,playButton;
void draw(){getWindow().setStatusBarColor(0xFF314163);ScrollView sc=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setPadding(24,30,24,16);root.setOrientation(1);root.setBackgroundColor(0xFFF8F9FD);sc.addView(root);setContentView(sc);
TextView title=new TextView(this);title.setText("🎙 Студия озвучки Вики");title.setTextSize(23);title.setTextColor(0xFF202B3E);title.setPadding(8,14,8,14);root.addView(title);
progress=new TextView(this);progress.setTextSize(16);progress.setTextColor(0xFF202B3E);progress.setPadding(8,14,8,14);root.addView(progress);
label=new TextView(this);label.setTextSize(16);label.setTextColor(0xFF202B3E);label.setPadding(8,14,8,14);root.addView(label);
text=new EditText(this);text.setTextSize(22);text.setTextColor(0xFF202B3E);text.setMinHeight(180);text.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF65738C));root.addView(text);
status=new TextView(this);status.setTextSize(15);status.setTextColor(0xFF202B3E);status.setPadding(8,14,8,14);root.addView(status);
recButton=new Button(this);recButton.setText("🔴 Начать запись");root.addView(recButton);recButton.setOnClickListener(v->{if(recording){stop();refresh();}else{if(Build.VERSION.SDK_INT>=23&&checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);return;}record();refresh();}});
playButton=new Button(this);playButton.setText("▶ Прослушать");root.addView(playButton);playButton.setOnClickListener(v->{try{if(player!=null)player.release();player=new MediaPlayer();player.setDataSource(audio().getAbsolutePath());player.prepare();player.start();}catch(Exception e){Toast.makeText(this,"Записи пока нет",0).show();}});
LinearLayout nav=new LinearLayout(this);nav.setOrientation(0);root.addView(nav);btn(nav,"← Назад",()->move(-1));btn(nav,"Далее →",()->move(1));
btn(root,"Перейти к реплике…",()->choosePhrase());
btn(root,"📦 Экспорт ZIP",()->{save();Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("application/zip");i.putExtra(Intent.EXTRA_TITLE,"Viki_Three_Pigs_Voices.zip");startActivityForResult(i,10);});
TextView footer=new TextView(this);footer.setText("Файлы сохраняются на устройстве. Экспорт создаёт ZIP с M4A-записями и текстами реплик.");footer.setTextSize(14);footer.setTextColor(0xFF202B3E);root.addView(footer);refresh();}
void move(int delta){if(recording)stop();if(player!=null){player.release();player=null;}save();int idx=(scene==1?0:0);for(int i=0;i<scene-1;i++)idx+=counts[i];idx+=line-1;idx=Math.max(0,Math.min(script.length-1,idx+delta));String[] parts=script[idx][0].split("_");scene=Integer.parseInt(parts[0]);line=Integer.parseInt(parts[1]);refresh();}
void save(){getPreferences(0).edit().putString(id(),text.getText().toString()).apply();}
void refresh(){int done=0;for(int ss=1;ss<=16;ss++)for(int ll=1;ll<=counts[ss-1];ll++)if(new File(dir,String.format(Locale.US,"scene_%02d_line_%02d.m4a",ss,ll)).exists())done++;int pos=0;for(int i=0;i<scene-1;i++)pos+=counts[i];pos+=line;progress.setText("Записано: "+done+" из "+script.length+"     •     Реплика "+pos);String[] row=scripted();label.setText(sceneNames[scene-1]+"  •  "+row[1]);text.setText(getPreferences(0).getString(id(),row[2]));status.setText(id()+".m4a  •  "+(audio().exists()?"✅ Записано":"○ Не записано"));recButton.setText(recording?"■ Завершить запись":"🔴 Начать запись");playButton.setEnabled(audio().exists()&&!recording);}
void record(){try{save();rec=new MediaRecorder();rec.setAudioSource(MediaRecorder.AudioSource.MIC);rec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);rec.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);rec.setOutputFile(audio().getAbsolutePath());rec.prepare();rec.start();recording=true;Toast.makeText(this,"Запись идёт",0).show();}catch(Exception e){if(rec!=null){rec.release();rec=null;}Toast.makeText(this,"Ошибка записи",0).show();}}
void stop(){if(rec!=null){try{rec.stop();}catch(Exception e){}rec.release();rec=null;}recording=false;}
protected void onActivityResult(int r,int code,Intent data){super.onActivityResult(r,code,data);if(r==10&&code==RESULT_OK&&data!=null){try(ZipOutputStream z=new ZipOutputStream(getContentResolver().openOutputStream(data.getData()))){byte[] b=new byte[8192];for(File f:dir.listFiles()){z.putNextEntry(new ZipEntry(f.getName()));try(FileInputStream in=new FileInputStream(f)){int n;while((n=in.read(b))>0)z.write(b,0,n);}z.closeEntry();}StringBuilder t=new StringBuilder();for(int s=1;s<=16;s++)for(int l=1;l<=counts[s-1];l++)t.append(String.format(Locale.US,"scene_%02d_line_%02d",s,l)).append(": ").append(getPreferences(0).getString(String.format(Locale.US,"scene_%02d_line_%02d",s,l),defaultLine(s,l))).append("\n");z.putNextEntry(new ZipEntry("texts.txt"));z.write(t.toString().getBytes("UTF-8"));z.closeEntry();Toast.makeText(this,"ZIP сохранён",0).show();}catch(Exception e){Toast.makeText(this,"Ошибка экспорта",0).show();}}}
public void onRequestPermissionsResult(int r,String[] p,int[] g){super.onRequestPermissionsResult(r,p,g);if(r==1&&g.length>0&&g[0]==PackageManager.PERMISSION_GRANTED){record();refresh();}}protected void onPause(){if(recording)stop();super.onPause();}protected void onDestroy(){if(player!=null)player.release();super.onDestroy();}
}