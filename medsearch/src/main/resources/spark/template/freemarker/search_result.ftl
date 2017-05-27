
 <div class="col-sm-6  col-md-offset-3">
    <#list articles as article>
         <p>${article.date}</p>
         <p>${article.article_name}</p>
          <p>${article.article_content}</p>
             <p>${article.author}</p>
   </#list>
 </div>