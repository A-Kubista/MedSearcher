
 <div class="col-sm-6  col-md-offset-3">
    <#list articles as article>
       <div class="row tweet "  >
             <div class="col-sm-12 tweet_text"  .text-center>

                    <div class="row">
                        <div class="col-sm-9 ">
                               <h2 class = "tweet_title"  id = ${article.article.id} >${article.article.title}</h2>
                        </div>
                        <div class="col-sm-3 ">
                               <p>${article.article.date}</p>
                        </div>
                    </div>
                               <h3>${article.article.full_keys}</h3>
                               <h3>${article.article.author}</h3>
                               <h4>${article.article.content}</h4>

                     <div class="row" >
                          <div class="col-sm-6 text-center">
                               <h5>TF: ${article.TF} (${article.TFnumber})</h5>
                                <h5>TF-IDF: ${article.IDF} (${article.IDFnumber})</h5>
                          </div>
                          <div class="col-sm-6 .text-center">
                               <h5>DMI: ${article.DMI} (${article.DMInumber})</h5>
                                <h5>LTI: ${article.LTI} (${article.LTInumber})</h5>
                          </div>
                      </div>
                         <h5>Lucene: ${article.test} (${article.test_two})</h5>

             </div>
       </div>
     </#list>
     </div>