
 <div class="col-sm-6  col-md-offset-3">
    <#list articles as article>
       <div class="row tweet "  >
             <div class="col-sm-12 tweet_text">
                    <h5>${article.article.full_keys}</h5>
                    <div class="row">
                        <div class="col-sm-9 ">
                               <h3 class = "tweet_title"  id = ${article.article.id} >${article.article.title}</h3>
                        </div>
                        <div class="col-sm-3 ">
                               <p>${article.article.date}</p>
                        </div>
                    </div>
                               <p>${article.article.content}</p>
                                 <p>${article.article.author}</p>
             </div>
       </div>
     </#list>
     </div>