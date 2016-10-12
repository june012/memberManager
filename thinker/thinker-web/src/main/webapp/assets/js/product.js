/**
 * Created by wan.peng on 2016/10/13.
 */
function findProduct() {
    var productTypeId = $("#productType").val();
    $.ajax({
        type: 'POST',
        url: '/consume/cash/findProduct',
        dataType: 'json',
        data: {'productTypeId': productTypeId},
        success: function (products) {
            $(products).each(function () {
                var product = eval(this);
                var id = product['id'];
                $("#product").append('<option>"+id+"</option>');
                //$("#product").append($('<option value="' + product['id'] + '" product_price="'+product['price']+'">' + product['productName'] + '</option>'));
            });
        }
    });
}