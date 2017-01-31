/**
 * Created by BaiyangTX on 2017/1/31.
 */

var log = function (text) {
    console.log(text)
}


var radiotab_handle = function (elem) {
    var target = $(elem).attr("data-target") ;
    var checked = $(elem).attr("checked") ;
    log(elem + " checked=" + checked );
    if( checked){
        $('#' + target).show() ;
    }else if ( target != null ){
        $("#" + target).hide() ;
    }
}

$(":radio[data-role='radiotab']").each(function (e) {
    radiotab_handle(this);
    $(this).change(function (e) {
        log("changed");
        log(this);
        radiotab_handle(this);
    });
});