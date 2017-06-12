
    <#list roots as root>
       <div class="col-sm-12" >
          <a href="/?query=${root.title.name}">${root.title.name}</a>
          <p> </p>
           <#list root.trees as tree>
                 <ul >
                     <li>
                         <a href="/?query=${tree.parent.name}">${tree.parent.name}</a>
                     </li>
                     <ul >
                          <li>
                             <a href="/?query=${tree.mainTerm.name}">${tree.mainTerm.name}</a>
                          </li>

                         <#list tree.children as child>
                               <ul >
                                  <li>
                                    <a href="/?query=${child.name}">${child.name}</a>
                                  </li>
                              </ul >
                         </#list>
                     </ul >
                 </ul >
           </#list>
       </div>
    </#list>