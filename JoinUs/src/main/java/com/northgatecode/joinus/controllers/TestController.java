package com.northgatecode.joinus.controllers;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.mongodb.MongoClient;
import com.northgatecode.joinus.auth.Authenticated;
import com.northgatecode.joinus.auth.TryAuthenticate;
import com.northgatecode.joinus.auth.UserPrincipal;
import com.northgatecode.joinus.dto.CodeMessage;
import com.northgatecode.joinus.mongodb.*;
import com.northgatecode.joinus.providers.GsonMessageBodyHandler;
import com.northgatecode.joinus.utils.JpaHelper;
import com.northgatecode.joinus.utils.MorphiaHelper;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.List;

/**
 * Created by qianliang on 19/3/2016.
 */
@Path("test")
public class TestController {
    @Path("hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, Jersey is working.";
    }

    @Path("query")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String query(@QueryParam("name") String name) {
        return "Query String: name=" + name;
    }

    @GET
    @Path("auth")
    @Authenticated
    @Produces(MediaType.TEXT_PLAIN)
    public String auth(@Context SecurityContext sc) {
        return "User Name:" + sc.getUserPrincipal().getName();
    }

    @GET
    @Path("tryAuth")
    @TryAuthenticate
    @Produces(MediaType.TEXT_PLAIN)
    public String tryAuth(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        if (userPrincipal != null) {
            return "User Name:" + userPrincipal.getName();
        } else {
            return "no user Principal";
        }
    }

    @GET
    @Path("countries")
    public Response getForums(@QueryParam("search") String search) {

        Datastore datastore = MorphiaHelper.getDatastore();
        List<Country> countries;
        if (search != null && search.length() > 0) {
            countries = datastore.createQuery(Country.class).field("name").containsIgnoreCase(search).order("name").asList();
        } else {
            countries = datastore.createQuery(Country.class).order("name").asList();
        }

        return Response.ok(countries).build();
    }

    @POST
    @Path("sendSMS")
    @Produces(MediaType.APPLICATION_JSON)
    public CodeMessage sendSMS() {
        CodeMessage message = ClientBuilder.newClient().register(GsonMessageBodyHandler.class).target("http://api.weimi.cc/2/sms/send.html")
                .queryParam("uid", "GM9NRw5jBz0m")
                .queryParam("pas", "mm4bypzs")
                .queryParam("mob", "18637800688")
                .queryParam("cid", "b77b4s65tjjU")
                .queryParam("p1", "-Join Us-")
                .queryParam("p2", RandomStringUtils.randomNumeric(6))
                .queryParam("type", "json")
                .request(MediaType.APPLICATION_JSON_TYPE).get(CodeMessage.class);
        return message;
    }

    @GET
    @Path("initDB")
    @Produces(MediaType.TEXT_PLAIN)
    public String initdb() {
        Datastore datastore = MorphiaHelper.getDatastore();

        datastore.save(new Role(1, "临时用户"));
        datastore.save(new Role(2, "注册用户"));
        datastore.save(new Role(3, "付费用户"));
        datastore.save(new Role(8, "VIP用户"));
        datastore.save(new Role(100, "系统管理"));

        datastore.save(new Gender(1, "保密"));
        datastore.save(new Gender(2, "男"));
        datastore.save(new Gender(3, "女"));

        datastore.save(new Category(1, "手机数码"));
        datastore.save(new Category(2, "计算机技术"));
        datastore.save(new Category(3, "黑科技"));
        datastore.save(new Category(4, "娱乐明星"));
        datastore.save(new Category(5, "电影电视"));
        datastore.save(new Category(6, "体育"));
        datastore.save(new Category(7, "游戏"));
        datastore.save(new Category(8, "动漫"));
        datastore.save(new Category(9, "高校"));
        datastore.save(new Category(100, "其他"));



        datastore.save(new Province(110000, "北京市"));
        datastore.save(new Province(120000, "天津市"));
        datastore.save(new Province(130000, "河北省"));
        datastore.save(new Province(140000, "山西省"));
        datastore.save(new Province(150000, "内蒙古自治区"));
        datastore.save(new Province(210000, "辽宁省"));
        datastore.save(new Province(220000, "吉林省"));
        datastore.save(new Province(230000, "黑龙江省"));
        datastore.save(new Province(310000, "上海市"));
        datastore.save(new Province(320000, "江苏省"));
        datastore.save(new Province(330000, "浙江省"));
        datastore.save(new Province(340000, "安徽省"));
        datastore.save(new Province(350000, "福建省"));
        datastore.save(new Province(360000, "江西省"));
        datastore.save(new Province(370000, "山东省"));
        datastore.save(new Province(410000, "河南省"));
        datastore.save(new Province(420000, "湖北省"));
        datastore.save(new Province(430000, "湖南省"));
        datastore.save(new Province(440000, "广东省"));
        datastore.save(new Province(450000, "广西壮族自治区"));
        datastore.save(new Province(460000, "海南省"));
        datastore.save(new Province(500000, "重庆市"));
        datastore.save(new Province(510000, "四川省"));
        datastore.save(new Province(520000, "贵州省"));
        datastore.save(new Province(530000, "云南省"));
        datastore.save(new Province(540000, "西藏自治区"));
        datastore.save(new Province(610000, "陕西省"));
        datastore.save(new Province(620000, "甘肃省"));
        datastore.save(new Province(630000, "青海省"));
        datastore.save(new Province(640000, "宁夏回族自治区"));
        datastore.save(new Province(650000, "新疆维吾尔自治区"));
        datastore.save(new Province(710000, "台湾省"));
        datastore.save(new Province(810000, "香港特别行政区"));
        datastore.save(new Province(820000, "澳门特别行政区"));
        datastore.save(new Province(110000, "北京市"));
        datastore.save(new Province(120000, "天津市"));
        datastore.save(new Province(130000, "河北省"));
        datastore.save(new Province(140000, "山西省"));
        datastore.save(new Province(150000, "内蒙古自治区"));
        datastore.save(new Province(210000, "辽宁省"));
        datastore.save(new Province(220000, "吉林省"));
        datastore.save(new Province(230000, "黑龙江省"));
        datastore.save(new Province(310000, "上海市"));
        datastore.save(new Province(320000, "江苏省"));
        datastore.save(new Province(330000, "浙江省"));
        datastore.save(new Province(340000, "安徽省"));
        datastore.save(new Province(350000, "福建省"));
        datastore.save(new Province(360000, "江西省"));
        datastore.save(new Province(370000, "山东省"));
        datastore.save(new Province(410000, "河南省"));
        datastore.save(new Province(420000, "湖北省"));
        datastore.save(new Province(430000, "湖南省"));
        datastore.save(new Province(440000, "广东省"));
        datastore.save(new Province(450000, "广西壮族自治区"));
        datastore.save(new Province(460000, "海南省"));
        datastore.save(new Province(500000, "重庆市"));
        datastore.save(new Province(510000, "四川省"));
        datastore.save(new Province(520000, "贵州省"));
        datastore.save(new Province(530000, "云南省"));
        datastore.save(new Province(540000, "西藏自治区"));
        datastore.save(new Province(610000, "陕西省"));
        datastore.save(new Province(620000, "甘肃省"));
        datastore.save(new Province(630000, "青海省"));
        datastore.save(new Province(640000, "宁夏回族自治区"));
        datastore.save(new Province(650000, "新疆维吾尔自治区"));
        datastore.save(new Province(710000, "台湾省"));
        datastore.save(new Province(810000, "香港特别行政区"));
        datastore.save(new Province(820000, "澳门特别行政区"));


        datastore.save(new City(110100, "北京(市辖区)", 110000));
        datastore.save(new City(110200, "北京(县)", 110000));
        datastore.save(new City(120100, "天津(市辖区)", 120000));
        datastore.save(new City(120200, "天津(县)", 120000));
        datastore.save(new City(130100, "石家庄市", 130000));
        datastore.save(new City(130200, "唐山市", 130000));
        datastore.save(new City(130300, "秦皇岛市", 130000));
        datastore.save(new City(130400, "邯郸市", 130000));
        datastore.save(new City(130500, "邢台市", 130000));
        datastore.save(new City(130600, "保定市", 130000));
        datastore.save(new City(130700, "张家口市", 130000));
        datastore.save(new City(130800, "承德市", 130000));
        datastore.save(new City(130900, "沧州市", 130000));
        datastore.save(new City(131000, "廊坊市", 130000));
        datastore.save(new City(131100, "衡水市", 130000));
        datastore.save(new City(140100, "太原市", 140000));
        datastore.save(new City(140200, "大同市", 140000));
        datastore.save(new City(140300, "阳泉市", 140000));
        datastore.save(new City(140400, "长治市", 140000));
        datastore.save(new City(140500, "晋城市", 140000));
        datastore.save(new City(140600, "朔州市", 140000));
        datastore.save(new City(140700, "晋中市", 140000));
        datastore.save(new City(140800, "运城市", 140000));
        datastore.save(new City(140900, "忻州市", 140000));
        datastore.save(new City(141000, "临汾市", 140000));
        datastore.save(new City(141100, "吕梁市", 140000));
        datastore.save(new City(150100, "呼和浩特市", 150000));
        datastore.save(new City(150200, "包头市", 150000));
        datastore.save(new City(150300, "乌海市", 150000));
        datastore.save(new City(150400, "赤峰市", 150000));
        datastore.save(new City(150500, "通辽市", 150000));
        datastore.save(new City(150600, "鄂尔多斯市", 150000));
        datastore.save(new City(150700, "呼伦贝尔市", 150000));
        datastore.save(new City(150800, "巴彦淖尔市", 150000));
        datastore.save(new City(150900, "乌兰察布市", 150000));
        datastore.save(new City(152200, "兴安盟", 150000));
        datastore.save(new City(152500, "锡林郭勒盟", 150000));
        datastore.save(new City(152900, "阿拉善盟", 150000));
        datastore.save(new City(210100, "沈阳市", 210000));
        datastore.save(new City(210200, "大连市", 210000));
        datastore.save(new City(210300, "鞍山市", 210000));
        datastore.save(new City(210400, "抚顺市", 210000));
        datastore.save(new City(210500, "本溪市", 210000));
        datastore.save(new City(210600, "丹东市", 210000));
        datastore.save(new City(210700, "锦州市", 210000));
        datastore.save(new City(210800, "营口市", 210000));
        datastore.save(new City(210900, "阜新市", 210000));
        datastore.save(new City(211000, "辽阳市", 210000));
        datastore.save(new City(211100, "盘锦市", 210000));
        datastore.save(new City(211200, "铁岭市", 210000));
        datastore.save(new City(211300, "朝阳市", 210000));
        datastore.save(new City(211400, "葫芦岛市", 210000));
        datastore.save(new City(220100, "长春市", 220000));
        datastore.save(new City(220200, "吉林市", 220000));
        datastore.save(new City(220300, "四平市", 220000));
        datastore.save(new City(220400, "辽源市", 220000));
        datastore.save(new City(220500, "通化市", 220000));
        datastore.save(new City(220600, "白山市", 220000));
        datastore.save(new City(220700, "松原市", 220000));
        datastore.save(new City(220800, "白城市", 220000));
        datastore.save(new City(222400, "延边朝鲜族自治州", 220000));
        datastore.save(new City(230100, "哈尔滨市", 230000));
        datastore.save(new City(230200, "齐齐哈尔市", 230000));
        datastore.save(new City(230300, "鸡西市", 230000));
        datastore.save(new City(230400, "鹤岗市", 230000));
        datastore.save(new City(230500, "双鸭山市", 230000));
        datastore.save(new City(230600, "大庆市", 230000));
        datastore.save(new City(230700, "伊春市", 230000));
        datastore.save(new City(230800, "佳木斯市", 230000));
        datastore.save(new City(230900, "七台河市", 230000));
        datastore.save(new City(231000, "牡丹江市", 230000));
        datastore.save(new City(231100, "黑河市", 230000));
        datastore.save(new City(231200, "绥化市", 230000));
        datastore.save(new City(232700, "大兴安岭地区", 230000));
        datastore.save(new City(310100, "上海(市辖区)", 310000));
        datastore.save(new City(310200, "上海(县)", 310000));
        datastore.save(new City(320100, "南京市", 320000));
        datastore.save(new City(320200, "无锡市", 320000));
        datastore.save(new City(320300, "徐州市", 320000));
        datastore.save(new City(320400, "常州市", 320000));
        datastore.save(new City(320500, "苏州市", 320000));
        datastore.save(new City(320600, "南通市", 320000));
        datastore.save(new City(320700, "连云港市", 320000));
        datastore.save(new City(320800, "淮安市", 320000));
        datastore.save(new City(320900, "盐城市", 320000));
        datastore.save(new City(321000, "扬州市", 320000));
        datastore.save(new City(321100, "镇江市", 320000));
        datastore.save(new City(321200, "泰州市", 320000));
        datastore.save(new City(321300, "宿迁市", 320000));
        datastore.save(new City(330100, "杭州市", 330000));
        datastore.save(new City(330200, "宁波市", 330000));
        datastore.save(new City(330300, "温州市", 330000));
        datastore.save(new City(330400, "嘉兴市", 330000));
        datastore.save(new City(330500, "湖州市", 330000));
        datastore.save(new City(330600, "绍兴市", 330000));
        datastore.save(new City(330700, "金华市", 330000));
        datastore.save(new City(330800, "衢州市", 330000));
        datastore.save(new City(330900, "舟山市", 330000));
        datastore.save(new City(331000, "台州市", 330000));
        datastore.save(new City(331100, "丽水市", 330000));
        datastore.save(new City(340100, "合肥市", 340000));
        datastore.save(new City(340200, "芜湖市", 340000));
        datastore.save(new City(340300, "蚌埠市", 340000));
        datastore.save(new City(340400, "淮南市", 340000));
        datastore.save(new City(340500, "马鞍山市", 340000));
        datastore.save(new City(340600, "淮北市", 340000));
        datastore.save(new City(340700, "铜陵市", 340000));
        datastore.save(new City(340800, "安庆市", 340000));
        datastore.save(new City(341000, "黄山市", 340000));
        datastore.save(new City(341100, "滁州市", 340000));
        datastore.save(new City(341200, "阜阳市", 340000));
        datastore.save(new City(341300, "宿州市", 340000));
        datastore.save(new City(341400, "巢湖市", 340000));
        datastore.save(new City(341500, "六安市", 340000));
        datastore.save(new City(341600, "亳州市", 340000));
        datastore.save(new City(341700, "池州市", 340000));
        datastore.save(new City(341800, "宣城市", 340000));
        datastore.save(new City(350100, "福州市", 350000));
        datastore.save(new City(350200, "厦门市", 350000));
        datastore.save(new City(350300, "莆田市", 350000));
        datastore.save(new City(350400, "三明市", 350000));
        datastore.save(new City(350500, "泉州市", 350000));
        datastore.save(new City(350600, "漳州市", 350000));
        datastore.save(new City(350700, "南平市", 350000));
        datastore.save(new City(350800, "龙岩市", 350000));
        datastore.save(new City(350900, "宁德市", 350000));
        datastore.save(new City(360100, "南昌市", 360000));
        datastore.save(new City(360200, "景德镇市", 360000));
        datastore.save(new City(360300, "萍乡市", 360000));
        datastore.save(new City(360400, "九江市", 360000));
        datastore.save(new City(360500, "新余市", 360000));
        datastore.save(new City(360600, "鹰潭市", 360000));
        datastore.save(new City(360700, "赣州市", 360000));
        datastore.save(new City(360800, "吉安市", 360000));
        datastore.save(new City(360900, "宜春市", 360000));
        datastore.save(new City(361000, "抚州市", 360000));
        datastore.save(new City(361100, "上饶市", 360000));
        datastore.save(new City(370100, "济南市", 370000));
        datastore.save(new City(370200, "青岛市", 370000));
        datastore.save(new City(370300, "淄博市", 370000));
        datastore.save(new City(370400, "枣庄市", 370000));
        datastore.save(new City(370500, "东营市", 370000));
        datastore.save(new City(370600, "烟台市", 370000));
        datastore.save(new City(370700, "潍坊市", 370000));
        datastore.save(new City(370800, "济宁市", 370000));
        datastore.save(new City(370900, "泰安市", 370000));
        datastore.save(new City(371000, "威海市", 370000));
        datastore.save(new City(371100, "日照市", 370000));
        datastore.save(new City(371200, "莱芜市", 370000));
        datastore.save(new City(371300, "临沂市", 370000));
        datastore.save(new City(371400, "德州市", 370000));
        datastore.save(new City(371500, "聊城市", 370000));
        datastore.save(new City(371600, "滨州市", 370000));
        datastore.save(new City(371700, "荷泽市", 370000));
        datastore.save(new City(410100, "郑州市", 410000));
        datastore.save(new City(410200, "开封市", 410000));
        datastore.save(new City(410300, "洛阳市", 410000));
        datastore.save(new City(410400, "平顶山市", 410000));
        datastore.save(new City(410500, "安阳市", 410000));
        datastore.save(new City(410600, "鹤壁市", 410000));
        datastore.save(new City(410700, "新乡市", 410000));
        datastore.save(new City(410800, "焦作市", 410000));
        datastore.save(new City(410900, "濮阳市", 410000));
        datastore.save(new City(411000, "许昌市", 410000));
        datastore.save(new City(411100, "漯河市", 410000));
        datastore.save(new City(411200, "三门峡市", 410000));
        datastore.save(new City(411300, "南阳市", 410000));
        datastore.save(new City(411400, "商丘市", 410000));
        datastore.save(new City(411500, "信阳市", 410000));
        datastore.save(new City(411600, "周口市", 410000));
        datastore.save(new City(411700, "驻马店市", 410000));
        datastore.save(new City(420100, "武汉市", 420000));
        datastore.save(new City(420200, "黄石市", 420000));
        datastore.save(new City(420300, "十堰市", 420000));
        datastore.save(new City(420500, "宜昌市", 420000));
        datastore.save(new City(420600, "襄樊市", 420000));
        datastore.save(new City(420700, "鄂州市", 420000));
        datastore.save(new City(420800, "荆门市", 420000));
        datastore.save(new City(420900, "孝感市", 420000));
        datastore.save(new City(421000, "荆州市", 420000));
        datastore.save(new City(421100, "黄冈市", 420000));
        datastore.save(new City(421200, "咸宁市", 420000));
        datastore.save(new City(421300, "随州市", 420000));
        datastore.save(new City(422800, "恩施土家族苗族自治州", 420000));
        datastore.save(new City(429000, "省直辖行政单位", 420000));
        datastore.save(new City(430100, "长沙市", 430000));
        datastore.save(new City(430200, "株洲市", 430000));
        datastore.save(new City(430300, "湘潭市", 430000));
        datastore.save(new City(430400, "衡阳市", 430000));
        datastore.save(new City(430500, "邵阳市", 430000));
        datastore.save(new City(430600, "岳阳市", 430000));
        datastore.save(new City(430700, "常德市", 430000));
        datastore.save(new City(430800, "张家界市", 430000));
        datastore.save(new City(430900, "益阳市", 430000));
        datastore.save(new City(431000, "郴州市", 430000));
        datastore.save(new City(431100, "永州市", 430000));
        datastore.save(new City(431200, "怀化市", 430000));
        datastore.save(new City(431300, "娄底市", 430000));
        datastore.save(new City(433100, "湘西土家族苗族自治州", 430000));
        datastore.save(new City(440100, "广州市", 440000));
        datastore.save(new City(440200, "韶关市", 440000));
        datastore.save(new City(440300, "深圳市", 440000));
        datastore.save(new City(440400, "珠海市", 440000));
        datastore.save(new City(440500, "汕头市", 440000));
        datastore.save(new City(440600, "佛山市", 440000));
        datastore.save(new City(440700, "江门市", 440000));
        datastore.save(new City(440800, "湛江市", 440000));
        datastore.save(new City(440900, "茂名市", 440000));
        datastore.save(new City(441200, "肇庆市", 440000));
        datastore.save(new City(441300, "惠州市", 440000));
        datastore.save(new City(441400, "梅州市", 440000));
        datastore.save(new City(441500, "汕尾市", 440000));
        datastore.save(new City(441600, "河源市", 440000));
        datastore.save(new City(441700, "阳江市", 440000));
        datastore.save(new City(441800, "清远市", 440000));
        datastore.save(new City(441900, "东莞市", 440000));
        datastore.save(new City(442000, "中山市", 440000));
        datastore.save(new City(445100, "潮州市", 440000));
        datastore.save(new City(445200, "揭阳市", 440000));
        datastore.save(new City(445300, "云浮市", 440000));
        datastore.save(new City(450100, "南宁市", 450000));
        datastore.save(new City(450200, "柳州市", 450000));
        datastore.save(new City(450300, "桂林市", 450000));
        datastore.save(new City(450400, "梧州市", 450000));
        datastore.save(new City(450500, "北海市", 450000));
        datastore.save(new City(450600, "防城港市", 450000));
        datastore.save(new City(450700, "钦州市", 450000));
        datastore.save(new City(450800, "贵港市", 450000));
        datastore.save(new City(450900, "玉林市", 450000));
        datastore.save(new City(451000, "百色市", 450000));
        datastore.save(new City(451100, "贺州市", 450000));
        datastore.save(new City(451200, "河池市", 450000));
        datastore.save(new City(451300, "来宾市", 450000));
        datastore.save(new City(451400, "崇左市", 450000));
        datastore.save(new City(460100, "海口市", 460000));
        datastore.save(new City(460200, "三亚市", 460000));
        datastore.save(new City(469000, "省直辖县级行政单位", 460000));
        datastore.save(new City(500100, "重庆(市辖区)", 500000));
        datastore.save(new City(500200, "重庆(县)", 500000));
        datastore.save(new City(500300, "重庆(市)", 500000));
        datastore.save(new City(510100, "成都市", 510000));
        datastore.save(new City(510300, "自贡市", 510000));
        datastore.save(new City(510400, "攀枝花市", 510000));
        datastore.save(new City(510500, "泸州市", 510000));
        datastore.save(new City(510600, "德阳市", 510000));
        datastore.save(new City(510700, "绵阳市", 510000));
        datastore.save(new City(510800, "广元市", 510000));
        datastore.save(new City(510900, "遂宁市", 510000));
        datastore.save(new City(511000, "内江市", 510000));
        datastore.save(new City(511100, "乐山市", 510000));
        datastore.save(new City(511300, "南充市", 510000));
        datastore.save(new City(511400, "眉山市", 510000));
        datastore.save(new City(511500, "宜宾市", 510000));
        datastore.save(new City(511600, "广安市", 510000));
        datastore.save(new City(511700, "达州市", 510000));
        datastore.save(new City(511800, "雅安市", 510000));
        datastore.save(new City(511900, "巴中市", 510000));
        datastore.save(new City(512000, "资阳市", 510000));
        datastore.save(new City(513200, "阿坝藏族羌族自治州", 510000));
        datastore.save(new City(513300, "甘孜藏族自治州", 510000));
        datastore.save(new City(513400, "凉山彝族自治州", 510000));
        datastore.save(new City(520100, "贵阳市", 520000));
        datastore.save(new City(520200, "六盘水市", 520000));
        datastore.save(new City(520300, "遵义市", 520000));
        datastore.save(new City(520400, "安顺市", 520000));
        datastore.save(new City(522200, "铜仁地区", 520000));
        datastore.save(new City(522300, "黔西南布依族苗族自治州", 520000));
        datastore.save(new City(522400, "毕节地区", 520000));
        datastore.save(new City(522600, "黔东南苗族侗族自治州", 520000));
        datastore.save(new City(522700, "黔南布依族苗族自治州", 520000));
        datastore.save(new City(530100, "昆明市", 530000));
        datastore.save(new City(530300, "曲靖市", 530000));
        datastore.save(new City(530400, "玉溪市", 530000));
        datastore.save(new City(530500, "保山市", 530000));
        datastore.save(new City(530600, "昭通市", 530000));
        datastore.save(new City(530700, "丽江市", 530000));
        datastore.save(new City(530800, "思茅市", 530000));
        datastore.save(new City(530900, "临沧市", 530000));
        datastore.save(new City(532300, "楚雄彝族自治州", 530000));
        datastore.save(new City(532500, "红河哈尼族彝族自治州", 530000));
        datastore.save(new City(532600, "文山壮族苗族自治州", 530000));
        datastore.save(new City(532800, "西双版纳傣族自治州", 530000));
        datastore.save(new City(532900, "大理白族自治州", 530000));
        datastore.save(new City(533100, "德宏傣族景颇族自治州", 530000));
        datastore.save(new City(533300, "怒江傈僳族自治州", 530000));
        datastore.save(new City(533400, "迪庆藏族自治州", 530000));
        datastore.save(new City(540100, "拉萨市", 540000));
        datastore.save(new City(542100, "昌都地区", 540000));
        datastore.save(new City(542200, "山南地区", 540000));
        datastore.save(new City(542300, "日喀则地区", 540000));
        datastore.save(new City(542400, "那曲地区", 540000));
        datastore.save(new City(542500, "阿里地区", 540000));
        datastore.save(new City(542600, "林芝地区", 540000));
        datastore.save(new City(610100, "西安市", 610000));
        datastore.save(new City(610200, "铜川市", 610000));
        datastore.save(new City(610300, "宝鸡市", 610000));
        datastore.save(new City(610400, "咸阳市", 610000));
        datastore.save(new City(610500, "渭南市", 610000));
        datastore.save(new City(610600, "延安市", 610000));
        datastore.save(new City(610700, "汉中市", 610000));
        datastore.save(new City(610800, "榆林市", 610000));
        datastore.save(new City(610900, "安康市", 610000));
        datastore.save(new City(611000, "商洛市", 610000));
        datastore.save(new City(620100, "兰州市", 620000));
        datastore.save(new City(620200, "嘉峪关市", 620000));
        datastore.save(new City(620300, "金昌市", 620000));
        datastore.save(new City(620400, "白银市", 620000));
        datastore.save(new City(620500, "天水市", 620000));
        datastore.save(new City(620600, "武威市", 620000));
        datastore.save(new City(620700, "张掖市", 620000));
        datastore.save(new City(620800, "平凉市", 620000));
        datastore.save(new City(620900, "酒泉市", 620000));
        datastore.save(new City(621000, "庆阳市", 620000));
        datastore.save(new City(621100, "定西市", 620000));
        datastore.save(new City(621200, "陇南市", 620000));
        datastore.save(new City(622900, "临夏回族自治州", 620000));
        datastore.save(new City(623000, "甘南藏族自治州", 620000));
        datastore.save(new City(630100, "西宁市", 630000));
        datastore.save(new City(632100, "海东地区", 630000));
        datastore.save(new City(632200, "海北藏族自治州", 630000));
        datastore.save(new City(632300, "黄南藏族自治州", 630000));
        datastore.save(new City(632500, "海南藏族自治州", 630000));
        datastore.save(new City(632600, "果洛藏族自治州", 630000));
        datastore.save(new City(632700, "玉树藏族自治州", 630000));
        datastore.save(new City(632800, "海西蒙古族藏族自治州", 630000));
        datastore.save(new City(640100, "银川市", 640000));
        datastore.save(new City(640200, "石嘴山市", 640000));
        datastore.save(new City(640300, "吴忠市", 640000));
        datastore.save(new City(640400, "固原市", 640000));
        datastore.save(new City(640500, "中卫市", 640000));
        datastore.save(new City(650100, "乌鲁木齐市", 650000));
        datastore.save(new City(650200, "克拉玛依市", 650000));
        datastore.save(new City(652100, "吐鲁番地区", 650000));
        datastore.save(new City(652200, "哈密地区", 650000));
        datastore.save(new City(652300, "昌吉回族自治州", 650000));
        datastore.save(new City(652700, "博尔塔拉蒙古自治州", 650000));
        datastore.save(new City(652800, "巴音郭楞蒙古自治州", 650000));
        datastore.save(new City(652900, "阿克苏地区", 650000));
        datastore.save(new City(653000, "克孜勒苏柯尔克孜自治州", 650000));
        datastore.save(new City(653100, "喀什地区", 650000));
        datastore.save(new City(653200, "和田地区", 650000));
        datastore.save(new City(654000, "伊犁哈萨克自治州", 650000));
        datastore.save(new City(654200, "塔城地区", 650000));
        datastore.save(new City(654300, "阿勒泰地区", 650000));
        datastore.save(new City(659000, "省直辖行政单位", 650000));

        return "Database successfully initialized.";
    }

}
