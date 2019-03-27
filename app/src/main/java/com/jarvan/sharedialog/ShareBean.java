package com.jarvan.sharedialog;


/**
 * Created by mustang on 19/3/27.
 * 分享实体类
 */
public class ShareBean {
    //图片字符串数组
    private String[] imageArray;
    //分享的链接
    private String shareUrl;
    //分享的内容
    private String shareContent;
    //分享的标题
    private String shareTitle;
    //小程序ID
    private String WXMiniProgramID;
    //小程序页面路径
    private String WXMiniProgramPath;
    //小程序版本类型
    private String WXMiniProgramType;
    //是否显示分享方式选择菜单
    private boolean showMenu;
    /* 分享方式。可选值见分享方式字典。当showMenu为true时，
      该参数为分享方式选择菜单中的可选分享方式列表（有序），如果数组中无有效的分享方式，
      则使用默认的分享方式列表；当showMenu为false时，该参数为无UI分享的分享方式，
     即数组中应只包含1个分享方式，若超过1个，则取第1个*/
    private String[] shareWay;

    /*分享内容类型，Text纯文字，Link网页，Image图片，WXMiniProgram微信小程序。不传时默认为Link网页分享。*/
          /*    取值          | 意义         |
            | ------------- | ------------ |
            | WeChatFriends | 微信好友     |
            | WeChatMoments | 微信朋友圈   |
            | AlipayFriends | 支付宝好友   |
            | AlipayMoments | 支付宝朋友圈 |
            | Clipboard     | 复制内容     |*/
    private String contentType;

    public String[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getWXMiniProgramID() {
        return WXMiniProgramID;
    }

    public void setWXMiniProgramID(String WXMiniProgramID) {
        this.WXMiniProgramID = WXMiniProgramID;
    }

    public String getWXMiniProgramPath() {
        return WXMiniProgramPath;
    }

    public void setWXMiniProgramPath(String WXMiniProgramPath) {
        this.WXMiniProgramPath = WXMiniProgramPath;
    }

    public String getWXMiniProgramType() {
        return WXMiniProgramType;
    }

    public void setWXMiniProgramType(String WXMiniProgramType) {
        this.WXMiniProgramType = WXMiniProgramType;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public String[] getShareWay() {
        return shareWay;
    }

    public void setShareWay(String[] shareWay) {
        this.shareWay = shareWay;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


}
