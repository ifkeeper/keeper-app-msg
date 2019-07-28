<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册验证</title>
</head>
<body>
<style type="text/css">
    body {
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        color: #364149;
        background: #fafafa;
    }

    span {
        display: block;
    }

    .content {
        text-indent: 2em;
        color: #363636;
    }

    .line {
        height: 1px;
        margin: 7px 0;
        overflow: hidden;
        background: rgba(0, 0, 0, .07);
    }

    .footer {
        color: #808080;
        font-size: smaller;
    }

    /**
     * -webkit-line-clamp: 用来限制在一个块元素显示的文本的行数,需要组合其他的WebKit属性.
     * display: -webkit-box: 必须结合的属性,将对象作为弹性伸缩盒子模型显示.
     * -webkit-box-orient: 必须结合的属性,设置或检索伸缩盒对象的子元素的排列方式.
     */
    a {
        color: #2400f8;
        text-decoration: underline;
        overflow: hidden;
        -webkit-line-clamp: 3;
        /*display: -webkit-box;*/
        -webkit-box-orient: vertical;
    }

    a:hover{
        color: #26CB7C;
    }
</style>
<div>
    <div>
        <span style="font-size: larger;font-weight: bolder">侣行网：</span><br/>
        <div class="content">
            <span>亲爱的 ${nickName}：</span><br/>
            <span>请点击下面的链接激活账号完成注册。（请在24小时内完成）</span>
            <span>
                <!--<a href="http://www.baidu.com">http://www.baidu.com</a>-->
                <a style="display: -webkit-box; width: 90%;" href="https://www.baidu.com/link?url=AQEdjIQt0FQPD6VCDR9VbVQnyXxNSOFXT0XeUm-UWIpxyins25VRkwDH7kecKtdM_JcP0BNsjJeoSyQn5DGzHFcEAXGQwDxVtN1iYRgNmfZpLqNBW_D758CUWArZaSvIjTbIsT8__oofZcw2m0YlLdJTciYAMjGe2yDbv_yp5hCYgevIKJqeBNLMacK1-rSC5s2NeqMBpjkt1-Lkvs-HFEqrUooN3N-OjWdNc28-sg_4NCCkNSCNR54ENJ40GLywrlT9uhGI_jTpWY9lCY8WobUjGHJZefCVlh5MNqL8dsyphfheChiXuyloV7D8jIEwvvLVXffdzTcSGggihzYZ5Ul0S4V0oWIq0xrFOfxX9tdMQ1R2Zq7dhSlFDU7a609vinuNeLDUGK9H73srIs0NXq4w3S5NYaLh6K87NQ7iD0UKEA_ycueAVKn6g6wDSEOvph3919wcpJ8Gh7VRZm06KCF8xlvokMW6u8JPo-o3J_-f4t2SP6GnSFydqToI52S_NL7udu9gd5mPj6_-qPKthPI7MNPFahzUWdq3tfHJbzwz4UNUPEnx0-WUtCw20E84&wd=&eqid=d0404c020000b5ed000000065c6eb88a">https://www.baidu.com/link?url=AQEdjIQt0FQPD6VCDR9VbVQnyXxNSOFXT0XeUm-UWIpxyins25VRkwDH7kecKtdM_JcP0BNsjJeoSyQn5DGzHFcEAXGQwDxVtN1iYRgNmfZpLqNBW_D758CUWArZaSvIjTbIsT8__oofZcw2m0YlLdJTciYAMjGe2yDbv_yp5hCYgevIKJqeBNLMacK1-rSC5s2NeqMBpjkt1-Lkvs-HFEqrUooN3N-OjWdNc28-sg_4NCCkNSCNR54ENJ40GLywrlT9uhGI_jTpWY9lCY8WobUjGHJZefCVlh5MNqL8dsyphfheChiXuyloV7D8jIEwvvLVXffdzTcSGggihzYZ5Ul0S4V0oWIq0xrFOfxX9tdMQ1R2Zq7dhSlFDU7a609vinuNeLDUGK9H73srIs0NXq4w3S5NYaLh6K87NQ7iD0UKEA_ycueAVKn6g6wDSEOvph3919wcpJ8Gh7VRZm06KCF8xlvokMW6u8JPo-o3J_-f4t2SP6GnSFydqToI52S_NL7udu9gd5mPj6_-qPKthPI7MNPFahzUWdq3tfHJbzwz4UNUPEnx0-WUtCw20E84&wd=&eqid=d0404c020000b5ed000000065c6eb88a</a>
            </span><br>
            <span>如果链接无法点击，请直接拷贝以上链接到浏览器地址栏中访问。</span>
        </div>
    </div>
    <div class="line"></div>
    <div class="footer">
        <span>此信息由<a href="">侣行网</a>系统发出，系统不接收回信，请勿直接回复</span>
        <span>如有任何疑问请 <a href="">联系我们</a>。 </span>
    </div>
</div>
</body>
</html>