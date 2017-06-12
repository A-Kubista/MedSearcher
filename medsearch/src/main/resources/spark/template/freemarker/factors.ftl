
 <div class="col-sm-6  col-md-offset-3">
  <form action="/index/" method="get">
   <div class="form-group">
    <#list articles as article>
       <div class="row" >
          <div class="col-sm-7  col-md-offset-2">
                 <input class="form-control" type='text' id="summary" name='query' placeholder="Search" />
          </div>
           <div class="col-sm-1">
                <input type='submit' value='Search' class="btn btn-primary"/>
          </div>
       </div>
    </#list>
   </div>
  </form>
 </div>