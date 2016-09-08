<div class="password-indicator">
    <p><fmt:message key="password.indicator.label"/><span class="password-text"></span></p>
    <div class="back-bar"></div>
    <div class="front-bar"></div>
</div>
<script type="text/javascript">
    function checkStrength(password) {

        //initial strength
        var strength = 0

        //if the password length is less than 6, return message.
        if (password.length < 6) {
            $('.front-bar').css({'width':'70px','background-color':'#ff0000'});
            $('.password-text').text('<fmt:message key="password.indicator.short.label"/>');
            return;
        }

        //length is ok, lets continue.

        //if length is 8 characters or more, increase strength value
        if (password.length > 7) strength += 1

        //if password contains both lower and uppercase characters, increase strength value
        if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1

        //if it has numbers and characters, increase strength value
        if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  strength += 1

        //if it has one special character, increase strength value
        if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  strength += 1

        //if it has two special characters, increase strength value
        if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,",%,&,@,#,$,^,*,?,_,~])/)) strength += 1

        //now we have calculated strength value, we can return messages

        //if value is less than 2
        if (strength < 2) {
            $('.front-bar').css({'width':'70px','background-color':'#ff0000'});
            $('.password-text').text('<fmt:message key="password.indicator.weak.label"/>');
        } else if (strength == 2) {
            $('.front-bar').css({'width':'140px','background-color':'#ffa500'});
            $('.password-text').text('<fmt:message key="password.indicator.normal.label"/>');
        } else {
            $('.front-bar').css({'width':'210px','background-color':'#008000'});
            $('.password-text').text('<fmt:message key="password.indicator.strong.label"/>');
        }
    }
</script>