//importing modules
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var cors = require('cors');
var path = require('path');
mongoose.Promise = global.Promise;

// to use express methode
var app = express();
// port number
const port = 3000;


//adiing middleware -cors
app.use(cors());


app.set('port', port);


// const http = require('http');
app.use(bodyParser.json());
//static files
app.use(express.static(path.join(__dirname,'public')));


// define routes
var index = require('./routes/index');
var teachers = require('./routes/teachers');
var location= require('./routes/location');
var babysitter= require('./routes/babysitter');

//  the routes go to route file in routes folder...
app.use('/', index);
app.use('/api', teachers);
app.use('/api', location);
app.use('/api', babysitter);


//testing server
app.get('/',(req,res)=>{
    res.sendFile(__dirname + '/index.html');
})

//app.listen(port,()=>{
 //   console.log('server started at port: '+port);
//})



// connect to mongo db
mongoose.connect('mongodb://localhost:27017/babyteacher');

//on connectionto mongo
mongoose.connection.on('connected',()=>{
    console.log('connected to database mongodb 27017');
});
//on error connection mongo
mongoose.connection.on('error',(err)=>{
    if(err)
        {
            console.log('error in  database connection:'+err);            
        }
});

///  socket---io 

var http = require('http').Server(app);
var io = require('socket.io')(http);

io.on('connection', function(socket){
    console.log('user connected');
    socket.on('chat message', function(msg){
      io.emit('chat message',msg );
    });
    //
    socket.on('disconnect', function(){
        console.log('user disconnected');
      });
    

  });
      
  http.listen(3000, function(){
    console.log('listening on *:3000');
  });