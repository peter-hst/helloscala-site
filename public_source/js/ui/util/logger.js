var NODE_ENV = process ? process.env.NODE_ENV : '';
var notProduction = 'production' !== NODE_ENV;

var logger = {
    info: function (msg) {
        if (notProduction) {
            console.info(msg);
        }
    },
    warn: function (msg) {
        if (notProduction) {
            console.warn(msg);
        }
    },
    log: function (msg) {
        if (notProduction) {
            console.log(msg);
        }
    }
};

module.exports = logger;
