var w = {
    JQUERY_ID_FLAG:'#',
    DAY_MILLISECONDS:1000*3600*24,
    DATE_FORMAT:'yyyy-MM-dd',
    FADE_SPEED:'slow',
    POS_LEFT:'left',
    POS_RIGHT:'right',
    POS_CENTER:'center',
    POS_TOP:'top',
    POS_BOTTOM:'bottom',
    SPACE:' ',
    COMMA:',',
    NEW_FLAG_CLASS:'new-flag',
    EDIT_FLAG_CLASS:'edit-flag',
    DEL_FLAG_CLASS:'del-flag',
    OK_FLAG_CLASS:'ok-flag',
    CANCEL_FLAG_CLASS:'cancel-flag',
    setCurrentMenuItem:function(menuId,idx) {
        var item = $($(this.JQUERY_ID_FLAG + menuId).children()[idx]);
        item.addClass("active").css('cursor','default');
        item.find("i").addClass("icon-white");
        item.find('a').css('cursor','default');
    },
    setFocus:function(formId) {
        $($(this.JQUERY_ID_FLAG + formId+ " :input[type!='hidden']").get(0)).focus().select();
    },
    updateLeftBarHeight:function() {
        $('.left-bar').height($('.content').height()+20);
    },
    doAction:function(formId) {
        this._showProgress(formId,true);
    },
    finishAction:function(formId) {
        this._showProgress(formId,false);
    },
    _showProgress:function(formId,s) {
        var ids = formId.split(","),divs,self=this;
        $.each(ids,function(i,v){
            divs = $(self.JQUERY_ID_FLAG + v+ " .form-actions div");

            if (s) {
                $(divs.get(0)).hide();
                $(divs.get(1)).show();
            } else {
                $(divs.get(1)).hide();
                $(divs.get(0)).show();
            }
        });
    },

    scrollToAnchor:function(anchor) {
        var top = $(this.JQUERY_ID_FLAG + anchor).offset().top;
        $('body').animate({scrollTop:top}, 500);
    },
    isSameDay:function(start,end) {
        return (end.getTime() - start.getTime())/this.DAY_MILLISECONDS <=1;
    },
    showEditor:function (target, delBtn, editor,isNew) {
        var docWidth = $(document).width(), hideCls = 'hide';
        var isRight = $(target).offset().left > docWidth / 2;

        var $delBtn = $(this.JQUERY_ID_FLAG + delBtn);
        if (isNew) {
            $delBtn.addClass(hideCls);
        } else {
            $delBtn.removeClass(hideCls);
        }

        this.positionEditor(target,editor,isRight?this.POS_RIGHT:this.POS_LEFT);
    },
    hideEditor:function (editor) {
        this.fadeOut(editor);
    },
    fadeIn:function(id) {
        $(this.JQUERY_ID_FLAG + id).fadeIn(this.FADE_SPEED);
    },
    fadeOut:function(id) {
        $(this.JQUERY_ID_FLAG + id).fadeOut(this.FADE_SPEED);
    },
    positionEditor:function(target,editor,pos) {
        var myPos,atPos,offset,isTop,isRight,isH,isV,className = "triangle-", triangle = this.JQUERY_ID_FLAG+"triangle";

        $(triangle).attr('class', className+pos);

        if (pos == this.POS_LEFT || pos == this.POS_RIGHT) {
            isRight = (pos == this.POS_RIGHT);
            myPos = isRight ? this.POS_RIGHT:this.POS_LEFT + this.SPACE+this.POS_CENTER;
            atPos = isRight ? this.POS_LEFT:this.POS_RIGHT + this.SPACE+this.POS_CENTER;
            offset = (isRight ? "-" : "") + "10 0";
            isV=false;
            isH=true;
        }
        if (pos == this.POS_TOP ) {
            isTop = (pos == this.POS_TOP);
            myPos = this.POS_CENTER + this.SPACE + (isTop ? this.POS_TOP:this.POS_BOTTOM);
            atPos = this.POS_CENTER + this.SPACE + (isTop ? this.POS_BOTTOM:this.POS_TOP);
            offset = "0 10";
            isH =false;
            isV=true;
        }

        $(this.JQUERY_ID_FLAG + editor + ', ' + triangle).fadeIn().position({
            my:myPos,
            at:atPos,
            of:$(target),
            offset:offset,
            collision:"fit fit"
        });

        if (isH) {
            $(triangle).css(this.POS_LEFT, "");
        }
        if (isV) {
            $(triangle).css(this.POS_TOP, "");
        }
    }
};