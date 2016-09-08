window.Employee = Backbone.Model.extend({
    urlRoot:'',
    idAttribute:'id'
});
window.Worklog = Backbone.Model.extend({
    urlRoot:''
});
window.Comment = Backbone.Model.extend({
    urlRoot:''
});

window.SharedEmployeeCollection = Backbone.Collection.extend({
    url:window.rootUri+'/worklog-data/showSharedPeople/',
    model:Employee
});

window.SharedTomMeEmployeeCollection = Backbone.Collection.extend({
    url:window.rootUri+'/worklog-data/shareToMe/',
    model:Employee
});

window.WorklogCollection = Backbone.Collection.extend({
    model:Worklog,
    url:window.rootUri+'/worklog-data/showWorkLogData/',
    findData:function(period,people){
        var self=this;
        $.get(this.url,{period:period,people:people},function(data){
            self.reset(_.isArray(data)?data:[]);
        });
    }
});

window.CommentCollection = Backbone.Collection.extend({
    model:Comment,
    url:window.rootUri+'/worklog-data/showComments/',
    findData:function(referId){
        var self=this;
        $.get(this.url,{referId:referId},function(data){
            if (_.isArray(data)) self.reset(data);
        });
    }
});


