 <script>
 document.onreadystatechange = function () {
     result = getTweetParamValue();
       if(result != 0){
                result = document.getElementById(result);
             if(result != null && result.id > 20){
                     window.scrollTo(0,result.scrollHeight);
             }else{
                window.scrollTo(0,document.body.scrollHeight);
             }
       }
 }

 function nextTweets(){
    result = getTweetParamValue();
    if(result != 0){
        window.location.href =   updateUrlParameter(window.location.href ,"tweets_num",parseInt(result) + 20);
    }else{
       insertParam("tweets_num",20);
    }
    window.scrollTo(0,document.body.scrollHeight);
}

function insertParam(key, value)
{
    key = encodeURI(key); value = encodeURI(value);

    var kvp = document.location.search.substr(1).split('&');

    var i=kvp.length; var x; while(i--)
    {
        x = kvp[i].split('=');

        if (x[0]==key)
        {
            x[1] = value;
            kvp[i] = x.join('=');
            break;
        }
    }

    if(i<0) {kvp[kvp.length] = [key,value].join('=');}

    document.location.search = kvp.join('&');
}

function getTweetParamValue(){
   var url =  window.location.href ;
   try {
     var captured = /tweets_num=([^&]+)/.exec(url)[1];
    }catch(err) {
         captured = 0;
   }
    return captured ? captured : 0;
}

function updateUrlParameter(url, param, value){
    var regex = new RegExp('('+param+'=)[^\&]+');
    return url.replace( regex , '$1' + value);
}
 </script>