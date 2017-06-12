
    <#list roots as root>
       <div class="col-sm-12" >
          <a href="/?query=${root.title.name}">${root.title.name}</a>
          <p> </p>
           <#list root.trees as tree>
                 <div class="col-sm-11 col-md-offset-1" >
                     <a href="/?query=${tree.parent.name}">${tree.parent.name}</a>
                 </div>
                 <div class="col-sm-10 col-md-offset-2" >
                    <a href="/?query=${tree.mainTerm.name}">${tree.mainTerm.name}</a>
                 </div>
                 <#list tree.children as child>
                     <div class="col-sm-9 col-md-offset-3" >
                        <a href="/?query=${child.name}">${child.name}</a>
                     </div>
                 </#list>
           </#list>
       </div>
    </#list>