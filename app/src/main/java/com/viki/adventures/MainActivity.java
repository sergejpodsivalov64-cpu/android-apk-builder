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
void choosePhrase(){if(recording)stop();String[] names=new String[script.length];for(int i=0;i<script.length;i++)names[i]=script[i][0]+"  "+script[i][1]+" — "+script[i][2];new AlertDialog.Builder(this).setTitle("Выбери реплику для записи").setSingleChoiceItems(names,-1,(dialog,which)->{String[] parts=script[which][0].split("_");save();scene=Integer.parseInt(parts[0]);line=Integer.parseInt(parts[1]);refresh();dialog.dismiss();}).setNegativeButton("Отмена",null).show();}
void draw(){ScrollView sc=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setPadding(28,32,28,28);root.setOrientation(1);root.setBackgroundColor(0xFFFFF5E7);sc.addView(root);setContentView(sc);label=new TextView(this);label.setTextSize(25);label.setTextColor(0xFF58361F);label.setPadding(0,0,0,18);root.addView(label);TextView note=new TextView(this);note.setText("Студия озвучки Вики • 16 сцен • 87 готовых реплик");note.setTextSize(16);note.setTextColor(0xFF76543B);note.setPadding(0,0,0,14);root.addView(note);text=new EditText(this);text.setMinLines(5);text.setTextSize(20);text.setTextColor(0xFF332A24);text.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFBA7E4D));text.setHint("Текст реплики");root.addView(text);
btn(root,"📋  СПИСОК ВСЕХ 87 РЕПЛИК",()->choosePhrase());
btn(root,"Сохранить текст",()->{save();Toast.makeText(this,"Сохранено",0).show();});
btn(root,"🎙  ЗАПИСАТЬ / ОСТАНОВИТЬ",()->{if(recording){stop();refresh();}else{if(Build.VERSION.SDK_INT>=23&&checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);return;}record();}});
btn(root,"▶  ПРОСЛУШАТЬ",()->{try{if(player!=null)player.release();player=new MediaPlayer();player.setDataSource(audio().getAbsolutePath());player.prepare();player.start();}catch(Exception e){Toast.makeText(this,"Записи пока нет",0).show();}});
btn(root,"◀  ПРЕДЫДУЩАЯ РЕПЛИКА",()->{if(recording){stop();}save();if(line>1)line--;else if(scene>1){scene--;line=counts[scene-1];}refresh();});
btn(root,"СЛЕДУЮЩАЯ РЕПЛИКА  ▶",()->{if(recording){stop();}save();if(line<counts[scene-1])line++;else if(scene<16){scene++;line=1;}refresh();});
btn(root,"📦  СОХРАНИТЬ ВСЕ ЗАПИСИ В ZIP",()->{save();Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("application/zip");i.putExtra(Intent.EXTRA_TITLE,"VoiceWiki.zip");startActivityForResult(i,10);});refresh();}
void save(){getPreferences(0).edit().putString(id(),text.getText().toString()).apply();}
void refresh(){int done=0;for(int ss=1;ss<=16;ss++)for(int ll=1;ll<=counts[ss-1];ll++)if(new File(dir,String.format(Locale.US,"scene_%02d_line_%02d.m4a",ss,ll)).exists())done++;label.setText("🐷 Три поросёнка\n"+sceneNames[scene-1]+"\nСцена "+scene+"/16 • Реплика "+line+"/"+counts[scene-1]+"\nЗаписано "+done+"/87"+(audio().exists()?" ✓":""));String[] row=scripted();text.setText(getPreferences(0).getString(id(),row[2]));label.append("\n"+row[1]);}
void record(){try{save();rec=new MediaRecorder();rec.setAudioSource(MediaRecorder.AudioSource.MIC);rec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);rec.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);rec.setOutputFile(audio().getAbsolutePath());rec.prepare();rec.start();recording=true;Toast.makeText(this,"Запись идёт",0).show();}catch(Exception e){if(rec!=null){rec.release();rec=null;}Toast.makeText(this,"Ошибка записи",0).show();}}
void stop(){if(rec!=null){try{rec.stop();}catch(Exception e){}rec.release();rec=null;}recording=false;}
protected void onActivityResult(int r,int code,Intent data){super.onActivityResult(r,code,data);if(r==10&&code==RESULT_OK&&data!=null){try(ZipOutputStream z=new ZipOutputStream(getContentResolver().openOutputStream(data.getData()))){byte[] b=new byte[8192];for(File f:dir.listFiles()){z.putNextEntry(new ZipEntry(f.getName()));try(FileInputStream in=new FileInputStream(f)){int n;while((n=in.read(b))>0)z.write(b,0,n);}z.closeEntry();}StringBuilder t=new StringBuilder();for(int s=1;s<=16;s++)for(int l=1;l<=counts[s-1];l++)t.append(String.format(Locale.US,"scene_%02d_line_%02d",s,l)).append(": ").append(getPreferences(0).getString(String.format(Locale.US,"scene_%02d_line_%02d",s,l),defaultLine(s,l))).append("\n");z.putNextEntry(new ZipEntry("texts.txt"));z.write(t.toString().getBytes("UTF-8"));z.closeEntry();Toast.makeText(this,"ZIP сохранён",0).show();}catch(Exception e){Toast.makeText(this,"Ошибка экспорта",0).show();}}}
protected void onPause(){if(recording)stop();super.onPause();}protected void onDestroy(){if(player!=null)player.release();super.onDestroy();}
}