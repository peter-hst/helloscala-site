/** @jsx React.DOM */
var React = require('react');
var __isObject = require('lodash/lang/isObject');
var __isFunction = require('lodash/lang/isFunction');
var logger = require('../util/logger');

var TableLine = React.createClass({
    propTypes: {
        /**
         * name:
         * label:
         * type: image
         * createTd: function(item, tdNo, lineNo)
         * className:
         */
        headers: React.PropTypes.arrayOf(React.PropTypes.object).isRequired,
        item: React.PropTypes.object,
        lineNo: React.PropTypes.number,
        createOption: React.PropTypes.func
    },
    render: function () {
        var item = this.props.item;
        if (!__isObject(item)) {
            return null;
        }
        var headers = this.props.headers;
        var len = headers.length;
        var tds = [<td key={'d0'}>{this.props.lineNo}</td>];
        for (var i = 0; i < len; i++) {
            var th = headers[i];
            var content;
            if (__isFunction(th.createTd)) {
                content = th.createTd(item, len, this.props.lineNo);
            } else {
                switch (th.type) {
                    case 'image':
                        content = (
                            <a href={item[th.name]} target="_blank"><img className={th.className || 'img-responsive'}
                                                                         src={item[th.name]}/></a>);
                        break;
                    default :
                        content = item[th.name];
                        break;
                }
            }
            tds.push(<td key={'d' + tds.length}>{content}</td>);
        }

        var optionContent = (<noscript></noscript>);
        if (__isFunction(this.props.createOption)) {
            optionContent = this.props.createOption(item, this.props.lineNo);
        } else {
            logger.warn('function createOption not defined');
        }
        tds.push(<td key={'d'+tds.length}>{optionContent}</td>);

        return (<tr>{tds}</tr>);
    }
});

module.exports = TableLine;
