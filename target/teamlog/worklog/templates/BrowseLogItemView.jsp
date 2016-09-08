<img src="<<=avatar>>" alt="" class="large-avatar">
<div class="log-main">
    <div><a href="#" class="user-link" id="u<<=userId>>"><strong><<=username>></strong></a><span class="pretty-time" value="<<=createTime>>" title="<<=CommonUtils.formatUtcDate(createTime)>>" style="padding-left:20px;"><<=CommonUtils.formatUtcDate(createTime)>></span></div>
    <div class="log-main-indicator">
        <div class="log-main-indicator-back"><div class="log-main-indicator-front"></div></div>
    </div>
    <div class="log-main-content"><<=description>></div>
    <div>
        <<_.each(tags.split(','),function(tag){>>
        <span class="badge"><i class="icon-tag icon-white"></i><<=tag>></span>
        <<});>>
    </div>
    <div class="log-main-icons"><span id="comments-<<=id>>"><<=comments>> <i class="icon-comment"></i></span><span style="margin-left: 20px;" id="nice-<<=id>>"><<=nice>> <i class="icon-heart"></i></span></div>
</div>
<div class="log-main-comments hide"></div>
