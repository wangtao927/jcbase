;
// reg -1
$("body").on("tap", ".begin_register", function (event) {
    event.preventDefault();
    var register_form = $("#update_info");
    var data = register_form.serialize();
    var url = register_form.attr('action');

    $.authPost(url, data, function (resp) {

        if(resp.error === 0){
            location.href = resp.data.next_url;
        } else if (resp.error == -2){
            location.href = resp.data.next_url;
        } else {
            scscms_alert(resp.error_reason, "warn", "", 1);
            return;
        }
    });
});


// reg - 2
$("body").on("touchend", "#ring", function (event) {
    event.preventDefault();
    self = $(this);
    if ('disabled' == self.attr('disabled')) {
        return;
    }
    self.attr('disabled', true);
    var uids = "";
    $(".pk_list").each(function () {
        uids = uids + $(this).attr('uid') + ",";
    });
    uids = uids.substring(0, uids.length - 1);
    var url = "../../../home.php-m=Home&c=Home&a=ajaxRing.js"/*tpa=http://v3.9938527.cn/wx/home.php?m=Home&c=Home&a=ajaxRing*/;
    var data = {'uids': uids};
    $.authPost(url, data, function () {
        location.href = DOMAIN;
    });
});

// 随机换
$("body").on("touchend", "#change", function (event) {
    event.preventDefault();
    jQuery("#ajax_list").empty();
    var url = "../../../home.php-m=Home&c=Home&a=ajaxChangeRobot.js"/*tpa=http://v3.9938527.cn/wx/home.php?m=Home&c=Home&a=ajaxChangeRobot*/;
    var data = null;
    $.authPost(url, data, function (resp) {

        jQuery("#ajax_list").html(resp.data.html);
    });
});
