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

    win.asb.RegisterView = (function(){
        var defParams = {
            form : {
                formEle : 'form.frm-regist',
                userIdEle : 'input[name=userid]',
                userNameEle : 'input[name=username]',
                userEmailEle : 'input[name=useremail]',
                userPassEle : 'input[name=password]',
                userPassConfirmEle : 'input[name=passwordConfirm]',
                submitBtn : 'button.btn-register'
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
                // console.log(this.submitBtn);
            },
            assigneElements : function(){
                this.$form = this.container.find(this.opts.form.formEle);
                this.$userIdEle = this.$form.find(this.opts.form.userIdEle);
                this.$userNameEle = this.$form.find(this.opts.form.userNameEle);
                this.$userEmailEle = this.$form.find(this.opts.form.userEmailEle);
                this.$userPassEle = this.$form.find(this.opts.form.userPassEle);
                this.$userPassConfirmEle = this.$form.find(this.opts.form.userPassConfirmEle);
                this.$submitBtn = this.$form.find(this.opts.form.submitBtn);
            },
            bindEvents : function(){
                this.$submitBtn.on('click', $.proxy(this.onClickSubmit, this));

            },
            validPassword : function(){
                return (this.$userPassEle.val() !== this.$userPassConfirmEle.val())? true:false;
            },
            onClickSubmit : function(e){
                e.preventDefault();

                //Password Validation Check
                if(this.validPassword()) return alert('Password Confirmation Failed.\nTry Again.');

                var _this = this;

                var inputUserData = {
                    "userid": _this.$userIdEle.val(),
                    "name": _this.$userNameEle.val(),
                    "email": _this.$userEmailEle.val(),
                    "password": _this.$userPassEle.val()
                };

                $.ajax({
                    method: "POST",
                    url: "register/userCreate",
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    data: JSON.stringify(inputUserData),
                    success: function (result) {

                        if(result=="hata"){
                            $('#lgnInfo').text('Hatalı giriş.');
                        }else{

                            console.log(result);
                            // window.location.replace("/dashboard?id="+result);
                        }

                    }
                });
            }
        };
    })();

    $(function() {
        var body = $('body');
        win.asb.RegisterView.init(body, {});

    });
})(window, jQuery);