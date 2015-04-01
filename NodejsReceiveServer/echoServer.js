var http = require('http');
var url = require('url');
var querystring = require('querystring');

http.createServer(function (request, response) {
  var params = '';

  if (request.method === 'GET') {
    params = JSON.stringify(url.parse(request.url, true).query);
    console.log('[GET] ' + params);
    response.writeHead(200, "OK", {'Content-Type': 'text/plain'});
    response.end(params);
  } else if (request.method === 'POST') {
    request.on('data', function(chunk) {
      params = JSON.stringify(querystring.parse(chunk.toString()));
      console.log('[POST] ' + params);
    });

    request.on('end', function() {
      response.writeHead(200, "OK", {'Content-Type': 'text/plain'});
      response.end(params);
    });
  }

}).listen(1337);