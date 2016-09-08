window.BrowseLogHeaderView = Backbone.View.extend({
    render:function(period) {
        var needDelegate=this.$el.children().size()>0;
        $(this.el).html(this.template());
        $('#reportrange',this.el).daterangepicker(
            {
                ranges: {
                    '今天': ['today', 'today'],
                    '昨天': ['yesterday', 'yesterday'],
                    '最近7天': [Date.today().add({ days: -6 }), 'today'],
                    '最近30天': [Date.today().add({ days: -29 }), 'today'],
                    '本月': [Date.today().moveToFirstDayOfMonth(), Date.today().moveToLastDayOfMonth()],
                    '上个月': [Date.today().moveToFirstDayOfMonth().add({ months: -1 }), Date.today().moveToFirstDayOfMonth().add({ days: -1 })]
                }
            },
            function(start, end) {
                $('#reportrange span').html(start.toString(constants.DATE_FORMAT) + ',' + end.toString(constants.DATE_FORMAT));
                app.navigate(teamlogUtils.getBrowseWorklogUrl(),true);
            }
        );

        if (!period) {
            period = Date.today().add({ days: -6 }).toString(constants.DATE_FORMAT) + ','+Date.today().toString(constants.DATE_FORMAT);
        }

        $('#reportrange span',this.el).html(period);

        if (needDelegate) this.delegateEvents();

        return this;
    },
    events: {
        "click #showReportBtn":"showReports"
    },

    navigate:function(url) {
        Backbone.history.navigate(constants.NUMBER_SIGN+url);
    },

    showReports:function(e) {
        var $reportsDiv = $('#work-log-reports');
        if (!$(e.target).hasClass('active')) {
            $reportsDiv.show();
            this.renderReports();
            $reportsDiv.slideDown();
        } else {
            $reportsDiv.slideUp();
        }
    },

    refreshReports:function() {
        if (this.$el.find('#showReportBtn').hasClass('active')) this.renderReports();
    },

    renderReports:function(){
        var users= _.pluck(window.app.browseLogPeopleView.model.toJSON(),'username'),comments=new Array(),likes=new Array(),
            efforts=new Array();
        var workLogs = window.app.browseLogPeopleView.browseLogListView.model.toJSON();

        if (!workLogs||workLogs.length==0) {
            var noData='没有数据...';
            $('#peopleChart').empty().html(noData);
            $('#categoryChart').empty().html(noData);
            $('#commentChart').empty().html(noData);
            $('#likeChart').empty().html(noData);
            return;
        }

        var categoryData = _.groupBy(workLogs,'tags');
        _.each(categoryData,function(d,idx){
            categoryData[idx] = _.reduce(d, function (c,log) {
                return c+CommonUtils.substractDate(log.endTime, log.startTime);
            }, 0);
        });

        for (var i = 0; i < users.length; i++) {
            comments[i]=0;
            likes[i]=0;
            efforts[i]=0;
        }

        _.each(workLogs,function(logs) {
            for (var i = 0; i < users.length; i++) {
                var user = users[i];
                if (logs.username == user) {
                    comments[i] += logs.comments;
                    likes[i] += logs.nice;
                    efforts[i] += CommonUtils.substractDate(logs.endTime,logs.startTime);
                    break;
                }
            }
        });

        $('#peopleChart,#commentChart,#likeChart').empty().css('height',users.length*50+'px').show();
        $('#categoryChart').empty().css('height',_.size(categoryData)*50+'px').show();
        // 50 px per person.
        var options = {
            seriesDefaults: {
                renderer:$.jqplot.BarRenderer,
                // Show point labels to the right ('e'ast) of each bar.
                // edgeTolerance of -15 allows labels flow outside the grid
                // up to 15 pixels.  If they flow out more than that, they
                // will be hidden.
                pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
                // Rotate the bar shadow as if bar is lit from top right.
                shadowAngle: 135,
                rendererOptions: {
                    barDirection: 'horizontal'
                }

            },
            axesDefaults: {
                rendererOptions: {
                    baselineWidth: 1.5,
                    drawBaseline: false
                }
            },
            grid: {
                drawBorder: false,
                borderWidth:0,
                shadow:false
            },

            axes: {
                yaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    drawMajorGridlines: false
                },
                xaxis: {
                    drawMajorGridlines: false,
                    showTicks:false
                }
            }
        };
        var sortFunc=function(d) {return d[0]};
        $.jqplot('peopleChart', [_.sortBy(_.zip(efforts,users),sortFunc).reverse()],options);
        $.jqplot('categoryChart', [_.sortBy(_.zip(_.values(categoryData), _.keys(categoryData)),sortFunc).reverse()],options);
        $.jqplot('commentChart',[_.sortBy(_.zip(comments,users),sortFunc).reverse()],options);
        $.jqplot('likeChart', [_.sortBy(_.zip(likes,users),sortFunc).reverse()],options);
    }
});


window.BrowseLogPeopleView = Backbone.View.extend({
    initialize:function() {
        this.model= new SharedTomMeEmployeeCollection();
        this.model.bind('reset',this.render,this);
    },

    updateCondition:function(c) {
        this.condition = c;
        this.renderLogView();
    },

    render:function(){

        var modelData = this.model.toJSON();

        if (modelData && modelData.length>0) {
            _.each(modelData,function(p) {
                teamlogUtils.avatarProcess(p);
                p.ellipsisName=CommonUtils.ellipsisStr(p.username);
            });

            $(this.el).empty().html(this.template({people:modelData})).addClass('h-scroll-bar');

            this.updatePeopleStatus(this.condition.people);
        }

        return this;
    },

    renderLogView:function() {
        if (!this.browseLogListView) {
            this.worklogCollection = new WorklogCollection();
            this.browseLogListView = new BrowseLogListView({model:this.worklogCollection});
        }

        if (!this.condition.period) this.condition.period = teamlogUtils.getBrowseWorklogPeriod();
        if (!this.condition.people)  {
            this.condition.people = teamlogUtils.getBrowseWorkLogUsers();
        } else {
            this.updatePeopleStatus(this.condition.people);
        }
        this.worklogCollection.findData(this.condition.period,this.condition.people);
        $('#exportReportBtn').attr('href',window.rootUri+'/worklog/export/'+ this.condition.period+"/"+ this.condition.people);
        $(constants.NUMBER_SIGN + 'work-log-container').empty().html(this.browseLogListView.el);
    },

    updatePeopleStatus:function(people) {
        if (!people) {
            people = $('#userId',this.el).html();
        }

        var parent = this.el;
        $("input[id!='c0'][type='checkbox']",parent).prop(constants.CHECKED_ATTR,false).removeClass(constants.ACTIVE_CLASS);
        $.each(people.split(constants.COMMA_CHAR),function(i,d) {
            $('input[id="c'+d+'"]',parent).attr(constants.CHECKED_ATTR,constants.CHECKED_ATTR).parent().addClass(constants.ACTIVE_CLASS);
        });
    },

    events: {
        "click .f-ul input":"selectPeople",
        "click .f-ul a":"selectOne",
        "keyup #filterInput":"filterPeople",
        "keypress #filterInput":"preventDefaultKey"
    },

    filterPeople:function(e) {
        var searchStr = $('#filterInput').val();

        var $peopleItems = $('.f-ul>li',this.el);
        if (searchStr=='') {
            $peopleItems.show();
        } else {
            $peopleItems.each(function(i,d){
                $(d).find("a").text().search(searchStr)>=0?$(d).show():$(d).hide();
            });
        }

    },

    preventDefaultKey:function(e) {
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    },

    clickAllPeople:function ($target) {
        var $checkbox = $('.f-ul input', this.el);
        if ($target.attr(constants.CHECKED_ATTR) == constants.CHECKED_ATTR) {
            $checkbox.attr(constants.CHECKED_ATTR, constants.CHECKED_ATTR);
        } else {
            $checkbox.removeAttr(constants.CHECKED_ATTR);
            if ($checkbox.length > 1) $($checkbox[1]).attr(constants.CHECKED_ATTR, constants.CHECKED_ATTR);//select first one.
        }
    },

    highlightPeople:function() {
        $('.f-ul input', this.el).parent().removeClass(constants.ACTIVE_CLASS);
        $('.f-ul input[checked="'+constants.CHECKED_ATTR+'"]', this.el).parent().addClass(constants.ACTIVE_CLASS);
    },

    doCheck:function($target) {
        if ($target.attr(constants.CHECKED_ATTR) == constants.CHECKED_ATTR) {
            $target.removeAttr(constants.CHECKED_ATTR);
        } else {
            $target.attr(constants.CHECKED_ATTR, constants.CHECKED_ATTR);
        }
    },

    selectPeople:function(e) {
        var $target = $(e.target);
        if ($target.attr("id") == 'c0') {
            this.clickAllPeople($target);
            this.highlightPeople();
        } else {
            $target.parent().toggleClass(constants.ACTIVE_CLASS);
        }
        teamlogUtils.hideReport();
    },

    selectOne:function(e) {
        this._navToOne($(e.target));
        e.preventDefault();
    },

    _navToOne:function($target) {
        if ($target.attr("id") == 'a0') {
            this.doCheck($target.next());//select all first.
            this.clickAllPeople($target.next());
        } else {
            $('.f-ul input', this.el).removeAttr(constants.CHECKED_ATTR);
            $target.next().attr(constants.CHECKED_ATTR,constants.CHECKED_ATTR);
        }
        this.highlightPeople();
        teamlogUtils.hideReport();
    }
});

window.BrowseLogListView = Backbone.View.extend({
    className:'log-list',
    initialize:function() {
        this.model.bind('reset',this.render,this);
    },

    render:function() {
        var currentDate,$ul,self=this;
        this.$el.empty();
        _.each(this.model.models,function(workLog){
            var starTime = workLog.get('startTime');
            if (!CommonUtils.equalDate(starTime,currentDate)) {
                currentDate = starTime;
                var d = Date.parse(currentDate);
                var dateWithTimezone = CommonUtils.formatDateWithTimezone(d);
                self.$el.append($('<div class="page-header"><h4>'+d.toString(constants.DATE_FORMAT)+'  <span class="help-inline pretty-time" value="'+ dateWithTimezone+'" title="'+CommonUtils.formatUtcDate(dateWithTimezone)+'"></span></h4></div>'));
                $ul = $('<ul>');
                self.$el.append($ul);
            }
            $ul.append(new BrowseLogItemView({model:workLog}).render().el);
        });
        if (this.model.models.length<1) {
           this.$el.append('<span class="label label-warning" style="font-size: 14px;padding: 10px 20px;">'+i18n.worklogDataEmpty+'</span>');
            $('#exportReportBtn').addClass("disabled").attr("href","#viewLog");
        } else {
            $('#exportReportBtn').removeClass("disabled");
        }
        $(constants.PRETTY_TIME_CLASS,this.el).prettyDate({attribute:"value"});
        app.viewLogView.refreshReports();
        return this;
    }
});

window.BrowseLogItemView = Backbone.View.extend({
    tagName:'li',

    events:{
        "click .log-main-icons>span":"detailData",
        "click .user-link":"filterUser"
    },

    filterUser:function(e) {
        var findStr = '#'+$(e.currentTarget).attr('id').replace('u','a');
        var $target = $(findStr);
        if ($target) {
            app.browseLogPeopleView._navToOne($target);
        }
        return false;
    },

    detailData:function(e) {
        var attr = $(e.target).attr('id');
        if (!attr) {
            attr = $(e.target).parent().attr('id');
        }
        var t = attr.split(constants.MINUS_CHAR);
        if (t[0]=='comments') {
            var $comments = $('.log-main-comments',this.el);
            if ($comments.text().length==0) {
                if (!this.commentCollection) {
                    this.commentCollection = new CommentCollection();
                }
                this.commentCollection.dataId = t[1];

                $comments.html(new BrowseLogCommentListView({model:this.commentCollection}).render().el);
                $comments.append(new BrowseLogPostCommentView({model:{dataId:t[1],avatar:$('.login-info img').attr('src'),username:$('#username').text()}}).render().el);
            }
            $comments.fadeToggle();

        } else {
            //nice
            $.post(window.rootUri+'/worklog-data/nice',{referId:t[1]},function(data) {
                if (_.isNumber(data)) {
                    var $nice = $('#nice-'+t[1]);
                    var count = parseInt($nice.text())+1;
                    $nice.html(count.toString()+' <i class="icon-heart"></i>');
                } else {
                    CommonUtils.reLogin(data);
                }

            });
        }
    },

    render:function() {
        var m = this.model.toJSON();
        if (!m.description) m.description=i18n.noDescription;
        if (!m.tags) m.tags=i18n.noTag;
        teamlogUtils.dateAndAvatarPrepare(m);
        this.$el.html(this.template(m));
        var e = Date.parse(m.endTime),s = Date.parse(m.startTime),h=(e-s)/1000/3600;

        this.$el.find('.log-main-indicator-front').css({'width':(h*10+constants.PIXEL_UNIT),'margin-left':(s.getHours()*10+constants.PIXEL_UNIT)});
        this.$el.find('.log-main-indicator').attr('title', s.toString(i18n.timeFormat)+constants.MINUS_CHAR+ e.toString(i18n.timeFormat)+", "+h+i18n.hourStr);
        return this;
    }
});

window.BrowseLogCommentListView =  Backbone.View.extend({
    tagName:'ul',

    initialize: function() {
        this.model.bind('reset',this.render,this);
        this.model.findData(this.model.dataId);
    },

    render:function() {
        var self=this;
        _.each(this.model.models,function(comment) {
            self.$el.append(new BrowseLogCommentView({model:comment}).render().el);
        });

        $(constants.PRETTY_TIME_CLASS,this.el).prettyDate();
        return this;
    }

});

window.BrowseLogCommentView =  Backbone.View.extend({
    tagName:'li',

    render:function() {
        var m = this.model.toJSON();
        teamlogUtils.dateAndAvatarPrepare(m);
        this.$el.html(this.template(m));
        return this;
    },
    events:{
        "click .user-link":"filterUser"
    },

    filterUser:function(e) {
        var findStr = '#'+$(e.currentTarget).attr('id').replace('ru','a');
        var $target = $(findStr);
        if ($target) {
            app.browseLogPeopleView._navToOne($target);
        }
        return false;
    }

});

window.BrowseLogPostCommentView = Backbone.View.extend({
    events: {
        "click .log-main-post":"togglePostForm",
        "click a":"togglePostForm",
        "click button":"submitPost"
    },

    togglePostForm:function() {
        $('.log-main-post-form,.log-main-post',this.el).toggle();
        $('textarea',this.el).val('').focus();
    },
    submitPost:function() {
        var self = this,desc = $('textarea',this.el).val(),done=false;
        CommonUtils.postForm(window.rootUri+'/worklog-data/newComment',{description:desc,referId:this.model.dataId},function(data,status,jqr) {
            if (_.isNumber(data)) {
                var subNode = $('<li>');
                subNode.html($(new BrowseLogCommentView().template({
                    userId:teamlogUtils.getCurrentUserId(),
                    avatar:self.model.avatar,description:desc,
                    createTime:CommonUtils.formatDateWithTimezone(new Date()),username:self.model.username
                }))).appendTo(self.$el.prev()).find(constants.PRETTY_TIME_CLASS).prettyDate();
                $('#comments-'+self.model.dataId).html(self.$el.prev().children().length+' <i class="icon-comment"></i>');
                done=true;
            } else {
                CommonUtils.reLogin(data);
            }
        },function() {
            self.toggleIndicator();
        },function() {
            self.toggleIndicator();
            if (done) self.togglePostForm();
        });

    },

    toggleIndicator:function() {
        $('.actionBtns,.doAction',this.el).toggle();
    },

    render:function() {
        this.$el.html(this.template(this.model));
        return this;
    }
});

window.EditLogView = Backbone.View.extend({
    render:function() {
        this.$el.html(this.template());
        return this;
    },

    events: {
        "click #cancelBtn":"closeEditor",
        "click #deleteBtn":"deleteWorklog"
    },

    closeEditor:function(e) {
        $('#worklogCalendar').fullCalendar('unselect');
        $('#editor').fadeOut();
        if (e) e.preventDefault();
    },

    deleteWorklog:function(e) {
        //todo: replace with confirm plugin to avoid the browser will prevent the dialog...
            if (confirm(i18n.deleteComfirm)) {
                var self=this;
                $.post(window.rootUri+"/worklog-data/delete",{id : self.currentEvent.id},function(data){
                    if (_.isNumber(data)) {
                        $('#worklogCalendar').fullCalendar('removeEvents',self.currentEvent.id);
                        self.closeEditor(e);
                    } else {
                        CommonUtils.reLogin(data);
                    }
                });
            }
        e.preventDefault();
    },

    formatDate:function(d) {
        return $.fullCalendar.formatDate(d,constants.DATETIME_FORMAT);
    },

    refreshTags:function() {
        $.getJSON(window.rootUri+'/worklog-data/getTags/',function(data){
            if (_.isArray(data) && data.length>0) {
                window.tags=data;
                var $tags = $('#tags'),optionT= _.template('<option value="<<=id>>"><<=name>></option>');
                $tags.empty();
                $.each(data,function(i,d){
                    $tags.append(optionT(d));
                });
            } else {
                CommonUtils.reLogin(data);
            }
        });
    },

    initForm:function() {
        var self = this;
        $('#worklogForm',this.el).ajaxForm({
            $tags:$('#tags'),
            beforeSubmit:function(formData, jqForm, options) {
                var $option = this.$tags.find('option:selected');
                formData.push(
                    {name:'start',value:self.formatDate(self.currentEvent.start)},
                    {name:'end',value:self.formatDate(self.currentEvent.end)},
                    {name:'tagStr',value:$option.text()},
                    {name:'tagId',value:$option.attr('value')},
                    {name:'id',value:self.currentEvent.id}
                );
            },
            dataType:'json',
            success:function(rtn) {
                if (_.isNumber(rtn)) {
                    var isNew = self.currentEvent.id ==0,$worklogCalendar = $('#worklogCalendar');
                    self.currentEvent.id = rtn;
                    self.currentEvent.title = $('#description').val();
                    self.currentEvent.description = self.currentEvent.title;
                    self.currentEvent.tagId = this.$tags.find('option:selected').attr('value');
                    self.currentEvent.allDay = false;
                    self.closeEditor();
                    if (isNew) {
                        $worklogCalendar.fullCalendar('renderEvent',self.currentEvent);
                    } else {
                        $worklogCalendar.fullCalendar('updateEvent', self.currentEvent);
                    }
                } else {
                    CommonUtils.reLogin(rtn);
                }
            }
        });
        self.refreshTags();
    },

    updateWorklog:function(w) {
        if (!w) w={tagId:0};
        $.each(['id','description'],function(i,d) {
            $('#'+d).val(w[d]?w[d]:'');
        });

        $('#tags').val(w.tagId);
    },

    initCalendar:function() {
        this.$calendar = $('#worklogCalendar',this.el);
        var self = this;
        this.$calendar.fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek,agendaDay'
            },
            firstHour:7,
            contentHeight:$(window).height() - 180,
            handleWindowResize:true,
            windowResize:function(v) {
                v.setHeight($(window).height() - 180);
            },
            monthNames:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
            monthNamesShort:['01','02','03','04','05','06','07','08','09','10','11','12'],
            dayNames:['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
            dayNamesShort:['日','一','二','三','四','五','六'],
            buttonText:{
                prev:     '&nbsp;&#9668;&nbsp;',  // left triangle
                next:     '&nbsp;&#9658;&nbsp;',  // right triangle
                prevYear: '&nbsp;&lt;&lt;&nbsp;', // <<
                nextYear: '&nbsp;&gt;&gt;&nbsp;', // >>
                today:    '今天',
                month:    '月',
                week:     '周',
                day:      '日'
            },
            titleFormat:{
                month: 'yyyy/MMM',                             // 2009 10
                week: "yyyy/MMM/d{  '&#8212;'  [ yyyy/][MMM/]d }", // Sep 7 - 13 2009
                day: 'yyyy/MMM/d (dddd)'                  // Tuesday, Sep 8, 2009
            },
            columnFormat:{
                month: 'ddd',    // Mon
                week: 'M/d ddd', // Mon 9/7
                day: 'M/d dddd'  // Monday 9/7
            },
            defaultView:'agendaWeek',
            selectable:true,
            unselectAuto:false,
            editable: true,
            allDaySlot:false,
            select:function(startDate, endDate, allDay, jsEvent, view) {
                self.updateWorklog();
                self.currentEvent={start:startDate,end:endDate,id:0};
                $('#deleteBtn').hide();
                CommonUtils.positionPopover(jsEvent.target,$('#editor'));
            },
            eventResize:function( event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view ) {
                $.post(window.rootUri+'/worklog-data/updateTime/',{id:event.id,start:self.formatDate(event.start),
                        end:self.formatDate(event.end)},
                    function(rtn) {
                        //do noting.
                        if (!_.isNumber(rtn)) {
                            CommonUtils.reLogin(rtn);
                            event.end.setDate(event.end.getDate() - dayDelta);
                            event.end.setMinutes(event.end.getMinutes() - minuteDelta);
                            self.$calendar.fullCalendar('updateEvent',event);
                        }
                    });

            },
            eventDrop:function( event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view ) {
                $.post(window.rootUri+'/worklog-data/updateTime/',{id:event.id,start:self.formatDate(event.start),
                        end:self.formatDate(event.end)},
                    function(rtn) {
                        //do noting.
                        if (!_.isNumber(rtn)) {
                            CommonUtils.reLogin(rtn);
                            event.start.setDate(event.start.getDate() - dayDelta);
                            event.start.setMinutes(event.start.getMinutes() - minuteDelta);
                            event.end.setDate(event.end.getDate() - dayDelta);
                            event.end.setMinutes(event.end.getMinutes() - minuteDelta);
                            self.$calendar.fullCalendar('updateEvent',event);
                        }
                    });
            },
            eventClick:function(calEvent, jsEvent, view) {
                self.updateWorklog(calEvent);
                self.currentEvent=calEvent;
                $('#deleteBtn').show();
                CommonUtils.positionPopover(jsEvent.target,$('#editor'));
            },
            events: function(start, end, callback) {
                $.get(window.rootUri+'/worklog-data/showWorkLogData',{period:Date.parse(start).toString(constants.DATE_FORMAT) + ","+Date.parse(end).toString(constants.DATE_FORMAT),people:teamlogUtils.getCurrentUserId()},function(rtn){
                    if (_.isArray(rtn)) {
                        var events=[];
                        $.each(rtn,function(idx,v){
//                            v.allDay = !w.isSameDay($.fullCalendar.parseDate(v.start),$.fullCalendar.parseDate(v.end));
//                            v.id = v.id;
                            v.start = v.startTime;
                            v.end = v.endTime;
                            v.title = v.description;
                            v.allDay = !CommonUtils.equalDate(v.start, v.end);
//                            v.tagId = v.tagId;
                            events.push(v);
                        });
                        callback(events);
                    } else {
                        CommonUtils.reLogin(rtn);
                    }

                });
            }
        });
        this.refreshCalendarHeight();
    },
    refreshCalendarHeight:function() {
        $('#worklogCalendar').fullCalendar('getView').setHeight($(window).height() - 180);
    }
});

window.EditLogPeopleView = Backbone.View.extend({

    render:function(people){

        if (this.model.models) {
            var peopleData=this.model.toJSON(),selfId = teamlogUtils.getCurrentUserId(),selfPos=0;
            $.each(peopleData,function(i,p) {
                teamlogUtils.avatarProcess(p);
                p.ellipsisName=CommonUtils.ellipsisStr(p.username);
                if (p.id == selfId) selfPos = i;
            });

            if (selfPos>0) peopleData.splice(selfPos,1);

            this.$el.html(this.template({people:peopleData})).addClass('h-scroll-bar');
        }

        return this;
    },

    events:{
        "click .f-ul>li>span":"sharePerson",
        "mouseenter .f-ul>li":"enterPerson",
        "mouseleave .f-ul>li":"leavePerson",
        "keyup #filterInput":"filterPeople",
        "keypress #filterInput":"preventDefaultKey"
    },

    filterPeople:function(e) {
        var searchStr = $('#filterInput').val();

        var $peopleItems = $('.f-ul>li',this.el);
        if (searchStr=='') {
            $peopleItems.show();
        } else {
            $peopleItems.each(function(i,d){
                $(d).text().search(searchStr)>=0?$(d).show():$(d).hide();
            });
        }
    },

    preventDefaultKey:function(e) {
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    },

    sharePerson:function(e) {
        var $span=$(e.target).parent(),personId=$span.attr('id');

        if ($span.hasClass('label-info')) {
            //delete
            $.post(window.rootUri+'/worklog-data/deleteSharedPerson/',{id:personId.replace('p','')},function(data) {
                if (_.isNumber(data)) {
                    $span.removeClass('label-info').addClass('hide').hide().attr('title',i18n.notSharePerson);
                } else {
                    CommonUtils.reLogin(data);
                }
            });

        } else {
            //add
            $.post(window.rootUri+'/worklog-data/addSharePerson/', {id:personId.replace('p', '')}, function (data) {
                if (_.isNumber(data)) {
                    $span.removeClass('hide').addClass('label-info').attr('title',i18n.sharePerson);
                } else {
                    CommonUtils.reLogin(data);
                }
            });
        }
    },

    enterPerson:function(e) {
        this._switchSpan(e.target,true);
    },
    leavePerson:function(e) {
        this._switchSpan(e.target,false);
    },
    _switchSpan:function(d,show) {
        var $span = $(d).find('span');
        if ($span.hasClass('hide')) {
            show?$span.show():$span.hide();
        }
    },

    initialize:function() {
        this.model=new SharedEmployeeCollection();
        this.model.bind('reset remove add',this.render,this);
        this.model.fetch();
    }


});