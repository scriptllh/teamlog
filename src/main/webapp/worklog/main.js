_.templateSettings = {
    escape: /<<-([\s\S]+?)>>/g,
    evaluate: /<<([\s\S]+?)>>/g,
    interpolate: /<<=([\s\S]+?)>>/g
};

window.constants={
    ACTIVE_CLASS:'active',
    PRETTY_TIME_CLASS:'.pretty-time',
    CHECKED_ATTR:'checked',
    NUMBER_SIGN:'#',
    V_LINE_CHAR:'|',
    MINUS_CHAR:'-',
    COMMA_CHAR:',',
    PIXEL_UNIT:'px',
    DATE_FORMAT:'yyyy-MM-dd',
    DATETIME_FORMAT:'yyyy-MM-dd HH:mm:ss'
};

window.WorkLogRouter = Backbone.Router.extend({
    routes: {
        "":"home",
        "editLog":"editLog",
        "viewLog":"viewLog",
        "viewLog/:period":"viewLog",
        "viewLog/:period/:people":"viewLog"
    },

    home:function() {
        this.editLog();
    },

    initialize:function() {
        this.currModule='edit';
    },

    editLog:function() {
        this.currModule='edit';
        this.selectFunc('edit-log');
        if (!this.editLogView) {
            this.editLogView = new EditLogView();
        }
        $(constants.NUMBER_SIGN + 'data-container').empty().html(this.editLogView.render().el);
        this.editLogView.initCalendar();
        this.editLogView.initForm();

        if (!this.editLogPeopleView) {
            this.editLogPeopleView = new EditLogPeopleView();
        }
        $('.function-context-bar>div').detach();
        $('.function-context-bar').html(this.editLogPeopleView.el);
        this.editLogPeopleView.model.fetch();
    },

    viewLog:function(period,people) {
        this.selectFunc('view-log');
        if (!this.viewLogView) {
            this.viewLogView = new BrowseLogHeaderView();
        }

        if (!this.browseLogPeopleView) {
            this.browseLogPeopleView = new BrowseLogPeopleView();
            this.browseLogPeopleView.model.fetch();
        }
        if (this.currModule == 'edit') {
            $(constants.NUMBER_SIGN + 'data-container').empty().html(this.viewLogView.render(period).el);
            $('.function-context-bar>div').detach();
            $('.function-context-bar').html(this.browseLogPeopleView.el);
            this.currModule = 'view';
        }

        this.browseLogPeopleView.updateCondition({people:people,period:period});
    },

    selectFunc:function(item) {

        var $f_list = $(".function-list"),cls = '-active';
        $f_list.find(".f-link-wrapper div:first-child").each(function(){
            var p = "class";
            var c = $(this).attr(p);

            if (c.search(cls)>0) {
                $(this).attr(p, c.replace(cls,''));
            }
        });
        $f_list.find('.f-link-text').removeClass('active');
        $f_list.find(".f-item-active-line,.f-item-active-color").detach();
        $f_list.find('.'+item).removeClass(item).addClass(item+cls);
        $('#'+item).append($('<div class="f-item-active-line">')).append($('<div class="f-item-active-color">')).find('.f-link-text').addClass('active');
    }
});

templateLoader.load(["BrowseLogHeaderView","BrowseLogPeopleView","BrowseLogItemView","BrowseLogCommentView"
    ,"BrowseLogPostCommentView","EditLogView","EditLogPeopleView"],
    function () {
        window.app = new WorkLogRouter();
        Backbone.history.start();
    }
);