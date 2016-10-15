/**
 * Created by wan.peng on 2016/10/13.
 */
function findProduct() {
    var productTypeId = $("#productType").val();
    alert( $("#product").html());
    var str="";
    $.ajax({
        type: 'POST',
        url: '/consume/cash/findProduct',
        dataType: 'json',
        data: {'productTypeId': productTypeId},
        success: function (products) {
            $(products).each(function () {
                var product = eval(this);
                var id = product.id;
                var price = product.price;
                var productName = product.productName;
                str += "<option>"+id+"</option>";
                //$('<option value="' + id + '" product_price="'+price+'">' + productName + '</option>')
            });
            $("#product").html(str).show();
        }
    });
}
function findPrice(){
    var productId = $("#product").val();
    $.ajax({
        type: 'POST',
        url: '/consume/cash/findPrice',
        dataType: 'json',
        data: {'productId': productId},
        success: function (price) {
            $("#productPrice").text(price);
        }
    });
}