// The Template Loader. Used to asynchronously load templates located in separate .html files
window.templateLoader = {

    load: function(views, callback) {

        var deferreds = [];

        $.each(views, function(index, view) {
            if (window[view]) {
                deferreds.push($.get(window.rootUri+'/worklog/templates/' + view + '.jsp', function(data) {
                    window[view].prototype.template = _.template(data);
                }, 'html'));
            } else {
                alert(view + " not found");
            }
        });

        $.when.apply(null, deferreds).done(callback);
    }

};

window.CommonUtils ={
    compareDate:function(d1,d2) {
        if (!d1 || !d2) return -1;
        return new Date(d1).clearTime().compareTo(new Date(d2).clearTime()) != 0;
    },
    substractDate:function(d1,d2) {
        return (Date.parse(d1) - Date.parse(d2))/1000/3600;
    },
    equalDate:function(d1,d2) {
        return this.compareDate(d1,d2) ==0;
    },
    formatDateWithTimezone:function(d) {
        return this.updateTimezone(d).toISOString();
    },
    updateTimezone:function(d) {
        if (d instanceof String) {
            d = Date.parse(d);
        }
        return new Date(d).setTimezone('GMT').setTimezoneOffset('+0800');
    },
    showInfoMsg:function(msg) {
        return $(".msg-box").removeClass("msg-error-box").find('span').text(msg).parent().fadeIn();
    },
    showErrorMsg:function(msg) {
        this.showInfoMsg(msg).addClass("msg-error-box");
    },
    postForm:function(url,data,success,beforeSend,complete,dataType){
        return $.ajax({
            type: 'POST',
            url: url,
            data: data,
            success: success,
            dataType: dataType?dataType:'json',
            beforeSend:beforeSend,
            complete:complete
        });
    },
    positionPopover:function(referComp,popOver) {
        var docWidth = $(document).width(),isRight = $(referComp).offset().left > docWidth / 2,$popOver=$(popOver);

        if (isRight) {
            $popOver.removeClass('right');
            $popOver.addClass('left');
        } else {
            $popOver.removeClass('left');
            $popOver.addClass('right');
        }

        var pr="right center",pl="left center";
        $popOver.show().position({
            of:$(referComp),
            my:isRight?pr:pl,
            at:isRight?pl:pr,
            offset:'10 0',
            collision:'fit fit'
        });

        this.formFocus(popOver);

    },
    formFocus:function(formId) {
        $('.focused',formId).focus();
    },
    getTagInfo:function(tagId,needGetId) {
        var rtn='',rtnId='',self=this;
        $.each($(tagId).tagit('tags'),function(i,d){
            if (i>0) {
                rtn+=constants.COMMA_CHAR;
                rtnId+=constants.COMMA_CHAR;
            }
            rtn += d.label;
            if (needGetId && needGetId==true) rtnId += self.getTagId(d.label);
        });

        return {str:rtn,ids:rtnId};
    },
    getTagId:function(label) {
        var rtn = _.find(window.tags,function(d){return d.label == label;});
        return rtn?rtn.id:'n';
    },
    reLogin:function(rtnValue) {
        if (rtnValue == 'noLogin') {
            window.location.href = window.location.origin+'/login'+window.location.pathname;
        } else {
            this.showErrorMsg(rtnValue);
        }
    },
    ellipsisStr:function(str) {
        if (str && str.length>6) {
            return str.substring(0,6)+"...";
        } else {
            return str;
        }
    },
    formatUtcDate:function(str) {
        return str.replace('T',' ').substring(0,str.indexOf('.'));
    },
    scrollTo:function($container,$item) {
        $container.scrollTop(
            $item.offset().top - $container.offset().top + $container.scrollTop()
        );
    }
}

window.teamlogUtils = {
    avatarProcess:function(obj) {
        if (!obj.avatar || obj.avatar=='') {
            obj.avatar = "/res/imgs/default-avatar.png";
        } else {
            obj.avatar = "/avatar/avatar/"+obj.avatar;
        }
        obj.avatar = window.rootUri+obj.avatar;
    },

    dateProcess:function(dateValue) {
        return CommonUtils.formatDateWithTimezone(Date.parse(dateValue));
    },

    dateAndAvatarPrepare:function(obj) {
        this.avatarProcess(obj);
        if (obj.createTime) obj.createTime = this.dateProcess(obj.createTime);
    },

    getBrowseWorkLogUsers:function() {
        var p='';
        $(".f-ul .active").each(function(i,d){
            if (i>0) p+=constants.COMMA_CHAR;
            p+= $(d).find('input').attr('id').replace('c','');
        });
        p = p.replace('0,','');

        if (p=='') p = $('#userId').html();
        return p;
    },
    getBrowseWorklogPeriod:function() {
        return $('#reportrange span').html();
    },
    getBrowseWorklogUrl:function() {
        return 'viewLog/'+this.getBrowseWorklogPeriod()+"/"+this.getBrowseWorkLogUsers();
    },
    getCurrentUserId:function() {
        return $('#userId').text();
    },
    hideReport:function() {
        app.navigate(teamlogUtils.getBrowseWorklogUrl(),true);
    }
}