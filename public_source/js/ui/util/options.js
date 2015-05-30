var __keys = require('lodash/object/keys');

var options = function (obj) {
    var opt = [];
    var keys = __keys(obj);
    for (var i = 0; i < keys.length; i++) {
        var key = keys[i];
        opt.push({label: obj[key], value: key});
    }
    return opt;
};

module.exports = options;