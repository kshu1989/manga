(function () {
    if (!isMobile) {
        var hours = 24;
        var date = new Date();
        date.setTime(date.getTime() + (hours * 60 * 60 * 1000));
        var visit = imh.utils.cookie('visited');
        if (visit == null || visit == undefined) {
            visit = 1;
            document.cookie = 'visited=1;expires=' + date.toGMTString() + ';path=/;domain=imanhua.com';
        }
        var refbd = document.referrer && /(baidu\.com)/gi.test(document.referrer.toLowerCase());
        if (!refbd) {
            try {
                if (visit == 50) {
                    visit++;
                    //document.write('<script src="http://js.333wan.com/page/?s=87"></script>');
                }
            } catch (e) { }
            finally {
                visit++;
                document.cookie = 'visited=' + String(visit) + ';expires=' + date.toGMTString() + ';path=/;domain=imanhua.com';
            }
        }
    }
})();

document.write('<script src="http://v7.cnzz.com/stat.php?id=565400&web_id=565400" language="JavaScript"></script>');
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F6dbc350428237b030c7de3a18ddadfc4' type='text/javascript'%3E%3C/script%3E"));