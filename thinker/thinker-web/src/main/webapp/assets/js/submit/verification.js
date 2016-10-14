/**
 * Created by wan.peng on 2016/10/14.
 */
function verification(){
    $.ajax({
        type: "POST",
        url:'/member/canBeUsedAccount',
        data:{'memberid':$('#memberid').val(),
            'money':$('#money').val()
        },
        success:function(data){
            if(data.status){
                submitForm();
            }else{
                $.__notify(data.message);
            }
        }
    });
}
function submitForm(){
    $.ajax({
        cache: true,
        type: "POST",
        url:$('#form1').attr("action"),
        data:$('#form1').serialize(),// 你的formid
        async: false,
        error: function(XmlHttpRequest) {
            var jsonstr =  eval("(" + XmlHttpRequest.responseText + ")");
            $.__notify(jsonstr.message);
        }
    });
}