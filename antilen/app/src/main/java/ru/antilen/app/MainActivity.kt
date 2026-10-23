package ru.antilen.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : Activity() {
    private val tasks = mutableListOf<Pair<String,Int>>()
    private val goals = mutableListOf<Pair<String,Int>>()
    private var earned = 0
    private var saved = 0
    private var xp = 0
    private lateinit var root: LinearLayout
    private val rewards = listOf(20,50,100,200)
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); load(); render() }
    private fun load() {
        val p=getSharedPreferences("data",0)
        earned=p.getInt("earned",0); saved=p.getInt("saved",0); xp=p.getInt("xp",0)
        val a=JSONArray(p.getString("tasks","[]")); for(i in 0 until a.length()){val o=a.getJSONObject(i);tasks.add(o.getString("name") to o.getInt("reward"))}
        val g=JSONArray(p.getString("goals","[]"));for(i in 0 until g.length()){val o=g.getJSONObject(i);goals.add(o.getString("name") to o.getInt("target"))}
    }
    private fun persist(){
        val a=JSONArray();tasks.forEach { (n,r)->a.put(JSONObject().put("name",n).put("reward",r)) }
        val g=JSONArray();goals.forEach { (n,t)->g.put(JSONObject().put("name",n).put("target",t)) }
        getSharedPreferences("data",0).edit().putInt("earned",earned).putInt("saved",saved).putInt("xp",xp).putString("tasks",a.toString()).putString("goals",g.toString()).apply()
    }
    private fun label(s:String,size:Float=18f):TextView=TextView(this).apply { text=s;textSize=size;setTextColor(Color.rgb(35,39,65));setPadding(12,12,12,12) }
    private fun button(s:String,action:()->Unit){root.addView(Button(this).apply{text=s;setOnClickListener{action()}})}
    private fun ask(title:String,hint:String,done:(String)->Unit){val e=EditText(this);e.hint=hint;android.app.AlertDialog.Builder(this).setTitle(title).setView(e).setPositiveButton("Добавить"){_,_->val v=e.text.toString().trim();if(v.isNotEmpty())done(v)}.setNegativeButton("Отмена",null).show()}
    private fun render(){
        val scroll=ScrollView(this);root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(20,24,20,24);setBackgroundColor(Color.rgb(245,247,255))}
        scroll.addView(root);setContentView(scroll)
        root.addView(label("⚔ Антилень",30f))
        root.addView(label("Герой: уровень ${xp/100+1} • опыт ${xp%100}/100\nЗаработано: ${earned} ₽ • отложено: ${saved} ₽"))
        root.addView(label("  ◉\n /|\\  ⚔\n / \\ \nТвой герой становится сильнее!",22f))
        button("Добавить задачу"){ask("Новое дело","Что нужно сделать?"){name->
            val options=arrayOf("Легко · 20 ₽","Средне · 50 ₽","Сложно · 100 ₽","Очень сложно · 200 ₽")
            android.app.AlertDialog.Builder(this).setTitle("Оцени сложность").setItems(options){_,which->tasks.add(name to rewards[which]);persist();render()}.show()
        }}
        button("Предложить квест"){val pool=listOf("Пять минут уборки","Прогулка 15 минут","Разобрать одну полку","Сделать зарядку","Закончить отложенное дело");val n=pool[(System.currentTimeMillis()%pool.size).toInt()];tasks.add(n to 50);persist();render()}
        root.addView(label("Задания",23f))
        if(tasks.isEmpty())root.addView(label("Пока нет заданий. Добавь первое!"))
        tasks.toList().forEach{ (name,reward)->val row=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL};row.addView(label("$name · $reward ₽"));row.addView(Button(this).apply{text="Выполнено ✓";setOnClickListener{tasks.remove(name to reward);earned+=reward;xp+=25;persist();render()}});root.addView(row)}
        root.addView(label("Копилки",23f))
        button("Создать цель"){ask("Новая копилка","Название цели"){name->ask("Сколько рублей?","Например, 5000"){amount->val n=amount.toIntOrNull();if(n!=null&&n>0){goals.add(name to n);persist();render()}}}}
        button("Я реально отложил деньги"){ask("Пополнение","Сумма в рублях"){s->val n=s.toIntOrNull();if(n!=null&&n>0){saved+=n;persist();render()}}}
        goals.forEach{(name,target)->root.addView(label("$name: цель $target ₽ (общая копилка $saved ₽)"))}
        root.addView(label("Деньги в приложении — учёт, а не банковский перевод.",13f))
    }
}
