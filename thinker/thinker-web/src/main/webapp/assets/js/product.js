/**
 * Created by wan.peng on 2016/10/13.
 */
function findProduct(){
    var productTypeId  = $("#productType").val();
    $.ajax({
        type: 'POST',
        url: '/consume/cash/findProduct',
        dataType:'json',
        data:{'productTypeId':productTypeId},
        success: function(products){
            for(var i=0;i<products.size();i++){
                alert(products.get(i).productName);
            }
        }
    });
}