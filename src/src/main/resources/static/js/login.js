/**
 * Created by andrew on 2017. 1. 6..
 */
;(function(win, $) {
    'use strict';

    if('undefined' === typeof win.asb) {
        /**
         * @global
         */
        win.asb = {};
    }

    var STATIC = {};

    win.asb.LoginView = (function(){
        var defParams = {
            form : {
                formEle : 'form.frm-login',
                userIdEle : 'input[name=userid]',
                userPassEle : 'input[name=password]',
                submitBtn : '.btn-lg'
            }

        };
        return {
            def : function(org, src) {
                for (var prop in src) {
                    if (!hasOwnProperty.call(src, prop)) continue;
                    if ('object' === $.type(org[prop])) {
                        org[prop] = (this.isArray(org[prop]) ? src[prop].slice(0) : this.def(org[prop], src[prop]));
                    } else {
                        org[prop] = src[prop];
                    }
                }
                return org;
            },
            init : function(container, args){
                if (!(this.container = container).size()) return;

                this.opts = this.def(defParams, (args || {}));
                this.assigneElements();
                this.bindEvents();
            },
            assigneElements : function(){
                this.$form = this.container.find(this.opts.form.formEle);
                this.$userIdEle = this.$form.find(this.opts.form.userIdEle);
                this.$userPassEle = this.$form.find(this.opts.form.userPassEle);
                this.$submitBtn = this.$form.find(this.opts.form.submitBtn);
                console.log(this.$submitBtn)
            },
            bindEvents : function(){
                this.$submitBtn.on('click', $.proxy(this.onClickSubmit, this));

            },
            onClickSubmit : function(e){
                e.preventDefault();

                var _this = this;

                var inputData = {
                    "userid": _this.$userIdEle.val(),
                    "password": _this.$userPassEle.val()
                };

                console.log('11');

                $.ajax({
                    method: "POST",
                    url: "login/userControl",
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    data: JSON.stringify(inputData),
                    success: function (result) {

                        if(result=="hata"){
                            $('#lgnInfo').text('Hatalı giriş.');
                        }else{

                            console.log(result);
                            window.location.replace("/dashboard?id="+result);
                        }

                    }
                });
            }
        };
    })();

    $(function() {
        var body = $('body');
        win.asb.LoginView.init(body, {});
alert(1);
    });
})(window, jQuery);