
 <div class="col-sm-6  col-md-offset-3">
    <#list articles as article>
       <div class="row tweet "  >
             <div class="col-sm-12 tweet_text">
                    <div class="row">
                        <div class="col-sm-9 ">
                               <h3 class = "tweet_title"  id = ${article.id} >${article.title}</h3>
                        </div>
                        <div class="col-sm-3 ">
                               <p>${article.date}</p>
                        </div>
                    </div>
                               <p>${article.content}</p>
                                 <p>${article.author}</p>
             </div>
       </div>
     </#list>
     </div>