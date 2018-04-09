package com.gongyunhao.nowmeeting.bean;
//    ┏┓　   ┏┓  
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//

/**
 * Creste by GongYunHao on 2018/4/8
 */
public class Voteitem {

    private String votename;
    private String organizername;
    private String date;

    public String getVotename() {
        return votename;
    }

    public void setVotename(String votename) {
        this.votename = votename;
    }

    public String getOrganizername() {
        return organizername;
    }

    public void setOrganizername(String organizername) {
        this.organizername = organizername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Voteitem(String votename, String date, String organizername) {
        this.votename = votename;
        this.organizername = organizername;
        this.date = date;
    }
}
