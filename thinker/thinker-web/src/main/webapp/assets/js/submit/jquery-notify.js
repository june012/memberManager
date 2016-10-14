/**
 * jquery pnotify
 * @author hongzheng.liu
 */


function closeBtn(obj){
    $(obj).parent().removeClass('in').hide().addClass('hide')
}

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
            '<button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="closeBtn(this)"><span aria-hidden="true">×</span></button>' +
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