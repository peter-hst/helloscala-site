var __isObject = require('lodash/lang/isObject');
var __isFunction = require('lodash/lang/isFunction');

module.exports = {
    emailRegex: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
    parseValue: function (value, dataType) {
        var result;
        switch (dataType) {
            case 'number':
                result = Number(value);
                break;

            case 'integer':
                result = parseInt(value);
                break;

            case 'string':
                if (__isObject(value) || __isFunction(value)) {
                    result = value.toString()
                } else {
                    result = '' + value;
                }
                break;

            default :
                result = value;
                break;
        }

        return result;
    }
};
