
 <div class="col-sm-6  col-md-offset-3">
  <form action="/index/" method="get">
   <div class="form-group">
    <#list queries_indexed as term_key,term_value>
       <div class="row" >
          <a href="/?query=${term.name}">${term.name}</a>
       </div>
    </#list>
   </div>
  </form>
 </div>