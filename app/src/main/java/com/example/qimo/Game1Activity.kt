package com.example.qimo

import android.app.Activity
import android.content.ContentValues
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import cn.edu.sicnu.cardgame.Card
import cn.edu.sicnu.cardgame.Card.Companion.idioms_count
import cn.edu.sicnu.cardgame.CardMatchingGame
import com.example.fragmentdemo.gamefragment.CardRecyclerViewAdapter
import com.example.qimo.CheckpointActivity.Companion.running
import com.example.qimo.CheckpointActivity.Companion.second
import com.example.qimo.CheckpointActivity.Companion.time
import kotlinx.android.synthetic.main.activity_game1.*
import java.util.*


class Game1Activity : AppCompatActivity() {
    companion object {
        private lateinit var game: CardMatchingGame

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)

        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
         //初始化闯关界面
        var imageindex = intent.getIntExtra("imageindex",0)
        imageView_idiom.setImageResource(Card.idiomImage[imageindex])
        button_changeidiom.isEnabled=false
        val hcopper=getHcopper()    //展示剩余铜钱
        textView_reCopper.text="剩余铜钱：${hcopper}"
        textView_promptprice.text="消耗铜钱：30"
        running =true




        lateinit var adapter: CardRecyclerViewAdapter
        game = CardMatchingGame(24)
        adapter = CardRecyclerViewAdapter(game)
        recylerView1.adapter = adapter


        val configuration = resources.configuration
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recylerView1.layoutManager = GridLayoutManager(this, 8)
        }else{
            val gridLayoutManager = GridLayoutManager(this, 6)
            recylerView1.layoutManager = gridLayoutManager
        }

        //放入正确成语
        val chengyu =Card.correct[imageindex]
        val random=Random()
        var i=0
        while(i<=3) {
            val index =random.nextInt(24)
            //防止把之前的正确的成语给替换了
            val word=game.cards[index].rank
            if(word!=chengyu[0].toString() && word!=chengyu[1].toString() && word!=chengyu[2].toString()
                && word!=chengyu[3].toString()){
                game.cards[index].rank=chengyu[i].toString()
                i++
            }
        }



        //提示
        var prompt_count=0 //点击提示次数
        button_Prompt.isEnabled=true
        button_Prompt.setOnClickListener {
           prompt_count++
            var copper=0
            if(prompt_count==1){
                copper=30
            }else if(prompt_count==2){
                copper=60
                button_Prompt.isEnabled=false
            }
            //提示后，修改拥有的铜钱
            val hcopper=getHcopper()  //获得当前的铜钱数量
            val contentValues2 = ContentValues().apply {
                put("Hcopper",hcopper-copper)
            }
            db.update(TABLE_NAME2,contentValues2,null, null)

            textView_reCopper.text="剩余铜钱：${hcopper-copper}"
            textView_promptprice.text="消耗铜钱：60"

            val chengyu =Card.correct[imageindex]
            val random = Random()
            var i=1
            while(i<=6){
                val index=random.nextInt(24)
                val word= game.cards[index].toString()
                if(word!=chengyu[0].toString() && word!=chengyu[1].toString() && word!=chengyu[2].toString() && word!=chengyu[3].toString()){
                    if(!game.cards[index].isTrue){
                        game.match(index)
                        i++
                    }

                }
            }
            updateUI()

        }


        //判断选择的成语与答案是否相等
        var textView_count = 1   //清除四个textview的内容
        var textView_choose=1
        val card = Card
        var xuanze=""            //记录选择的成语
        var match_count = arrayOfNulls<Int>(4)

        adapter.setOnCardClickListener {
            //将选择的字放入文本框
            if(textView_choose==1) textView_one?.text= game.cards[it].toString()
            if(textView_choose==2) textView_two.text= game.cards[it].toString()
            if(textView_choose==3) textView_three.text= game.cards[it].toString()
            if(textView_choose==4) textView_four.text= game.cards[it].toString()

            xuanze = textView_one.text.toString()+textView_two.text.toString()+textView_three.text.toString()+textView_four.text.toString()


            if(xuanze == card.correct[imageindex]){
               running =false
                //清楚选择框的内容
                xuanze=""
                textView_one.text=""
                textView_two.text=""
                textView_three.text=""
                textView_four.text=""

                Toast.makeText(this,"恭喜选对了", Toast.LENGTH_SHORT)
                    //.setGravity(Gravity.CENTER, 0, 0)
                    .show()

                var grade=0

                var scores=""
                if(second<=10){
                    scores="★★★"
                    grade=3
                }else if(second<=25){
                    scores="★★☆"
                    grade=2
                }else if(second<=40){
                    scores="★☆☆"
                    grade=1
                }else{
                    scores="☆☆☆"
                }
                score.text="成绩：${scores}"
                button_changeidiom.isEnabled=true
                val copper=getCopper(grade,imageindex)  //可得铜钱

                //更改成绩
                val getgrade=getLockgrade(imageindex)

                if(getgrade!=3){  //如果等于3，星级将不会被更改
                    val contentValues = ContentValues().apply {
                        put("checkpoint","第${imageindex+1}关")
                        put("score","${scores}")
                        put("ispass","已过关")
                        put("time", time)
                        put("Getcopper",50-copper)
                        put("lockgrade",grade)
                    }
                    db.update(TABLE_NAME,contentValues,"checkpoint = ?", arrayOf("第${imageindex+1}关"))
                }

                //更改玩家表 gameId text,Honor text,percen text,Hcopper integer)

                val honor=getHonor(imageindex+1)  //获得荣誉称号
                val hcopper=getHcopper()  //获得当前的铜钱数量
                val contentValues2 = ContentValues().apply {
                    put("Honor",honor)
                    put("percen"," ${imageindex+1}/${idioms_count}")
                    put("Hcopper",hcopper+copper)
                }
                db.update(TABLE_NAME2,contentValues2,null, null)
                textView_reCopper.text="${getHcopper()}"
                second=0
            }
            if(xuanze.length==4 && xuanze!=card.correct[imageindex]){

                val chengyu1 =Card.correct[imageindex]

                var count=0
                if(textView_one.text.toString()==chengyu1[0].toString()){ count++ }
                if(textView_two.text.toString()==chengyu1[1].toString()){ count++ }
                if(textView_three.text.toString()==chengyu1[2].toString()){ count++ }
                if(textView_four.text.toString()==chengyu1[3].toString()){ count++ }

                when(count){
                    0-> Toast.makeText(this,"一个都没有选对哦，要多了解点成语呀", Toast.LENGTH_SHORT).show()
                    1-> Toast.makeText(this,"对了一个哦，请继续努力呀", Toast.LENGTH_SHORT).show()
                    2-> Toast.makeText(this,"对了两个啊，好棒啊", Toast.LENGTH_SHORT).show()
                    3-> Toast.makeText(this,"有三个都选对了，离胜利就差一步了哟", Toast.LENGTH_SHORT).show()
                }
                textView_one.text=""
                textView_two.text=""
                textView_three.text=""
                textView_four.text=""
            }
        }
        //四个选择框
        textView_one.setOnClickListener {
            textView_one.setBackgroundResource(R.drawable.shapeblue)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)
            textView_choose=1
        }
        textView_two.setOnClickListener {
            textView_two.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)

            textView_choose=2
        }
        textView_three.setOnClickListener {
            textView_three.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)
            textView_choose=3
        }
        textView_four.setOnClickListener {
            textView_four.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_choose=4
        }

        //下一关
        button_changeidiom.setOnClickListener {
            imageindex++
            score.text="成绩"

            button_Prompt.isEnabled=true
            button_changeidiom.isEnabled=false
            textView_promptprice.text="消耗铜钱：30"

            imageView_idiom.setImageResource(Card.idiomImage[imageindex])
            val chengyu =Card.correct[imageindex]
            game.reset()
            var i=0
            while(i<=3) {
                val index =random.nextInt(24)
                //防止把之前的正确的成语给替换了
                val word=game.cards[index].rank
                if(word!=chengyu[0].toString() && word!=chengyu[1].toString() && word!=chengyu[2].toString()
                    && word!=chengyu[3].toString()){
                    game.cards[index].rank=chengyu[i].toString()
                    i++
                }
            }


            running =true
            updateUI()
        }
    }
    fun updateUI() {
        recylerView1.adapter?.notifyDataSetChanged()
    }
    fun getHonor(index:Int,):String {
        val honors=arrayOf("成语小白","成语进士","成语状元","成语之神")
        var honor="无"
       if(index<10){ return honor }
        else if(index<20){
           honor=honors[0]
       }else if(index<30){
           honor=honors[1]
       }else if(index<40){
           honor=honors[2]
       }
        return "荣誉称号："+honor
    }
    fun getCopper(grade: Int,imageindex:Int):Int{
        var canGetcopper=0
        val guanka="第${imageindex+1}关"
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME,null,"checkpoint like '%$guanka%'", null,null,null,null)

        if(cursor.moveToFirst()){
            do {
                canGetcopper=cursor.getInt(cursor.getColumnIndex("Getcopper"))

            }while(cursor.moveToNext())
        }
        cursor.close()

        var hcopper=0
        if(canGetcopper==0){
            hcopper=0
        }else{
            if(grade==1 ){
                if(canGetcopper>=15){
                    hcopper=15
                }else{
                    hcopper=canGetcopper
                }

            }else if(grade==2){
                if(canGetcopper>=35){
                    hcopper=35
                }else{
                    hcopper=canGetcopper
                }
            }else{
                if(canGetcopper>=50){
                    hcopper=50
                }else{
                    hcopper=canGetcopper
                }
            }

        }
        val value =ContentValues()
        value.put("Getcopper",canGetcopper-hcopper)
        db.update(TABLE_NAME,value,"checkpoint = ?", arrayOf("第${imageindex+1}关"))
        return hcopper
    }
    fun getLockgrade(imageindex: Int):Int{
        var lockgrade=0
        val guanka="第${imageindex+1}关"
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME,null,"checkpoint like '%$guanka%'", null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                lockgrade=cursor.getInt(cursor.getColumnIndex("lockgrade"))
            }while(cursor.moveToNext())
        }
        cursor.close()
        return lockgrade
    }
    fun getHcopper():Int{
        var hcopper=0
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME2,null,null, null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                hcopper=cursor.getInt(cursor.getColumnIndex("Hcopper"))
            }while(cursor.moveToNext())
        }
        cursor.close()
        return hcopper
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("second",second)
        outState.putBoolean("running",running)
    }

}