
<div class="col-sm-6">
  <form action="/?query = ${query}" method="get">
   <div class="form-group">
        <#list factors as factor>
           <div class="row" >
              <div class="col-sm-6">
                    <p>${factor.val1}</p>
              </div>
              <div class="col-sm-6">
                    <input class="form-control" type=number step=0.01 name='factor_${factor.id}' value='${factor.val2}' placeholder="Weigths" />
              </div>
           </div>
        </#list>
          <div class="col-md-0 hidden">
        <input  -sm class="form-control" type=text  name='query' value='${query}' placeholder="Search" />
        </div>
        <div class="col-md-4">
             <input  type='submit' value='Change Weights' class="btn btn-primary"/>
         </div>
   </div>
  </form>
 </div>