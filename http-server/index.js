const http = require('http');
const fs = require('fs');

const server = http.createServer((req, res) => {
    console.log(`${new Date()} Request was made: ${req.url}`);
    const readStream = fs.createReadStream('./../Main.class');
    readStream.pipe(res);
});
server.listen(3002);
console.log(`Server is running on port ${server.address().port}`);