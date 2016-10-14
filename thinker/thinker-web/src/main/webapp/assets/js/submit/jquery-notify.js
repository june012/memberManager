/**
 * jquery pnotify
 * @author hongzheng.liu
 */

(function($) {
  'use strict';

  $.notify = function(options) {
    options = $.extend({
      placement: "top", // [top, top-left, top-right, bottom, bottom-left, bottom-right]
      type: "warning",
      title: '',
      text: "Alert message!",
      delay: 0,
      onClose: null
    }, options);

    var $alert = $('<div class="alert alert-' + options.type + ' alert-dismissible fade" data-delay="' + options.delay +
      '" data-placement="' + options.placement + '" role="alert">' +
      '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>' +
      '<strong>' + options.title + '</strong><br/>' + options.text + '</div>').notify();

    if (options.onClose) {
      $alert.on("close.bs.alert",  $.proxy(options.onClose, this));
    }
  };

  $.notify.defaults = {
    delay: 3000,
    placement: "top"
  };

  $.fn.notify = function() {
    return this.each(function() {
      var $this = $(this),
        $placement = $this.data('placement') || $.notify.defaults.placement,
        $box = $('body').children('.notify-box.' + $placement),
        delay = $this.data('delay') || $.notify.defaults.delay;

      $box.length || ($box = $('<div class="notify-box ' + $placement + '"></div>').appendTo('body'));

      $this.appendTo($box).removeClass('hide').show().addClass('in');

      if (delay > 0) {
        $this.on('mouseenter', function(e) {
          $this.data('timeout') && clearTimeout($this.data('timeout'));
          $this.removeData('timeout');
        }).on('mouseleave', function(e) {
          $this.data('timeout', setTimeout(function() {
            $this.alert('close');
          }, delay));
        }).data('timeout', setTimeout(function() {
          $this.alert('close');
        }, delay));
      }
    });
  };

})(jQuery);

$(function(){
    var hash = location.hash;
    if(hash){

    }
    $('body').delegate('[checkList]','change',function(){
        var isAll = $(this).hasClass('check-all-box');
        var checked = $(this).prop('checked');
        var checkList = $(this).attr('checkList');
        if(isAll){
           $('[checkList='+checkList+']').prop('checked',checked);
        }else{
            $('.check-all-box[checkList='+checkList+']').prop('checked',!$('[checkList='+checkList+']').not('.check-all-box').not(':disabled').not(':checked').length);
        }
    });
    $.__notify = function(text){
        $.notify && $.notify({
            title: '提示',
            text: text
        });
    };
    $.__waiting = function(type){
        $('.waiting-for-div')[type || 'show']();
    };
    $.ajaxSetup({
        complete:function(){
            $.__waiting('hide');
        }
    });
});