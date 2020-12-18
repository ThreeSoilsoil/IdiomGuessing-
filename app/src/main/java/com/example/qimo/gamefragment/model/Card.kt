package cn.edu.sicnu.cardgame

import android.nfc.Tag
import android.util.Log
import cn.edu.sicnu.cardgame.Card.Companion.correct
import com.example.qimo.R
import java.io.Serializable

class Card(private var rank: String, var isTrue:Boolean = false): Serializable  {
    companion object {
        var rankStrings= arrayOf( "大", "打", "我", "非", "你", "她", "所", "问", "无", "道", "知", "不","满","腹","经","纶","天","罗","地","网","东","山","再","非")
        val correct = arrayOf("棒打鸳鸯","隔岸观火","花天酒地","金鸡独立","宽大为怀","流言蜚语","芒刺在背","人声鼎沸","推己及人","香草美人","杏雨梨云","一箭双雕","一往情深")
        val idiomImage = arrayOf(R.raw.bangdayuanyang,R.raw.geanguanhuo,R.raw.huatianjiudi,R.raw.jinjiduli,R.raw.kuandaweihuai,R.raw.liuyanfeiyu,R.raw.mangcizaibei,
            R.raw.renshengdingfei,R.raw.tuijijiren,R.raw.xiangcaomeiren,R.raw.xingyuliyun,R.raw.yijianshuangdiao,R.raw.yiwangqingshen)
        val idioms_count= correct.count()
    }
    public  fun getIstrue():Boolean{
        return isTrue
    }
    override fun toString(): String {
        return  rank
    }


}
fun main(){
    var a= correct[0]
    var b=a[1]
    val idioms_count= correct.count()
    println(b)
    println(idioms_count)
}
