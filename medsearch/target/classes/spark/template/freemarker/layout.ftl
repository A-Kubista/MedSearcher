
<html>
<head>
           <title>Med Searcher</title>
           <#include "css/style.css">
           <#include "js/script.js">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
           <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
           <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
           <link rel="stylesheet" href="http://getbootstrap.com/examples/starter-template/starter-template.css">
       </head>
    <body>


                                <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                                         <div class="container">
                                               <form action="/index/" method="get">
                                                          <div class="form-group">
                                                              <div class="row">
                                                                  <div class="col-sm-7  col-md-offset-2">
                                                                         <input class="form-control" type='text' id="summary" name='query' placeholder="Search" />
                                                                  </div>
                                                                   <div class="col-sm-1">
                                                                        <input type='submit' value='Search' class="btn btn-primary"/>
                                                                  </div>
                                                              </div>
                                                          </div>
                                                    </form>
                                         </div>
                                     </div>


       <div class="jumbotron text-center">
          <p>Pub Med searcher</p>

           <div class="col-sm-6 query offset-md-3 well well-sm">
                <div class="row">
                      <div class="col-sm-2">
                           <div class="dropdown">
                               <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Sort by
                               <span class="caret"></span></button>
                               <ul class="dropdown-menu">
                                   <li><a href="#">HTML</a></li>
                                   <li><a href="#">CSS</a></li>
                                   <li><a href="#">JavaScript</a></li>
                               </ul>
                           </div>
                      </div>
                      <div class="col-sm-6 query offset-md-2">
                        <h4> ${query}</h4>
                      </div>
                </div>
           </div>

        </div>

         <div class="container">

          <#include "${templateName}">
           <div class="col-sm-2  col-md-offset-5">
                <button type="button" class="btn btn-info"  onclick="nextTweets()">More</button>
           </div>
      </div>

              <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
              <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
    </body>
</html>